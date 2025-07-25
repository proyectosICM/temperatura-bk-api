package com.icm.temperatura_bk_api.services;

import com.icm.temperatura_bk_api.dtos.UserDTO;
import com.icm.temperatura_bk_api.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserModel> findByUsername(String username);
    Optional<UserModel> getUserById(Long id);
    List<UserModel> getAllUsers();
    Page<UserModel> getAllUsersPaginated(Pageable pageable);
    List<UserModel> getUsersByCompanyId(Long companyId);
    Page<UserModel> getUsersByCompanyId(Long companyId, Pageable pageable);
    UserModel createUser(UserDTO dto);
    UserModel updateUser(Long id, UserDTO dto);
    void deleteUser(Long id);
}
