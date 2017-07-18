package com.filterContact;

import com.filterContact.entity.Contact;
import com.filterContact.service.ContactService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@IntegrationTest
public class FilterContactApplicationTests {

	@Autowired
	private ContactService contactService;

	/**
	 * Check {@link ContactService#getContactsByFilter(String)} method
	 * Check ^A.*$ filter (not start with "A")
     */
	@Test
	public void testGetContactsByFilter_NotStartWithA() {
		List<Contact> contacts = contactService.getContactsByFilter("^A.*$");
		assertThat(contacts.size(), is(3));
		contacts.forEach(contact -> {
			assertFalse(contact.getName().startsWith("A"));
		});
	}

	/**
	 * Check {@link ContactService#getContactsByFilter(String)} method
	 * Check ^.*[aei].*$ filter (not contains the letters "a, e, i")
	 */
	@Test
	public void testGetContactsByFilter_NotContainsLetters() {
		List<Contact> contacts = contactService.getContactsByFilter("^.*[aei].*$");
		assertThat(contacts.size(), is(3));
		contacts.forEach(contact -> {
			assertFalse(contact.getName().contains("a"));
			assertFalse(contact.getName().contains("e"));
			assertFalse(contact.getName().contains("i"));
		});
	}
}
