# JSON Web Token
JSON web token (JWT), pronounced "jot", is an open standard (RFC 7519) that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. Again, JWT is a standard, meaning that all JWTs are tokens, but not all tokens are JWTs.

Because of its relatively small size, a JWT can be sent through a URL, through a POST parameter, or inside an HTTP header, and it is transmitted quickly. A JWT contains all the required information about an entity to avoid querying a database more than once. The recipient of a JWT also does not need to call a server to validate the token.

[JSON Web Token 入门教程](http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)

![JWT](https://pic1.zhimg.com/80/v2-dff0fd056340557d72755bdaa41503f0_720w.jpg)

![](https://miro.medium.com/max/700/0*xLJ-xTHdjY0LA_mb.png)

1. The client calls authentication service, like POST /users/login with username and password.
2. The server creates a JWT with a secret, return it to the client.
3. The client requests access to a secured service and sends the JWT on the Authorization Header.
4. Security layer checks the signature on the token and if it's genuine the access is granted. And then get user information from the JWT and send response to the client.

## Securing RESTful APIs with JWTs
Applications use JWTS no longer have to hold cookies or other session data about their users.

During the authentication process, when a user successfully logs in using their credentials, a JSON Web Token is returned and must be saved locally (typically in local storage). Whenever the user wants to access a protected route or resource (an endpoint), the user agent must send the JWT, usually in the Authorization header using the Bearer schema, along with the request.

When a backend server receives a request with a JWT, the first thing to do is to validate the token. This consists of a series of steps, and if any of these fails then, the request must be rejected. The following list shows the validation steps needed:

> > Check that the JWT is well formed
> > Check the signature
> > Validate the standard claims
> > Check the Client permissions (scopes)



## Authorization/Access Control
![authorization/access control](https://pic1.zhimg.com/v2-63dea95302a35394bc33e6d4a3f9699c_r.jpg)
1. Client send authentication request with username and password to UserController. 
2. JwtAuthenticationFilter(extends OncePerRequestFilter) checks if the request has token
3. 


## Authentication
![authentication](https://pic4.zhimg.com/80/v2-d7cb2969c0ed4adbc099c21bc36b274b_720w.jpg)

## JWT
JSON Web Token (JWT) defines a compact and self-contained way for securely transmitting information between parties as a JSON object. 

JWT contains three parts: header, payload, signature.

### Header
It contains metadata about the type of token and the cryptographic algorithms used to secure its contents.

### Payload
It is a set of claims and contains verifiable security statements, such as the identity of the user and the permissions they are allowed.

### Signature
It  used to validate that the token is trustworthy and has not been tampered with. When you use a JWT, you must check its signature before storing and using it.

![JWT](https://images.ctfassets.net/cdy7uua7fh8z/7FI79jeM55zrNGd6QFdxnc/80a18597f06faf96da649f86560cbeab/encoded-jwt3.png)

## Why do you use Spring Security and JWT?
### JWT
1. Create a RESTful API, the server should be stateless, so it cannot store session. 

2. JWT is small so it can be sent with each request like a session cookie. But the different between them is that JWT has data, it does not point to any data stored on the server. A JWT contains three parts: header, payload, signature. When the server receives a JWT, it can already retrieve all the information directly from the token, it does not need to do extra work.

3. JWT can carry its own expiry date along with the user data. Therefore the security layer can check both JWT's authenticity and the expiry time, if the token is expired, then the access will be refused.

### Spring Security
1. It supports authentication and authorization.
2. It can be integrated with Spring Boot

## What are the difference between JWT and OAuth?
1. JWT is a token format, OAuth is an standardised authorization protocol that can use JWT as a token.


## Process of implement JWT
### Enabling User Registration on Spring Boot APIs
#### Entity Class
Before we start secure our application, the first step is to allow users to register themselves. So first we create a new entity class called `User`, which implements `UserDetails`:
```java
package com.jinyu.ppmtool.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email(message = "Username needs to be an email")
    @NotBlank(message = "username is required")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "Please enter your full name")
    private String fullName;
    @NotBlank(message = "Password field is required")
    private String password;
    @Transient
    private String confirmPassword;
    private Date create_At;
    private Date update_At;

    //OneToMany with Project
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "user", orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getCreate_At() {
        return create_At;
    }

    public void setCreate_At(Date create_At) {
        this.create_At = create_At;
    }

    public Date getUpdate_At() {
        return update_At;
    }

    public void setUpdate_At(Date update_At) {
        this.update_At = update_At;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @PrePersist
    protected void onCreate(){
        this.create_At = new Date();
    }

    @PreUpdate
    protected void onUpdate(){
        this.update_At = new Date();
    }

     /*
    UserDetails interface methods
     */

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
```
This entity class contains some properties like:

> + the `id` that works as the primary identifier of a user instance in the application,
> + the `username` that will be used by users to identify themselves,
> + and the `password` to check the user identity.

We use some annotations to do validation for some attributes of user, like `@NotBlank` on `username`, when the username is blank, it will display a message to user that username is required. `@Email` annotation on `username`, it requires that the style of username needs to be an email.

`UserDetails` is an interface in Spring Security, we can override some methods like `isAccountNonExpired`.

`@JsonIgnore` annotation make the overriding methods of `UserDetails` not be a part of JSON object.

#### Repository Class
In order to manage the persistence layer of this entity, we will create an interface called `UserRepository`. This interface will be an extension of `CrudRepository`, which is an interface in Spring framework and gives us access to some common methods like `save`, `findById`, `findAll`, `Delete`:

```java
package com.jinyu.ppmtool.repositories;

import com.jinyu.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User getById(Long id);
}
```

We have also added two methods called `findByUsername` and `getById` to this interface. These methods will be used when we implement the authentication feature.

#### Service Class
We need to create a service class which called `UserService` to store new users in our database:

```java
package com.jinyu.ppmtool.services;

import com.jinyu.ppmtool.domain.User;
import com.jinyu.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.jinyu.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser (User newUser){
        try{
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            //Username has to be unique (exception)
            newUser.setUsername(newUser.getUsername());
            // Make sure that password and confirmPassword match
            // We don't persist or show the confirmPassword
            newUser.setConfirmPassword("");
            return userRepository.save(newUser);

        }catch (Exception e){
            throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
        }

    }
}

```

##### Encrypt Password
All it does is encrypt the password of the new user (make password unreadable instead of holding it as a plain text) and then save it to the database. The encryption process is handled by an instance of `BCryptPasswordEncoder`, which is a class that belongs to the Spring Security framework.

Before we use the instance of `BCryptPasswordEncoder`, we need to generate it, This method must be annotated with `@Bean` and we will add it in the `PpmtoolApplication` class, which is the main `SpringBootApplication`:
```java
package com.jinyu.ppmtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PpmtoolApplication {

	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(PpmtoolApplication.class, args);
	}

}
```
##### Throw UsernameAlreadyExistsException
The username is unique, when user register with a username that is already existed, the apllication will display a message to user that they need to choose some other username.

The `CustomResponseEntityExceptionHandler` class manage all exception handlers
```java
package com.jinyu.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectIdException(ProjectIdException ex, WebRequest request){
        ProjectIdExceptionResponse exceptionResponse = new ProjectIdExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request){
        ProjectNotFoundExceptionResponse exceptionResponse = new ProjectNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleUsernameAlreadyExists(UsernameAlreadyExistsException ex, WebRequest request){
        UsernameAlreadyExistsResponse exceptionResponse = new UsernameAlreadyExistsResponse(ex.getMessage());
        return new ResponseEntity<Object>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
```


#### Controller Class
The endpoint that enables new users to register will be handled by a new `@Controller` class. We will call this controller `UserController`:

```java
package com.jinyu.ppmtool.web;

import com.jinyu.ppmtool.domain.User;
import com.jinyu.ppmtool.payload.JWTLoginSucessReponse;
import com.jinyu.ppmtool.payload.LoginRequest;
import com.jinyu.ppmtool.security.JwtTokenProvider;
import com.jinyu.ppmtool.services.MapValidationErrorService;
import com.jinyu.ppmtool.services.UserService;
import com.jinyu.ppmtool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.jinyu.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        // Validate passwords match
        userValidator.validate(user, result);

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null)return errorMap;

        User newUser = userService.saveUser(user);

        return  new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}

```
We have a method `registerUser` to save new users in our database, in this method we need to validate the password through `UserValidator` class. It implements `Validator` interface, we override `validate` method to make sure the length of password must be at least 6 characters and the password and the confirm password are matched:
```java
package com.jinyu.ppmtool.validator;

import com.jinyu.ppmtool.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {

        User user = (User) object;

        if(user.getPassword().length() <6){
            errors.rejectValue("password","Length", "Password must be at least 6 characters");
        }

        //confirmPassword
        if(!user.getPassword().equals(user.getConfirmPassword())){
            errors.rejectValue("confirmPassword","Match", "Passwords must match");

        }
    }
}
``` 

### User Authentication and Authorization on Spring Boot
To support both authentication and authorization in our application, we are going to:

> + implement an authentication filter to issue JWTS to users sending credentials,
> + implement an authorization filter to validate requests containing JWTS,
> + create a custom implementation of `UserDetailsService` to help Spring Security loading user-specific data in the framework,
> + and extend the `WebSecurityConfigurerAdapter` class to customize the security framework to our needs.

#### The Authentication Filter
The first element that we are going to create is the class responsible for the authentication process. We are going to call this class `JwtAuthenticationFilter`, and we will implement it with the following code:

```java
package com.jinyu.ppmtool.security;

import com.jinyu.ppmtool.domain.User;
import com.jinyu.ppmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import static com.jinyu.ppmtool.security.SecurityConstants.HEADER_STRING;
import static com.jinyu.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {

            String jwt = getJWTFromRequest(httpServletRequest);

            if(StringUtils.hasText(jwt)&& tokenProvider.validateToken(jwt)){
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }catch (Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }



    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HEADER_STRING);

        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }
}

```

Now we have to create the `SecurityConstants` class:

```java
package com.jinyu.ppmtool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET ="SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX= "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 300_000; //300 seconds
}
```
This class contains some constants referenced by the `JwtAuthenticationFilter` class.

#### The Authorization Filter
As we have implemented the filter responsible for authenticating users, we now need to implement the filter responsible for user authorization. We create this filter as a new class, called `JWTAuthorizationFilter`:

```java
package com.jinyu.ppmtool.security;

import com.jinyu.ppmtool.domain.User;
import com.jinyu.ppmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

import static com.jinyu.ppmtool.security.SecurityConstants.HEADER_STRING;
import static com.jinyu.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {

            String jwt = getJWTFromRequest(httpServletRequest);

            if(StringUtils.hasText(jwt)&& tokenProvider.validateToken(jwt)){
                Long userId = tokenProvider.getUserIdFromJWT(jwt);
                User userDetails = customUserDetailsService.loadUserById(userId);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.emptyList());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }

        }catch (Exception ex){
            logger.error("Could not set user authentication in security context", ex);
        }


        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }



    private String getJWTFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader(HEADER_STRING);

        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith(TOKEN_PREFIX)){
            return bearerToken.substring(7, bearerToken.length());
        }

        return null;
    }
}

```

We have extended the `OncePerRequestFilter` to make Spring replace it in the filter chain with our custom implementation. The most important part of the filter that we've implemented is the private `getJWTFromRequest` method. This method reads the JWT from the Authorization header, and then uses JWT to validate the token. If everything is in place, we set the user in the `SecurityContext` and allow the request to move on.


#### Integrating the Security Filters on Spring Boot
Now that we have both security filters properly created, we have to configure them on the Spring Security filter chain. To do that, we are going to create a new class called `SecurityConfig` to extend `WebSecurityConfigurerAdapter`. `WebSecurityConfigurerAdapter` is an abstract class in Spring Security, it provides default security configurations. By extending it, we are allowed to customize those security configurations by overriding some of the methods:

```java
package com.jinyu.ppmtool.security;

import com.jinyu.ppmtool.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.jinyu.ppmtool.security.SecurityConstants.H2_URL;
import static com.jinyu.ppmtool.security.SecurityConstants.SIGN_UP_URLS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {return  new JwtAuthenticationFilter();}

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// enable Cross-Origin Resource Sharing and disable Cross-Site Request Forgery
        http.cors().and().csrf().disable()
                // to handle exception, return a JSON object to tell users that their // username or password is invalid
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // manage session, it's a RESTful API and we want to use JWT, so the  // server should not hold a session, the SessionCreationPolicy should // be STATELESS.
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // to enable H2 Database
                .headers().frameOptions().sameOrigin() 
                .and()
                // to specify some of the routes that we want to make them public,
                // the suffix of the routes are png, jpg, html, js, css...
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .antMatchers(SIGN_UP_URLS).permitAll()
                .antMatchers(H2_URL).permitAll()
                // any thing other than that should have an authentication
                .anyRequest().authenticated();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
```

We have annotated this class with `@EnableWebSecurity` and made it extend `WebSecurityConfigurerAdapter` to take advantage of the default web security configuration provided by Spring Security. This allows us to fine-tune the framework to our needs by defining three methods:

##### `configure(HttpSecurity http)` method
a method where we can define which resources are public and which are secured: 
> + In our case, we set the urls of user login, sign-up(`SIGN_UP_URL`) and some other routes(which suffix are png, jpg, html, css...) as being public and everything else as being secured. 
> + We also configure CORS (Cross-Origin Resource Sharing) support through `http.cors()` and disable CSRF(Cross-Site Request Forgery) through `csrf().disable()`. 
> + We configure the session, it's a RESTful API and we want to use JWT, so the server should not hold a session, the `SessionCreationPolicy` should be STATELESS.
> + We configure the exception handling by using `.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)`. 

In order to handle the exception, we need to create a new class called `JwtAuthenticationEntryPoint`, which implements `AuthenticationEntryPoint` , this class will display some massage to users instead of an error(401 unauthorized) when the users are fail for the authentication:
```java
package com.jinyu.ppmtool.security;

import com.google.gson.Gson;
import com.jinyu.ppmtool.exceptions.InvalidLoginResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// for autowired
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                         AuthenticationException e) throws IOException, ServletException {

        InvalidLoginResponse loginResponse = new InvalidLoginResponse();
        String jsonLoginResponse = new Gson().toJson(loginResponse);

        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().print(jsonLoginResponse);
    }
}
```
When the users are fail for the authentication, it will return a JSON object(`InvalidLoginResponse`) to tell them that their username or password is invalid:
```java
package com.jinyu.ppmtool.exceptions;

public class InvalidLoginResponse {
    private String username;
    private String password;

    public InvalidLoginResponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

##### `configure(AuthenticationManagerBuilder auth)`
 a method where we defined a custom implementation of `UserDetailsService` to load user-specific data in the security framework. We have also used this method to set the encrypt method used by our application (`BCryptPasswordEncoder`).

Spring Security doesn't come with a concrete implementation of `UserDetailsService` that we could use out of the box with our in-memory database. Therefore, we create a new class called `CustomUserDetailsService`:

```java
package com.jinyu.ppmtool.services;


import com.jinyu.ppmtool.domain.User;
import com.jinyu.ppmtool.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null) new UsernameNotFoundException("User not found");
        return user;
    }


    @Transactional
    public User loadUserById(Long id){
        User user = userRepository.getById(id);
        if(user==null) new UsernameNotFoundException("User not found");
        return user;

    }
}
```

The methods that we had to implement are `loadUserByUsername` and `loadUserById`. When a user tries to authenticate, these method receives the `username` or `id`, searches the database for a record containing it, and (if found) returns an instance of User. The properties of this instance (username and password) are then checked against the credentials passed by the user in the login request. This last process is executed outside this class, by the Spring Security framework.



