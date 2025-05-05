package edu.ucaldas.back.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.ucaldas.back.DTO.HouseImageSaveDTO;
import edu.ucaldas.back.infra.exception.SaveFileError;
import edu.ucaldas.back.models.rent.HouseImage;
import edu.ucaldas.back.models.rent.HouseImageData;
import edu.ucaldas.back.repository.IHouseImageRepository;
import edu.ucaldas.back.repository.IHouseRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Service class for managing house images.
 * 
 * This service provides functionality to save image files associated with houses.
 * It validates the file type, ensures the target directory exists, and associates
 * the image with a house in the database.
 * 
 * Dependencies:
 * - IHouseImageRepository: Repository for managing house image entities.
 * - IHouseRepository: Repository for managing house entities.
 * 
 * Methods:
 * - saveHouseImage(MultipartFile file, HouseImageData houseImageData): Saves an image
 *   file associated with a house and returns the details of the saved image.
 * 
 * Exceptions:
 * - IllegalArgumentException: Thrown if the provided file is not an image.
 * - EntityNotFoundException: Thrown if the house with the given ID is not found or is inactive.
 * - SaveFileError: Thrown if an error occurs while saving the image file.
 * - IOException: Thrown if an I/O error occurs during file saving.
 */
@Service
public class HouseImageService {

    @Autowired
    private IHouseImageRepository houseImageRepository;
    @Autowired
    private IHouseRepository houseRepository;

    /**
     * Saves an image file associated with a house and returns the details of the
     * saved image.
     *
     * @param file           The image file to be saved. Must be of a valid image
     *                       MIME type.
     * @param houseImageData Data containing the house ID to associate the image
     *                       with.
     * @return A DTO containing the house ID and the saved file name.
     * @throws IOException              If an I/O error occurs during file saving.
     * @throws IllegalArgumentException If the provided file is not an image.
     * @throws EntityNotFoundException  If the house with the given ID is not found
     *                                  or is inactive.
     * @throws SaveFileError            If an error occurs while saving the image
     *                                  file.
     */
    public HouseImageSaveDTO saveHouseImage(MultipartFile file, HouseImageData houseImageData) throws IOException {
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("Invalid file type. Only image files are allowed.");
        }
        try {
            String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
            // Ensure the uploads directory exists
            Path uploadsDir = Paths.get("uploads");
            if (!Files.exists(uploadsDir)) {
                Files.createDirectories(uploadsDir);
            }
            Path path = Paths.get("uploads/" + fileName);
            Files.copy(file.getInputStream(), path);

            var house = houseRepository.findByIdAndIsActiveTrue(houseImageData.houseId())
                    .orElseThrow(() -> new EntityNotFoundException("House not found"));
            HouseImage houseImage = new HouseImage(house, fileName);
            houseImageRepository.save(houseImage);
            HouseImageSaveDTO houseImageSaveDTO = new HouseImageSaveDTO(house.getId(), fileName);
            return houseImageSaveDTO;
        } catch (Exception e) {
            throw new SaveFileError("Error trying to save image", e);
        }

    }
}
