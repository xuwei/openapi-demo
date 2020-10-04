package co.zip.candidate.userapi.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Component
public class CreateUserRequest {

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

    public CreateUserRequest(@NotNull(message = "null is not a valid value") @NotEmpty(message = "Please provide a name") @Size(min = 3, max = 100) String name, @NotNull(message = "null is not a valid value") @NotEmpty(message = "Please provide an email") @Email(message = "Please provide a valid email format") @Size(min = 3, max = 256, message = "Please provide an email within 256 characters") String email, @NotNull @Digits(integer = 10, fraction = 2) @DecimalMin(value = "0.0", message = "monthly salary must be positive value") BigDecimal monthlySalary, @NotNull @Digits(integer = 10, fraction = 2) @DecimalMin(value = "0.0", message = "monthly expense must be positive value") BigDecimal monthlyExpense) {
        this.name = name;
        this.email = email;
        this.monthlySalary = monthlySalary;
        this.monthlyExpense = monthlyExpense;
    }

    public CreateUserRequest() {
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
}
