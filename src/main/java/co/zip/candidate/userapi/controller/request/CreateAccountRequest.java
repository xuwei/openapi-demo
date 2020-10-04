package co.zip.candidate.userapi.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateAccountRequest {

    @JsonProperty("email")
    @NotNull
    @NotEmpty
    @Email(message = "Please provide a valid email format")
    @Column(unique = true)
    @Size(min = 3, max = 256, message = "Please provide an email within 256 characters")
    private String email;

    public CreateAccountRequest(@NotNull @NotEmpty @Email(message = "Please provide a valid email format") @Size(min = 3, max = 256, message = "Please provide an email within 256 characters") String email) {
        this.email = email;
    }

    public CreateAccountRequest() {
    }

    public String getEmail() {
        return email;
    }
}
