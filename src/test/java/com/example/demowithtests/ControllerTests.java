package com.example.demowithtests;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.domain.Gender;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import com.example.demowithtests.service.EmployeeService;
import com.example.demowithtests.util.config.mapstruct.EmployeeMapper;
import com.example.demowithtests.web.EmployeeControllerBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = EmployeeControllerBean.class)
@DisplayName("Employee Controller Tests")
public class ControllerTests {


    @Autowired
    ObjectMapper mapper;

    @MockBean
    EmployeeService service;

 /* @MockBean
    EmployeeServiceEM serviceEM;*/

    @MockBean
    EmployeeMapper employeeMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("POST API -> /api/users")
    @WithMockUser(roles = "ADMIN")
    public void createPassTest() throws Exception {
        var employee = Employee.builder()
                .id(1)
                .name("Mike")
                .email("mail@mail.com").build();

        when(employeeMapper.fromDto(any(EmployeeDto.class))).thenReturn(employee);
        // when(employeeMapper.toReadDto(any(Employee.class))).thenReturn();
        when(service.create(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .post("/api/users")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));


        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Mike")));
        verify(service).create(any());
    }

    @Test
    @DisplayName("POST API -> /api/users/jpa")
    @WithMockUser(roles = "USER")
    public void testSaveWithJpa() throws Exception {

        var employeeToBeReturn = Employee.builder()
                .id(1)
                .name("Mark")
                .country("France").build();

/*
        doReturn(employeeToBeReturn).when(serviceEM).createWithJpa(any());
        when(this.serviceEM.createWithJpa(any(Employee.class))).thenReturn(employeeToBeReturn);
*/
        // Execute the POST request
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .post("/api/users/jpa")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employeeToBeReturn))
                .with(csrf());
        mockMvc
                .perform(builder)
                .andExpect(status().isMethodNotAllowed())
                .andReturn().getResponse();

/*
        verify(this.serviceEM, times(1)).createWithJpa(any(Employee.class));
        verifyNoMoreInteractions(this.serviceEM);
*/
    }

    @Test
    @DisplayName("GET API -> /api/users/{id}")
    @WithMockUser(roles = "USER")
    public void getPassByIdTest() throws Exception {
        var employee = Employee.builder()
                .id(1)
                .name("Mike")
                .build();
/*
        when(employeeMapper.toEmployeeReadDto(any(Employee.class))).thenReturn(response);
*/
        when(service.getById(1)).thenReturn(employee);

        MockHttpServletRequestBuilder mockRequest = get("/api/users/1");

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name", is("Mike")));

        verify(service).getById(anyInt());
    }

    @Test
    @DisplayName("PUT API -> /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void updatePassByIdTest() throws Exception {
        var response = EmployeeDto.builder().id(1).build();
        var employee = Employee.builder().id(1).name("name").gender(Gender.M).country("country").email("email").build();

        when(service.updateNameById(any(Integer.class), any(String.class))).thenReturn(Optional.ofNullable(employee));
        when(service.updateGenderById(any(Integer.class), any(Gender.class))).thenReturn(Optional.ofNullable(employee));
        when(service.updateCountryById(any(Integer.class), any(String.class))).thenReturn(Optional.ofNullable(employee));
        when(service.updateEmailById(any(Integer.class), any(String.class))).thenReturn(Optional.ofNullable(employee));
        when(service.getById(eq(1))).thenReturn(employee);
        when(employeeMapper.toDto(any(Employee.class))).thenReturn(response);
        when(employeeMapper.fromDto(any(EmployeeDto.class))).thenReturn(employee);
        // when(service.updateById(eq(1), any(Employee.class))).thenReturn(employee);


        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .put("/api/users/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(employee));


        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));

        verify(service).updateNameById(eq(1), any(String.class));
        verify(service).updateGenderById(eq(1), any(Gender.class));
        verify(service).updateCountryById(eq(1), any(String.class));
        verify(service).updateEmailById(eq(1), any(String.class));
    }

    @Test
    @DisplayName("DELETE API -> /api/users/{id}")
    @WithMockUser(roles = "ADMIN")
    public void deletePassTest() throws Exception {

        doNothing().when(service).removeById(1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .patch("/api/users/1")
                .with(csrf());

        mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());

        verify(service).removeById(1);
    }

    @Test
    @DisplayName("GET API -> /api/users/pages")
    @WithMockUser(roles = "USER")
    public void getUsersPageTest() throws Exception {

        var employee = Employee.builder().id(1).name("John").country("US").build();
        var employeeTwo = Employee.builder().id(2).name("Jane").country("UK").build();
        var employeeThree = Employee.builder().id(3).name("Bob").country("US").build();

        List<Employee> list = Arrays.asList(employee, employeeTwo, employeeThree);
        Page<Employee> employeesPage = new PageImpl<>(list);
        Pageable pageable = PageRequest.of(0, 5);

        EmployeeReadDto dto = new EmployeeReadDto();
        EmployeeReadDto dtoTwo = new EmployeeReadDto();
        EmployeeReadDto dtoThree = new EmployeeReadDto();

        when(service.getAllWithPagination(eq(pageable))).thenReturn(employeesPage);
/*
        when(employeeMapper.toEmployeeReadDto(employee)).thenReturn(dto);
        when(employeeMapper.toEmployeeReadDto(employeeTwo)).thenReturn(dtoTwo);
        when(employeeMapper.toEmployeeReadDto(employeeThree)).thenReturn(dtoThree);
*/
        MockHttpServletRequestBuilder mockRequest = get("/api/users/p").param("page", "0")
                .param("size", "5");

     MvcResult result =  mockMvc.perform(mockRequest)
               .andExpect(status().isOk())
                .andReturn();

        verify(service).getAllWithPagination(eq(pageable));
/*
        verify(employeeMapper, times(1)).toEmployeeReadDto(employee);
        verify(employeeMapper, times(1)).toEmployeeReadDto(employeeTwo);
        verify(employeeMapper, times(1)).toEmployeeReadDto(employeeThree);

*/
        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertTrue(contentType.contains(MediaType.APPLICATION_JSON_VALUE));
        String responseContent = result.getResponse().getContentAsString();
        assertNotNull(responseContent);
    }

}