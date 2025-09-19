package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateUserDTO;
import com.bank.managment.dto.response.UserDTO;
import com.bank.managment.entity.User;
import com.bank.managment.mapper.UserMapper;
import com.bank.managment.repository.UserRepository;
import com.bank.managment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO save(CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO update(Long id, CreateUserDTO dto) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(dto.getName());
                    user.setEmail(dto.getEmail());
                    user.setPassword(dto.getPassword());
                    return userMapper.toDTO(userRepository.save(user));
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id " + id));
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}


