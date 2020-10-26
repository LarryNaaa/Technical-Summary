# Keywords

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

### final
A non-access modifier used for classes, attributes and methods, which makes them non-changeable (impossible to inherit or override)
	
### finally
Used with exceptions, a block of code that will be executed no matter if there is an exception or not

#### What is the difference between final and finally?
**final**: 

1. is a non-modifier, used for classes, attributes and methods
2. makes classes, attributes and methods non-changeable (impossible to inherit or override)

**finally**:
1. used with exception
2. a block of code that will be executed no matter if there is an exception or not

### try
The **try** statement allows you to define a block of code to be tested for errors while it is being executed.

### catch
The **catch** statement allows you to define a block of code to be executed, if an error occurs in the try block.

### throw
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

### instanceof
Checks whether an object is an instance of a specific class or an interface
