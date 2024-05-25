package jsl.security.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("create a user")
    public void createUser() throws Exception {
        var user = """
                {
                  "firstname":"Nop",
                  "lastname":"Zoe",
                  "username":"nop@email.com",
                  "password":"12345678"
                }
                """;
        var result = mockMvc.perform(post("/person?authority=read")
                .contentType(MediaType.APPLICATION_JSON)
                .content(user));
        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Did user log in?")
    public void didUserLogin() throws Exception {
        var result = mockMvc.perform(get("/person")
                .with(user("jay@email.com").password("12345678")));
        result.andExpect(status().isOk());
    }
}
