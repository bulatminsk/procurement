package by.htp.procurement.content.parameter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import static java.util.regex.Pattern.UNICODE_CHARACTER_CLASS;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.htp.procurement.content.constant.ContentConstant.*;

public enum ParamType implements Predicate<String> {

    LOGIN(Pattern.compile(LOGIN_REGEX, UNICODE_CHARACTER_CLASS).asPredicate()),
    PASSWORD(t -> (t.length() >= PASSWORD_LENGTH_MIN) && (t.length() <= PASSWORD_LENGTH_MAX)),
    EMAIL(Pattern.compile(EMAIL_REGEX).asPredicate()),
    FIRST_NAME(t -> (t.length() <= FIRST_NAME_LENGTH_MAX) && (!t.isEmpty())),
    LAST_NAME(t -> (t.length() <= LAST_NAME_LENGTH_MAX) && (!t.isEmpty())),
    COMPANY_ID(t -> (t != null) && (!t.isEmpty()) && (!t.equalsIgnoreCase(NULL))),
    NAME(t -> (t.length() >= COMPANY_NAME_LENGTH_MIN) && (t.length() <= COMPANY_NAME_LENGTH_MAX)),
    TAX_NUMBER(t -> (t.length() >= TAX_NUMBER_LENGTH_MIN) && (t.length() <= TAX_NUMBER_LENGTH_MAX)),
    COUNTRY(t -> ((t.length() >= COUNTRY_LENGTH_MIN) && (t.length() <= COUNTRY_LENGTH_MAX))),
    WEB(t -> (t.length() <= WEB_LENGTH_MAX)),
    TENDER_NAME(t -> (t.length() <= TENDER_NAME_LENGTH_MAX)),
    CATEGORY(t -> (t.length() <= CATEGORY_LENGTH_MAX)),
    DESCRIPTION(t -> (t.length() <= DESCRIPTION_LENGTH_MAX)),
    PRICE(t -> (Integer.parseInt(t) >= PRICE_MIN) && Integer.parseInt(t) <= PRICE_MAX),
    DEADLINE_AT(new Predicate<String>() {
        @Override
        public boolean test(String t) {
            try {
                return (new SimpleDateFormat(DATE_FORMAT)).parse(t).after(new Date());
            } catch (ParseException ex) {
                logger.warn("SimpleDateFormat parsing exception", ex);
            }
            return false;
        }
    }),
    APPLICATION(t -> (t != null)&& t.length() <= APPLICATION_LENGTH_MAX),
    FILE_SOURCE(t -> (t == null) || t.length() <= FILE_SOURCE_LENGTH_MAX),
    CRITERIA(t -> (t != null)&& (!t.isEmpty())&& (t.length() <= CRITERIA_LENGTH_MAX)),
    WEIGHT(t -> (t != null)&& (!t.isEmpty())&&(Integer.parseInt(t) >= WEIGHT_MIN) && Integer.parseInt(t) <= WEIGHT_MAX),
    TENDER_ID(t -> (t != null) && (!t.isEmpty())),
    MEMBER_TO_ADD_LOGIN,
    MEMBER_TO_DELETE_LOGIN,
    PROPOSAL_ID,
    WINNER_CHOISE,
    CRITERIA_TO_DELETE_ID,
    EVALUATION_ID,
    SCORE;
    

    private Predicate<String> predicate;
    private static Logger logger = LogManager.getLogger();

    ParamType() {
    }

    ParamType(Predicate<String> predicate) {
        this.predicate = predicate;
    }
    
    public String getName() {
        return this.toString().toLowerCase();
    }

    public Predicate getPredicate() {
        return predicate;
    }

    @Override
    public boolean test(String t) {
        return predicate.test(t);
    }
}
