package co.zip.candidate.userapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.UUID;

@XmlRootElement(name = "AccountModel")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AccountModel {

    @JsonIgnore
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty("email")
    @NotNull
    @NotEmpty
    private String email;

    public AccountModel(@NotNull @NotEmpty String email) {
        this.email = email;
    }

    public AccountModel() { }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
