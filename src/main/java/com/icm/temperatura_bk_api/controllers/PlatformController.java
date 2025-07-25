package com.icm.temperatura_bk_api.controllers;

import com.icm.temperatura_bk_api.dtos.PlatformDTO;
import com.icm.temperatura_bk_api.dtos.PlatformTemperatureDTO;
import com.icm.temperatura_bk_api.models.PlatformModel;
import com.icm.temperatura_bk_api.services.PlatformService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/platforms")
@RequiredArgsConstructor
public class PlatformController {
    private final PlatformService platformService;

    @GetMapping("/{id}")
    public ResponseEntity<PlatformModel> getLaneById(@PathVariable Long id) {
        return platformService.getLaneById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PlatformModel>> getAllLanes() {
        List<PlatformModel> platforms = platformService.getAllLanes();
        if (platforms.isEmpty()){
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<PlatformModel>> getAllLanesPaginated(@RequestParam(defaultValue = "0") int page,
                                                                    @RequestParam(defaultValue = "10") int size) {
        Page<PlatformModel> platforms = platformService.getAllLanesPaginated(page, size);
        if (platforms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<List<PlatformModel>> getByCompany(@PathVariable Long companyId) {
        List<PlatformModel> platforms = platformService.getByCompany(companyId);
        if (platforms.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(platforms);
    }

    @GetMapping("/by-company-paginated/{companyId}")
    public ResponseEntity<Page<PlatformModel>> getByCompanyPaginated(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<PlatformModel> platforms = platformService.getByCompanyPaginated(companyId, page, size);
        return ResponseEntity.ok(platforms);
    }

    @PostMapping
    public ResponseEntity<PlatformDTO> create(@RequestBody PlatformDTO dto) {
        return ResponseEntity.ok(platformService.createLane(dto));
    }


    @PutMapping("/{id}")
    public ResponseEntity<PlatformDTO> updatePlatform(
            @PathVariable Long id,
            @RequestBody PlatformDTO dto
    ) {
        PlatformDTO updated = platformService.updateLane(id, dto);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/temperature/{id}")
    public PlatformTemperatureDTO updateTemperature(
            @PathVariable Long id,
            @RequestBody PlatformTemperatureDTO temperatureDTO
    ) {
        return platformService.updateTemperature(id, temperatureDTO.getTemperature());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLane(@PathVariable Long id) {
        try {
            platformService.deleteLane(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
