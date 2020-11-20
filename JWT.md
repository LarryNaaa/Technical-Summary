# Spring Security

# JSON Web Token
[JSON Web Token 入门教程](http://www.ruanyifeng.com/blog/2018/07/json_web_token-tutorial.html)

![JWT](https://pic1.zhimg.com/80/v2-dff0fd056340557d72755bdaa41503f0_720w.jpg)

![](https://miro.medium.com/max/700/0*xLJ-xTHdjY0LA_mb.png)

1. The client calls authentication service, like POST /users/login with username and password.
2. The server creates a JWT with a secret, return it to the client.
3. The client requests access to a secured service and sends the JWT on the Authorization Header.
4. Security layer checks the signature on the token and if it's genuine the access is granted. And then get user information from the JWT and send response to the client.

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
The header typically consists of two parts: the type of the token, which is JWT, and the signing algorithm being used, such as HMAC SHA256 or RSA.

### Payload
The second part of the token is the payload, which contains the claims. Claims are statements about an entity (typically, the user) and additional data. There are three types of claims: registered, public, and private claims.

### Signature
The signature is used to verify the message wasn't changed along the way, and, in the case of tokens signed with a private key, it can also verify that the sender of the JWT is who it says it is.


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

