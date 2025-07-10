package com.icm.temperatura_bk_api.controllers;

import com.icm.temperatura_bk_api.services.CompanyService;
import com.icm.temperatura_bk_api.models.CompanyModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/company")
public class CompanyController {
    public final CompanyService companyService;

    @GetMapping
    public ResponseEntity<List<CompanyModel>> getAllCompanies() {
        List<CompanyModel> companies = companyService.getAllCompanies();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<CompanyModel>> getAllCompaniesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CompanyModel> companies = companyService.getAllCompaniesPaginated(page, size);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyModel> getCompanyById(@RequestParam Long id) {
        return companyService.getCompanyById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CompanyModel> createCompany(@RequestBody CompanyModel company) {
        CompanyModel createdCompany = companyService.createCompany(company);
        return new ResponseEntity<>(createdCompany, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyModel> updateLane(
            @PathVariable Long id,
            @RequestBody CompanyModel company) {

        CompanyModel updated = companyService.updateCompany(id, company);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
    }
}
