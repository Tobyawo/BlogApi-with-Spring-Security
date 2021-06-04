package com.fb2.fb.Security;

import com.fb2.fb.Auth.ApplicationUserService;
import com.fb2.fb.jwt.JwtConfig;
import com.fb2.fb.jwt.JwtTokenVerifier;
import com.fb2.fb.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;
import javax.sql.DataSource;

import static com.fb2.fb.Security.ApplicationUserRole.*;


//using Basic Auth.
//every request must be authenticated i.e the client must specify the username and password
//its simple and fast but one of the drawback of basic auth is that you cant logout
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true) // this enables the use of annotations for authorities on methods
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;
private final SecretKey secretKey;
private final JwtConfig jwtConfig;


    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder,
                                     ApplicationUserService applicationUserService,
                                     SecretKey secretKey,
                                     JwtConfig jwtConfig) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;









    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http
//              .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())// needed when u need forms to be submitted from browser to ur server
//              .and()
              .csrf().disable()
              .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt web token must be stateless. the session wont be stored in a database
              .and()
              .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), secretKey, jwtConfig))  // we have access to authenticationManager bcos its in WebSecurityConfigurerAdapter which we extend this class from
              .addFilterAfter(new JwtTokenVerifier(jwtConfig, secretKey),JwtUsernameAndPasswordAuthenticationFilter.class) // register this filter to perform after the one above
              .authorizeRequests()
                    .antMatchers("/fbindex/*","/index/*","/css/*","/js/*").permitAll() //Whitelisting url: all the pages with any of these url path are excluded from requiring access.

//          The order of ur antMatchers matter. they are executed as arranged here
//              .antMatchers("/admin").permitAll()
              .antMatchers("/api/v1/").permitAll()
              .antMatchers("/admin/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name());//role based authentication
//              .antMatchers("/api/v1/").hasRole(USER.name()) //role based authentication
              //granting permissions and authority to roles. this is an alternative to using annotations "preAuthorize" on methods
//              .antMatchers(HttpMethod.DELETE,"/admin/api/**").hasAuthority(ApplicationUserPermission.ADMIN_DELETE.getPermission()) // only admin has authority to delete
//              .antMatchers(HttpMethod.GET,"/admin/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
//                    .anyRequest()
//                    .authenticated();


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //spring security will use the userDetailsService to get the details of user everytime its called
        auth.userDetailsService(userDetailsService); //this allows u to pass the userDetails instance to the authentication manager builder



    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

    }














//    //  for form based authentication
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
////              .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())// needed when u need forms to be submitted from browser to ur server
////              .and()
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/","index","/css/*","/js/*").permitAll() //Whitelisting url: all the pages with any of these url path are excluded from requiring access.
//
////          The order of ur antMatchers matter. they are executed as arranged here
//                .antMatchers("/api/v1/**").hasRole(USER.name()) //role based authentication
//                //granting permissions and authority to roles. this is an alternative to using annotations "preAuthorize" on methods
////              .antMatchers(HttpMethod.DELETE,"/admin/api/**").hasAuthority(ApplicationUserPermission.ADMIN_DELETE.getPermission()) // only admin has authority to delete
////              .antMatchers(HttpMethod.GET,"/admin/api/**").hasAnyRole(ADMIN.name(),ADMINTRAINEE.name())
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin()//.httpBasic(); //  here u specify either basic authentication or formbased authentication
//                .loginPage("/login").permitAll() // to be able to use ur own customised login page.
//                .defaultSuccessUrl("/users",true) //page to show when successful
//                .passwordParameter("password")// parameter form the login form
//                .usernameParameter("username")
//                .and()
//                .rememberMe()
//                .tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(21)) //customize the duration of ur sessionsId  expiration with remember-me cookies to 21 days or ur choice
//                .key("putSomethingVerySecureHere")
//                .rememberMeParameter("remember-me") // parameter form the login form
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout","GET")) //this line is used when crsf is disabled. it should be deleted when csrf is enabled.
//                .clearAuthentication(true)
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID","remember-me")
//                .logoutSuccessUrl("/login");
//
//    }
//
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(applicationUserService);
//        return provider;
//    }
