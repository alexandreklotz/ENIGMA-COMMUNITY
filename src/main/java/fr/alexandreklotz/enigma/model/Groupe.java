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
public class Groupe {

    //Entity variables
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @JsonView(CustomJsonView.GroupeView.class)
    private UUID id;

    @Column
    @JsonView(CustomJsonView.GroupeView.class)
    private String nameGroupe;

    @Column
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy HH:mm:ss", timezone = "Europe/Paris")
    @JsonView(CustomJsonView.GroupeView.class)
    private Date dateCreationGroupe;

    @Column
    @JsonView(CustomJsonView.GroupeView.class)
    private UUID groupeOwner;


    //Constructor
    public Groupe(){}

    /////////////
    //Relations//
    /////////////

    //Relation between users and groups. Groups can have multiple users and users can be in multiple groups.
    @ManyToMany(mappedBy = "userGroupes")
    private Set<Utilisateur> groupesUsers;

    //Relation between groups and messages. A message can be sent to multiple groups and a group also has multiple messages.
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "groups_messages",
            joinColumns = {@JoinColumn(name = "groupe_id")},
            inverseJoinColumns = {@JoinColumn(name = "message_id")}
    )
    private Set<Message> groupesMessages = new HashSet<>();

    /////////////////////
    //Setters & Getters//
    /////////////////////

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNameGroupe() {
        return nameGroupe;
    }

    public void setNameGroupe(String nomGroupe) {
        this.nameGroupe = nomGroupe;
    }

    public Date getDateCreationGroupe() {
        return dateCreationGroupe;
    }

    public void setDateCreationGroupe(Date dateCreationGroupe) {
        this.dateCreationGroupe = dateCreationGroupe;
    }

    public UUID getGroupeOwner() {
        return groupeOwner;
    }

    public void setGroupeOwner(UUID groupeOwner) {
        this.groupeOwner = groupeOwner;
    }
}
