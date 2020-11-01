# Inheritance
It is the mechanism in java by which one class is allow to inherit the features(fields and methods) of another class.

> Important terminology:
> 
> > Super Class: The class whose features are inherited is known as super class(or a base class or a parent class).
> 
> > Sub Class: The class that inherits the other class is known as sub class(or a derived class, extended class, or child class). The subclass can add its own fields and methods in addition to the superclass fields and methods.
>
> > Reusability: Inheritance supports the concept of “reusability”, i.e. when we want to create a new class and there is already a class that includes some of the code that we want, we can derive our new class from the existing class. By doing this, we are reusing the fields and methods of the existing class.

Types of Inheritance in Java:

1. Single Inheritance
2. Multilevel Inheritance
3. Hierarchical Inheritance
4. Multiple Inheritance(Through Interfaces) 
5. Hybrid Inheritance(Through Interfaces) 

Important facts about inheritance in Java

1. Default superclass: Except Object class, which has no superclass, every class has one and only one direct superclass (single inheritance). In the absence of any other explicit superclass, every class is implicitly a subclass of Object class.

2. Superclass can only be one: A superclass can have any number of subclasses. But a subclass can have only one superclass. This is because Java does not support multiple inheritance with classes. Although with interfaces, multiple inheritance is supported by java.

3. Inheriting Constructors: A subclass inherits all the members (fields, methods, and nested classes) from its superclass. Constructors are not members, so they are not inherited by subclasses, but the constructor of the superclass can be invoked from the subclass.

4. Private member inheritance: A subclass does not inherit the private members of its parent class. However, if the superclass has public or protected methods(like getters and setters) for accessing its private fields, these can also be used by the subclass.

What all can be done in a Subclass?

In sub-classes we can inherit members as is, replace them, hide them, or supplement them with new members:

1. The inherited fields can be used directly, just like any other fields.
2. We can declare new fields in the subclass that are not in the superclass.
3. The inherited methods can be used directly as they are.
4. We can write a new instance method in the subclass that has the same signature as the one in the superclass, thus overriding it (as in example above, toString() method is overridden).
5. We can write a new static method in the subclass that has the same signature as the one in the superclass, thus hiding it.
6. We can declare new methods in the subclass that are not in the superclass.
7. We can write a subclass constructor that invokes the constructor of the superclass, either implicitly or by using the keyword super.