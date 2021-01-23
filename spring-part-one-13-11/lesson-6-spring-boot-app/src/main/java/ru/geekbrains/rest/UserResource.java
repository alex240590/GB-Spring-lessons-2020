package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.UserRepository;

import java.util.List;

// http://localhost:8080/mvc-app/swagger-ui/index.html

@CrossOrigin(origins = "http://localhost:63342")
@Tag(name = "User resource API", description = "API to manipulate User resource ...")
@RequestMapping("/api/v1/user")
@RestController
public class UserResource {

    private final UserRepository repository;

    @Autowired
    public UserResource(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<User> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public User findById(@PathVariable("id") long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public User createUser(@RequestBody User user) {
        repository.save(user);
        return user;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public User updateUser(@RequestBody User user) {
        if (repository.existsById(user.getId())) {
            repository.save(user);
            return user;
        }
        throw new NotFoundException();
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteUser(@PathVariable("id") long id) {
        repository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }
}
