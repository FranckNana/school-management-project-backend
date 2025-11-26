package com.school_management.services.impl;

import com.school_management.models.dto.MetricsDTO;
import com.school_management.models.entities.PaiementEntity;
import com.school_management.models.entities.PersonnelEntity;
import com.school_management.models.entities.ScheduleEntity;
import com.school_management.models.entities.StudentEntity;
import com.school_management.models.enums.Jour;
import com.school_management.models.enums.Poste;
import com.school_management.repository.PaiementRepository;
import com.school_management.repository.PersonnelRepository;
import com.school_management.repository.ScheduleRepository;
import com.school_management.repository.StudentRepository;
import com.school_management.services.IMetricsService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class MetricsService implements IMetricsService {

    private final StudentRepository studentRepository;
    private final PersonnelRepository personnelRepository;
    private final PaiementRepository paiementRepository;
    private final ScheduleRepository scheduleRepository;

    public MetricsService(StudentRepository studentRepository, PersonnelRepository personnelRepository,
                          PaiementRepository paiementRepository, ScheduleRepository scheduleRepository) {
        this.studentRepository = studentRepository;
        this.personnelRepository = personnelRepository;
        this.paiementRepository = paiementRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public MetricsDTO getAllMetrics() {
        Jour aujourdHui = this.getJourCourant();

        int totalStudents = this.studentRepository.findAll().size();
        int totalTeachers = this.personnelRepository.findByPoste(Poste.Enseignant).size();
        double paymentComplianceRate = (this.calculTotalPaiements() / this.calculTotalScolarite()) * 100;
        int todaysClassesCount = this.scheduleRepository.findByJour(aujourdHui).size();

        return new MetricsDTO(
                totalStudents,
                totalTeachers,
                paymentComplianceRate,
                todaysClassesCount,
                LocalDate.now().getYear(),
                LocalDate.now().getMonth()
            );
    }

    private Jour getJourCourant() {
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();

        // Conversion de java.time.DayOfWeek vers ton enum Jour
        return switch (dayOfWeek) {
            case MONDAY -> Jour.Lundi;
            case TUESDAY -> Jour.Mardi;
            case WEDNESDAY -> Jour.Mercredi;
            case THURSDAY -> Jour.Jeudi;
            case FRIDAY -> Jour.Vendredi;
            case SATURDAY -> Jour.Samedi;
            case SUNDAY -> Jour.Dimanche;
        };
    }

    private double calculTotalScolarite() {
        List<StudentEntity> students = this.studentRepository.findAll();
        double totalScolarite = 0;
        if(!students.isEmpty()) {
            for (StudentEntity student : students) {
                totalScolarite += student.getPrixScholarite();
            }
        }
        return totalScolarite;
    }

    private double calculTotalPaiements() {
        List<PaiementEntity> allPaiements = this.paiementRepository.findAll();
        double totalPaiements = 0;
        if(!allPaiements.isEmpty()) {
            for (PaiementEntity paiement : allPaiements) {
                totalPaiements += paiement.getMontant();
            }
        }
        return totalPaiements;
    }
}
