package com.example.demowithtests.web;

import com.example.demowithtests.service.emailSender.CsvExportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api")
public class CsvDownloadController {

    CsvExportService csvExportService;

    @GetMapping("/download/emails.csv")
    public void downloadCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=emails.csv");
        csvExportService.exportEmailsToCSV(response.getWriter());
    }
}