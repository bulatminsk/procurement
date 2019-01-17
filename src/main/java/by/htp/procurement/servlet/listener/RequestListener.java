package by.htp.procurement.servlet.listener;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.htp.procurement.servlet.listener.AttributeToRemoveAfterRedirectCollection.*;

@WebListener
public class RequestListener implements ServletRequestListener {

    private static Logger logger = LogManager.getLogger();

    @Override
    public void requestInitialized(ServletRequestEvent event) {
        logger.info("requestInitialized " + event.getServletRequest());
        HttpServletRequest request = (HttpServletRequest) event.getServletRequest();
        attributesToRemoveAfterRedirect.forEach((e) -> {
            String name = e.getAttribute();
            Object value = ((HttpServletRequest) event.getServletRequest()).getSession().getAttribute(name);
            if (value != null) {
                request.setAttribute(name, value);
                request.getSession().removeAttribute(name);
            }
        });
    }

    @Override
    public void requestDestroyed(ServletRequestEvent event) {
        logger.info("requestDestroyed " + event.getServletRequest());
    }
}
