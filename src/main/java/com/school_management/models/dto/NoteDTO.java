package com.school_management.models.dto;

public class NoteDTO {

    private Long id;
    private String matiere;
    private double note;
    private double coefficient;
    private int trimestre;

    public NoteDTO() {
    }

    public NoteDTO(Long id, String matiere, double note, double coefficient, int trimestre) {
        this.id = id;
        this.matiere = matiere;
        this.note = note;
        this.coefficient = coefficient;
        this.trimestre = trimestre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public double getNote() {
        return note;
    }

    public void setNote(double note) {
        this.note = note;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public int getTrimestre() {
        return trimestre;
    }

    public void setTrimestre(int trimestre) {
        this.trimestre = trimestre;
    }
}
