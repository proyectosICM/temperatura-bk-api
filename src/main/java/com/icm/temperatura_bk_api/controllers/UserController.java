package com.icm.temperatura_bk_api.controllers;

import com.icm.temperatura_bk_api.dtos.UserDTO;
import com.icm.temperatura_bk_api.enums.Role;
import com.icm.temperatura_bk_api.mappers.UserMapper;
import com.icm.temperatura_bk_api.models.UserModel;
import com.icm.temperatura_bk_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/roles")
    public List<String> getRoles() {
        return Arrays.stream(Role.values())
                .map(Enum::name)
                .toList();
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserModel> getByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<UserModel>> getAllUsersPaginated(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserModel> users = userService.getAllUsersPaginated(pageable);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-company/{companyId}")
    public ResponseEntity<List<UserModel>> getUsersByCompanyId(@PathVariable Long companyId) {
        List<UserModel> users = userService.getUsersByCompanyId(companyId);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/by-company-paginated/{companyId}")
    public ResponseEntity<Page<UserModel>> getUsersByCompanyIdPaginated(@PathVariable Long companyId,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<UserModel> users = userService.getUsersByCompanyId(companyId, pageable);
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO dto) {
        UserModel saved = userService.createUser(dto);
        return new ResponseEntity<>(UserMapper.toDTO(saved), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        UserModel updated = userService.updateUser(id, dto);
        return ResponseEntity.ok(UserMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
