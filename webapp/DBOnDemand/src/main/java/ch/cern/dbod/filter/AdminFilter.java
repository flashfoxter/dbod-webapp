package ch.cern.dbod.filter;

import ch.cern.dbod.util.CommonConstants;
import ch.cern.dbod.util.EGroupHelper;
import ch.cern.dbod.util.HTTPHelper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filters requests to only allow admin users to visit admin content.
 * @author Daniel Gomez Blanco
 */
public class AdminFilter implements Filter{

    /**
     * Init method
     * @param fc filter config
     */
    @Override
    public void init(FilterConfig fc){}

    /**
     * Destroy method
     */
    @Override
    public void destroy() {}

    /**
     * Filters the request.
     * @param request servlet request.
     * @param response servlet response.
     * @param filterChain filter chain.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) {
        try {
            //Get groups
            String eGroups = ((HttpServletRequest) request).getHeader(CommonConstants.ADFS_GROUP);

            //If user is not admin redirect to unauthorized
            if (EGroupHelper.groupInList(CommonConstants.ADMIN_E_GROUP, eGroups))
                filterChain.doFilter(request, response);
            else
                HTTPHelper.redirect((HttpServletRequest) request, (HttpServletResponse) response, CommonConstants.PAGE_NOT_AUTHORIZED);
        }
        catch (IOException | ServletException ex) {
            Logger.getLogger(AdminFilter.class.getName()).log(Level.SEVERE, "ERROR IN ADMIN FILTER", ex);
            HTTPHelper.redirect((HttpServletRequest) request, (HttpServletResponse) response, CommonConstants.PAGE_ERROR);
        }
    }
}