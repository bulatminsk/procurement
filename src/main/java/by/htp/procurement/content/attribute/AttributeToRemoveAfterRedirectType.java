package by.htp.procurement.content.attribute;

public enum AttributeToRemoveAfterRedirectType {

    PROFILE_UPDATED("profileUpdated"),
    SUCCESS_MESSAGE("successMessage"),
    TENDERS("tenders"),
    COMMITTEE_MEMBER_TENDERS("committeeMemberTenders"),
    USER_TENDERS("userTenders"),
    PROPOSALS("proposals"),
    TENDER_PROPOSALS("tenderProposals"),
    USER_PROPOSALS("userProposals"),
    TENDER_APPOINTED_USERS("tenderAppointedUsers"),
    TENDER_TO_APPLY("tenderToApply"),
    TENDER_ADDED("tenderAdded"),
    TENDER_TO_APPOINT("tenderToAppoint"),
    COMPANY_USERS("companyUsers"),
    COMPANY_ADDED("companyAdded"),
    COUNTRY_LIST("countryList");

    private String attribute;

    private AttributeToRemoveAfterRedirectType(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
