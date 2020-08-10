package com.inoastrum.pharmaroleservice.service;

import com.inoastrum.pharmaroleservice.exceptions.RoleNotFoundException;
import com.inoastrum.pharmaroleservice.repositories.RoleRepository;
import com.inoastrum.pharmaroleservice.web.mappers.RoleMapper;
import com.inoastrum.pharmaroleservice.web.model.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto findRoleDtoById(UUID roleId) {
        return roleMapper.roleToRoleDto(roleRepository.findById(roleId).orElseThrow(RoleNotFoundException::new));
    }
}
