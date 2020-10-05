package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.enums.AccountStatus;
import co.zip.candidate.userapi.exception.GenericException;
import co.zip.candidate.userapi.exception.NonUniqueException;
import co.zip.candidate.userapi.exception.NotEligibleException;
import co.zip.candidate.userapi.exception.NotFoundException;
import co.zip.candidate.userapi.model.AccountModel;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.impl.AccountService;
import co.zip.candidate.userapi.service.impl.UserService;
import co.zip.candidate.userapi.service.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    UserService userService;

    TestDataUtil dataUtil = new TestDataUtil();
    UserModel john;
    UserModel james;
    UserModel peter;

    @BeforeEach
    public void setup() {
        john = dataUtil.getTestUser(TestDataUtil.JOHN);
        james = dataUtil.getTestUser(TestDataUtil.JAMES);
        peter = dataUtil.getTestUser(TestDataUtil.PETER);
        userService.createUser(john);
        userService.createUser(james);
        userService.createUser(peter);
        assertNotNull(john);
        assertNotNull(james);
        assertNotNull(peter);
        assertNotNull(john.getId());
        assertNotNull(james.getId());
        assertNotNull(peter.getId());
    }

    @Test
    public void testListAccount() {

        try {
            AccountModel johnAccount = accountService.createAccount(john.getEmail());
            AccountModel peterAccount = accountService.createAccount(peter.getEmail());
            AccountModel jamesAccount = accountService.createAccount(james.getEmail());
            assertNotNull(johnAccount);
            assertNotNull(peterAccount);
            assertNotNull(jamesAccount);
            List<AccountModel> accountList = accountService.listAccounts();
            assertNotNull(accountList);
            assertTrue(accountList.size() == 3);
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testListAccountEmpty() {
        try {
            List<AccountModel> accountList = accountService.listAccounts();
            assertNotNull(accountList);
            assertTrue(accountList.size() == 0);
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testCreateAccount() {
        try {
            AccountModel johnAccount = accountService.createAccount(john.getEmail());
            assertNotNull(johnAccount);
            assertNotNull(johnAccount.getId());
            assertNotNull(johnAccount.getCreated());
            assertNotNull(johnAccount.getEmail());
            assertTrue(johnAccount.getEmail().equals(john.getEmail()));
            assertTrue(johnAccount.getAccountStatus() == AccountStatus.Active);
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testCreateAccountEmailNotFound() {

        UserModel fakeJohn = new UserModel(john.getName(), "dummy@gmail.com", john.getMonthlySalary(), john.getMonthlyExpense());
        try {
            AccountModel johnAccount = accountService.createAccount(fakeJohn.getEmail());
        } catch (Exception e) {
            if (e instanceof GenericException) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }

    @Test
    public void testCreateAccountIneligible() {
        UserModel ineligible_dave = dataUtil.getTestUser(TestDataUtil.INELIGIBLE_DAVE);
        try {
            UserModel dave = userService.createUser(ineligible_dave);
            accountService.createAccount(dave.getEmail());
        } catch (Exception e) {
            if (e instanceof NotEligibleException) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }

    @Test
    public void testCreateAccountDuplicatedEmail() {
        try {
            accountService.createAccount(john.getEmail());
            accountService.createAccount(john.getEmail());
        } catch (Exception e) {
            if (e instanceof NonUniqueException) {
                assertTrue(true);
            } else {
                fail();
            }
        }
    }

    @Test
    public void testGetAccount() {
        try {
            AccountModel johnAccount = accountService.createAccount(john.getEmail());
            assertNotNull(johnAccount.getId());
            UUID accountId = johnAccount.getId();
            AccountModel fetchedAccount = accountService.getAccount(accountId.toString());
            johnAccount.getId().equals(fetchedAccount.getId());
            johnAccount.getEmail().equals(fetchedAccount.getEmail());
            johnAccount.getCreated().equals(fetchedAccount.getCreated());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void testGetAccountInvalidUUID() {
        try {
            accountService.getAccount(dataUtil.INVALID_UUID);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetAccountNotExistingUUID() {
        try {
            accountService.getAccount(dataUtil.WRONG_UUID);
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                assertTrue(true);
            } else {
                fail(e.getLocalizedMessage());
            }
        }
    }
}
