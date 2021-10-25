package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.GroupeDao;
import fr.alexandreklotz.enigma.dao.UtilisateurDao;
import fr.alexandreklotz.enigma.model.Utilisateur;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin

//@Controller -> Will be used when thymeleafs will be implemented. Thymeleafs needs this bean in order to work properly.

public class UtilisateurController {

    private UtilisateurDao utilisateurDao;
    private GroupeDao groupeDao;

    @Autowired
    UtilisateurController (UtilisateurDao utilisateurDao, GroupeDao groupeDao) {
        this.utilisateurDao = utilisateurDao;
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


    //This method was originally part of the post method. I split it in two different methods,
    //one to update existing users and another one to create new users.
    /* TODO : Check if this method is working, if not make it work */
    @JsonView(CustomJsonView.UtilisateurView.class)
    @PutMapping("/user/modify/{id}")
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

    @DeleteMapping("/user/delete/{id}")
    public String deleteUser (@PathVariable UUID id){

        if (utilisateurDao.findById(id).isPresent()){
            utilisateurDao.deleteById(id);
            return "The user with the id '" + id + "' has been deleted.";
        } else {
            return "This user doesn't exist. Impossible to complete deletion.";
        }
    }

}
