package com.icm.temperatura_bk_api.services;

import com.icm.temperatura_bk_api.models.TemperatureLogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TemperatureLogService {
    Optional<TemperatureLogModel> findById(Long id);
    List<TemperatureLogModel> findByPlatformId(Long platformId);
    Page<TemperatureLogModel> findByPlatformId(Long platformId, Pageable pageable);
    List<TemperatureLogModel> findByCompanyId(Long companyId);
    Page<TemperatureLogModel> findByCompanyId(Long companyId, Pageable pageable);
    TemperatureLogModel save(TemperatureLogModel log);
    void deleteById(Long id);
}
