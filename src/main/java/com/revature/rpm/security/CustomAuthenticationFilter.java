package com.revature.rpm.security;

import com.revature.rpm.exceptions.SubversionAttemptException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

/**
 * The purpose of this class is to create custom filters for this service to only accept and
 * authorize requests from zuul or to anything that wants info about the health of the service.
 * Currently, it looks for this header from zuul using the ZuulConfig. object. If anything tries to
 * hit an end point for actuator it lets it through regardless of headers. This is allowed for now
 * as ELB on AWS will check actuator/info in order to evaluate the health of the service. If it does
 * not get through to that end point, ELB will shut down the service and start a new one within 3
 * minutes.
 */
public class CustomAuthenticationFilter extends GenericFilterBean {

  private ZuulConfig zuulConfig;
  private static Logger logger = Logger.getLogger("DRIVER_LOGGER");

  /**
   * Constructor for CustomAuthenticationFilter that instantiates the ZuulConfig field.
   *
   * @param zuulConfig Provides configuration for validating that requests came through Zuul
   */
  public CustomAuthenticationFilter(ZuulConfig zuulConfig) {
    this.zuulConfig = zuulConfig;
  }

  /** This is the filter that is used to authenticate specific traffic. */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String headerZuul = httpRequest.getHeader(zuulConfig.getHeader());
    try {
      // Checks if the actuator end point is being hit or that the zuul header is present.
      if (httpRequest.getRequestURI().contains("/project/actuator")) {
        Authentication auth =
            new AccessAuthenticationToken(headerZuul, "ROLE_ACTUATOR", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
      } else if (validateHeader(headerZuul)) {
        Authentication auth =
            new AccessAuthenticationToken(headerZuul, "ROLE_USER", new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(auth);
      } else {
        /*
         * In case of attempted subversion around Zuul we want to invalidate the
         * session, so we can guarantee that the user will not be authenticated
         */
        SecurityContextHolder.clearContext();
        ((HttpServletResponse) response).setStatus(401);
        // Log this
        throw new SubversionAttemptException("ZUUL header is " + headerZuul);
      }
      System.out.println(headerZuul);
    } catch (SubversionAttemptException e) {
      e.printStackTrace();
      String ipAddress = ((HttpServletRequest) request).getHeader("X-FORWARDED-FOR");
      if (ipAddress == null) {
        ipAddress = request.getRemoteAddr();
      }

      String appUrl = request.getScheme() + "://" + request.getLocalAddr();
      logger.log(Level.INFO, "URL:" + appUrl);
    }
    // Activates the next filter if there is any.
    filterChain.doFilter(request, response);
  }

  /**
   * Method to perform a SHA-512 hash. Retrieved from: @Link
   * https://stackoverflow.com/questions/33085493/how-to-hash-a-password-with-sha-512-in-java
   */
  public String get_SHA_512_SecureHash(String passwordToHash, String salt) {
    String generatedPassword = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-512");
      md.update(salt.getBytes(StandardCharsets.UTF_8));
      byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++) {
        sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
      }
      generatedPassword = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
    return generatedPassword;
  }

  /**
   * Method to check if the header matches that provided by Zuul
   *
   * @param header The retrieved header from the request object
   */
  public boolean validateHeader(String header) {
    if (header == null) {
      return false;
    }
    return header.equals(get_SHA_512_SecureHash(zuulConfig.getSecret(), zuulConfig.getSalt()));
  }
}
