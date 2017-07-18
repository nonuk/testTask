package com.filterContact.controller;

import com.filterContact.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

/**
 * Controller
 */
@Transactional
@RestController
@RequestMapping("/hello/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    /**
     * Get array of Contacts with applying filter
     * @param filter request param
     * @return JSON Array of Contacts and Http status
     * @see ContactService#getFilteredContacts(Pattern, Page)
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getContacts(@RequestParam(name = "filterName") String filter) {
        return new ResponseEntity<>(contactService.getContactsByFilter(filter), HttpStatus.ACCEPTED);
    }

    /**
     * Create and save Contact to database
     * @param name - Name of new Contact
     * @return JSON object of created Contact
     * @see ContactService#create(String)
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createContact(@RequestBody String name) {
        return new ResponseEntity<>(contactService.create(name), HttpStatus.CREATED);
    }

    /**
     * Delete Contact by identifiable
     * @param id - Contact id
     * @return {@link HttpStatus#OK} if deleted or {@link HttpStatus#NOT_FOUND} if throw exception {@link ClassNotFoundException}
     */
    @RequestMapping(value = "/{id}" ,method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteContact(@PathVariable(value = "id") Integer id) {
        if (contactService.delete(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
