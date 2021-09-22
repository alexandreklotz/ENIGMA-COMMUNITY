package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.GroupeDao;
import fr.alexandreklotz.enigma.dao.MessageDao;
import fr.alexandreklotz.enigma.dao.UtilisateurDao;
import fr.alexandreklotz.enigma.model.Message;
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

//@Controller Will be used when thymeleafs will be implemented
public class MessageController {

    private MessageDao messageDao;
    private UtilisateurDao utilisateurDao;
    private GroupeDao groupeDao;

    @Autowired
    MessageController (MessageDao messageDao, UtilisateurDao utilisateurDao, GroupeDao groupeDao){
        this.messageDao = messageDao;
        this.utilisateurDao = utilisateurDao;
        this.groupeDao = groupeDao;
    }

    //////////////////
    // Rest methods //
    //////////////////

    //Get method to retrieve all messages. Will probably be restricted to admins later on.
    //Used for development purposes until then.
    @JsonView(CustomJsonView.MessageView.class)
    @GetMapping("/message/all")
    public ResponseEntity<List<Message>> getListeMessages(){
        return ResponseEntity.ok(messageDao.findAll());
    }

    //Get method to retrieve one message with its id.
    @JsonView(CustomJsonView.MessageView.class)
    @GetMapping("/message/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable UUID id){
        Optional<Message> messageBdd = messageDao.findById(id);

        if (messageBdd.isPresent()){
            return ResponseEntity.ok(messageBdd.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    //Post method to create a new message.
    /* TODO : Get the "userMessages" list to work. */
    @JsonView(CustomJsonView.MessageView.class)
    @PostMapping("/message/new")
    public void newMessage (@RequestBody Message message){

        //We generate a random UUID that will be given to the message
        UUID messageUuid = UUID.randomUUID();
        message.setId(messageUuid);

        //We then create a user object linked to the interface's findById method
        Optional<Utilisateur> utilisateurSender = utilisateurDao.findById(message.getSenderId());
        Optional<Utilisateur> utilisateurReceiver = utilisateurDao.findById(message.getRecipientId());

        if (utilisateurSender.isPresent() && utilisateurReceiver.isPresent()) {
            //We then retrieve its ID to assign it to the senderId variable
            message.setSenderId(message.getSenderId());
            //We set the other message's variables
            message.setMsgText(message.getMsgText()); //Message encryption will be done later
            message.setRecipientId(message.getRecipientId());
            //Date at which the message has been sent
            message.setMsgDateSent(Date.from(Instant.now()));

            //Add the message to the userMessages list for both the receiver and sender

            //We then save the message.
            messageDao.saveAndFlush(message);
        } else {
            //What could it possibly return ?
            //else if if the sender doesn't exist and if the recipient exist / etc ? Would be preferrable to send an error if one of the conditions is false
            //to not give anything away just in case.
        }
    }

}
