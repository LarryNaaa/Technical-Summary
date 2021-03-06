# Java

## Operators
### Logical Operators
Logical operators are used to determine the logic between variables or values

#### Logical and(&&)
Returns true if both statements are true

#### Logical or(||)
Returns true if one of the statements is true

#### Logical not(!)
Reverse the result, returns false if the result is true

### Bitwise Operators
Bitwise operators are used to perform binary logic with the bits of an integer or long integer.

#### AND(&)
Sets each bit to 1 if both bits are 1

#### OR(|)
Sets each bit to 1 if any of the two bits is 1

#### NOT(~)
Inverts all the bits

#### XOR(^)
Sets each bit to 1 if only one of the two bits is 1

#### Zero-fill left shift(<<)
Shift left by pushing zeroes in from the right and letting the leftmost bits fall off

#### Signed right shift(>>)
Shift right by pushing copies of the leftmost bit in from the left and letting the rightmost bits fall off

#### Zero-fill right shift(>>>)
Shift right by pushing zeroes in from the left and letting the rightmost bits fall off


## Modifiers
> We divide modifiers into two groups:
> > Access Modifiers - controls the access level
> 
> > Non-Access Modifiers - do not control access level, but provides other functionality.

### Access Modifiers
> For classes, we can use either public or default:
>
> > public: The class is accessible by any other class
>
> > default: The class is only accessible by classes in the same package. 
>This is used when you don't specify a modifier.

> For attributes, methods and constructors, we can use the one of the following:
>
> > public: The code is accessible for all classes
>
> > private: The code is only accessible within the declared class
>
> > default: The code is only accessible in the same package. This is used when you don't specify a modifier.
>
> > protected: The code is accessible in the same package and subclasses. 

