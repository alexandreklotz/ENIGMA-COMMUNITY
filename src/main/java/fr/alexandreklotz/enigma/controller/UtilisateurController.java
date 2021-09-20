package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.UtilisateurDao;
import fr.alexandreklotz.enigma.model.Utilisateur;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/users")
    public ResponseEntity<List<Utilisateur>> getUsersList(){
        return ResponseEntity.ok(utilisateurDao.findAll());
    }

    //This method will be used when looking for a specific user with his id. Its use will be decided later.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @GetMapping("/users/{id}")
    public ResponseEntity<Utilisateur> getUser(@PathVariable UUID id){
        Optional<Utilisateur> utilisateurBdd = utilisateurDao.findById(id);

        if (utilisateurBdd.isPresent()) {
            return ResponseEntity.ok(utilisateurBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //Post method to create a new user.

    /* TODO : Check this method. Since there are no users in the database yet, it returns null and prevents the rest of the method to work.
        No users -> if returns 0 but when we send a JSON form with POSTMAN it refuses to create a new user saying that the given id must not be null.
        But it's the method's job to generate a UUID. It needs to be sorted out. */

    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/users/new")
    public void newUtilisateur (@RequestBody Utilisateur utilisateur){

        Optional<Utilisateur> utilisateurBdd = utilisateurDao.findById(utilisateur.getId());

        if (utilisateurBdd.isPresent()){
            //If the user exists, we retrieve it by using its UUID.
            utilisateur.setId(utilisateurBdd.get().getId());

            //We then modify its info.
            utilisateurBdd.get().setEmailuser(utilisateur.getEmailuser());
            utilisateurBdd.get().setUserChatPwd(utilisateur.getUserChatPwd());
            utilisateurBdd.get().setUserLogin(utilisateur.getUserLogin());
            utilisateurBdd.get().setUserNickname(utilisateur.getUserNickname());
            utilisateurBdd.get().setUserPassword(utilisateur.getUserPassword());
            utilisateurBdd.get().setUserPublicId(utilisateur.getUserPublicId());
        } else {
            //If the user doesn't exist, we create it.

            utilisateur.setId(UUID.randomUUID());

            utilisateur.setEmailuser(utilisateur.getEmailuser());
            utilisateur.setUserChatPwd(utilisateur.getUserChatPwd());
            utilisateur.setUserLogin(utilisateur.getUserLogin());
            utilisateur.setUserNickname(utilisateur.getUserNickname());
            utilisateur.setUserPassword(utilisateur.getUserPassword());
            utilisateur.setUserPublicId(utilisateur.getUserPublicId());
        }
    }

}
