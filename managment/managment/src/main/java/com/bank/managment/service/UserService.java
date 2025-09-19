package com.bank.managment.service;

import com.bank.managment.dto.request.CreateUserDTO;
import com.bank.managment.dto.response.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    UserDTO save(CreateUserDTO dto);
    UserDTO update(Long id, CreateUserDTO dto);
    void delete(Long id);
    Optional<UserDTO> getById(Long id);
    List<UserDTO> getAll();
}
