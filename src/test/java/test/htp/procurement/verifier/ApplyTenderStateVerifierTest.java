package test.htp.procurement.verifier;

import by.htp.procurement.verifier.*;
import by.htp.procurement.content.constant.EntityNameType;
import by.htp.procurement.entity.Entity;
import java.util.EnumMap;
import by.htp.procurement.entity.Company;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.testng.Assert;
import org.testng.annotations.Test;
import static by.htp.procurement.verifier.EntityInvalidStateTypeCollection.stateForApplyTender;

public class ApplyTenderStateVerifierTest {
    String lang = "EN";
    
    @Test
    public void verifyTest() throws ParseException {
        User user = new User();
        Tender tender = new Tender();
        tender.setDeadlineAt(new SimpleDateFormat( "yyyyMMdd" ).parse("20190520"));
        tender.setIsArchived(false);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.TENDER, tender);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        StringBuilder expected=new StringBuilder("You need to define your company first / ");
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForApplyTender, actual, lang);
        Assert.assertEquals(expected.length(), actual.length());
    }
    @Test
    public void verifyTest1() throws ParseException {
        User user = new User();
        Company company=new Company();
        user.setCompany(company);
        Tender tender = new Tender();
        tender.setDeadlineAt(new SimpleDateFormat( "yyyyMMdd" ).parse("20190520"));
        tender.setIsArchived(true);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.TENDER, tender);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        StringBuilder expected=new StringBuilder("Tender was deleted! / ");
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForApplyTender, actual, lang);
        Assert.assertEquals(expected.length(), actual.length());
    }
    
    @Test
    public void verifyTest2() throws ParseException {
        User user = new User();
        Company company=new Company();
        user.setCompany(company);
        Tender tender = new Tender();
        tender.setDeadlineAt(new SimpleDateFormat( "yyyyMMdd" ).parse("20190101"));
        tender.setIsArchived(false);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.TENDER, tender);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        StringBuilder expected=new StringBuilder("Deadline for this tender is finished  / ");
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForApplyTender, actual, lang);
        Assert.assertEquals(expected.length(), actual.length());
    }
    
    @Test
    public void verifyTest_ok() throws ParseException {
        User user = new User();
        Company company=new Company();
        user.setCompany(company);
        Tender tender = new Tender();
        tender.setDeadlineAt(new SimpleDateFormat( "yyyyMMdd" ).parse("20190520"));
        tender.setIsArchived(false);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.TENDER, tender);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        StringBuilder expected=new StringBuilder();
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForApplyTender, actual, lang);
        Assert.assertEquals(expected.length(), actual.length());
    }
}
