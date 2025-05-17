package edu.ucaldas.back.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.ucaldas.back.DTO.HouseGetDTO;
import edu.ucaldas.back.DTO.HouseImageSaveDTO;
import edu.ucaldas.back.DTO.HouseSaveDTO;
import edu.ucaldas.back.DTO.HouseUpdateDTO;
import edu.ucaldas.back.models.rent.HouseData;
import edu.ucaldas.back.models.rent.HouseImageData;
import edu.ucaldas.back.service.HouseImageService;
import edu.ucaldas.back.service.HouseService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;

/**
 * REST controller for managing house entities and their images.
 * <p>
 * Provides endpoints to:
 * <ul>
 *   <li>Retrieve house details by ID</li>
 *   <li>Retrieve paginated and sorted lists of houses</li>
 *   <li>Create new houses</li>
 *   <li>Upload images for houses</li>
 *   <li>Update existing house information</li>
 *   <li>Delete houses by ID</li>
 * </ul>
 * <p>
 * Endpoints are mapped under the "/house" path.
 * <p>
 * Uses {@link HouseService} for house operations and {@link HouseImageService} for image handling.
 */
@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseImageService houseImageService;

    /**
     * Retrieves the details of a house by its unique identifier.
     *
     * @param id the unique identifier of the house to retrieve
     * @return a {@link HouseGetDTO} containing the house details
     */
    @GetMapping("/{id}")
    public HouseGetDTO getHouse(@PathVariable Long id) {
        return houseService.getHouse(id);
    }

    /**
     * Retrieves a paginated list of houses, sorted by the "stars" property by default.
     *
     * @param page the pagination and sorting information, with a default page size of 15 and sorted by "stars"
     * @return a page of {@link HouseGetDTO} objects representing the houses
     */
    @GetMapping()
    public Page<HouseGetDTO> getHousePages(@PageableDefault(size = 15, sort = "stars") Pageable page) {
        return houseService.getHouses(page);
    }

    /**
     * Handles HTTP POST requests to create a new house.
     *
     * @param house the house data to be created, validated from the request body
     * @return a ResponseEntity containing the saved HouseSaveDTO
     */
    @PostMapping()
    public ResponseEntity<HouseSaveDTO> createHouse(@RequestBody @Valid HouseData house) {
        return ResponseEntity.ok(houseService.saveHouse(house));
    }

    /**
     * Handles HTTP POST requests to upload an image for a house.
     *
     * @param houseImageData the metadata for the house image, validated from the request body
     * @param file the image file to be uploaded, received as a multipart file from the request
     * @return a ResponseEntity containing the saved house image data as a HouseImageSaveDTO
     * @throws IOException if an error occurs during file processing
     */
    @PostMapping("images")
    public ResponseEntity<HouseImageSaveDTO> uploadHouseImage(@RequestBody @Valid HouseImageData houseImageData,
            @RequestParam("image") MultipartFile file) throws IOException {
        HouseImageSaveDTO houseImage = houseImageService.saveHouseImage(file, houseImageData);
        return ResponseEntity.ok(houseImage);

    }

    /**
     * Updates the details of an existing house with the specified ID.
     *
     * @param id the unique identifier of the house to be updated
     * @param houseUpdateDTO the data transfer object containing updated house information
     * @return the updated HouseUpdateDTO object
     */
    @PutMapping("/{id}")
    public HouseUpdateDTO updateHouse(@PathVariable long id, @RequestBody @Valid HouseUpdateDTO houseUpdateDTO) {
        return houseService.updateHouse(id, houseUpdateDTO);
    }

    /**
     * Deletes a house with the specified ID.
     *
     * @param id the ID of the house to be deleted
     * @return a ResponseEntity containing a success message if the house was deleted successfully
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHouse(@PathVariable long id) {
        houseService.deleteHouse(id);
        return ResponseEntity.ok("House deleted successfully");
    }

}
