package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.geekbrains.NotFoundException;
import ru.geekbrains.persist.entity.User;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.UserRepository;

import java.util.List;

@Tag(name = "Product resource API", description = "API to manipulate Product resource ...")
@RequestMapping("/api/v1/product")
@RestController
public class ProductResource {

    private final ProductRepository repository;

    @Autowired
    public ProductResource(ProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/all", produces = "application/json")
    public List<Product> findAll() {
        return repository.findAll();
    }

    @GetMapping(path = "/{id}/id", produces = "application/json")
    public Product findById(@PathVariable("id") long id) {
        return repository.findById(id)
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody Product product) {
        repository.save(product);
        return product;
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public Product updateProduct(@RequestBody Product product) {
        if (product.getId() != null) {
        repository.save(product);
        return product;}
        else throw new NotFoundException();
    }

    @DeleteMapping(path = "/{id}/id")
    public void deleteProduct(@PathVariable("id") long id) {
        repository.deleteById(id);
    }

    @ExceptionHandler
    public ResponseEntity<String> notFoundExceptionHandler(NotFoundException ex) {
        return new ResponseEntity<>("Entity not found", HttpStatus.NOT_FOUND);
    }
}
