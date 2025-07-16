package com.icm.temperatura_bk_api.services.impl;

import com.icm.temperatura_bk_api.models.TemperatureLogModel;
import com.icm.temperatura_bk_api.repositories.TemperatureLogRepository;
import com.icm.temperatura_bk_api.services.TemperatureLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TemperatureLogServiceImpl implements TemperatureLogService {
    private final TemperatureLogRepository temperatureLogRepository;

    @Override
    public Optional<TemperatureLogModel> findById(Long id) {
        return temperatureLogRepository.findById(id);
    }

    @Override
    public List<TemperatureLogModel> findByPlatformId(Long platformId) {
        return temperatureLogRepository.findByPlatformId(platformId);
    }

    @Override
    public Page<TemperatureLogModel> findByPlatformId(Long platformId, Pageable pageable) {
        return temperatureLogRepository.findByPlatformId(platformId, pageable);
    }

    @Override
    public List<TemperatureLogModel> findByCompanyId(Long companyId) {
        return temperatureLogRepository.findByPlatformCompanyId(companyId);
    }

    @Override
    public Page<TemperatureLogModel> findByCompanyId(Long companyId, Pageable pageable) {
        return temperatureLogRepository.findByPlatformCompanyId(companyId, pageable);
    }

    @Override
    public TemperatureLogModel save(TemperatureLogModel log) {
        return temperatureLogRepository.save(log);
    }

    @Override
    public void deleteById(Long id) {
        temperatureLogRepository.deleteById(id);
    }
}
