package com.codegym.fashionshop.service.authenticate;

import com.codegym.fashionshop.entities.AppRole;
/**
 * Service interface for managing roles.
 * Extends {@link IGeneralService} for common CRUD operations on {@link AppRole}.
 * This interface adds specific methods for finding roles by name and updating roles.
 * <p>
 * Author: KhangDV
 */
public interface IRoleService extends IGeneralService<AppRole> {
    /**
     * Finds a role by its name.
     *
     * @param roleName the name of the role to find
     * @return the role with the given name, or null if not found
     */
    AppRole findByRoleName(String roleName);

    /**
     * Updates an existing role.
     *
     * @param role the role entity with updated information
     */
    void update(AppRole role);
}
