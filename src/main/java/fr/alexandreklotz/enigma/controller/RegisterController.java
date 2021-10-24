package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.UtilisateurDao;
import fr.alexandreklotz.enigma.model.Utilisateur;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin
public class RegisterController {

    private UtilisateurDao utilisateurDao;

    @Autowired
    RegisterController(UtilisateurDao utilisateurDao) {
        this.utilisateurDao = utilisateurDao;
    }

    //////////////////
    // Rest methods //
    //////////////////

    //Post method for user registration.
    @JsonView(CustomJsonView.UtilisateurView.class)
    @PostMapping("/register")
    public void userRegistration (@RequestBody Utilisateur utilisateur){

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
}
