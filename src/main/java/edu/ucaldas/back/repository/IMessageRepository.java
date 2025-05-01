package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.chat.Message;

@Repository
public interface IMessageRepository extends JpaRepository<Message, Long> {
    // Custom query methods can be defined here if needed

}
