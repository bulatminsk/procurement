package by.htp.procurement.servlet;

import by.htp.procurement.content.CommandResult;
import by.htp.procurement.command.ActionStrategy;
import by.htp.procurement.command.ActionCommand;
import by.htp.procurement.command.CommandException;
import by.htp.procurement.command.CommandType;
import by.htp.procurement.content.RequestContent;
import by.htp.procurement.logic.ServiceException;
import by.htp.procurement.pool.ConnectionPool;
import by.htp.procurement.pool.PoolException;
import by.htp.procurement.util.ConfigurationManager;
import by.htp.procurement.content.parameter.ParamType;
import java.io.IOException;
import java.util.EnumSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private static Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestContent content = new RequestContent(request);
        CommandResult router;
        try {
            ActionStrategy client = new ActionStrategy();
            ActionCommand command = client.getActionCommand(content);
            EnumSet<ParamType> parametersToValidate = client.getParametersSet(content);
            try {
                router = command.execute(content, parametersToValidate);
                content.updateValues(request);
            } catch (ServiceException ex) {
                router = new CommandResult();
                router.setPage(ConfigurationManager.getProperty("path.page.error"));
                logger.error("ServiceException in Controller", ex);
            }
            if (router.getResponseType() == CommandResult.ResponseType.FORWARD) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPage());
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + router.getPage());
            }
        } catch (CommandException ex) {
            logger.error("CommandException in Controller", ex);
            RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + ConfigurationManager.getProperty("path.page.error"));
            dispatcher.forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool.getInstance().releasePool();
        } catch (PoolException ex) {
            throw new RuntimeException(ex);
        }
    }
}
