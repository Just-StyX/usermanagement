package jsl.security.controller;

import jsl.security.entities.Authority;
import jsl.security.entities.Person;
import jsl.security.usermanagment.details.PersonUserDetails;
import jsl.security.usermanagment.details.PersonUserDetailsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonUserDetailsService personUserDetailsService;

    public PersonController(PersonUserDetailsService personUserDetailsService) {
        this.personUserDetailsService = personUserDetailsService;
    }

    @PostMapping
    private ResponseEntity<Void> createUser(@RequestBody Person person, @RequestParam String authority) {
        var auth = new Authority();
        auth.setAuthority(authority);
        person.addAuthority(auth);
        person.isEnabled(true);
        var userDetails = new PersonUserDetails(person);
        personUserDetailsService.createUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<String> login(@RequestHeader("Request-Id") String requestId) {
        return ResponseEntity.ok("you're logged in");
    }

}
