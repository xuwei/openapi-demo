package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.IAccountService;
import co.zip.candidate.userapi.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/account")
@Tag(name = "AccountController", description = "Account API")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @Async
    @ResponseStatus(code = HttpStatus.CREATED)
    @ResponseBody
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<AccountModel>> createAccount(@Valid @RequestBody String email) {
            AccountModel newAccount = accountService.createAccount(email);
            ResponseEntity response = new ResponseEntity<>(newAccount, HttpStatus.OK);
            return CompletableFuture.completedFuture(response);
    }
}
