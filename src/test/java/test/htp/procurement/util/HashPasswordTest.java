package test.htp.procurement.util;

import org.mindrot.jbcrypt.BCrypt;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class HashPasswordTest {
    private String hashedPass;

    @BeforeMethod
    public void setUp(){
        hashedPass = BCrypt.hashpw("testPassword", BCrypt.gensalt(12));
    }
    @Test
    public void checkPassword() {
        boolean actual = BCrypt.checkpw("testPassword", hashedPass);
        boolean expected = true;
        Assert.assertEquals(actual, expected);
    }

    @AfterMethod
    public void tearDown(){
        hashedPass = null;
    }
}
