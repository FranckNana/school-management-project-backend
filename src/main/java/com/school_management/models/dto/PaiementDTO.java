package com.school_management.models.dto;

import java.time.LocalDate;

public class PaiementDTO {

    private Long id;
    private LocalDate date;
    private double montant;
    private String motif;

    public PaiementDTO() {
    }

    public PaiementDTO(Long id, LocalDate date, double montant, String motif) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.motif = motif;
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
}
