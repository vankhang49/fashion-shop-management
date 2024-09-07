package com.codegym.fashionshop.repository.authenticate;

import com.codegym.fashionshop.entities.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing AppUser entities.
 * This interface provides methods to interact with the database
 * for CRUD operations and custom queries related to AppUser entities.
 * <p>
 * Author: KhangDV
 */
@Repository
public interface IUserRepository extends JpaRepository<AppUser, Long> {
    /**
     * Checks if an AppUser exists with the given username.
     * <p>
     * @param username the username to check
     * @return true if an AppUser exists with the username, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Finds an AppUser entity by username.
     * <p>
     * @param username the username to search for
     * @return the found AppUser entity or null if not found
     */
    @Query(value = "SELECT user_id, user_name, encrypted_password, user_code, date_create, background_image, avatar, " +
            "full_name, gender, date_of_birth, phone_number, email, address, role_id, account_non_expired, " +
            "account_non_locked, credentials_non_expired, enabled FROM app_user " +
            "WHERE user_name = :username", nativeQuery = true)
    AppUser findByUsername(@Param("username") String username);

    /**
     * Searches for AppUser entities by username, user code, or role name.
     * <p>
     * @param searchContent the search content to match against username or user code
     * @param roleName the search content to match against role name
     * @param pageable      the pagination information
     * @return a Page of AppUser entities matching the search criteria
     */
    @Query(value = "SELECT u.user_id, u.user_name,u.encrypted_password, u.user_code, u.date_create, " +
            "u.background_image, u.avatar, u.full_name, u.gender, u.date_of_birth, u.phone_number, " +
            "u.email, u.address, u.role_id, u.account_non_expired, u.account_non_locked, " +
            "u.credentials_non_expired, u.enabled FROM app_user u JOIN app_role r on u.role_id = r.role_id " +
            "WHERE u.full_name LIKE %:searchContent% " +
            "OR u.user_code LIKE %:searchContent% " +
            "AND r.role_name LIKE %:roleName%", nativeQuery = true)
    Page<AppUser> searchAppUserByFullNameOrUserCodeAndRoleName(@Param("searchContent") String searchContent
            , @Param("roleName") String roleName, Pageable pageable);

    /**
     * Retrieves all AppUser entities.
     * <p>
     * @return a list of all AppUser entities
     */
    @Query(value = "SELECT user_id, user_name, encrypted_password, user_code, date_create, background_image, " +
            "avatar, full_name, gender, date_of_birth, phone_number, email, address, role_id, account_non_expired, " +
            "account_non_locked, credentials_non_expired, enabled FROM app_user", nativeQuery = true)
    List<AppUser> findAll();

    /**
     * Finds an AppUser entity by user ID.
     * <p>
     * @param userId the user ID to search for
     * @return an Optional containing the found AppUser entity or an empty Optional if not found
     */
    @Query(value = "SELECT user_id, user_name, encrypted_password, user_code, date_create, background_image, " +
            "avatar, full_name, gender, date_of_birth, phone_number, email, address, role_id, account_non_expired, " +
            "account_non_locked, credentials_non_expired, enabled FROM app_user WHERE user_id = :userId", nativeQuery = true)
    Optional<AppUser> findById(@Param("userId") Long userId);

    /**
     * Deletes an AppUser entity by user ID.
     * <p>
     * @param userId the user ID to delete
     */
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM app_user WHERE user_id = :userId", nativeQuery = true)
    void deleteById(@Param("userId") Long userId);

