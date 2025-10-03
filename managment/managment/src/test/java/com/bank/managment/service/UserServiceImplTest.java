package com.bank.managment.service;

import com.bank.managment.dto.request.CreateUserDTO;
import com.bank.managment.dto.response.UserDTO;
import com.bank.managment.entity.User;
import com.bank.managment.exception.NotFoundException;
import com.bank.managment.mapper.UserMapper;
import com.bank.managment.repository.UserRepository;
import com.bank.managment.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUser() {

        CreateUserDTO createUserDTO = new CreateUserDTO("Jeka", "jeka@email.com", "123456");

        User userEntity = new User();
        userEntity.setName("Jeka");
        userEntity.setEmail("jeka@email.com");
        userEntity.setPassword("123456");

        User savedUserEntity = new User();
        savedUserEntity.setName("Jeka");
        savedUserEntity.setEmail("jeka@email.com");
        savedUserEntity.setPassword("123456");


        try {
            Field idField = User.class.getDeclaredField("idUser");
            idField.setAccessible(true);
            idField.set(savedUserEntity, 1L);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        UserDTO expectedDTO = new UserDTO(1L, "Jeka", "salgado@gmail.com");

        when(userMapper.toEntity(createUserDTO)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUserEntity);
        when(userMapper.toDTO(savedUserEntity)).thenReturn(expectedDTO);

        UserDTO result = userService.save(createUserDTO);

        assertNotNull(result);
        assertEquals(1L, result.getIdUser());
        assertEquals("Jeka", result.getName());
    }



    @Test
    void testGetById_notFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(99L));
    }

    @Test
    void testDelete_success() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.delete(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_notFound() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> userService.delete(99L));
    }

    @Test
    void testGetAll_success() {
        User user = new User();
        user.setIdUser(1L);
        user.setName("Carlos");
        user.setEmail("carlos@gmail.com");

        UserDTO dto = new UserDTO(1L, "Carlos", "carlos@gmail.com");

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toDTO(user)).thenReturn(dto);

        List<UserDTO> result = userService.getAll();

    }
}