### Non-Access Modifiers
> For classes, you can use either final or abstract:
>
> > final: The class cannot be inherited by other classes
>
> > abstract: The class cannot be used to create objects (To access an abstract class, it must be inherited from another class.

> For attributes and methods, you can use the one of the following:
>
> > final: Attributes and methods cannot be overridden/modified
>
> > static: Attributes and methods belongs to the class, rather than an object
>
> > abstract: Can only be used in an abstract class, and can only be used on methods. The method does not have a body, for example abstract void run();. The body is provided by the subclass (inherited from). 

## Keywords

### public
An access modifier used for classes, attributes, methods and constructors, making them accessible by any other class

### private
An access modifier used for attributes, methods and constructors, making them only accessible within the declared class

### protected
An access modifier used for attributes, methods and constructors, making them accessible in the same package and subclasses

### abstract
A non-access modifier. Used for classes and methods: An abstract class cannot be used to create objects (to access it, it must be inherited from another class). An abstract method can only be used in an abstract class, and it does not have a body. The body is provided by the subclass (inherited from)

### static
A non-access modifier used for nested class(cannot be used for top level class),
 methods, attributes and code blocks. Static methods/attributes can be accessed without creating an object of a class
> static methods:
> > they belong to the current class, not to the object
>
> > they cannot be overridden
>
> > they can be called by classname directly
>
> > cannot use this and super keywords in them

> static attributes:
> > they can be shared by all instances, and do not depend on objects
> 
> > when JVM loads classes, the memory is only allocated once for them

> static code blocks:
> > they are used for program optimization
>
> > they only be executed once when class is loaded
>
> > if there are multiple static code blocks, they will be executed in sequence 

#### What is the difference between static nested class and inner class?
there are two kinds of class in Java, 
one is called **top level class** and other is called **nested class**. 

As name suggested top level class is a class which is declared in 
.java file and not enclosed under any other class. 

On other hand nested class is declared inside another class. 
The class which enclosed nested class is known as Outer class.

In Java programming language you can not make a top level class static. 
We can only make nested class either static or non static. 
If you make a nested class non static then it also referred as Inner class.

1. Nested static class doesn't need reference of Outer class 
but non static nested class or Inner class requires Outer class 
reference. We can not create instance of Inner class 
without creating instance of Outer class. But we can create instance of
static nested class directly.
   
2. Static nested class cannot access non static members. 
While Inner class can access both static and 
non static member of Outer class.

### final
A non-access modifier used for classes, attributes and methods, which makes them non-changeable (impossible to inherit or override)
	
### finally
Used with exceptions, a block of code that will be executed no matter if there is an exception or not

#### What is the difference between final and finally?
**final**: 

1. is a mom-modifier, used for classes, attributes and methods
2. makes classes, attributes and methods non-changeable (impossible to inherit or override)

**finally**:
1. used with exception
2. a block of code that will be executed no matter if there is an exception or not

### try
Creates a try...catch statement

### catch
Catches exceptions generated by try statements

### throw
Creates a custom error

### throws
Indicates what exceptions may be thrown by a method

#### What is the difference between throw and throws?
**throw**: 

1. Used to throw an exception for a method
2. Cannot throw multiple exceptions
3. throw is followed by an object (new type)
4. used inside the method

**throws**: 

1. Used to indicate what exception type may be thrown by a method
2. Can declare multiple exceptions
3. throws is followed by a class
4. and used with the method signature

## Exceptions
When executing Java code, different errors can occur: coding errors made by the programmer, errors due to wrong input, or other unforeseeable things.

When an error occurs, Java will normally stop and generate an error message. The technical term for this is: Java will throw an exception (throw an error).

### try/catch keyword
The **try** statement allows you to define a block of code to be tested for errors while it is being executed.

The **catch** statement allows you to define a block of code to be executed, if an error occurs in the try block.

The try and catch keywords come in pairs:
```java
try {
  //  Block of code to try
}
catch(Exception e) {
  //  Block of code to handle errors
}
```
### finally keyword
The **finally** statement lets you execute code, after try...catch, regardless of the result:
```java
public class MyClass {
  public static void main(String[] args) {
    try {
      int[] myNumbers = {1, 2, 3};
      System.out.println(myNumbers[10]);
    } catch (Exception e) {
      System.out.println("Something went wrong.");
    } finally {
      System.out.println("The 'try catch' is finished.");
    }
  }
}
```
### throw keyword
The **throw** statement allows you to create a custom error.

The **throw** statement is used together with an exception type. There are many exception types available in Java: ArithmeticException, FileNotFoundException, ArrayIndexOutOfBoundsException, SecurityException, etc:
```java
public class MyClass {
  static void checkAge(int age) {
    if (age < 18) {
      throw new ArithmeticException("Access denied - You must be at least 18 years old.");
    }
    else {
      System.out.println("Access granted - You are old enough!");
    }
  }

  public static void main(String[] args) {
    checkAge(15); // Set age to 15 (which is below 18...)
  }
}
```

### instanceof
Checks whether an object is an instance of a specific class or an interface

## Encapsulation
> The meaning of Encapsulation, is to make sure that "sensitive" data is hidden from users. To achieve this, you must:
>
> > declare class variables/attributes as private
>
> > provide public get and set methods to access and update the value of a private variable

### Get and Set
You learned from the previous chapter that private variables can only be accessed within the same class (an outside class has no access to it). However, it is possible to access them if we provide public get and set methods.

The get method returns the variable value, and the set method sets the value.

Syntax for both is that they start with either get or set, followed by the name of the variable, with the first letter in upper case:
```java
public class Person {
  private String name; // private = restricted access

  // Getter
  public String getName() {
    return name;
  }

  // Setter
  public void setName(String newName) {
    this.name = newName;
  }
}
```
### Why Encapsulation?
1. Better control of class attributes and methods
2. Class attributes can be made read-only (if you only use the get method), or write-only (if you only use the set method)
3. Flexible: the programmer can change one part of the code without affecting other parts
4. Increased security of data

## Inheritance
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

## Abstraction
Hiding the internal implementation of the feature and only 
showing the functionality to the users. i.e. what it works (showing), 
how it works (hiding). 

Both abstract class and interface are used for abstraction.

### Abstract class
Abstract class: is a restricted class that cannot be used to create objects (to access the abstract class, it must be inherited from another class).

1. An abstract class is a class that is declared with abstract keyword.
2. An abstract class may or may not have all abstract methods. Some of them can be concrete methods.
3. Any class that contains one or more abstract methods must also be declared with abstract keyword.
4. There can be no object of an abstract class.That is, an abstract class can not be directly instantiated with the new operator.
5. An abstract class can have parametrized constructors and 
default constructor is always present in an abstract class, 
but they cannot be declared abstract methods.
6. A subclass of an abstract class must give a concrete implementation of an abstract method in an abstract class unless the subclass is also an abstract class.


For example: we  have a abstract class Animal, and we have a subclass Pig
which inherit from Animal, so we can create a Pig 
object to access the abstract class Animal.

An abstract class can have both abstract and regular methods:
```java
// Abstract class
abstract class Animal {
  // Abstract method (does not have a body)
  public abstract void animalSound();
  // Regular method
  public void sleep() {
    System.out.println("Zzz");
  }
}

// Subclass (inherit from Animal)
class Pig extends Animal {
  public void animalSound() {
    // The body of animalSound() is provided here
    System.out.println("The pig says: wee wee");
  }
}

class MyMainClass {
  public static void main(String[] args) {
    Pig myPig = new Pig(); // Create a Pig object
    myPig.animalSound();
    myPig.sleep();
  }
}
```

### Abstract method
Abstract method: can only be used in an abstract class, and it does not have a body. The body is provided by the subclass (inherited from).

1. An abstract method is a method that is declared without 
an implementation，does not contain the body of the method
2. A method defined abstract must always be redefined in the subclass, thus making overriding compulsory OR either make subclass itself abstract.


> Why And When To Use Abstract Classes and Methods?
>
> To achieve security - hide certain details and only show the important details of an object.

### Interface
An interface in Java is an abstract type, a collection of abstract methods. A class inherits the abstract methods of an interface by inheriting the interface.

```java
// interface
interface Animal {
  public void animalSound(); // interface method (does not have a body)
  public void run(); // interface method (does not have a body)
}
```
To access the interface methods, the interface must be "implemented" (kinda like inherited) by another class with the implements keyword (instead of extends). The body of the interface method is provided by the "implement" class:
```java
// Interface
interface Animal {
  public void animalSound(); // interface method (does not have a body)
  public void sleep(); // interface method (does not have a body)
}

// Pig "implements" the Animal interface
class Pig implements Animal {
  public void animalSound() {
    // The body of animalSound() is provided here
    System.out.println("The pig says: wee wee");
  }
  public void sleep() {
    // The body of sleep() is provided here
    System.out.println("Zzz");
  }
}

class MyMainClass {
  public static void main(String[] args) {
    Pig myPig = new Pig();  // Create a Pig object
    myPig.animalSound();
    myPig.sleep();
  }
}
```
> Notes on Interfaces:
>
> Like abstract classes, interfaces cannot be used to create objects (in the example above, it is not possible to create an "Animal" object in the MyMainClass)
>  
> Interface methods do not have a body - the body is provided by the "implement" class
> 
> On implementation of an interface, you must override all of its methods
> 
> Interface methods are by default abstract and public
> 
> Interface attributes are by default public, static and final
> 
> An interface cannot contain a constructor (as it cannot be used to create objects)

> Why And When To Use Interfaces?
> 
> It is used to achieve total abstraction.
> 
> Since java does not support multiple inheritance in case of class, but by using interface it can achieve multiple inheritance .
> 
> It is also used to achieve loose coupling.
> 
> Interfaces are used to implement abstraction.

> Why use interfaces when we have abstract classes?
> The reason is, abstract classes may contain non-final variables, whereas variables in interface are final, public and static.

### Difference between Abstract Class and Interface in Java
1. Keywords: A Java interface can be implemented using keyword “implements” and abstract class can be extended using keyword “extends”.
2. Type of methods: Interface can have only abstract methods. Abstract class can have abstract and non-abstract methods. From Java 8, it can have default and static methods also.
3. Type of variables: Abstract class can have final, non-final, static and non-static variables. Interface has only static and final variables.
4. Inheritance and Implementation: A class can only extend another class, but it can implements multiple interfaces. An interface can extend multiple interfaces and cannot implements any interface.
5. Abstract classes are abstractions to classes, interfaces are abstractions to behaviors; An abstract class is an abstraction of the entire class, including properties, behaviors; An interface abstracts the behavior (local) of a class.

### Why use interfaces when we have abstract classes?
1. An interface can extend another Java interface only, an abstract class can extend another Java class and implement multiple Java interfaces.
2. abstract classes may contain non-final variables, whereas variables in interface are final, public and static.

### How to choose abstract class or interface?
1. Use an abstract class if we have some functionality that we want it's
 subclasses to have. For instance, if we have a set of functions that we
  want all of the base abstract class's subclasses to have.
2. Use an interface if we just want a general contract on 
behavior/functionality. If we have a function or object that we
 want to take in a set of different objects, use an interface. Then we
  can change out the object that is passed in, without changing 
  the method or object that is taking it.
3. If we want to create a base class without any method definitions or 
member variables, we should choose an interface.
4. Abstract classes should be chosen only when method definitions and member variables are required, because one or more methods that are concretely implemented are allowed in an abstract class.

### Why can interfaces inherit more and classes not?
The reason classes cannot inherit more is to prevent two identical methods from being inherited by subclasses from two different superclasses.

The reason interfaces can inherit more is that when two interfaces have the same method, the method inside the interface has no method body, so the method can be combined.

## Overload
Overloading allows different methods to have the same name, 
but different signatures where the signature can differ by the 
number of input parameters or type of input parameters or both. 

We don’t have to create and remember different names for functions doing the same thing.

## Override
Overriding is about same function, same signature but different classes connected through inheritance.


Overriding is a feature that allows a subclass or child class to provide a specific implementation of a method that is already provided by one of its super-classes or parent classes.

When a method in a subclass has the same name, same parameters or signature, and same return type(or sub-type) as a method in its super-class, then the method in the subclass is said to override the method in the super-class.

1. Overriding and Access-Modifiers : The access modifier for an overriding method can allow more, but not less, access than the overridden method. For example, a protected instance method in the super-class can be made public, but not private, in the subclass. Doing so, will generate compile-time error.

2. Final methods can not be overridden

3. Static methods can not be overridden

4. Private methods can not be overridden

5. The overriding method must have same return type (or subtype)

6. Invoking overridden method from sub-class : We can call parent class method in overriding method using super keyword.

7. Overriding and constructor : We can not override constructor as parent and child class can never have constructor with same name(Constructor name must always be same as Class name).

## Singleton Class
The Singleton's purpose is to control object creation, limiting the number of objects to only one. Since there is only one Singleton instance, any instance fields of a Singleton will occur only once per class, just like static fields. Singletons often control access to resources, such as database connections or sockets.

```java
public class ClassicSingleton {

   private static ClassicSingleton instance = null;
   private ClassicSingleton() {
      // Exists only to defeat instantiation.
   }

   public static ClassicSingleton getInstance() {
      if(instance == null) {
         instance = new ClassicSingleton();
      }
      return instance;
   }
}
```

## Packages
In order to organize classes better, Java provides a package mechanism to distinguish the namespace of class names.

1. group related classes in one package, easy to find and use

2. avoid name conflicts, we need to add package name to distinguish classes
which have same name, because we cannot have same classes name in one package,
but we can have same classes name in different packages.

3. it limits the access rights, the class with the package access rights can access the classes in a package

## Do Java call functions pass arguments by value or by reference?
Pass by value: A copy of the actual parameter is passed to the function when the function is called, so that if the parameter is modified in the function, the actual parameter will not be affected.

Pass by reference: The address of the actual parameter is passed directly to the function when the function is called, so that if the parameter is modified in the function, the actual parameter will be affected.

in Java we call functions pass parameters by value!


