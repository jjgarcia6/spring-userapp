package com.backend.usersapp.backend_usersapp.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.backend.usersapp.backend_usersapp.models.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Page<User> findAll(Pageable pageable);
}
