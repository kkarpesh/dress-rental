package com.epam.brest.courses.service_api;

import com.epam.brest.courses.model.Dress;

import java.util.List;
import java.util.Optional;

/**
 * A service interface that defines the methods
 * of working with the Dress model.
 *
 * @author Kirill Karpesh
 * @version 1.0
 * @since 1.0
 */
public interface DressService {

    /**
     * Finds all dresses.
     *
     * @return dresses list.
     */
    List<Dress> findAll();

    /**
     * Finds dress by Id.
     *
     * @param dressId dress Id.
     * @return dress.
     */
    Optional<Dress> findById(Integer dressId);

    /**
     * Creates new dress.
     *
     * @param dress dress.
     * @return created dress Id.
     */
    Integer create(Dress dress);

    /**
     * Updates dress.
     *
     * @param dress dress.
     * @return number of updated records in the database.
     */
    Integer update(Dress dress);

    /**
     * Deletes dress.
     *
     * @param dressId dress Id.
     * @return number of deleted records in the database.
     */
    Integer delete(Integer dressId);

    /**
     * Checks if the name of the dress is already exist.
     *
     * @param dress dress.
     * @return the boolean value of the existence of a name.
     */
    Boolean isNameAlreadyExist(Dress dress);

    /**
     * Checks if the dress with a given ID has orders.
     *
     * @param dressId dress ID.
     * @return the boolean value is there a dress orders.
     */
    Boolean isDressHasRents(Integer dressId);
}
