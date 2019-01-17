package by.htp.procurement.servlet.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebListener
public class SessionListener implements HttpSessionListener {

    private final String ATTRIBUTE_LOCALE = "locale";
    private final String ATTRIBUTE_LANGUAGE = "language";
    private final String EN_LANGUAGE = "EN";

    private static Logger logger = LogManager.getLogger();

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        logger.info("Session " + event.getSession().getId() + " created");

        event.getSession().setAttribute(ATTRIBUTE_LOCALE, Locale.getDefault());
        event.getSession().setAttribute(ATTRIBUTE_LANGUAGE, EN_LANGUAGE);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("Session " + se.getSession().getId() + " destroyed");
    }
}
