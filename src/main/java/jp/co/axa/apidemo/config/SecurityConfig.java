package jp.co.axa.apidemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
        .antMatchers("/api/v1/**", "/swagger-ui.html", "/v2/api-docs", "/h2-console/**")
        .hasRole("ADMIN") // Example endpoint that requires ADMIN role
        .anyRequest().authenticated()
        .and()
      .httpBasic().and()
      .csrf().disable() // Disable CSRF protection for simplicity
      .headers().frameOptions().disable(); // Disable X-Frame-Options for H2 console
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("admin")
      .password("{noop}password") // Use {noop} prefix for plain text password
      .roles("ADMIN");
  }
}