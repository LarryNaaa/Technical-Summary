# String
The String class represents character strings. All string literals in Java programs, such as"abc", are implemented as instances of this class. **Strings are constant; their values cannot be changed after they are created.**

## String Method

## What is the difference between == operator and .equals() method?
### == operator
For primitive types, we use == for content comparison; For objects, we use == for address comparison, == checks if both objects point to the same memory location.

### .equals() method
we use .equals() method for content comparison, .equals() evaluates to the comparison of values in the objects.

If a class does not override the equals method, then by default it uses equals(Object o) method of the closest parent class that has overridden this method. 

### String Constant Pool
One of the most important characteristics of a string in Java is that they are immutable. This immutability is achieved through the use of a special string constant pool in the heap.

A string constant pool is a separate place in the heap memory where the values of all the strings which are defined in the program are stored. When we declare a string, an object of type String is created in the stack, while an instance with the value of the string is created in the heap. On standard assignment of a value to a string variable, the variable is allocated stack, while the value is stored in the heap in the string constant pool.

![string constant pool 1](https://media.geeksforgeeks.org/wp-content/uploads/20200601211147/string_pool_11.png)

A pointer points towards the value stored in the heap from the object in the stack. 

![string constant pool 2](https://media.geeksforgeeks.org/wp-content/uploads/20200601211203/string_pool_2.png)

In this case, both the string objects get created in the stack, but another instance of the value “Hello” is not created in the heap. Instead, the previous instance of “Hello” is re-used. The string constant pool is a small cache that resides within the heap. Java stores all the values inside the string constant pool on direct allocation. This way, if a similar value needs to be accessed again, a new string object created in the stack can reference it directly with the help of a pointer. In other words, the string constant pool exists mainly to reduce memory usage and improve the re-use of existing instances in memory. When a string object is assigned a different value, the new value will be registered in the string constant pool as a separate instance. 

![string constant pool 3](https://media.geeksforgeeks.org/wp-content/uploads/20200602014728/string_pool_3.png)

One way to skip this memory allocation is to use the new keyword while creating a new string object. The ‘new’ keyword forces a new instance to always be created regardless of whether the same value was used previously or not. Using ‘new’ forces the instance to be created in the heap outside the string constant pool which is clear, since caching and re-using of instances isn’t allowed here. 

![string constant pool 4](https://media.geeksforgeeks.org/wp-content/uploads/20200602104736/output_string_4.png)

## What is the difference between String and StringBuilder?
String is immutable, but StringBuffer is mutable.

## What is the difference between StringBuilder and StringBuffer?
StringBuilder is being used bu a single thread, it is faster than StringBuffer.

StringBuffer is safe for use by multiple threads, some methods are synchronized.

## Conversion between types of strings
### From String to StringBuffer and StringBuilder
We can directly pass String class object to StringBuffer and StringBuilder class constructors.
```java
String str = "Geeks"; 
          
// conversion from String object to StringBuffer 
StringBuffer sbr = new StringBuffer(str); 

// conversion from String object to StringBuilder 
StringBuilder sbl = new StringBuilder(str); 
```
### From StringBuffer and StringBuilder to String
This conversions can be perform using toString() method which is overridden in both StringBuffer and StringBuilder classes.
```java
StringBuffer sbr = new StringBuffer("Geeks"); 
StringBuilder sbdr = new StringBuilder("Hello"); 
          
// conversion from StringBuffer object to String 
String str = sbr.toString(); 

// conversion from StringBuilder object to String 
String str1 = sbdr.toString(); 
```
### From StringBuffer to StringBuilder or vice-versa
We first convert StringBuffer/StringBuilder object to String using toString() method and then from String to StringBuilder/StringBuffer using constructors.
```java
StringBuffer sbr = new StringBuffer("Geeks"); 
          
// conversion from StringBuffer object to StringBuilder 
String str = sbr.toString(); 
StringBuilder sbl = new StringBuilder(str); 
```
