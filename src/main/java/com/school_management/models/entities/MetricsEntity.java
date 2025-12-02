package com.school_management.models.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.Month;

@Entity
@Table(name = "metrics")
public class MetricsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_students", nullable = false)
    private int totalStudents;

    @Column(name = "total_teachers", nullable = false)
    private int totalTeachers;

    @Column(name = "payment_compliance_rate", nullable = false)
    private double paymentComplianceRate;

    @Column(name = "todays_classes_count", nullable = false)
    private int todaysClassesCount;

    private int year;
    private Month month;
    private long comparedStudent;
    private long comparedTeacher;

    public MetricsEntity() {}

    public MetricsEntity(int totalStudents, int totalTeachers, double paymentComplianceRate,
                         int todaysClassesCount, int year, Month month, long comparedStudent, long comparedTeacher) {
        this.totalStudents = totalStudents;
        this.totalTeachers = totalTeachers;
        this.paymentComplianceRate = paymentComplianceRate;
        this.todaysClassesCount = todaysClassesCount;
        this.year = year;
        this.month = month;
        this.comparedStudent = comparedStudent;
        this.comparedTeacher = comparedTeacher;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public long getComparedStudent() {
        return comparedStudent;
    }

    public void setComparedStudent(long comparedStudent) {
        this.comparedStudent = comparedStudent;
    }

    public long getComparedTeacher() {
        return comparedTeacher;
    }

    public void setComparedTeacher(long comparedTeacher) {
        this.comparedTeacher = comparedTeacher;
    }
}
