package com.icm.temperatura_bk_api.services.impl;

import com.icm.temperatura_bk_api.models.PlatformModel;
import com.icm.temperatura_bk_api.repositories.PlatformRepository;
import com.icm.temperatura_bk_api.services.PlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {
    private PlatformRepository platformRepository;

    @Override
    public Optional<PlatformModel> getLaneById(Long id) {
        return platformRepository.findById(id);
    }

    @Override
    public List<PlatformModel> getAllLanes() {
        return platformRepository.findAll();
    }

    @Override
    public Page<PlatformModel> getAllLanesPaginated(int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return platformRepository.findAll(pageable);
    }

    @Override
    public List<PlatformModel> getByCompany(Long companyId) {
        return platformRepository.findByCompanyId(companyId);
    }

    @Override
    public Page<PlatformModel> getByCompanyPaginated(Long companyId, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return platformRepository.findByCompanyId(companyId, pageable);
    }

    @Override
    public PlatformModel createLane(PlatformModel lane) {
        return platformRepository.save(lane);
    }

    @Override
    public PlatformModel updateLane(Long id, PlatformModel lane) {
        return platformRepository.findById(id)
                .map(existingLane -> {
                    existingLane.setName(lane.getName());
                    return platformRepository.save(existingLane);
                })
                .orElse(null);
    }
}
