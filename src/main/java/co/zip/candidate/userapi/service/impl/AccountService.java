package co.zip.candidate.userapi.service.impl;

import co.zip.candidate.userapi.exception.NotEligibleException;
import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.repository.AccountRepository;
import co.zip.candidate.userapi.service.IAccountService;
import co.zip.candidate.userapi.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    final BigDecimal MIN_CASH_FLOW = BigDecimal.valueOf(1000);
    final BigDecimal INITIAL_ACCOUNT_BALANCE = BigDecimal.valueOf(1000);

    @Autowired
    private IUserService userService;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<AccountModel> listAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    @Override
    public AccountModel createAccount(AccountModel account) throws RuntimeException {
        Optional<UserModel> user = userService.getUserByEmail(account.getEmail());
        boolean isEligible = false;
        if (user.isPresent()) {
            isEligible = isEligible(user.get().getMonthlySalary() , user.get().getMonthlyExpense());
        }

        if (isEligible) {
            try {
                account.setBalance(INITIAL_ACCOUNT_BALANCE);
                return accountRepository.save(account);
            } catch(RuntimeException ex) {
                throw ex;
            }
        } else {
            throw new NotEligibleException();
        }
    }

    private boolean isEligible(BigDecimal salary, BigDecimal expense) {
        BigDecimal cashflow = salary.subtract(expense);
        return cashflow.compareTo(MIN_CASH_FLOW) > 0 ? true : false;
    }
}
