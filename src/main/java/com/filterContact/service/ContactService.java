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
     * Get partially all Contacts from DB and apply filter in {@link ContactService#getFilteredContacts(Pattern, Page)}
     * @param filterName regexp filter to contacts
     * @return Array of Contacts
     */
    public ContactsDTO getContactsByFilter(String filterName) {
        Pattern pattern = Pattern.compile(filterName);
        Pageable pageable = new PageRequest(0,100);
        Page<Contact> page = getAll(pageable);
        ContactsDTO contactsDTO = new ContactsDTO(getFilteredContacts(pattern, page));
        while (page.hasNext()) {
            page = getAll(page.nextPageable());
            contactsDTO.getContacts().addAll(getFilteredContacts(pattern, page));
        }
        return contactsDTO;
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
}
