package com.school_management.models.dto;

import java.time.LocalDate;

public class PresenceDTO {
    private Long id;
    private LocalDate date;
    private boolean present;
    private String motif;

    public PresenceDTO() {
    }

    public PresenceDTO(Long id, LocalDate date, boolean present, String motif) {
        this.id = id;
        this.date = date;
        this.present = present;
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

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }
}
