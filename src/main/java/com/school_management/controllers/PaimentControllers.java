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
}
