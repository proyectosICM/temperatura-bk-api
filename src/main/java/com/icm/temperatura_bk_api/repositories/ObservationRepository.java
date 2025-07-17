package com.icm.temperatura_bk_api.repositories;

import com.icm.temperatura_bk_api.models.ObservationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<ObservationModel, Long> {
    List<ObservationModel> findByCompanyId(Long companyId);
    Page<ObservationModel> findByCompanyId(Long companyId, Pageable pageable);
    @Query("SELECT COUNT(o) FROM ObservationModel o WHERE o.company.id = :companyId AND DATE(o.createdAt) = CURRENT_DATE")
    Long countByCompanyIdAndToday(Long companyId);
}
