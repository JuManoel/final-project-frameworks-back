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
 * Service class responsible for handling operations related to house images.
 * <p>
 * Provides functionality to save image files associated with houses, ensuring
 * that only valid image files are accepted, generating unique filenames, storing
 * files in the designated directory, and persisting image metadata linked to houses.
 * </p>
 *
 * <p>
 * Dependencies:
 * <ul>
 *   <li>{@link IHouseImageRepository} for persisting image entities.</li>
 *   <li>{@link IHouseRepository} for retrieving and validating house entities.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Main responsibilities:
 * <ul>
 *   <li>Validates uploaded files to ensure they are images.</li>
 *   <li>Generates unique filenames and stores files in the "uploads" directory.</li>
 *   <li>Associates images with active house entities.</li>
 *   <li>Handles exceptions related to file operations and entity persistence.</li>
 * </ul>
 * </p>
 */
@Service
public class HouseImageService {

    @Autowired
    private IHouseImageRepository houseImageRepository;
    @Autowired
    private IHouseRepository houseRepository;

    /**
     * Saves an image file associated with a house.
     * <p>
     * This method validates the uploaded file to ensure it is an image, generates a unique filename,
     * stores the file in the "uploads" directory, and creates a new {@link HouseImage} entity linked to the specified house.
     * </p>
     *
     * @param file the image file to be uploaded and saved
     * @param houseImageData data containing the house ID to associate the image with
     * @return a {@link HouseImageSaveDTO} containing the house ID and the saved file name
     * @throws IOException if an I/O error occurs during file saving
     * @throws IllegalArgumentException if the uploaded file is not an image
     * @throws EntityNotFoundException if the specified house does not exist or is not active
     * @throws SaveFileError if any other error occurs while saving the image or entity
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
