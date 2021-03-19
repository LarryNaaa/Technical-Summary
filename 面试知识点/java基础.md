# java基础

[TOC]

## 1. 深拷贝和浅拷贝

- 浅拷贝只复制对象，不复制对象的引用
- 深拷贝不仅复制对象还复制对象的引用
- 类实现Cloneable方法，重写clone方法抛出clonenotsupportedexception

![java基础_1](/Users/na/IdeaProjects/Technical summary/Image/java基础_1.png)

## 2. 什么是多态?

- **一个引用变量到底会指向哪个类的实例对象，该引用变量发出的方法调用到底是哪个类中实现的方法，必须由程序运行期间才能决定**，这就是多态性
  多态性可以分为编译时多态和运行时多态。

  - **编译时多态**：主要是指方法的重载，它是根据参数列表的不同来区分不同的函数
  - **运行时多态**：在运行时谈不上多态，而运行时多态是动态的，它是通过动态绑定来实现，也就是我们说的多态性

  ###### 多态的实现

  Java实现多态有三个必要条件：继承、重写（覆盖）、向上转型

  - **继承**：在多态中必须存在有继承关系的子类和父类
  - **重写**：子类对父类中某些方法进行重新定义，在调用这些方法时就会调用子类的方法
  - **向上转型**：**在多态中需要将子类的引用赋给父类对象**，只有这样该引用才能够具备调用父类的方法和子类的方法（**当子类中重写了父类的方法时，则调用时会调用子类的方法**）

## 3. 接口与抽象类的区别

- 接口只有定义，不能有方法的实现，java 1.8中可以定义default方法体，而抽象类可以有定义与实现，方法可在抽象类中实现。
- 实现接口的关键字为implements，继承抽象类的关键字为extends。一个类可以实现多个接口，但一个类只能继承一个抽象类。所以，使用接口可以间接地实现多重继承。
- 接口强调特定功能的实现，而抽象类强调所属关系。
- 接口成员变量默认为public static final，必须赋初值，不能被修改；其所有的成员方法都是public、abstract的。抽象类中成员变量默认default，可在子类中被重新定义，也可被重新赋值；抽象方法被abstract修饰，不能被private、static、synchronized和native等修饰，必须以分号结尾，不带花括号。
- 都不能被实例化，接口的实现类或抽象类的子类都只有实现了接口或抽象类中的方法后才能实例化。

## 4. 反射

- 在运行状态中，对于任何一个类，我们都能够知道这个类有哪些方法和属性。对于任何一个对象，我们都能够对它的方法和属性进行调用。我们把这种动态获取对象信息和调用对象方法的功能称之为反射机制.
- 获取class文件对象的方式：
          1：Object类的getClass()方法
          2：数据类型的静态属性class
          3：Class类中的静态方法：Class.forName(“类的路径”)
- 创建对象的两种方式：
  
-  class对象的newInstance()方法：要求class对象有默认的空构造器
  
  -  Constructor对象的newInstance()方法：可以选择构造方法创建实例
  
- 实现java反射的类有什么？

  （1）Class：表示正在运行的Java应用程序中的类和接口，注意所有获取对象的信息都需要Class类来实现；

  （2）Field：提供有关类和接口的属性信息，以及对它的动态访问权限；

  （3）Constructor：提供关于类的单个构造方法的信息以及它的访问权限；

  （4）Method：提供类或接口中某个方法的信息。

- 获取成员变量并使用
          1: 获取Class对象
          2：通过Class对象获取Constructor对象
          3：Object obj = Constructor.newInstance()创建对象
          4：Field field = Class.getField("指定变量名")获取单个成员变量对象
          5：field.set(obj,"") 为obj对象的field字段赋值
  如果需要访问私有或者默认修饰的成员变量
          1:Class.getDeclaredField()获取该成员变量对象
          2:setAccessible() 暴力访问  

## 5.overload和override

- overload：一个类中，同名方法有不同的参数列表(参数类型不同，参数个数不同)
- override：子类和父类中，重写的方法有相同的参数列表和返回类型，比父类更好访问，比父类更少的异常

## 6. string

- **String是不可变的字符串**，它的底层是一个用final修饰的字符数组
- String s1 = “Cat”当我们用这种方式创建字符串对象的时候，首先会去字符串常量池中查找看有没有“Cat”字符串，如果有则返回它的地址给s1,如果没有则在常量池中创建“Cat”字符串，并将地址返回给s1.
- String s3 = new String(“Cat”)当我们用这种方式创建字符串对象的时候，首先会去字符串常量池中查找看有没有“Cat”字符串,如果没有则在常量池中创建“Cat”字符串，然后在堆内存中创建“Cat”字符串，并将堆内存中的地址返回给s3.
- StringBuilder是线程不安全的，它的执行效率比StriingBuffer要高；StringBuffer是线程安全的，它的执行效率比StringBuilder要低

