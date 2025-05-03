package edu.ucaldas.back.service.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ucaldas.back.repository.IUserRepository;

@Component
public class ValidationsUser {
    @Autowired
    private IUserRepository userRepository;

    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsUser(Long id) {
        return userRepository.findByIdAndIsActiveTrue(id).isPresent();
    }

    public boolean existsUser(String email) {
        return userRepository.existsByEmail(email);
    }
}
