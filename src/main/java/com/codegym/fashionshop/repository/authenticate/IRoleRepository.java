package com.codegym.fashionshop.repository.authenticate;

import com.codegym.fashionshop.entities.AppRole;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing AppRole entities.
 * This interface provides methods to interact with the database
 * for CRUD operations and custom queries related to AppRole entities.
 * <p>
 * Author: KhangDV
 */
public interface IRoleRepository extends JpaRepository<AppRole, Long> {
    /**
     * Finds an AppRole entity by role name.
     *
     * @param roleName the role name to search for
     * @return the found AppRole entity or null if not found
     */
    @Query(value = "SELECT role_id, role_name FROM app_role WHERE role_name = :roleName", nativeQuery = true)
    AppRole findByRoleName(@Param("roleName") String roleName);

    /**
     * Retrieves all AppRole entities.
     *
     * @return a list of all AppRole entities
     */
    @Query(value = "SELECT role_id, role_name FROM app_role", nativeQuery = true)
    List<AppRole> findAll();


    /**
     * Finds an AppRole entity by role ID.
     *
     * @param roleId the role ID to search for
     * @return an Optional containing the found AppRole entity or an empty Optional if not found
     */
    @Query(value = "SELECT role_id, role_name FROM app_role WHERE role_id = :roleId", nativeQuery = true)
    Optional<AppRole> findById(@Param("roleId") Long roleId);

    /**
     * Saves a new AppRole entity into the database.
     *
     * @param roleName the role name of the new AppRole
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO app_role (role_name) VALUES (:roleName)", nativeQuery = true)
    void save(@Param("roleName") String roleName);

    /**
     * Updates an existing AppRole entity in the database.
     *
     * @param roleName the updated role name of the AppRole
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE app_role SET role_name = :roleName WHERE role_id = :roleId", nativeQuery = true)
    void update(@Param("roleName") String roleName, @Param("roleId") Long roleId);

    /**
     * Deletes an AppRole entity by role ID.
     *
     * @param roleId the role ID to delete
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_role WHERE role_id = :roleId", nativeQuery = true)
    void delete(@Param("roleId") Long roleId);
}
