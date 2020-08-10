package com.inoastrum.pharmaroleservice.web.mappers;

import com.inoastrum.pharmaroleservice.domain.Role;
import com.inoastrum.pharmaroleservice.web.model.RoleDto;
import org.mapstruct.Mapper;


@Mapper(uses = {DateMapper.class})
public interface RoleMapper {

    RoleDto roleToRoleDto(Role role);

    Role roleDtoToRole(RoleDto roleDto);
}
