package fr.alexandreklotz.enigma.controller;

import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.dao.MessageDao;
import fr.alexandreklotz.enigma.model.Message;
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

//@Controller Will be used when thymeleafs will be implemented
public class MessageController {

    private MessageDao messageDao;

    @Autowired
    MessageController (MessageDao messageDao){
        this.messageDao = messageDao;
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
    /* TODO : This method needs to be tested. It might not retrieve the sender's ID properly or not at all. It may not work for the recipient neither */
    @JsonView(CustomJsonView.MessageView.class)
    @PostMapping("/message/new")
    public void newMessage (@RequestBody Message message){

        //We generate a random UUID that will be given to the message
        UUID messageUuid = UUID.randomUUID();
        message.setId(messageUuid);

        //We then create a user object
        Utilisateur utilisateur = new Utilisateur();
        //We then retrieve its ID to assign it to the senderId variable
        message.setSenderId(utilisateur.getId());
        //We set the other message's variables
        message.setMsgText(message.getMsgText()); //Message encryption will be done later
        message.setUserRecipient(message.getUserRecipient());
        //The "hasBeenRead" parameter will be implemented later.

        //We then save the message.
        messageDao.saveAndFlush(message);
    }

}