    /**
     * Disable an AppUser entity by user ID.
     * <p>
     * @param userId the user ID to disable
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE app_user SET account_non_expired = 0, account_non_locked = 0, credentials_non_expired = 0, " +
            "enabled = 0 WHERE user_id = :userId", nativeQuery = true)
    void disableUser(@Param("userId") Long userId);


    /**
     * Enable an AppUser entity by user ID.
     * <p>
     * @param userId the user ID to enable
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE app_user SET account_non_expired = 1, account_non_locked = 1, credentials_non_expired = 1, " +
            "enabled = 1 WHERE user_id = :userId", nativeQuery = true)
    void enableUser(@Param("userId") Long userId);


    /**
     * Inserts a new AppUser entity into the database.
     * <p>
     * @param username             the username of the new AppUser
     * @param encryptedPassword    the encrypted password of the new AppUser
     * @param userCode             the user code of the new AppUser
     * @param dateCreate           the creation date of the new AppUser
     * @param backgroundImage      the background URL of the new AppUser
     * @param avatar               the avatar URL of the new AppUser
     * @param fullName             the full name of the new AppUser
     * @param gender               the gender of the new AppUser
     * @param dateOfBirth          the date of birth of the new AppUser
     * @param phoneNumber          the phone number of the new AppUser
     * @param email                the email address of the new AppUser
     * @param address              the address of the new AppUser
     * @param roleId               the role ID of the new AppUser
     * @param accountNonExpired    whether the account is non-expired for the new AppUser
     * @param accountNonLocked     whether the account is non-locked for the new AppUser
     * @param credentialsNonExpired whether the credentials are non-expired for the new AppUser
     * @param enabled              whether the account is enabled for the new AppUser
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO app_user (user_name, encrypted_password, user_code, date_create," +
            " background_image, avatar, full_name, gender, date_of_birth, phone_number, email, address, role_id," +
            " account_non_expired, account_non_locked, credentials_non_expired, enabled) " +
            "VALUES (:username, :encryptedPassword, :userCode, :dateCreate, :backgroundImage, :avatar, :fullName," +
            ":gender, :dateOfBirth, :phoneNumber, :email, :address, :roleId," +
            ":accountNonExpired, :accountNonLocked, :credentialsNonExpired, :enabled)", nativeQuery = true)
    void saveUser(@Param("username") String username, @Param("encryptedPassword") String encryptedPassword,
                  @Param("userCode") String userCode, @Param("dateCreate") LocalDate dateCreate,
                  @Param("backgroundImage") String backgroundImage, @Param("avatar") String avatar,
                  @Param("fullName") String fullName, @Param("gender") Integer gender,
                  @Param("dateOfBirth")LocalDate dateOfBirth, @Param("phoneNumber") String phoneNumber,
                  @Param("email") String email, @Param("address") String address, @Param("roleId") Long roleId,
                  @Param("accountNonExpired") Boolean accountNonExpired, @Param("accountNonLocked")Boolean accountNonLocked,
                  @Param("credentialsNonExpired")Boolean credentialsNonExpired, @Param("enabled") Boolean enabled);

    /**
     * Updates an existing AppUser entity in the database.
     * <p>
     * @param username             the updated username of the AppUser
     * @param encryptedPassword    the updated encrypted password of the AppUser
     * @param userCode             the updated user code of the AppUser
     * @param dateCreate           the updated creation date of the AppUser
     * @param backgroundImage      the updated background URL of the AppUser
     * @param avatar               the updated avatar URL of the AppUser
     * @param fullName             the updated full name of the AppUser
     * @param gender               the updated gender of the AppUser
     * @param dateOfBirth          the updated date of birth of the AppUser
     * @param phoneNumber          the updated phone number of the AppUser
     * @param email                the updated email address of the AppUser
     * @param address              the updated address of the AppUser
     * @param roleId               the updated role ID of the AppUser
     * @param accountNonExpired    whether the updated account is non-expired for the AppUser
     * @param accountNonLocked     whether the updated account is non-locked for the AppUser
     * @param credentialsNonExpired whether the updated credentials are non-expired for the AppUser
     * @param enabled              whether the updated account is enabled for the AppUser
     * @param userId               the user ID of the AppUser to update
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE app_user set user_name = :username, encrypted_password = :encryptedPassword," +
            "user_code = :userCode, date_create = :dateCreate, background_image = :backgroundImage, avatar = :avatar, " +
            "full_name = :fullName, gender = :gender, date_of_birth = :dateOfBirth, phone_number = :phoneNumber, " +
            "email = :email, address = :address, role_id = :roleId, account_non_expired = :accountNonExpired," +
            "account_non_locked = :accountNonLocked, credentials_non_expired = :credentialsNonExpired," +
            "enabled = :enabled WHERE user_id = :userId", nativeQuery = true)
    void updateUser(@Param("username") String username, @Param("encryptedPassword") String encryptedPassword,
                    @Param("userCode") String userCode, @Param("dateCreate") LocalDate dateCreate,
                    @Param("backgroundImage") String backgroundImage, @Param("avatar") String avatar,
                    @Param("fullName") String fullName, @Param("gender") Integer gender,
                    @Param("dateOfBirth")LocalDate dateOfBirth, @Param("phoneNumber") String phoneNumber,
                    @Param("email") String email, @Param("address") String address, @Param("roleId") Long roleId,
                    @Param("accountNonExpired") Boolean accountNonExpired, @Param("accountNonLocked")Boolean accountNonLocked,
                    @Param("credentialsNonExpired")Boolean credentialsNonExpired, @Param("enabled") Boolean enabled,
                    @Param("userId") Long userId);

    /**
     * Updates background image and avatar for AppUser entity in the database.
     * <p>
     * @param backgroundImage      the updated background URL of the AppUser
     * @param avatar               the updated avatar URL of the AppUser
     * @param userId               the user ID of the AppUser to update
     */
    @Modifying
    @Transactional
    @Query(value = "UPDATE app_user set background_image = :backgroundImage, avatar = :avatar " +
            "WHERE user_id = :userId", nativeQuery = true)
    void updateAvatarAndBackGroundImage(@Param("backgroundImage") String backgroundImage, @Param("avatar") String avatar,
                                        @Param("userId") Long userId);
}