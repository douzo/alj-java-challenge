package jp.co.axa.apidemo.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Test
    public void testGetEmployeeById() {
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setId(employeeId);
        Mockito.when(employeeService.getEmployee(employeeId)).thenReturn(employee);

        ResponseEntity<Employee> response = employeeController.getEmployee(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employee, response.getBody());
    }

    @Test
    public void testGetEmployeeByIdNotFound() {
        Long employeeId = 1L;
        Mockito.when(employeeService.getEmployee(employeeId)).thenReturn(null);

        ResponseEntity<Employee> response = employeeController.getEmployee(employeeId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee();

        employeeController.saveEmployee(employee);

        Mockito.verify(employeeService).saveEmployee(employee);
    }

    @Test
    public void testDeleteEmployee() {
        Long employeeId = 1L;

        ResponseEntity<String> response = employeeController.deleteEmployee(employeeId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee Deleted Successfully", response.getBody());
        Mockito.verify(employeeService).deleteEmployee(employeeId);
    }
}