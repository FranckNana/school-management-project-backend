package com.school_management.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "paiement")
public class PaiementEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private double montant;
    private String motif;

    @ManyToOne
    @JoinColumn(name = "id_eleve", nullable = false)
    private StudentEntity eleve;

    public PaiementEntity(Long id, LocalDate date, double montant, String motif, StudentEntity eleve) {
        this.id = id;
        this.date = date;
        this.montant = montant;
        this.motif = motif;
        this.eleve = eleve;
    }

    public PaiementEntity() {
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

    public StudentEntity getEleve() {
        return eleve;
    }

    public void setEleve(StudentEntity eleve) {
        this.eleve = eleve;
    }
}
