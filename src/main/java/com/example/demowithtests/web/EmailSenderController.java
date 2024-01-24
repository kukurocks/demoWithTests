package com.example.demowithtests.web;

import com.example.demowithtests.service.emailSender.EmailSenderServiceBean;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class EmailSenderController {

    EmailSenderServiceBean emailSenderServiceBean;

    @PostMapping("/send-emails")
    public ResponseEntity<?> sendEmailsFromCsv(@RequestParam String pathToCsv) {
        emailSenderServiceBean.sendEmailsFromCsv(pathToCsv);
        return ResponseEntity.ok("Emails sent");
    }
}