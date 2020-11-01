# Object

## Methods

### hashCode()
The contract between equals() and hashCode() is:
1. If two objects are equal, then they must have the same hash code.
2. If two objects have the same hash code, they may or may not be equal.

The idea behind a Map is to be able to find an object faster than a linear search. Using hashed keys to locate objects is a two-step process. Internally, the HashMap is implemented as an array of Entry objects. Each Entry has a pair and a pointer pointing to the next Entry. The hash code of the key object is the index for addressing the array. This locates the linked list of Entries in that cell of the array. The linked list in the cell is then searched linearly by using equals() to determine if two objects are equal.

The default implementation of hashCode() in Object class returns distinct integers for different objects. Therefore, the second apple has a different hash code.

The HashMap is organized like a sequence of buckets. The key objects are put into different buckets. It takes time of O(1) to get to the right bucket because it is an array access with the index. Therefore, it's a good practice to evenly distribute the objects into those buckets, i.e., having a hashCode() method that produces evenly distributed hash code. (Not the main point here though)