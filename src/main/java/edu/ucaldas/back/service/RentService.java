package edu.ucaldas.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import edu.ucaldas.back.DTO.RentAcceptDTO;
import edu.ucaldas.back.DTO.RentDTO;
import edu.ucaldas.back.infra.exception.NotPermited;
import edu.ucaldas.back.models.rent.Rent;
import edu.ucaldas.back.models.rent.RentData;
import edu.ucaldas.back.models.user.User;
import edu.ucaldas.back.repository.IHouseRepository;
import edu.ucaldas.back.repository.IRentRepository;
import edu.ucaldas.back.repository.IUserRepository;
import edu.ucaldas.back.service.validations.ValidationsHouse;
import edu.ucaldas.back.service.validations.ValidationsRent;
import edu.ucaldas.back.service.validations.ValidationsUser;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

/**
 * Service class for managing rental operations in the application.
 * <p>
 * This service handles the creation, acceptance, retrieval, and deletion (soft delete) of rental agreements
 * between users (locators) and house owners. It enforces business rules such as preventing users from renting
 * their own houses, ensuring only one active rent per user, and validating the existence and availability of
 * houses and users.
 * </p>
 *
 * <p>
 * Main responsibilities include:
 * <ul>
 *   <li>Creating new rental agreements with all necessary validations.</li>
 *   <li>Retrieving paginated lists of active rents for both locators and house owners.</li>
 *   <li>Accepting or rejecting rent requests with proper authorization and business rule enforcement.</li>
 *   <li>Soft-deleting (deactivating) rents, ensuring only house owners can perform this action.</li>
 * </ul>
 * </p>
 *
 * <p>
 * This service relies on repository interfaces for data access and validation helper classes to enforce
 * business logic and entity existence.
 * </p>
 *
 * @see edu.ucaldas.back.repository.IRentRepository
 * @see edu.ucaldas.back.repository.IHouseRepository
 * @see edu.ucaldas.back.repository.IUserRepository
 * @see edu.ucaldas.back.validations.ValidationsHouse
 * @see edu.ucaldas.back.validations.ValidationsUser
 * @see edu.ucaldas.back.validations.ValidationsRent
 */
@Service
public class RentService {
    @Autowired
    private IRentRepository rentRepository;

    @Autowired
    private IHouseRepository houseRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ValidationsHouse validationsHouse;

    @Autowired
    private ValidationsUser validationsUser;

    @Autowired
    private ValidationsRent validationsRent;

    /**
     * Creates a new rent based on the provided rent data.
     * <p>
     * This method performs several validations before creating the rent:
     * <ul>
     *   <li>Checks if the house exists.</li>
     *   <li>Checks if the user (locator) exists.</li>
     *   <li>Prevents the owner from renting their own house.</li>
     *   <li>Prevents a user from renting if they already have an active rent.</li>
     * </ul>
     * If all validations pass, a new {@link Rent} is created and saved, and a {@link RentDTO} is returned.
     *
     * @param rentData the data required to create the rent
     * @return a {@link RentDTO} containing the details of the created rent
     * @throws EntityNotFoundException if the house or user does not exist
     * @throws NotPermited if the user tries to rent their own house or already has an active rent
     */
    public RentDTO createRent(RentData rentData) {
        System.out.println(rentData);
        if(!validationsHouse.existsHouse(rentData.houseId())){
            throw new EntityNotFoundException("La casa no existe");
        }
        if(!validationsUser.existsUser(rentData.locator())){
            throw new EntityNotFoundException("El usuario no existe");
        }
        var house = houseRepository.findByIdAndIsActiveTrue(rentData.houseId()).get();
        var locator = userRepository.getUser(rentData.locator()).get();
        if(house.getOwner().getEmail().equals(locator.getEmail())){
            throw new NotPermited("No puedes alquilar tu propia casa");
        }
        if(validationsRent.userHasRent(locator.getEmail())){
            throw new NotPermited("No puedes alquilar una casa si ya tienes un alquiler activo");
        }
        Rent rent = new Rent(rentData, house, locator);
        rentRepository.save(rent);
        return new RentDTO(rent.getId(), house.getId(), locator.getEmail(), false);
    }

