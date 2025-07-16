package com.icm.temperatura_bk_api.repositories;

import com.icm.temperatura_bk_api.models.TemperatureLogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemperatureLogRepository extends JpaRepository<TemperatureLogModel, Long> {
    List<TemperatureLogModel> findByPlatformId(Long platformId);
    Page<TemperatureLogModel> findByPlatformId(Long platformId, Pageable pageable);
}
