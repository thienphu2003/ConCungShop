package com.example.concungfinal.service.impl;

import com.example.concungfinal.dto.RoleDto;
import com.example.concungfinal.entity.Role;
import com.example.concungfinal.entity.RoleName;
import com.example.concungfinal.repository.RoleRepository;
import com.example.concungfinal.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Optional<RoleDto> findByName(RoleName name) {
        Role role = roleRepository.findByName(name).get();
        return Optional.ofNullable(modelMapper.map(role, RoleDto.class));
    }

    @Override
    public Iterable<RoleDto> findAll() {
        Iterable<Role> roles = roleRepository.findAll();
        return StreamSupport.stream(roles.spliterator(), true)
                .map(role -> modelMapper.map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(RoleDto roleDto) {

    }

    @Override
    public void remove(Long id) {

    }

}
