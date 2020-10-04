package co.zip.candidate.userapi.model;

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
public class UserModel {

    @JsonIgnore
    @JsonProperty("uuid")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonProperty("name")
    @NotNull(message = "null is not a valid value")
    @NotEmpty(message = "Please provide a name")
    @Size(min = 3, max = 100)
    private String name;

    @JsonProperty("email")
    @NotNull(message = "null is not a valid value")
    @NotEmpty(message = "Please provide an email")
    @Email(message = "Please provide a valid email format")
    @Column(unique = true)
    @Size(min = 3, max = 256, message = "Please provide an email within 256 characters")
    private String email;

    @JsonProperty("monthlySalary")
    @NotNull
    @Digits(integer=10, fraction=2)
    @Column(precision=2)
    @DecimalMin(value = "0.0", message = "monthly salary must be positive value")
    private BigDecimal monthlySalary;

    @JsonProperty("monthlyExpense")
    @NotNull
    @Digits(integer=10, fraction=2)
    @Column(precision=2)
    @DecimalMin(value = "0.0", message = "monthly expense must be positive value")
    private BigDecimal monthlyExpense;

    @JsonIgnore
    @JsonProperty("created")
    @CreatedDate
    private Date created;

    public UserModel(@NotNull @NotEmpty @Size(min = 3, max = 100) String name, @NotNull @NotEmpty @Email @Size(max = 256) String email, @NotNull @Digits(integer = 10, fraction = 2) BigDecimal monthlySalary, @NotNull @Digits(integer = 10, fraction = 2) BigDecimal monthlyExpense) {
        this.name = name;
        this.email = email;
        this.monthlySalary = monthlySalary;
        this.monthlyExpense = monthlyExpense;
        this.created = new Date();
    }

    public UserModel() {
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public BigDecimal getMonthlyExpense() {
        return monthlyExpense;
    }

    public Date getCreated() {
        return created;
    }
}
