package com.icm.temperatura_bk_api.controllers;

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
@RequestMapping("/api/v1/platform")
@RequiredArgsConstructor
public class PlatformController {
    private PlatformService platformService;

    @GetMapping("/{id}")
    public ResponseEntity<PlatformModel> getLaneById(@PathVariable Long id) {
        return platformService.getLaneById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<PlatformModel>> getAllLanes() {
        List<PlatformModel> platforms = platformService.getAllLanes();
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
    public ResponseEntity<PlatformModel> createLane(@RequestBody PlatformModel lane) {
        PlatformModel createdLane = platformService.createLane(lane);
        return new ResponseEntity<>(createdLane, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlatformModel> updateLane(
            @PathVariable Long id,
            @RequestBody PlatformModel lane) {

        PlatformModel updated = platformService.updateLane(id, lane);
        if (updated == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updated);
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
