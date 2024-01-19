package com.example.demowithtests.web;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.mapstruct.EmployeeMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class EmployeeControllerBeanTest {
    private EmployeeControllerBean employeeControllerBean;
    @Mock
    private EmployeeMapper employeeMapper;
    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        employeeControllerBean = new EmployeeControllerBean(employeeMapper, employeeService);
    }

    @Test
    void whenFindByCountryThenReturnPage() {
        // Given
        String queryCountry = "USA";
        int pageIndex = 1;
        int pageSize = 10;
        List<String> sortField = Arrays.asList("name");
        Sort.Direction sortDirection = Sort.Direction.ASC;

        List<Employee> foundEmployees = Arrays.asList(
            new Employee(1, "John Doe", "test@test.com", "USA", null, null, null),
            new Employee(2, "Jane Doe", "jane@test.com", "USA", null, null, null)
        );
        Page<Employee> expectedResult = new PageImpl<>(foundEmployees, PageRequest.of(pageIndex, pageSize, Sort.by(sortDirection, "name")), 2);
        
        // When
        when(employeeService.findByCountryContaining(anyString(), anyInt(), anyInt(), anyList(), anyString())).thenReturn(expectedResult);
        
        // Then
        Page<Employee> result = employeeControllerBean.findByCountry(queryCountry, pageIndex, pageSize, sortField, sortDirection);
        assertNotNull(result);
        assertEquals(expectedResult, result);
        verify(employeeService, times(1)).findByCountryContaining(queryCountry, pageIndex, pageSize, sortField, sortDirection.toString());
        assertEquals(2, result.getContent().size());
        for(Employee e: result){
            System.out.println(e);
        }
        assertEquals(foundEmployees.get(0).getCountry(), result.getContent().get(0).getCountry());
        assertEquals(foundEmployees.get(1).getCountry(), result.getContent().get(1).getCountry());
    }   

    @AfterEach
    void tearDown() {
        employeeControllerBean = null;
    }
}