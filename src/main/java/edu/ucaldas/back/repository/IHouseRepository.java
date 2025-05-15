package edu.ucaldas.back.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.ucaldas.back.models.rent.House;

@Repository
public interface IHouseRepository extends JpaRepository<House, Long> {
        // Custom query methods can be defined here if needed
        Optional<House> findByIdAndIsActiveTrue(Long id); // Find house by ID and user ID

        @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM House h WHERE h.address.street = :street AND h.address.city = :city AND h.address.state = :state AND h.address.number = :number AND h.address.complement = :complement AND h.isActive = true")
        boolean existsByAddress(@Param("street") String street, @Param("city") String city,
                        @Param("state") String state,
                        @Param("number") String number, @Param("complement") String complement);

        @Query("SELECT CASE WHEN COUNT(h) > 0 THEN true ELSE false END FROM House h WHERE h.owner.email = :email AND h.isActive = true")
        boolean existsByOwnerEmail(@Param("email") String email);

        @Query("SELECT h FROM House h WHERE h.isActive = true AND h.isAvailable = true")
        Page<House> findAllAvailableAndActiveHouses(Pageable pageable);

}
