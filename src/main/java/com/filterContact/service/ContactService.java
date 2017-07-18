package com.filterContact.service;

import com.filterContact.entity.Contact;
import com.filterContact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    /**
     * Create and save to database Contact entity
     * @param name not null name of Contact
     * @return saved entity from database
     */
    public Contact create(String name) {
        // Save entity Contact to database
        return contactRepository.save(new Contact(name));
    }

    /**
     * Get partially all Contacts from DB and apply filter in {@link ContactService#getFilteredContacts(Pattern, Page)}
     * @param filterName regexp filter to contacts
     * @return Array of Contacts
     */
    public List<Contact> getContactsByFilter(String filterName) {
        // Compile pattern for regexp "filterName"
        Pattern pattern = Pattern.compile(filterName);
        // Create first pageable with limit 100 records
        Pageable pageable = new PageRequest(0,100);
        // Get Page of contacts from database with pagination
        Page<Contact> page = getAll(pageable);
        //Get List of Contacts for first page with applying filter
        List<Contact> contacts = getFilteredContacts(pattern, page);
        //if we have more than one page, we should apply filter for all page and add result to contacts
        while (page.hasNext()) {
            page = getAll(page.nextPageable());
            contacts.addAll(getFilteredContacts(pattern, page));
        }
        return contacts;
    }

    /**
     * Get array Contacts from Page with applying filter
     * @param pattern {@link Pattern} of request filter
     * @param contactPage {@link Page} of Contact from database with limit records
     * @return Array of Contact
     */
    private List<Contact> getFilteredContacts(Pattern pattern, Page<Contact> contactPage) {
        return contactPage.getContent().parallelStream().filter(contact -> !pattern.matcher(contact.getName()).matches()).collect(Collectors.toList());
    }

    /**
     * Get Page of Contacts from database with limited records
     * @param pageable - which page of Contact should be taken from database
     * @return Page of Contact
     */
    private Page<Contact> getAll(Pageable pageable) {
        return contactRepository.findAll(pageable);
    }

    /**
     * Delete Contact from database
     * @param id - Contact id
     */
    public Contact delete(Integer id) {
        // Get Contact by id
        Contact contact = contactRepository.findOne(id);
        // Check for exist searched contact
        if (contact == null) {
            return null;
        }
        // delete Contact from database
        contactRepository.delete(contact);
        return contact;
    }
}
