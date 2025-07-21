package com.school_management.controllers;

import com.school_management.exceptions.ControllerExceptionHandler;
import com.school_management.models.dto.PaiementDTO;
import com.school_management.models.dto.PersonnelDTO;
import com.school_management.models.dto.StudentDTO;
import com.school_management.services.IPersonnelService;
import com.school_management.services.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class StudentControllers extends ControllerExceptionHandler {

    @Autowired
    private IStudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentdto) {
        StudentDTO student = this.studentService.create(studentdto);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> students = this.studentService.getAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        this.studentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/students")
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentdto) {
        StudentDTO updatedStudent = this.studentService.update(studentdto);
        return new ResponseEntity<>(updatedStudent, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Long id) {
        StudentDTO student = this.studentService.getById(id);
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PatchMapping("/students/paiements")
    public ResponseEntity<StudentDTO> updateStudentPaiment(@RequestBody PaiementDTO paiement) {
        System.out.println("paiement ====> "+paiement);
        StudentDTO studentDTO = this.studentService.updatePaiement(paiement);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping("/students/paiements")
    public ResponseEntity<Void> deleteStudentPaiement(@RequestBody PaiementDTO paiement) {
        this.studentService.deletePaiement(paiement);
        return ResponseEntity.noContent().build();
    }

}
