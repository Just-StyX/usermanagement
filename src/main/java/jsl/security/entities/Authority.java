package jsl.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "authorities")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "authority", nullable = false)
    private String authority;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "authorities")
    private Set<Person> people = new LinkedHashSet<>();

    public void addPerson(Person person) {
        this.people.add(person);
        person.getAuthorities().add(this);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Authority authority1 = (Authority) object;
        return Objects.equals(id, authority1.id) && Objects.equals(authority, authority1.authority) && Objects.equals(people, authority1.people);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authority, people);
    }

    @Override
    public String toString() {
        return "Authority{" +
                "id='" + id + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
