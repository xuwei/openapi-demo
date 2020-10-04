package co.zip.candidate.userapi.controller;

import co.zip.candidate.userapi.controller.request.CreateAccountRequest;
import co.zip.candidate.userapi.exception.GenericException;
import co.zip.candidate.userapi.exception.NonUniqueException;
import co.zip.candidate.userapi.exception.NotFoundException;
import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.impl.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@RestController
@RequestMapping("/api/account")
@Tag(name = "AccountController", description = "Account API")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Async
    @Operation(summary = "Get account by account's UUID", description = "", tags = { "account" })
    @GetMapping("/{accountId}")
    public CompletableFuture<ResponseEntity<UserModel>> getUser(@PathVariable String accountId) {
        try {
            AccountModel account = accountService.getAccount(accountId);
            ResponseEntity response = new ResponseEntity<>(account, HttpStatus.OK);
            return CompletableFuture.completedFuture(response);
        } catch (Exception ex) {
            throw new CompletionException(new NotFoundException());
        }
    }

    @Async
    @Operation(summary = "List all accounts", description = "", tags = { "account" })
    @GetMapping("/list")
    public CompletableFuture<ResponseEntity<List<AccountModel>>> listAccounts() {
        List<AccountModel> accounts = accountService.listAccounts();
        ResponseEntity response = new ResponseEntity<>(accounts, HttpStatus.OK);
        return CompletableFuture.completedFuture(response);
    }

    @Async
    @Operation(summary = "Add a new account", description = "", tags = { "account" })
    @PostMapping(value = "/")
    public CompletableFuture<ResponseEntity<AccountModel>> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        try {
            AccountModel newAccount = accountService.createAccount(request.getEmail());
            ResponseEntity response = new ResponseEntity<>(newAccount, HttpStatus.OK);
            return CompletableFuture.completedFuture(response);
        } catch (Exception ex) {
            throw new CompletionException(ex);
        }
    }


}
