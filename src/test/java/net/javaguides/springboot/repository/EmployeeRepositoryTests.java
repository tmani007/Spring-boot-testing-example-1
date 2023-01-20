package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@outlook.com")
                .build();
    }

    //Junit test for save employee method
    @Test
    @DisplayName("Junit test for save employee method")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    //Junit test for get All employees method
    @DisplayName("Junit test for get All employees method")
    @Test
    public void givenEmployeeList_whenFindAll_thenReturnEmployeeList() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();

        Employee employee1 = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@outlook.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when - action or behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        //then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);

    }

    // Junit test for get employee by id method
    @DisplayName("Junit test for get employee by id method")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // Junit test for get employee email  method
    @DisplayName("Junit test for get employee email  method")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("James")
//                .lastName("Bond")
//                .email("james@outlook.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        //then - verify the output
        assertThat(employeeDB).isNotNull();

    }

    // Junit test for update employee method
    @DisplayName("Junit test for update employee method")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setLastName("kanta");
        savedEmployee.setEmail("kanta@outlook.com");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("kanta@outlook.com");
        assertThat(updatedEmployee.getLastName()).isEqualTo("kanta");

    }

    // Junit test for delete employee operation method
    @DisplayName("Junit test for delete employee operation method")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        // employeeRepository.delete(employee);
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then - verify the output
        assertThat(employeeOptional).isEmpty();

    }

    // Junit test for custom query using JPQL with index
    @DisplayName("Junit test for custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Mani";
        String lastName = "kumar";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // Junit test for custom query using JPQL with Named params
    @DisplayName("Junit test for custom query using JPQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();
        employeeRepository.save(employee);
        String firstName = "Mani";
        String lastName = "kumar";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // Junit test for custom query using SQL with index params
    @DisplayName("Junit test for custom query using SQL with index params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {

        //given - precondition
//        Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();
        employeeRepository.save(employee);
//        String firstName = "Mani";
//        String lastName = "kumar";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQL(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }

    // Junit test for custom query using SQL with Named params
    @DisplayName("Junit test for custom query using SQL with Named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {

        //given - precondition
//                Employee employee = Employee.builder()
//                .firstName("Mani")
//                .lastName("kumar")
//                .email("mani@outlook.com")
//                .build();
        employeeRepository.save(employee);
//        String firstName = "Mani";
//        String lastName = "kumar";

        //when - action or behaviour that we are going to test
        Employee savedEmployee = employeeRepository.findByNativeSQLNamed(employee.getFirstName(), employee.getLastName());

        //then - verify the output
        assertThat(savedEmployee).isNotNull();

    }


}

