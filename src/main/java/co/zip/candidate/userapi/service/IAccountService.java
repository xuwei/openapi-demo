package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.model.AccountModel;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface IAccountService {

    List<AccountModel> listAccounts(UUID userId);
    AccountModel getAccount(String accountId);
    AccountModel createAccount(UUID userId);

    boolean isEligible(BigDecimal salary, BigDecimal expense);
}
