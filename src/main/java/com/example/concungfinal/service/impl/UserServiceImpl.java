package com.example.concungfinal.service.impl;

import com.example.concungfinal.dto.RoleDto;
import com.example.concungfinal.dto.UserDto;
import com.example.concungfinal.entity.Role;
import com.example.concungfinal.entity.User;
import com.example.concungfinal.repository.UserRepository;
import com.example.concungfinal.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepositoty;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepositoty, ModelMapper modelMapper) {
        this.userRepositoty = userRepositoty;
        this.modelMapper = modelMapper;
    }

    @Override
    public Iterable<UserDto> findAll() {
        Iterable<User> users = userRepositoty.findByActivated(true);
        return StreamSupport.stream(users.spliterator(), true)
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDto> findById(Long id) {
        Optional<User> user = userRepositoty.findById(id);
        return user.map(u -> modelMapper.map(u, UserDto.class));
    }


    @Override
    public void save(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        if (!userDto.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(10));
            user.setPassword(hashedPassword);
        }
        userRepositoty.save(user);
    }

    @Override
    public void remove(Long id) {
        User user = userRepositoty.findById(id).get();
        user.setActivated(false);;
        userRepositoty.save(user);
    }


    @Override
    public Optional<UserDto> findByUsername(String username) {
        Optional<User> user = userRepositoty.findByUsername(username);
        return user.map(u -> modelMapper.map(u, UserDto.class));
    }

    @Override
    public Iterable<UserDto> findByActivatedAndRole(Boolean isActivated, RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        Iterable<User> users = userRepositoty.findByActivatedAndRole(true, role);
        return StreamSupport.stream(users.spliterator(), true)
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void updatePassword(UserDto userDto) {
        Optional<User> user = userRepositoty.findByUsername(userDto.getUsername());
        if (user.isPresent()){
            String hashedPassword = BCrypt.hashpw(user.get().getPassword(), BCrypt.gensalt(10));
            user.get().setPassword(hashedPassword);
        }
    }

    @Override
    public void updateRole(UserDto userDto) {
        Role role = userDto.getRole();
        User user = userRepositoty.findById(userDto.getId()).get();
        user.setRole(role);
        userRepositoty.save(user);
    }

    @Override
    public Iterable<UserDto> findByFullNameContainingAndActivated(String fullname, Boolean isActivated) {
        Iterable<User> users = userRepositoty.findByFullNameContainingAndActivated(fullname, true);
        return StreamSupport.stream(users.spliterator(), true)
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }
}
