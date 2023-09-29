package org.soneech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty
    @Size(min = 2, max = 100)
    private String lastName;

    @Email
    @NotEmpty
    @Size(min = 2, max = 100)
    private String email;

    @Column(name = "phone_number")
    @NotEmpty
    @Size(min = 11, max = 12)
    private String phoneNumber;
}
