
package com.bank.managment.service.impl;

import com.bank.managment.dto.request.CreateUserDTO;
import com.bank.managment.dto.request.UpdateUserDTO;
import com.bank.managment.dto.response.TransactionDTO;
import com.bank.managment.dto.response.UserDTO;
import com.bank.managment.entity.User;
import com.bank.managment.exception.DuplicateException;
import com.bank.managment.exception.NotFoundException;
import com.bank.managment.mapper.UserMapper;
import com.bank.managment.repository.UserRepository;
import com.bank.managment.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(CreateUserDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateException("Ya existe un usuario con el email " + dto.getEmail());
        }
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO update(UpdateUserDTO dto) {
        return userRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setPassword(dto.getPassword());
                    User updated = userRepository.save(existing);
                    return userMapper.toDTO(updated);
                })
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + dto.getId()));
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado con id " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<UserDTO> getById(Long id) {
        return Optional.ofNullable(userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + id)));
    }


    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}


























/*
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO save(CreateUserDTO dto) {
        User user = userMapper.toEntity(dto);
        User saved = userRepository.save(user);
        return userMapper.toDTO(saved);
    }

    @Override
    public UserDTO update(UpdateUserDTO dto) {
        return userRepository.findById(dto.getId())
                .map(existing -> {
                    existing.setName(dto.getName());
                    existing.setEmail(dto.getEmail());
                    existing.setPassword(dto.getPassword());
                    User updated = userRepository.save(existing);
                    return userMapper.toDTO(updated);
                })
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con id " + dto.getId()));


    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id " + id);
        }
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
*/



