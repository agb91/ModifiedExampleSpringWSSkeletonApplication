package com.leanstacks.ws.web.api;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.leanstacks.ws.model.Country;
import com.leanstacks.ws.service.CountryService;

/**
 * The CountryController class is a RESTful web service controller. The <code>@RestController</code> annotation informs
 * Spring that each <code>@RequestMapping</code> method returns a <code>@ResponseBody</code>.
 * 
 * @author Matt Warman
 */
@RestController
public class CountryController {

    /**
     * The Logger for this Class.
     */
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    /**
     * The CountryService business service.
     */
    @Autowired
    private transient CountryService countryService;

    /**
     * Web service endpoint to fetch all Country entities. The service returns the collection of Country entities as
     * JSON.
     * 
     * @return A ResponseEntity containing a Collection of Country objects.
     */
    @RequestMapping(value = "/api/countries",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Country>> getCountries() {
        logger.info("> getCountrys");

        final Collection<Country> countries = countryService.findAll();

        logger.info("< getCountrys");
        return new ResponseEntity<Collection<Country>>(countries, HttpStatus.OK);
    }

    /**
     * <p>
     * Web service endpoint to fetch a single Country entity by primary key identifier.
     * </p>
     * <p>
     * If found, the Country is returned as JSON with HTTP status 200. If not found, the service returns an empty
     * response body with HTTP status 404.
     * </p>
     * 
     * @param id A Long URL path variable containing the Country primary key identifier.
     * @return A ResponseEntity containing a single Country object, if found, and a HTTP status code as described in
     *         the method comment.
     */
    @RequestMapping(value = "/api/countries/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> getCountry(@PathVariable final Long id) {
        logger.info("> getCountry");

        final Country country = countryService.findOne(id);
        if (country == null) {
            logger.info("< getCountry");
            return new ResponseEntity<Country>(HttpStatus.NOT_FOUND);
        }

        logger.info("< getCountry");
        return new ResponseEntity<Country>(country, HttpStatus.OK);
    }

    /**
     * <p>
     * Web service endpoint to create a single Country entity. The HTTP request body is expected to contain a Country
     * object in JSON format. The Country is persisted in the data repository.
     * </p>
     * <p>
     * If created successfully, the persisted Country is returned as JSON with HTTP status 201. If not created
     * successfully, the service returns an empty response body with HTTP status 500.
     * </p>
     * 
     * @param country The Country object to be created.
     * @return A ResponseEntity containing a single Country object, if created successfully, and a HTTP status code as
     *         described in the method comment.
     */
    @RequestMapping(value = "/api/countries",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> createCountry(@RequestBody final Country country) {
        logger.info("> createCountry");

        final Country savedCountry = countryService.create(country);

        logger.info("< createCountry");
        return new ResponseEntity<Country>(savedCountry, HttpStatus.CREATED);
    }

    /**
     * <p>
     * Web service endpoint to update a single Country entity. The HTTP request body is expected to contain a Country
     * object in JSON format. The Country is updated in the data repository.
     * </p>
     * <p>
     * If updated successfully, the persisted Country is returned as JSON with HTTP status 200. If not found, the
     * service returns an empty response body and HTTP status 404. If not updated successfully, the service returns an
     * empty response body with HTTP status 500.
     * </p>
     * 
     * @param country The Country object to be updated.
     * @return A ResponseEntity containing a single Country object, if updated successfully, and a HTTP status code as
     *         described in the method comment.
     */
    @RequestMapping(value = "/api/countries/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Country> updateCountry(@PathVariable("id") final Long id,
            @RequestBody final Country country) {
        logger.info("> updateCountry");

        country.setId(id);

        final Country updatedCountry = countryService.update(country);

        logger.info("< updateCountry");
        return new ResponseEntity<Country>(updatedCountry, HttpStatus.OK);
    }

    /**
     * <p>
     * Web service endpoint to delete a single Country entity. The HTTP request body is empty. The primary key
     * identifier of the Country to be deleted is supplied in the URL as a path variable.
     * </p>
     * <p>
     * If deleted successfully, the service returns an empty response body with HTTP status 204. If not deleted
     * successfully, the service returns an empty response body with HTTP status 500.
     * </p>
     * 
     * @param id A Long URL path variable containing the Country primary key identifier.
     * @return A ResponseEntity with an empty response body and a HTTP status code as described in the method comment.
     */
    @RequestMapping(value = "/api/countries/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Country> deleteCountry(@PathVariable("id") final Long id) {
        logger.info("> deleteCountry");

        countryService.delete(id);

        logger.info("< deleteCountry");
        return new ResponseEntity<Country>(HttpStatus.NO_CONTENT);
    }

}
