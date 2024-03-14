package com.example.mappingobjects.demo;

import com.example.mappingobjects.demo.dto.EmployeeDTO;
import com.example.mappingobjects.demo.entities.Address;
import com.example.mappingobjects.demo.entities.Employee;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MapperMain {
    public static void main(String[] args) {

        ModelMapper mapper = new ModelMapper();

        Address address = new Address("Ivan Nikolov", 4, "Karlovo", "Bulgaria");

        Employee source = new Employee(
                "Peshp",
                "Ivanov",
                BigDecimal.valueOf(2000),
                LocalDate.now(),
                address
        );

        EmployeeDTO dto = mapper.map(source, EmployeeDTO.class);

        System.out.println(dto);
    }
}
