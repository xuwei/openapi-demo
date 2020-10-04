package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;

public interface IAccountService {
    public AccountModel createAccount(String email);
}