    /**
     * Retrieves a paginated list of active rents associated with the currently authenticated locator (user).
     *
     * <p>This method obtains the authenticated user's email and fetches all active rent records
     * where the locator's email matches the user's email. The results are returned as a page of
     * {@link RentDTO} objects.</p>
     *
     * @param page the pagination information
     * @return a {@link Page} of {@link RentDTO} representing the active rents for the locator
     */
    public Page<RentDTO> getRentsLocator(Pageable page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        var rents = rentRepository.findByLocatorEmailAndIsActiveTrue(user.getEmail(), page);
        return rents.map(rent -> new RentDTO(rent.getId(), rent.getHouse().getId(), rent.getLocator().getEmail(), rent.isActive()));
    }

    /**
     * Retrieves a paginated list of active rents for the currently authenticated house owner.
     *
     * <p>This method obtains the authenticated user's email and fetches all active rent records
     * associated with houses owned by that user. The results are returned as a page of {@link RentDTO} objects.</p>
     *
     * @param page the pagination information
     * @return a {@link Page} of {@link RentDTO} representing the active rents for the house owner
     */
    public Page<RentDTO> getRentsOwener(Pageable page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        var rents = rentRepository.findByHouseOwnerEmailAndIsActiveTrue(user.getEmail(), page);
        return rents.map(rent -> new RentDTO(rent.getId(), rent.getHouse().getId(), rent.getLocator().getEmail(), rent.isActive()));
    }   
    
    /**
     * Accepts or rejects a rent request based on the provided RentAcceptDTO.
     * <p>
     * This method performs several validations:
     * <ul>
     *     <li>Checks if the rent exists.</li>
     *     <li>Ensures the authenticated user is the locator who requested the rent.</li>
     *     <li>Verifies the house is active and available.</li>
     *     <li>Ensures the user does not already have an active rent.</li>
     * </ul>
     * If all validations pass, the rent is accepted or rejected according to the DTO,
     * the house's availability is updated, and both entities are saved.
     *
     * @param rentAcceptDTO Data transfer object containing the rent ID and acceptance status.
     * @return RentDTO containing updated rent information.
     * @throws EntityNotFoundException if the rent does not exist.
     * @throws NotPermited if the user is not authorized to accept the rent or if business rules are violated.
     */
    @Transactional
    public RentDTO acceptRent(RentAcceptDTO rentAcceptDTO) {
        if(!validationsRent.existsRent(rentAcceptDTO.id())){
            throw new EntityNotFoundException("El alquiler no existe");
        }
        var rent = rentRepository.findByIdAndIsActiveTrue(rentAcceptDTO.id()).get();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(!rent.getLocator().getEmail().equals(user.getEmail())){
            throw new NotPermited("No puedes aceptar un alquiler que no has solicitado");
        }
        if(!rent.getHouse().isActive() || !rent.getHouse().isActive()){
            throw new NotPermited("No puedes aceptar un alquiler de una casa que no esta disponible");
        }
        if(validationsRent.userHasRent(user.getEmail())){
            throw new NotPermited("No puedes alquilar una casa si ya tienes un alquiler activo");
        }
        rent.setAccepted(rentAcceptDTO.accepted());
        rent.getHouse().setAvailable(!rentAcceptDTO.accepted());
        houseRepository.save(rent.getHouse());
        rentRepository.save(rent);
        return new RentDTO(rent.getId(), rent.getHouse().getId(), rent.getLocator().getEmail(), rent.isActive());
    }

    /**
     * Deletes (deactivates) a rent by its ID if the current authenticated user is the owner of the house.
     * <p>
     * This method performs the following actions:
     * <ul>
     *     <li>Checks if the rent with the specified ID exists; throws {@link EntityNotFoundException} if not.</li>
     *     <li>Verifies that the authenticated user is the owner of the house associated with the rent; throws {@link NotPermited} if not.</li>
     *     <li>Marks the rent as inactive (soft delete) and saves the change.</li>
     *     <li>Sets the associated house as available and saves the change.</li>
     * </ul>
     *
     * @param id the ID of the rent to delete
     * @throws EntityNotFoundException if the rent does not exist
     * @throws NotPermited if the current user is not the owner of the house
     */
    @Transactional
    public void deleteRent(long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        if(!validationsRent.existsRent(id)){
            throw new EntityNotFoundException("El alquiler no existe");
        }
        var rent = rentRepository.findByIdAndIsActiveTrue(id).get();
        if(!rent.getHouse().getOwner().getEmail().equals(user.getEmail())){
            throw new NotPermited("No puedes eliminar un alquilar de una casa que no es tuya");
        }
        rent.setActive(false);
        rentRepository.save(rent);
        rent.getHouse().setAvailable(true);
        houseRepository.save(rent.getHouse());
    }
}
