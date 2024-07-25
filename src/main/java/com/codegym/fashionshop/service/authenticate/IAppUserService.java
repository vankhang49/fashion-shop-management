package com.codegym.fashionshop.service.authenticate;


import com.codegym.fashionshop.dto.request.AppUserRequest;
import com.codegym.fashionshop.dto.respone.AuthenticationResponse;
import com.codegym.fashionshop.entities.AppUser;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing application users.
 * Extends {@link IGeneralService} for common CRUD operations on {@link AppUser}.
 * This interface adds specific methods for checking user existence by username,
 * finding users by username, searching users by various criteria, and saving users.
 * <p>
 * Author: KhangDV
 */
public interface IAppUserService extends IGeneralService<AppUser>{
    /**
     * Checks if a user exists by their username.
     *
     * @param username the username to check
     * @return true if a user with the username exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Finds a user by their username.
     *
     * @param username the username of the user to find
     * @return the user with the given username, or null if not found
     */
    AppUser findByUsername(String username);

    /**
     * Searches for users by username, user code, or role name using pagination.
     *
     * @param searchContent the search content to match against usernames, user codes, and role names
     * @param pageable      the pagination information for retrieving search results
     * @return an {@link AuthenticationResponse} containing the search results
     */
    AuthenticationResponse searchAllByFullNameOrUserCodeOrRoleName(String searchContent, Pageable pageable);

    /**
     * Saves a new user based on the provided {@link AppUserRequest}.
     *
     * @param appUserRequest the request object containing user details to be saved
     * @return an {@link AuthenticationResponse} indicating the outcome of the save operation
     */
    AuthenticationResponse save(AppUserRequest appUserRequest);

    /**
     * Disable an entity by its ID.
     *
     * @param id the ID of the entity to disable
     */
    void disableUser(Long id);

    /**
     * Enable an entity by its ID.
     *
     * @param id the ID of the entity to enable
     */
    void enableUser(Long id);
}
