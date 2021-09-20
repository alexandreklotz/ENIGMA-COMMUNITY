package fr.alexandreklotz.enigma.dao;

import fr.alexandreklotz.enigma.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageDao extends JpaRepository<Message, UUID> {

}