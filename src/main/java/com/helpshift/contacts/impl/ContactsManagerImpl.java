package com.helpshift.contacts.impl;

import com.google.inject.Inject;

import com.helpshift.contacts.ContactsManager;
import com.helpshift.contacts.model.Contact;
import com.helpshift.trie.Trie;
import com.helpshift.utils.Pair;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by amitsingh.c on 06/01/17.
 */
@Slf4j
public class ContactsManagerImpl implements ContactsManager {

    private final Trie<Contact> firstNameTrie;
    private final Trie<Contact> lastNameTrie;

    @Inject
    public ContactsManagerImpl(Trie<Contact> firstNameTrie, Trie<Contact> lastNameTrie) {
        this.firstNameTrie = firstNameTrie;
        this.lastNameTrie = lastNameTrie;
    }

    /**
     * Validates input, check if duplicate request else creates a new contact.
     * @param fistName
     * @param lastName
     */
    @Override
    public void addContact(String fistName, String lastName) {
        log.debug("Validate contact.");
        Validate.isTrue(!StringUtils.isEmpty(fistName) || !StringUtils.isEmpty(lastName),
                "Both first name and last name can not be empty.");

        Contact contact = new Contact(fistName, lastName);

        log.debug("Verify if contact already exists.");
        Validate.isTrue(!isDuplicateContact(contact), "Duplicate contact detected.");


        log.debug("Creating new contact {}", contact);
        if (!StringUtils.isEmpty(contact.getFirstName())) {
            log.debug("Inserting contact in  firstNameTrie store");
            firstNameTrie.insertWord(contact.getFirstName(), contact);
        }

        if (!StringUtils.isEmpty(contact.getLastName())) {
            log.debug("Inserting contact in  lastNameTrie store");
            lastNameTrie.insertWord(contact.getLastName(), contact);
        }
        log.info("Created contact {} successfully", contact);
    }

    private boolean isDuplicateContact(Contact contact) {
        Set<Contact> matchedContacts = firstNameTrie.getDataListForWordExactMatch(contact.getFirstName());
        matchedContacts.addAll(lastNameTrie.getDataListForWordExactMatch(contact.getLastName()));
        if (matchedContacts.size() > 0 && matchedContacts.stream().filter(o -> o.equals(contact)).count() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 1. Validates input
     * 2. queries firstNameTrie data store and gets set of matches.
     * 3. queries lastNameTrie data store and gets set of matches.
     * 4. Combines the results of the two result set and ranks them on the basis of exact match first.
     *
     * @param searchString
     * @return
     */
    @Override
    public List<Contact> searchContacts(String searchString) {
        List<Contact> prefixMatchedContacts = new ArrayList<>();
        if (!StringUtils.isEmpty(searchString)) {
            Set<Pair<Contact, Boolean>> firstNameMatch = firstNameTrie.getDataListForWordStartsWith(searchString);
            Set<Contact> firstNameMatchContact = firstNameMatch.stream().map(o -> o.getLeft()).collect(Collectors.toSet());

            Set<Pair<Contact, Boolean>> lastNameMatch = lastNameTrie.getDataListForWordStartsWith(searchString);
            Set<Pair<Contact, Boolean>> lastNameMatchFiltered = lastNameMatch.stream().filter(o ->
                    !firstNameMatchContact.contains(o.getLeft())).collect(Collectors.toSet());
            firstNameMatch.addAll(lastNameMatchFiltered);
            prefixMatchedContacts = firstNameMatch.stream()
                    .sorted((o1,o2) -> o1.getRight()==o2.getRight() ? 0 : (o1.getRight() ? -1:1))
                    .map(o->o.getLeft())
                    .collect(Collectors.toList());
        }
        return prefixMatchedContacts;
    }
}
