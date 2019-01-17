package by.htp.procurement.servlet;

import static by.htp.procurement.content.constant.ContentConstant.*;
import by.htp.procurement.entity.Tender;
import by.htp.procurement.entity.User;
import by.htp.procurement.util.ConfigurationManager;
import by.htp.procurement.util.MessageManager;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = "/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 50)

public class FileUploader extends HttpServlet {

    private static final String ATTRIBUTE_LANGUAGE = "language";
    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_TENDER = "tender";
    private static final String PARAMETR_MESSAGE = "message";
    private static final String FILE_SOURCE = "file_source";
    private static final String SAVE_DIR = "uploadFiles";

    private static Logger logger = LogManager.getLogger();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String fileSource = null;
        String lang = (String) (request.getSession().getAttribute(ATTRIBUTE_LANGUAGE));
        User user = (User) request.getSession().getAttribute(ATTRIBUTE_USER);
        Tender tender = (Tender) request.getSession().getAttribute(ATTRIBUTE_TENDER);
        String appPath = request.getServletContext().getRealPath(EMPTY_STRING);
        String dbPath = SAVE_DIR + PATH_DIVIDER + tender.getId() + PATH_DIVIDER + user.getLogin();
        String savePath = appPath + dbPath;
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }
        try {
            for (Part part : request.getParts()) {
                if (part.getSubmittedFileName() != null) {
                    part.write(savePath + File.separator + part.getSubmittedFileName());
                    fileSource = dbPath + PATH_DIVIDER + part.getSubmittedFileName();
                }
            }
        } catch (IOException ex) {
            logger.error(ex);
            request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.error")).forward(request, response);
        }
        if (fileSource.length() > FILE_SOURCE_LENGTH_MAX) {
            fileSource = fileSource.substring(FILE_PATH_STRING_BEGIN, FILE_SOURCE_LENGTH_MAX);
        }
        request.getSession().setAttribute(FILE_SOURCE, fileSource);
        request.setAttribute(PARAMETR_MESSAGE, MessageManager.valueOf(lang.toUpperCase()).getMessage("message.upload"));
        request.getRequestDispatcher(ConfigurationManager.getProperty("path.page.addProposal")).forward(
                request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
