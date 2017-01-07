package com.helpshift;

import com.google.inject.Inject;

import com.helpshift.contacts.ContactsManager;
import com.helpshift.contacts.model.Contact;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContactsManagementApplication {

    private final ContactsManager contactsManager;

    @Inject
    public ContactsManagementApplication(ContactsManager contactsManager) {
        this.contactsManager = contactsManager;
    }

    /**
     * Validate input word and send it to ContactsManager.class to do prefix search.
     */
    public void handleContactSearching(String line) throws IOException {
        try {
            String[] prefix = line.trim().split(" ");
            if (prefix.length <= 0) {
                System.out.print("Please enter valid prefix");
            }
            List<Contact> res = contactsManager.searchContacts(prefix[0]);
            res.stream().map(o -> Contact.getDisplayString(o)).forEach(System.out::println);

        } catch (IllegalArgumentException e) {
            System.out.println(
                    String.format("\nError occurred while searching contact. Error message = %s", e.getMessage()));
        }
    }

    /**
     * Validate input and send it to @ContactsManager.class for contact creation.
     */
    public void handleContactCreation(String line) throws IOException {
        String[] input = line.split(" ");
        try {
            if (input.length <= 0) {
                System.out.print("\nPlease enter valid name");
            } else if (input.length == 1) {
                contactsManager.addContact(input[0].trim(), null);
            } else {
                contactsManager.addContact(input[0], input[1].trim());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(
                    String.format("\nError occurred while inserting contact. Error message = %s", e.getMessage()));
        }
    }
}
