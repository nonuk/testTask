package com.filterContact.controller;

import com.filterContact.service.ContactService;
import com.filterContact.service.ContactsDTO;
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
    public ResponseEntity<ContactsDTO> getContacts(@RequestParam(name = "nameFilter") String filter) {
        return new ResponseEntity<>(contactService.getContactsByFilter(filter), HttpStatus.OK);
    }
}
