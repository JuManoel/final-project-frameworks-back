package edu.ucaldas.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ucaldas.back.models.chat.Chat;

@Repository
public interface IChatRepository extends JpaRepository<Chat, Long> {

}
