package co.zip.candidate.userapi.model;

import co.zip.candidate.userapi.enums.AccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Component
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AccountModel {

    @JsonIgnore
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty("userId")
    @NotNull
    @NotEmpty
    private String userId;

    @JsonIgnore
    @JsonProperty("balance")
    @Digits(integer=10, fraction=2)
    @Column(precision=2)
    @DecimalMin(value = "0.0", message = "balance must be zero or positive value")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @JsonIgnore
    @JsonProperty("created")
    @CreatedDate
    private Date created;

    public AccountModel(@NotNull @NotEmpty String userId,
                        @Digits(integer = 10, fraction = 2)
                        @DecimalMin(value = "0.0", message = "balance must be zero or positive value")
                                BigDecimal balance) {
        this.userId = userId;
        this.balance = balance;
        this.created = new Date();
        this.accountStatus = AccountStatus.Active;
    }

    public AccountModel() { }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() { return userId; }

    public BigDecimal getBalance() {
        return balance;
    }

    public Date getCreated() {
        return created;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }
}
