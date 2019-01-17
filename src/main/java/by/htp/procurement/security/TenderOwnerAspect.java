package by.htp.procurement.security;

import by.htp.procurement.content.CommandResult;
import by.htp.procurement.content.parameter.ParamType;
import by.htp.procurement.content.RequestContent;
import by.htp.procurement.util.ConfigurationManager;
import java.util.EnumSet;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TenderOwnerAspect {

    @Pointcut("@annotation(TenderOwnerCheck)&& args(content,parametersToValidate)")
    public void servicePoint(RequestContent content, EnumSet<ParamType> parametersToValidate) throws Throwable {
    }

    @Around("servicePoint(content,parametersToValidate)")
    public CommandResult serviceAround(ProceedingJoinPoint joinPoint, RequestContent content, EnumSet<ParamType> parametersToValidate) throws Throwable {
        if (TenderOwnerChecker.getInstance().checkIfTenderOwner(content)) {
            CommandResult router = (CommandResult) joinPoint.proceed();
            return router;
        } else {
            return new CommandResult(ConfigurationManager.getProperty("path.page.accessDenied"), CommandResult.ResponseType.REDIRECT);
        }
    }
}
