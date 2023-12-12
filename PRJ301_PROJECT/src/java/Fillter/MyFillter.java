/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package Fillter;

import Models.*;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author Thành Vinh
 */
public class MyFillter implements Filter {

    private static final boolean debug = true;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public MyFillter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MyFillter:DoBeforeProcessing");
        }

        // Write code here to process the request and/or response before
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log items on the request object,
        // such as the parameters.
        /*
	for (Enumeration en = request.getParameterNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    String values[] = request.getParameterValues(name);
	    int n = values.length;
	    StringBuffer buf = new StringBuffer();
	    buf.append(name);
	    buf.append("=");
	    for(int i=0; i < n; i++) {
	        buf.append(values[i]);
	        if (i < n-1)
	            buf.append(",");
	    }
	    log(buf.toString());
	}
         */
    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("MyFillter:DoAfterProcessing");
        }

        // Write code here to process the request and/or response after
        // the rest of the filter chain is invoked.
        // For example, a logging filter might log the attributes on the
        // request object after the request has been processed. 
        /*
	for (Enumeration en = request.getAttributeNames(); en.hasMoreElements(); ) {
	    String name = (String)en.nextElement();
	    Object value = request.getAttribute(name);
	    log("attribute: " + name + "=" + value.toString());

	}
         */
        // For example, a filter might append something to the response.
        /*
	PrintWriter respOut = new PrintWriter(response.getWriter());
	respOut.println("<P><B>This has been appended by an intrusive filter.</B>");
         */
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession ses = req.getSession();
        Accounts a = (Accounts) ses.getAttribute("user");
        String url = req.getRequestURI();
        if (a == null) {
            // Người dùng chưa đăng nhập
            if (!url.contains("/Admin") && !url.contains("/Member")) {
                chain.doFilter(request, response);
            } else {
                // Người dùng chưa đăng nhập và cố gắng truy cập
                res.sendRedirect(req.getContextPath() + "/Home");
            }
//        } else {
//            Accounts a = (Accounts) ses.getAttribute("user");
//            if (a.getIsAdmin() == 1) {
//                // Admin
//                if (!url.contains("/Admin")) {
//                    res.sendRedirect(req.getContextPath() + "/Admin/ManagerProduct");
//                } else {
//                    chain.doFilter(request, response);
//                }
//            } else if (a.getIsAdmin() == 0) {
//                // Khách
//                if (url.contains("/ManagerProduct")) {
//                    res.sendRedirect(req.getContextPath() + "/Member/Home");
//                } else {
//                    chain.doFilter(request, response);
//                }
//            } else {
//                // Người xem (không phải Admin hoặc Khách)
//                chain.doFilter(request, response);
//            }
//        }

            //==============================================//
//        if (ses.getAttribute("account") == null) {
//            // If user is not logged in, allow access to non-restricted pages
//            if (!url.contains("/Admin") && !url.contains("/Member")) {
//                chain.doFilter(request, response);
//            } else {
//                // If user is not logged in and tries to access restricted pages, redirect to login
//                res.sendRedirect(req.getContextPath() + "/Login");
//            }
        } else {
//            Accounts a = (Accounts) ses.getAttribute("user");
            if (a.getIsAdmin() == 1) {
//                if (!url.contains("/Admin") ) {
//                    res.sendRedirect(req.getContextPath() + "/Admin/ManagerProduct");
//                } else {
                chain.doFilter(request, response);
//                }
            } else {
                if (a.getIsAdmin() == 0) {
                    if (url.contains("Admin")) {
                        res.sendRedirect(req.getContextPath() + "/Member/Cart");
                    } else {
                        chain.doFilter(request, response);
                    }
                } else if (a.getIsAdmin() != 0 && a.getIsAdmin() != 1) {
                    if (url.contains("Admin") || url.contains("Member")) {
                        res.sendRedirect(req.getContextPath() + "/Home");
                    } else {
                        chain.doFilter(request, response);
                    }

                }
            }
        }
//        if (ses.getAttribute("account") == null) {
//
//            // If user is not logged in, allow access to non-restricted pages
//            if (!url.contains("/admin") && !url.contains("/poster")) {
//                chain.doFilter(request, response);
//            } else {
//                // If user is not logged in and tries to access restricted pages, redirect to login
//                res.sendRedirect(req.getContextPath() + "/login");
//            }
//        } else {
//            Users u = (Users) ses.getAttribute("account");
//            if (u.getRole_id() == 1) {
//                chain.doFilter(request, response);
//            } else {
//                if (u.getRole_id()== 3) {
//                    if (url.contains("admin")) {
//                        res.sendRedirect(req.getContextPath() + "/poster/posterMovie");
//                    } else {
//                        chain.doFilter(request, response);
//                    }
//
//                } else if (u.getRole_id()== 2) {
//                    if (url.contains("admin") || url.contains("member")) {
//                        res.sendRedirect(req.getContextPath() + "/home");
//                    } else {
//                        chain.doFilter(request, response);
//                    }
//
//                }
//            }
//        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {
                log("MyFillter:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("MyFillter()");
        }
        StringBuffer sb = new StringBuffer("MyFillter(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

}
