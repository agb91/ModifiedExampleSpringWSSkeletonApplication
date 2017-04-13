package com.leanstacks.ws.service;

import java.util.Collection;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.leanstacks.ws.AbstractTest;
import com.leanstacks.ws.model.Country;

/**
 * Unit test methods for the CountryService and CountryServiceBean.
 * 
 * @author Matt Warman
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CountryServiceTest extends AbstractTest {

    private static final String VALUE_NAME = "Germany";
    private static final String VALUE_CAPITAL = "Berlin";

    @Autowired
    private transient CountryService countryService;

    @Override
    public void doBeforeEachTest() {
        countryService.evictCache();
    }

    @Override
    public void doAfterEachTest() {
        // perform test clean up
    }

    @Test
    public void testGetCountrys() {

        final Collection<Country> countries = countryService.findAll();

        Assert.assertNotNull("failure - expected not null", countries);
        Assert.assertEquals("failure - expected 2 countries", 2, countries.size());

    }

    @Test
    public void testGetCountry() {

        final Long id = new Long(1);

        final Country country = countryService.findOne(id);

        Assert.assertNotNull("failure - expected not null", country);
        Assert.assertEquals("failure - expected country.id match", id, country.getId());

    }

    @Test
    public void testGetCountryNotFound() {

        final Long id = Long.MAX_VALUE;

        final Country country = countryService.findOne(id);

        Assert.assertNull("failure - expected null", country);

    }

    @Test
    public void testCreateCountry() {

        final Country country = new Country();
        country.setName(VALUE_NAME);
        country.setCapital(VALUE_CAPITAL);

        final Country createdCountry = countryService.create(country);

        Assert.assertNotNull("failure - expected country not null", createdCountry);
        Assert.assertNotNull("failure - expected country.id not null", createdCountry.getId());
        Assert.assertEquals("failure - expected country.text match", VALUE_NAME, createdCountry.getName());

        final Collection<Country> countries = countryService.findAll();

        Assert.assertEquals("failure - expected 3 countries", 3, countries.size());

    }

    @Test
    public void testCreateCountryWithId() {

        Exception exception = null;

        final Country country = new Country();
        country.setId(Long.MAX_VALUE);
        country.setName(VALUE_NAME);

        try {
            countryService.create(country);
        } catch (EntityExistsException eee) {
            exception = eee;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected EntityExistsException", exception instanceof EntityExistsException);

    }

    @Test
    public void testUpdateCountry() {

        final Long id = new Long(1);

        final Country country = countryService.findOne(id);

        Assert.assertNotNull("failure - expected country not null", country);

        final String updatedText = country.getName() + " test";
        country.setName(updatedText);
        final Country updatedCountry = countryService.update(country);

        Assert.assertNotNull("failure - expected updated country not null", updatedCountry);
        Assert.assertEquals("failure - expected updated country id unchanged", id, updatedCountry.getId());
        Assert.assertEquals("failure - expected updated country text match", updatedText, updatedCountry.getName());

    }

    @Test
    public void testUpdateCountryNotFound() {

        Exception exception = null;

        final Country country = new Country();
        country.setId(Long.MAX_VALUE);
        country.setName("test");

        try {
            countryService.update(country);
        } catch (NoResultException nre) {
            exception = nre;
        }

        Assert.assertNotNull("failure - expected exception", exception);
        Assert.assertTrue("failure - expected NoResultException", exception instanceof NoResultException);

    }

    @Test
    public void testDeleteCountry() {

        final Long id = new Long(1);

        final Country country = countryService.findOne(id);

        Assert.assertNotNull("failure - expected country not null", country);

        countryService.delete(id);

        final Collection<Country> countries = countryService.findAll();

        Assert.assertEquals("failure - expected 1 country", 1, countries.size());

        final Country deletedCountry = countryService.findOne(id);

        Assert.assertNull("failure - expected country to be deleted", deletedCountry);

    }

}
