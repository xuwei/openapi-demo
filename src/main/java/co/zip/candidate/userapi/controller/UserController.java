package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    @Autowired
    private IUserService userService;

    @Async
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @Cacheable
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<Page<UserModel>>> listUsers(@RequestParam Pageable pageable) {
        Page users = userService.listUsers(pageable);
        ResponseEntity response = new ResponseEntity<>(users, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @ResponseStatus(code = HttpStatus.OK)
    @ResponseBody
    @GetMapping("/{userId}")
    public CompletableFuture<ResponseEntity<UserModel>> getUser(@PathVariable String userId) {
        Optional<UserModel> user = userService.getUser(userId);
        ResponseEntity response = new ResponseEntity<>(user, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<UserModel>> createUser(@Valid @RequestBody UserModel user) {
        UserModel savedUser = userService.createUser(user);
        ResponseEntity response = new ResponseEntity<>(savedUser, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }
}
