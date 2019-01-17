package by.htp.procurement.initializer;

import by.htp.procurement.entity.Tender;
import by.htp.procurement.logic.ServiceException;
import by.htp.procurement.content.parameter.ParamType;
import java.util.EnumMap;
import by.htp.procurement.entity.Evaluation;

public class EvaluationParametersInitializer {

    private static final EvaluationParametersInitializer INSTANCE = new EvaluationParametersInitializer();

    public static EvaluationParametersInitializer getInstance() {
        return INSTANCE;
    }

    public void setEvaluationParamFromMap(Evaluation evaluation, EnumMap<ParamType, String> evaluationParameters) throws ServiceException {
        Tender tender = new Tender();
        evaluation.setCriteria(evaluationParameters.get(ParamType.CRITERIA));
        evaluation.setWeight(Integer.parseInt(evaluationParameters.get(ParamType.WEIGHT)));
        tender.setId(Integer.parseInt(evaluationParameters.get(ParamType.TENDER_ID)));
        evaluation.setTender(tender);
    }
}
