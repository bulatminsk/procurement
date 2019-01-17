package by.htp.procurement.verifier;

import by.htp.procurement.content.constant.EntityNameType;
import by.htp.procurement.entity.Entity;
import by.htp.procurement.util.MessageManager;
import java.util.EnumMap;
import java.util.EnumSet;
import static by.htp.procurement.content.constant.ContentConstant.*;

public class EntityInvalidStateVerifier {

    private static final EntityInvalidStateVerifier INSTANCE = new EntityInvalidStateVerifier();

    public static EntityInvalidStateVerifier getInstance() {
        return INSTANCE;
    }

    public boolean verify(EnumMap<EntityNameType, Entity> entites, EnumSet<EntityInvalidStateType> stateToVerify, StringBuilder stateMessage, String lang) {
        stateToVerify.forEach(e -> {
            if (e.verifyEntityState(entites)) {
                stateMessage.append(MessageManager.valueOf(lang).getMessage(e.getMessage())).append(PHRASE_DIVIDER);
            }
        });
        return stateMessage.length() == 0;
    }
}
