package com.leanstacks.ws.service;

import java.util.Collection;

import com.leanstacks.ws.model.Country;

/**
 * <p>
 * The AccountService interface defines all public business behaviors for operations on the Account entity model and
 * some related entities such as Role.
 * </p>
 * <p>
 * This interface should be injected into AccountService clients, not the implementation bean.
 * </p>
 * 
 * @author Matt Warman
 */
public interface CountryService {
	/**
     * Find all Country entities.
     * 
     * @return A Collection of Country objects.
     */
    Collection<Country> findAll();

    /**
     * Find a single Country entity by primary key identifier.
     * 
     * @param id A BigInteger primary key identifier.
     * @return A Country or <code>null</code> if none found.
     */
    Country findOne(Long id);

    /**
     * Persists a Country entity in the data store.
     * 
     * @param country A Country object to be persisted.
     * @return A persisted Country object or <code>null</code> if a problem occurred.
     */
    Country create(Country country);

    /**
     * Updates a previously persisted Country entity in the data store.
     * 
     * @param country A Country object to be updated.
     * @return An updated Country object or <code>null</code> if a problem occurred.
     */
    Country update(Country country);

    /**
     * Removes a previously persisted Country entity from the data store.
     * 
     * @param id A BigInteger primary key identifier.
     */
    void delete(Long id);

    /**
     * Evicts all members of the "countrys" cache.
     */
    void evictCache();

}
