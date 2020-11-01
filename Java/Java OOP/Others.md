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