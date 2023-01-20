package net.javaguides.springboot.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
//@Testcontainers
public class EmployeeControllerIT extends AbstractContainerBaseTest {

//    @Container
//    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
//            .withUsername("root")
//            .withPassword("root")
//            .withDatabaseName("ems");
//
//    @DynamicPropertySource
//    public static void dynamicPropertySource(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mySQLContainer::getUsername);
//        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//    }

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        employeeRepository.deleteAll();
    }

    //Integration test for createEmployee REST API using TestContainer(docker)
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        //given - precondition
        Employee employee = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@gmail.com")
                .build();

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then - verify the output
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    // Integration test for getAllEmployees REST API using TestContainer(docker)
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnsEmployeeList() throws Exception {

        //given - precondition
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("mani").lastName("kumar").email("mani@gmail.com").build());
        listOfEmployees.add(Employee.builder().firstName("bond").lastName("james").email("bond@gmail.com").build());
        employeeRepository.saveAll(listOfEmployees);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees"));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));

    }

    // Integration test for GetEmployeeById REST API - Positive Case-Valid employee id using TestContainer(docker)
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        //given - precondition
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    // Integration test for GetEmployeeById REST API - Negative Case-InValid employee id using TestContainer(docker)
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {

        //given - precondition
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@gmail.com")
                .build();
        employeeRepository.save(employee);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // Integration test for Update Employee REST API - positive Case using TestContainer(docker)
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception {

        //given - precondition
        Employee savedEmployee = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("tim")
                .lastName("cook")
                .email("tim@gmail.com")
                .build();

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));

    }

    // Integration test for Update Employee REST API - Negative Case using TestContainer(docker)
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {

        //given - precondition
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("tim")
                .lastName("cook")
                .email("tim@gmail.com")
                .build();

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // Integration test for delete employee REST API using TestContainer(docker)
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {

        //given - precondition
        Employee savedEmployee = Employee.builder()
                .firstName("Mani")
                .lastName("kumar")
                .email("mani@gmail.com")
                .build();
        employeeRepository.save(savedEmployee);

        //when - action or behaviour that we are going to test
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", savedEmployee.getId()));

        //then - verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }


}
