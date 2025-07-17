package com.icm.temperatura_bk_api.services.impl;

import com.icm.temperatura_bk_api.models.ObservationModel;
import com.icm.temperatura_bk_api.repositories.ObservationRepository;
import com.icm.temperatura_bk_api.services.ObservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ObservationServiceImpl implements ObservationService {
    private final ObservationRepository observationRepository;

    @Override
    public Long countTodayObservationsByCompany(Long companyId) {
        return observationRepository.countByCompanyIdAndToday(companyId);
    }
    @Override
    public Optional<ObservationModel> findById(Long id) {
        return observationRepository.findById(id);
    }

    @Override
    public List<ObservationModel> findByCompanyId(Long companyId) {
        return observationRepository.findByCompanyId(companyId);
    }

    @Override
    public Page<ObservationModel> findByCompanyId(Long companyId, Pageable pageable) {
        return observationRepository.findByCompanyId(companyId, pageable);
    }

    @Override
    public ObservationModel save(ObservationModel observation) {
        return observationRepository.save(observation);
    }

    @Override
    public void deleteById(Long id) {
        observationRepository.deleteById(id);
    }
}
