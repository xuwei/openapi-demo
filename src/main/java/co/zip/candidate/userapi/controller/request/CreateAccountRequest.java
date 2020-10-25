package co.zip.candidate.userapi.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateAccountRequest {

    @JsonProperty("userId")
    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String userId;

    public CreateAccountRequest(@NotNull @NotEmpty String userId) {
        this.userId = userId;
    }

    public CreateAccountRequest() {
    }

    public String getUserId() {
        return userId;
    }
}
