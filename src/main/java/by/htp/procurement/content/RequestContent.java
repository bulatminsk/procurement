package by.htp.procurement.content;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import static by.htp.procurement.content.constant.ContentConstant.*;

public class RequestContent {

    private boolean sessionInvalidated;
    private String realPath;
    private Map<String, String[]> requestParameters = new TreeMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();
    private Map<String, Object> sessionAttributes = new HashMap<>();

    public RequestContent(HttpServletRequest request) {
        requestAttributes.putAll(Collections.list(request.getAttributeNames())
                .stream().collect(Collectors.toMap(e -> e, e -> request.getAttribute(e))));
        sessionAttributes.putAll(Collections.list(request.getSession().getAttributeNames())
                .stream().collect(Collectors.toMap(e -> e, e -> request.getSession().getAttribute(e))));
        requestParameters.putAll(request.getParameterMap());
        realPath = request.getServletContext().getRealPath(EMPTY_STRING);
    }

    public Map<String, String[]> getParameterMap() {
        return requestParameters;
    }

    public String getParameter(String name) {
        return requestParameters.get(name)[0];
    }

    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(getParameterMap().keySet());
    }

    public String[] getParameterValues(String name) {
        return getParameterMap().get(name);
    }

    public Map<String, Object> getAttributes() {
        return requestAttributes;
    }

    public Object getAttribute(String attributeName) {
        return requestAttributes.get(attributeName);
    }

    public void setAttribute(String attributeName, Object value) {
        requestAttributes.put(attributeName, value);
    }

    public Map<String, Object> getSessionAttributes() {
        return sessionAttributes;
    }

    public Object getSessionAttribute(String attributeName) {
        return sessionAttributes.get(attributeName);
    }

    public void setSessionAttribute(String attributeName, Object value) {
        sessionAttributes.put(attributeName, value);
    }

    /**
     * Updating request code>HttpServletRequest</code> values. 
     * First attributes are removed from request if aren't present in RequestContent.
     * Second attributes are added/updated to  request from RequestContent.
     * Third if sessionInvalidated parameter in RequestContent is true then request Session is invalidated.
     *
     * @return void
     * 
     */
    public void updateValues(HttpServletRequest request) {
        Collections.list(request.getSession().getAttributeNames()).stream().forEach(e
                -> {
            if (!sessionAttributes.containsKey(e)) {
                request.getSession().removeAttribute(e);
            }
        });
        Collections.list(request.getAttributeNames()).stream().forEach(e
                -> {
            if (!requestAttributes.containsKey(e)) {
                request.removeAttribute(e);
            }
        });
        requestAttributes.entrySet().stream().forEach(e -> request.setAttribute(e.getKey(), e.getValue()));
        sessionAttributes.entrySet().stream().forEach(e -> request.getSession().setAttribute(e.getKey(), e.getValue()));
        if (sessionInvalidated) {
            request.getSession().invalidate();
        }
    }

    public void invalidateSession() {
        sessionInvalidated = true;
    }

    public void removeSessionAttribute(String attributeName) {
        sessionAttributes.remove(attributeName);
    }

    public String getServletContextRealPath() {
        return realPath;
    }
}
