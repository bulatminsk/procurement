package by.htp.procurement.verifier;

import java.util.EnumSet;

public class EntityInvalidStateTypeCollection {

    public static final EnumSet<EntityInvalidStateType> stateForApplyTender = EnumSet.range(
            EntityInvalidStateType.TENDER_DEADLINE_IS_OVER, EntityInvalidStateType.USER_NOT_DEFINED_COMPANY);
    public static final EnumSet<EntityInvalidStateType> stateForPublishTender = EnumSet.of(EntityInvalidStateType.TENDER_IS_PUBLISHED, EntityInvalidStateType.CRITERIA_NOT_DEFINED, EntityInvalidStateType.TENDER_IS_ARCHIVED);
    public static final EnumSet<EntityInvalidStateType> stateForPlaceTender = EnumSet.of(
            EntityInvalidStateType.USER_NOT_DEFINED_COMPANY);
    public static final EnumSet<EntityInvalidStateType> stateForUpdateProfile = EnumSet.of(
            EntityInvalidStateType.TENDER_IS_PLACED,EntityInvalidStateType.PROPOSAL_IS_PLACED);
    public static final EnumSet<EntityInvalidStateType> stateForAddProposal = EnumSet.of(
            EntityInvalidStateType.TENDER_DEADLINE_IS_OVER,EntityInvalidStateType.TENDER_IS_ARCHIVED);
}
