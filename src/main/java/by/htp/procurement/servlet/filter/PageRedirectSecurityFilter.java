package by.htp.procurement.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = {"/jsp/evaluation/add_criteria.jsp", "/jsp/evaluation/proposal_pues.jsp", "/jsp/evaluation/winner.jsp"}
        , initParams = {@WebInitParam(name = "INDEX_PATH", value = "/index.jsp")})
public class PageRedirectSecurityFilter implements Filter {

    private String indexPath;
    private static final String PARAM_PATH="INDEX_PATH";

    public void init(FilterConfig fConfig) throws ServletException {
        indexPath = fConfig.getInitParameter(PARAM_PATH);
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendRedirect(req.getContextPath() + indexPath);
        chain.doFilter(request, response);
    }
}
