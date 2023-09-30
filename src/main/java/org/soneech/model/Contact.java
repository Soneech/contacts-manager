package org.soneech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

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

    @NotEmpty
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotEmpty
    @Size(min = 2, max = 100)
    private String email;

    @Column(name = "phone_number")
    @NotEmpty
    @Size(min = 11, max = 12)
    private String phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
