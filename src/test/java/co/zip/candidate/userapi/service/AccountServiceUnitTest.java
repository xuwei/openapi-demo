package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.service.impl.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountServiceUnitTest {

    @Autowired
    AccountService accountService;

    @Test
    public void testIsEligiblePositive() {
        BigDecimal salary = new BigDecimal(2000);
        BigDecimal expense1 = new BigDecimal(1000);
        BigDecimal expense2 = new BigDecimal(100);
        assertTrue(accountService.isEligible(salary, expense1));
        assertTrue(accountService.isEligible(salary, expense2));
    }

    @Test
    public void testIsEligibleNegative() {
        BigDecimal salary = new BigDecimal(2000);
        BigDecimal expense1 = new BigDecimal(3000);
        BigDecimal expense2 = new BigDecimal(1500);
        assertFalse(accountService.isEligible(salary, expense1));
        assertFalse(accountService.isEligible(salary, expense2));
    }
}
