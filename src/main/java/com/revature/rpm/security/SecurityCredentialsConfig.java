package com.revature.rpm.security;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This currently checks if the current User is allowed to access a certain end point using their
 * role.
 */
@EnableWebSecurity
public class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter {

  private ZuulConfig zuulConfig;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        // Disables protection against Cross-Site Request Forgery, else requests can't
        // be made from zuul-service
        .csrf()
        .disable()

        // Ensures a stateless session; session will not store user information
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()

        // Handle any authentication exceptions by sending an Unauthorized 401 response
        .exceptionHandling()
        .authenticationEntryPoint(
            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()

        // Add customized filter to check for Zuul header, or if request is to get
        // information from the Actuator
        .addFilterBefore(
            new CustomAuthenticationFilter(zuulConfig), UsernamePasswordAuthenticationFilter.class)
        .authorizeRequests()

        // Allow GET requests to the "/" and "/history" endpoints
        .mvcMatchers(HttpMethod.GET, "/")
        .permitAll()
        .mvcMatchers(HttpMethod.GET, "/history")
        .permitAll()

        // Allow POST requests to the "/auth" and "/auth/users" endpoints
//        .mvcMatchers(HttpMethod.POST, "/auth")
//        .permitAll()
//        .mvcMatchers(HttpMethod.POST, "/project")
//        .hasAnyRole("ADMIN", "USER")
        
        // Allow PATCH requests to the "/" and "/unread/" endpoints
        .mvcMatchers(HttpMethod.PATCH, "/")
        .permitAll()
        .mvcMatchers(HttpMethod.PATCH, "/unread/")
        .permitAll()
        
        // Allow only admins to access the h2-console
        .mvcMatchers("/h2-console/**")
        .hasRole("ADMIN")

        // Allow unrestricted access to the actuator/info endpoint.
        // Otherwise, AWS ELB cannot perform a health check on the instance and it
        // drains the instances.
        .antMatchers(HttpMethod.GET, "/actuator/info")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/actuator/health")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/actuator/**")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/actuator/routes")
        .permitAll()

        // Allow unrestricted access to swagger's documentation
        .antMatchers(
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**")
        .permitAll()

        // All other requests must be authenticated
        .anyRequest()
        .authenticated();
  }

  @Bean
  public ZuulConfig zuulConfig() {
    return new ZuulConfig();
  }

  @Autowired
  public void setZuulConfig(ZuulConfig zuulConfig) {
    this.zuulConfig = zuulConfig;
  }
}
