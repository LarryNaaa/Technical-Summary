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

### 9.1 **HashTable**

- 方法使用synchronized上锁，并发效率低

#### 9.1.1 为啥Hashtable 是不允许键或值为 null 的，HashMap 的键值则都可以为 null？

- 这是因为Hashtable使用的是**安全失败机制（fail-safe）**，这种机制会使此次读到的数据不一定是最新的数据。
- 如果使用null值，就会使得其无法判断对应的key是不存在还是为空，因为你无法再调用一次contain(key）来对key是否存在进行判断，ConcurrentHashMap同理。

### 9.1.2 说出一些Hashtable 跟HashMap不一样点么？

- Hashtable 是不允许键或值为 null 的，HashMap 的键值则都可以为 null。
- **实现方式不同**：Hashtable 继承了 Dictionary类，而 HashMap 继承的是 AbstractMap 类。
- **初始化容量不同**：HashMap 的初始容量为：16，Hashtable 初始容量为：11，两者的负载因子默认都是：0.75。
- **扩容机制不同**：当现有容量大于总容量 * 负载因子时，HashMap 扩容规则为当前容量翻倍，Hashtable 扩容规则为当前容量翻倍 + 1。
- **迭代器不同**：HashMap 中的 Iterator 迭代器是 fail-fast 的，而 Hashtable 的 Enumerator 不是 fail-fast 的。所以，当其他线程改变了HashMap 的结构，如：增加、删除元素，将会抛出ConcurrentModificationException 异常，而 Hashtable 则不会。

### 9.1.3 fail-fast

- **快速失败（fail—fast）**是java集合中的一种机制， 在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出Concurrent Modification Exception。
- 工作原理：迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个 modCount 变量。集合在被遍历期间如果内容发生变化，就会改变modCount的值。每当迭代器使用hashNext()/next()遍历下一个元素之前，都会检测modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出异常，终止遍历。
- 应该使用迭代器的remove方法，改方法会在remove元素后更新expectmodcount。
- 场景：java.util包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改）算是一种安全机制吧。
- 非保障的，迭代器的快速失败行为应该仅用于检测程序中的bug

### 9.1.4 fail—safe

- java.util.concurrent包下的容器都是安全失败，可以在多线程下并发使用，并发修改。
- 采用fail-safe机制来说，就不会抛出异常
- 当集合的结构被改变的时候，fail-safe机制会在复制原集合的一份数据出来，然后在复制的那份数据遍历。
- 缺点：
  1. 复制时需要额外的空间和时间上的开销。
  2. 不能保证遍历的是最新内容。

### 9.2 **Collections.synchronizedMap()**

- 内部维护了一个普通对象Map，还有排斥锁mutex，将对象排斥锁赋值为this，即调用synchronizedMap的对象，创建出synchronizedMap之后，再操作map的时候，就会对方法上锁
- 并发效率低

### 9.3 **CurrentHashMap**

#### 9.3.1 JDK 1.7

- 是由 Segment 数组、HashEntry 组成，和 HashMap 一样，仍然是**数组加链表**。
- HashEntry跟HashMap差不多的，但是不同点是，他使用volatile去修饰了他的数据Value还有下一个节点next。
- 原理上来说，ConcurrentHashMap 采用了**分段锁**技术，其中 Segment 继承于 ReentrantLock。不会像 HashTable 那样不管是 put 还是 get 操作都需要做同步处理，理论上 ConcurrentHashMap 支持 CurrencyLevel (Segment 数组数量)的线程并发。每当一个线程占用锁访问一个 Segment 时，不会影响到其他的 Segment。就是说如果容量大小是16他的并发度就是16，可以同时允许16个线程操作16个Segment而且还是线程安全的。

```java
static final class Segment<K,V> extends ReentrantLock implements Serializable {

    private static final long serialVersionUID = 2249069246763182397L;

    // 和 HashMap 中的 HashEntry 作用一样，真正存放数据的桶
    transient volatile HashEntry<K,V>[] table;

    transient int count;
        // 记得快速失败（fail—fast）么？
    transient int modCount;
        // 大小
    transient int threshold;
        // 负载因子
    final float loadFactor;

}
```

- 先定位到Segment，然后再进行Segmentd的put操作。
- 首先第一步的时候会尝试获取锁，如果获取失败肯定就有其他线程存在竞争，则利用 `scanAndLockForPut()` 自旋获取锁。
  1. 尝试自旋获取锁。
  2. 如果重试的次数达到了 `MAX_SCAN_RETRIES` 则改为阻塞锁获取，保证能获取成功。

```java
final V put(K key, int hash, V value, boolean onlyIfAbsent) {
          // 将当前 Segment 中的 table 通过 key 的 hashcode 定位到 HashEntry
            HashEntry<K,V> node = tryLock() ? null :
                scanAndLockForPut(key, hash, value);
            V oldValue;
            try {
                HashEntry<K,V>[] tab = table;
                int index = (tab.length - 1) & hash;
                HashEntry<K,V> first = entryAt(tab, index);
                for (HashEntry<K,V> e = first;;) {
                    if (e != null) {
                        K k;
 // 遍历该 HashEntry，如果不为空则判断传入的 key 和当前遍历的 key 是否相等，相等则覆盖旧的 value。
                        if ((k = e.key) == key ||
                            (e.hash == hash && key.equals(k))) {
                            oldValue = e.value;
                            if (!onlyIfAbsent) {
                                e.value = value;
                                ++modCount;
                            }
                            break;
                        }
                        e = e.next;
                    }
                    else {
                 // 不为空则需要新建一个 HashEntry 并加入到 Segment 中，同时会先判断是否需要扩容。
                        if (node != null)
                            node.setNext(first);
                        else
                            node = new HashEntry<K,V>(hash, key, value, first);
                        int c = count + 1;
                        if (c > threshold && tab.length < MAXIMUM_CAPACITY)
                            rehash(node);
                        else
                            setEntryAt(tab, index, node);
                        ++modCount;
                        count = c;
                        oldValue = null;
                        break;
                    }
                }
            } finally {
               //释放锁
                unlock();
            }
            return oldValue;
        }
```

- get 逻辑比较简单，只需要将 Key 通过 Hash 之后定位到具体的 Segment ，再通过一次 Hash 定位到具体的元素上。由于 HashEntry 中的 value 属性是用 volatile 关键词修饰的，保证了内存可见性，所以每次获取时都是最新值。ConcurrentHashMap 的 get 方法是非常高效的，**因为整个过程都不需要加锁**。

- rehash()方法：每个Segment只管它自己的扩容，互相之间并不影响。换句话说，可以出现这个 Segment的长度为2，另一个Segment的长度为4的情况（只要是2的n次幂）。

- 因为基本上还是数组加链表的方式，我们去查询的时候，还得遍历链表，会导致效率很低，这个跟jdk1.7的HashMap是存在的一样问题，所以他在jdk1.8完全优化了。

#### 9.3.2 JDK 1.8

- 其中抛弃了原有的 Segment 分段锁，而采用了 `CAS + synchronized` 来保证并发安全性。

- 跟HashMap很像，也把之前的HashEntry改成了Node，但是作用不变，把值和next采用了volatile去修饰，保证了可见性，并且也引入了红黑树，在链表大于一定值的时候会转换（默认是8）。

- ConcurrentHashMap在进行put操作的还是比较复杂的，大致可以分为以下步骤：

  1. 根据 key 计算出 hashcode 。
  2. 判断是否需要进行初始化。
  3. 即为当前 key 定位出的 Node，如果为空表示当前位置可以写入数据，利用 CAS 尝试写入，失败则自旋保证成功。
  4. 如果当前位置的 `hashcode == MOVED == -1`,则需要进行扩容。
  5. 如果都不满足，则利用 synchronized 锁写入数据。
  6. 如果数量大于 `TREEIFY_THRESHOLD` 则要转换为红黑树。

  ![HashMap](/Users/na/IdeaProjects/Technical summary/Image/HashMap.webp)

- get操作：

  - 根据计算出来的 hashcode 寻址，如果就在桶上那么直接返回值。
  - 如果是红黑树那就按照树的方式获取值。
  - 就不满足那就按照链表的方式遍历获取值。
  
  ![HashMap_1](/Users/na/IdeaProjects/Technical summary/Image/HashMap_1.webp)

#### 9.3.3 区别

- 采用红黑树之后可以保证查询效率（`O(logn)`）
- 取消了 ReentrantLock 改为了 synchronized

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

## 12. Hashmap中的链表大小超过八个时会自动转化为红黑树，当删除小于六时重新变为链表，为啥呢？

- 根据泊松分布，在负载因子默认为0.75的时候，单个hash槽内元素个数为8的概率小于百万分之一，所以将7作为一个分水岭，等于7的时候不转换，大于等于8的时候才进行转换，小于等于6的时候就化为链表。



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
- 谈谈你理解的 Hashtable，讲讲其中的 get put 过程。ConcurrentHashMap同问。
- 1.8 做了什么优化？
- 线程安全怎么做的？
- 不安全会导致哪些问题？
- 如何解决？有没有线程安全的并发容器？
- ConcurrentHashMap 是如何实现的？
- ConcurrentHashMap并发度为啥好这么多？
- 1.7、1.8 实现有何不同？为什么这么做？
- CAS是啥？
- ABA是啥？场景有哪些，怎么解决？
- synchronized底层原理是啥？
- synchronized锁升级策略
- 快速失败（fail—fast）是啥，应用场景有哪些？安全失败（fail—safe）同问。