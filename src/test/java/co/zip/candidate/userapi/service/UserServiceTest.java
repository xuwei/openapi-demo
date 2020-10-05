package co.zip.candidate.userapi.service;

import co.zip.candidate.userapi.exception.NonUniqueException;
import co.zip.candidate.userapi.exception.NotFoundException;
import co.zip.candidate.userapi.model.UserModel;
import co.zip.candidate.userapi.service.impl.UserService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void testListUsers() {

        UserModel peter = getTestUser("peter");
        UserModel james = getTestUser("james");
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
        UserModel john = getTestUser("john");
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
    public void testCreateUserDuplicatedEmail() {
        UserModel john = getTestUser("john");
        UserModel peter = getTestUser("peter");
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
        UserModel john = getTestUser("john");
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
        UserModel john = getTestUser("john");
        try {
            UserModel savedUser = userService.createUser(john);
            assertNotNull(savedUser.getId());
            assertNotNull(savedUser.getCreated());
            assertTrue(savedUser.getEmail().equals(john.getEmail()));
            UserModel fetchUser = userService.getUser("abc");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testGetUserNotExistingUUID() {
        UserModel john = getTestUser("john");
        try {
            UserModel savedUser = userService.createUser(john);
            assertNotNull(savedUser.getId());
            assertNotNull(savedUser.getCreated());
            assertTrue(savedUser.getEmail().equals(john.getEmail()));
            UserModel fetchUser = userService.getUser("123e4567-e89b-12d3-a456-426614174000");
        } catch (Exception e) {
            if (e instanceof NotFoundException) {
                assertTrue(true);
            } else {
                fail(e.getLocalizedMessage());
            }
        }
    }

    protected UserModel getTestUser(String name) {
        switch (name) {
            case "john":
                BigDecimal salary1 = new BigDecimal(1000);
                BigDecimal expense1 = new BigDecimal(200);
                UserModel john = new UserModel("john smith", "john.smith@gmail.com", salary1, expense1);
                return john;
            case "peter":
                BigDecimal salary2 = new BigDecimal(1000);
                BigDecimal expense2 = new BigDecimal(200);
                UserModel peter = new UserModel("peter pan", "peter.pan@gmail.com", salary2, expense2);
                return peter;
            case "james":
                BigDecimal salary3 = new BigDecimal(1000);
                BigDecimal expense3 = new BigDecimal(200);
                UserModel james = new UserModel("james mckay", "james.mckay@gmail.com", salary3, expense3);
                return james;
        }
        return null;
    }
}
