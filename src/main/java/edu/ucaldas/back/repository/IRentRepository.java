package edu.ucaldas.back.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.ucaldas.back.models.rent.Rent;

@Repository
public interface IRentRepository extends JpaRepository<Rent, Long> {
    
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM Rent r " +
           "WHERE r.isActive = true AND (r.house.owner.email = :email OR r.locator.email = :email) AND r.accepted = true")
    boolean existsActiveRentByUserEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
        "FROM Rent r " +
        "WHERE r.isActive = true AND r.house.id = :houseId")
    boolean existsActiveRentByHouseId(@Param("houseId") Long houseId);

    Page<Rent> findByLocatorEmailAndIsActiveTrue(String email, org.springframework.data.domain.Pageable pageable);
    Page<Rent> findByHouseOwnerEmailAndIsActiveTrue(String email, org.springframework.data.domain.Pageable pageable);

    boolean existsByIdAndIsActiveTrue(Long id);

    Optional<Rent> findByIdAndIsActiveTrue(long id);
}
