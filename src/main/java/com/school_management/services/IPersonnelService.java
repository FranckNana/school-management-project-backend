package com.school_management.services;

import com.school_management.models.dto.PersonnelDTO;

import java.util.List;

public interface IPersonnelService {

    public List<PersonnelDTO> getAll();
    public PersonnelDTO getById(Long id);
    public PersonnelDTO create(PersonnelDTO personneldto);
    public PersonnelDTO update(PersonnelDTO updatedPersonnel);
    public void delete(Long id);
}
