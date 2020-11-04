# Polymorphism
Polymorphism allows us to perform a single action in different ways. In other words, polymorphism allows you to define one interface and have multiple implementations.

In Java polymorphism is mainly divided into two types:
> + Compile time Polymorphism
> + Runtime Polymorphism

## Compile time Polymorphism
It is also known as static polymorphism. This type of polymorphism is achieved by function overloading or operator overloading.

### Method Overloading
When there are multiple functions with same name but different parameters then these functions are said to be overloaded. Functions can be overloaded by change in number of arguments or/and change in type of arguments.

### Operator Overloading
Java also provide option to overload operators. For example, we can make the operator (‘+’) for string class to concatenate two strings. We know that this is the addition operator whose task is to add two operands. So a single operator ‘+’ when placed between integer operands, adds them and when placed between string operands, concatenates them.

In java, Only “+” operator can be overloaded:
> + To add integers
> + To concatenate strings

## Runtime Polymorphism
It is also known as Dynamic Method Dispatch. It is a process in which a function call to the overridden method is resolved at Runtime. 

### Method Overriding
Method overriding, occurs when a derived class has a definition for one of the member functions of the base class. That base function is said to be overridden. On the other hand, overriding is a feature that allows a subclass or child class to provide a specific implementation of a method that is already provided by one of its super-classes or parent classes.

1. Overriding and Access-Modifiers : The access modifier for an overriding method can allow more, but not less, access than the overridden method. For example, a protected instance method in the super-class can be made public, but not private, in the subclass. Doing so, will generate compile-time error.

2. Final methods can not be overridden

3. Static methods can not be overridden

4. Private methods can not be overridden

5. The overriding method must have same return type (or subtype)

6. Invoking overridden method from sub-class : We can call parent class method in overriding method using super keyword.

7. Overriding and constructor : We can not override constructor as parent and child class can never have constructor with same name(Constructor name must always be same as Class name).