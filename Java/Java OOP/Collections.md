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

### HashTable
This class implements a hash table, which maps keys to values. **Any non-null object can be used as a key or as a value**. To successfully store and retrieve objects from a hashtable, the objects used as keys must implement the hashCode method and the equals method.

1. **It is similar to HashMap, but is synchronised.**
2. HashTable stores key/value pair in hash table.
3. In HashTable we specify an object that is used as a key, and the value we want to associate to that key. **The key is then hashed, and the resulting hash code is used as the index at which the value is stored within the table.**

#### What is the difference between HashMap and HashTable
1. HashTable is synchronised, but HashMap is not.
2. HashTable does not allow null key or value, but HashMap allows null key also but only once and multiple null values.










