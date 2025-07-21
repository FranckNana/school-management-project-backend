package com.school_management.models.dto;

import com.school_management.models.entities.StudentEntity;

import java.time.LocalDate;

public class PaiementDTO {

    private Long id;
    private LocalDate date;
    private double montant;
    private String motif;
    private Long eleve;

    public PaiementDTO() {
    }

    public PaiementDTO(Long id, LocalDate date, double montant, String motif, Long eleve) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.motif = motif;
        this.eleve = eleve;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Long getEleve() {
        return eleve;
    }

    public void setEleve(Long idStudent) {
        this.eleve = idStudent;
    }

    @Override
    public String toString() {
        return "PaiementDTO{" +
                "id=" + id +
                ", date=" + date +
                ", montant=" + montant +
                ", motif='" + motif + '\'' +
                ", idStudent=" + eleve +
                '}';
    }
}
