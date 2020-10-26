# Nested Classes
Java allows us to define classes inside other classes. Nested classes enable us to logically group classes that are only used in one place, write more readable and maintainable code and increase encapsulation.

There are several types of nested classes:
1. Static nested classes
2. Non-static nested classes
3. Local classes
4. Anonymous classes

![Nested Classes](https://www.baeldung.com/wp-content/uploads/2019/04/nested-classes.png)

## Static Nested Classes
1. As with static members, these belong to their enclosing class, and not to an instance of the class
2. They can have all types of access modifiers in their declaration
3. They only have access to static members in the enclosing class
4. They can define both static and non-static members
```java
public class Outer {
    public static class StaticNested {
        //...
    }
}
```
We can instantiate an static nested class directly.
```java
Outer.StaticNested staticNested = new Outer.StaticNested();
```  

## Inner Classes / Non-Static Nested Classes
1. They can have all types of access modifiers in their declaration
2. Just like instance variables and methods, inner classes are associated with an instance of the enclosing class
3. They have access to all members of the enclosing class, regardless of whether they are static or non-static
4. They can only define non-static members
```java
public class Outer {
    
    public class Inner {
        // ...
    }
}
```

To instantiate an inner class, we must first instantiate its enclosing class.
```java
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
```

### Local Classes
Local classes are a special type of inner classes – in which **the class is defined inside a method or scope block**.

1. They cannot have access modifiers in their declaration
2. They have access to both static and non-static members in the enclosing context
3. They can only define instance members and non-static members
```java
public class NewEnclosing {
    
    void run() {
        class Local {
 
            void run() {
                // method implementation
            }
        }
        Local local = new Local();
        local.run();
    }
    
    @Test
    public void test() {
        NewEnclosing newEnclosing = new NewEnclosing();
        newEnclosing.run();
    }
}
```

### Anonymous Classes
**Anonymous classes are inner classes with no name.** 
Since they have no name, we can't use them in order to create instances of anonymous classes. As a result, we have to declare and instantiate anonymous classes in a single expression at the point of use.

**We may either extend an existing class or implement an interface.**

1. They cannot have access modifiers in their declaration
2. They have access to both static and non-static members in the enclosing context
3. They can only define instance members and non-static members
4. They're the only type of nested classes that cannot define constructors or extend/implement other classes or interfaces

![Anonymous Classes](https://www.runoob.com/wp-content/uploads/2020/06/nm-word-image-145.png)

```java
abstract class SimpleAbstractClass {
    abstract void run();
}

public class AnonymousInnerUnitTest {
    
    @Test
    public void whenRunAnonymousClass_thenCorrect() {
        SimpleAbstractClass simpleAbstractClass = new SimpleAbstractClass() {
            void run() {
                // method implementation
            }
        };
        simpleAbstractClass.run();
    }
}
```

```java
public class Demo {
    public static void main(String[] args) {
       new Thread(new Runnable() {
           @Override
           public void run() {
               System.out.println();
           }
       }).start();
 
    }
}
```
The syntax of anonymous classes does not allow us to make them implement multiple interfaces. During construction, there might exist exactly one instance of an anonymous class. Therefore, they can never be abstract. Since they have no name, we can't extend them. For the same reason, anonymous classes cannot have explicitly declared constructors.

In fact, the absence of a constructor doesn't represent any problem for us for the following reasons:

1. we create anonymous class instances at the same moment as we declare them
2. from anonymous class instances, we can access local variables and enclosing class's members


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

## Shadowing
The declaration of the members of an inner class shadow those of the enclosing class if they have the same name.

In this case, the this keyword refers to the instances of the nested class and the members of the outer class can be referred to using the name of the outer class.

```java
class Outer {
      public int age = 18;    
      class Inner {
          public int age = 20;    
          public viod showAge() {
              int age  = 25;
              System.out.println(age);//25
              System.out.println(this.age);//20
              System.out.println(Outer.this.age);//18
          }
      }
  }
```

## Why we use a nested class?
1. Java does not allow multiple inheritance, 
but it is possible to have multiple inner classes within an outer class, 
with each inner class implementing one inheritance, 
thereby implementing multiple inheritance of an external class.

## Why we use a anonymous class?
Saves the process of defining implementation classes, and enables automatic upward transitions.
```java
public class Demo {

    public void test(String title) {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(title);
            }
        });
        thread.start();
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Demo demo = new Demo();
            demo.test("title" + i);
        }
    }
}
```
Use anonymous classes to simplify the following:
```java
// 实现Runnable接口
class MyRunnable implements Runnable {

    @Override
    public void run() {
        
    }
}

// 向上转型
Runnable myRunnable = new MyRunnable();
```
## Why we use a local class?
When we need to define a inner class in the method of the outer class.