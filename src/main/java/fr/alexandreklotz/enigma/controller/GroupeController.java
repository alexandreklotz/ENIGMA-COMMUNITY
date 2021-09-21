package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.GroupeDao;
import fr.alexandreklotz.enigma.model.Groupe;
import fr.alexandreklotz.enigma.model.Utilisateur;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.hibernate.type.UUIDCharType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin

//@Controller Will be re-enabled when thymeleafs will be implemented
public class GroupeController {

    private GroupeDao groupeDao;

    @Autowired
    GroupeController (GroupeDao groupeDao) {
        this.groupeDao = groupeDao;
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
    /* TODO : This method needs to be tested. I think it wont retrieve the user's uuid. */
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
        groupe.setGroupeOwner(utilisateur.getId());

        //We then save the group
        groupeDao.saveAndFlush(groupe);
    }
}
