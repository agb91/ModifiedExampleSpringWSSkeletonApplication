package com.leanstacks.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.model.Country;
import com.leanstacks.ws.repository.CountryRepository;

/**
 * The CountryServiceBean encapsulates all business behaviors operating on the Country entity model.
 * 
 * @author Matt Warman
 */
@Service
public class CountryServiceBean implements CountryService {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CountryServiceBean.class);

    /**
     * The <code>CounterService</code> captures metrics for Spring Actuator.
     */
    @Autowired
    private transient CounterService counterService;

    /**
     * The Spring Data repository for Country entities.
     */
    @Autowired
    private transient CountryRepository countryRepository;

    @Override
    public Collection<Country> findAll() {
        logger.info("> findAll");

        counterService.increment("method.invoked.countryServiceBean.findAll");

        final Collection<Country> countrys = countryRepository.findAll();

        logger.info("< findAll");
        return countrys;
    }

    @Override
    public Country findOne(final Long id) {
        logger.info("> findOne {}", id);

        counterService.increment("method.invoked.countryServiceBean.findOne");

        final Country country = countryRepository.findOne(id);

        logger.info("< findOne {}", id);
        return country;
    }

    @Transactional
    @Override
    public Country create(final Country country) {
        logger.info("> create");

        counterService.increment("method.invoked.countryServiceBean.create");

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (country.getId() != null) {
            logger.error("Attempted to create a Country, but id attribute was not null.");
            logger.info("< create");
            throw new EntityExistsException(
                    "Cannot create new Country with supplied id.  The id attribute must be null to create an entity.");
        }

        final Country savedCountry = countryRepository.save(country);

        logger.info("< create");
        return savedCountry;
    }

    @Transactional
    @Override
    public Country update(final Country country) {
        logger.info("> update {}", country.getId());

        counterService.increment("method.invoked.countryServiceBean.update");

        // Ensure the entity object to be updated exists in the repository to
        // prevent the default behavior of save() which will persist a new
        // entity if the entity matching the id does not exist
        final Country countryToUpdate = findOne(country.getId());
        if (countryToUpdate == null) {
            logger.error("Attempted to update a Country, but the entity does not exist.");
            logger.info("< update {}", country.getId());
            throw new NoResultException("Requested Country not found.");
        }

        countryToUpdate.setName(country.getName());
        countryToUpdate.setCapital(country.getCapital());
        final Country updatedCountry = countryRepository.save(countryToUpdate);

        logger.info("< update {}", country.getId());
        return updatedCountry;
    }

    @Transactional
    @Override
    public void delete(final Long id) {
        logger.info("> delete {}", id);

        counterService.increment("method.invoked.countryServiceBean.delete");

        countryRepository.delete(id);

        logger.info("< delete {}", id);
    }

    @Override
    public void evictCache() {
        logger.info("> evictCache");

        counterService.increment("method.invoked.countryServiceBean.evictCache");

        logger.info("< evictCache");
    }

}
