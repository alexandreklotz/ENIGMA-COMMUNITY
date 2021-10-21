package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.GroupeDao;
import fr.alexandreklotz.enigma.dao.MessageDao;
import fr.alexandreklotz.enigma.dao.UtilisateurDao;
import fr.alexandreklotz.enigma.model.Groupe;
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
    private MessageDao messageDao;
    private GroupeDao groupeDao;

    @Autowired
    UtilisateurController (UtilisateurDao utilisateurDao, MessageDao messageDao, GroupeDao groupeDao) {
        this.utilisateurDao = utilisateurDao;
        this.messageDao = messageDao;
        this.groupeDao = groupeDao;
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
    /* TODO : Check if this method is working, if not make it work. Change the Patch to Put*/
    @JsonView(CustomJsonView.UtilisateurView.class)
    @PatchMapping("/user/modify")
    public void updateUtilisateur (@PathVariable UUID id, @RequestBody Utilisateur utilisateur){
        Optional<Utilisateur> utilisateurBdd = utilisateurDao.findById(utilisateur.getId());

        if (utilisateurBdd.isPresent()) {
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
        } else {

        }
    }

    // TODO : When trying to delete a user, it returns an error
    @DeleteMapping("/user/delete/{id}")
    public String deleteUser (@PathVariable UUID id){

        Utilisateur utilisateur = new Utilisateur();

        if (utilisateurDao.findById(utilisateur.getId()).isPresent()){
            utilisateurDao.deleteById(id);
            return "The user with the id '" + id + "' has been deleted.";
        } else {
            return "This user doesn't exist. Impossible to complete deletion.";
        }
    }

}
