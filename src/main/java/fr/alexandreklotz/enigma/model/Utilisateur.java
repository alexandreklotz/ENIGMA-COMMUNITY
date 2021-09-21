package fr.alexandreklotz.enigma.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import fr.alexandreklotz.enigma.view.CustomJsonView;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Utilisateur {

    //Entity variables
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.MessageView.class, CustomJsonView.GroupeView.class})
    private UUID id;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String userLogin;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String userPassword;

    @Column
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String emailuser;

    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "Europe/Paris")
    @JsonView(CustomJsonView.UtilisateurView.class)
    private Date userDateRegistration;

    @Column
    @JsonView(CustomJsonView.UtilisateurView.class)
    private boolean userIsVisible;

    @Column(nullable = false)
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String userNickname;

    @Column
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String userPublicId;

    @Column
    @JsonView(CustomJsonView.UtilisateurView.class)
    private String userChatPwd;


    //Constructor
    public Utilisateur(){};

    /////////////
    //Relations//
    /////////////

    //An user can be in multiple groups and also create multiple groups. We therefore need to implement a manytomany relation.
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.GroupeView.class})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_groupes",
            joinColumns = {@JoinColumn(name = "utilisateur_id")},
            inverseJoinColumns = {@JoinColumn (name = "groupe_id")}
    )
    private Set<Groupe> userGroupes = new HashSet<>();

    //An user can send multiple messages and receive multiple messages aswell. An user can send the same message to multiple users without
    //having to create a group for that.
    @JsonView({CustomJsonView.UtilisateurView.class, CustomJsonView.MessageView.class})
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_messages",
            joinColumns = {@JoinColumn(name = "utilisateur_id")},
            inverseJoinColumns = {@JoinColumn(name = "message_id")}
    )
    private Set<Message> userMessages = new HashSet<>();


    /////////////////////
    //Getters & Setters//
    /////////////////////
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmailuser() {
        return emailuser;
    }

    public void setEmailuser(String emailuser) {
        this.emailuser = emailuser;
    }

    public Date getUserDateRegistration() {
        return userDateRegistration;
    }

    public void setUserDateRegistration(Date userDateRegistration) {
        this.userDateRegistration = userDateRegistration;
    }

    public boolean isUserIsVisible() {
        return userIsVisible;
    }

    public void setUserIsVisible(boolean userIsVisible) {
        this.userIsVisible = userIsVisible;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserPublicId() {
        return userPublicId;
    }

    public void setUserPublicId(String userPublicId) {
        this.userPublicId = userPublicId;
    }

    public String getUserChatPwd() {
        return userChatPwd;
    }

    public void setUserChatPwd(String userChatPwd) {
        this.userChatPwd = userChatPwd;
    }
}
