package com.inoastrum.pharmaroleservice.web.controllers;

import com.inoastrum.pharmaroleservice.service.RoleService;
import com.inoastrum.pharmaroleservice.web.model.RoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
