package com.helpshift;

import com.google.inject.Guice;
import com.google.inject.Injector;

import com.helpshift.config.ContactManagementModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ContactsManagementApplicationDriver {

    public static void main(String arr[]) throws IOException {
        Injector injector = Guice.createInjector(new ContactManagementModule());
        ContactsManagementApplication contactsApp = injector.getInstance(ContactsManagementApplication.class);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.println("1) Add contact 2) Search 3) Exit");
            int option = getValidOption(br);
            switch (option) {
                case 1:
                    System.out.print("\nEnter name:");
                    contactsApp.handleContactCreation(br.readLine());
                    System.out.println("\n============================\n");
                    break;
                case 2:
                    System.out.print("\nEnter name:");
                    contactsApp.handleContactSearching(br.readLine());
                    System.out.println("\n============================\n");
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.print("\nInvalid option entered. Please try again");
                    System.out.println("\n============================\n");
            }
        }
    }


    private static int getValidOption(BufferedReader br) throws IOException {
        try {
            return Integer.parseInt(br.readLine());
        } catch (NumberFormatException e) {
            return 99;
        }

    }
}
