package com.school_management.models.entities;

import com.school_management.models.abstracts.AbstractUser;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "student")
public class StudentEntity extends AbstractUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroMatricule;
    private String classe;
    private String nomParent;
    private String telephoneParent;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteEntity> notes = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PresenceEntity> presences = new ArrayList<>();;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaiementEntity> paiements = new ArrayList<>();

    private double prixScholarite;
    private double resteApayer;
    private String anneeScolaire;


    public StudentEntity(String nom, String prenom, LocalDate dateNaissance, String telephone, String email, String adresse,
                         Long id, String numeroMatricule, String classe, String nomParent, String telephoneParent,
                         List<NoteEntity> notes, List<PresenceEntity> presences, List<PaiementEntity> paiements,
                         double prixScholarite, double resteApayer, String anneeScolaire) {
        super(nom, prenom, dateNaissance, telephone, email, adresse);
        this.id = id;
        this.numeroMatricule = numeroMatricule;
        this.classe = classe;
        this.nomParent = nomParent;
        this.telephoneParent = telephoneParent;
        this.notes = notes;
        this.presences = presences;
        this.paiements = paiements;
        this.prixScholarite = prixScholarite;
        this.resteApayer = resteApayer;
        this.anneeScolaire = anneeScolaire;
    }

    public StudentEntity(String nom, String prenom, LocalDate dateNaissance, String telephone, String email, String adresse) {
        super(nom, prenom, dateNaissance, telephone, email, adresse);
    }

    public StudentEntity() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroMatricule() {
        return numeroMatricule;
    }

    public void setNumeroMatricule(String numeroMatricule) {
        this.numeroMatricule = numeroMatricule;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getNomParent() {
        return nomParent;
    }

    public void setNomParent(String nomParent) {
        this.nomParent = nomParent;
    }

    public String getTelephoneParent() {
        return telephoneParent;
    }

    public void setTelephoneParent(String telephoneParent) {
        this.telephoneParent = telephoneParent;
    }

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
    }

    public List<PresenceEntity> getPresences() {
        return presences;
    }

    public void setPresences(List<PresenceEntity> presences) {
        this.presences = presences;
    }

    public List<PaiementEntity> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<PaiementEntity> paiements) {
        this.paiements = paiements;
    }

    public double getPrixScholarite() {
        return prixScholarite;
    }

    public void setPrixScholarite(double prixScholarite) {
        this.prixScholarite = prixScholarite;
    }

    public double getResteApayer() {
        return resteApayer;
    }

    public void setResteApayer(double resteApayer) {
        this.resteApayer = resteApayer;
    }

    public String getAnneeScolaire() {
        return anneeScolaire;
    }

    public void setAnneeScolaire(String anneeScolaire) {
        this.anneeScolaire = anneeScolaire;
    }
}
