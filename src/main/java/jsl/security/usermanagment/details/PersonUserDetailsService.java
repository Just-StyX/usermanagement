package jsl.security.usermanagment.details;

import jsl.security.entities.Person;
import jsl.security.usermanagment.repository.PersonRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class PersonUserDetailsService implements UserDetailsManager {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonUserDetailsService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(UserDetails user) {
        var personUserDetails = (PersonUserDetails) user;
        var person = personUserDetails.person();
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepository.save(person);
    }

    @Override
    public void updateUser(UserDetails user) {
        var personDetails = (PersonUserDetails) user;
        var person = personRepository.findByUsername(personDetails.getUsername());
        if (person.isPresent()) {
            Person newPerson = personDetails.person();
            var foundPerson = person.get();
            foundPerson.setFirstname(newPerson.getFirstname());
            foundPerson.setLastname(newPerson.getLastname());
            foundPerson.setUsername(newPerson.getUsername());
            personRepository.save(foundPerson);
        }
    }

    @Override
    public void deleteUser(String username) {
        personRepository.deleteByUsername(username);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        var person = personRepository.findByUsername(oldPassword);
        person.ifPresent(value -> {
            value.setPassword(newPassword);
            personRepository.save(value);
        });
    }

    @Override
    public boolean userExists(String username) {
        return personRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var person = personRepository.findByUsername(username);
        return person.map(PersonUserDetails::new).orElse(null);
    }
}
