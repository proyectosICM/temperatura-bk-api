package com.icm.temperatura_bk_api.dtos;

import lombok.Data;

@Data
public class PlatformDTO {
    private Long id; // solo para respuestas (opcional en POST)
    private String name;
    private String sensorId;
    private Double temperature;
    private Long companyId;
}
