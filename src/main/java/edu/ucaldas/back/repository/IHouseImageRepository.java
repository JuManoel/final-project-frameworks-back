package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.rent.HouseImage;

@Repository
public interface IHouseImageRepository extends JpaRepository<HouseImage, Long> {
    // Custom query methods can be defined here if needed

}
