package edu.ucaldas.back.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ucaldas.back.DTO.RentAcceptDTO;
import edu.ucaldas.back.DTO.RentDTO;
import edu.ucaldas.back.models.rent.RentData;
import edu.ucaldas.back.service.RentService;
import jakarta.validation.Valid;

/**
 * Controller for managing rent-related operations.
 * <p>
 * This controller provides endpoints for creating, retrieving, updating, and deleting rent records.
 * It supports paginated retrieval of rents for both locators and owners, as well as accepting and deleting rents.
 * </p>
 * 
 * <ul>
 *   <li><b>POST /rent</b>: Create a new rent.</li>
 *   <li><b>GET /rent/locator</b>: Retrieve a paginated list of rents for the locator.</li>
 *   <li><b>GET /rent/owner</b>: Retrieve a paginated list of rents for the owner.</li>
 *   <li><b>PUT /rent/accept</b>: Accept a rent request.</li>
 *   <li><b>DELETE /rent/{id}</b>: Delete a rent by its ID.</li>
 * </ul>
 * 
 * <p>
 * All endpoints expect and return data in JSON format.
 * </p>
 * 
 * @author juan-manoel
 * @see RentService
 */
@RestController
@RequestMapping("/rent")
public class RentController {
    @Autowired
    private RentService rentService;

    /**
     * Handles HTTP POST requests to create a new rent.
     *
     * @param rentData the data for the rent to be created, validated from the request body
     * @return the created RentDTO object
     */
    @PostMapping()
    public RentDTO createRent(@RequestBody @Valid RentData rentData) {
        return rentService.createRent(rentData);
    }

    /**
     * Retrieves a paginated list of rents for the locator.
     *
     * @param page the pagination information, with a default page size of 10
     * @return a page of {@link RentDTO} objects representing the rents
     */
    @GetMapping("locator")
    public Page<RentDTO> getRentsLocator(@PageableDefault(size = 10) Pageable page) {
        return rentService.getRentsLocator(page);
    }

    /**
     * Retrieves a paginated list of rents associated with the owner.
     *
     * @param page the pagination information, including page number and size
     * @return a page of {@link RentDTO} objects representing the owner's rents
     */
    @GetMapping("owner")
    public Page<RentDTO> getRentsOwner(@PageableDefault(size = 10) Pageable page) {
        return rentService.getRentsOwener(page);
    }

    /**
     * Handles HTTP PUT requests to accept a rent.
     * 
     * @param rentData the data required to accept the rent, validated and mapped from the request body
     * @return the updated RentDTO after accepting the rent
     */
    @PutMapping("accept")
    public RentDTO acceptRent(@RequestBody @Valid RentAcceptDTO rentData) {
        return rentService.acceptRent(rentData);
    }

    /**
     * Deletes a rent record with the specified ID.
     *
     * @param id the ID of the rent to be deleted
     */
    @DeleteMapping("/{id}")
    public void deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
    }
}