## 7. ArrayList 和 LinkedList 有什么区别

- ArrayList是数组的数据结构，LinkedList是链表的数据结构
- 随机访问的时候，ArrayList的效率比较高，因为LinkedList要移动指针，而ArrayList是基于索引(index)的数据结构，可以直接映射到
- 插入、删除数据时，LinkedList的效率比较高，因为ArrayList要移动数据
- ArrayList从原理上就是数据结构中的数组，也就是内存中一片连续的空间，这意味着，当我get（index）的时候，我可以根据数组的（首地址+偏移量），直接计算出我想访问的第index个元素在内存中的位置。写过c的话，可以很容易的理解。LinkedList可以简单理解为数据结构中的链表（说简单理解，因为其实是双向循环链表），在内存中开辟的不是一段连续的空间，而是每个元素有一个[元素|下一元素地址]这样的内存结构。当get（index）时，只能从首元素开始，依次获得下一个元素的地址。用时间复杂度表示的话，ArrayList的get（n）是o（1），而LinkedList是o（n）。
- ArrayList 是线性表（数组）
  get() 直接读取第几个下标，复杂度 O(1)
  add(E) 添加元素，直接在后面添加，复杂度O（1）
  add(index, E) 添加元素，在第几个元素后面插入，后面的元素需要向后移动，复杂度O（n）
  remove（）删除元素，后面的元素需要逐个移动，复杂度O（n）
- LinkedList 是链表的操作
  get() 获取第几个元素，依次遍历，复杂度O(n)
  add(E) 添加到末尾，复杂度O(1)
  add(index, E) 添加第几个元素后，需要先查找到第几个元素，直接指针指向操作，复杂度O(n)
  remove（）删除元素，直接指针指向操作，复杂度O(1)

## 8. ArrayList 和 array有什么区别

- array可以包含基本类型和对象类型，arraylist只能包含对象类型。
- Array对象的初始化必须只定指定大小，且创建后的数组大小是固定的，而ArrayList的大小可以动态指定，其大小可以在初始化时指定，也可以不指定
- ArrayList是List接口的实现类，相比Array支持更多的方法和特性。

## 9. jdk8新特性

- Lambda表达式
- Stream：主要作用就是对集合数据进行查找过滤等操作

## 10. 链接和绑定

- **静态链接**
  当一个 字节码文件被装载进JVM内部时，如果被调用的目标方法在编译期可知，且运行期保持不变时。这种情况下将调用方法的符号引用转换为直接引用的过程称之为静态链接。
- **动态链接**
  如果被调用的方法在编译期无法被确定下来，也就是说，只能够在程序运行期将调用方法的符号引用转换为直接引用，由于这种引用转换过程具备动态性，因此也就被称之为动态链接。

对应的方法的绑定机制为：早起绑定（Early Binding）和晚期绑定（Late Bingding）。绑定是一个字段、方法或者类在符号引用被替换为直接引用的过程，这仅仅发生一次。

- **早期绑定**
  早期绑定就是指被调用的目标方法如果在编译期可知，且运行期保持不变时，即可将这个方法与所属的类型进行绑定，这样一来，由于明确了被调用的目标方法究竟是哪一个，因此也就可以使用静态链接的方式将符号引用转换为直接引用。
- **晚期绑定**
  如果被调用的方法在编译期无法被确定下来，只能够在程序运行期根据实际的类型绑定相关的方法，这种绑定方式也就被称之为晚期绑定。

## 11. Integer和int之间的转换

- **int到Integer：**

  - int a=3; Integer A=new Integer(a);
- Integer A=Integer.valueOf(a);
  
- **Integer到int:**
  - Integer A=new Integer(5); int a=A.intValue();

## 12. equals

- 判断两个对象在逻辑上是否相等，如根据类的成员变量来判断两个类的实例是否相等，而继承Object中的equals方法只能判断两个引用变量是否是同一个对象。这样我们往往需要重写equals()方法。

- 1、自反性：对于任何非空引用x，x.equals(x)应该返回true。
  2、对称性：对于任何引用x和y，如果x.equals(y)返回true，那么y.equals(x)也应该返回true。
  3、传递性：对于任何引用x、y和z，如果x.equals(y)返回true，y.equals(z)返回true，那么x.equals(z)也应该返回true。
  4、一致性：如果x和y引用的对象没有发生变化，那么反复调用x.equals(y)应该返回同样的结果。
  5、非空性：对于任意非空引用x，x.equals(null)应该返回false。

