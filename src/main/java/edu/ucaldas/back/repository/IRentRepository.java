package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.ucaldas.back.models.rent.Rent;

@Repository
public interface IRentRepository extends JpaRepository<Rent, Long> {
    
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
           "FROM Rent r " +
           "WHERE r.isActive = true AND (r.house.owner.email = :email OR r.locator.email = :email)")
    boolean existsActiveRentByUserEmail(@Param("email") String email);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END " +
        "FROM Rent r " +
        "WHERE r.isActive = true AND r.house.id = :houseId")
    boolean existsActiveRentByHouseId(@Param("houseId") Long houseId);
}
