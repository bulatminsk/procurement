package by.htp.procurement.validator;

import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.content.constant.ContentConstant;
import java.util.EnumMap;

public class ParameterValidator {

    private static final ParameterValidator INSTANCE = new ParameterValidator();

    public static ParameterValidator getInstance() {
        return INSTANCE;
    }

    public boolean validate(EnumMap<ParamType, String> parameters, StringBuilder wrongParameters) {
        parameters.entrySet().stream().forEach(e -> {
            if (!e.getKey().test(e.getValue())) {
                wrongParameters.append(ContentConstant.PHRASE_DIVIDER).append(e.getKey().toString()).append(ContentConstant.PHRASE_DIVIDER);
            }
        });
        return wrongParameters.length() == 0;
    }
}
