package com.leanstacks.ws.web.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.leanstacks.ws.AbstractTest;
import com.leanstacks.ws.model.Country;
import com.leanstacks.ws.service.CountryService;

/**
 * <p>
 * Unit tests for the CountryController using mocked business components.
 * </p>
 * <p>
 * These tests utilize Spring's Test Framework to mock objects to simulate interaction with back-end components.
 * Back-end components are mocked and injected into the controller. Verifications are performed ensuring controller
 * behaviors.
 * </p>
 * 
 * @author Matt Warman
 */
@RunWith(SpringRunner.class)
@WebMvcTest(CountryController.class)
public class CountryControllerTest extends AbstractTest {

    /**
     * The base resource URI.
     */
    private static final String RESOURCE_URI = "/api/countries";
    /**
     * The resource single item URI.
     */
    private static final String RESOURCE_ITEM_URI = "/api/countries/{id}";
    /**
     * The resource single item URI with the 'send' action.
     */

    /**
     * A mocked CountryService.
     */
    @MockBean
    private transient CountryService countryService;

    /**
     * A mock servlet environment.
     */
    @Autowired
    private transient MockMvc mvc;

    /**
     * A Jackson ObjectMapper for JSON conversion.
     */
    @Autowired
    private transient ObjectMapper mapper;

    @Override
    public void doBeforeEachTest() {
        // perform test initialization
    }

    @Override
    public void doAfterEachTest() {
        // perform test clean up
    }

    @Test
    @WithMockUser
    public void testGetCountrys() throws Exception {

        // Create some test data
        final Collection<Country> list = getEntityListStubData();

        // Stub the CountryService.findAll method return value
        when(countryService.findAll()).thenReturn(list);

        // Perform the behavior being tested
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_URI).accept(MediaType.APPLICATION_JSON)).andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the CountryService.findAll method was invoked once
        verify(countryService, times(1)).findAll();

        // Perform standard JUnit assertions on the response
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

    }

    @Test
    @WithMockUser
    public void testGetCountry() throws Exception {

        // Create some test data
        final Long id = new Long(1);
        final Country entity = getEntityStubData();

        // Stub the CountryService.findOne method return value
        when(countryService.findOne(id)).thenReturn(entity);

        // Perform the behavior being tested
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_ITEM_URI, id).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the CountryService.findOne method was invoked once
        verify(countryService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));
    }

    @Test
    @WithMockUser
    public void testGetCountryNotFound() throws Exception {

        // Create some test data
        final Long id = Long.MAX_VALUE;

        // Stub the CountryService.findOne method return value
        when(countryService.findOne(id)).thenReturn(null);

        // Perform the behavior being tested
        final MvcResult result = mvc
                .perform(MockMvcRequestBuilders.get(RESOURCE_ITEM_URI, id).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the CountryService.findOne method was invoked once
        verify(countryService, times(1)).findOne(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 404", 404, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", Strings.isNullOrEmpty(content));

    }

    @Test
    @WithMockUser
    public void testCreateCountry() throws Exception {

        // Create some test data
        final Country entity = getEntityStubData();

        // Stub the CountryService.create method return value
        when(countryService.create(any(Country.class))).thenReturn(entity);

        // Perform the behavior being tested
        // final String inputJson = json.mapToJson(entity);
        final String inputJson = mapper.writeValueAsString(entity);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(RESOURCE_URI)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the CountryService.create method was invoked once
        verify(countryService, times(1)).create(any(Country.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 201", 201, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

        // final Country createdEntity = json.mapFromJson(content, Country.class);
        final Country createdEntity = mapper.readValue(content, Country.class);

        Assert.assertNotNull("failure - expected entity not null", createdEntity);
        Assert.assertNotNull("failure - expected id attribute not null", createdEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getName(), createdEntity.getName());
    }

    @Test
    @WithMockUser
    public void testUpdateCountry() throws Exception {

        // Create some test data
        final Country entity = getEntityStubData();
        entity.setName(entity.getName() + " test");
        final Long id = new Long(1);

        // Stub the CountryService.update method return value
        when(countryService.update(any(Country.class))).thenReturn(entity);

        // Perform the behavior being tested
        final String inputJson = mapper.writeValueAsString(entity);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(RESOURCE_ITEM_URI, id)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(inputJson))
                .andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the CountryService.update method was invoked once
        verify(countryService, times(1)).update(any(Country.class));

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 200", 200, status);
        Assert.assertTrue("failure - expected HTTP response body to have a value", !Strings.isNullOrEmpty(content));

        final Country updatedEntity = mapper.readValue(content, Country.class);

        Assert.assertNotNull("failure - expected entity not null", updatedEntity);
        Assert.assertEquals("failure - expected id attribute unchanged", entity.getId(), updatedEntity.getId());
        Assert.assertEquals("failure - expected text attribute match", entity.getName(), updatedEntity.getName());

    }

    @Test
    @WithMockUser
    public void testDeleteCountry() throws Exception {

        // Create some test data
        final Long id = new Long(1);

        // Perform the behavior being tested
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(RESOURCE_ITEM_URI, id)).andReturn();

        // Extract the response status and body
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        // Verify the CountryService.delete method was invoked once
        verify(countryService, times(1)).delete(id);

        // Perform standard JUnit assertions on the test results
        Assert.assertEquals("failure - expected HTTP status 204", 204, status);
        Assert.assertTrue("failure - expected HTTP response body to be empty", Strings.isNullOrEmpty(content));

    }


    private Collection<Country> getEntityListStubData() {
        final Collection<Country> list = new ArrayList<Country>();
        list.add(getEntityStubData());
        return list;
    }

    private Country getEntityStubData() {
        final Country entity = new Country();
        entity.setId(1L);
        entity.setName("Greece");
        entity.setName("Athens");
        return entity;
    }

}
