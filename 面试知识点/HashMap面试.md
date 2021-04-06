# HashMap

[TOC]

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

- 为了**实现哈希算法的均匀分布**，**最大程度减少hash碰撞**，16 - 1 = 15，二进制为 1111，此时index的结果取决于hashcode的后几位的值，因此只要输入的HashCode本身分布均匀，Hash算法的结果就是均匀的。

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
- 当通过get(k)获取对应的value时，如果获取到的是null时，无法判断，它是put（k,v）的时候value为null，还是这个key从来没有做过映射。假如线程1调用m.contains（key）返回true，然后在调用m.get(key)，这时的m可能已经不同了。因为线程2可能在线程1调用m.contains（key）时，删除了key节点，这样就会导致线程1得到的结果不明确，**产生多线程安全问题**，因此，Hashmap和ConcurrentHashMap的key和value不能为null。

### 9.1.2 说出一些Hashtable 跟HashMap不一样点么？

- Hashtable 是不允许键或值为 null 的，HashMap 的键值则都可以为 null。
- HashMap 是线程不安全的，HashTable 是线程安全的；
- 1.8之后hashmap采用数组+链表+红黑树的数据结构；Hashtable的底层实现都是`数组`+`链表`结构实现
- Hashtable计算hash是直接使用key的hashcode对table数组的长度直接进行取模；HashMap通过位运算
- **实现方式不同**：Hashtable 继承了 Dictionary类，而 HashMap 继承的是 AbstractMap 类。
- **初始化容量不同**：HashMap 的初始容量为：16，Hashtable 初始容量为：11，两者的负载因子默认都是：0.75。
- **扩容机制不同**：当现有容量大于总容量 * 负载因子时，HashMap 扩容规则为当前容量翻倍，Hashtable 扩容规则为当前容量翻倍 + 1。
- **迭代器不同**：HashMap 中的 Iterator 迭代器是 fail-fast 的，而 Hashtable 的 Enumerator 不是 fail-fast 的。所以，当其他线程改变了HashMap 的结构，如：增加、删除元素，将会抛出ConcurrentModificationException 异常，而 Hashtable 则不会。

### 9.1.3 fail-fast

- 在用迭代器遍历一个集合对象时，如果遍历过程中对集合对象的内容进行了修改（增加、删除、修改），则会抛出Concurrent Modification Exception。
- **原理：迭代器在遍历时直接访问集合中的内容，并且在遍历过程中使用一个 modCount 变量。集合在被遍历期间如果内容发生变化，就会改变modCount的值。每当迭代器使用hashNext()/next()遍历下一个元素之前，都会检测modCount变量是否为expectedmodCount值，是的话就返回遍历；否则抛出异常，终止遍历。**
- 注意：这里异常的抛出条件是检测到 modCount！=expectedmodCount 这个条件。如果集合发生变化时修改modCount值刚好又设置为了expectedmodCount值，则异常不会抛出。因此，不能依赖于这个异常是否抛出而进行并发操作的编程，这个异常只建议用于检测并发修改的bug。
- **场景：java.util包下的集合类都是快速失败的，不能在多线程下发生并发修改（迭代过程中被修改）。**

### 9.1.4 fail—safe

- 采用fail-safe机制来说，就不会抛出异常
- **采用安全失败机制的集合容器，在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。**
- **原理：由于迭代时是对原集合的拷贝进行遍历，所以在遍历过程中对原集合所作的修改并不能被迭代器检测到，所以不会触发Concurrent Modification Exception。**
- 缺点：基于拷贝内容的优点是避免了Concurrent Modification Exception，但同样地，迭代器并不能访问到修改后的内容，即：迭代器遍历的是开始遍历那一刻拿到的集合拷贝，在遍历期间原集合发生的修改迭代器是不知道的。
- **场景：java.util.concurrent包下的容器都是安全失败，可以在多线程下并发使用，并发修改。**

### 9.2 **Collections.synchronizedMap()**

- 内部维护了一个普通对象Map，还有排斥锁mutex，将对象排斥锁赋值为this，即调用synchronizedMap的对象，创建出synchronizedMap之后，再操作map的时候，就会对方法上锁
- 并发效率低

### 9.3 ConcurrentHashMap

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
                 // 为空则需要新建一个 HashEntry 并加入到 Segment 中，同时会先判断是否需要扩容。
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

#### 9.3.3 为什么不用ReentrantLock而用synchronized

- 减少内存开销：如果使用ReentrantLock则需要节点继承AQS来获得同步支持，增加内存开销，而1.8中只有头节点需要进行同步。
- 内部优化：synchronized则是JVM直接支持的，JVM能够在运行时作出相应的优化措施：锁粗化、锁消除、锁自旋等等。

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

- 根据泊松分布，在负载因子默认为0.75的时候，8 个键值对同时存在于同一个桶的概率小于百万分之一，所以将7作为一个分水岭，等于7的时候不转换，大于等于8的时候才进行转换，小于等于6的时候就化为链表。还有选择6和8，中间有个差值7可以有效防止链表和树频繁转换。假设一下，如果设计成链表个数超过8则链表转换成树结构，链表个数小于8则树结构转换成链表，如果一个HashMap不停的插入、删除元素，链表个数在8左右徘徊，就会频繁的发生树转链表、链表转树，效率会很低。

