package co.zip.candidate.userapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @GetMapping("/users")
    public List<String> retrieveAllUsers() {
        return new ArrayList<String>();
    }

    @PostMapping("/user")
    public void createUser() {
        return;
    }
}
