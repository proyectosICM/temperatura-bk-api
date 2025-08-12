package com.icm.temperatura_bk_api.services.impl;

import com.icm.temperatura_bk_api.dtos.PlatformDTO;
import com.icm.temperatura_bk_api.dtos.PlatformTemperatureDTO;
import com.icm.temperatura_bk_api.mappers.PlatformMapper;
import com.icm.temperatura_bk_api.models.CompanyModel;
import com.icm.temperatura_bk_api.models.ObservationModel;
import com.icm.temperatura_bk_api.models.PlatformModel;
import com.icm.temperatura_bk_api.models.TemperatureLogModel;
import com.icm.temperatura_bk_api.repositories.CompanyRepository;
import com.icm.temperatura_bk_api.repositories.ObservationRepository;
import com.icm.temperatura_bk_api.repositories.PlatformRepository;
import com.icm.temperatura_bk_api.services.PlatformService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements PlatformService {
    private final PlatformRepository platformRepository;
    private final CompanyRepository companyRepository;
    private final TemperatureLogServiceImpl temperatureLogService;
    private final ObservationServiceImpl observationService;
    private final ObservationRepository observationRepository;

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
    public PlatformDTO createLane(PlatformDTO dto) {
        CompanyModel company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        PlatformModel entity = PlatformMapper.toEntity(dto, company);
        PlatformModel saved = platformRepository.save(entity);

        return PlatformMapper.toDTO(saved);
    }

    @Override
    public PlatformDTO updateLane(Long id, PlatformDTO dto) {
        PlatformModel existing = platformRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Platform not found"));

        // Actualiza los campos
        existing.setName(dto.getName());
        existing.setSensorId(dto.getSensorId());
        existing.setMinTemperature(dto.getMinTemperature());
        existing.setMaxTemperature(dto.getMaxTemperature());
        CompanyModel company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        existing.setCompany(company);

        PlatformModel updated = platformRepository.save(existing);
        return PlatformMapper.toDTO(updated);
    }

    @Override
    public PlatformTemperatureDTO updateTemperature(Long id, Double temperature) {
        Optional<PlatformModel> optionalPlatform = platformRepository.findById(id);
        if (optionalPlatform.isEmpty()) {
            throw new RuntimeException("Plataforma no encontrada");
        }

        PlatformModel platform = optionalPlatform.get();
        platform.setTemperature(temperature);
        PlatformModel updated = platformRepository.save(platform);

        TemperatureLogModel log = new TemperatureLogModel();
        log.setTemperature(temperature);
        log.setPlatform(platform);
        log.setCompany(platform.getCompany());
        temperatureLogService.save(log);

        boolean ahoraFueraDeRango = temperature < platform.getMinTemperature() || temperature > platform.getMaxTemperature();

        // Solo crear observación si hay transición a fuera de rango
        if (ahoraFueraDeRango) {
            // Consultar última observación
            Optional<ObservationModel> ultimaObsOpt = observationRepository.findLastByPlatformId(platform.getId());

            boolean crearNueva = true;
            if (ultimaObsOpt.isPresent()) {
                ObservationModel ultimaObs = ultimaObsOpt.get();
                // Evitar duplicar si la última también fue fuera de rango
                boolean ultimaFueraDeRango =
                        ultimaObs.getTemperature() < platform.getMinTemperature() ||
                                ultimaObs.getTemperature() > platform.getMaxTemperature();

                if (ultimaFueraDeRango) {
                    crearNueva = false;
                }
            }

            if (crearNueva) {
                ObservationModel obs = new ObservationModel();
                obs.setTemperature(temperature);
                obs.setDescription(String.format(
                        "Temperatura fuera de rango (%.1f - %.1f): valor actual %.1f°C",
                        platform.getMinTemperature(), platform.getMaxTemperature(), temperature
                ));
                obs.setPlatform(platform);
                obs.setCompany(platform.getCompany());
                obs.setUser(null);
                observationService.save(obs);
            }
        }


        return PlatformMapper.toTemperatureDTO(updated);
    }

    @Override
    public void deleteLane(Long id) {
        PlatformModel lane = platformRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lane not found with id: " + id));
        platformRepository.delete(lane);
    }
}
