package com.icm.temperatura_bk_api.services;

import com.icm.temperatura_bk_api.models.UserModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<UserModel> getUserById(Long id);
    List<UserModel> getAllUsers();
    Page<UserModel> getAllUsersPaginated(int page, int size);
    List<UserModel> getUsersByCompanyId(Long companyId);
    Page<UserModel> getUsersByCompanyId(Long companyId, int page, int size);
    UserModel createUser(UserModel user);
    UserModel updateUser(Long id, UserModel user);
}
