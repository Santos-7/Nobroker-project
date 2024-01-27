package com.Nobroker.controller;
import com.Nobroker.entity.User;
import com.Nobroker.repositery.UserRepository;
import com.Nobroker.service.PdfService;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    @Autowired
    private PdfService pdfService;
    @Autowired
    private UserRepository userRepository;
    //http://localhost:8080/pdf/report
    @GetMapping("/report")
    public ResponseEntity<byte[]> generatePdfReport() {
        try {
            List<User> userList = userRepository.findAll();// Fetch your user data from the database or any other source
            byte[] pdfBytes = pdfService.generatePdfReport(userList);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", "report.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (DocumentException e) {
            // Handle document exception
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

