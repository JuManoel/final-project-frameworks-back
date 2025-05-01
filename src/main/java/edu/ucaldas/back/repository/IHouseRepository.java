package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.rent.House;

@Repository
public interface IHouseRepository extends JpaRepository<House, Long> {
    // Custom query methods can be defined here if needed

}
