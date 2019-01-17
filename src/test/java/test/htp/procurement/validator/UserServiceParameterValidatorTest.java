package test.htp.procurement.validator;

import java.util.EnumMap;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.validator.ParameterValidator;

public class UserServiceParameterValidatorTest {
    
    @Test
    public void validateTest_email_length_5_pass_length_4() throws ParseException {
        String[] params = {"1234", "12345", "1", "1", "1"};
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, params[1]);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = " / PASSWORD /  / EMAIL / ";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void validateTest_pass_length_4() throws ParseException {
        String[] params = {"1234", "a12345", "1", "1", "1"};
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, params[1]);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = " / PASSWORD / ";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void validateTest_pass_length_5() throws ParseException {
        String[] params = {"12345", "a12345", "1", "1", "1"};
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, params[1]);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = "";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void validateTest_pass_length_20() throws ParseException {
        String[] params = {"12345678901234567890", "a12345", "1", "1", "1"};
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, params[1]);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = "";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void validateTest_pass_length_21() throws ParseException {
        String[] params = {"123456789012345678901", "a12345", "1", "1", "1"};
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, params[1]);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = " / PASSWORD / ";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

//    @Test
    public void validateTest_pass_length_ok() throws ParseException {
        String[] params = {"12345678901234567890", "qqa@qaq.qqa", "1", "1", "1"};
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, params[1]);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = "";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void validateTest_email_length_5() throws ParseException {
        String[] params = {"12345678901234567890", "", "1", "1", "1"};
        String param = "a1234";
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, param);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = "";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void validateTest_email_length_ok_100() throws ParseException {
        String[] params = {"12345678901234567890", "", "1", "1", "1"};
        String param = "a012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678";
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, param);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = "";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }

    @Test
    public void validateTest_email_length_ok_101() throws ParseException {
        String[] params = {"12345678901234567890", "", "1", "1", "1"};
        String param = "a012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678a";
        EnumMap<ParamType, String> userParameters = new EnumMap<>(ParamType.class);
        userParameters.put(ParamType.PASSWORD, params[0]);
        userParameters.put(ParamType.EMAIL, param);
        userParameters.put(ParamType.FIRST_NAME, params[2]);
        userParameters.put(ParamType.LAST_NAME, params[3]);
        userParameters.put(ParamType.COMPANY_ID, params[4]);
        StringBuilder actual = new StringBuilder();
        String expected = " / EMAIL / ";
        ParameterValidator.getInstance().validate(userParameters, actual);
        Assert.assertEquals(actual.toString(), expected);
    }
}
