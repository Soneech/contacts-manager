package org.soneech.controller;

import jakarta.validation.Valid;
import org.soneech.dto.ContactDTO;
import org.soneech.exception.ContactNotFoundException;
import org.soneech.model.Contact;
import org.soneech.service.ContactService;
import org.soneech.util.ContactValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/contacts")
public class ContactController {
    private final ContactService contactService;
    private final ContactValidator contactValidator;

    @Autowired
    public ContactController(ContactService contactService, ContactValidator contactValidator) {
        this.contactService = contactService;
        this.contactValidator = contactValidator;
    }

    @GetMapping
    public List<ContactDTO> getContacts() {
        return contactService
                .findAll().stream().map(contactService::convertToContactDTO).collect(Collectors.toList());
    }

    @GetMapping("/original")
    public List<Contact> getOriginalContacts() {
        return contactService.findAll();
    }

    @GetMapping("/{id}")
    public ContactDTO getContactById(@PathVariable("id") Long id) throws ContactNotFoundException {
        return contactService.convertToContactDTO(contactService.findById(id).get());
    }

    @GetMapping("/original/{id}")
    public Contact getOriginalContactById(@PathVariable("id") Long id) throws ContactNotFoundException {
        return contactService.findById(id).get();
    }

    @PostMapping
    public ResponseEntity<?> saveContact(@RequestBody @Valid ContactDTO contactDTO, BindingResult bindingResult) {
        Contact contact = contactService.convertToContact(contactDTO);
        contactValidator.validate(contact, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(Map.of("message", contactService.prepareFieldsErrorMessage(bindingResult)));
        }
        Contact savedContact = contactService.save(contact);
        return ResponseEntity.ok(savedContact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateContact(@PathVariable("id") Long id,
                                           @RequestBody @Valid ContactDTO contactDTO,
                                           BindingResult bindingResult) throws ContactNotFoundException {
        Contact contact = contactService.convertToContactWithId(contactDTO, id);
        contactValidator.validate(contact, bindingResult);

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .badRequest().body(Map.of("message", contactService.prepareFieldsErrorMessage(bindingResult)));
        }
        Contact updatedContact = contactService.update(id, contact);
        return ResponseEntity.ok(updatedContact);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") Long id) throws ContactNotFoundException {
        contactService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<Map<String, String>> handleException(ContactNotFoundException exception) {
        return new ResponseEntity<>(Map.of("message", exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
