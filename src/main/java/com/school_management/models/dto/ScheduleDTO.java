package com.school_management.models.dto;

import com.school_management.models.enums.Jour;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalTime;

public class ScheduleDTO {

    private Long id;
    private String classe;
    private Jour jour;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String matiere;
    private String enseignant;
    private String salle;

    public ScheduleDTO(Long id, String classe, Jour jour, LocalTime heureDebut, LocalTime heureFin, String matiere,
                       String enseignant, String salle) {
        this.id = id;
        this.classe = classe;
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.matiere = matiere;
        this.enseignant = enseignant;
        this.salle = salle;
    }

    public ScheduleDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Jour getJour() {
        return jour;
    }

    public void setJour(Jour jour) {
        this.jour = jour;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getEnseignant() {
        return enseignant;
    }

    public void setEnseignant(String enseignant) {
        this.enseignant = enseignant;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }
}
