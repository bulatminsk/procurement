package by.htp.procurement.util;

import by.htp.procurement.content.constant.ContentConstant;
import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle(ContentConstant.RESOURCES_MESSAGES, new Locale(ContentConstant.EN_SMALL, ContentConstant.US))),
    RU(ResourceBundle.getBundle(ContentConstant.RESOURCES_MESSAGES, new Locale(ContentConstant.RU_SMALL, ContentConstant.RU)));

    private ResourceBundle bundle;

    private MessageManager() {
        bundle = ResourceBundle.getBundle(ContentConstant.RESOURCES_MESSAGES, Locale.getDefault());
    }

    MessageManager(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void changeResource(Locale locale) {
        bundle = ResourceBundle.getBundle(ContentConstant.RESOURCES_MESSAGES, locale);
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
