package com.example.demowithtests.service;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.EmployeesWorkPlaces;
import com.example.demowithtests.domain.WorkPlace;

public interface EmployeeWorkPlaceService {

    EmployeesWorkPlaces addConnection(Employee employee, WorkPlace workPlace);

    EmployeesWorkPlaces getConnection(Integer employeeId, Integer workPlaceId);

    void deleteConnection(Integer employeeId, Integer workPlaceId);

    Integer getCountActiveBusyPlaces(Integer id);

    Integer getCountHeldWorkPlacesOfEmployee (Integer empId);
}
