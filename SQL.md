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
