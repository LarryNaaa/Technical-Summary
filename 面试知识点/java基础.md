# java基础

[TOC]

## 1. 深拷贝和浅拷贝

- 浅拷贝只复制对象，不复制对象的引用
- 深拷贝不仅复制对象还复制对象的引用
- 类实现Cloneable方法，重写clone方法抛出clonenotsupportedexception

![java基础_1](/Users/na/IdeaProjects/Technical summary/Image/java基础_1.png)

## 2. 什么是多态?

- **一个引用变量到底会指向哪个类的实例对象，该引用变量发出的方法调用到底是哪个类中实现的方法，必须由程序运行期间才能决定**，这就是多态性。多态性可以分为编译时多态和运行时多态。
- **编译时多态**：主要是指方法的重载，它是根据参数列表的不同来区分不同的函数
- **运行时多态**：在运行时谈不上多态，而运行时多态是动态的，它是通过动态绑定来实现，也就是我们说的多态性
- Java实现多态有三个必要条件：继承、重写（覆盖）、向上转型
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

## 6. String和字符串常量池

### 6.1 String

- **String是不可变的字符串**，它的底层是一个用final修饰的字符数组
- String s1 = “Cat”当我们用这种方式创建字符串对象的时候，首先会去字符串常量池中查找看有没有“Cat”字符串，如果有则返回它的地址给s1,如果没有则在常量池中创建“Cat”字符串，并将地址返回给s1.
- String s3 = new String(“Cat”)当我们用这种方式创建字符串对象的时候，首先会去字符串常量池中查找看有没有“Cat”字符串,如果没有则在常量池中创建“Cat”字符串，然后在堆内存中创建“Cat”字符串，并将堆内存中的地址返回给s3.
- StringBuilder是线程不安全的，它的执行效率比StriingBuffer要高；StringBuffer是线程安全的，它的执行效率比StringBuilder要低

### 6.2 字符串常量池

- String Pool 是一个固定大小的Hashtable，在jdk6中StringTable是固定的，默认值大小长度是1009，在jdk7中，StringTable的长度默认值是60013。
- 如果放进StringPool的String非常多， 就会造成Hash冲突严重，从而导致链表会很长，而链表长了后直接会造成的影响就是当调用String. intern时性能会大幅下降。

### 6.3 字符串拼接

- 如果拼接符号左右两边都是字符串常量或常量引用，拼接结果在常量池，原理是编译期优化

```java
    @Test
    public void test1(){
        String s1 = "a" + "b" + "c";//编译期优化：等同于"abc"
        String s2 = "abc"; //"abc"一定是放在字符串常量池中，将此地址赋给s2
        /*
         * 最终.java编译成.class,再执行.class
         * String s1 = "abc";
         * String s2 = "abc"
         */
        System.out.println(s1 == s2); //true
        System.out.println(s1.equals(s2)); //true
    }

    @Test
    public void test4(){
        final String s1 = "a";
        final String s2 = "b";
        String s3 = "ab";
        String s4 = s1 + s2;
        System.out.println(s3 == s4);//true
    }
```

- 如果拼接符号的前后出现了变量，则相当于在堆空间中new String()，结果就在堆中，变量拼接的原理是StringBuilder

```java
    @Test
    public void test3(){
        String s1 = "a";
        String s2 = "b";
        String s3 = "ab";
        /*
        如下的s1 + s2 的执行细节：(变量s是我临时定义的）
        ① StringBuilder s = new StringBuilder();
        ② s.append("a")
        ③ s.append("b")
        ④ s.toString()  --> 约等于 new String("ab")

        补充：在jdk5.0之后使用的是StringBuilder,
        在jdk5.0之前使用的是StringBuffer
         */
        String s4 = s1 + s2;//
        System.out.println(s3 == s4);//false
    }
```

- 拼接操作与append的效率对比：
  - StringBuilder的append()的方式：自始至终中只创建过一个StringBuilder的对象
  - 使用String的字符串拼接方式：创建过多个StringBuilder和String的对象，内存占用更大；如果进行GC，需要花费额外的时间。

### 6.4 intern()方法

- 如果不是用双引号声明的String对象，可以使用String提供的intern方法：判断字符串常量池中是否存在该字符串，如果存在，则返回常量池中该字符串的地址；否则，在常量池中添加该字符串，并返回次对象的地址。
- jdk1.6中，将这个字符串对象尝试放入字符串常量池。
  - ➢如果字符串常量池中有，则并不会放入。返回已有的串池中的对象的地址
  - ➢如果没有，会把此对象复制一份，放入字符串常量池，并返回字符串常量池中的对象地址
