package by.htp.procurement.command;

import by.htp.procurement.content.RequestContent;
import by.htp.procurement.content.parameter.ParamType;
import java.util.EnumSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActionStrategy {

    private final String PARAM_COMMAND = "command";

    private static Logger logger = LogManager.getLogger();

    public CommandType defineType(RequestContent content) throws CommandException {
        CommandType type = null;
        String action = content.getParameter(PARAM_COMMAND);
        if (action == null || action.isEmpty()) {
            throw new CommandException("Command name is null");
        }
        try {
            type = CommandType.valueOf(action.toUpperCase());
            logger.info("Current command is " + type);
        } catch (IllegalArgumentException ex) {
            throw new CommandException("Command is not defined", ex);
        }
        return type;
    }
    
    public ActionCommand getActionCommand(RequestContent content) throws CommandException {
        return defineType(content).getActionCommand();
    }

    public EnumSet<ParamType> getParametersSet(RequestContent content) throws CommandException {
        return defineType(content).getParametersSet();
    }
}
