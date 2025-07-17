package com.icm.temperatura_bk_api.dtos;

import com.icm.temperatura_bk_api.enums.Role;
import lombok.Data;

@Data
public class UserDTO {
    private Long id; // puede ser null en creación
    private String name;
    private String email;
    private String username;
    private String password; // opcional en respuestas si quieres omitirlo
    private Role role;
    private Boolean active;
    private Long companyId;
}