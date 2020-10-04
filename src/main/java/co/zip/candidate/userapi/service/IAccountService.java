package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;

import java.util.List;

public interface IAccountService {
    List<AccountModel> listAccounts();
    AccountModel createAccount(String email);
}
