package edu.ucaldas.back.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.user.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndIsActiveTrue(Long id);
    UserDetails findByEmailAndIsActiveTrue(String email);

}
