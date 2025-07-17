package com.icm.temperatura_bk_api.mappers;

import com.icm.temperatura_bk_api.dtos.PlatformDTO;
import com.icm.temperatura_bk_api.dtos.PlatformTemperatureDTO;
import com.icm.temperatura_bk_api.models.CompanyModel;
import com.icm.temperatura_bk_api.models.PlatformModel;

public class PlatformMapper {

    // De entidad a DTO
    public static PlatformDTO toDTO(PlatformModel entity) {
        PlatformDTO dto = new PlatformDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSensorId(entity.getSensorId());
        dto.setTemperature(entity.getTemperature());
        dto.setCompanyId(entity.getCompany().getId());
        return dto;
    }

    // De entidad a DTO parcial (sólo temperatura)
    public static PlatformTemperatureDTO toTemperatureDTO(PlatformModel entity) {
        PlatformTemperatureDTO dto = new PlatformTemperatureDTO();
        dto.setTemperature(entity.getTemperature());
        return dto;
    }

    // De DTO a entidad (creación)
    public static PlatformModel toEntity(PlatformDTO dto, CompanyModel company) {
        PlatformModel entity = new PlatformModel();
        entity.setName(dto.getName());
        entity.setSensorId(dto.getSensorId());
        entity.setTemperature(dto.getTemperature());
        entity.setCompany(company);
        return entity;
    }

    // Para actualizaciones (opcional)
    public static void updateEntityFromDTO(PlatformDTO dto, PlatformModel entity, CompanyModel company) {
        entity.setName(dto.getName());
        entity.setSensorId(dto.getSensorId());
        entity.setCompany(company);
    }
}
