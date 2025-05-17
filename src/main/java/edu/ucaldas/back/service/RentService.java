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

    public RentDTO createRent(RentData rentData) {
        System.out.println(rentData);
        if(!validationsHouse.existsHouse(rentData.houseId())){
            throw new EntityNotFoundException("La casa no existe");
        }
        if(!validationsUser.existsEmail(rentData.locator())){
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

    public Page<RentDTO> getRentsLocator(Pageable page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        var rents = rentRepository.findByLocatorEmailAndIsActiveTrue(user.getEmail(), page);
        return rents.map(rent -> new RentDTO(rent.getId(), rent.getHouse().getId(), rent.getLocator().getEmail(), rent.isActive()));
    }

    public Page<RentDTO> getRentsOwener(Pageable page){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        var rents = rentRepository.findByHouseOwnerEmailAndIsActiveTrue(user.getEmail(), page);
        return rents.map(rent -> new RentDTO(rent.getId(), rent.getHouse().getId(), rent.getLocator().getEmail(), rent.isActive()));
    }   
    
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
