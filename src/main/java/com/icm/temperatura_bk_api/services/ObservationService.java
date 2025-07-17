package com.icm.temperatura_bk_api.services;

import com.icm.temperatura_bk_api.models.ObservationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ObservationService {
    Long countTodayObservationsByCompany(Long companyId);
    Optional<ObservationModel> findById(Long id);
    List<ObservationModel> findByCompanyId(Long companyId);
    Page<ObservationModel> findByCompanyId(Long companyId, Pageable pageable);
    ObservationModel save(ObservationModel observation);
    void deleteById(Long id);
}
