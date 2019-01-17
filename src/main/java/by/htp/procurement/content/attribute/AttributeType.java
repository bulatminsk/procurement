package by.htp.procurement.content.attribute;

public enum AttributeType {

    USER("user"),
    COMPANY("company"),
    TENDER("tender"),
    FAILURE_MESSAGE("failureMessage"),
    INVALID_STATE("invalidState"),
    LANGUAGE("language"),
    LOCALE("locale"),
    EN_LOCALE("en_US"),
    RU_LOCALE("ru_RU"),
    RU_LANGUAGE("RU"),
    LAST_PAGE("lastPage"),
    PROPOSAL("proposal"),
    EVALUATIONS("evaluations"),
    PUES("pues"),
    FINAL_SCORES("final_scores");
    
    private String attribute;

    private AttributeType() {
    }

    private AttributeType(String attribute) {
        this.attribute = attribute;
    }

    public String getAttribute() {
        return attribute;
    }
}
