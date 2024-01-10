package com.example.demowithtests.util.config.mapstruct;

import com.example.demowithtests.domain.Employee;
import com.example.demowithtests.dto.EmployeeDto;
import com.example.demowithtests.dto.EmployeeReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {
   EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);
   /* @Mapping(source = "firstName", target = "firstName")
    @Mapping(source = "lastName", target = "lastName")
    @Mapping(source = "email", target = "email")*/
    EmployeeDto toDto(Employee employee);

    Employee fromDto(EmployeeDto dto);

 EmployeeReadDto toReadDto(Employee employee);

 List<EmployeeReadDto> toListReadDto(List<Employee> list);
}
