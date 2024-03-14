package com.example.advancedmapping.demo;
import com.example.advancedmapping.demo.dto.ManagerDTO;
import com.example.advancedmapping.demo.entities.Address;
import com.example.advancedmapping.demo.entities.Employee;

import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MapperMain {
    public static void main(String[] args) {

        ModelMapper mapper = new ModelMapper();

        Address address = new Address("Ivan Nikolov", 4, "Karlovo", "Bulgaria");

        Employee manager = new Employee(
                "Mr.",
                "Manager",
                BigDecimal.valueOf(2000),
                LocalDate.now(),
                address,
                true);

        Employee first = new Employee(
                "Ivan",
                "Ivanov",
                BigDecimal.valueOf(1000),
                LocalDate.now(),
                address,
                true);

        Employee second = new Employee(
                "Dragan",
                "Draganov",
                BigDecimal.valueOf(1500),
                LocalDate.now(),
                address,
                true);

        manager.addEmployee(first); // Добавете първия служител към мениджъра
        manager.addEmployee(second); // Добавете втория служител към мениджъра

        ManagerDTO dto = mapper.map(manager, ManagerDTO.class);

        System.out.println(dto);
    }
}
