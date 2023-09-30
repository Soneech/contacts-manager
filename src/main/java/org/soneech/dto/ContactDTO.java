package org.soneech.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ContactDTO {
    @NotEmpty(message = "имя не должно быть пустым")
    @Size(min = 2, max = 100)
    private String name;

    @Email
    @NotEmpty(message = "почта не должна быть пустой")
    @Size(min = 2, max = 100)
    private String email;

    @NotEmpty(message = "номер не может быть пустым")
    @Size(min = 11, max = 12)
    private String phoneNumber;
}
