# SQL
SQL stands for Structured Query Language

SQL is a standard language for storing, manipulating and retrieving data in databases.

## Syntax
### SELECT
The SELECT statement is used to select data from a database.
```sql
SELECT column1, column2, ...
FROM table_name;
```

#### SELECT DISTINCT
The SELECT DISTINCT statement is used to return only distinct (different) values.
```sql
SELECT DISTINCT column1, column2, ...
FROM table_name;
```

### WHERE
The WHERE clause is used to filter records.
```sql
SELECT column1, column2, ...
FROM table_name
WHERE condition;
```

#### AND, OR and NOT
The WHERE clause can be combined with AND, OR, and NOT operators.

The AND and OR operators are used to filter records based on more than one condition:

The AND operator displays a record if all the conditions separated by AND are TRUE.
```sql
SELECT column1, column2, ...
FROM table_name
WHERE condition1 AND condition2 AND condition3 ...;
```

The OR operator displays a record if any of the conditions separated by OR is TRUE.
```sql
SELECT column1, column2, ...
FROM table_name
WHERE condition1 OR condition2 OR condition3 ...;
```

The NOT operator displays a record if the condition(s) is NOT TRUE.
```sql
SELECT column1, column2, ...
FROM table_name
WHERE NOT condition;
```

### ORDER BY
The ORDER BY keyword is used to sort the result-set in ascending or descending order.

The ORDER BY keyword sorts the records in ascending order by default. To sort the records in descending order, use the DESC keyword.

```sql
SELECT column1, column2, ...
FROM table_name
ORDER BY column1, column2, ... ASC|DESC;
```

#### ORDER BY Several Columns
The following SQL statement selects all customers from the "Customers" table, sorted by the "Country" and the "CustomerName" column. This means that it orders by Country, but if some rows have the same Country, it orders them by CustomerName:
```sql
SELECT * FROM Customers
ORDER BY Country, CustomerName;
```

### INSERT INTO
The INSERT INTO statement is used to insert new records in a table.

It is possible to write the INSERT INTO statement in two ways.

The first way specifies both the column names and the values to be inserted:
```sql
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);
```

If you are adding values for all the columns of the table, you do not need to specify the column names in the SQL query. However, make sure the order of the values is in the same order as the columns in the table. The INSERT INTO syntax would be as follows:
```sql
INSERT INTO table_name
VALUES (value1, value2, value3, ...);
```

If a column is an auto-increment field, it will be generated automatically when a new record is inserted into the table.

### NULL Values
A field with a NULL value is a field with no value.

If a field in a table is optional, it is possible to insert a new record or update a record without adding a value to this field. Then, the field will be saved with a NULL value.

**A NULL value is different from a zero value or a field that contains spaces. A field with a NULL value is one that has been left blank during record creation!**

#### IS NULL and IS NOT NULL
It is not possible to test for NULL values with comparison operators, such as =, <, or <>.

We will have to use the IS NULL and IS NOT NULL operators instead.

```sql
SELECT column_names
FROM table_name
WHERE column_name IS NULL|IS NOT NULL;
```

### UPDATE
The UPDATE statement is used to modify the existing records in a table.

```sql
UPDATE table_name
SET column1 = value1, column2 = value2, ...
WHERE condition;
```

**Note: Be careful when updating records in a table! Notice the WHERE clause in the UPDATE statement. The WHERE clause specifies which record(s) that should be updated. If you omit the WHERE clause, all records in the table will be updated!**

### DELETE
The DELETE statement is used to delete existing records in a table.

```sql
DELETE FROM table_name WHERE condition;
```

**Note: Be careful when deleting records in a table! Notice the WHERE clause in the DELETE statement. The WHERE clause specifies which record(s) should be deleted. If you omit the WHERE clause, all records in the table will be deleted!**

### LIMIT
```sql
SELECT column_name(s)
FROM table_name
WHERE condition
LIMIT number;
```

### MIN() and MAX()
The MIN() function returns the smallest value of the selected column.

The MAX() function returns the largest value of the selected column.

```sql
SELECT MIN(column_name)|MAX(column_name)
FROM table_name
WHERE condition;
```

