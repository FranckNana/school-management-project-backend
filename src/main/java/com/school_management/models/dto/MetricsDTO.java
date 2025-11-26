package com.school_management.models.dto;

import java.time.LocalDateTime;
import java.time.Month;

public class MetricsDTO {
    private int totalStudents;
    private int totalTeachers;
    private double paymentComplianceRate;
    private int todaysClassesCount;
    private int year;
    private Month month;

    public MetricsDTO() {}

    public MetricsDTO(int totalStudents, int totalTeachers, double paymentComplianceRate,
                      int todaysClassesCount, int year, Month month) {
        this.totalStudents = totalStudents;
        this.totalTeachers = totalTeachers;
        this.paymentComplianceRate = paymentComplianceRate;
        this.todaysClassesCount = todaysClassesCount;
        this.year = year;
        this.month = month;
    }


    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }

    public int getTotalTeachers() {
        return totalTeachers;
    }

    public void setTotalTeachers(int totalTeachers) {
        this.totalTeachers = totalTeachers;
    }

    public double getPaymentComplianceRate() {
        return paymentComplianceRate;
    }

    public void setPaymentComplianceRate(double paymentComplianceRate) {
        this.paymentComplianceRate = paymentComplianceRate;
    }

    public int getTodaysClassesCount() {
        return todaysClassesCount;
    }

    public void setTodaysClassesCount(int todaysClassesCount) {
        this.todaysClassesCount = todaysClassesCount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }
}