- 重写equals时总是要重写hashCode

  > **A.equals(B) 则必须使得 A.hashCode() == B.hashCode();**
  >
  > **如果 A.hashCode() ！= B.hashCode() 则 A.equals(B) 一定为false。**

```java
public boolean equals(Object another) {
 
        //先判断是不是自己,提高运行效率
        if (this == another)
            return true;
 
        //再判断是不是Person类,提高代码的健壮性
        if (another instanceof Person2) {
 
            //向下转型,父类无法调用子类的成员和方法
            Person2 anotherPerson = (Person2) another;
 
            //最后判断类的所有属性是否相等，其中String类型和Object类型可以用相应的equals()来判断
            if ((this.getName().equals(anotherPerson.getName())) && (this.getAge() == anotherPerson.getAge()))
                return true;
        } else {
            return false;
        }
 
        return false;
    }

public int hashCode() {
        int result = 17;
        result = 31 * result + (name == null ? 0 : name.hashCode());
        result = 31 * result + (age == null ? 0 : age.hashCode());
        return result;
    }
```

## 13. Object类下面有几种方法呢

 1.Object()

这个没什么可说的，Object类的构造方法。(非重点)

 2.registerNatives()

为了使JVM发现本机功能，他们被一定的方式命名。例如，对于java.lang.Object.registerNatives，对应的C函数命名为Java_java_lang_Object_registerNatives。

通过使用registerNatives（或者更确切地说，JNI函数RegisterNatives），可以命名任何你想要你的C函数。(非重点)

 3.clone()

clone()函数的用途是用来另存一个当前存在的对象。只有实现了Cloneable接口才可以调用该方法，否则抛出CloneNotSupportedException异常。（注意：回答这里时可能会引出设计模式的提问）

 4.getClass()

final方法，用于获得运行时的类型。该方法返回的是此Object对象的类对象/运行时类对象Class。效果与Object.class相同。（注意：回答这里时可能会引出类加载，反射等知识点的提问）

 5.equals()

equals用来比较两个对象的内容是否相等。默认情况下(继承自Object类)，equals和==是一样的，除非被覆写(override)了。（注意：这里可能引出更常问的“equals与==的区别”及hashmap实现原理的提问）

 6.hashCode()

该方法用来返回其所在对象的物理地址（哈希码值），常会和equals方法同时重写，确保相等的两个对象拥有相等的hashCode。（同样，可能引出hashmap实现原理的提问）

 7.toString()

toString()方法返回该对象的字符串表示，这个方法没什么可说的。

 8.wait()

导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法。（引出线程通信及“wait和sleep的区别”的提问）

 9.wait(long timeout)

导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者超过指定的时间量。（引出线程通信及“wait和sleep的区别”的提问）

 10.wait(long timeout, int nanos)

导致当前的线程等待，直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法，或者其他某个线程中断当前线程，或者已超过某个实际时间量。（引出线程通信及“wait和sleep的区别”的提问）

 11.notify()

唤醒在此对象监视器上等待的单个线程。（引出线程通信的提问）

 12. notifyAll()

唤醒在此对象监视器上等待的所有线程。（引出线程通信的提问）

 13.finalize()

当垃圾回收器确定不存在对该对象的更多引用时，由对象的垃圾回收器调用此方法。（非重点，但小心引出垃圾回收的提问）

## 14. 进程和线程

1. 进程是资源分配最小单位，线程是程序执行的最小单位；
2. 进程有自己独立的地址空间，每启动一个进程，系统都会为其分配地址空间，建立数据表来维护代码段、堆栈段和数据段，线程没有独立的地址空间，它使用相同的地址空间共享数据；
3. CPU切换一个线程比切换进程花费小；
4. 创建一个线程比进程开销小；
5. 线程占用的资源要⽐进程少很多。
6. 线程之间通信更方便，同一个进程下，线程共享全局变量，静态变量等数据，进程之间的通信需要以通信的方式（IPC）进行；（但多线程程序处理好同步与互斥是个难点）
7. 多进程程序更安全，生命力更强，一个进程死掉不会对另一个进程造成影响（源于有独立的地址空间），多线程程序更不易维护，一个线程死掉，整个进程就死掉了（因为共享地址空间）；
8. 进程对资源保护要求高，开销大，效率相对较低，线程资源保护要求不高，但开销小，效率高，可频繁切换；

## 15. 序列化与反序列化

Java序列化是指把Java对象转换为字节序列的过程，而Java反序列化是指把字节序列恢复为Java对象的过程：