### COUNT(), AVG() and SUM()
The COUNT() function returns the number of rows that matches a specified criterion.

**Note: NULL values are not counted.**

The AVG() function returns the average value of a numeric column.

**Note: NULL values are ignored.**

The SUM() function returns the total sum of a numeric column.

**Note: NULL values are ignored.**

```sql
SELECT COUNT(column_name)|AVG(column_name)|SUM(column_name)
FROM table_name
WHERE condition;
```

### LIKE
The LIKE operator is used in a WHERE clause to search for a specified pattern in a column.

There are two wildcards often used in conjunction with the LIKE operator:

% - The percent sign represents zero, one, or multiple characters

_ - The underscore represents a single character

```sql
SELECT column1, column2, ...
FROM table_name
WHERE columnN LIKE pattern;
```

WHERE CustomerName LIKE 'a%': Finds any values that start with "a"

WHERE CustomerName LIKE '%a': Finds any values that end with "a"

WHERE CustomerName LIKE '%or%': Finds any values that have "or" in any position

WHERE CustomerName LIKE '_r%': Finds any values that have "r" in the second position

WHERE CustomerName LIKE 'a_%': Finds any values that start with "a" and are at least 2 characters in length

WHERE CustomerName LIKE 'a__%': Finds any values that start with "a" and are at least 3 characters in length

WHERE ContactName LIKE 'a%o': Finds any values that start with "a" and ends with "o"

#### Wildcard Characters
A wildcard character is used to substitute one or more characters in a string.

Wildcard characters are used with the SQL LIKE operator. The LIKE operator is used in a WHERE clause to search for a specified pattern in a column.

%	Represents zero or more characters	bl% finds bl, black, blue, and blob

_	Represents a single character	h_t finds hot, hat, and hit

[]	Represents any single character within the brackets	h[oa]t finds hot and hat, but not hit

^	Represents any character not in the brackets	h[^oa]t finds hit, but not hot and hat

-	Represents a range of characters	c[a-b]t finds cat and cbt

### IN
The IN operator allows you to specify multiple values in a WHERE clause.

The IN operator is a shorthand for multiple OR conditions.

```sql
SELECT column_name(s)
FROM table_name
WHERE column_name IN (value1, value2, ...);
```

### BETWEEN
The BETWEEN operator selects values within a given range. The values can be numbers, text, or dates.

The BETWEEN operator is inclusive: begin and end values are included. 

```sql
SELECT column_name(s)
FROM table_name
WHERE column_name BETWEEN value1 AND value2;
```

### AS
SQL aliases are used to give a table, or a column in a table, a temporary name.

Aliases are often used to make column names more readable.

An alias only exists for the duration of the query.

```sql
SELECT column_name AS alias_name
FROM table_name;
```

### JOIN
A JOIN clause is used to combine rows from two or more tables, based on a related column between them.

#### INNER JOIN
The INNER JOIN keyword selects records that have matching values in both tables.

```sql
SELECT column_name(s)
FROM table1
INNER JOIN table2
ON table1.column_name = table2.column_name;
```

#### LEFT JOIN and RIGHT JOIN
The LEFT JOIN keyword returns all records from the left table (table1), and the matched records from the right table (table2). The result is NULL from the right side, if there is no match.

```sql
SELECT column_name(s)
FROM table1
LEFT JOIN table2
ON table1.column_name = table2.column_name;
```

The RIGHT JOIN keyword returns all records from the right table (table2), and the matched records from the left table (table1). The result is NULL from the left side, when there is no match.

```sql
SELECT column_name(s)
FROM table1
RIGHT JOIN table2
ON table1.column_name = table2.column_name;
```

#### FULL JOIN
The FULL OUTER JOIN keyword returns all records when there is a match in left (table1) or right (table2) table records.

Note: FULL OUTER JOIN can potentially return very large result-sets!

Tip: FULL OUTER JOIN and FULL JOIN are the same.

```sql
SELECT column_name(s)
FROM table1
FULL OUTER JOIN table2
ON table1.column_name = table2.column_name
WHERE condition;
```

