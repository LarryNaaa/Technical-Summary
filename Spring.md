# Spring
Spring Framework is an open-source application framework and Inversion of Control container written in Java.

This is in part due to Spring’s unique selection of functionalities like inversion of control and aspect-oriented programming (AOP) that are great for working with beans.

### What are the benefits of Spring?
Lightweight: Spring is lightweight in resource use, with the basic Spring Framework only costing 2MB of storage.

Scalable: Spring’s transaction management interface can scale to either a local transaction on a single database to global transactions using the JTA module

Exception Handling: Exception handling is easy thanks to plentiful API resources for handling exceptions in each module.

Layered Architecture: Allows you to use the parts of the program you need and discard the rest.

POJO Enabled: Plain Old Java Object Programming allows you continuous testability and integration.

Open-source: Free for everyone and has no vendor lock-in.

Inversion of control (IOC): Achieves loose coupling via IOC by allowing objects to give their dependencies to other objects rather without dependent objects.

Aspect oriented (AOP): Spring supports Aspect-oriented programming, a paradigm that separates application business logic from system services.

### What is the configuration file for Spring?
The configuration file for Spring is an XML file that contains the class information for a project. They describe the configuration of each class, how they’re introduced to other classes, and dependencies across the program.

### What are the different components of a Spring application?
Spring applications contain 5 components:

1. Interface: Defines program functions.

2. Bean class: Contains properties, setter and getter methods for accessing the bean, and specific functions, etc.

3. Spring Aspect-Oriented Programming (AOP): Includes cross-cutting concerns functionality, which is not supported in Object-Oriented Programming.

4. Bean Configuration File: Contains the information of classes, how to configure them, and defines their relationships.

5. User program: Calls functions across the program

### What is dependency injection?
Dependency injection (DI) is a concept that defines how multiple classes should be connected. This is one example of Inversion of Control. You don’t need to connect services and components explicitly in code when using dependency injection. Instead, you describe the services needed by each component in an XML configuration file and allow the IOC container to connect them automatically.

### What is a Spring IoC container?
An IoC container creates, configures, and connects objects while also managing their lifecycle. The container gets instructions on these areas from configuration metadata given by the user.

### What are the types of IoC?
1. BeanFactory Container: This factory class contains a prepackaged collection of beans that instantiate when called by clients. This is the most basic container to support DI.

2. ApplicationContext Container: Built on top of the BeanFactory Container, this container provides additional enterprise-focused functionalities. For example, ApplicationContext containers grant the ability to resolve textual messages and publish application events.

### What is Aspect-Oriented Programming (AOP)?
AOP is a programming technique that allows programmers to modularize behavior that is used across the typical divisions of responsibility found in Object-Oriented Programming. The core AOP construct of aspects are behaviors applicable across classes. Extracting these behaviors from individual beans to aspects allows them to be reused easily.

### What are Spring beans?
Beans are objects created from configuration metadata when it is passed to the IOC container. They form the foundation of all Spring programs. The IOC container instantiates, configures, connects, and manages each bean.

### What are the common implementations of the ApplicationContext?
Three of the most popular containers are:

1. FileSystemXmlApplicationContext: Causes the constructor to load bean definitions from an XML configuration file. Must be pointed to with a full file path.

2. ClassPathXmlApplicationContext: This container does the same as the above but does not require a full file path. Instead, you set the CLASSPATH property and allow the container to find the XML at that CLASSPATH.

3. WebXmlApplicationContext: Loads all bean definitions to a web application from an XML file.

### What is the difference between BeanFactory and ApplicationContext?
The BeanFactory is a basic, space-efficient container with limited functionality. It is best used for simple tasks or when using low-resource machines.

The ApplicationContext is an advanced, more intensive container with an extended interface and additional capabilities like AOP. This container is best used when you need more functionality than the BeanFactory and have ample resources available on the machine.

### How do you add a bean in a Spring application?
We must annotate a method: @Bean annotation. When JavaConfig encounters this method, it will execute that method and register the return value as a bean within a BeanFactory.

### What bean scopes does Spring support?
Spring supports five scopes for beans:

1. Singleton: Scopes a bean definition to be restricted to a single instance per Spring IoC container

2. Prototype: Scopes a single bean to enable any number of instances.

