package com.example.demowithtests.service.emailSender;


import com.opencsv.exceptions.CsvValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.opencsv.CSVReader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@AllArgsConstructor
@Slf4j
@Service
public class EmailSenderServiceBean implements EmailSenderService {



    private JavaMailSender mailSender;

    private final String fromEmail = "my-email@example.com";

    public void sendEmailsFromCsv(String pathToCsv) {
        String text = "email text";

        try(CSVReader reader = new CSVReader(new FileReader(pathToCsv))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                sendEmail(line[0], text);
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch(IOException | CsvValidationException e) {
            log.error("Exception occurred while reading CSV file", e);
        }
    }

    private void sendEmail(String toEmail, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setText(content);
        mailSender.send(message);
        log.info("Email sent to {}", toEmail);
    }
}




