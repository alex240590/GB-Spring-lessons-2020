package ru.geekbrains.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import ru.geekbrains.persist.entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    void save(User product);

    void deleteById(Long id);
}