### UNION
The UNION operator is used to combine the result-set of two or more SELECT statements.

Each SELECT statement within UNION must have the same number of columns

The columns must also have similar data types

The columns in each SELECT statement must also be in the same order

```sql
SELECT column_name(s) FROM table1
UNION
SELECT column_name(s) FROM table2;
```

**The UNION operator selects only distinct values by default. To allow duplicate values, use UNION ALL:**

```sql
SELECT column_name(s) FROM table1
UNION ALL
SELECT column_name(s) FROM table2;
```

### GROUP BY
The GROUP BY statement groups rows that have the same values into summary rows, like "find the number of customers in each country".

The GROUP BY statement is often used with aggregate functions (COUNT, MAX, MIN, SUM, AVG) to group the result-set by one or more columns.

```sql
SELECT column_name(s)
FROM table_name
WHERE condition
GROUP BY column_name(s)
ORDER BY column_name(s);
```

#### HAVING
The HAVING clause was added to SQL because the WHERE keyword could not be used with aggregate functions.

```sql
SELECT column_name(s)
FROM table_name
WHERE condition
GROUP BY column_name(s)
HAVING condition
ORDER BY column_name(s);
```

### Keys
#### Primary key
A primary key is a minimal set of attributes (columns) in a table that uniquely identifies tuples (rows) in that table.

#### Super key
A super key is a set of one or more attributes (columns), which can uniquely identify a row in a table.

#### Candidate Key 
A super key with no redundant attribute is known as candidate key. Candidate keys are selected from the set of super keys, the only thing we take care while selecting candidate key is that the candidate key should not have any redundant attributes. That’s the reason they are also termed as minimal super key.

#### Foreign key
Foreign keys are the columns of a table that points to the primary key of another table. They act as a cross-reference between tables.

#### Composite key
A key that has more than one attributes is known as composite key. It is also known as compound key.

Note: Any key such as super key, primary key, candidate key etc. can be called composite key if it has more than one attributes.

#### Alternate key
As we have seen in the candidate key guide that a table can have multiple candidate keys. Among these candidate keys, only one key gets selected as primary key, the remaining keys are known as alternative or secondary keys.


### Normalization
Normalization is a database design technique that reduces data redundancy and eliminates undesirable characteristics like Insertion, Update and Deletion Anomalies. Normalization rules divides larger tables into smaller tables and links them using relationships. The purpose of Normalization in SQL is to eliminate redundant (repetitive) data and ensure data is stored logically.

#### 1NF (First Normal Form) Rules
Each table column should contain a single value.

#### 2NF (Second Normal Form) Rules
No non-prime attribute is dependent on the proper subset of any candidate key of table.

An attribute that is not part of any candidate key is known as non-prime attribute.

#### 3NF (Third Normal Form) Rules
Transitive functional dependency of non-prime attribute on any super key should be removed.

##### Transitive functional dependency
A functional dependency is said to be transitive if it is indirectly formed by two functional dependencies. For e.g.

X -> Z is a transitive dependency if the following three functional dependencies hold true:

X->Y

Y does not ->X

Y->Z

Note: A transitive dependency can only occur in a relation of three of more attributes. This dependency helps us normalizing the database in 3NF (3rd Normal Form).

#### Boyce Codd normal form (BCNF)
BCNF is stricter than 3NF. A table complies with BCNF if it is in 3NF and for every functional dependency X->Y, X should be the super key of the table.

### What is the MySQL default port number?
MySQL default port number is 3306.

### What are the differences between a primary key and a foreign key?
1. The primary key uniquely identifies a record, whereas foreign key refers to the primary key of another table.

2. The primary key can never accept a NULL value but foreign key accepts a NULL value.

3. When a record is inserted in a table that contains the primary key then it is not necessary to insert the value on the table that contains this primary key field as the foreign key.

4. When a record is deleted from the table that contains the primary key then the corresponding record must be deleted from the table containing the foreign key for data consistency. But any record can be deleted from the table that contains a foreign key without deleting a related record of another table.

### What are the differences between CHAR and VARCHAR data types?
Both CHAR and VARCHAR data types are used to store string data in the field of the table.

