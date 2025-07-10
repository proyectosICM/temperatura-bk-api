package com.icm.temperatura_bk_api.services;

import com.icm.temperatura_bk_api.models.CompanyModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CompanyService {
    List<CompanyModel> getAllCompanies();
    Page<CompanyModel> getAllCompaniesPaginated(int page, int size);
    Optional<CompanyModel> getCompanyById(Long id);
    CompanyModel createCompany(CompanyModel company);
    CompanyModel updateCompany(Long id, CompanyModel company);
}