- **序列化：**对象序列化的最主要的用处就是在传递和保存对象的时候，保证对象的完整性和可传递性。序列化是把对象转换成有序字节流，以便在网络上传输或者保存在本地文件中。核心作用是对象状态的保存与重建。
- **反序列化：**客户端从文件中或网络上获得序列化后的对象字节流，根据字节流中所保存的对象状态及描述信息，通过反序列化重建对象。

首先我们要把准备要序列化类，实现 Serializabel接口

```java
import java.io.Serializable;


public class Person implements Serializable { //本类可以序列化

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String toString() {
        return "姓名：" + this.name + "，年龄" + this.age;
    }
}
```

然后：我们将name和age序列化（也就是把这2个对象转为二进制，理解为“打碎”）

```java
package org.lxh.SerDemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class ObjectOutputStreamDemo { //序列化
    public static void main(String[] args) throws Exception {
        //序列化后生成指定文件路径
        File file = new File("D:" + File.separator + "person.ser");
        ObjectOutputStream oos = null;
        //装饰流（流）
        oos = new ObjectOutputStream(new FileOutputStream(file));

        //实例化类
        Person per = new Person("张三", 30);
        oos.writeObject(per); //把类对象序列化
        oos.close();
    }
}
```

## 16. List、Set、Map 之间的区别是什么？

**List(列表)**

List的元素以线性方式存储，可以存放重复对象，List主要有以下两个实现类：

**1.ArrayList:** 长度可变的数组，可以对元素进行随机的访问，向ArrayList中插入与删除元素的速度慢。JDK8中ArrayList扩容的实现是通过`grow()`方法里使用语句`newCapacity = oldCapacity + (oldCapacity >> 1)`（即1.5倍扩容）计算容量，然后调用`Arrays.copyof()`方法进行对原数组进行复制。

**LinkedList:** 采用链表数据结构，插入和删除速度快，但访问速度慢。

**Set(集合)**

Set中的对象不按特定(HashCode)的方式排序，并且没有重复对象，Set主要有以下两个实现类：

**1.HashSet：**HashSet按照哈希算法来存取集合中的对象，存取速度比较快。当HashSet中的元素个数超过数组大小*loadFactor（默认值为0.75）时，就会进行近似两倍扩容（`newCapacity = (oldCapacity << 1) + 1`）。

**2.TreeSet：**TreeSet实现了SortedSet接口，能够对集合中的对象进行排序。

**Map(映射)**

Map是一种把键对象和值对象映射的集合，它的每一个元素都包含一个键对象和值对象。Map主要有以下实现类：

**HashMap：**HashMap基于散列表实现，其插入和查询`<K,V>`的开销是固定的，可以通过构造器设置容量和负载因子来调整容器的性能。

**LinkedHashMap：**类似于HashMap，但是迭代遍历它时，取得`<K,V>`的顺序是其插入次序，或者是最近最少使用(LRU)的次序。

**TreeMap：**TreeMap基于红黑树实现。查看`<K,V>`时，它们会被排序。TreeMap是唯一的带有`subMap()`方法的Map，`subMap()`可以返回一个子树。

## 17. Comparable和Comparator

- Comparable可以认为是一个内比较器，实现了Comparable接口的类有一个特点，就是这些类是可以和自己比较的，至于具体和另一个实现了Comparable接口的类如何比较，则依赖compareTo方法的实现。

```java
public class Domain implements Comparable<Domain>
{
   private String str;

   public Domain(String str)
   {
       this.str = str;
   }

   public int compareTo(Domain domain)
   {
       if (this.str.compareTo(domain.str) > 0)
           return 1;
       else if (this.str.compareTo(domain.str) == 0)
           return 0;
       else 
           return -1;
   }

   public String getStr()
   {
       return str;
   }
}
```

- Comparator接口里面有一个compare方法，方法有两个参数T o1和T o2

```java
public class DomainComparator implements Comparator<Domain>
{
   public int compare(Domain domain1, Domain domain2)
   {
       if (domain1.getStr().compareTo(domain2.getStr()) > 0)
           return 1;
       else if (domain1.getStr().compareTo(domain2.getStr()) == 0)
           return 0;
       else 
           return -1;
   }
}
```

- 如果实现类没有实现Comparable接口，又想对两个类进行比较（或者实现类实现了Comparable接口，但是对compareTo方法内的比较算法不满意），那么可以实现Comparator接口，自定义一个比较器，写比较算法。实现Comparable接口的方式比实现Comparator接口的耦合性要强一些，如果要修改比较算法，要修改Comparable接口的实现类，而实现Comparator的类是在外部进行比较的，不需要对实现类有任何修改。

