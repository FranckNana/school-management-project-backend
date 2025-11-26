package com.school_management.models.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDTO {

    private Long id;
    private String numeroMatricule;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private String telephone;
    private String email;
    private String adresse;
    private String classe;
    private String nomParent;
    private String telephoneParent;

    private List<NoteDTO> notes = new ArrayList<>();
    private List<PresenceDTO> presences = new ArrayList<>();
    private List<PaiementDTO> paiements = new ArrayList<>();

    private double prixScholarite;
    private double resteApayer;

    public StudentDTO(Long id, String numeroMatricule, String nom, String prenom, LocalDate dateNaissance,
                      String telephone, String email, String adresse, String classe, String nomParent,
                      String telephoneParent, List<NoteDTO> notes, List<PresenceDTO> presences, List<PaiementDTO> paiements,
                      double prixScholarite, double resteApayer) {
        this.id = id;
        this.numeroMatricule = numeroMatricule;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
        this.classe = classe;
        this.nomParent = nomParent;
        this.telephoneParent = telephoneParent;
        this.notes = notes;
        this.presences = presences;
        this.paiements = paiements;
        this.prixScholarite = prixScholarite;
        this.resteApayer = resteApayer;
    }

    public StudentDTO() {
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }

    public List<PresenceDTO> getPresences() {
        return presences;
    }

    public void setPresences(List<PresenceDTO> presences) {
        this.presences = presences;
    }

    public List<PaiementDTO> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<PaiementDTO> paiements) {
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

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", numeroMatricule='" + numeroMatricule + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", classe='" + classe + '\'' +
                ", nomParent='" + nomParent + '\'' +
                ", telephoneParent='" + telephoneParent + '\'' +
                ", notes=" + notes +
                ", presences=" + presences +
                ", paiements=" + paiements +
                '}';
    }
}
