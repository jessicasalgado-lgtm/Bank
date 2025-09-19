package com.bank.managment.controller;

import com.bank.managment.dto.request.CreateUserDTO;
import com.bank.managment.dto.response.UserDTO;
import com.bank.managment.entity.User;
import com.bank.managment.mapper.UserMapper;
import com.bank.managment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Tag(name = "user-controller", description = "Gestión de usuarios")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    // ✅ GET ALL
    @Operation(summary = "Obtener todos los usuarios")
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ✅ GET BY ID
    @Operation(summary = "Obtener un usuario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(user -> ResponseEntity.ok(userMapper.toDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ CREATE
    @Operation(summary = "Crear un nuevo usuario")
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);
        User savedUser = userService.save(user);
        return ResponseEntity.ok(userMapper.toDTO(savedUser));
    }

    // ✅ UPDATE
    @Operation(summary = "Actualizar un usuario existente")
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable Long id,
            @RequestBody CreateUserDTO dto) {
        User updated = userService.update(id, userMapper.toEntity(dto));
        return ResponseEntity.ok(userMapper.toDTO(updated));
    }

    // ✅ DELETE
    @Operation(summary = "Eliminar un usuario")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

