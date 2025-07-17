package com.icm.temperatura_bk_api.mappers;

import com.icm.temperatura_bk_api.dtos.UserDTO;
import com.icm.temperatura_bk_api.models.CompanyModel;
import com.icm.temperatura_bk_api.models.UserModel;

public class UserMapper {

    // De entidad a DTO
    public static UserDTO toDTO(UserModel entity) {
        UserDTO dto = new UserDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());
        dto.setRole(entity.getRole());
        dto.setActive(entity.getActive());
        dto.setCompanyId(entity.getCompany().getId());
        // dto.setPassword(null); // Puedes omitirlo en respuestas si prefieres
        return dto;
    }

    // De DTO a entidad (creación)
    public static UserModel toEntity(UserDTO dto, CompanyModel company) {
        UserModel entity = new UserModel();
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setActive(dto.getActive() != null ? dto.getActive() : true); // valor por defecto
        entity.setCompany(company);
        return entity;
    }

    // Para actualizaciones
    public static void updateEntityFromDTO(UserDTO dto, UserModel entity, CompanyModel company) {
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setUsername(dto.getUsername());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            entity.setPassword(dto.getPassword());
        }
        entity.setRole(dto.getRole());
        entity.setActive(dto.getActive());
        entity.setCompany(company);
    }
}
