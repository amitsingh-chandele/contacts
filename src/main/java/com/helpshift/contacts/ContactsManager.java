package com.helpshift.contacts;

import com.helpshift.contacts.model.Contact;

import java.util.List;

/**
 * Created by amitsingh.c on 06/01/17.
 */
public interface ContactsManager {
    void addContact(String fistName, String lastName);
    List<Contact> searchContacts(String searchString);
}
