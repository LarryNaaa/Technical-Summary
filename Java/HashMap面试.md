# HashMap

## 1. 数据结构中的HashMap么？能跟我聊聊他的结构和底层原理么？

- 由**数组和链表组合构成**的数据结构
- 数组里面每个地方都存了Key-Value这样的实例，在Java7叫Entry在Java8中叫Node。
- 因为他本身所有的位置都为null，在put插入的时候会根据key的hash去计算一个index值。

## 2. 你提到了还有链表，为啥需要链表，链表又是怎么样子的呢？

- hashmap中采用hash算法，哈希算法本身是概率性的，存在多个对象的哈希值相同的情况，通过哈希值计算得到的数组index也相同，因此对于多个对象在同一个数组下标存储我们可以选择链表的数据结构。
- Node的数据结构: 每一个节点都会保存自身的hash、key、value、以及下个节点

```java
static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        Node<K,V> next;
}
```

## 3. 你知道新的Entry节点在插入链表的时候，是怎么插入的么？

- **java8之前是头插法**，就是说新来的值会取代数组下标原有的值，原有的值就顺推到链表中去，
- 采用头插法的原因，据说是考虑到热点数据的原因，即最近插入的元素也很可能最近会被使用到。所以为了缩短链表查找元素的时间，所以每次都会将新插入的元素放到表头。

```java
    void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K,V> e = table[bucketIndex];
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        size++;
    }
```

- **在java8之后，都是所用尾部插入了。**

## 4. 尾插法的好处

- 头插法在扩容时会改变链表的顺序，例如扩容前A -> B -> C，在扩容重新计算数组下标后，可能出现环形链表的情况，即A ->B, B->A。原因是扩容转移后前后链表顺序倒置，在转移过程中修改了原来链表中节点的引用关系。此时使用get方法，就会无限循环。
- **使用尾插**，在扩容时会保持链表元素原本的顺序，就不会出现链表成环的问题了。

## 5. HashMap的扩容

### 5.1 什么时候resize呢？

- HashMap的扩容条件就是当HashMap中的元素个数（size）超过临界值（threshold）时就会自动扩容。
- 阈值(threshold) = Capacity(HashMap当前长度，默认16) * LoadFactor(负载因子，默认值0.75f，设置成0.75有一个好处，那就是0.75正好是3/4，而capacity又是2的幂。所以，两个数的乘积都是整数)
- Jdk1.7中还要求当前数组下标中的元素不为空

```java
void addEntry(int hash, K key, V value, int bucketIndex) {
　　　　//1、判断当前个数是否大于等于阈值
　　　　//2、当前存放是否发生哈希碰撞
　　　　//如果上面两个条件否发生，那么就扩容
　　　　if ((size >= threshold) && (null != table[bucketIndex])) {
　　　　　　//扩容，并且把原来数组中的元素重新放到新数组中
　　　　　　resize(2 * table.length);
　　　　　　hash = (null != key) ? hash(key) : 0;
　　　　　　bucketIndex = indexFor(hash, table.length);
　　　　}
 
　　　　createEntry(hash, key, value, bucketIndex);
　　}
```

### 5.2 扩容的步骤？

扩容分为两步

- 创建一个新的Entry空数组，长度是原数组的2倍。
- ReHash：遍历原Entry数组，把所有的Entry重新Hash到新数组。

### 5.3 为什么要重新Hash呢，直接复制过去不香么？

- 是因为长度扩大以后，Hash的规则也随之改变。
- Hash的公式： index = HashCode（Key） & （Length - 1）

```java
static int indexFor(int h, int length) {

    return h & (length-1);

}
```

- 相同的哈希值不同的数组长度，与操作后得到的数组下标可能不同

## 6. 那是不是意味着Java8就可以把HashMap用在多线程中呢？

- 即使不会出现死循环，但是通过源码看到put/get方法都没有加同步锁，多线程情况最容易出现的就是：无法保证上一秒put的值，下一秒get的时候还是原值，所以线程安全还是无法保证。

