# Overload
Overloading allows different methods to have the same name, 
but different signatures where the signature can differ by the 
number of input parameters or type of input parameters or both. 

We don’t have to create and remember different names for functions doing the same thing.

# Override
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