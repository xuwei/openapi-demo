package co.zip.candidate.userapi.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;

@Component
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @NotEmpty
    @Email
    @Column(unique = true)
    @Size(max = 256)
    private String email;

    @NotNull
    @Digits(integer=10, fraction=2)
    @Column(precision=2)
    private BigDecimal monthlySalary;

    @NotNull
    @Digits(integer=10, fraction=2)
    @Column(precision=2)
    private BigDecimal monthlyExpense;

    @NotNull
    @CreatedDate
    private Date created;

    public UserModel() {
    }

    public UserModel(@NotNull @NotEmpty @Size(min = 3, max = 100) String name, @NotNull @NotEmpty @Email @Size(max = 256) String email, @NotNull @Digits(integer = 10, fraction = 2) BigDecimal monthlySalary, @NotNull @Digits(integer = 10, fraction = 2) BigDecimal monthlyExpense) {
        this.name = name;
        this.email = email;
        this.monthlySalary = monthlySalary;
        this.monthlyExpense = monthlyExpense;
    }
}
