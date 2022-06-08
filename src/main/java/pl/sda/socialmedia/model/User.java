package pl.sda.socialmedia.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @NotNull
    @Length(min = 5, max = 15, message = "username has to be between 5 and 15 chars long")
    private String username;
    @NotNull
    @Length(min = 8, message = "password has to be minimum 8 chars long")
    private String password;
    private String role;
    @NotNull
    @Email(message = "Has to be a valid email address.")
    @Column(unique = true)
    private String email;

}
