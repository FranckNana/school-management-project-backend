package com.school_management.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "note")
public class NoteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matiere;
    private double note;
    private double coefficient;
    private int trimestre;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;

    public NoteEntity(Long id, String matiere, double note, double coefficient, int trimestre, StudentEntity student) {
        this.id = id;
        this.matiere = matiere;
        this.note = note;
        this.coefficient = coefficient;
        this.trimestre = trimestre;
        this.student = student;
    }

    public NoteEntity() {
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

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }
}
