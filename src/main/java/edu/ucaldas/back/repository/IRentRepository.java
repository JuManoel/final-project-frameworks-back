package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.rent.Rent;

@Repository
public interface IRentRepository extends JpaRepository<Rent, Long> {

}
