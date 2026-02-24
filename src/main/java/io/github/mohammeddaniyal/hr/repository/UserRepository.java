package io.github.mohammeddaniyal.hr.repository;

import io.github.mohammeddaniyal.hr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    public User findByUsername(String username);
}
