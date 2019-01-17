package by.htp.procurement.servlet.listener;

import by.htp.procurement.content.attribute.AttributeToRemoveAfterRedirectType;
import java.util.EnumSet;

public class AttributeToRemoveAfterRedirectCollection {

    public static final EnumSet<AttributeToRemoveAfterRedirectType> attributesToRemoveAfterRedirect
            = EnumSet.allOf(AttributeToRemoveAfterRedirectType.class);
}
