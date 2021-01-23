package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
