package com.icm.temperatura_bk_api.repositories;

import com.icm.temperatura_bk_api.models.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {
}
