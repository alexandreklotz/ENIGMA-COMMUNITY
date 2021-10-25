package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.GroupeDao;
import fr.alexandreklotz.enigma.dao.UtilisateurDao;
import fr.alexandreklotz.enigma.model.Groupe;
import fr.alexandreklotz.enigma.model.Utilisateur;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin

//@Controller Will be re-enabled when thymeleafs will be implemented
public class GroupeController {

    private GroupeDao groupeDao;
    private UtilisateurDao utilisateurDao;

    @Autowired
    GroupeController (GroupeDao groupeDao, UtilisateurDao utilisateurDao) {
        this.groupeDao = groupeDao;
        this.utilisateurDao = utilisateurDao;
    }

    //////////////////
    // Rest methods //
    //////////////////

    //Get method to retrieve all the groups in the database. Will probably be restricted to admins only later or even removed.
    //Will be used for development purposes meanwhile
    @GetMapping("/groupe/all")
    @JsonView(CustomJsonView.GroupeView.class)
    public ResponseEntity<List<Groupe>> getGroupesList(){
        return ResponseEntity.ok(groupeDao.findAll());
    }

    //Get method to retrieve one specific group with its id
    @GetMapping("/groupe/{id}")
    @JsonView(CustomJsonView.GroupeView.class)
    public ResponseEntity<Groupe> getGroupe(@PathVariable UUID id){
        Optional<Groupe> groupeBdd = groupeDao.findById(id);

        if (groupeBdd.isPresent()){
            return ResponseEntity.ok(groupeBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //Post method for group creation
    /* TODO : Method OK, but the group doesn't appear in the owner's userGroupes list. Find a way to save it in the user_groupes table */
    @PostMapping("/groupe/new")
    @JsonView(CustomJsonView.GroupeView.class)
    public void newGroupe (@RequestBody Groupe groupe){

        Utilisateur utilisateur = new Utilisateur();

        //We first generate a UUID that will be given to the group
        UUID groupeUuid = UUID.randomUUID();
        //We then assign it to the group
        groupe.setId(groupeUuid);
        //We then proceed to save its info, in this case we'll only save its name since it's the only variable that can be set by the user
        groupe.setNameGroupe(groupe.getNameGroupe());
        //We then set the owner of the group
        //groupe.setGroupeOwner(groupe.getGroupeOwner());
        groupe.setGroupeOwner(utilisateur.getId());
        //We save the creation date
        groupe.setDateCreationGroupe(Date.from(Instant.now()));
        //Users which are members of the group
        //groupe.setGroupesUsers(groupe.getGroupesUsers());
        //We then save the group
        groupeDao.saveAndFlush(groupe);
    }


    @DeleteMapping("/groupe/delete/{id}")
    public String deleteGroupe (@PathVariable UUID id){

        if(groupeDao.findById(id).isPresent()){
            groupeDao.deleteById(id);
            return "The group with the id '" + id + "' has been deleted.";
        } else {
            return "The group with the specified id doesn't exist in the database.";
        }

    }
}
