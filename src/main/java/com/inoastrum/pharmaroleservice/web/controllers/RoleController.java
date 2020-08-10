package com.inoastrum.pharmaroleservice.web.controllers;

import com.inoastrum.pharmaroleservice.service.RoleService;
import com.inoastrum.pharmaroleservice.web.model.Permission;
import com.inoastrum.pharmaroleservice.web.model.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
@Slf4j
@RestController
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDto> getRoleById(@PathVariable UUID roleId) {
        return new ResponseEntity<>(roleService.findRoleDtoById(roleId), HttpStatus.OK);
    }

    @GetMapping("/permissions")
    public ResponseEntity<List<Permission>> gerPermissions() {
        return new ResponseEntity<>(List.of(Permission.values()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UUID> createNewRole(@RequestBody @Validated RoleDto roleDto) {
        return new ResponseEntity<>(roleService.createNewRole(roleDto).getId(), HttpStatus.CREATED);
    }

    @PutMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRole(@PathVariable UUID roleId, @RequestBody @Validated RoleDto roleDto) {
        roleService.updateRole(roleId, roleDto);
    }

    @DeleteMapping("/{roleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRole(@PathVariable UUID roleId) {
        roleService.deleteRoleById(roleId);
    }
}
