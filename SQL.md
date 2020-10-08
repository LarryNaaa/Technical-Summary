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


