package test.htp.procurement.verifier;

import by.htp.procurement.verifier.*;
import by.htp.procurement.content.constant.EntityNameType;
import by.htp.procurement.entity.Entity;
import java.util.EnumMap;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import java.text.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import static by.htp.procurement.verifier.EntityInvalidStateTypeCollection.*;

import by.htp.procurement.entity.Proposal;
import java.util.ArrayList;
import java.util.List;

public class UpdateProfileStateVerifierTest {

    String lang = "EN";

    @Test
    public void verifyTest_all() throws ParseException {
        User user = new User();
        List<Tender> tenders = new ArrayList<>();
        tenders.add(new Tender());
        List<Proposal> proposals = new ArrayList<>();
        proposals.add(new Proposal());
        user.setTenders(tenders);
        user.setProposals(proposals);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        String expected = "You can't change profile when there are active tenders announced / You can't change profile when there are proposals applied / ";
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForUpdateProfile, actual, lang);
        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void verifyTest_proposals() throws ParseException {
        User user = new User();
        List<Tender> tenders = new ArrayList<>();
        List<Proposal> proposals = new ArrayList<>();
        proposals.add(new Proposal());
        user.setTenders(tenders);
        user.setProposals(proposals);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        String expected = "You can't change profile when there are proposals applied / ";
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForUpdateProfile, actual, lang);
        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void verifyTest_tenders() throws ParseException {
        User user = new User();
        List<Tender> tenders = new ArrayList<>();
        tenders.add(new Tender());
        List<Proposal> proposals = new ArrayList<>();
        user.setTenders(tenders);
        user.setProposals(proposals);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        String expected = "You can't change profile when there are active tenders announced / ";
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForUpdateProfile, actual, lang);
        Assert.assertEquals(expected, actual.toString());
    }

    @Test
    public void verifyTest_ok() throws ParseException {
        User user = new User();
        List<Tender> tenders = new ArrayList<>();
        List<Proposal> proposals = new ArrayList<>();
        user.setTenders(tenders);
        user.setProposals(proposals);
        EnumMap<EntityNameType, Entity> entites = new EnumMap<>(EntityNameType.class);
        entites.put(EntityNameType.USER, user);
        StringBuilder actual = new StringBuilder();
        String expected = "";
        EntityInvalidStateVerifier.getInstance().verify(entites, stateForUpdateProfile, actual, lang);
        Assert.assertEquals(expected, actual.toString());
    }

}
