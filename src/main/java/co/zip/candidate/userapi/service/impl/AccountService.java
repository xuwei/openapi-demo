package co.zip.candidate.userapi.service.impl;

import co.zip.candidate.userapi.exception.GenericException;
import co.zip.candidate.userapi.exception.NonUniqueException;
import co.zip.candidate.userapi.exception.NotEligibleException;
import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.repository.AccountRepository;
import co.zip.candidate.userapi.service.IAccountService;
import co.zip.candidate.userapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService implements IAccountService {

    final BigDecimal MIN_CASH_FLOW = BigDecimal.valueOf(1000);
    final BigDecimal INITIAL_ACCOUNT_BALANCE = BigDecimal.valueOf(1000);

    @Autowired
    private IUserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Cacheable("account")
    @Override
    public List<AccountModel> listAccounts(UUID userId) {
        return accountRepository.findAccountModelsByUserId(userId.toString());
    }

    @Cacheable("account")
    @Override
    public AccountModel getAccount(String accountId) {
        UUID id = UUID.fromString(accountId);
        AccountModel account = accountRepository.findAccountModelById(id);
        return account;
    }

    @CacheEvict(cacheNames = "account", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public AccountModel createAccount(UUID userId) throws RuntimeException {
        try {
            UserModel user = userService.getUser(userId.toString());
            boolean isEligible = isEligible(user.getMonthlySalary() , user.getMonthlyExpense());
            if (isEligible) {
                AccountModel account = new AccountModel(userId.toString(), INITIAL_ACCOUNT_BALANCE);
                return accountRepository.save(account);
            } else {
                throw new NotEligibleException();
            }
        } catch(RuntimeException ex) {
            if(ex instanceof NotEligibleException || ex instanceof NonUniqueException) { throw ex; } else {
                throw new GenericException();
            }
        }
    }

    public boolean isEligible(BigDecimal salary, BigDecimal expense) {
        BigDecimal cashFlow = salary.subtract(expense);
        return cashFlow.compareTo(MIN_CASH_FLOW) >= 0 ? true : false;
    }
}