## 7. HashMap的默认初始化长度为什么是2的幂？

- 因为位运算直接对内存数据进行操作，不需要转成十进制，所以位运算要比取模运算的效率更高，所以HashMap在计算元素要存放在数组中的index的时候，使用位运算代替了取模运算。之所以可以做等价代替，前提是要求HashMap的容量一定要是2^n 。

### 7.1 为什么默认值是16？

- 为了**实现哈希算法的均匀分布**，16 - 1 = 15，二进制为 0111，此时index的结果取决于hashcode的后几位的值，因此只要输入的HashCode本身分布均匀，Hash算法的结果就是均匀的。

### 7.2 如何保证其容量一定可以是2^n 的呢？

- HashMap根据用户传入的初始化容量，利用无符号右移和按位或运算等方式计算出第一个大于该数的2的幂。

```java
public static int highestOneBit(int i) {
        // HD, Figure 3-1
        i |= (i >>  1);
        i |= (i >>  2);
        i |= (i >>  4);
        i |= (i >>  8);
        i |= (i >> 16);
        return i - (i >>> 1);
    }
```

## 8. 为啥我们重写equals方法的时候需要重写hashCode方法呢？你能用HashMap给我举个例子么？

- Ojbect类中有两个方法equals、hashCode，这两个方法都是用来比较两个对象是否相等的。
- Ojbect的equals方法是比较两个对象的内存地址
- HashMap是通过key的hashCode去寻找index，如果两个对象的index相同，需要通过equals方法判断哪个才是get的对象
- 重写hashCode方法：以保证相同的对象返回相同的hash值，不同的对象返回不同的hash值。

## 9. 怎么处理HashMap在线程安全的场景么？

- **HashTable**：方法使用synchronized上锁，并发效率低
- **Collections.synchronizedMap()**：synchronized对mutex对象上锁，在synchronized代码块中执行map方法，并发效率低
- **CurrentHashMap**：

## 10. jdk1.7中的HashMap扩容造成死循环

- 通过jps和jstack命名查看死循环情况，从堆栈信息中可以看到出现死循环的位置，通过该信息可明确知道死循环发生在HashMap的扩容函数中，根源在**transfer函数**中。

```java
void transfer(Entry[] newTable, boolean rehash) {
        int newCapacity = newTable.length;
        for (Entry<K,V> e : table) {
            while(null != e) {
                Entry<K,V> next = e.next;
                if (rehash) {
                    e.hash = null == e.key ? 0 : hash(e.key);
                }
                int i = indexFor(e.hash, newCapacity);
                e.next = newTable[i];
                newTable[i] = e;
                e = next;
            }
        }
    }
```

- 在对table进行扩容到newTable后，需要将原来数据转移到newTable中，在转移元素的过程中，使用的是头插法，也就是链表的顺序会翻转，这里也是形成死循环的关键点。

## 11. jdk1.8**线程不安全**

- 在多线程环境下，会发生数据覆盖的情况
- 如果没有hash碰撞则会直接插入元素。如果线程A和线程B同时进行put操作，刚好这两条不同的数据hash值一样，并且该位置数据为null，所以这线程A、B都会进入第6行代码中。假设一种情况，线程A进入后还未进行数据插入时挂起，而线程B正常执行，从而正常插入数据，然后线程A获取CPU时间片，此时线程A不用再进行hash判断了，线程A会把线程B插入的数据给**覆盖**，发生线程不安全。



HashMap常见面试题：

- HashMap的底层数据结构？
- HashMap的存取原理？
- Java7和Java8的区别？
- 为啥会线程不安全？
- 有什么线程安全的类代替么?
- 默认初始化大小是多少？为啥是这么多？为啥大小都是2的幂？
- HashMap的扩容方式？负载因子是多少？为什是这么多？
- HashMap的主要参数都有哪些？
- HashMap是怎么处理hash碰撞的？
- hash的计算规则？