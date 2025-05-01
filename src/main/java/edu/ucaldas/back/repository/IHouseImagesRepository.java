package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.rent.HouseImages;

@Repository
public interface IHouseImagesRepository extends JpaRepository<HouseImages, Long> {
    // Custom query methods can be defined here if needed

}
