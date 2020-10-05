package co.zip.candidate.userapi.service.util;

import co.zip.candidate.userapi.model.UserModel;

import java.math.BigDecimal;

public class TestDataUtil {

    // user names
    public static final String JOHN = "john";
    public static final String PETER = "peter";
    public static final String JAMES= "james";
    public static final String INELIGIBLE_DAVE = "dave";

    // invalid uuid
    public static final String WRONG_UUID = "123e4567-e89b-12d3-a456-426614174000";
    public static final String INVALID_UUID = "abc";

    public UserModel getTestUser(String name) {

        switch (name) {
            case JOHN:
                BigDecimal salary1 = new BigDecimal(1200);
                BigDecimal expense1 = new BigDecimal(200);
                UserModel john = new UserModel("john smith", "john.smith@gmail.com", salary1, expense1);
                return john;
            case PETER:
                BigDecimal salary2 = new BigDecimal(2000);
                BigDecimal expense2 = new BigDecimal(100);
                UserModel peter = new UserModel("peter pan", "peter.pan@gmail.com", salary2, expense2);
                return peter;
            case JAMES:
                BigDecimal salary3 = new BigDecimal(11000);
                BigDecimal expense3 = new BigDecimal(900);
                UserModel james = new UserModel("james mckay", "james.mckay@gmail.com", salary3, expense3);
                return james;
            case INELIGIBLE_DAVE:
                BigDecimal salary4 = new BigDecimal(1000);
                BigDecimal expense4 = new BigDecimal(900);
                UserModel dave = new UserModel("dave o'donald", "dave.d@gmail.com", salary4, expense4);
                return dave;
        }
        return null;
    }
}
