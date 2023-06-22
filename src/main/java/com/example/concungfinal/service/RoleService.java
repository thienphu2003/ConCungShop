package com.example.concungfinal.service;

import com.example.concungfinal.dto.RoleDto;
import com.example.concungfinal.entity.RoleName;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RoleService extends GeneralService<RoleDto> {
    Optional<RoleDto> findByName(RoleName name);
}
