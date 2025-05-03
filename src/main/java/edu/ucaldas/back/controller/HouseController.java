package edu.ucaldas.back.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import edu.ucaldas.back.DTO.HouseImageSaveDTO;
import edu.ucaldas.back.DTO.HouseSaveDTO;
import edu.ucaldas.back.models.rent.HouseData;
import edu.ucaldas.back.models.rent.HouseImageData;
import edu.ucaldas.back.service.HouseImageService;
import edu.ucaldas.back.service.HouseService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private HouseImageService houseImageService;

    @GetMapping("/{id}")
    public String getHouse(@PathVariable Long id) {
        return "House data";
    }

    @GetMapping()
    public String getHousePages(@PageableDefault(size = 15, sort = "starts") Pageable page) {
        return "House pages data";
    }

    @PostMapping()
    public ResponseEntity<HouseSaveDTO> createHouse(@RequestBody @Valid HouseData house) {
        return ResponseEntity.ok(houseService.saveHouse(house));
    }

    @PostMapping("images")
    public ResponseEntity<HouseImageSaveDTO> uploadHouseImage(@RequestBody @Valid HouseImageData houseImageData,
            @RequestParam("image") MultipartFile file) throws IOException {
        HouseImageSaveDTO houseImage = houseImageService.saveHouseImage(file, houseImageData);
        return ResponseEntity.ok(houseImage);

    }

    @PutMapping("/{id}")
    public String updateHouse() {
        return "House updated";
    }

}