- Jdk1.7起，将这个字符串对象尝试放入字符串常量池。
  - ➢如果字符串常量池中有，则并不会放入。返回已有的串池中的对象的地址
  - ➢如果没有，则会把对象的引用地址复制一份，放入字符串常量池，并返回字符串常量池中的引用地址

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

## 11. Integer

- **int到Integer：**
- int a=3; Integer A=new Integer(a);
  - Integer A=Integer.valueOf(a);
- **Integer到int:**
  - Integer A=new Integer(5); int a=A.intValue();
- int 和Integer在进行比较的时候，Integer会进行拆箱，转为int值与int进行比较。
- Integer与Integer比较的时候，由于直接赋值的时候会进行自动的装箱，那么这里就需要注意两个问题，一个是-128<= x<=127的整数，将会直接缓存在IntegerCache中，那么当赋值在这个区间的时候，不会创建新的Integer对象，而是从缓存中获取已经创建好的Integer对象。二：当大于这个范围的时候，直接new Integer来创建Integer对象。
- new Integer(1) 和Integer a = 1不同，前者会创建对象，存储在堆中，而后者因为在-128到127的范围内，不会创建新的对象，而是从IntegerCache中获取的。那么Integer a = 128, 大于该范围的话才会直接通过new Integer（128）创建对象，进行装箱。

```java
public class Main{
    public static void main(String[] args){
        Integer i = new Integer(128);
        Integer i2 = 128;

        System.out.println(i == i2);//false

        Integer i3 = new Integer(127);
        Integer i4 = 127;
        System.out.println(i3 == i4);//false

        Integer i5 = 128;
        Integer i6 = 128;
        System.out.println(i5 == i6);//false

        Integer i7 = 127;
        Integer i8 = 127;
        System.out.println(i7 == i8);//true

        Integer i9 = 127;
        System.out.println(i9 == 127);//true

        Integer i10 = 127;
        int i11 = 127;
        System.out.println(i9 == i11);//true
    }
}

/**
     * Returns an {@code Integer} instance representing the specified
     * {@code int} value.  If a new {@code Integer} instance is not
     * required, this method should generally be used in preference to
     * the constructor {@link #Integer(int)}, as this method is likely
     * to yield significantly better space and time performance by
     * caching frequently requested values.
     *
     * This method will always cache values in the range -128 to 127,
     * inclusive, and may cache other values outside of this range.
     *
     * @param  i an {@code int} value.
     * @return an {@code Integer} instance representing {@code i}.
     * @since  1.5
     */
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
```

## 12. hashCode（）和equals（）方法

- 判断两个对象在逻辑上是否相等，如根据类的成员变量来判断两个类的实例是否相等，而继承Object中的equals方法只能判断两个引用变量是否是同一个对象。这样我们往往需要重写equals()方法。

- equals的设计原则：
  
  - 1、自反性：对于任何非空引用x，x.equals(x)应该返回true。
  - 2、对称性：对于任何引用x和y，如果x.equals(y)返回true，那么y.equals(x)也应该返回true。
  - 3、传递性：对于任何引用x、y和z，如果x.equals(y)返回true，y.equals(z)返回true，那么x.equals(z)也应该返回true。
  - 4、一致性：如果x和y引用的对象没有发生变化，那么反复调用x.equals(y)应该返回同样的结果。
  - 5、非空性：对于任意非空引用x，x.equals(null)应该返回false。
  
- 重写equals时总是要重写hashCode

  > **A.equals(B) 则必须使得 A.hashCode() == B.hashCode();**
  >
  > **如果 A.hashCode() ！= B.hashCode() 则 A.equals(B) 一定为false。**
  
- hashCode的设计原则：

  - 在一个Java应用程序的执行期间，如果一个对象提供给equals做比较的信息没有被修改的话，该对象多次调用hashCode（）方法，该方法必须始终如一返回同一个整数。
  - 如果两个对象根据equals（Object）方法是替代的，那么调用各自各自的hashCode（）方法必须产生同一个整数结果。
  - 并不要求根据equals（java.lang.Object）方法不相似的两个对象，调用两个各自的hashCode（）方法必须产生不同的integer结果。然而，程序员应该考虑到不同的对象产生不同的integer结果，有可能会提高哈希表的性能。

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

