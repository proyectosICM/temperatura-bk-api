package com.icm.temperatura_bk_api.controllers;

import com.icm.temperatura_bk_api.models.TemperatureLogModel;
import com.icm.temperatura_bk_api.services.TemperatureLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/temperature-logs")
@RequiredArgsConstructor
public class TemperatureLogController {

    private final TemperatureLogService temperatureLogService;

    @GetMapping("/{id}")
    public ResponseEntity<TemperatureLogModel> getById(@PathVariable Long id) {
        return temperatureLogService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-platform/{platformId}")
    public ResponseEntity<List<TemperatureLogModel>> getByPlatformId(@PathVariable Long platformId) {
        return ResponseEntity.ok(temperatureLogService.findByPlatformId(platformId));
    }

    @GetMapping("/by-platform-paginated/{platformId}")
    public ResponseEntity<Page<TemperatureLogModel>> getPagedByPlatformId(
            @PathVariable Long platformId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<TemperatureLogModel> logs = temperatureLogService.findByPlatformId(platformId, pageable);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<List<TemperatureLogModel>> getByCompany(@PathVariable Long companyId) {
        return ResponseEntity.ok(temperatureLogService.findByCompanyId(companyId));
    }

    @GetMapping("/by-company-paginated/{companyId}")
    public ResponseEntity<Page<TemperatureLogModel>> getByCompanyPaginated(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(temperatureLogService.findByCompanyId(companyId, pageable));
    }

    @PostMapping
    public ResponseEntity<TemperatureLogModel> create(@RequestBody TemperatureLogModel log) {
        TemperatureLogModel saved = temperatureLogService.save(log);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (temperatureLogService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        temperatureLogService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
