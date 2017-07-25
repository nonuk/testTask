package com.filterContact.repository;

import com.filterContact.entity.Contact;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Interface of CRUD operation on database, extend {@link PagingAndSortingRepository} to release pagination
 */
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {
}
