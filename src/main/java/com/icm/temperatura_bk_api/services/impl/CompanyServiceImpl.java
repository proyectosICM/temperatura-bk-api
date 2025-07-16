package com.icm.temperatura_bk_api.services.impl;

import com.icm.temperatura_bk_api.models.CompanyModel;
import com.icm.temperatura_bk_api.repositories.CompanyRepository;
import com.icm.temperatura_bk_api.services.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;

    @Override
    public Optional<CompanyModel> getCompanyById(Long id) {
        return companyRepository.findById(id);
    }

    @Override
    public List<CompanyModel> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Page<CompanyModel> getAllCompaniesPaginated(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    @Override
    public CompanyModel createCompany(CompanyModel company) {
        return companyRepository.save(company);
    }

    @Override
    public CompanyModel updateCompany(Long id, CompanyModel company) {
        return companyRepository.findById(id)
                .map(existingCompany -> {
                    existingCompany.setName(company.getName());
                    return companyRepository.save(existingCompany);
                })
                .orElse(null);
    }
}
