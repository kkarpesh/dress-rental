package com.epam.brest.courses.service_rest;

import com.epam.brest.courses.model.Rent;
import com.epam.brest.courses.service_api.RentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.ResolvableType;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Gets rents data from RESTful source in JSON format.
 */
public class RentServiceRest implements RentService {

    /**
     * Default logger for current class.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RentServiceRest.class);

    /**
     * The RESTful source URL.
     */
    private String url;

    /**
     * Synchronous client to perform HTTP request.
     */
    private RestTemplate restTemplate;

    /**
     * Constructs new object with given url and Rest Template object.
     *
     * @param url          url.
     * @param restTemplate res template.
     */
    public RentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    /**
     * Gets all rents from source.
     *
     * @return rents list.
     */
    @Override
    public List<Rent> findAll() {
        LOGGER.debug("Gets all dresses from REST");
        ResolvableType resolvableType =
                ResolvableType.forClassWithGenerics(List.class, Rent.class);
        ParameterizedTypeReference<List<Rent>> refType =
                ParameterizedTypeReference.forType(resolvableType.getType());
        ResponseEntity<List<Rent>> responseEntity =
                restTemplate.exchange(url, HttpMethod.GET,
                        null, refType);
        return responseEntity.getBody();
    }

    /**
     * Gets rent by given ID from source.
     *
     * @param rentId rent Id.
     * @return a Optional description of the rent found.
     */
    @Override
    public Optional<Rent> findById(Integer rentId) {
        LOGGER.debug("Find dress by ID {}", rentId);
        ResponseEntity<Rent> responseEntity =
                restTemplate.getForEntity(url + "/" + rentId, Rent.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    /**
     * Creates new rent.
     *
     * @param rent rent.
     * @return created rent ID.
     */
    @Override
    public Integer create(Rent rent) {
        LOGGER.debug("Create new rent {}", rent);
        ResponseEntity<Integer> responseEntity =
                restTemplate.postForEntity(url, rent, Integer.class);
        return responseEntity.getBody();
    }

    /**
     * Updates an existing rent with a new object.
     *
     * @param rent rent.
     * @return number of updated records in the database.
     */
    @Override
    public Integer update(Rent rent) {
        LOGGER.debug("Update rent {}", rent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Rent> rentHttpEntity = new HttpEntity<>(rent, headers);
        ResponseEntity<Integer> responseEntity =
                restTemplate.exchange(url, HttpMethod.PUT,
                        rentHttpEntity, Integer.class);
        return responseEntity.getBody();
    }

    /**
     * Deletes rent from data source.
     *
     * @param rentId rent.
     * @return number of deleted records in the database.
     */
    @Override
    public Integer delete(Integer rentId) {
        LOGGER.debug("Delete rent with id = {}", rentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Rent> rentHttpEntity = new HttpEntity<>(headers);
        ResponseEntity<Integer> responseEntity =
                restTemplate.exchange(url + "/" + rentId,
                        HttpMethod.DELETE, rentHttpEntity, Integer.class);
        return responseEntity.getBody();
    }

    /**
     * Checks if dress rented for this date.
     *
     * @param rent rent.
     * @return true if dress has already been rented
     * for this date and false if not.
     */
    @Override
    public Boolean hasDressAlreadyBeenRentedForThisDate(Rent rent) {
        LOGGER.debug("is rent exists - {}", rent);
        ResponseEntity<Boolean> responseEntity =
                restTemplate.postForEntity(url + "/isExists",
                        rent, Boolean.class);
        return responseEntity.getBody();
    }
}
