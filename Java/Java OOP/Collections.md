# Collections
![Collections](https://media.geeksforgeeks.org/wp-content/uploads/20200623124952/Java-Collections-Hierarchy-1.png)

## List
This is a child interface of the collection interface. This interface is dedicated to the data of the list type in which **we can store all the ordered collection of the objects.** This also **allows duplicate data to be present in it.** This list interface is implemented by various classes like ArrayList, Vector, Stack, etc. Since all the subclasses implement the list, we can instantiate a list object with any of these classes.
```java
List <T> al = new ArrayList<> ();
List <T> ll = new LinkedList<> ();
List <T> v = new Vector<> ();
Where T is the type of the object
```
### ArrayList
ArrayList provides us with **dynamic arrays** in Java. Though, it may be **slower than standard arrays** but can be helpful in programs where lots of manipulation in the array is needed. **The size of an ArrayList is increased automatically** if the collection grows or shrinks if the objects are removed from the collection. Java ArrayList allows us to **randomly access the list**. ArrayList **can not be used for primitive types**, like int, char, etc. We will need a wrapper class for such cases.

#### Methods
##### Adding Elements
> + add(Object): This method is used to add an element at the end of the ArrayList.
> + add(int index, Object): This method is used to add an element at a specific index in the ArrayList.

##### Changing Elements
> + set(int index, Object): Replaces the element at the specified position in this list with the specified element.

##### Removing Elements
> + remove(Object): This method is used to simply remove an object from the ArrayList. If there are multiple such objects, then the first occurrence of the object is removed.
> + remove(int index): Since an ArrayList is indexed, this method takes an integer value which simply removes the element present at that specific index in the ArrayList. After removing the element, all the elements are moved to the left to fill the space and the indices of the objects are updated.

##### Iterating the ArrayList

### LinkedList
LinkedList class is an implementation of the LinkedList data structure which is a linear data structure where **the elements are not stored in contiguous locations and every element is a separate object with a data part and address part.** The elements are **linked using pointers and addresses.** Each element is known as a node.

### Vector
A vector provides us with dynamic arrays in Java. Though, it may be slower than standard arrays but can be helpful in programs where lots of manipulation in the array is needed. This is identical to ArrayList in terms of implementation. However, the primary difference between a vector and an ArrayList is that **a Vector is synchronized and an ArrayList is non-synchronized.**

### Stack
Stack class models and implements the Stack data structure. The class is based on the basic principle of **last-in-first-out**. In addition to the basic push and pop operations, the class provides three more functions of empty, search and peek. The class can also be referred to as **the subclass of Vector**. 

## Queue
A queue interface maintains the **FIFO(First In First Out) order** similar to a real-world queue line. This interface is dedicated to **storing all the elements where the order of the elements matter.** 