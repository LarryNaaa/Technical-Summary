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
Before we start secure our application, the first step is to allow users to register themselves. So first we create a new entity class called `User`:
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
This entity class contains some properties:

> + the `id` that works as the primary identifier of a user instance in the application,

> + the `username` that will be used by users to identify themselves,

> + and the `password` to check the user identity.

#### Repository Class
In order to manage the persistence layer of this entity, we will create an interface called `UserRepository`. This interface will be an extension of `CrudRepository`, which gives us access to some common methods like `save`, `findById`, `findAll`, `Delete`:

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

All it does is encrypt the password of the new user (holding it as plain text wouldn't be a good idea) and then save it to the database. The encryption process is handled by an instance of `BCryptPasswordEncoder`, which is a class that belongs to the Spring Security framework.

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

### User Authentication and Authorization on Spring Boot










