package com.bikkadit.electronic.store.dtos;

import com.bikkadit.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    @Size(min = 4, max = 20, message = "username must be min 4 chars and max 20 chars")
    private String name;


    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "Invalid User Email!")
    private String email;

    @Size(min = 8, max = 30, message = "password must be min 8 chars and max 30 chars")
    @Pattern( regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=]).*$",
            message = "password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid Gender!")
    private String gender;

    @NotBlank(message = "Write Something About Yourself!")
    private String about;

    @ImageNameValid
    private String imageName;

}
