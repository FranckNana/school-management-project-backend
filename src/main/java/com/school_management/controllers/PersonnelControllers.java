package com.school_management.controllers;

import com.school_management.exceptions.ControllerExceptionHandler;
import com.school_management.models.dto.PersonnelDTO;
import com.school_management.services.IPersonnelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PersonnelControllers extends ControllerExceptionHandler {

    @Autowired
    private IPersonnelService personnelService;

    @PostMapping("/personnels")
    public ResponseEntity<PersonnelDTO> createPersonnel(@RequestBody PersonnelDTO personneldto) {
        PersonnelDTO personnel = this.personnelService.create(personneldto);
        return new ResponseEntity<>(personnel, HttpStatus.OK);
    }

    @PatchMapping("/personnels")
    public ResponseEntity<PersonnelDTO> updatePersonnel(@RequestBody PersonnelDTO personneldto) {
        PersonnelDTO personnel = this.personnelService.update(personneldto);
        return new ResponseEntity<>(personnel, HttpStatus.OK);
    }

    @GetMapping("/personnels/{id}")
    public ResponseEntity<PersonnelDTO> getById(@PathVariable Long id) {
        PersonnelDTO personnel = this.personnelService.getById(id);
        return new ResponseEntity<>(personnel, HttpStatus.OK);
    }

    @GetMapping("/personnels")
    public ResponseEntity<List<PersonnelDTO>> getAll() {
        List<PersonnelDTO> personnels = this.personnelService.getAll();
        return new ResponseEntity<>(personnels, HttpStatus.OK);
    }

    @DeleteMapping("/personnels/{id}")
    public ResponseEntity<Void> deletePersonnel(@PathVariable Long id) {
        this.personnelService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
