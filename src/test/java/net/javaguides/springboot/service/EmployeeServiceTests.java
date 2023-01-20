package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.service.impl.EmployeeServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {
//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@outlook.com")
                .build();
    }

    // Junit test for save Employee method
    @DisplayName("Junit test for save Employee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        //given - precondition
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());

        given(employeeRepository.save(employee)).willReturn(employee);

//        System.out.println(employeeRepository);
//        System.out.println(employeeService);
        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // System.out.println(savedEmployee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // Junit test for save Employee method
    @DisplayName("Junit test for save Employee method which throws Exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

        //given - precondition
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //given(employeeRepository.save(employee)).willReturn(employee);


        //when - action or behaviour that we are going to test
        assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        //then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));

    }

    // Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method")
    @Test
    public void givenEmployeesList_when_then() {

        //given - precondition
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("James")
                .lastName("bond")
                .email("james@outlook.com")
                .build();

        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    // Junit test for getAllEmployees method
    @DisplayName("Junit test for getAllEmployees method(Negative scenario)")
    @Test
    public void givenEmptyEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {

        //given - precondition
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("James")
                .lastName("bond")
                .email("james@outlook.com")
                .build();

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);

    }

    // Junit test for getEmployeeById method
    @DisplayName("Junit test for getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {

        //given - precondition
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // Junit test for updatedEmployee method
    @DisplayName("Junit test for updatedEmployee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given - precondition
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setFirstName("Manu");
        employee.setEmail("manu@gmail.com");

        //when - action or behaviour that we are going to test
        Employee updatedemployee = employeeService.updateEmployee(employee);

        //then - verify the output
        assertThat(updatedemployee.getEmail()).isEqualTo("manu@gmail.com");
        assertThat(updatedemployee.getFirstName()).isEqualTo("Manu");

    }

    // Junit test for deleteEmployee method
    @DisplayName("Junit test for deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {
        long employeeId = 1L;

        //given - precondition
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when - action or behaviour that we are going to test
        employeeService.deleteEmployee(employeeId);

        //then - verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);

    }

}