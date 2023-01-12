package com.ruso.zapicito.repository;

import com.ruso.zapicito.entity.Company;
import com.ruso.zapicito.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
