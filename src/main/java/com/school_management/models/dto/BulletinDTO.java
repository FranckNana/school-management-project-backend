package com.school_management.models.dto;

import java.time.LocalDate;
import java.util.List;

public class BulletinDTO {
    private Long id;
    private Long eleveId;
    private String nomEleve;
    private String classe;
    private Integer trimestre;
    private String annee;
    private List<NoteDTO> notes;
    private Double moyenneGenerale;
    private Integer rang;
    private String appreciation;
    private LocalDate dateGeneration;

    public BulletinDTO(Long id, Long eleveId, String nomEleve, String classe, Integer trimestre, String annee,
                       List<NoteDTO> notes, Double moyenneGenerale, Integer rang, String appreciation, LocalDate dateGeneration) {
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

    public BulletinDTO() {
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

    public List<NoteDTO> getNotes() {
        return notes;
    }

    public void setNotes(List<NoteDTO> notes) {
        this.notes = notes;
    }

    public Double getMoyenneGenerale() {
        return moyenneGenerale;
    }

    public void setMoyenneGenerale(Double moyenneGenerale) {
        this.moyenneGenerale = moyenneGenerale;
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
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

    @Override
    public String toString() {
        return "BulletinDTO{" +
                "id=" + id +
                ", eleveId=" + eleveId +
                ", nomEleve='" + nomEleve + '\'' +
                ", classe='" + classe + '\'' +
                ", trimestre=" + trimestre +
                ", annee='" + annee + '\'' +
                ", notes=" + notes +
                ", moyenneGenerale=" + moyenneGenerale +
                ", rang=" + rang +
                ", appreciation='" + appreciation + '\'' +
                ", dateGeneration=" + dateGeneration +
                '}';
    }
}