1. CHAR data type is used to store fixed-length string data and the VARCHAR data type is used to store variable-length string data.

2. The storage size of the CHAR data type will always be the maximum length of this data type and the storage size of VARCHAR will be the length of the inserted string data. Hence, it is better to use the CHAR data type when the length of the string will be the same length for all the records.

3. CHAR is used to store small data whereas VARCHAR is used to store large data.

4. CHAR works faster and VARCHAR works slower.

### What is the purpose of using the TIMESTAMP data type?
A TIMESTAMP data type is used to store the combination of date and time value which is 19 characters long.

### How can you filter the duplicate data while retrieving records from the table?
A DISTINCT keyword is used to filter the duplicate data from the table while retrieving the records from a table.

### What is the difference between NOW() and CURRENT_DATE()?
Both NOW() and CURRENT_DATE() are built-in MySQL methods. NOW() is used to show the current date and time of the server and CURRENT_DATE() is used to show only the date of the server.

### Which statement is used in a select query for partial matching?
REGEXP(Regular Expression) and LIKE statements can be used in a SELECT query for partial matching. REGEXP is used to search records based on the pattern and LIKE is used to search any record by matching any string at the beginning or end or middle of a particular field value.

### Which MySQL function is used to concatenate string?
CONCAT() function is used to combine two or more string data. The use of this function is here with an example.

### What is an index? How can an index be declared in MySQL?
An index is a data structure of a MySQL table that is used to speed up the queries.

It is used by the database search engine to find out the records faster. One or more fields of a table can be used as an index key. Index key can be assigned at the time of table declaration or can be assigned after creating the table.

### What is the difference between the Primary key and the Unique key?
Unique data is stored in the primary key and unique key fields. The primary key field never accepts NULL value but a unique key field accepts a NULL value.

### What is the purpose of using the IFNULL() function?
IFNULL() function takes two arguments. It returns the first argument value if the value of the first argument is not NULL and it returns the second argument if the value of the first argument is NULL.

### What is a join? Explain the different types of MySQL joins.
The SQL statement that is used to make a connection between two or more tables based on the matching columns is called a join. It is mainly used for complex queries.

Different types of SQL joins are mentioned below:

Inner Join: It is a default join. It returns records when the values match in the joining tables.

Left Outer Join: It returns all the records from the left table based on the matched records from the right table.

Right Outer Join: It returns all the records from the right table based on the matched records from the left table.

Full Outer Join: It returns all the records that match from the left or right table.

### How can you retrieve a particular number of records from a table?
LIMIT clause is used with the SQL statement to retrieve a particular number of records from a table. From which record and how many records will be retrieved are defined by the LIMIT clause.

### Explain the difference between DELETE and TRUNCATE.
1. DELETE command is used to delete a single or multiple or all the records from the table. The TRUNCATE command is used to delete all the records from the table or make the table empty.

2. When DELETE command is used to delete all the records from the table then it doesn’t re-initialize the table. So, the AUTO_INCREMENT field does not count from one when the user inserts any record. But when all the records of any table are deleted by using TRUNCATE command then it re-initializes the table and a new record will start from one for the AUTO_INCREMENT field.

### What is a storage engine? What are the differences between InnoDB and MyISAM engines?
One of the major components of the MySQL server is the storage engine for doing different types of database operations. Each database table created is based on the specific storage engine.

MySQL supports two types of storage engines i.e transactional and non-transactional. InnoDB is the default storage engine of MySQL which is transactional. MyISAM storage engine is a non-transactional storage engine.

The differences between InnoDB and MyISAM storage engines are discussed below:

1. MyISAM supports the FULLTEXT index but InnoDB doesn’t support the FULLTEXT index.

2. MyISAM is faster and InnoDB is slower.

3. InnoDB supports ACID (Atomicity, Consistency, Isolation, and Durability) property but MyISAM doesn’t.

4. InnoDB supports row-level locking and MyISAM supports table-level locking.

5. InnoDB is suitable for large database and MyISAM is suitable for a small database.

