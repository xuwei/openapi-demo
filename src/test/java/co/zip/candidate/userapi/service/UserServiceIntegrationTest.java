package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.exception.NonUniqueException;
import co.zip.candidate.userapi.exception.NotFoundException;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.impl.UserService;
import co.zip.candidate.userapi.service.util.TestDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    TestDataUtil dataUtil = new TestDataUtil();

    @Test
    public void testListUsers() {


        UserModel peter = dataUtil.getTestUser(TestDataUtil.PETER);
        UserModel james = dataUtil.getTestUser(TestDataUtil.JAMES);
        userService.createUser(peter);
        userService.createUser(james);

        try {
            List<UserModel> userList = userService.listUsers();
            assertNotNull(userList);
            assertTrue(userList.size() == 2);
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testListUsersEmpty() {

        try {
            List<UserModel> userList = userService.listUsers();
            assertNotNull(userList);
            assertTrue(userList.isEmpty());
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testCreateUser() {
        UserModel john = dataUtil.getTestUser(TestDataUtil.JOHN);
        try {
            UserModel savedUser = userService.createUser(john);
            assertNotNull(savedUser.getId());
            assertNotNull(savedUser.getCreated());
            assertTrue(savedUser.getEmail().equals(john.getEmail()));
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testCreateUserNegativeSalary() {
        BigDecimal salary = new BigDecimal(-1000);
        BigDecimal expense = new BigDecimal(1000);
        try {
            UserModel john = new UserModel("john", "john@gmail.com", salary, expense);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCreateUserNegativeExpense() {
        BigDecimal salary = new BigDecimal(1000);
        BigDecimal expense = new BigDecimal(-1000);
        try {
            UserModel john = new UserModel("john", "john@gmail.com", salary, expense);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testCreateUserDuplicatedEmail() {
        UserModel john = dataUtil.getTestUser(TestDataUtil.JOHN);
        UserModel peter = dataUtil.getTestUser(TestDataUtil.PETER);
        try {
            UserModel savedUser = userService.createUser(john);
            UserModel newUser = new UserModel(peter.getName(), john.getEmail(), peter.getMonthlySalary(), peter.getMonthlyExpense());
            userService.createUser(newUser);
        } catch (Exception e) {
            if (e instanceof NonUniqueException) {
                assertTrue(true);
            } else {
                fail(e.getLocalizedMessage());
            }
        }
    }

    @Test
    public void testGetUser() {
        UserModel john = dataUtil.getTestUser(TestDataUtil.JOHN);
        try {
            UserModel savedUser = userService.createUser(john);
            assertNotNull(savedUser.getId());
            assertNotNull(savedUser.getCreated());
            assertTrue(savedUser.getEmail().equals(john.getEmail()));
            UserModel fetchUser = userService.getUser(savedUser.getId().toString());
            assertNotNull(fetchUser);
            assertTrue(savedUser.getEmail().equals(fetchUser.getEmail()));
        } catch (Exception e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public void testGetUserInvalidUUID() {
        UserModel john = dataUtil.getTestUser(TestDataUtil.JOHN);
        try {
            UserModel savedUser = userService.createUser(john);
            assertNotNull(savedUser.getId());
            assertNotNull(savedUser.getCreated());
            assertTrue(savedUser.getEmail().equals(john.getEmail()));
            userService.getUser(dataUtil.INVALID_UUID);
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetUserNotExistingUUID() {
        UserModel john = dataUtil.getTestUser(TestDataUtil.JOHN);
        try {
            UserModel savedUser = userService.createUser(john);
            assertNotNull(savedUser.getId());
            assertNotNull(savedUser.getCreated());
            assertTrue(savedUser.getEmail().equals(john.getEmail()));
            userService.getUser(dataUtil.WRONG_UUID);
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                assertTrue(true);
            } else {
                fail(e.getLocalizedMessage());
            }
        }
    }


}
