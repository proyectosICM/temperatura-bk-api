package com.icm.temperatura_bk_api.repositories;

import com.icm.temperatura_bk_api.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<UserModel> findByUsername(String username);
    List<UserModel> findByCompanyId(Long companyId);
    Page<UserModel> findByCompanyId(Long companyId, org.springframework.data.domain.Pageable pageable);
}
