package test.htp.procurement.util;

import by.htp.procurement.util.MessageManager;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MessageManagerTest {

    @Test
    public void getRuMessage() {
        String actual = MessageManager.RU.getMessage("label.login.title");
        String expected = "Логин";
        Assert.assertEquals(actual,expected);
    }

    @Test
    public void getEnMessage() {
        String actual = MessageManager.EN.getMessage("label.login.title");
        String expected = "Login";
        Assert.assertEquals(actual,expected);
    }
}
