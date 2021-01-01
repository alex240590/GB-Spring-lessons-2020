package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
