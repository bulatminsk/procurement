package by.htp.procurement.verifier;

import by.htp.procurement.content.constant.EntityNameType;
import by.htp.procurement.entity.Entity;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import java.util.Date;
import java.util.EnumMap;
import static by.htp.procurement.content.constant.EntityNameType.*;

public enum EntityInvalidStateType {
    TENDER_DEADLINE_IS_OVER("message.tender.deadlineOver") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            Tender tender = (Tender) entites.get(TENDER);
            Date today = new Date();
            return (tender.getDeadlineAt().before(today));
        }
    },
    TENDER_IS_ARCHIVED("message.tender.isArchived") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            Tender tender = (Tender) entites.get(TENDER);
            return (tender.getIsArchived());
        }
    },
    USER_NOT_DEFINED_COMPANY("message.user.companyIsNotDefined") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            User user = (User) entites.get(USER);
            return (user.getCompany() == null);
        }
    },
    TENDER_IS_PUBLISHED("message.tender.isAlreadyPublished") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            Tender tender = (Tender) entites.get(TENDER);
            return (tender.getPublishedAt() != null);
        }
    },
    CRITERIA_NOT_DEFINED("message.tender.noCriteria") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            Tender tender = (Tender) entites.get(TENDER);
            return (tender.getCriteria().isEmpty());
        }
    },
    TENDER_IS_PLACED("message.user.tenderState") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            User user = (User) entites.get(USER);
            return (!user.getTenders().isEmpty());
        }
    },
    PROPOSAL_IS_PLACED("message.user.proposalState") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            User user = (User) entites.get(USER);
            return (!user.getProposals().isEmpty());
        }
    },
    USER_ALREADY_EXISTS ("message.loginexists") {
        @Override
        public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
            User user = (User) entites.get(USER);
            return (user.getCompany() == null);
        }
    },
    ;
    private String message;

    EntityInvalidStateType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean verifyEntityState(EnumMap<EntityNameType, Entity> entites) {
        throw new UnsupportedOperationException();
    }
}