## 13. Hashmap的结构，1.7和1.8有哪些区别

- **JDK1.7用的是头插法，而JDK1.8及之后使用的都是尾插法**
- **JDK1.7的时候是直接用hash值和需要扩容的二进制数进行&**，**JDK1.8原始位置或原始位置+扩容的大小值**
- **JDK1.7的时候使用的是数组+ 单链表的数据结构。但是在JDK1.8及之后时，使用的是数组+链表+红黑树的数据结构**
- 在hash 取下标时将1.7 的9次扰动（5次按位与和4次位运算）改为2次（一次按位与和一次位运算）
- JDK1.7用的是Entry，JDK1.8用的是Node

## 14. HashMap put和get操作

- **1.7 添加操作的执行流程为:**
  - 先判断有没有初始化
  - 再判断传入的key 是否为空，为空保存在table[o] 位置
  - key 不为空就对key 进hash，hash 的结果再& 数组的长度就得到存储的位置
  - 如果存储位置为空则创建节点，不为空就说明存在冲突
  - 解决冲突HashMap 会先遍历链表，如果有相同的value 就更新旧值，否则构建节点添加到链表头
  - 添加还要先判断存储的节点数量是否达到阈值，到达阈值要进行扩容
  - 扩容扩2倍，是新建数组所以要先转移节点，转移时都重新计算存储位置，可能保持不变可能为旧容量+位置。
  - 扩容结束后新插入的元素也得再hash 一遍才能插入。
- 1.7 获取节点的操作和添加差不多，也是
  - 先判断是否为空，为空就在table[0] 去找值
  - 不为空也是先hash,&数组长度计算下标位置
  - 再遍历找相同的key 返回值

-  1.8 put() 方法：

  - ①. 判断键值对数组table[i]是否为空或为null，否则执行resize()进行扩容，初始容量是16；
  - ②. 根据键值key计算hash值得到插入的数组索引i，如果table[i]==null，直接新建节点添加，转向⑥，如果table[i]不为空，转向③；
  - ③. 判断table[i]的首个元素是否和key一样，如果相同直接覆盖value，否则转向④，这里的相同指的是hashCode以及equals；
  - ④. 判断table[i] 是否为TreeNode，即table[i] 是否是红黑树，如果是红黑树，遍历发现该key不存在 则直接在树中插入键值对；遍历发现key已经存在直接覆盖value即可；
  - ⑤. 如果table[i] 不是TreeNode则是链表节点，遍历发现该key不存在，则先添加在链表结尾， 判断链表长度是否大于8，大于8的话把链表转换为红黑树；遍历发现key已经存在直接覆盖value即可；
  - ⑥. 插入成功后，判断实际存在的键值对数量size是否超多了最大容量threshold，如果超过，进行扩容。

- 1.8 get步骤总结如下：

  通过hash定位桶，然后根据该桶的存储结构决定是遍历红黑树还是遍历链表。

  1 table[i]的首个元素是否和key一样，如果相同则返回该value

  2 如果不同，先判断首元素是否是红黑树节点，如果是则去红黑树中查找；反之去链表中查找

## 15. **红黑树**

- 1.节点是红色或黑色。

  2.根节点是黑色。

  3.每个叶子节点都是黑色的空节点（NIL节点）。

  4 每个红色节点的两个子节点都是黑色。(从每个叶子到根的所有路径上不能有两个连续的红色节点)

  5.从任一节点到其每个叶子的所有路径都包含相同数目的黑色节点。

- 插入：新节点为红色

  - 父节点是黑色，不用调整
  - 父节点是红色：
    - 叔叔节点是黑色或是null，旋转+变色
    - 叔叔节点是红色，父节点+叔叔节点变黑色，祖父节点变红色

```java
static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
                                                    TreeNode<K,V> x) {
            x.red = true;
            // x是新插入的节点，xp是父节点，xpp是祖父节点，xppl祖父节点的左儿子，xppr祖父节点的右儿子
            // 循环递归判断红黑树是否合适
            for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
                // 判断新插入的节点是不是根节点
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                // 父节点为黑色，不同调整
                else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                // 父节点是红色:
                // 父节点为祖父节点的左儿子:
                if (xp == (xppl = xpp.left)) {
                    // 叔叔节点是红色: 
                    if ((xppr = xpp.right) != null && xppr.red) {
                        // 父节点+叔叔节点变黑色，祖父节点变红色
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        // 继续递归
                        x = xpp;
                    }
                    // 叔叔节点是黑色或是null:
                    else {
                        // 新插入的节点是父节点的右孩子，先左旋再右旋
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        // 新插入的节点是父节点的左孩子，右旋
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                }
                // 父节点为祖父节点的右儿子:
                else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }
```

## 16. LinkedHashMap，TreeMap 有什么区别？使用场景？

- LinkedHashMap 保存了记录的插入顺序，在用 Iterator 遍历时，先取到的记录肯定是先插入的；遍历比 HashMap 慢；在需要按自然顺序或自定义顺序遍历键的 情况下；
- TreeMap 实现 SortMap 接口，能够把它保存的记录根据键排序（默认按键值升序排序，也可以指定排序的比较器）；在需要输出的顺序和输入的顺序相同的情况下。

## 17. 解决Hash 冲突的不同方案

- 链地址法
- 开发地址：线性探测法、平方探测法
- 完全散列：布谷鸟散列