package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IAccountService {

    List<AccountModel> listAccounts();
    AccountModel getAccount(String accountId);
    AccountModel createAccount(String email);

    boolean isEligible(BigDecimal salary, BigDecimal expense);
}
