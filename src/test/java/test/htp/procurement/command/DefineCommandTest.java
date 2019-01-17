package test.htp.procurement.command;

import by.htp.procurement.command.ActionStrategy;
import by.htp.procurement.command.CommandException;
import by.htp.procurement.content.RequestContent;
import org.testng.Assert;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefineCommandTest {

    private static final String COMMAND = "register";
    private static Logger logger = LogManager.getLogger();

//    @Test
    public void defineCommand() {
        String expected = COMMAND;
        RequestContent content = newRequestContent();
        ActionStrategy actionStrategy = new ActionStrategy();
        String actual = new String();
        try {
            actual = actionStrategy.defineType(content).toString();
        } catch (CommandException ex) {
            logger.info("CommandException ", ex);
        }
        Assert.assertEquals(actual.length(), expected.length());
    }

    public static RequestContent newRequestContent() {
        RequestContent delegate = (RequestContent) Proxy.newProxyInstance(
                RequestContent.class.getClassLoader(),
                new Class[]{RequestContent.class}, new ThrowingInvocationHandler());
        return delegate;

    }

    private static class ThrowingInvocationHandler implements InvocationHandler {

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            throw new UnsupportedOperationException("No methods are supported on this object");
        }
    }

}