- 为什么是31，而不是32，33等其他数字呢？
  - 31是一个素数，素数作用就是如果我用一个数字来乘以这个素数，那么最终的出来的结果只能被素数本身和被乘数还有1来整除。
  - 31由可以`i*31 == (i<<5)-1`来表示，现在很多虚拟机里面都有做相关优化。
  - 因为如果计算出来的hash地址偏移，所谓的“冲突”就越少，查找起来效率也会提高。
  - 并且31只占用5bits，相乘造成数据重叠的概率过多。

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

![](https://www.runoob.com/wp-content/uploads/2014/01/2243690-9cd9c896e0d512ed.gif)

## 17. Comparable和Comparator

- Comparable接口被用来提供对象的自然排序，依赖compareTo方法的实现。

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

- Comparator接口被用来提供不同的排序算法，我们可以选择需要使用的Comparator来对给定的对象集合进行排序。Comparator接口里面有一个compare方法，方法有两个参数T o1和T o2

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

## 18. 泛型

- 2.泛型允许我们为集合提供一个可以容纳的对象类型，因此，如果你添加其它类型的任何元素，它会在编译时报错。

  3.这避免了在运行时出现ClassCastException，因为你将会在编译时得到报错信息。

  4.泛型也使得代码整洁，我们不需要使用显式转换和instanceOf操作符。

  5.它也给运行时带来好处，因为不会产生类型检查的字节码指令。

## 19. **ArrayList和Vector**

- ArrayList和Vector在很多时候都很类似。

（1）两者都是基于索引的，内部由一个数组支持。

（2）两者维护插入的顺序，我们可以根据插入顺序来获取元素。

（3）ArrayList和Vector的迭代器实现都是fail-fast的。

（4）ArrayList和Vector两者允许null值，也可以使用索引值对元素进行随机访问。

- 以下是ArrayList和Vector的不同点。

（1）Vector是同步的，而ArrayList不是。然而，如果你寻求在迭代的时候对列表进行改变，你应该使用CopyOnWriteArrayList。

（2）ArrayList比Vector快，它因为有同步，不会过载。

（3）ArrayList更加通用，因为我们可以使用Collections工具类轻易地获取同步列表和只读列表。

## 20. 判断一个整数是否是奇数

```java
public boolean isOdd(int i) {
    return (i & 1) == 1;
}
```

- 负数的二进制：负数我们用的补码，原码取反变反码，反码加1变成补码。比如1 是0000 0001，反码:1111 1110，补码:1111 1111，-1为1111 1111。

## 21. Object 0 = new Object()在内存中占多少个字节？

对象头占12个字节(markword占8个，class pointer占4个)，实例数据如果没有成员变量占0个字节(如果有另算，如int占4个字节，引用类型String占4个字节)，对其填充占4个(确保对象的整体字节数是8的倍数)，引用是压缩class pointer占4个字节(JVM自动开启，如果关闭则占8个字节)

## 22. 泛型

1. Java中的泛型是什么 ? 使用泛型的好处是什么?
   泛型是一种参数化类型的机制。它可以使得代码适用于各种类型，从而编写更加通用的代码，例如集合框架。

泛型是一种编译时类型确认机制。它提供了编译期的类型安全，确保在泛型类型（通常为泛型集合）上只能使用正确类型的对象，避免了在运行时出现ClassCastException。

2、Java的泛型是如何工作的 ? 什么是类型擦除 ?
泛型的正常工作是依赖编译器在编译源码的时候，先进行类型检查，然后进行类型擦除并且在类型参数出现的地方插入强制转换的相关指令实现的。

编译器在编译时擦除了所有类型相关的信息，所以在运行时不存在任何类型相关的信息。例如List<String>在运行时仅用一个List类型来表示。为什么要进行擦除呢？这是为了避免类型膨胀。

3. 什么是泛型中的限定通配符和非限定通配符 ?
   限定通配符对类型进行了限制。有两种限定通配符，一种是<? extends T>它通过确保类型必须是T的子类来设定类型的上界，另一种是<? super T>它通过确保类型必须是T的父类来设定类型的下界。泛型类型必须用限定内的类型来进行初始化，否则会导致编译错误。另一方面<?>表示了非限定通配符，因为<?>可以用任意类型来替代。
4. List<? extends T>和List <? super T>之间有什么区别 ?
   这和上一个面试题有联系，有时面试官会用这个问题来评估你对泛型的理解，而不是直接问你什么是限定通配符和非限定通配符。这两个List的声明都是限定通配符的例子，List<? extends T>可以接受任何继承自T的类型的List，而List<? super T>可以接受任何T的父类构成的List。例如List<? extends Number>可以接受List<Integer>或List<Float>。在本段出现的连接中可以找到更多信息。

```java
public class Test3 {
    public static void main(String[] args) {
        Box<Food> box = new Box<>();
        Box<? extends Fruit> box1 = new Box<Apple>();
        Box<? super Fruit> box2 = new Box<Food>();
    }
}

class Food{

}

class Fruit extends Food{

}

class Apple extends Fruit{

}

class Box<T>{

}
```



3. 如何编写一个泛型方法，让它能接受泛型参数并返回泛型类型?
   编写泛型方法并不困难，你需要用泛型类型来替代原始类型，比如使用T, E or K,V等被广泛认可的类型占位符。泛型方法的例子请参阅Java集合类框架。最简单的情况下，一个泛型方法可能会像这样:
     public V put(K key, V value) {  
   return cache.put(key, value);  
     }  
4. Java中如何使用泛型编写带有参数的类?
   这是上一道面试题的延伸。面试官可能会要求你用泛型编写一个类型安全的类，而不是编写一个泛型方法。关键仍然是使用泛型类型来代替原始类型，而且要使用JDK中采用的标准占位符。 
5. 编写一段泛型程序来实现LRU缓存?
   对于喜欢Java编程的人来说这相当于是一次练习。给你个提示，LinkedHashMap可以用来实现固定大小的LRU缓存，当LRU缓存已经满了的时候，它会把最老的键值对移出缓存。LinkedHashMap提供了一个称为removeEldestEntry()的方法，该方法会被put()和putAll()调用来删除最老的键值对。
6. 你可以把List<String>传递给一个接受List<Object>参数的方法吗？
   对任何一个不太熟悉泛型的人来说，这个Java泛型题目看起来令人疑惑，因为乍看起来String是一种Object，所以List<String>应当可以用在需要List<Object>的地方，但是事实并非如此。真这样做的话会导致编译错误。如果你再深一步考虑，你会发现Java这样做是有意义的，因为List<Object>可以存储任何类型的对象包括String, Integer等等，而List<String>却只能用来存储Strings。
     List<Object> objectList;  
     List<String> stringList;   
     objectList = stringList;  //compilation error incompatible types  
7. Array中可以用泛型吗?
   这可能是Java泛型面试题中最简单的一个了，当然前提是你要知道Array事实上并不支持泛型，这也是为什么Joshua Bloch在Effective Java一书中建议使用List来代替Array，因为List可以提供编译期的类型安全保证，而Array却不能。
8. 如何阻止Java中的类型未检查的警告?
   如果你把泛型和原始类型混合起来使用，例如下列代码，Java 5的javac编译器会产生类型未检查的警告，例如List<String> rawList = new ArrayList()
   注意: Hello.java使用了未检查或称为不安全的操作;
   这种警告可以使用@SuppressWarnings("unchecked")注解来屏蔽。

11、Java中List<Object>和原始类型List之间的区别?
原始类型和带参数类型<Object>之间的主要区别是，在编译时编译器不会对原始类型进行类型安全检查，却会对带参数的类型进行检查，通过使用Object作为类型，可以告知编译器该方法可以接受任何类型的对象，比如String或Integer。这道题的考察点在于对泛型中原始类型的正确理解。它们之间的第二点区别是，你可以把任何带参数的泛型类型传递给接受原始类型List的方法，但却不能把List<String>传递给接受List<Object>的方法，因为会产生编译错误。

12、Java中List<?>和List<Object>之间的区别是什么?
这道题跟上一道题看起来很像，实质上却完全不同。List<?> 是一个未知类型的List，而List<Object>其实是任意类型的List。你可以把List<String>, List<Integer>赋值给List<?>，却不能把List<String>赋值给List<Object>。   
List<?> listOfAnyType;  
List<Object> listOfObject = new ArrayList<Object>();  
List<String> listOfString = new ArrayList<String>();  
List<Integer> listOfInteger = new ArrayList<Integer>();  
        
listOfAnyType = listOfString; //legal  
listOfAnyType = listOfInteger; //legal  
listOfObjectType = (List<Object>) listOfString; //compiler error - in-convertible types  

13、List<String>和原始类型List之间的区别.
该题类似于“原始类型和带参数类型之间有什么区别”。带参数类型是类型安全的，而且其类型安全是由编译器保证的，但原始类型List却不是类型安全的。你不能把String之外的任何其它类型的Object存入String类型的List中，而你可以把任何类型的对象存入原始List中。使用泛型的带参数类型你不需要进行类型转换，但是对于原始类型，你则需要进行显式的类型转换。
List listOfRawTypes = new ArrayList();  
listOfRawTypes.add("abc");  
listOfRawTypes.add(123); //编译器允许这样 - 运行时却会出现异常  
String item = (String) listOfRawTypes.get(0); //需要显式的类型转换  
item = (String) listOfRawTypes.get(1); //抛ClassCastException，因为Integer不能被转换为String  
        
List<String> listOfString = new ArrayList();  
listOfString.add("abcd");  
listOfString.add(1234); //编译错误，比在运行时抛异常要好  
item = listOfString.get(0); //不需要显式的类型转换 - 编译器自动转换  

## 23. 解决哈希冲突的方法

- 开放定址法：从发生冲突的那个单元起，按照一定的次序，从哈希表中找到一个空闲的单元。然后把发生冲突的元素存入到该单元的一种方法。开放定址法需要的表长度要大于等于所需要存放的元素。
   在开放定址法中解决冲突的方法有：线行探查法、平方探查法、双散列函数探查法。
   开放定址法的缺点在于删除元素的时候不能真的删除，否则会引起查找错误，只能做一个特殊标记。只到有下个元素插入才能真正删除该元素。
- 链地址法：链接地址法的思路是将哈希值相同的元素构成一个同义词的单链表，并将单链表的头指针存放在哈希表的第i个单元中，查找、插入和删除主要在同义词链表中进行。链表法适用于经常进行插入和删除的情况。
- 再哈希法：就是同时构造多个不同的哈希函数：
   Hi = RHi(key)   i= 1,2,3 ... k;
   当H1 = RH1(key)  发生冲突时，再用H2 = RH2(key) 进行计算，直到冲突不再产生，这种方法不易产生聚集，但是增加了计算时间。

## 24. 基本数据类型

**byte：**

- byte 数据类型是8位、有符号的，以二进制补码表示的整数；
- 最小值是 **-128（-2^7）**；
- 最大值是 **127（2^7-1）**；
- 默认值是 **0**；
- byte 类型用在大型数组中节约空间，主要代替整数，因为 byte 变量占用的空间只有 int 类型的四分之一；
- 例子：byte a = 100，byte b = -50。

**short：**

- short 数据类型是 16 位、有符号的以二进制补码表示的整数
- 最小值是 **-32768（-2^15）**；
- 最大值是 **32767（2^15 - 1）**；
- Short 数据类型也可以像 byte 那样节省空间。一个short变量是int型变量所占空间的二分之一；
- 默认值是 **0**；
- 例子：short s = 1000，short r = -20000。

**int：**

- int 数据类型是32位、有符号的以二进制补码表示的整数；
- 最小值是 **-2,147,483,648（-2^31）**；
- 最大值是 **2,147,483,647（2^31 - 1）**；
- 一般地整型变量默认为 int 类型；
- 默认值是 **0** ；
- 例子：int a = 100000, int b = -200000。

**long：**

- long 数据类型是 64 位、有符号的以二进制补码表示的整数；
- 最小值是 **-9,223,372,036,854,775,808（-2^63）**；
- 最大值是 **9,223,372,036,854,775,807（2^63 -1）**；
- 这种类型主要使用在需要比较大整数的系统上；
- 默认值是 **0L**；
- 例子： long a = 100000L，Long b = -200000L。
  "L"理论上不分大小写，但是若写成"l"容易与数字"1"混淆，不容易分辩。所以最好大写。

**float：**

- float 数据类型是单精度、32位、符合IEEE 754标准的浮点数；
- float 在储存大型浮点数组的时候可节省内存空间；
- 默认值是 **0.0f**；
- 浮点数不能用来表示精确的值，如货币；
- 例子：float f1 = 234.5f。

**double：**

- double 数据类型是双精度、64 位、符合IEEE 754标准的浮点数；
- 浮点数的默认类型为double类型；
- double类型同样不能表示精确的值，如货币；
- 默认值是 **0.0d**；
- 例子：double d1 = 123.4。
- Float 为单精度，内存中占 4 个字节，有效数位是 7 位（因为有正负，所以不是8位）；double为 双精度，占 8 个字节，有效数位是 16 位。

**boolean：**

- boolean数据类型表示一位的信息；
- 只有两个取值：true 和 false；
- 这种类型只作为一种标志来记录 true/false 情况；
- 默认值是 **false**；
- 例子：boolean one = true。

**char：**

- char类型是一个单一的 16 位 Unicode 字符；
- 最小值是 **\u0000**（即为 0）；
- 最大值是 **\uffff**（即为 65535）；
- char 数据类型可以储存任何字符；
- 例子：char letter = 'A';。

