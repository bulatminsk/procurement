package by.htp.procurement.command;

import by.htp.procurement.logic.ServiceException;
import by.htp.procurement.content.CommandResult;
import by.htp.procurement.content.RequestContent;
import by.htp.procurement.content.parameter.ParamType;
import java.util.EnumSet;

@FunctionalInterface
public interface ActionCommand {

    CommandResult execute(RequestContent content, EnumSet<ParamType> parametersToValidate) throws ServiceException;
}
