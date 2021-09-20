package fr.alexandreklotz.enigma.dao;

import fr.alexandreklotz.enigma.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UtilisateurDao extends JpaRepository<Utilisateur, UUID> {

}
