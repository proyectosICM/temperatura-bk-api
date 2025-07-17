package com.icm.temperatura_bk_api.services;

import com.icm.temperatura_bk_api.dtos.PlatformDTO;
import com.icm.temperatura_bk_api.models.PlatformModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PlatformService {
    Optional<PlatformModel> getLaneById(Long id);
    List<PlatformModel> getAllLanes();
    Page<PlatformModel> getAllLanesPaginated(int page, int size);
    List<PlatformModel> getByCompany(Long companyId);
    Page<PlatformModel> getByCompanyPaginated(Long companyId, int page, int size);
    PlatformDTO createLane(PlatformDTO dto);
    PlatformDTO updateLane(Long id, PlatformDTO dto);
    void deleteLane(Long id);
}
