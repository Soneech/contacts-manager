package org.soneech.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException() {
        super("контакт не найден");
    }
}
