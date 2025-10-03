package com.bank.managment.controller;

import com.bank.managment.dto.request.CreateUserDTO;
import com.bank.managment.dto.request.UpdateUserDTO;
import com.bank.managment.dto.response.UserDTO;
import com.bank.managment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "user-controller", description = "Gestión de usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @Operation(summary = "Obtener todos los usuarios")

    public ResponseEntity<List<UserDTO>> getUser(){
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping // /users
    public ResponseEntity<UserDTO> saveUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(createUserDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserDTO userDTO){
        return ResponseEntity.ok((UserDTO) userService.update(userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}