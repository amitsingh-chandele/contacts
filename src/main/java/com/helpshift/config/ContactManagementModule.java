package com.helpshift.config;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import com.helpshift.ContactsManagementApplication;
import com.helpshift.contacts.ContactsManager;
import com.helpshift.contacts.impl.ContactsManagerImpl;
import com.helpshift.contacts.model.Contact;
import com.helpshift.trie.Trie;
import com.helpshift.trie.TrieArrayImpl;

/**
 * Created by amitsingh.c on 06/01/17.
 */
public class ContactManagementModule extends AbstractModule {
    /**
     * Configures a {@link Binder} via the exposed methods.
     */
    @Override
    protected void configure() {
        bind(new TypeLiteral<Trie<Contact>>(){}).to(new TypeLiteral<TrieArrayImpl<Contact>>(){});
        bind(ContactsManager.class).to(ContactsManagerImpl.class).in(Singleton.class);
        bind(ContactsManagementApplication.class).in(Singleton.class);
    }
}
