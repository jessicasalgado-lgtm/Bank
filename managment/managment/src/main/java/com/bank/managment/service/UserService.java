package com.bank.managment.service;

import com.bank.managment.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User update(Long id, User userDetails);
    void delete(Long id);
    Optional<User> getById(Long id);
    List<User> getAll();
}
