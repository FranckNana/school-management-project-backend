package com.school_management.services;

import com.school_management.models.dto.PaiementDTO;
import com.school_management.models.dto.SalaryDTO;

import java.util.List;

public interface IPaiementService {
    public List<PaiementDTO> getAll();
    public PaiementDTO create(PaiementDTO paiementDTO);
    public SalaryDTO createSalary(SalaryDTO salaryDTO);
    public List<SalaryDTO> getAllSalaries();
    public SalaryDTO getSalaryByID(Long id );
    public void delete(Long id);
    public SalaryDTO updateSalary(SalaryDTO salaryDTO);
    public double getSolde();
    public double getRecettes();
    public double getAllRecettes();
    public double getDepenses();
    public double getAllDepense();
    public double getSoldeBefore();
}
