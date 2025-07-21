package com.school_management.models.dto;

public class BalanceDTO {

    private double total;
    private double recettes;
    private double depenses;

    public BalanceDTO(double total, double recettes, double depenses) {
        this.total = total;
        this.recettes = recettes;
        this.depenses = depenses;
    }

    public BalanceDTO() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getRecettes() {
        return recettes;
    }

    public void setRecettes(double recettes) {
        this.recettes = recettes;
    }

    public double getDepenses() {
        return depenses;
    }

    public void setDepenses(double depenses) {
        this.depenses = depenses;
    }
}
