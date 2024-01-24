package com.example.demowithtests.service.emailSender;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.repository.EmployeeRepository;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.List;

@AllArgsConstructor
@Service
public class CsvExportService {

    private EmployeeRepository employeeRepository;

    public void exportEmailsToCSV(PrintWriter writer) {
        try {
            List<Employee> employees = employeeRepository.findAll();

            ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(Employee.class);


                strategy.setColumnMapping("email");




            StatefulBeanToCsv<Employee> csvExporter = new StatefulBeanToCsvBuilder<Employee>(writer)
                    .withMappingStrategy(strategy)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();

            csvExporter.write(employees);

        } catch (Exception e) {
            throw new RuntimeException("Failed to export data to CSV", e);
        }
    }
}