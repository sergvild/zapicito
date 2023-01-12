package com.ruso.zapicito.repository;

import com.ruso.zapicito.entity.Employee;
import com.ruso.zapicito.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByName(String name);
}
