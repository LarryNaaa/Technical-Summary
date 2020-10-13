# hibernate and mybatis
https://www.perfomatix.com/hibernate-vs-mybatis/

## Object Relational Mapping (ORM) 
Object-Relational Mapping (ORM) is a programming technique which ables to write simple and complicated queries, using the object-oriented paradigm of your preferred programming language (i.e, converting data between relational databases and object oriented programming languages such as Java, .NET, etc.).

Object Relational Mapping (ORM) is a technique (Design Pattern) of accessing a relational database from an object-oriented language that helps your application to achieve persistence.

## Persistence
Persistence simply means that we would like our application’s data to outlive the application process. In Java terms, the state of some objects lives beyond the scope of the JVM so that the same state is available later.

## Hibernate
Hibernate is an open-source, lightweight, ORM (Object Relational Mapping) tool. It is a Java framework that simplifies the development of Java applications to interact with the database implementing the specifications of JPA (Java Persistence API) for data persistence by mapping application domain objects to the relational database tables and vice versa.

### ORM
ORM is an acronym for Object/Relational mapping. It is a programming strategy to map object with the data stored in the database. It simplifies data creation, data manipulation, and data access.

## Mybatis
MyBatis is an open-source, lightweight, persistence framework. It is an alternative to JDBC and Hibernate. It automates the mapping between SQL databases and objects in Java, .NET, etc. A significant difference between MyBatis and other persistence frameworks is that MyBatis emphasizes the use of SQL, while other frameworks such as Hibernate typically uses a custom query languages( HQL/EJB QL). 

MyBatis is SQL centric. It helps you call SQL statements and mapping results (tables) to object trees. The main benefit is that it is not an ORM. It does not map tables to objects, so it does not suffer the ORM impedance mismatch.

## hibernate and mybatis
Hibernate is used for

General CRUD(CREATE, READ, UPDATE, and DELETE ) functionality.
The environment is driven by object models and needs to generate SQL automatically.
Session management.

and MyBatis is used for

Analytic fetch queries.
Stored procedures and dynamic SQL.
Support complicated search queries, where search criteria are dynamic, and paging of results.

## ORM vs Persistence framework
Hibernate is an object-relational mapping framework (ORM) which maps Java classes to database tables. MyBatis is a persistence framework – not ORM. It maps SQL statements to Java methods.

If you have a simple domain and just fetch information, use MyBatis. If you have a complex domain and persist entities, use Hibernate. If you do both, consider a hybrid approach (i.e., thousands of entities to keep it under control). 

Hibernate works better if your view is more object-centric. However, if your view is more database-centric, then myBatis is a much stronger choice.

