package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesWorkPlaces;
import com.example.demowithtests.domain.EmployeesWorkPlacesKey;
import com.example.demowithtests.domain.WorkPlace;
import com.example.demowithtests.repository.EmployeesWorkPlacesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeWorkPlaceServiceBean implements EmployeeWorkPlaceService{


    private EmployeesWorkPlacesRepository repository;

    @Override
    public EmployeesWorkPlaces addConnection(Employee employee, WorkPlace workPlace) {
        EmployeesWorkPlaces connection = getConnection(employee.getId(), workPlace.getId());
        if (connection != null){
            connection.setIsActive(Boolean.TRUE);
        }
        else {
            connection = new EmployeesWorkPlaces();
            connection.setEmployee(employee);
            connection.setWorkPlace(workPlace);
        }
        return repository.save(connection);
    }

    @Override
    public EmployeesWorkPlaces getConnection(Integer employeeId, Integer workPlaceId) {
        return repository.findById(
                EmployeesWorkPlacesKey
                        .builder()
                        .employeeId(employeeId)
                        .wpId(workPlaceId).build())
                .orElse(null);
    }
    @Override
    public void deleteConnection(Integer employeeId, Integer workPlaceId) {

    }

    @Override
    public Integer getCountActiveBusyPlaces(Integer id) {

        return repository.selectCountOfActiveWorkPlacesById(id);
    }

    @Override
    public Integer getCountHeldWorkPlacesOfEmployee(Integer empId) {
        return repository.selectCountHeldWorkPlaceOfEmployeeById(empId);
    }
}
