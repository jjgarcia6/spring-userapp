package com.backend.usersapp.backend_usersapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.backend.usersapp.backend_usersapp.models.dto.UserDTO;
import com.backend.usersapp.backend_usersapp.models.entities.User;
import com.backend.usersapp.backend_usersapp.models.request.UserRequest;

public interface UserService {

    List<UserDTO> findAll();

    Page<UserDTO> findAll(Pageable pageable);

    Optional<UserDTO> findById(Long id);

    UserDTO save(User user);

    Optional<UserDTO> update(UserRequest user, Long id);

    void deleteById(Long id);

}
