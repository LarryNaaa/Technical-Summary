# Wrapper Classes
A Wrapper class is a class whose object wraps or contains primitive data types.

## Why we need wrapper class?
1. They convert primitive data types into objects. Objects are needed if we wish to modify the arguments passed into a method (because primitive types are passed by value).
2. Data structures in the Collection framework, such as ArrayList and Vector, store only objects (reference types) and not primitive types.
3. An object is needed to support synchronization in multithreading.

![Primitive Data types and their Corresponding Wrapper class](https://media.geeksforgeeks.org/wp-content/cdn-uploads/20200806191733/Wrapper-Class-in-Java.png)

## Autoboxing
Automatic conversion of primitive types to the object of their corresponding wrapper classes is known as autoboxing.
```java
Integer wrapper=1;
// or
Integer wrapper=Integer.valueOf(1);
```

## Unboxing
It is just the reverse process of autoboxing.
```java
int original=wrapper;
//or
int original=wrapper.intValue();
```

## What is the difference between wrapper and primitive data type?
1. The Storage of both also differ Primitive types are stored in Stack while reference types are store in Heap
2. Wrapper data types are null able in nature and it assigned with null value. In case of primitive data type default value is different for all the data type. Like value for byte is 0, short is 0, int is 0, long is 0L, float is 0.0f, double is 0.0d, char is ‘\u0000’ and boolean is false
3. It's created in a different way, we use 'new' keyword to create a wrapper data type
4. Wrapper classes pass by referance, but primitive data types pass by value