package com.myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myy803.traineeship.domainmodel.Company;

public interface CompanyMapper extends JpaRepository<Company, String>{

	Optional<Company> findByUsername(String username);
}
