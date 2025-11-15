package com.backend.usersapp.backend_usersapp.models.dto.mapper;

import com.backend.usersapp.backend_usersapp.models.dto.UserDTO;
import com.backend.usersapp.backend_usersapp.models.entities.User;

public class DtoMapperUser {

    private User user;

    private DtoMapperUser() {
    }

    public static DtoMapperUser builder() {
        return new DtoMapperUser();
    }

    public DtoMapperUser setUser(User user) {
        this.user = user;
        return this;
    }

    public UserDTO build(){
        if (user == null) {
            throw new RuntimeException("Must happen entity User");
        }
        boolean isAdmin = user.getRoles().stream().anyMatch(r -> "ROLE_ADMIN".equals(r.getName()));
        return new UserDTO(this.user.getId(), this.user.getUsername(), this.user.getEmail(), isAdmin); 
    }

}
