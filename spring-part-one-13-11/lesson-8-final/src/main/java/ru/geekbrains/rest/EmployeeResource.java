package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.persist.entity.Employee;
import ru.geekbrains.persist.repo.EmployeeRepository;

import java.util.List;

@Tag(name = "Employee resource API", description = "API to manage employee resource")
@RequestMapping("/api/v1/employee")
@RestController
public class EmployeeResource {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeResource(EmployeeRepository repository){
        this.repository = repository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Employee> findAll(){
        return repository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Employee findById(@PathVariable("id") Long id){
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Employee createEmployee (@RequestBody Employee emp){
        repository.save(emp);
        return emp;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Employee updateEmployee (@RequestBody Employee emp){

        repository.findById(emp.getId())
                .orElseThrow(NotFoundException::new);

        repository.save(emp);
        return emp;
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteEmployee (@PathVariable("id") Long id){
        repository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException ex){
        return new ResponseEntity<>("Entity not found!", HttpStatus.NOT_FOUND);
    }

}
