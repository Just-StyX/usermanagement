package jsl.security.usermanagment.details;

import jsl.security.entities.Person;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public record PersonUserDetails(Person person) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return person.getAuthorities()
                .stream()
                .map(authority -> (GrantedAuthority) () -> String.valueOf(authority))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }
}
