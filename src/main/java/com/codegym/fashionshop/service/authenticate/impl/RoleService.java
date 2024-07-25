package com.codegym.fashionshop.service.authenticate.impl;

import com.codegym.fashionshop.entities.AppRole;
import com.codegym.fashionshop.repository.authenticate.IRoleRepository;
import com.codegym.fashionshop.service.authenticate.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing {@link AppRole} entities.
 * This class provides methods for CRUD operations related to roles.
 * <p>
 * Author: KhangDV
 */
@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;

    /**
     * Retrieves an {@link AppRole} entity by its role name.
     *
     * @param role The role name to search for.
     * @return The found {@link AppRole} entity or null if not found.
     */
    @Override
    public AppRole findByRoleName(String role) {
        return roleRepository.findByRoleName(role);
    }

    /**
     * Retrieves all {@link AppRole} entities.
     *
     * @return A list of all {@link AppRole} entities.
     */
    @Override
    public List<AppRole> findAll() {
        return roleRepository.findAll();
    }

    /**
     * Retrieves an {@link AppRole} entity by its ID.
     *
     * @param id The ID of the role to retrieve.
     * @return The found {@link AppRole} entity or null if not found.
     */
    @Override
    public AppRole findById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    /**
     * Saves a new {@link AppRole} entity.
     *
     * @param appRole The {@link AppRole} entity to save.
     */
    @Override
    public void save(AppRole appRole) {
        roleRepository.save(appRole.getRoleName());
    }

    /**
     * Updates an existing {@link AppRole} entity.
     *
     * @param appRole The {@link AppRole} entity containing updated details.
     */
    @Override
    public void update(AppRole appRole) {
        roleRepository.update(appRole.getRoleName(), appRole.getRoleId());
    }

    /**
     * Removes an {@link AppRole} entity by its ID.
     *
     * @param id The ID of the role to remove.
     */
    @Override
    public void remove(Long id) {
        roleRepository.delete(id);
    }
}
