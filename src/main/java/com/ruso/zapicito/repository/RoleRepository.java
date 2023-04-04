package com.ruso.zapicito.repository;

import com.ruso.zapicito.dto.RoleType;
import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleType name);
}
