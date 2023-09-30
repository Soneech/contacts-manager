package org.soneech.util;


import org.soneech.model.Contact;
import org.soneech.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class ContactValidator implements Validator {
    private final ContactService contactService;

    @Autowired
    public ContactValidator(ContactService contactService) {
        this.contactService = contactService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Contact.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Contact contact = (Contact) target;

        Optional<Contact> foundByEmail = contactService.findByEmail(contact.getEmail());
        if (foundByEmail.isPresent() && contact.getId() != foundByEmail.get().getId())
            errors.rejectValue("email", "", "контакт с таким email уже существует");

        Optional<Contact> foundByPhoneNumber = contactService.findByPhoneNumber(contact.getPhoneNumber());
        if (foundByPhoneNumber.isPresent() && contact.getId() != foundByPhoneNumber.get().getId())
            errors.rejectValue("phoneNumber", "",
                    "контакт с таким номером телефона уже существует");
    }
}
