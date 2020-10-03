package co.zip.candidate.userapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.util.Date;;

@XmlRootElement(name = "UserModel")
@XmlAccessorType(XmlAccessType.FIELD)
@Component
@Entity
@EntityListeners(AuditingEntityListener.class)
public class UserModel {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonProperty("name")
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;

    @JsonProperty("email")
    @NotNull
    @NotEmpty
    @Email
    @Column(unique = true)
    @Size(min = 3, max = 256)
    private String email;

    @JsonProperty("monthlySalary")
    @NotNull
    @Digits(integer=10, fraction=2)
    @Column(precision=2)
    private BigDecimal monthlySalary;

    @JsonProperty("monthlyExpense")
    @NotNull
    @Digits(integer=10, fraction=2)
    @Column(precision=2)
    private BigDecimal monthlyExpense;

    @JsonProperty("created")
    @CreatedDate
    @NotNull
    private Date created;

    public UserModel() {
    }

    public UserModel(@NotNull @NotEmpty @Size(min = 3, max = 100) String name, @NotNull @NotEmpty @Email @Size(max = 256) String email, @NotNull @Digits(integer = 10, fraction = 2) BigDecimal monthlySalary, @NotNull @Digits(integer = 10, fraction = 2) BigDecimal monthlyExpense) {
        this.name = name;
        this.email = email;
        this.monthlySalary = monthlySalary;
        this.monthlyExpense = monthlyExpense;
        this.created = new Date();
    }
}
