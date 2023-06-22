package com.example.concungfinal.service;

import com.example.concungfinal.dto.RoleDto;
import com.example.concungfinal.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService extends GeneralService<UserDto> {
    Optional<UserDto> findByUsername(String username);
    Iterable<UserDto> findByActivatedAndRole(Boolean isActivated, RoleDto roleDto);
    void updatePassword(UserDto userDto);
    void updateRole(UserDto userDto);
    Iterable<UserDto> findByFullNameContainingAndActivated(String fullname, Boolean isActivated);

}
