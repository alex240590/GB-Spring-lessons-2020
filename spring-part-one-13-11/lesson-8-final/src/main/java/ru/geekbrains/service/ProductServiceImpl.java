package ru.geekbrains.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.controllers.ProductController;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    Sort.Direction sd = Sort.Direction.ASC;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Page<Product> findWithFilter(Optional<String> nameFilter,
                                        Optional<BigDecimal> minFilter,
                                        Optional<BigDecimal> maxFilter,
                                        Optional<Integer> page,
                                        Optional<Integer> size,
                                        Optional<String> sortField,
                                        Optional<Boolean> changeSortOrder) {

        Specification<Product> spec = Specification.where(null);

        // revert sorting order
        if (changeSortOrder.isPresent() && changeSortOrder.get()) {
            sd = (sd == Sort.Direction.ASC) ? Sort.Direction.DESC : Sort.Direction.ASC;
        }

        if (nameFilter.isPresent()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }
        if (minFilter.isPresent()) {
            spec = spec.and(ProductSpecification.priceBigger(minFilter.get()));
        }
        if (maxFilter.isPresent()) {
            spec = spec.and(ProductSpecification.priceLess(maxFilter.get()));
        }


        if (sortField.isPresent() && !sortField.get().equals("")) {
            return productRepository.findAll(spec, PageRequest.of(page.orElse(1) - 1,
                    size.orElse(5),
                    Sort.by(sd, sortField.get())));
        }else{
                return productRepository.findAll(spec, PageRequest.of(page.orElse(1) - 1,
                        size.orElse(5),
                        Sort.by(Sort.Direction.ASC, "id")));
            }
    }

    @Override
    public List<Product> findAll(Specification<Product> spec) {
        return productRepository.findAll(spec);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
