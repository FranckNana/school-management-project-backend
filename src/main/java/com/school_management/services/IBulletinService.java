package com.school_management.services;

import com.school_management.models.dto.BulletinDTO;
import com.school_management.models.dto.StudentDTO;

import java.util.List;

public interface IBulletinService {

    public List<BulletinDTO> getAll();
    public BulletinDTO getById(Long id);
    public List<BulletinDTO> getByEleve(Long idEleve);
    public BulletinDTO create(BulletinDTO bulletinDTO);
    public void delete(Long idBulletin);
    public BulletinDTO update(BulletinDTO bulletinDTO);
    public byte[] generatePDF(Long bulletinId);

}
