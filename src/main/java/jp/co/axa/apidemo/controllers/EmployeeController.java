package jp.co.axa.apidemo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  public void setEmployeeService(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @GetMapping("/employees")
  public List<Employee> getEmployees() {
    try {
      List<Employee> employees = employeeService.retrieveEmployees();
      return employees;
    } catch (Exception e) {
      // Log the error or handle it as needed
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve employees", e);
    }
  }

  @GetMapping("/employees/{employeeId}")
  public ResponseEntity<Employee> getEmployee(@Valid @PathVariable(name = "employeeId") Long employeeId) {
    try {
      Employee employee = employeeService.getEmployee(employeeId);
      return ResponseEntity.ok(employee);
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PostMapping("/employees")
  public void saveEmployee(@Valid @RequestBody Employee employee) {
    try {
      employeeService.saveEmployee(employee);
    } catch (Exception e) {
      // Log the error or handle it as needed
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save employee", e);
    }
  }

  @DeleteMapping("/employees/{employeeId}")
  public ResponseEntity<String> deleteEmployee(@Valid @PathVariable(name = "employeeId") Long employeeId) {
    try {
      employeeService.deleteEmployee(employeeId);
      return ResponseEntity.ok("Employee Deleted Successfully");
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @PutMapping("/employees/{employeeId}")
  public ResponseEntity<String> updateEmployee(@Valid @RequestBody Employee employee,
      @PathVariable(name = "employeeId") Long employeeId) {
    try {
      Employee emp = employeeService.getEmployee(employeeId);
      if (emp != null) {
        employeeService.updateEmployee(employee);
        return ResponseEntity.ok("Employee Updated Successfully");
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
