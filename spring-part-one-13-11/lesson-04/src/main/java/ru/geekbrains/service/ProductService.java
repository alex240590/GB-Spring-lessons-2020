package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    Page<Product> findWithFilter(Optional<String> nameFilter,
                                 Optional<BigDecimal> minFilter,
                                 Optional<BigDecimal> maxFilter,
                                 Optional<Integer> page,
                                 Optional<Integer> size,
                                 Optional<String> sortField);

    List<Product> findAll(Specification<Product> spec);
    
    Optional<Product> findById(Long id);

    void save(Product product);

    void deleteById(Long id);
}
