package com.icm.temperatura_bk_api.controllers;

import com.icm.temperatura_bk_api.models.ObservationModel;
import com.icm.temperatura_bk_api.services.ObservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/observations")
@RequiredArgsConstructor
public class ObservationController {

    private final ObservationService observationService;

    @GetMapping("/{id}")
    public ResponseEntity<ObservationModel> getById(@PathVariable Long id) {
        return observationService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<List<ObservationModel>> getAllByCompany(@PathVariable Long companyId) {
        List<ObservationModel> observations = observationService.findByCompanyId(companyId);
        return ResponseEntity.ok(observations);
    }

    @GetMapping("/by-company-paginated/{companyId}")
    public ResponseEntity<Page<ObservationModel>> getPagedByCompany(
            @PathVariable Long companyId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ObservationModel> observationModel = observationService.findByCompanyId(companyId, pageable);
        return ResponseEntity.ok(observationModel);
    }

    @PostMapping
    public ResponseEntity<ObservationModel> create(@RequestBody ObservationModel observation) {
        ObservationModel saved = observationService.save(observation);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (observationService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        observationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}