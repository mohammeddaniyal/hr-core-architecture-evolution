package io.github.mohammeddaniyal.hr.repository;

import io.github.mohammeddaniyal.hr.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {
    public boolean existsByPanNumber(String panNumber);
    public boolean existsByAadharCardNumber(String aadharCardNumber);
    public boolean existsByDesignationCode(Integer  designationCode);
}
