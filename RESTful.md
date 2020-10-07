# RESTful Web Services

## Web Services

### Key Terminology
Request and Response: Request is the input to a web service, and the response is the output from a web service.

Message Exchange Format: It is the format of the request and response. There are two popular message exchange formats: XML and JSON.

Service Provider or Server: Service provider is one which hosts the web service.

Service Consumer or Client: Service consumer is one who is using the web service.

Service Definition: Service definition is the contract between the service provider and service consumer. Service definition defines the format of request and response, request structure, response structure, and endpoint.

Transport: Transport defines how a service is called. There is two popular way of calling a service: HTTP and Message Queue (MQ). By tying the URL of service, we can call the service over the internet. MQ communicates over the queue. The service requester puts the request in the queue. As soon as the service provider listens to the request. It takes the request, process the request, and create a response, and put the response back into MQ. The service requester gets the response from the queue. The communication happens over the queue.

## RESTful
REST stands for REpresentational State Transfer.

Three keywords in RESTful API:

1. **Resources:** everything is a resource, use URL to represent a resource.

2. **Representation:** pass the representation of resources between client and server, like JSON and XML format.

3. **State Transfer:** client uses HTTP methods to operate resources on server, like GET, POST, PUT and DELETE.

The main goal of RESTful web services is to make web services more effective. RESTful web services try to define services using the different concepts that are already present in HTTP. REST is an architectural approach, not a protocol.

It does not define the standard message exchange
 format. We can build REST services with both XML
  and JSON. JSON is more popular format with REST. 
  
The key abstraction is a resource in REST. A resource can be anything. It can be accessed through a Uniform Resource Identifier (URI). For example: The resource has representations like XML, HTML, and JSON. The current state capture by representational resource. When we request a resource, we provide the representation of the resource.

### HTTP Methods
REST APIs enable you to develop any kind of web application having all possible CRUD (create, retrieve, update, delete) operations. REST guidelines suggest using a specific HTTP method on a particular type of call made to the server.

#### HTTP GET
Use GET requests to retrieve resource representation/information only – and not to modify it in any way. As GET requests do not change the state of the resource, these are said to be safe methods. Additionally, GET APIs should be idempotent, which means that making multiple identical requests must produce the same result every time until another API (POST or PUT) has changed the state of the resource on the server.

If the Request-URI refers to a data-producing process, it is the produced data which shall be returned as the entity in the response and not the source text of the process, unless that text happens to be the output of the process.

For any given HTTP GET API, if the resource is found on the server, then it must return HTTP response code 200 (OK) – along with the response body, which is usually either XML or JSON content (due to their platform-independent nature).

In case resource is NOT found on server then it must return HTTP response code 404 (NOT FOUND). Similarly, if it is determined that GET request itself is not correctly formed then server will return HTTP response code 400 (BAD REQUEST).

> Summary: 
>
> > GET requests can be used to retrieve resources
>
> > GET requests are safe methods, they do not change the state of the resource
>
> > GET requests are idempotent
>
> > GET requests can be cached
>
> > GET requests remain in the browser history
>
> > GET requests can be bookmarked
>
> > GET requests should never be used when dealing with sensitive data
>
> > GET requests have length restrictions
>
> > GET requests are only used to request data (not modify)

#### HTTP POST
Use POST APIs to create new subordinate resources, e.g., a file is subordinate to a directory containing it or a row is subordinate to a database table. When talking strictly in terms of REST, POST methods are used to create a new resource into the collection of resources.

Ideally, if a resource has been created on the origin server, the response SHOULD be HTTP response code 201 (Created) and contain an entity which describes the status of the request and refers to the new resource, and a Location header.

Many times, the action performed by the POST method might not result in a resource that can be identified by a URI. In this case, either HTTP response code 200 (OK) or 204 (No Content) is the appropriate response status.

Responses to this method are not cacheable, unless the response includes appropriate Cache-Control or Expires header fields.

Please note that POST is neither safe nor idempotent, and invoking two identical POST requests will result in two different resources containing the same information (except resource ids).

