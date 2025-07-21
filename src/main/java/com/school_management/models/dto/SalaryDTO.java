package com.school_management.models.dto;

import java.time.LocalDate;

public class SalaryDTO {
    private Long id;
    private LocalDate date;
    private String employe;
    private Double montant;
    private String mois;
    private String poste;

    public SalaryDTO(Long id, LocalDate date, String employe, Double montant, String mois, String poste) {
        this.id = id;
        this.date = date;
        this.employe = employe;
        this.montant = montant;
        this.mois = mois;
        this.poste = poste;
    }

    public SalaryDTO() {
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

    public String getEmploye() {
        return employe;
    }

    public void setEmploye(String employe) {
        this.employe = employe;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getMois() {
        return mois;
    }

    public void setMois(String mois) {
        this.mois = mois;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }
}
