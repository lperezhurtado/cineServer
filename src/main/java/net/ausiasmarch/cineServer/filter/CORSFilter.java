package net.ausiasmarch.cineServer.filter;

import java.io.IOException;
import java.util.Collection;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class CORSFilter implements Filter {

      private void addSameSiteCookieAttribute(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        for (String header : headers) { // there can be multiple Set-Cookie attributes
            if (firstHeader) {
                response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict"));
                firstHeader = false;
                continue;
            }
            response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; %s", header, "SameSite=Strict"));
        }
    }
    
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        if (req instanceof HttpServletRequest && res instanceof HttpServletResponse) {

            HttpServletRequest oRequest = (HttpServletRequest) req;
            HttpServletResponse oResponse = (HttpServletResponse) res;
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
            //oResponse.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "3600");        
            response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
            
            if (oRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
                response.setHeader("Access-Control-Allow-Private-Network", "true");
            }
            addSameSiteCookieAttribute((HttpServletResponse) response);
            
            chain.doFilter(req, res);
            

//            if (oRequest.getMethod().equalsIgnoreCase("OPTIONS")) {
//                //https://stackoverflow.com/questions/56479150/access-blocked-by-cors-policy-response-to-preflight-request-doesnt-pass-access
//                //System.out.println("Pre-flight");
//                oResponse.setHeader("Access-Control-Allow-Origin", oRequest.getHeader("origin"));
//                oResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD,PATCH");
//                oResponse.setHeader("Access-Control-Max-Age", "3600");
//                oResponse.setHeader("Access-Control-Allow-Credentials", "true");
//                oResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, "
//                       + "Origin, "
//                       + "Accept, "
//                       + "Authorization, "
//                       + "ResponseType, "
//                       + "Observe, "
//                       + "X-Requested-With, "
//                       + "Content-Type, "
//                       + "Access-Control-Expose-Headers, "
//                       + "Access-Control-Request-Method, "
//                       + "Access-Control-Request-Headers");
//                oResponse.setStatus(HttpServletResponse.SC_OK);
//            } else {
//                oResponse.setHeader("Cache-control", "no-cache, no-store");
//                oResponse.setHeader("Pragma", "no-cache");
//                oResponse.setHeader("Expires", "-1");
//                oResponse.setHeader("Access-Control-Allow-Origin", oRequest.getHeader("origin"));
//                oResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,OPTIONS,HEAD,PATCH");
//                oResponse.setHeader("Access-Control-Max-Age", "86400");
//                oResponse.setHeader("Access-Control-Allow-Credentials", "true");
//                oResponse.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, "
//                       + "remember-me, "
//                       + "Origin, "
//                       + "Accept, "
//                       + "Authorization, "
//                       + "ResponseType, "
//                       + "Observe, "
//                       + "X-Requested-With, "
//                       + "Content-Type, "
//                       + "Access-Control-Expose-Headers, "
//                       + "Access-Control-Request-Method, "
//                       + "Access-Control-Request-Headers");
//                oResponse.setHeader("Access-Control-Allow-Origin", oRequest.getHeader("origin"));
//                oResponse.setHeader("Access-Control-Allow-Private-Network", "true");
//                //response.setHeader("Access-Control-Allow-Origin", "*");
//                oResponse.setHeader("Access-Control-Allow-Credentials", "true");
//                //response.setHeader("Access-Control-Allow-Credentials", "false");
//                oResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
//                oResponse.setHeader("Access-Control-Max-Age", "3600");
//                oResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");
               
//            }
        }

    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}
