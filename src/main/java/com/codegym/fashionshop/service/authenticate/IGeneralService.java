package com.codegym.fashionshop.service.authenticate;
import java.util.List;

/**
 * Interface for general CRUD operations on entities.
 * This interface defines common methods for interacting with entities such as
 * finding all entities, finding an entity by its ID, saving an entity, and removing an entity.
 * <p>
 * Author: KhangDV
 * <p>
 * @param <T> the type of entity this service works with
 */
public interface IGeneralService<T> {
    /**
     * Retrieves all entities of type T.
     *
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve
     * @return the entity with the given ID, or null if not found
     */
    T findById(Long id);

    /**
     * Saves an entity.
     *
     * @param t the entity to save
     */
    void save(T t);

    /**
     * Removes an entity by its ID.
     *
     * @param id the ID of the entity to remove
     */
    void remove(Long id);
}
