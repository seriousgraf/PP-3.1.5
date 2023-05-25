package ru.kata.spring.security.services;

import org.springframework.stereotype.Service;
import ru.kata.spring.security.models.Role;

import java.util.List;
public interface RoleService {
    public List<Role> findAllRoles();
}