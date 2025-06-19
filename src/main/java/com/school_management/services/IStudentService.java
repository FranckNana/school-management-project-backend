package com.school_management.services;

import com.school_management.models.dto.PersonnelDTO;
import com.school_management.models.dto.StudentDTO;

import java.util.List;

public interface IStudentService {

    public StudentDTO create(StudentDTO studentDTO);
    public StudentDTO update(StudentDTO studentDTO);
    public StudentDTO getById(Long id);
    public List<StudentDTO> getAll();
    public void delete(Long id);
}
