package com.icm.temperatura_bk_api.services.impl;

import com.icm.temperatura_bk_api.dtos.UserDTO;
import com.icm.temperatura_bk_api.mappers.UserMapper;
import com.icm.temperatura_bk_api.models.CompanyModel;
import com.icm.temperatura_bk_api.models.UserModel;
import com.icm.temperatura_bk_api.repositories.CompanyRepository;
import com.icm.temperatura_bk_api.repositories.UserRepository;
import com.icm.temperatura_bk_api.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<UserModel> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Page<UserModel> getAllUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public List<UserModel> getUsersByCompanyId(Long companyId) {
        return userRepository.findByCompanyId(companyId);
    }

    @Override
    public Page<UserModel> getUsersByCompanyId(Long companyId, Pageable pageable) {
        return userRepository.findByCompanyId(companyId, pageable);
    }

    public UserModel createUser(UserDTO dto) {
        CompanyModel company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada"));
        UserModel user = UserMapper.toEntity(dto, company);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public UserModel updateUser(Long id, UserDTO dto) {
        UserModel existing = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        CompanyModel company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa no encontrada"));

        UserMapper.updateEntityFromDTO(dto, existing, company);
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            existing.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario con ID " + id + " no encontrado.");
        }
        userRepository.deleteById(id);
    }
}
