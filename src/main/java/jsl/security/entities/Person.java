package jsl.security.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "first_name", nullable = false)
    private String firstname;

    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "enabled")
    private int enabled;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private Set<Authority> authorities = new LinkedHashSet<>();

    public Person addAuthority(Authority authority) {
        this.authorities.add(authority);
        authority.getPeople().add(this);
        return this;
    }

    public Person firstName(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public Person lastName(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public Person username(String username) {
        this.username = username;
        return this;
    }

    public Person password(String password) {
        this.password = password;
        return this;
    }

    public Person isEnabled(boolean isEnabled) {
        this.enabled = isEnabled ? 1 : 0;
        return this;
    }

    public String fullName() {
        return this.firstname + this.lastname;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
