package com.icm.temperatura_bk_api.repositories;

import com.icm.temperatura_bk_api.models.PlatformModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatformRepository extends JpaRepository<PlatformModel, Long> {
    List<PlatformModel> findByCompanyId(Long companyId);
    Page<PlatformModel> findByCompanyId(Long companyId, Pageable pageable);
    List<PlatformModel> findByNameContainingIgnoreCase(String name);
}
