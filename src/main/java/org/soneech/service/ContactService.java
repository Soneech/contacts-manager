package org.soneech.service;

import org.modelmapper.ModelMapper;
import org.soneech.dto.ContactDTO;
import org.soneech.exception.ContactNotFoundException;
import org.soneech.model.Contact;
import org.soneech.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ContactService {
    private final ContactRepository contactRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ContactService(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<Contact> findById(Long id) {
        Optional<Contact> foundContact = contactRepository.findById(id);
        if (foundContact.isEmpty())
            throw new ContactNotFoundException();
        return foundContact;
    }

    public Optional<Contact> findByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    public Optional<Contact> findByPhoneNumber(String phoneNumber) {
        return contactRepository.findByPhoneNumber(phoneNumber);
    }

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    @Transactional
    public Contact save(Contact contact) {
        contact.setCreatedAt(LocalDateTime.now());
        return contactRepository.save(contact);
    }

    @Transactional
    public Contact update(Long id, Contact contact) {
        Optional<Contact> foundContact = contactRepository.findById(id);
        if (foundContact.isEmpty())
            throw new ContactNotFoundException();

        contact.setId(id);
        contact.setCreatedAt(foundContact.get().getCreatedAt());
        return contactRepository.save(contact);
    }

    @Transactional
    public void delete(Long id) {
        Optional<Contact> foundContact = contactRepository.findById(id);
        if (foundContact.isEmpty())
            throw new ContactNotFoundException();

        contactRepository.delete(foundContact.get());
    }

    public Contact convertToContact(ContactDTO contactDTO) {
        if (contactDTO != null)
            return modelMapper.map(contactDTO, Contact.class);
        return null;
    }

    public Contact convertToContactWithId(ContactDTO contactDTO, Long id) {
        if (contactDTO != null) {
            Contact contact = modelMapper.map(contactDTO, Contact.class);
            contact.setId(id);
            return contact;
        }
        return null;
    }

    public ContactDTO convertToContactDTO(Contact contact) {
        if (contact != null)
            return modelMapper.map(contact, ContactDTO.class);
        return null;
    }

    public String prepareFieldsErrorMessage(BindingResult bindingResult) {
        StringBuilder stringBuilder = new StringBuilder();
        for (var error: bindingResult.getFieldErrors()) {
            stringBuilder.append(error.getField()).append(":");
            stringBuilder.append(error.getDefaultMessage()).append(";");
        }

        return stringBuilder.toString();
    }
}
