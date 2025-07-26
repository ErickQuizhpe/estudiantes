package com.api.estudiantes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.estudiantes.entity.company.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
