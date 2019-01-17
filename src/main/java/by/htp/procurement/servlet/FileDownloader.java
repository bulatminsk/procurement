package by.htp.procurement.servlet;

import by.htp.procurement.util.ConfigurationManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static by.htp.procurement.content.constant.ContentConstant.*;

@WebServlet(urlPatterns = "/download")

public class FileDownloader extends HttpServlet {

    private final String ATTRIBUTE_FILE_TO_DOWNLOAD = "fileToDownload";
    private final String ATTRIBUTE_EXTENSION = "pdf";

    private static Logger logger = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String appPath = request.getServletContext().getRealPath("");
        String fileSource = (String) request.getParameter(ATTRIBUTE_FILE_TO_DOWNLOAD);
        String fullPath = appPath + fileSource;
        response.setContentType(ATTRIBUTE_EXTENSION);
        response.setHeader(RESPONSE_HEADER_CONTENT, RESPONSE_HEADER_CONTENT_TYPE + fileSource + PATH_CLOSURE);
        try (FileInputStream fileInputStream = new FileInputStream(fullPath)) {
            int i;
            while ((i = fileInputStream.read()) != -1) {
                response.getOutputStream().write(i);
            }
        } catch (IOException ex) {
            logger.error("File not found ",ex);
            response.sendRedirect(request.getContextPath() + ConfigurationManager.getProperty("path.page.errorFile"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
