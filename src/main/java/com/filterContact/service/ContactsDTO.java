package com.filterContact.service;

import com.filterContact.entity.Contact;
import lombok.Data;

import java.util.List;

@Data
public class ContactsDTO {
    private List<Contact> contacts;

    public ContactsDTO(List<Contact> contacts) {
        this.contacts = contacts;
    }
}
