# Nested Classes
Java allows us to define classes inside other classes. Nested classes enable us to logically group classes that are only used in one place, write more readable and maintainable code and increase encapsulation.

There are several types of nested classes:
1. Static nested classes
2. Non-static nested classes
3. Local classes
4. Anonymous classes

## Static Nested Classes
1. As with static members, these belong to their enclosing class, and not to an instance of the class
2. They can have all types of access modifiers in their declaration
3. They only have access to static members in the enclosing class
4. They can define both static and non-static members

## Non-Static Nested Classes / Inner Classes
1. They can have all types of access modifiers in their declaration
2. Just like instance variables and methods, inner classes are associated with an instance of the enclosing class
3. They have access to all members of the enclosing class, regardless of whether they are static or non-static
4. They can only define non-static members

To instantiate an inner class, we must first instantiate its enclosing class.
```java
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

## What is the difference between static nested class and inner class?
1. Static nested class are belong to the Outer class, not to an instance of the class, 
but Inner class are associated with an instance of the Outer class. 
Therefore, we can not create instance of Inner class 
without creating instance of Outer class. But we can create instance of
static nested class directly.
   
2. Static nested class can only access static members in the Outer class. 
While Inner class can access both static and 
non-static member of Outer class.

3. Static nested class cam define both static and non-static members, 
but Inner class can only define non-static members.
