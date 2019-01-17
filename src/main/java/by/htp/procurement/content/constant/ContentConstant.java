package by.htp.procurement.content.constant;

public class ContentConstant {

    public static final String PHRASE_DIVIDER = " / ";
    public static final String PATH_DIVIDER = "/";
    public static final String RESOURCES_MESSAGES = "resources.messages";
    public static final String US = "US";
    public static final String RU = "RU";
    public static final String EN_SMALL = "en";
    public static final String RU_SMALL = "ru";
    public static final String LOGIN_REGEX = "\\b[a-zA-Z][a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]{4,19}\\b";
    public static final int PASSWORD_LENGTH_MIN = 5;
    public static final int PASSWORD_LENGTH_MAX = 20;
    public static final String EMAIL_REGEX = "\\b[a-zA-Z][a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]{4,99}\\b";
    public static final int FIRST_NAME_LENGTH_MAX = 20;
    public static final int LAST_NAME_LENGTH_MAX = 20;
    public static final String NULL = "null";
    public static final int COMPANY_NAME_LENGTH_MIN = 1;
    public static final int COMPANY_NAME_LENGTH_MAX = 30;
    public static final int TAX_NUMBER_LENGTH_MIN = 1;
    public static final int TAX_NUMBER_LENGTH_MAX = 30;
    public static final int COUNTRY_LENGTH_MIN = 2;
    public static final int COUNTRY_LENGTH_MAX = 100;
    public static final int WEB_LENGTH_MAX = 30;
    public static final int TENDER_NAME_LENGTH_MAX = 80;
    public static final int CATEGORY_LENGTH_MAX = 20;
    public static final int DESCRIPTION_LENGTH_MAX = 500;
    public static final int PRICE_MIN = 1;
    public static final int PRICE_MAX = 1_000_000;
    public static final int APPLICATION_LENGTH_MAX = 500;
    public static final int FILE_SOURCE_LENGTH_MAX = 500;
    public static final int CRITERIA_LENGTH_MAX = 80;
    public static final int WEIGHT_MIN = 1;
    public static final int WEIGHT_MAX = 100;
    public static final int SCORE_MIN = 0;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String EMPTY_STRING = "";
    public static final int FILE_PATH_STRING_BEGIN = 0;
    public static final String RESPONSE_HEADER_CONTENT = "Content-Disposition";
    public static final String RESPONSE_HEADER_CONTENT_TYPE = "attachment; filename=\"";
    public static final String PATH_CLOSURE = "\"";
}
