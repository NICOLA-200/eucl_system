package com.eucl.example.role;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableAutoConfiguration
public interface RoleRepository  extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String roleStudent);
}
