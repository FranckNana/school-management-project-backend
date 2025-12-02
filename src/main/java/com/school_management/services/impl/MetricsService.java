package com.school_management.services.impl;

import com.school_management.models.dto.MetricsDTO;
import com.school_management.models.entities.PaiementEntity;
import com.school_management.models.entities.PersonnelEntity;
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
import java.util.Map;
import java.util.stream.Collectors;

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
        List<StudentEntity> students = this.studentRepository.findAll();
        List<PersonnelEntity> enseignants = this.personnelRepository.findByPoste(Poste.Enseignant);

        int totalStudents = students.size();
        int totalTeachers = enseignants.size();
        double paymentComplianceRate = this.calculTotalScolarite() !=0 ? (this.calculTotalPaiements() / this.calculTotalScolarite()) * 100
                                                                       : 0.00;
        int todaysClassesCount = this.scheduleRepository.findByJour(aujourdHui).size();

        long comparedStudent = this.compareNbStudentsWithLastYear(students);

        long comparedTeachers = this.compareNbTeachersWithLastYear(enseignants);

        return new MetricsDTO(
                totalStudents,
                totalTeachers,
                paymentComplianceRate,
                todaysClassesCount,
                LocalDate.now().getYear(),
                LocalDate.now().getMonth(),
                comparedStudent,
                comparedTeachers
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
                if(this.isAnneeScolaire(student.getAnneeScolaire())) {
                    totalScolarite += student.getPrixScholarite();
                }
            }
        }
        return totalScolarite;
    }

    private double calculTotalPaiements() {
        List<PaiementEntity> allPaiements = this.paiementRepository.findAll();
        double totalPaiements = 0;
        if(!allPaiements.isEmpty()) {
            for (PaiementEntity paiement : allPaiements) {
                if(this.isAnneeScolaire(paiement.getEleve().getAnneeScolaire())) {
                    totalPaiements += paiement.getMontant();
                }
            }
        }
        return totalPaiements;
    }

    private boolean isAnneeScolaire(String anneeScolaire) {
        LocalDate today = LocalDate.now();

        // Sépare les deux années
        String[] parts = anneeScolaire.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Format invalide. Utiliser 'YYYY - YYYY'.");
        }

        int startYear = Integer.parseInt(parts[0].trim());
        int endYear = Integer.parseInt(parts[1].trim());

        // Définition des bornes exactes
        LocalDate startDate = LocalDate.of(startYear, 9, 1);  // 1er septembre startYear
        LocalDate endDate = LocalDate.of(endYear, 8, 31);     // 31 août endYear

        // Vérifie l'appartenance
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }

    private long compareNbStudentsWithLastYear(List<StudentEntity> students){
        Map<String, Long> studentSTATS = students.stream().collect(Collectors.
                groupingBy(StudentEntity::getAnneeScolaire, Collectors.counting()));

        String currentAcademicYear = getCurrentAcademicYear(LocalDate.now());
        String precedentAnneeScolaire = getPrecedentAnneeScolaire(currentAcademicYear);

        return studentSTATS.getOrDefault(currentAcademicYear,0L) - studentSTATS.getOrDefault(precedentAnneeScolaire, 0L);
    }

    private long compareNbTeachersWithLastYear(List<PersonnelEntity> enseignants){

        List<PersonnelEntity> collect = enseignants.stream().filter(e -> getCurrentAcademicYear(e.getDateEmbauche()).equals(getCurrentAcademicYear(LocalDate.now())))
                .toList();

        return collect.size();

    }

    //obtenir l'année academique à partir de la date courante
    private static String getCurrentAcademicYear(LocalDate today) {
        int year = today.getYear();

        if (today.getMonthValue() < 9) {
            return (year - 1) + " - " + year;
        }

        return year + " - " + (year + 1);
    }

    private String getPrecedentAnneeScolaire(String currentAnneeScolaire) {
        if (currentAnneeScolaire == null || !currentAnneeScolaire.matches("\\d{4} - \\d{4}")) {
            throw new IllegalArgumentException("Format invalide, attendu : YYYY-YYYY");
        }

        String[] parts = currentAnneeScolaire.split("-");
        int start = Integer.parseInt(parts[0].trim());
        int end = Integer.parseInt(parts[1].trim());

        int prevStart = start - 1;
        int prevEnd = end - 1;

        return prevStart + " - " + prevEnd;
    }

}