> Summary: 
>
> > POST requests can be used to create/update resources
>
> > POST requests are not safe methods
>
> > POST requests are not idempotent
>
> > POST requests are never cached
>
> > POST requests do not remain in the browser history
>
> > POST requests requests cannot be bookmarked
>
> > POST requests requests have no restrictions on data length

##### What is the different between GET and POST?
1. GET is used to retrieve resources; POST is used to create or update resources

2. GET is idempotent, but POST is not

3. GET can be bookmarked and remained in the history, but POST cannot

4. GET is harmless when using back button/reload, but POST will re-submit data

5. GET has restrictions on data length, because GET adds data to URL, and the length of URL is limited; POST has no restrictions

6. GET has restrictions on data type, only ASCII characters allowed; POST has no restrictions 

7. GET is less secure compared to POST because data sent is part of the URL

8. GET is faster than POST when accessing resources, 
because GET can be cached and POST need to send request head first, then 
receive 100 Continue response, and send request body. 

#### HTTP PUT
Use PUT APIs primarily to update existing resource (if the resource does not exist, then API may decide to create a new resource or not). If a new resource has been created by the PUT API, the origin server MUST inform the user agent via the HTTP response code 201 (Created) response and if an existing resource is modified, either the 200 (OK) or 204 (No Content) response codes SHOULD be sent to indicate successful completion of the request.

If the request passes through a cache and the Request-URI identifies one or more currently cached entities, those entries SHOULD be treated as stale. Responses to this method are not cacheable.

> Summary: 
>
> > PUT requests can be used to create/update resources
>
> > PUT requests are not idempotent
>
> > PUT can not be cached

##### What is the different between POST and PUT?
1. PUT is used to update a single resource; POST is used to create a resource

2. PUT is idempotent; POST is not. For example: when we create a blog by sending multiple POST requests, 
we will create multiple blog; but if we send PUT multiple requests, we will only create one blog.


#### HTTP DELETE
As the name applies, DELETE APIs are used to delete resources (identified by the Request-URI).

A successful response of DELETE requests SHOULD be HTTP response code 200 (OK) if the response includes an entity describing the status, 202 (Accepted) if the action has been queued, or 204 (No Content) if the action has been performed but the response does not include an entity.

DELETE operations are idempotent. If you DELETE a resource, it’s removed from the collection of resources. Repeatedly calling DELETE API on that resource will not change the outcome – however, calling DELETE on a resource a second time will return a 404 (NOT FOUND) since it was already removed. Some may argue that it makes the DELETE method non-idempotent. It’s a matter of discussion and personal opinion.

If the request passes through a cache and the Request-URI identifies one or more currently cached entities, those entries SHOULD be treated as stale. Responses to this method are not cacheable.

#### HTTP PATCH
HTTP PATCH requests are to make partial update on a resource. If you see PUT requests also modify a resource entity, so to make more clear – PATCH method is the correct choice for partially updating an existing resource, and PUT should only be used if you’re replacing a resource in its entirety.

#### HTTP HEAD
HEAD is almost identical to GET, but without the response body.

In other words, if GET /users returns a list of users, then HEAD /users will make the same request but will not return the list of users.

HEAD requests are useful for checking what a GET request will return before actually making a GET request - like before downloading a large file or response body.

#### HTTP OPTIONS
The OPTIONS method describes the communication options for the target resource.

### HTTP also defines the following standard status code
404: RESOURCE NOT FOUND

200: SUCCESS

201: CREATED

401: UNAUTHORIZED

500: SERVER ERROR

### RESTful Service Constraints
There must be a service producer and service consumer.

The service is stateless.

The service result must be cacheable.

The interface is uniform and exposing resources.

The service should assume a layered architecture.

### Advantages of RESTful web services
RESTful web services are platform-independent.

It can be written in any programming language and can be executed on any platform.

It provides different data format like JSON, text, HTML, and XML.

It is fast in comparison to SOAP because there is no strict specification like SOAP.

These are reusable.

They are language neutral.

