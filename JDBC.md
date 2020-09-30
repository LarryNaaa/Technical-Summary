## JDBC

### Advantages
1. We can quickly and directly access the database.
2. It is more flexible, we can write complex SQL statements.

### Disadvantages
1. When connecting to the database, we need to operate the database frequently, which causes the waste of database resources and affects the performance of the database.

Solution: Use the database connection pool to manage connections to the database.

2. SQL statements are in Java code and are not good for system maintenance.

Solution: Use the Spring framework to place SQL statements in an XML configuration file.

3. The operation is tedious and a lot of code needs to be repeated many times.