package com.school_management.controllers;

import com.school_management.exceptions.ControllerExceptionHandler;
import com.school_management.models.dto.BulletinDTO;
import com.school_management.services.IBulletinService;
import com.school_management.services.ITokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BulletinControllers extends ControllerExceptionHandler {

    @Autowired
    private IBulletinService bulletinService;

    @Autowired
    private ITokenService tokenService;

    @GetMapping("/bulletins")
    public ResponseEntity<List<BulletinDTO>> getAll() {
        List<BulletinDTO> bulletin = this.bulletinService.getAll();
        return new ResponseEntity<>(bulletin, HttpStatus.OK);
    }

    @PostMapping("/bulletins")
    public ResponseEntity<BulletinDTO> createBulletin(@RequestBody BulletinDTO bulletinDTO) {
        BulletinDTO bulletin = this.bulletinService.create(bulletinDTO);
        return new ResponseEntity<>(bulletin, HttpStatus.OK);
    }

    @GetMapping("/bulletins/{id}")
    public ResponseEntity<BulletinDTO> getById(@PathVariable Long id) {
        BulletinDTO bulletin = this.bulletinService.getById(id);
        return new ResponseEntity<>(bulletin, HttpStatus.OK);
    }

    @GetMapping("/bulletins/eleve/{id}")
    public ResponseEntity<List<BulletinDTO>> getByEleve(@PathVariable Long idEleve) {
        List<BulletinDTO> bulletins = this.bulletinService.getByEleve(idEleve);
        return new ResponseEntity<>(bulletins, HttpStatus.OK);
    }

    @DeleteMapping("/bulletins/{id}")
    public ResponseEntity<Void> deleteBulletin(@PathVariable Long id) {
        this.bulletinService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/bulletins")
    public ResponseEntity<BulletinDTO> updateBulletin(@RequestBody BulletinDTO bulletinDTO) {
        BulletinDTO bulletin = this.bulletinService.update(bulletinDTO);
        return new ResponseEntity<>(bulletin, HttpStatus.OK);
    }

    @GetMapping("/bulletins/generate-pdf/{id}")
    public ResponseEntity<byte[]> generateBulletinPDF(@PathVariable Long id) {
        byte[] pdfBytes = bulletinService.generatePDF(id);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=bulletin-" + id + ".pdf")
                .body(pdfBytes);
    }


    @GetMapping("/bulletins/verify/{id}")
    public ResponseEntity<byte[]> verify(@PathVariable Long id, @RequestParam(name = "t") String token) {

        if (!tokenService.isValid(id, token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        byte[] pdfBytes = this.bulletinService.generatePDF(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        // 'inline' permet l'affichage dans le navigateur, 'attachment' force le téléchargement
        headers.setContentDisposition(ContentDisposition.inline().filename("bulletin_" + id + ".pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}