3. Request: Scopes a bean definition to a single HTTP request within an ApplicationContext

4. Session: Scopes a bean definition to an HTTP session within an ApplicationContext

5. Global-session: Scopes a bean definition to a Global HTTP

### What are the steps of the Bean lifecycle?
There are seven steps to the Bean lifecycle:

Instantiate: The bean is instantiated by the Spring container using the bean’s definition found in the XML configuration file.

Populate properties: Spring populates all the defined properties from the XML file using dependency injection.

Set Bean Name: Spring passes the bean’s identifier to the setBeanName() method If the bean uses the BeanNameAware interface.

Set Bean factory: Spring passes the beanfactory to the setBeanFactory() method if the bean is configured to use the BeanFactoryAware interface.

Pre Initialization: Spring calls any BeanPostProcessors associated with the bean using the postProcessorBeforeInitialization() method.

Initialization: The bean is then initialized. Any special initialization process specified in the init-method is followed.

Post Initialization: All defined postProcessAfterInitialization() methods are called. Now the bean is complete. Beans that implement DisposableBean will be deleted using the destroy() after their job is finished.

### What is a Joinpoint?
Joinpoints represent any point in a program where an action is taken. Examples of a joinpoint include when handling an exception or a method is executed. When using AOP, only method executions are joinpoints.

### What is an Advice in Spring?
An Advice is the action taken at a given joinpoint. AOP uses an Advice as an interceptor before the method’s execution is complete.

### What are the types of advice for a Spring Framework?
Before: These are advices that execute before the joinpoint methods. They’re marked using the @Before annotation mark.

After returning: These execute after the joinpoint’s method completes executing without issue. They’re marked using the @AfterReturning annotation mark.

After throwing: These execute only if the joinpoint method ends by throwing an exception. They’re marked using the @AfterThrowing annotation mark.

After: These execute after a joinpoint method, regardless of how it completes. They’re marked using the @After annotation mark.

Around: These execute before and after a joinpoint and are marked using the @Around annotation mark.

### What is the Spring Model-View-Controller (MVC) framework?
Spring MVC is an HTTP oriented Spring Framework that handles classes denoted as controllers. We can implement methods for different HTTP requests. It offers decoupled ways of solving web frameworks through a model view controller(MVC) design pattern. We can reuse code without repurposing it again.

The Spring MVC framework provides model-view-controller architecture and premade components used to develop loosely coupled web applications. Using MVC you can separate different aspects of the program like a business, input, and UI logics while still having a loose coupling between each. This allows for greater flexibility in your web applications.

### What are the parts of Spring MVC framework?
The 3 main parts of MVC are:

DispatcherServlet: This part of MVC manages all the HTTP requests, and responses that interact with the program. The DispatcherServlet first receives relevant handler mapping from the configuration file and then passes off the request to the controller. The DispatcherServlet is the most important part of the Spring Web MVC framework.

WebApplicationContext: This acts as an extension of the plain ApplicationContext with extra features necessary for web applications. It can uniquely resolve themes and automatically decide which servlet it is associated with.

Controllers: These are beans within the DispatcherServlet that act as filters between user input and application response. Controllers take user input, decide if it should be transformed into either a View or a Model, and finally returns the transformed input to the View Resolver for review.

### What are the different parts of the DispatcherServlet?
Handler Mapping: An interface that defines the mapping between handler and request objects. Can be used to create a custom mapping strategy.

Controller: Determines the app’s response to user input by sorting input requests by their desired outcome. Input is either immediately returned with a View or is transformed into a Model before being passed to the view-resolver.

View-Resolver: Takes and renders Models from the controller by mapping between View names and actual Views.

### What is the Spring Boot?
Spring Boot helps us to inject our functionality into the progarm and it provides a preconfigured projects to add in as dependecies. We don't need to spend much time on project set up.

Spring Boot is an open-source Java framework used to create microservices. It is a project built on top of Spring to simplify the task of deploying Java applications. Its two major components are the Spring Framework and Embedded HTTP Servers. Spring Boot is used to:

Simplify the process of developing production-ready Spring applications

Avoid XML configuration in Spring

Reduce development time by decreasing the number of needed import statements

Provide an opinionated development approach

These are often used to get Spring applications running quickly.