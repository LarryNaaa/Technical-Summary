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
5. An abstract class can have parametrized constructors and default constructor is always present in an abstract class, but they cannot be declared abstract methods.
6. A subclass of an abstract class must give a concrete implementation of an abstract method in an abstract class unless the subclass is also an abstract class.


For example: we  have a abstract class Animal, and we have a subclass Pig which inherit from Animal, so we can create a Pig object to access the abstract class Animal.

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