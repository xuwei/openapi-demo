package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.controller.request.CreateUserRequest;
import co.zip.candidate.userapi.exception.GenericException;
import co.zip.candidate.userapi.exception.NotFoundException;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.impl.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RestController
@RequestMapping("/api/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    @Autowired
    private UserService userService;

    @Async
    @Operation(summary = "Query users by pagination", description = "", tags = { "user" })
    @GetMapping("/query")
    public CompletableFuture<ResponseEntity<Page<UserModel>>> queryUsers(@RequestParam int pageNo, @RequestParam int pageSize) {
        Page users = userService.queryUsers(pageNo, pageSize);
        ResponseEntity response = new ResponseEntity<>(users, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @Operation(summary = "List all users", description = "", tags = { "user" })
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<UserModel>>> listUsers() {
        List users = userService.listUsers();
        ResponseEntity response = new ResponseEntity<>(users, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @Operation(summary = "Get user by user's UUID", description = "", tags = { "user" })
    @GetMapping("/{userId}")
    public CompletableFuture<ResponseEntity<UserModel>> getUser(@PathVariable String userId) {
        try {
            UserModel user = userService.getUser(userId);
            ResponseEntity response = new ResponseEntity<>(user, HttpStatus.OK);
            return CompletableFuture.completedFuture(response);
        } catch (Exception ex) {
            throw new CompletionException(new NotFoundException());
        }
    }

    @Async
    @Operation(summary = "Add a new user", description = "", tags = { "user" })
    @PostMapping(value = "/")
    public CompletableFuture<ResponseEntity<UserModel>> createUser(@Valid @RequestBody CreateUserRequest request) throws ConstraintViolationException {
        try {
            UserModel user = new UserModel(request.getName(), request.getEmail(), request.getMonthlySalary(), request.getMonthlyExpense());
            UserModel savedUser = userService.createUser(user);
            ResponseEntity response = new ResponseEntity<>(savedUser, HttpStatus.OK);
            return CompletableFuture.completedFuture(response);
        } catch (Exception ex) {
            throw new CompletionException(ex);
        }
    }
}
