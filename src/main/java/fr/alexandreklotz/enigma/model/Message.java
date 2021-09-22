package fr.alexandreklotz.enigma.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Message {

    //Entity variables
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView(CustomJsonView.MessageView.class)
    private UUID id;

    @Column
    @JsonView(CustomJsonView.MessageView.class)
    private UUID senderId;

    @Column
    @JsonView(CustomJsonView.MessageView.class)
    private String msgText;

    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "Europe/Paris")
    @JsonView(CustomJsonView.MessageView.class)
    private Date msgDateSent;

    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "Europe/Paris")
    @JsonView(CustomJsonView.MessageView.class)
    private Date msgDateRead;

    @Column
    @JsonView(CustomJsonView.MessageView.class)
    private boolean hasBeenRead;

    @Column
    @JsonView(CustomJsonView.MessageView.class)
    private UUID recipientId;


    //Constructor
    public Message(){};


    /////////////
    //Relations//
    /////////////

    //A user can send one or multiple messages to multiple users.
    @ManyToMany(mappedBy = "userMessages")
    private Set<Utilisateur> messagesUser = new HashSet<>();

    //A user can send the same message to multiple groups. Groups can have multiple messages.
    @ManyToMany(mappedBy = "groupesMessages")
    private Set<Groupe> messagesGroup = new HashSet<>();


    /////////////////////
    //Getters & Setters//
    /////////////////////

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public Date getMsgDateSent() {
        return msgDateSent;
    }

    public void setMsgDateSent(Date msgDateEnvoi) {
        this.msgDateSent = msgDateEnvoi;
    }

    public Date getMsgDateRead() {
        return msgDateRead;
    }

    public void setMsgDateRead(Date msgDateLu) {
        this.msgDateRead = msgDateLu;
    }

    public boolean isHasBeenRead() {
        return hasBeenRead;
    }

    public void setHasBeenRead(boolean hasBeenRead) {
        this.hasBeenRead = hasBeenRead;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(UUID userRecipient) {
        this.recipientId = userRecipient;
    }

    public Set<Utilisateur> getMessagesUser() {
        return messagesUser;
    }

    public void setMessagesUser(Set<Utilisateur> messagesUser) {
        this.messagesUser = messagesUser;
    }

    public Set<Groupe> getMessagesGroup() {
        return messagesGroup;
    }

    public void setMessagesGroup(Set<Groupe> messagesGroup) {
        this.messagesGroup = messagesGroup;
    }
}
