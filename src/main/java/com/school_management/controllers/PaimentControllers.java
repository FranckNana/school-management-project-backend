package com.school_management.controllers;

import com.school_management.exceptions.ControllerExceptionHandler;
import com.school_management.models.dto.PaiementDTO;
import com.school_management.models.dto.SalaryDTO;
import com.school_management.services.IPaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PaimentControllers extends ControllerExceptionHandler {

    @Autowired
    private IPaiementService paiementService;

    @PostMapping("/paiements")
    public ResponseEntity<PaiementDTO> createPaiement(@RequestBody PaiementDTO paiementDTO) {
        PaiementDTO paiement = this.paiementService.create(paiementDTO);
        return new ResponseEntity<>(paiement, HttpStatus.OK);
    }

    @GetMapping("/paiements")
    public ResponseEntity<List<PaiementDTO>> getAll() {
        List<PaiementDTO> paiement = this.paiementService.getAll();
        return new ResponseEntity<>(paiement, HttpStatus.OK);
    }

    @PostMapping("/paiements/salary")
    public ResponseEntity<SalaryDTO> createSalary(@RequestBody SalaryDTO salaryDTO) {
        SalaryDTO salary = this.paiementService.createSalary(salaryDTO);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    @PatchMapping("/paiements/salary")
    public ResponseEntity<SalaryDTO> updateSalary(@RequestBody SalaryDTO salaryDTO) {
        SalaryDTO salary = this.paiementService.updateSalary(salaryDTO);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    @GetMapping("/paiements/salary")
    public ResponseEntity<List<SalaryDTO>> getAllSalaries() {
        List<SalaryDTO> salaries = this.paiementService.getAllSalaries();
        return new ResponseEntity<>(salaries, HttpStatus.OK);
    }

    @GetMapping("/paiements/salary/{id}")
    public ResponseEntity<SalaryDTO> getSalaryById(@PathVariable Long id) {
        SalaryDTO salary = this.paiementService.getSalaryByID(id);
        return new ResponseEntity<>(salary, HttpStatus.OK);
    }

    @DeleteMapping("/paiements/salary/{id}")
    public ResponseEntity<List<SalaryDTO>> deleteSalary(@PathVariable Long id) {
        this.paiementService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paiements/solde")
    public ResponseEntity<Double> getSolde() {
        double solde = this.paiementService.getSolde();
        return new ResponseEntity<>(solde, HttpStatus.OK);
    }

    @GetMapping("/paiements/soldebefore")
    public ResponseEntity<Double> getSoldeBefore() {
        double sold = this.paiementService.getSoldeBefore();
        return new ResponseEntity<>(sold, HttpStatus.OK);
    }

    @GetMapping("/paiements/depenses")
    public ResponseEntity<Double> getDepenses() {
        double depenses = this.paiementService.getDepenses();
        return new ResponseEntity<>(depenses, HttpStatus.OK);
    }

    @GetMapping("/paiements/alldepenses")
    public ResponseEntity<Double> getAllDepenses() {
        double depenses = this.paiementService.getAllDepense();
        return new ResponseEntity<>(depenses, HttpStatus.OK);
    }

    @GetMapping("/paiements/recette")
    public ResponseEntity<Double> getRecette() {
        double recette = this.paiementService.getRecettes();
        return new ResponseEntity<>(recette, HttpStatus.OK);
    }

    @GetMapping("/paiements/allrecette")
    public ResponseEntity<Double> getAllRecette() {
        double allRecette = this.paiementService.getAllRecettes();
        return new ResponseEntity<>(allRecette, HttpStatus.OK);
    }

}
