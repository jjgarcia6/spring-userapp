package com.backend.usersapp.backend_usersapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.usersapp.backend_usersapp.models.IUser;
import com.backend.usersapp.backend_usersapp.models.dto.UserDTO;
import com.backend.usersapp.backend_usersapp.models.dto.mapper.DtoMapperUser;
import com.backend.usersapp.backend_usersapp.models.entities.Role;
import com.backend.usersapp.backend_usersapp.models.entities.User;
import com.backend.usersapp.backend_usersapp.models.request.UserRequest;
import com.backend.usersapp.backend_usersapp.repositories.RoleRepository;
import com.backend.usersapp.backend_usersapp.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        List<User> users = (List<User>) userRepository.findAll();

        return users
            .stream()
            .map(u -> DtoMapperUser.builder().setUser(u).build())
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(u -> DtoMapperUser
                .builder()
                .setUser(u)
                .build());
    }

    @Override
    @Transactional
    public UserDTO save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(getRoles(user));
        return DtoMapperUser
                .builder()
                .setUser(userRepository.save(user))
                .build();
    }

    @Override
    @Transactional
    public Optional<UserDTO> update(UserRequest user, Long id) {
        Optional<User> o = userRepository.findById(id);
        User userOptional = null;
        if(o.isPresent()){
            User userDB = o.orElseThrow();
            userDB.setRoles(getRoles(user));
            userDB.setUsername(user.getUsername());
            userDB.setEmail(user.getEmail());
            userOptional = userRepository.save(userDB);
        }
        return Optional.ofNullable(DtoMapperUser
                .builder()
                .setUser(userOptional)
                .build());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    private List<Role> getRoles(IUser user){
        Optional<Role> ou = roleRepository.findByName("ROLE_USER");

        List<Role> roles = new ArrayList<>();
        if (ou.isPresent()) {
            roles.add(ou.orElseThrow());
        }

        if (user.isAdmin()) {
            Optional<Role> oa = roleRepository.findByName("ROLE_ADMIN");
            if (oa.isPresent()) {
                roles.add(oa.orElseThrow());
            }
        }
        return roles;
    }
}
