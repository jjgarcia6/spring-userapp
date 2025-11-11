package com.backend.usersapp.backend_usersapp.services;

import java.util.List;
import java.util.Optional;

import com.backend.usersapp.backend_usersapp.models.dto.UserDTO;
import com.backend.usersapp.backend_usersapp.models.entities.User;
import com.backend.usersapp.backend_usersapp.models.request.UserRequest;

public interface UserService {

    List<UserDTO> findAll();

    Optional<UserDTO> findById(Long id);

    UserDTO save(User user);

    Optional<UserDTO> update(UserRequest user, Long id);

    void deleteById(Long id);

}
