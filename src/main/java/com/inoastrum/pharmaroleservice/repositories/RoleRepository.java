package com.inoastrum.pharmaroleservice.repositories;

import com.inoastrum.pharmaroleservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
