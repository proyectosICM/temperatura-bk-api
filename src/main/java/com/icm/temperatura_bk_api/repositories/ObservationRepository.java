package com.icm.temperatura_bk_api.repositories;

import com.icm.temperatura_bk_api.models.ObservationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ObservationRepository extends JpaRepository<ObservationModel, Long> {
    List<ObservationModel> findByCompanyId(Long companyId);
    Page<ObservationModel> findByCompanyId(Long companyId, Pageable pageable);
    //Data
    @Query("SELECT COUNT(o) FROM ObservationModel o WHERE o.company.id = :companyId AND DATE(o.createdAt) = CURRENT_DATE")
    Long countByCompanyIdAndToday(Long companyId);

    @Query("SELECT o FROM ObservationModel o WHERE o.platform.id = :platformId ORDER BY o.createdAt DESC LIMIT 1")
    Optional<ObservationModel> findLastByPlatformId(@Param("platformId") Long platformId);
}
