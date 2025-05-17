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

@RestController
@RequestMapping("/rent")
public class RentController {
    @Autowired
    private RentService rentService;

    @PostMapping()
    public RentDTO createRent(@RequestBody @Valid RentData rentData) {
        return rentService.createRent(rentData);
    }

    @GetMapping("locator")
    public Page<RentDTO> getRentsLocator(@PageableDefault(size = 10) Pageable page) {
        return rentService.getRentsLocator(page);
    }

    @GetMapping("owner")
    public Page<RentDTO> getRentsOwner(@PageableDefault(size = 10) Pageable page) {
        return rentService.getRentsOwener(page);
    }

    @PutMapping("accept")
    public RentDTO acceptRent(@RequestBody @Valid RentAcceptDTO rentData) {
        return rentService.acceptRent(rentData);
    }

    @DeleteMapping("/{id}")
    public void deleteRent(@PathVariable Long id) {
        rentService.deleteRent(id);
    }
}
