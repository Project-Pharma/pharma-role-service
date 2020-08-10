package com.inoastrum.pharmaroleservice.service;

import com.inoastrum.pharmaroleservice.web.model.RoleDto;

import java.util.UUID;

public interface RoleService {
    RoleDto findRoleDtoById(UUID roleId);

    RoleDto createNewRole(RoleDto roleDto);

    void updateRole(UUID roleId, RoleDto roleDto);

    void deleteRoleById(UUID roleId);
}
