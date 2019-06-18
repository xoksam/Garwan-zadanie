package oksa.marek.eshop.controller.security;

import oksa.marek.eshop.controller.security.filters.JWTAuthenticationFilter;
import oksa.marek.eshop.controller.security.filters.JWTAuthorizationFilter;
import oksa.marek.eshop.controller.services.CustomUserDetailsService;
import oksa.marek.eshop.controller.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Controller
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private CustomUserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserService userService;

    public WebSecurity(CustomUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_URL)
                .permitAll()
                .antMatchers(SecurityConstants.AUTHENTICATED_URLS).authenticated()
                .antMatchers(SecurityConstants.ADMIN_URLS).hasAuthority(SecurityConstants.ADMIN_ROLE)
                .anyRequest().authenticated() //Any other requests are authenticated
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

//        auth.inMemoryAuthentication().withUser("admin").roles("ADMIN");

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);


    }

    // allows/restricts our CORS (Cross-Origin-Resource-Sharing) support
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}

