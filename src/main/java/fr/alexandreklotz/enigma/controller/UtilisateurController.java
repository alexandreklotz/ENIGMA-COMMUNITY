package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.UtilisateurDao;
import fr.alexandreklotz.enigma.model.Utilisateur;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin

//@Controller -> Will be used when thymeleafs will be implemented. Thymeleafs needs this bean in order to work properly.

public class UtilisateurController {

    private UtilisateurDao utilisateurDao;

    @Autowired
    UtilisateurController (UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    ////////////////
    //Rest methods//
    ////////////////

    //Get method to get all the users in the database. This method will be restricted to administrators later.
    //It will be used for development purposes until then.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/user/all")
    public ResponseEntity<List<Utilisateur>> getUsersList(){
        return ResponseEntity.ok(utilisateurDao.findAll());
    }

    //This method will be used when looking for a specific user with his id. Its use will be decided later.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/user/{id}")
    public ResponseEntity<Utilisateur> getUser(@PathVariable UUID id){
        Optional<Utilisateur> utilisateurBdd = utilisateurDao.findById(id);

        if (utilisateurBdd.isPresent()) {
            return ResponseEntity.ok(utilisateurBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }


    //Post method for user registration.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/user/new")
    public void newUtilisateur (@RequestBody Utilisateur utilisateur){

        //We first generate a random UUID
        UUID userUuid = UUID.randomUUID();
        //We then assign it to the new user
        utilisateur.setId(userUuid);
        //Then, we save his info.
        utilisateur.setEmailuser(utilisateur.getEmailuser());
        utilisateur.setUserChatPwd(utilisateur.getUserChatPwd());
        utilisateur.setUserLogin(utilisateur.getUserLogin());
        utilisateur.setUserNickname(utilisateur.getUserNickname());
        utilisateur.setUserPassword(utilisateur.getUserPassword());
        utilisateur.setUserPublicId(utilisateur.getUserPublicId());
        //The registration date is saved
        utilisateur.setUserDateRegistration(Date.from(Instant.now()));
        //And once everything is saved, we save the user itself.
        utilisateurDao.saveAndFlush(utilisateur);
    }

    //This method was originally part of the post method. I split it in two different methods,
    //one to update existing users and another one to create new users.
    /* TODO : Check if this method is working, if not make it work.*/
    @JsonView(CustomJsonView.UtilisateurView.class)
    @PatchMapping("/user/modify")
    public void updateUtilisateur (@PathVariable UUID id, @RequestBody Utilisateur utilisateur){
        Optional<Utilisateur> utilisateurBdd = utilisateurDao.findById(utilisateur.getId());

        //We retrieve the user using his ID.
        utilisateur.setId(utilisateurBdd.get().getId());

        //We then modify its info.
        utilisateurBdd.get().setEmailuser(utilisateur.getEmailuser());
        utilisateurBdd.get().setUserChatPwd(utilisateur.getUserChatPwd());
        utilisateurBdd.get().setUserLogin(utilisateur.getUserLogin());
        utilisateurBdd.get().setUserNickname(utilisateur.getUserNickname());
        utilisateurBdd.get().setUserPassword(utilisateur.getUserPassword());
        utilisateurBdd.get().setUserPublicId(utilisateur.getUserPublicId());
        //We then save the user
        utilisateurDao.saveAndFlush(utilisateur);

    }

}
