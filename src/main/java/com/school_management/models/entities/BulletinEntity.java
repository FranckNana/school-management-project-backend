package com.school_management.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bulletin")
public class BulletinEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long eleveId;
    private String nomEleve;

    @Column(nullable = false)
    private String classe;

    @Column(nullable = false)
    private Integer trimestre;

    @Column(nullable = false)
    private String annee;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "bulletin_id")
    private List<NoteEntity> notes = new ArrayList<>();

    @Column(name = "moyenne_generale")
    private Double moyenneGenerale;

    private int rang;

    private String appreciation;

    @Column(name = "date_generation")
    private LocalDate dateGeneration;

    public BulletinEntity(Long id, Long eleveId, String nomEleve, String classe, Integer trimestre, String annee,
                          List<NoteEntity> notes, Double moyenneGenerale, int rang, String appreciation,
                          LocalDate dateGeneration) {
        this.id = id;
        this.eleveId = eleveId;
        this.nomEleve = nomEleve;
        this.classe = classe;
        this.trimestre = trimestre;
        this.annee = annee;
        this.notes = notes;
        this.moyenneGenerale = moyenneGenerale;
        this.rang = rang;
        this.appreciation = appreciation;
        this.dateGeneration = dateGeneration;
    }

    public BulletinEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEleveId() {
        return eleveId;
    }

    public void setEleveId(Long eleveId) {
        this.eleveId = eleveId;
    }

    public String getNomEleve() {
        return nomEleve;
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(Integer trimestre) {
        this.trimestre = trimestre;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteEntity> notes) {
        this.notes = notes;
    }

    public Double getMoyenneGenerale() {
        return moyenneGenerale;
    }

    public void setMoyenneGenerale(Double moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public LocalDate getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(LocalDate dateGeneration) {
        this.dateGeneration = dateGeneration;
    }
}
