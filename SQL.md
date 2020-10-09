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

