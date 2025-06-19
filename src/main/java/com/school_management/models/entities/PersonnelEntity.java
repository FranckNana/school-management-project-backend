package com.school_management.models.entities;

import com.school_management.models.abstracts.AbstractUser;
import com.school_management.models.enums.Matiere;
import com.school_management.models.enums.Poste;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "personnel")
public class PersonnelEntity extends AbstractUser implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Poste poste;

    private LocalDate dateEmbauche;
    private Double salaire;

    @ElementCollection(targetClass = Matiere.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "personnel_matieres", joinColumns = @JoinColumn(name = "personnel_id"))
    @Column(name = "matiere")
    private List<Matiere> matieres;

    public PersonnelEntity(String nom, String prenom, LocalDate dateNaissance, String telephone, String email,
                           String adresse, Long id, Poste poste, LocalDate dateEmbauche, Double salaire, List<Matiere> matieres) {
        super(nom, prenom, dateNaissance, telephone, email, adresse);
        this.id = id;
        this.poste = poste;
        this.dateEmbauche = dateEmbauche;
        this.salaire = salaire;
        this.matieres = matieres;
    }

    public PersonnelEntity(String nom, String prenom, LocalDate dateNaissance, String telephone, String email, String adresse) {
        super(nom, prenom, dateNaissance, telephone, email, adresse);
    }

    public PersonnelEntity() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Poste getPoste() {
        return poste;
    }

    public void setPoste(Poste poste) {
        this.poste = poste;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }
}
