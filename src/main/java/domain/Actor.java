package domain;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import security.UserAccount;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity{

    // Constructors -----------------------------------------------------------

    public Actor() {
        super();
    }

    // Attributes -------------------------------------------------------------

    private String	name;
    private String	email;
    private String	phone;

    @NotBlank
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    @Email
    public String getEmail() {
        return this.email;
    }
    public void setEmail(final String email) {
        this.email = email;
    }

    @NotBlank
    @Pattern(regexp = "^([+-]\\d+\\s+)?(\\([0-9]+\\)\\s+)?([\\w\\s-]+)$")
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(final String phone) {
        this.phone = phone;
    }


    // Relationships ----------------------------------------------------------

    private UserAccount	userAccount;

    @NotNull
    @Valid
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public void setUserAccount(final UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
