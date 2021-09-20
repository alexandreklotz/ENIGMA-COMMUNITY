package fr.alexandreklotz.enigma.dao;

import fr.alexandreklotz.enigma.model.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GroupeDao extends JpaRepository<Groupe, UUID> {
}
