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

### LinkedList
LinkedList class is an implementation of the LinkedList data structure which is a linear data structure where **the elements are not stored in contiguous locations and every element is a separate object with a data part and address part.** The elements are **linked using pointers and addresses.** Each element is known as a node.

### Vector
A vector provides us with dynamic arrays in Java. It is identical to ArrayList in terms of implementation. However, the primary difference between a vector and an ArrayList is that **a Vector is synchronized and an ArrayList is non-synchronized.**

### Stack
Stack class models and implements the Stack data structure. The class is based on the basic principle of **last-in-first-out**. In addition to the basic push and pop operations, the class provides three more functions of empty, search and peek. The class can also be referred to as **the subclass of Vector**. 

### What is the difference between ArrayList and LinkedList and Vector
1. ArrayList is a dynamic array, it's elements can be accessed directly by using the get and set methods.

2. LinkedList is implemented as a double linked list, its performance on add and remove is better than arraylist, but worse on get and set methods.

3. Vector is similar with arraylist, but it is synchronized.

### What is the difference between ArrayList and Array
1. An array is basic functionality provided by Java. ArrayList is part of collection framework in Java.
2. Array can contain both primitive data types as well as objects of a class . However, ArrayList only supports object entries.
3. Array is a fixed size data structure while ArrayList is not.
4. ArrayList supports many additional operations like indexOf(), remove(), etc. These functions are not supported by Arrays.

## Queue
A queue interface maintains the **FIFO(First In First Out) order** similar to a real-world queue line. This interface is dedicated to **storing all the elements where the order of the elements matter.** 
```java
Queue <T> pq = new PriorityQueue<> ();
Queue <T> ad = new ArrayDeque<> ();
Where T is the type of the object.
```

### Priority Queue
A PriorityQueue is used when the objects are supposed to be processed **based on the priority**. It is known that a queue follows the First-In-First-Out algorithm, but sometimes the elements of the queue are needed to be processed according to the priority and this class is used in these cases. **The PriorityQueue is based on the priority heap**. The elements of the priority queue are ordered according to the natural ordering, or by **a Comparator provided at queue construction time**, depending on which constructor is used.

## Set
A set is **an unordered collection of objects in which duplicate values cannot be stored**. This collection is used when we wish to **avoid the duplication of the objects and wish to store only the unique objects**. This set interface is implemented by various classes like HashSet, TreeSet, LinkedHashSet, etc. Since all the subclasses implement the set, we can instantiate a set object with any of these classes.

```java
Set<T> hs = new HashSet<> ();
Set<T> lhs = new LinkedHashSet<> ();
Set<T> ts = new TreeSet<> ();
Where T is the type of the object.
```

### HashSet
The HashSet class is an inherent implementation of the **hash table data structure**. It implements the Set interface, backed by a hash table which is actually a HashMap instance.The objects that we insert into the HashSet **do not guarantee to be inserted in the same order**. The objects are inserted **based on their hashcode**. This class also allows the **insertion of NULL elements**.

### LinkedHashSet
A LinkedHashSet is very similar to a HashSet. The difference is that this uses **a doubly linked list to store the data** and **retains the ordering of the elements**.

### Sorted Set
This interface is very similar to the set interface. The only difference is that this interface has extra methods that maintain the ordering of the elements. The sorted set interface extends the set interface and is used to handle the data which needs to be sorted. The class which implements this interface is TreeSet. Since this class implements the SortedSet, we can instantiate a SortedSet object with this class.

```java
SortedSet<T> ts = new TreeSet<> ();
Where T is the type of the object.
```
#### TreeSet
The TreeSet class uses a Tree for storage. The ordering of the elements is maintained by a set using their natural ordering whether or not an explicit comparator is provided. This must be consistent with equals if it is to correctly implement the Set interface. It can also be ordered by a Comparator provided at set creation time, depending on which constructor is used.

## Map
A map is a data structure which supports **the key-value pair mapping for the data**. This interface **doesn’t support duplicate keys because the same key cannot have multiple mappings**. A map is useful if there is a data and **we wish to perform operations on the basis of the key**. This map interface is implemented by various classes like HashMap, TreeMap etc. Since all the subclasses implement the map, we can instantiate a map object with any of these classes.

```java
Map<T> hm = new HashMap<> ();
Map<T> tm = new TreeMap<> ();
Where T is the type of the object.
```

### HashMap
HashMap provides the basic implementation of the Map interface of Java. **It stores the data in (Key, Value) pairs**. **To access a value in a HashMap, we must know its key**. HashMap uses a technique called Hashing. Hashing is a technique of converting a large String to small String that represents the same String so that the indexing and search operations are faster. HashSet also uses HashMap internally.

**It may have one null key and multiple null values.**

**It maintains no order.**

**If the key of the HashMap is self-defined objects, then equals() and hashCode() contract need to be followed.**

### HashTable
This class implements a hash table, which maps keys to values. **Any non-null object can be used as a key or as a value**. To successfully store and retrieve objects from a hashtable, the objects used as keys must implement the hashCode method and the equals method.

**A Hashtable is an array of list. Each list is known as a bucket. The position of bucket is identified by calling the hashcode() method. A Hashtable contains values based on the key.**

**It can not have any null key or value.**

**It is synchronized.**

### LinkedHashMap
**The LinkedHashMap is just like HashMap with an additional feature of maintaining an order of elements inserted into it.** HashMap provided the advantage of quick insertion, search, and deletion but it never maintained the track and order of insertion which the LinkedHashMap provides where the elements can be accessed in their insertion order. 

**It may have one null key and multiple null values.**

**It is non-synchronized.**

### TreeMap
The TreeMap in Java is used to implement Map interface and NavigableMap along with the AbstractMap Class. The map is **sorted according to the ordering of its keys**, or **by a Comparator provided at map creation time, depending on which constructor is used.** This proves to be an efficient way of sorting and storing the key-value pairs.

**It cannot have null key but can have multiple null values.**

**It is non-synchronized.**

### What is the difference between HashMap and HashTable
1. HashTable is synchronised, but HashMap is not.
2. HashTable does not allow null key or value, but HashMap may have one null key and multiple null values.

### What is the difference between HashMap and TreeMap and LinkedHashMap  
1. HashMap is implemented as a hash table, and there is no ordering on keys or values. 
2. TreeMap is implemented based on red-black tree structure, and it is ordered by the key. 
3. LinkedHashMap preserves the insertion order 

![Comparison Table](https://media.geeksforgeeks.org/wp-content/uploads/comparisonTable.png)