### What is a transaction? Describe MySQL transaction properties.
When a group of database operations is done as a single unit then it is called a transaction. If any task of the transactional tasks remains incomplete then the transaction will not succeed. Hence, it is mandatory to complete all the tasks of a transaction to make the transaction successful.

A transaction has four properties which are known as ACID property. These properties are described below.

Atomicity: It ensures that all the tasks of a transaction will be completed successfully otherwise all the completed tasks will be rolled back to the previous state for any failure.

Consistency: It ensures that the database state must be changed accurately for the committed transaction.

Isolation: It ensures that all the tasks of a transaction will be done independently and transparently.

Durability: It ensures that all the committed transaction is consistent for any type of system failure.

### What are the functions of commit and rollback statements?
Commit is a transaction command that executes when all the tasks of a transaction are completed successfully. It will modify the database permanently to confirm the transaction.

Rollback is another transactional command that executes when any of the transactional tasks become unsuccessful and undoes all the changes that are made by any transactional task to make the transaction unsuccessful.

### SQL Order of Operations?
(1) FROM <left_table>

(2) <join_type> JOIN <right_table>

(3) ON <join_condition>

(4) WHERE <where_condition>

(5) GROUP BY <group_by_list>

(6) HAVING <having_condition>

(7) SELECT

(8) DISTINCT

(9) UNION

(10) ORDER BY <order_by_list>

### Isolation Levels
#### READ UNCOMMITTED
In READ-UNCOMMITTED isolation level, there isn’t much isolation present between the transactions at all, ie ., No locks. A transaction can see changes to data made by other transactions that are not committed yet. This is the lowest level in isolation and highly performant since there is no overhead of maintaining locks, With this isolation level, there is always for getting a “Dirty-Read”
##### Dirty-Read
That means transactions could be reading data that may not even exist eventually because the other transaction that was updating the data rolled-back the changes and didn’t commit. 

#### READ COMMITTED
In READ-COMMITTED isolation level, the phenomenon of dirty read is avoided, because any uncommitted changes are not visible to any other transaction until the change is committed. This is the default isolation level with most of popular RDBMS software, but not with MySQL.

Within this isolation level, each SELECT uses its own snapshot of the committed data that was committed before the execution of the SELECT. Now because each SELECT has its own snapshot, here is the trade-off now, so the same SELECT, when running multiple times during the same transaction, could return different result sets. This phenomenon is called non-repeatable read.
##### non-repeatable read
A non-repeatable occurs when a transaction performs the same transaction twice but gets a different result set each time. 

#### REPEATABLE READ
In REPEATABLE-READ isolation level, the phenomenon of non-repeatable read is avoided. It is the default isolation in MySQL.This isolation level returns the same result set throughout the transaction execution for the same SELECT run any number of times during the progression of a transaction.

This is how it works, a snapshot of the SELECT is taken the first time the SELECT is run during the transaction and the same snapshot is used throughout the transaction when the same SELECT is executed. A transaction running in this isolation level does not take into account any changes to data made by other transactions, regardless of whether the changes have been committed or not. This ensures that reads are always consistent(repeatable). Maintaining a snapshot can cause extra overhead and impact some performance

Although this isolation level solves the problem of non-repeatable read, another possible problem that occurs is phantom reads.
##### phantom reads
A Phantom is a row that appears where it is not visible before. 

#### SERIALIZABLE
SERIALIZABLE completely isolates the effect of one transaction from others. It is similar to REPEATABLE READ with the additional restriction that row selected by one transaction cannot be changed by another until the first transaction finishes. The phenomenon of phantom reads is avoided. This isolation level is the strongest possible isolation level. 

### Concurrency Control Mechanisms
#### Optimistic concurrency control (OCC)
It allows multiple transactions to modify data without interfering with each other. While a transaction is running, the data that will be edited isn't locked. Before a transaction commits, optimistic concurrency control checks whether a conflicting modification exists. If a conflict exists, the committing transaction is rolled back.

#### Pessimistic concurrency control
when a transaction is modifying data, pessimistic locking applies a lock to the data so other transactions can't access the same data. After the transaction commits, the lock is released.


