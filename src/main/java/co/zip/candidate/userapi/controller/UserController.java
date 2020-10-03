package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success")
            }
    )
    @Async
    @GetMapping("/user/list")
    public CompletableFuture<ResponseEntity<List<UserModel>>> listUsers() {
        List users = userService.listUsers();
        ResponseEntity response = new ResponseEntity<>(users, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @GetMapping("/user")
    public CompletableFuture<ResponseEntity<UserModel>> getUser(@RequestParam(required = true) Long userId) {
        UserModel user = userService.getUser(userId);
        ResponseEntity response = new ResponseEntity<>(user, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<UserModel>> createUser(@Valid @RequestBody UserModel user) throws Exception {
        UserModel savedUser = userService.createUser(user);
        ResponseEntity response = new ResponseEntity<>(savedUser, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }
}
