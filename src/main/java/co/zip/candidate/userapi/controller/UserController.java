package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.exception.NonUniqueException;
import co.zip.candidate.userapi.exception.NotFoundException;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.impl.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RestController
@RequestMapping("/api/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    @Autowired
    private UserService userService;

    @Async
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<UserModel>>> listUsers() {
        List users = userService.listUsers();
        ResponseEntity response = new ResponseEntity<>(users, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @GetMapping("/{userId}")
    public CompletableFuture<ResponseEntity<UserModel>> getUser(@PathVariable String userId) {
        try {
            Optional<UserModel> user = userService.getUser(userId);
            ResponseEntity response;
            if (user.isPresent()) {
                response = new ResponseEntity<>(user.get(), HttpStatus.OK);
            } else {
                throw new NotFoundException();
            }
            return CompletableFuture.completedFuture(response);
        } catch (Exception ex) {
            throw new CompletionException(new NotFoundException());
        }
    }

    @PostMapping(value = "/")
    public CompletableFuture<ResponseEntity<UserModel>> createUser(@Valid @RequestBody UserModel user) throws ConstraintViolationException {
        try {
            UserModel savedUser = userService.createUser(user);
            ResponseEntity response = new ResponseEntity<>(savedUser, HttpStatus.OK);
            return CompletableFuture.completedFuture(response);
        } catch (Exception ex) {
            throw new CompletionException(ex);
        }
    }
}
