package io.github.mohammeddaniyal.hr.repository;

import io.github.mohammeddaniyal.hr.entity.Designation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesignationRepository extends JpaRepository<Designation, Integer> {
    boolean existsByTitle(String title);
    boolean existsByTitleAndCodeNot(String title,int code);
}
