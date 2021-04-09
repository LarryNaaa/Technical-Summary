# JUC多线程及高并发

[TOC]

### 一、请你谈谈对volatile的理解

 `Package java.util.concurrent`---> `AtomicInteger` `Lock` `ReadWriteLock`

#### 1、volatile是java虚拟机提供的轻量级的同步机制

保证可见性、不保证原子性、禁止指令重排

1. 保证可见性

   - 多个线程访问同一个被volatile修饰的变量时，一个线程修改了这个变量的值，其他线程能够立即被通知主内存的值被修改。
   - Java的内存模型实现总是从**主存**（即共享内存）读取变量，是不需要进行特别的注意的。而在当前的 Java 内存模型下，线程可以把变量保存**本地内存**（比如机器的寄存器）中，而不是直接在主存中进行读写。这就可能造成一个线程在主存中修改了一个变量的值，而另外一个线程还继续使用它在寄存器中的变量值的拷贝，造成**数据的不一致**。
   - 线程本身并不直接与主内存进行数据的交互，而是通过线程的工作内存来完成相应的操作。这也是导致线程间数据不可见的本质原因。因此要实现volatile变量的可见性，直接从这方面入手即可。对volatile变量的写操作与普通变量的主要区别有两点：
     - 修改volatile变量时会强制将修改后的值刷新的主内存中。
     - 修改volatile变量后会导致其他线程工作内存中对应的变量值失效。因此，再读取该变量值的时候就需要重新从读取主内存中的值。

   当不添加volatile关键字时示例：

   ```java
   package com.jian8.juc;
   
   import java.util.concurrent.TimeUnit;
   
   /**
    * 1验证volatile的可见性
    * 1.1 如果int num = 0，number变量没有添加volatile关键字修饰
    * 1.2 添加了volatile，可以解决可见性
    */
   public class VolatileDemo {
   
       public static void main(String[] args) {
           visibilityByVolatile();//验证volatile的可见性
       }
   
       /**
        * volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
        */
       public static void visibilityByVolatile() {
           MyData myData = new MyData();
   
           //第一个线程
           new Thread(() -> {
               System.out.println(Thread.currentThread().getName() + "\t come in");
               try {
                   //线程暂停3s
                   TimeUnit.SECONDS.sleep(3);
                   myData.addToSixty();
                   System.out.println(Thread.currentThread().getName() + "\t update value:" + myData.num);
               } catch (Exception e) {
                   // TODO Auto-generated catch block
                   e.printStackTrace();
               }
           }, "thread1").start();
           //第二个线程是main线程
           while (myData.num == 0) {
               //如果myData的num一直为零，main线程一直在这里循环
           }
           System.out.println(Thread.currentThread().getName() + "\t mission is over, num value is " + myData.num);
       }
   }
   
   class MyData {
       //    int num = 0;
       volatile int num = 0;
   
       public void addToSixty() {
           this.num = 60;
       }
   }
   ```

   输出结果：

   ```
   thread1	 come in
   thread1	 update value:60
   //线程进入死循环
   ```

   当我们加上`volatile`关键字后，`volatile int num = 0;`输出结果为：

   ```
   thread1	 come in
   thread1	 update value:60
   main	 mission is over, num value is 60
   //程序没有死循环，结束执行
   ```

2. 不保证原子性

   原子性：不可分割、完整性，即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整，要么同时成功，要么同时失败

   保证原子性的方法：1. synchronized 2. Atomic

   i++: 读取i，i加一，值写回。这个过程中有些线程被挂起，导致没有接收到volatile值更新的通知，没有获得最新值，导致错误

   验证示例（变量添加volatile关键字，方法不添加synchronized）：

   ```java
   package com.jian8.juc;
   
   import java.util.concurrent.TimeUnit;
   import java.util.concurrent.atomic.AtomicInteger;
   
   /**
    * 1验证volatile的可见性
    *  1.1 如果int num = 0，number变量没有添加volatile关键字修饰
    * 1.2 添加了volatile，可以解决可见性
    *
    * 2.验证volatile不保证原子性
    *  2.1 原子性指的是什么
    *      不可分割、完整性，即某个线程正在做某个具体业务时，中间不可以被加塞或者被分割，需要整体完整，要么同时成功，要么同时失败
    */
   public class VolatileDemo {
   
       public static void main(String[] args) {
   //        visibilityByVolatile();//验证volatile的可见性
           atomicByVolatile();//验证volatile不保证原子性
       }
       
       /**
        * volatile可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
        */
   	//public static void visibilityByVolatile(){}
       
       /**
        * volatile不保证原子性
        * 以及使用Atomic保证原子性
        */
       public static void atomicByVolatile(){
           MyData myData = new MyData();
           for(int i = 1; i <= 20; i++){
               new Thread(() ->{
                   for(int j = 1; j <= 1000; j++){
                       myData.addSelf();
                       myData.atomicAddSelf();
                   }
               },"Thread "+i).start();
           }
           //等待上面的线程都计算完成后，再用main线程取得最终结果值
           try {
               TimeUnit.SECONDS.sleep(4);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           while (Thread.activeCount()>2){
               Thread.yield();
           }
           System.out.println(Thread.currentThread().getName()+"\t finally num value is "+myData.num);
           System.out.println(Thread.currentThread().getName()+"\t finally atomicnum value is "+myData.atomicInteger);
       }
   }
   
   class MyData {
       //    int num = 0;
       volatile int num = 0;
   
       public void addToSixty() {
           this.num = 60;
       }
   
       public void addSelf(){
           num++;
       }
       
       AtomicInteger atomicInteger = new AtomicInteger();
       public void atomicAddSelf(){
           atomicInteger.getAndIncrement();
       }
   }
   ```

   执行三次结果为：

   ```
   //1.
   main	 finally num value is 19580	
   main	 finally atomicnum value is 20000
   //2.
   main	 finally num value is 19999
   main	 finally atomicnum value is 20000
   //3.
   main	 finally num value is 18375
   main	 finally atomicnum value is 20000
   //num并没有达到20000
   ```

3. 保证有序性：禁止指令重排

   有序性：在计算机执行程序时，为了提高性能，编译器和处理器常常会对**指令重排(没有数据依赖)**，一般分以下三种

   ![Thread_14](/Users/na/IdeaProjects/Technical summary/Image/Thread_14.png)
   
   单线程环境里面确保程序最终执行结果和代码顺序执行的结果一致。
   
   处理器在进行重排顺序是必须要考虑指令之间的**数据依赖性**
   
   多线程环境中线程交替执行，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性时无法确定的，结果无法预测
   
   重排代码实例：
   

声明变量：`int a,b,x,y=0`

| 线程1  | 线程2     |
| ------ | --------- |
| x = a; | y = b;    |
| b = 1; | a = 2;    |
| 结 果  | x = 0 y=0 |

如果编译器对这段程序代码执行重排优化后，可能出现如下情况：

| 线程1  | 线程2     |
| ------ | --------- |
| b = 1; | a = 2;    |
| x= a;  | y = b;    |
| 结 果  | x = 2 y=1 |

这个结果说明在多线程环境下，由于编译器优化重排的存在，两个线程中使用的变量能否保证一致性是无法确定的

volatile实现禁止指令重排，从而避免了多线程环境下程序出现乱序执行的现象

- happen-before规则：
  - 同一个线程中的，前面的操作 happen-before 后续的操作。（即单线程内按代码顺序执行。但是，在不影响在单线程环境执行结果的前提下，编译器和处理器可以进行重排序，这是合法的。换句话说，这一是规则无法保证编译重排和指令重排）。
  - 监视器上的解锁操作 happen-before 其后续的加锁操作。（Synchronized 规则）
  - 对volatile变量的写操作 happen-before 后续的读操作。（volatile 规则）
  - 线程的start() 方法 happen-before 该线程所有的后续操作。（线程启动规则）
  - 线程所有的操作 happen-before 其他线程在该线程上调用 join 返回成功后的操作。
  - 如果 a happen-before b，b happen-before c，则a happen-before c（传递性）。

-  内存屏障（Memory Barrier）又称内存栅栏，是一个CPU指令，他的作用有两个：

   1. 保证特定操作的执行顺序
   2. 保证某些变量的内存可见性（利用该特性实现volatile的内存可见性）

- StoreStore屏障，保证上面的普通写不和volatile写发生重排序
- StoreLoad屏障，保证volatile写与后面可能的volatile读写不发生重排序
- LoadLoad屏障，禁止volatile读与后面的普通读重排序
- LoadStore屏障，禁止volatile读和后面的普通写重排序

![Thread_15](/Users/na/IdeaProjects/Technical summary/Image/Thread_15.png)

#### 2、JMM（java内存模型）

JMM（Java Memory Model）本身是一种抽象的概念，并不真实存在，他描述的时一组规则或规范，通过这组规范定义了程序中各个变量（包括实例字段，静态字段和构成数组对象的元素）的访问方式。

**JMM关于同步的规定：**

1. 线程解锁前，必须把共享变量的值刷新回主内存
2. 线程加锁前，必须读取主内存的最新值到自己的工作内存
3. 加锁解锁时同一把锁

由于JVM运行程序的实体是线程，而每个线程创建时JVM都会为其创建一个工作内存（有的成为栈空间），工作内存是每个线程的私有数据区域，而java内存模型中规定所有变量都存储在**==主内存==**，主内存是贡献内存区域，所有线程都可以访问，**==但线程对变量的操作（读取赋值等）必须在工作内存中进行，首先概要将变量从主内存拷贝到自己的工作内存空间，然后对变量进行操作，操作完成后再将变量写回主内存，==**不能直接操作主内存中的变量，各个线程中的工作内存中存储着主内存的**==变量副本拷贝==**，因此不同的线程件无法访问对方的工作内存，线程间的通信（传值）必须通过主内存来完成，期间要访问过程如下图：

1. 可见性
2. 原子性
3. 有序性

#### 3、你在那些地方用过volatile

当普通单例模式在多线程情况下：

```java
public class SingletonDemo {
    private static SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 构造方法SingletonDemo（）");
    }

    public static SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }

    public static void main(String[] args) {
        //构造方法只会被执行一次
//        System.out.println(getInstance() == getInstance());
//        System.out.println(getInstance() == getInstance());
//        System.out.println(getInstance() == getInstance());

        //并发多线程后，构造方法会在一些情况下执行多次
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                SingletonDemo.getInstance();
            }, "Thread " + i).start();
        }
    }
}
```

其构造方法在一些情况下会被执行多次

解决方式：

1. **单例模式DCL代码**

   DCL （Double Check Lock双端检锁机制）在加锁前和加锁后都进行一次判断

   ```java
       public static SingletonDemo getInstance() {
           if (instance == null) {
               synchronized (SingletonDemo.class) {
                   if (instance == null) {
                       instance = new SingletonDemo();
                   }
               }
           }
           return instance;
       }
   ```

   **大部分运行结果构造方法只会被执行一次**，但指令重排机制会让程序很小的几率出现构造方法被执行多次

   **==DCL（双端检锁）机制不一定线程安全==**，原因时有指令重排的存在，加入volatile可以禁止指令重排

   原因是在某一个线程执行到第一次检测，读取到instance不为null时，instance的引用对象可能==没有完成初始化==。instance=new SingleDemo();可以被分为一下三步（伪代码）：

   ```
   memory = allocate();//1.分配对象内存空间
   instance(memory);	//2.初始化对象
   instance = memory;	//3.设置instance执行刚分配的内存地址，此时instance!=null
   ```

   步骤2和步骤3不存在数据依赖关系，而且无论重排前还是重排后程序的执行结果在单线程中并没有改变，因此这种重排优化时允许的，**如果3步骤提前于步骤2，但是instance还没有初始化完成**

   但是指令重排只会保证串行语义的执行的一致性（单线程），但并不关心多线程间的语义一致性。

   ==所以当一条线程访问instance不为null时，由于instance示例未必已初始化完成，也就造成了线程安全问题。==

2. **单例模式volatile代码**

   为解决以上问题，可以将SingletongDemo实例上加上volatile

   ```
   private static volatile SingletonDemo instance = null;
   ```

### 二、CAS你知道吗

![Thread_19](/Users/na/IdeaProjects/Technical summary/Image/Thread_19.png)

#### 1、compareAndSet----比较并交换

AtomicInteger.conpareAndSet(int expect, indt update)

```java
    public final boolean compareAndSet(int expect, int update) {
        return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
    }
```

第一个参数为拿到的期望值，如果期望值没有一致，进行update赋值，如果期望值不一致，证明数据被修改过，返回fasle，取消赋值

例子：

```java
package com.jian8.juc.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.CAS是什么？
 * 1.1比较并交换
 */
public class CASDemo {
    public static void main(String[] args) {
       checkCAS();
    }

    public static void checkCAS(){
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data is " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2014) + "\t current data is " + atomicInteger.get());
    }
}
```

输出结果为：

```
true	 current data is 2019
false	 current data is 2019
```

#### 2、CAS底层原理？对Unsafe的理解

比较当前工作内存中的值和主内存中的值，如果相同则执行规定操作，否则继续比较直到主内存和工作内存中的值一直为止

1. atomicInteger.getAndIncrement();

   ```java
       public final int getAndIncrement() {
           return unsafe.getAndAddInt(this, valueOffset, 1);
       }
   ```

2. Unsafe

   - 是CAS核心类，由于Java方法无法直接访问地层系统，需要通过本地（native）方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存数据。Unsafe类存在于`sun.misc`包中，其内部方法操作可以像C的指针一样直接操作内存，因为Java中CAS操作的执行依赖于Unsafe类的方法。

     **Unsafe类中的所有方法都是native修饰的，也就是说Unsafe类中的方法都直接调用操作系统底层资源执行相应任务**

   - 变量valueOffset，表示该变量值在内存中的偏移地址，因为Unsafe就是根据内存便宜地址获取数据的

   - 变量value用volatile修饰，保证多线程之间的可见性

3. CAS是什么

   CAS全称呼Compare-And-Swap，它是一条CPU并发原语

   他的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，这个过程是原子的。

   CAS并发原语体现在JAVA语言中就是sun.misc.Unsafe类中各个方法。调用Unsafe类中的CAS方法，JVM会帮我们实现CAS汇编指令。这是一种完全依赖于硬件的功能，通过他实现了原子操作。由于CAS是一种系统原语，原语属于操作系统用语范畴，是由若干条指令组成的，用于完成某个功能的一个过程，并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说**CAS是一条CPU的原子指令，不会造成数据不一致问题**。

   ![Thread_16](/Users/na/IdeaProjects/Technical summary/Image/Thread_16.png)
   
   var1 AtomicInteger对象本身
   
   var2 该对象的引用地址
   
   var4 需要变动的数据
   
   var5 通过var1 var2找出的主内存中真实的值
   

用该对象前的值与var5比较；

如果相同，更新var5+var4并且返回true，

如果不同，继续去之然后再比较，直到更新完成

![Thread_17](/Users/na/IdeaProjects/Technical summary/Image/Thread_17.png)

![Thread_18](/Users/na/IdeaProjects/Technical summary/Image/Thread_18.png)

#### 3、CAS缺点

1. **循环时间长，CPU开销大**

   例如getAndAddInt方法执行，有个do while循环，如果CAS失败，一直会进行尝试，如果CAS长时间不成功，可能会给CPU带来很大的开销

2. **只能保证一个共享变量的原子操作**

   对多个共享变量操作时，循环CAS就无法保证操作的原子性，这个时候就可以用锁来保证原子性

3. **ABA问题**

#### 4、CAS和synchronized比较

- synchronized保证了一致性，但并发性较弱；CAS不加锁，即保证了一致性又保证了并发性
- CAS只能保证一个共享变量的原子操作，synchronized能保证多个共享变量的原子操作

### 三、原子类AtomicInteger的ABA问题？时间戳的原子引用

#### 1、ABA如何产生

CAS算法实现一个重要前提需要去除内存中某个时刻的数据并在当下时刻比较并替换，那么在这个时间差类会导致数据的变化。

比如**线程1**从内存位置V取出A，**线程2**同时也从内存取出A，并且线程2进行一些操作将值改为B，然后线程2又将V位置数据改成A，这时候线程1进行CAS操作发现内存中的值依然时A，然后线程1操作成功。

==尽管线程1的CAS操作成功，但是不代表这个过程没有问题==

#### 2、如何解决？原子引用

示例代码：

```java
package juc.cas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicRefrenceDemo {
    public static void main(String[] args) {
        User z3 = new User("张三", 22);
        User l4 = new User("李四", 23);
        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3, l4) + "\t" + atomicReference.get().toString());
    }
}

@Getter
@ToString
@AllArgsConstructor
class User {
    String userName;
    int age;
}
```

输出结果

```
true	User(userName=李四, age=23)
false	User(userName=李四, age=23)
```

#### 3、时间戳的原子引用

新增机制，修改版本号

```java
package com.jian8.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题解决
 * AtomicStampedReference
 */
public class ABADemo {
    static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        System.out.println("=====以下时ABA问题的产生=====");
        new Thread(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        }, "Thread 1").start();

        new Thread(() -> {
            try {
                //保证线程1完成一次ABA操作
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100, 2019) + "\t" + atomicReference.get());
        }, "Thread 2").start();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=====以下时ABA问题的解决=====");

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号" + stamp);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第2次版本号" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t第3次版本号" + atomicStampedReference.getStamp());
        }, "Thread 3").start();

        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t第1次版本号" + stamp);
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean result = atomicStampedReference.compareAndSet(100, 2019, stamp, stamp + 1);

            System.out.println(Thread.currentThread().getName() + "\t修改是否成功" + result + "\t当前最新实际版本号：" + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName() + "\t当前最新实际值：" + atomicStampedReference.getReference());
        }, "Thread 4").start();
    }
}
```

输出结果：

```
=====以下时ABA问题的产生=====
true	2019
=====以下时ABA问题的解决=====
Thread 3	第1次版本号1
Thread 4	第1次版本号1
Thread 3	第2次版本号2
Thread 3	第3次版本号3
Thread 4	修改是否成功false	当前最新实际版本号：3
Thread 4	当前最新实际值：100
```

### 四、我们知道ArrayList是线程不安全的，请编写一个不安全的案例并给出解决方案

#### 1、线程不安全

```java
package com.jian8.juc.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 集合类不安全问题
 * ArrayList
 */
public class ContainerNotSafeDemo {
    public static void main(String[] args) {
        notSafe();
    }

    /**
     * 故障现象
     * java.util.ConcurrentModificationException
     */
    public static void notSafe() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, "Thread " + i).start();
        }
    }
}
```

报错：

```
Exception in thread "Thread 10" java.util.ConcurrentModificationException
```

#### 2、导致原因

并发争抢修改导致

一个人正在写入，另一个同学来抢夺，导致数据不一致，并发修改异常

#### 3、解决方法：CopyOnWriteArrayList

```java
List:
List<String> list = new Vector<>();//Vector线程安全
List<String> list = Collections.synchronizedList(new ArrayList<>());//使用辅助类
List<String> list = new CopyOnWriteArrayList<>();//写时复制，读写分离

Set:
Set<String> set = Collections.synchronizedSet(new HashSet<>());//使用辅助类
Set<String> set = new CopyOnWriteArraySet<>();//写时复制，读写分离

Map:
HashTable
Map<String, String> map = new ConcurrentHashMap<>();
Map<String, String> map = Collections.synchronizedMap(new HashMap<>());// synchronized对mutex对象上锁
```

CopyOnWriteArrayList.add方法：

CopyOnWrite容器即写时复制，往一个元素添加容器的时候，不直接往当前容器Object[]添加，而是先将当前容器Object[]进行copy，复制出一个新的容器Object[] newElements，让后新的容器添加元素，添加完元素之后，再将原容器的引用指向新的容器setArray(newElements),这样做可以对CopyOnWrite容器进行并发的读，而不需要加锁，因为当前容器不会添加任何元素，所以CopyOnWrite容器也是一种读写分离的思想，读和写不同的容器

```java
	public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
```

### 4、HashSet的底层

- HashSet底层是一个HashMap，存储的值放在HashMap的key里，value存储了一个PRESENT的静态Object对象

```java
// hashmap的add方法：将值放入hashmap的key中，将object常量放入value中
public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }

// present是个object常量
private static final Object PRESENT = new Object();
```

### 五、公平锁、非公平锁、可重入锁、递归锁、自旋锁？

#### 1、公平锁、非公平锁

1. **是什么**

   公平锁就是先来后到、非公平锁就是允许加塞，`Lock lock = new ReentrantLock(Boolean fair);` 默认非公平。

   - **==公平锁==**是指多个线程按照申请锁的顺序来获取锁，类似排队打饭。
   - **==非公平锁==**是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程优先获取锁，在高并发的情况下，有可能会造成优先级反转或者饥饿现象。

2. **两者区别**

   - **公平锁**：公平锁，就是很公平，在并发环境中，每个线程在获取锁时，会先查看此锁维护的等待队列，如果为空，或者当前线程就是等待队列的第一个，就占有锁，否则就会加入到等待队列中，以后会按照FIFO的规则从队列中取到自己。

   - **非公平锁**：非公平锁比较粗鲁，上来就直接尝试占有额，如果尝试失败，就再采用类似公平锁那种方式。

3. **other**

   - 对Java ReentrantLock而言，通过构造函数指定该锁是否公平，默认是非公平锁，非公平锁的优点在于吞吐量比公平锁大

   - 对Synchronized而言，是一种非公平锁
   
4. 实现方式：

- 公平锁的实现机理在于每次有线程来抢占锁的时候，都会检查一遍有没有等待队列，当前同步队列没有前驱节点（也就是没有线程在等待）时才会去`compareAndSetState(0, acquires)`使用CAS修改同步状态变量。
- 非公平锁的实现在刚进入lock方法时会直接使用一次CAS去尝试获取锁，不成功才会到acquire方法中。而在nonfairTryAcquire方法中并没有判断是否有前驱节点在等待，如果发现锁这个时候被释放了（state == 0），非公平锁会直接 CAS 抢锁。如果这两次 CAS 都不成功，那么后面非公平锁和公平锁是一样的，都要进入到阻塞队列等待唤醒。

#### 2、可重入锁（递归锁）

1. **递归锁是什么**

   指的时同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，在同一个线程在外层方法获取锁的时候，在进入内层方法会自动获取锁，也就是说，==线程可以进入任何一个它已经拥有的锁所同步着的代码块==

2. **ReentrantLock/Synchronized 就是一个典型的可重入锁**

3. synchronized如何实现可重入？每个锁关联一个线程持有者和一个计数器。当计数器为0时表示该锁没有被任何线程持有，那么任何线程都都可能获得该锁而调用相应方法。当一个线程请求成功后，JVM会记下持有锁的线程，并将计数器计为1。此时其他线程请求该锁，则必须等待。而该持有锁的线程如果再次请求这个锁，就可以再次拿到这个锁，同时计数器会递增。当线程退出一个synchronized方法/块时，计数器会递减，如果计数器为0则释放该锁。

4. **可重入锁最大的作用是避免死锁**

5. **代码示例**

   ```java
   package com.jian8.juc.lock;
   
   ####
       public static void main(String[] args) {
           Phone phone = new Phone();
           new Thread(() -> {
               try {
                   phone.sendSMS();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }, "Thread 1").start();
           new Thread(() -> {
               try {
                   phone.sendSMS();
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }, "Thread 2").start();
       }
   }
   class Phone{
       public synchronized void sendSMS()throws Exception{
           System.out.println(Thread.currentThread().getName()+"\t -----invoked sendSMS()");
           Thread.sleep(3000);
           sendEmail();
       }
   
       public synchronized void sendEmail() throws Exception{
           System.out.println(Thread.currentThread().getName()+"\t +++++invoked sendEmail()");
       }
   }
   ```

   ```java
   package com.jian8.juc.lock;
   
   import java.util.concurrent.locks.Lock;
   import java.util.concurrent.locks.ReentrantLock;
   
   public class ReentrantLockDemo {
       public static void main(String[] args) {
           Mobile mobile = new Mobile();
           new Thread(mobile).start();
           new Thread(mobile).start();
       }
   }
   class Mobile implements Runnable{
       Lock lock = new ReentrantLock();
       @Override
       public void run() {
           get();
       }
   
       public void get() {
           lock.lock();
           try {
               System.out.println(Thread.currentThread().getName()+"\t invoked get()");
               set();
           }finally {
               lock.unlock();
           }
       }
       public void set(){
           lock.lock();
           try{
               System.out.println(Thread.currentThread().getName()+"\t invoked set()");
           }finally {
               lock.unlock();
           }
       }
   }
   ```

#### 3、 独占锁和共享锁

- **独占锁**：指该锁一次只能被一个线程所持有，对ReentrantLock和Synchronized而言都是独占锁
- **共享锁**：只该锁可被多个线程所持有

#### 4、互斥锁

- 互斥锁是一种「独占锁」，**互斥锁**加锁失败后，线程会**释放 CPU** ，给其他线程；
- 比如当线程 A 加锁成功后，此时互斥锁已经被线程 A 独占了，只要线程 A 没有释放手中的锁，线程 B 加锁就会失败，于是就会释放 CPU 让给其他线程，**既然线程 B 释放掉了 CPU，自然线程 B 加锁的代码就会被阻塞**。
- **对于互斥锁加锁失败而阻塞的现象，是由操作系统内核实现的**。当加锁失败时，内核会将线程置为「睡眠」状态，等到锁被释放后，内核会在合适的时机唤醒线程，当这个线程成功获取到锁后，于是就可以继续执行。
- 互斥锁加锁失败时，会从用户态陷入到内核态，让内核帮我们切换线程，虽然简化了使用锁的难度，但是存在一定的性能开销成本。会有**两次线程上下文切换的成本**：
  - 当线程加锁失败时，内核会把线程的状态从「运行」状态设置为「睡眠」状态，然后把 CPU 切换给其他线程运行；
  - 接着，当锁被释放时，之前「睡眠」状态的线程会变为「就绪」状态，然后内核会在合适的时间，把 CPU 切换给该线程运行。
- 线程的上下文切换的是什么？当两个线程是属于同一个进程，**因为虚拟内存是共享的，所以在切换时，虚拟内存这些资源就保持不动，只需要切换线程的私有数据、寄存器等不共享的数据。**
- **如果你能确定被锁住的代码执行时间很短，就不应该用互斥锁，而应该选用自旋锁，否则使用互斥锁。**

![Thread_21](/Users/na/IdeaProjects/Technical summary/Image/Thread_21.png)

#### 5、自旋锁：非公平锁

- 自旋锁是通过 CPU 提供的 `CAS` 函数（*Compare And Swap*），在「用户态」完成加锁和解锁操作，不会主动产生线程上下文切换，所以相比互斥锁来说，会快一些，开销也小一些。

- 一般加锁的过程，包含两个步骤：

  - 第一步，查看锁的状态，如果锁是空闲的，则执行第二步；
  - 第二步，将锁设置为当前线程持有；

- CAS 函数就把这两个步骤合并成一条硬件级指令，形成**原子指令**，这样就保证了这两个步骤是不可分割的，要么一次性执行完两个步骤，要么两个步骤都不执行。

- 使用自旋锁的时候，当发生多线程竞争锁的情况，加锁失败的线程会「忙等待」，直到它拿到锁。这里的「忙等待」可以用 `while` 循环等待实现，不过最好是使用 CPU 提供的 `PAUSE` 指令来实现「忙等待」，因为可以减少循环等待时的耗电量。

- **需要注意，在单核 CPU 上，需要抢占式的调度器（即不断通过时钟中断一个线程，运行其他线程）。否则，自旋锁在单 CPU 上无法使用，因为一个自旋的线程永远不会放弃 CPU。**

- 手写自旋锁：

  ```java
  package com.jian8.juc.lock;
  
  import java.util.concurrent.TimeUnit;
  import java.util.concurrent.atomic.AtomicReference;
  
  /**
   * 实现自旋锁
   * 自旋锁好处，循环比较获取知道成功位置，没有类似wait的阻塞
   *
   * 通过CAS操作完成自旋锁，A线程先进来调用mylock方法自己持有锁5秒钟，B随后进来发现当前有线程持有锁，不是null，所以只能通过自旋等待，知道A释放锁后B随后抢到
   */
  public class SpinLockDemo {
      public static void main(String[] args) {
          SpinLockDemo spinLockDemo = new SpinLockDemo();
          new Thread(() -> {
              spinLockDemo.mylock();
              try {
                  TimeUnit.SECONDS.sleep(3);
              }catch (Exception e){
                  e.printStackTrace();
              }
              spinLockDemo.myUnlock();
          }, "Thread 1").start();
  
          try {
              TimeUnit.SECONDS.sleep(3);
          }catch (Exception e){
              e.printStackTrace();
          }
  
          new Thread(() -> {
              spinLockDemo.mylock();
              spinLockDemo.myUnlock();
          }, "Thread 2").start();
      }
  
      //原子引用线程
      AtomicReference<Thread> atomicReference = new AtomicReference<>();
  
      public void mylock() {
          Thread thread = Thread.currentThread();
          System.out.println(Thread.currentThread().getName() + "\t come in");
          while (!atomicReference.compareAndSet(null, thread)) {
  
          }
      }
  
      public void myUnlock() {
          Thread thread = Thread.currentThread();
          atomicReference.compareAndSet(thread, null);
          System.out.println(Thread.currentThread().getName()+"\t invoked myunlock()");
      }
  }
  ```

#### 6、读写锁

1. **概念**

   - **ReentrantReadWriteLock**其读锁是共享锁，写锁是独占锁
- 读写锁的工作原理是：
     - 当「写锁」没有被线程持有时，多个线程能够并发地持有读锁，这大大提高了共享资源的访问效率，因为「读锁」是用于读取共享资源的场景，所以多个线程同时持有读锁也不会破坏共享资源的数据。
  - 但是，一旦「写锁」被线程持有后，读线程的获取读锁的操作会被阻塞，而且其他写线程的获取写锁的操作也会被阻塞。
   - 读写锁可以分为「读优先锁」和「写优先锁」。
- **公平读写锁比较简单的一种方式是：用队列把获取锁的线程排队，不管是写线程还是读线程都按照先进先出的原则加锁即可，这样读线程仍然可以并发，也不会出现「饥饿」的现象。**
  
2. **代码示例**

   ```java
   package com.jian8.juc.lock;
   
   import java.util.HashMap;
   import java.util.Map;
   import java.util.concurrent.TimeUnit;
   import java.util.concurrent.locks.Lock;
   import java.util.concurrent.locks.ReentrantReadWriteLock;
   
   /**
    * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
    * 但是
    * 如果有一个线程象取写共享资源来，就不应该自由其他线程可以对资源进行读或写
    * 总结
    * 读读能共存
    * 读写不能共存
    * 写写不能共存
    */
   public class ReadWriteLockDemo {
       public static void main(String[] args) {
           MyCache myCache = new MyCache();
           for (int i = 1; i <= 5; i++) {
               final int tempInt = i;
               new Thread(() -> {
                   myCache.put(tempInt + "", tempInt + "");
               }, "Thread " + i).start();
           }
           for (int i = 1; i <= 5; i++) {
               final int tempInt = i;
               new Thread(() -> {
                   myCache.get(tempInt + "");
               }, "Thread " + i).start();
           }
       }
   }
   
   class MyCache {
       private volatile Map<String, Object> map = new HashMap<>();
       private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
   
       /**
        * 写操作：原子+独占
        * 整个过程必须是一个完整的统一体，中间不许被分割，不许被打断
        *
        * @param key
        * @param value
        */
       public void put(String key, Object value) {
           rwLock.writeLock().lock();
           try {
               System.out.println(Thread.currentThread().getName() + "\t正在写入：" + key);
               TimeUnit.MILLISECONDS.sleep(300);
               map.put(key, value);
               System.out.println(Thread.currentThread().getName() + "\t写入完成");
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               rwLock.writeLock().unlock();
           }
   
       }
   
       public void get(String key) {
           rwLock.readLock().lock();
           try {
               System.out.println(Thread.currentThread().getName() + "\t正在读取：" + key);
               TimeUnit.MILLISECONDS.sleep(300);
               Object result = map.get(key);
               System.out.println(Thread.currentThread().getName() + "\t读取完成: " + result);
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               rwLock.readLock().unlock();
           }
   
       }
   
       public void clear() {
           map.clear();
       }
   }
   ```

#### 7、悲观锁和乐观锁

#### 7.1 悲观锁

- 悲观锁做事比较悲观，它认为**多线程同时修改共享资源的概率比较高，于是很容易出现冲突，所以访问共享资源前，先要上锁**。前面提到的互斥锁、自旋锁、读写锁，都是属于悲观锁。
- 比较适合写入操作比较频繁的场景，如果出现大量的读取操作，每次读取的时候都会进行加锁，这样会增加大量的锁的开销，降低了系统的吞吐量。

#### 7.2 乐观锁

- 乐观锁做事比较乐观，它假定冲突的概率很低，它的工作方式是：**先修改完共享资源，再验证这段时间内有没有发生冲突，如果没有其他线程在修改资源，那么操作完成，如果发现有其他线程已经修改过这个资源，就放弃本次操作**。**乐观锁全程并没有加锁，所以它也叫无锁编程**。
- 比较适合读取操作比较频繁的场景，如果出现大量的写入操作，数据发生冲突的可能性就会增大，为了保证数据的一致性，应用层需要不断的重新获取数据，这样会增加大量的查询操作，降低了系统的吞吐量。

### 六、CountDownLatch/CyclicBarrier/Semaphore使用过吗

#### 1、CountDownLatch（火箭发射倒计时）

1. 它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有的框架服务之后再执行

2. CountDownLatch主要有两个方法，当一个或多个线程调用await()方法时，调用线程会被阻塞。其他线程调用countDown()方法会将计数器减1，当计数器的值变为0时，因调用await()方法被阻塞的线程才会被唤醒，继续执行

3. 代码示例：

   ```java
   package com.jian8.juc.conditionThread;
   
   import java.util.concurrent.CountDownLatch;
   import java.util.concurrent.TimeUnit;
   
   public class CountDownLatchDemo {
       public static void main(String[] args) throws InterruptedException {
   //        general();
           countDownLatchTest();
       }
   
       public static void general(){
           for (int i = 1; i <= 6; i++) {
               new Thread(() -> {
                   System.out.println(Thread.currentThread().getName()+"\t上完自习，离开教室");
               }, "Thread-->"+i).start();
           }
           while (Thread.activeCount()>2){
               try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }
           }
           System.out.println(Thread.currentThread().getName()+"\t=====班长最后关门走人");
       }
   
       public static void countDownLatchTest() throws InterruptedException {
           CountDownLatch countDownLatch = new CountDownLatch(6);
           for (int i = 1; i <= 6; i++) {
               new Thread(() -> {
                   System.out.println(Thread.currentThread().getName()+"\t被灭");
                   countDownLatch.countDown();
               }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
           }
           countDownLatch.await();
           System.out.println(Thread.currentThread().getName()+"\t=====秦统一");
       }
   }
   ```

![Thread_9](/Users/na/IdeaProjects/Technical summary/Image/Thread_9.png)

#### 2、CyclicBarrier（集齐七颗龙珠召唤神龙）

1. CyclicBarrier

   可循环（Cyclic）使用的屏障。让一组线程到达一个屏障（也可叫同步点）时被阻塞，直到最后一个线程到达屏障时，屏障才会开门，所有被屏障拦截的线程才会继续干活，线程进入屏障通过CycliBarrier的await()方法

2. 代码示例：

   ```java
   package com.jian8.juc.conditionThread;
   
   import java.util.concurrent.BrokenBarrierException;
   import java.util.concurrent.CyclicBarrier;
   
   public class CyclicBarrierDemo {
       public static void main(String[] args) {
           cyclicBarrierTest();
       }
   
       public static void cyclicBarrierTest() {
           CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
               System.out.println("====召唤神龙=====");
           });
           for (int i = 1; i <= 7; i++) {
               final int tempInt = i;
               new Thread(() -> {
                   System.out.println(Thread.currentThread().getName() + "\t收集到第" + tempInt + "颗龙珠");
                   try {
                       cyclicBarrier.await();
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   } catch (BrokenBarrierException e) {
                       e.printStackTrace();
                   }
               }, "" + i).start();
           }
       }
   }
   ```

#### 3、Semaphore信号量：多个线程抢多个资源的锁

可以代替Synchronize和Lock

1. **信号量主要用于两个目的，一个是用于多个共享资源的互斥作用，另一个用于并发线程数的控制**；用来控制同一时间，资源可被访问的线程数量，一般可用于流量的控制。

2. 代码示例：

   **抢车位示例**：

   ```java
   package com.jian8.juc.conditionThread;
   
   import java.util.concurrent.Semaphore;
   import java.util.concurrent.TimeUnit;
   
   public class SemaphoreDemo {
       public static void main(String[] args) {
           Semaphore semaphore = new Semaphore(3);//模拟三个停车位
           for (int i = 1; i <= 6; i++) {//模拟6部汽车
               new Thread(() -> {
                   try {
                       semaphore.acquire();
                       System.out.println(Thread.currentThread().getName() + "\t抢到车位");
                       try {
                           TimeUnit.SECONDS.sleep(3);//停车3s
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                       }
                       System.out.println(Thread.currentThread().getName() + "\t停车3s后离开车位");
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   } finally {
                       semaphore.release();
                   }
               }, "Car " + i).start();
           }
       }
   }
   ```

### 七、阻塞队列

- **==ArrayBlockingQueue==**是一个基于数组结构的有界阻塞队列，此队列按FIFO原则对元素进行排序
- **==LinkedBlockingQueue==**是一个基于链表结构的阻塞队列，此队列按FIFO排序元素，吞吐量通常要高于ArrayBlockingQueue
- **==SynchronousQueue==**是一个不存储元素的阻塞队列，灭个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于

#### 1、队列和阻塞队列

1. 首先是一个队列，而一个阻塞队列再数据结构中所起的作用大致如下图

   ```
   graph LR
   Thread1-- put -->id1["阻塞队列"]
   subgraph BlockingQueue
   	id1
   end
   id1-- Take -->Thread2
   蛋糕师父--"放(柜满阻塞)"-->id2[蛋糕展示柜]
   subgraph 柜
   	id2
   end
   id2--"取(柜空阻塞)"-->顾客
   ```

   线程1往阻塞队列中添加元素，而线程2从阻塞队列中移除元素

   当阻塞队列是空是，从队列中==获取take==元素的操作会被阻塞

   当阻塞队列是满时，从队列中==添加put==元素的操作会被阻塞

   试图从空的阻塞队列中获取元素的线程将会被阻塞，直到其他的线程网空的队列插入新的元素。

   试图往已满的阻塞队列中添加新元素的线程同样会被阻塞，知道其他的线程从列中移除一个或者多个元素或者完全清空队列后使队列重新变得空闲起来并后续新增

#### 2、为什么用？有什么好处？

1. 在多线程领域：所谓阻塞，在某些情况下会挂起线程，一旦满足条件，被挂起的线程又会自动被唤醒

2. 为什么需要BlockingQueue

   好处时我们不需要关心什么时候需要阻塞线程，什么时候需要唤醒线程，因为这一切BlockingQueue都给你一手包办了

   在concurrent包发布以前，在多线程环境下，==我们每个程序员都必须自己控制这些细节，尤其还要兼顾效率和线程安全==，而这回给我们程序带来不小的复杂度

#### 3、BlockingQueue的核心方法

| 方法类型 | 抛出异常  | 特殊值   | 阻塞   | 超时               |
| -------- | --------- | -------- | ------ | ------------------ |
| 插入     | add(e)    | offer(e) | put(e) | offer(e,time,unit) |
| 移除     | remove()  | poll()   | take   | poll(time,unit)    |
| 检查     | element() | peek()   | 不可用 | 不可用             |

| 方法类型 | status                                                       |
| -------- | ------------------------------------------------------------ |
| 抛出异常 | 当阻塞队列满时，再往队列中add会抛`IllegalStateException: Queue full` 当阻塞队列空时，在网队列里remove会抛`NoSuchElementException` |
| 特殊值   | 插入方法，成功true失败false，移除方法，成功返回出队列的元素，队列里没有就返回null |
| 一直阻塞 | 当阻塞队列满时，生产者线程继续往队列里put元素，队列会一直阻塞线程知道put数据或响应中断退出 当阻塞队列空时，消费者线程试图从队列take元素，队列会一直阻塞消费者线程知道队列可用。 |
| 超时退出 | 当阻塞队列满时，队列会阻塞生产者线程一定时间，超过限时后生产者线程会退出 |

#### 4、架构梳理+种类分析

1. 种类分析

   - ==ArrayBlockingQueue==:由数据结构组成的有界阻塞队列。
   - ==LinkedBlockingQueue==:由链表结构组成的有界（但大小默认值为`Integer.MAX_VALUE`)阻塞队列。
   - PriorityBlockingQueue:支持优先级排序的无界阻塞队列。
   - DelayQueue:使用优先级队列实现的延迟无界阻塞队列。
   - ==SychronousQueue==:不存储元素的阻塞队列，也即单个元素的队列。元素定制阻塞队列
   - LinkedTransferQueue:由链表结构组成的无界阻塞队列。
   - LinkedBlocking**Deque**:由历览表结构组成的双向阻塞队列。

2. **SychronousQueue**

   - 理论：SynchronousQueue没有容量，与其他BlockingQueue不同，SychronousQueue是一个不存储元素的BlockingQueue，每一个put操作必须要等待一个take操作，否则不能继续添加元素，反之亦然。

   - 代码示例

     ```java
     package com.jian8.juc.queue;
     
     import java.util.concurrent.BlockingQueue;
     import java.util.concurrent.SynchronousQueue;
     import java.util.concurrent.TimeUnit;
     
     /**
      * ArrayBlockingQueue是一个基于数组结构的有界阻塞队列，此队列按FIFO原则对元素进行排序
      * LinkedBlockingQueue是一个基于链表结构的阻塞队列，此队列按FIFO排序元素，吞吐量通常要高于ArrayBlockingQueue
      * SynchronousQueue是一个不存储元素的阻塞队列，一个插入操作必须等到另一个线程调用移除操作，否则插入操作一直处于阻塞状态，吞吐量通常要高于
      * 1.队列
      * 2.阻塞队列
      * 2.1 阻塞队列有没有好的一面
      * 2.2 不得不阻塞，你如何管理
      */
     public class SynchronousQueueDemo {
         public static void main(String[] args) throws InterruptedException {
             BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
             new Thread(() -> {
                 try {
                     System.out.println(Thread.currentThread().getName() + "\t put 1");
                     blockingQueue.put("1");
                     System.out.println(Thread.currentThread().getName() + "\t put 2");
                     blockingQueue.put("2");
                     System.out.println(Thread.currentThread().getName() + "\t put 3");
                     blockingQueue.put("3");
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }, "AAA").start();
             new Thread(() -> {
                 try {
                     TimeUnit.SECONDS.sleep(5);
                     System.out.println(Thread.currentThread().getName() + "\ttake " + blockingQueue.take());
                     TimeUnit.SECONDS.sleep(5);
                     System.out.println(Thread.currentThread().getName() + "\ttake " + blockingQueue.take());
                     TimeUnit.SECONDS.sleep(5);
                     System.out.println(Thread.currentThread().getName() + "\ttake " + blockingQueue.take());
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }, "BBB").start();
         }
     }
     ```

#### 5、生产者消费者模式

1. 生产者消费者模式

   - 传统版1.0：synchronized+wait+notify 2.0：lock+await+signal

     ```java
     package com.jian8.juc.queue;
     
     import java.util.concurrent.locks.Condition;
     import java.util.concurrent.locks.Lock;
     import java.util.concurrent.locks.ReentrantLock;
     
     /**
      * 一个初始值为零的变量，两个线程对其交替操作，一个加1一个减1，来5轮
      * 1. 线程  操作/方法  资源类
      * 2. 判断  干活  通知
      * 3. 防止虚假唤起机制 判断用while不是if
      */
     public class ProdConsumer_TraditionDemo {
         public static void main(String[] args) {
             ShareData shareData = new ShareData();
             for (int i = 1; i <= 5; i++) {
                 new Thread(() -> {
                     try {
                         shareData.increment();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }, "ProductorA " + i).start();
             }
             for (int i = 1; i <= 5; i++) {
                 new Thread(() -> {
                     try {
                         shareData.decrement();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }, "ConsumerA  " + i).start();
             }
             for (int i = 1; i <= 5; i++) {
                 new Thread(() -> {
                     try {
                         shareData.increment();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }, "ProductorB " + i).start();
             }
             for (int i = 1; i <= 5; i++) {
                 new Thread(() -> {
                     try {
                         shareData.decrement();
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }, "ConsumerB  " + i).start();
             }
         }
     }
     
     class ShareData {//资源类
         private int number = 0;
         private Lock lock = new ReentrantLock();
         private Condition condition = lock.newCondition();
     
         public void increment() throws Exception {
             lock.lock();
             try {
                 //1.判断
                 while (number != 0) {
                     //等待不能生产
                     condition.await();
                 }
                 //2.干活
                 number++;
                 System.out.println(Thread.currentThread().getName() + "\t" + number);
                 //3.通知
                 condition.signalAll();
             } catch (Exception e) {
                 e.printStackTrace();
             } finally {
                 lock.unlock();
             }
         }
     
         public void decrement() throws Exception {
             lock.lock();
             try {
                 //1.判断
                 while (number == 0) {
                     //等待不能消费
                     condition.await();
                 }
                 //2.消费
                 number--;
                 System.out.println(Thread.currentThread().getName() + "\t" + number);
                 //3.通知
                 condition.signalAll();
             } catch (Exception e) {
                 e.printStackTrace();
             } finally {
                 lock.unlock();
             }
         }
     }
     ```

   - 阻塞队列版：volatile cas atomicInteger BlockQueue

     ```java
     package com.jian8.juc.queue;
     
     import java.util.concurrent.ArrayBlockingQueue;
     import java.util.concurrent.BlockingQueue;
     import java.util.concurrent.TimeUnit;
     import java.util.concurrent.atomic.AtomicInteger;
     
     public class ProdConsumer_BlockQueueDemo {
         public static void main(String[] args) {
             MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));
             new Thread(() -> {
                 System.out.println(Thread.currentThread().getName() + "\t生产线程启动");
                 try {
                     myResource.myProd();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }, "Prod").start();
             new Thread(() -> {
                 System.out.println(Thread.currentThread().getName() + "\t消费线程启动");
                 try {
                     myResource.myConsumer();
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }, "Consumer").start();
     
             try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
             System.out.println("5s后main叫停，线程结束");
             try {
                 myResource.stop();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     }
     
     class MyResource {
         private volatile boolean flag = true;//默认开启，进行生产+消费
         private AtomicInteger atomicInteger = new AtomicInteger();
     
         BlockingQueue<String> blockingQueue = null;
     
         public MyResource(BlockingQueue<String> blockingQueue) {
             this.blockingQueue = blockingQueue;
             System.out.println(blockingQueue.getClass().getName());
         }
     
         public void myProd() throws Exception {
             String data = null;
             boolean retValue;
             while (flag) {
                 data = atomicInteger.incrementAndGet() + "";
                 retValue = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
                 if (retValue) {
                     System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "成功");
                 } else {
                     System.out.println(Thread.currentThread().getName() + "\t插入队列" + data + "失败");
                 }
                 TimeUnit.SECONDS.sleep(1);
             }
             System.out.println(Thread.currentThread().getName() + "\t大老板叫停了，flag=false，生产结束");
         }
     
         public void myConsumer() throws Exception {
             String result = null;
             while (flag) {
                 result = blockingQueue.poll(2, TimeUnit.SECONDS);
                 if (null == result || result.equalsIgnoreCase("")) {
                     flag = false;
                     System.out.println(Thread.currentThread().getName() + "\t超过2s没有取到蛋糕，消费退出");
                     System.out.println();
                     return;
                 }
                 System.out.println(Thread.currentThread().getName() + "\t消费队列" + result + "成功");
             }
         }
     
         public void stop() throws Exception {
             flag = false;
         }
     }
     ```

2. 线程池

3. 消息中间件

#### 6、synchronized和lock有什么区别？用新的lock有什么好处？请举例

> 区别

1. 原始构成

   - synchronized是关键字，属于jvm

     **monitorenter**，底层是通过monitor对象来完成，其实wait/notify等方法也依赖于monitor对象只有在同步或方法中才能掉wait/notify等方法

     **monitorexit**，两次：保证正常退出和异常退出

   - Lock是具体类，是api层面的锁（java.util.）

2. 使用方法

   - sychronized不需要用户取手动释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用
   - ReentrantLock则需要用户去手动释放锁若没有主动释放锁，就有可能导致出现死锁现象，需要lock()和unlock()方法配合try/finally语句块来完成

3. 等待是否可中断

   - synchronized不可中断，除非抛出异常或者正常运行完成
   - ReentrantLock可中断，设置超时方法tryLock(long timeout, TimeUnit unit)，或者lockInterruptibly()放代码块中，调用interrupt()方法可中断。

4. 加锁是否公平

   - synchronized非公平锁
   - ReentrantLock两者都可以，默认公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁

5. 锁绑定多个条件Condition

   - synchronized没有
   - ReentrantLock用来实现分组唤醒需要要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程。

#### 7、对线程之间按顺序调用，实现A>B>C三个线程启动

   ```java
   package com.jian8.juc.lock;
   
   import java.util.concurrent.locks.Condition;
   import java.util.concurrent.locks.Lock;
   import java.util.concurrent.locks.ReentrantLock;
   
   /**
    * synchronized和lock区别
    * <p===lock可绑定多个条件===
    * 对线程之间按顺序调用，实现A>B>C三个线程启动，要求如下：
    * AA打印5次，BB打印10次，CC打印15次
    * 紧接着
    * AA打印5次，BB打印10次，CC打印15次
    * 。。。。
    * 来十轮
    */
   public class SyncAndReentrantLockDemo {
       public static void main(String[] args) {
           ShareData shareData = new ShareData();
           new Thread(() -> {
               for (int i = 1; i <= 10; i++) {
                   shareData.print5();
               }
           }, "A").start();
           new Thread(() -> {
               for (int i = 1; i <= 10; i++) {
                   shareData.print10();
               }
           }, "B").start();
           new Thread(() -> {
               for (int i = 1; i <= 10; i++) {
                   shareData.print15();
               }
           }, "C").start();
       }
   
   }
   
   class ShareData {
       private int number = 1;//A:1 B:2 C:3
       private Lock lock = new ReentrantLock();
       private Condition condition1 = lock.newCondition();
       private Condition condition2 = lock.newCondition();
       private Condition condition3 = lock.newCondition();
   
       public void print5() {
           lock.lock();
           try {
               //判断
               while (number != 1) {
                   condition1.await();
               }
               //干活
               for (int i = 1; i <= 5; i++) {
                   System.out.println(Thread.currentThread().getName() + "\t" + i);
               }
               //通知
               number = 2;
               condition2.signal();
   
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               lock.unlock();
           }
       }
       public void print10() {
           lock.lock();
           try {
               //判断
               while (number != 2) {
                   condition2.await();
               }
               //干活
               for (int i = 1; i <= 10; i++) {
                   System.out.println(Thread.currentThread().getName() + "\t" + i);
               }
               //通知
               number = 3;
               condition3.signal();
   
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               lock.unlock();
           }
       }
       public void print15() {
           lock.lock();
           try {
               //判断
               while (number != 3) {
                   condition3.await();
               }
               //干活
               for (int i = 1; i <= 15; i++) {
                   System.out.println(Thread.currentThread().getName() + "\t" + i);
               }
               //通知
               number = 1;
               condition1.signal();
   
           } catch (Exception e) {
               e.printStackTrace();
           } finally {
               lock.unlock();
           }
       }
   }
   ```

### 八、线程池

#### 1、Callable接口的使用

```java
package com.jian8.juc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 多线程中，第三种获得多线程的方式
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //FutureTask(Callable<V> callable)
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new MyThread2());

        new Thread(futureTask, "AAA").start();
//        new Thread(futureTask, "BBB").start();//复用，直接取值，不要重启两个线程
        int a = 100;
        int b = 0;
        //b = futureTask.get();//要求获得Callable线程的计算结果，如果没有计算完成就要去强求，会导致堵塞，直到计算完成
        while (!futureTask.isDone()) {//当futureTask完成后取值
            b = futureTask.get();
        }
        System.out.println("*******Result" + (a + b));
    }
}

class MyThread implements Runnable {
    @Override
    public void run() {
    }
}

class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println("Callable come in");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1024;
    }
}
```

#### 1.1 线程的创建方式

- 继承Thread类，重写Run方法
- 实现Runnable接口，重写Run方法
- 实现Callable接口，重写Call方法
- 线程池Executors工具类创建或者自定义线程池new ThreadPoolExecutor()

#### 1.2 Callable和Runnable的区别

- 重写方法不同，一个run方法一个call方法

- Callable有返回值
- call方法会抛出异常

#### 2、线程池的优点

1. 线程池做的工作主要是控制运行的线程的数量，处理过程中将任务放入队列，然后在线程创建后启动给这些任务，如果线程数量超过了最大数量，超出数量的线程排队等候，等其他线程执行完毕，再从队列中取出任务来执行

2. 主要特点

   线程复用、控制最大并发数、管理线程

   - 降低资源消耗，通过重复利用已创建的线程降低线程创建和销毁造成的消耗
   - 提过响应速度。当任务到达时，任务可以不需要等到线程创建就能立即执行
   - 提高线程的客观理想。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池可以进行统一的分配，调优和监控

#### 3、线程池如何使用

1. 架构说明

   Java中的线程池是通过Executor框架实现的，该框架中用到了Executor,Executors,ExecutorService,ThreadPoolExecutor

   ```
   graph BT
   	类-Executors
   	类-ScheduledThreadPoolExecutor-->类-ThreadPoolExecutor
   	类-ThreadPoolExecutor-->类-AbstractExecutorService
   	类-AbstractExecutorService-.->接口-ExecutorService
   	类-ScheduledThreadPoolExecutor-.->接口-ScheduledExecutorService
   	接口-ScheduledExecutorService-->接口-ExecutorService
   	接口-ExecutorService-->接口-Executor
   ```

2. 编码实现

   实现有五种，Executors.newScheduledThreadPool()是带时间调度的，java8新推出Executors.newWorkStealingPool(int),使用目前机器上可用的处理器作为他的并行级别

   重点有三种

   - Executors.newFixedThreadPool(int)

     **执行长期的任务，性能好很多**

     创建一个定长线程池，可控制线程最大并发数，炒出的线程回在队列中等待。

     newFixedThreadPool创建的线程池corePoolSize和maximumPoolSize值是想到等的，他使用的是LinkedBlockingQueue

   - Executors.newSingleThreadExecutor()

     **一个任务一个任务执行的场景**

     创建一个单线程话的线程池，他只会用唯一的工作线程来执行任务，保证所有任务按照指定顺序执行

     newSingleThreadExecutor将corePoolSize和maximumPoolSize都设置为1，使用LinkedBlockingQueue

   - Executors.newCachedThreadPool()

     **执行很多短期异步的小程序或负载较轻的服务器**

     创建一个可缓存线程池，如果线程池长度超过处理需要，可灵活回收空闲线程，若无可回收，则新建线程。

     newCachedThreadPool将corePoolSize设置为0，将maximumPoolSize设置为Integer.MAX_VALUE,使用的SynchronousQueue,也就是说来了任务就创建线程运行，当线程空闲超过60s，就销毁线程

3. 底层是**ThreadPoolExecutor**

![Thread_10](/Users/na/IdeaProjects/Technical summary/Image/Thread_10.png)

#### 4、线程池的重要参数

```
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler)
```

1. ==corePoolSize==

   ：线程池中常驻核心线程数

   - 在创建了线程池后，当有请求任务来之后，就会安排池中的线程去执行请求任务
   - 当线程池的线程数达到corePoolSize后，就会把到达的任务放到缓存队列当中

2. **==maximumPoolSize==**：线程池能够容纳同时执行的最大线程数，必须大于等于1

3. ==keepAliveTime==：多余的空闲线程的存活时间

   - 当前线程池数量超过corePoolSize时，档口空闲时间达到keepAliveTime值时，多余空闲线程会被销毁到只剩下corePoolSize个线程为止

4. **==unit==**：keepAliveTime的单位

5. **==workQueue==**：任务队列，被提交但尚未被执行的任务

6. **==threadFactory==**：表示生成线程池中工作线程的线程工厂，用于创建线程一般用默认的即可

7. **==handler==**：拒绝策略，表示当工作队列workQueue满了并且工作线程大于等于线程池的最大线程数（maximumPoolSize）时如何来拒绝请求执行的runable的策略

![Thread_11](/Users/na/IdeaProjects/Technical summary/Image/Thread_11.png)

#### 5、线程池的底层工作原理

![Thread_12](/Users/na/IdeaProjects/Technical summary/Image/Thread_12.png)

**==流程==**

1. 在创建了线程池之后，等待提交过来的任务请求。

2. 当调用execute()方法添加一个请求任务时，线程池会做出如下判断

   2.1 如果正在运行的线程数量小于corePoolSize，那么马上船舰线程运行这个任务；

   2.2 如果正在运行的线程数量大于或等于corePoolSize，那么将这个任务放入队列；

   2.3如果此时队列满了且运行的线程数小于maximumPoolSize，那么还是要创建非核心线程立刻运行此任务

   2.4如果队列满了且正在运行的线程数量大于或等于maxmumPoolSize，那么启动饱和拒绝策略来执行

3. 当一个线程完成任务时，他会从队列中却下一个任务来执行

4. 当一个线程无事可做超过一定的时间（keepAliveTime）时，线程池会判断：

   如果当前运行的线程数大于corePoolSize，那么这个线程会被停掉；所以线程池的所有任务完成后他最大会收缩到corePoolSize的大小

### 九、线程池用过吗？生产上你如何设置合理参数

#### 1、线程池的拒绝策略

1. 什么是线程策略

   等待队列也已经排满了，再也塞不下新任务了，同时线程池中的max线程也达到了，无法继续为新任务服务。这时我们就需要拒绝策略机制合理的处理这个问题。

2. JDK内置的拒绝策略

   - AbortPolicy(默认)

     直接抛出RejectedExecutionException异常阻止系统正常运行

   - CallerRunsPolicy

     ”调用者运行“一种调节机制，该策略既不会抛弃任务，也不会抛出异常，而是将某些任务回退到调用者，从而降低新任务的流量

   - DiscardOldestPolicy

     抛弃队列中等待最久的任务，然后把当前任务加入队列中尝试再次提交当前任务

   - DiscardPolicy

     直接丢弃任务，不予任何处理也不抛异常。如果允许任务丢失，这是最好的一种方案

3. 均实现了RejectedExecutionHandler接口

#### 2、你在工作中单一的/固定数的/可变的三种创建线程池的方法，用哪个多

**==一个都不用，我们生产上只能使用自定义的！！！！==**

为什么？

线程池不允许使用Executors创建，试试通过ThreadPoolExecutor的方式，规避资源耗尽风险

- **FixedThreadPool 和 SingleThreadExecutor** ：允许请求的队列长度为 Integer.MAX_VALUE ，可能堆积大量的请求，从而导致OOM。
- **CachedThreadPool 和 ScheduledThreadPool** ：允许创建的线程数量为 Integer.MAX_VALUE ，可能会创建大量线程，从而导致OOM。

#### 3、你在工作中时如何使用线程池的，是否自定义过线程池使用

```java
public class MyThreadPoolDemo {
    public static void main(String[] args) {
//        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);
//        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
//        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        ExecutorService threadPool = new ThreadPoolExecutor(
                3,
                5,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()
                );


        try{
            for (int i = 1; i <= 15; i++){
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName());
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
```

#### 4、合理配置线程池你是如何考虑的？

1. **CPU密集型**

   CPU密集的意思是该任务需要大量的运算，而没有阻塞，CPU一直全速运行

   CPU密集任务只有在真正多核CPU上才可能得到加速（通过多线程）

   而在单核CPU上，无论你开几个模拟的多线程该任务都不可能得到加速，因为CPU总的运算能力就那些

   CPU密集型任务配置尽可能少的线程数量：

   ==**一般公式：CPU核数+1个线程的线程池**==

2. **IO密集型**

   - 由于IO密集型任务线程并不是一直在执行任务，则应配置经可能多的线程，如CPU核数 * 2

   - IO密集型，即该任务需要大量的IO，即大量的阻塞。

     在单线程上运行IO密集型的任务会导致浪费大量的 CPU运算能力浪费在等待。

     所以在IO密集型任务中使用多线程可以大大的加速程序运行，即使在单核CPU上，这种加速主要就是利用了被浪费掉的阻塞时间。

     IO密集型时，大部分线程都阻塞，故需要多配置线程数：

     参考公式：==CPU核数/（1-阻塞系数） 阻塞系数在0.8~0.9之间==

     八核CPU：8/（1-0，9）=80

### 十、死锁

1. 是什么

   死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象。一种非常简单的避免死锁的方式就是：**指定获取锁的顺序，并强制线程按照指定的顺序获取锁。**因此，如果所有的线程都是以同样的顺序加锁和释放锁，就不会出现死锁了。

   ![Thread_13](/Users/na/IdeaProjects/Technical summary/Image/Thread_13.png)
   
2. 产生死锁的主要原因

   - 系统资源不足
   - 进程运行推进的顺序不合适
   - 资源分配不当

3. 死锁示例

   ```java
   package com.jian8.juc.thread;
   
   import java.util.concurrent.TimeUnit;
   
   /**
    * 死锁是指两个或两个以上的进程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力干涉那他们都将无法推进下去，
    */
   public class DeadLockDemo {
       public static void main(String[] args) {
           String lockA = "lockA";
           String lockB = "lockB";
           new Thread(new HoldThread(lockA,lockB),"Thread-AAA").start();
           new Thread(new HoldThread(lockB,lockA),"Thread-BBB").start();
       }
   }
   
   class HoldThread implements Runnable {
   
       private String lockA;
       private String lockB;
   
       public HoldThread(String lockA, String lockB) {
           this.lockA = lockA;
           this.lockB = lockB;
       }
   
       @Override
       public void run() {
           synchronized (lockA) {
               System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockA + "\t尝试获得：" + lockB);
               try {
                   TimeUnit.SECONDS.sleep(2);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               synchronized (lockB) {
                   System.out.println(Thread.currentThread().getName() + "\t自己持有：" + lockB + "\t尝试获得：" + lockA);
               }
           }
       }
   }
   ```

4. 解决

   1. 使用`jps -l`定位进程号
   2. `jstack 进程号`找到死锁查看

## 十一

### 1. 线程的状态

![States of a Thread](https://pic2.zhimg.com/v2-54ad049834f12e2f839f14c51fab3299_b.jpg)

![Thread_1](/Users/na/IdeaProjects/Technical summary/Image/Thread_1.png)

- 当线程对象创建后，即进入新建状态，如：`Thread t = new MyThread();`

- 当调用线程对象的`start()`方法时，线程即进入就绪状态。处于就绪状态的线程只是说明此线程已经做好准备，随时等待CPU调度执行，并不是说执行了`start()`方法就立即执行。

- 当CPU开始调度处于就绪状态的线程时，此时线程才得以真正执行，即进入到运行状态。

- 处于运行状态中的线程由于某种原因，暂时放弃对CPU的使用权，停止执行，此时进入阻塞状态，直到其进入到就绪状态，才有机会再次被CPU调用以进入到运行状态。

  **阻塞状态分类**

  1. 等待阻塞：运行状态中的线程执行wait()方法，使本线程进入到等待阻塞状态；
  2. 同步阻塞：线程在获取`synchronized`同步锁失败（因为锁被其它线程占用），它会进入到同步阻塞状态；
  3. 其他阻塞：通过调用线程的sleep()或join()或发出I/O请求时，线程会进入到阻塞状态。当`sleep()`状态超时、`join()`等待线程终止或者超时、或者I/O处理完毕时，线程重新转入就绪状态。

- 线程执行完毕或者是异常退出，该线程结束生命周期。

### 1.1 Object方法

#### 1.1.1 wait方法

- wait()方法的作用是将当前运行的线程挂起（即让其进入阻塞状态），直到notify或notifyAll方法来唤醒线程.
- wait(long timeout)，该方法与wait()方法类似，唯一的区别就是在指定时间内，如果没有notify或notifAll方法的唤醒，也会自动唤醒。
- 调用wait方法后，线程是会释放对monitor对象的所有权的。
- 一个通过wait方法阻塞的线程，必须同时满足以下两个条件才能被真正执行：
  - 线程需要被唤醒（超时唤醒或调用notify/notifyll）
  - 线程唤醒后需要竞争到锁（monitor）
- wait方法的使用必须在同步的范围内，否则就会抛出IllegalMonitorStateException异常， 要注意`wait()`方法会强迫线程先进行释放锁操作，所以在调用`wait()`时， 该线程必须已经获得锁，否则会抛出异常。但是由于`wait()`在`synchronized`的方法内部被执行， 锁一定已经获得， 就不会抛出异常了。

#### 1.1.2 notify/notifyAll方法

- notify和notifyAll的区别在于前者只能唤醒monitor上的一个线程，对其他线程没有影响，而notifyAll则唤醒所有的线程
- notify和notifyAll方法的使用必须在同步的范围内，需要找到monitor对象获取到这个对象的锁，然后到这个对象的等待队列中去唤醒一个线程。

#### 1.1.3 wait、notify、notifyAll与synchronized一起使用的原因

-  Lost Wake-Up Problem：

```java
class BlockingQueue {
    Queue<String> buffer = new LinkedList<String>();

    public void give(String data) {
        buffer.add(data);
        notify();                   // 往队列里添加的时候notify，因为可能有人在等着take
    }

    public String take() throws InterruptedException {
        while (buffer.isEmpty())    // 用while，防止spurious wakeups（虚假唤醒）
            wait(); // 当buffer是空的时候就等着别人give
        return buffer.remove();
    }
}
```

- 这段代码并没有受 synchronized 保护，于是便有可能发生以下场景：
  1. 首先，消费者线程调用 take 方法并判断 buffer.isEmpty 方法是否返回 true，若为 true 代表buffer是空的，则线程希望进入等待，但是在线程调用 wait 方法之前，就被调度器暂停了，所以此时还没来得及执行 wait 方法。
  2. 此时生产者开始运行，执行了整个 give 方法，它往 buffer 中添加了数据，并执行了 notify 方法，但 notify 并没有任何效果，因为消费者线程的 wait 方法没来得及执行，所以没有线程在等待被唤醒。
  3. 此时，刚才被调度器暂停的消费者线程回来继续执行 wait 方法并进入了等待。
- 虽然刚才消费者判断了 buffer.isEmpty 条件，但真正执行 wait 方法时，之前的buffer.isEmpty 的结果已经过期了，不再符合最新的场景了，因为这里的“判断-执行”不是一个原子操作，它在中间被打断了，是线程不安全的。假设这时没有更多的生产者进行生产，消费者便有可能陷入无穷无尽的等待，因为它错过了刚才 give 方法内的 notify 的唤醒。
- 我们看到正是因为 wait 方法所在的 take 方法没有被 synchronized 保护，所以它的 while 判断和 wait 方法无法构成原子操作，那么此时整个程序就很容易出错。

```java
class BlockingQueue {
    Queue<String> buffer = new LinkedList<String>();

    public void give(String data) {
        synchronized(this){
          buffer.add(data);
          notify();                   // 往队列里添加的时候notify，因为可能有人在等着take
        }
    }

    public String take() throws InterruptedException {
        synchronized(this){
          while (buffer.isEmpty())    // 用while，防止spurious wakeups（虚假唤醒），这样即便被虚假唤醒了，也会再次检查while里面的条件，如果不满足条件，就会继续wait，也就消除了虚假唤醒的风险。
            wait(); // 当buffer是空的时候就等着别人give
        }
        return buffer.remove();
    }
}
```

- 把代码改写成源码注释所要求的被 synchronized 保护的同步代码块的形式。这样就可以确保 notify 方法永远不会在 buffer.isEmpty 和 wait 方法之间被调用，提升了程序的安全性。
- 另外，wait 方法会释放monitor锁，这也要求我们必须首先进入到 synchronized 内持有这把锁。
- notify和notifyAll方法的使用必须在同步的范围内，需要找到monitor对象获取到这个对象的锁，然后到这个对象的等待队列中去唤醒一个线程。

### 1.2 Thread方法

#### 1.2.1 sleep方法

- sleep方法的作用是让当前线程暂停指定的时间（毫秒）
- 与wait方法的区别：最简单的区别是，wait方法依赖于同步，而sleep方法可以直接调用。而更深层次的区别在于sleep方法只是暂时让出CPU的执行权，并不释放锁。而wait方法则需要释放锁。
- sleep暂停期间一直持有monitor对象锁，其他线程是不能进入的。而wait方法则不同，当调用wait方法后，当前线程会释放持有的monitor对象锁，因此，其他线程还可以进入到同步方法，线程被唤醒后，需要竞争锁，获取到锁之后再继续执行。
- 与wait的区别：
  - 1.所属的类不同：sleep方法是定义在Thread上，wait方法是定义在Object上
  - 2.对于锁资源的处理方式不同sleep不会释放锁，wait会释放锁
  - 3.使用范围：sleep可以使用在任何代码块，wait必须在同步方法或同步代码块执行
  - 4.唤醒：void notify()可以唤醒等待（wait）的单个线程，而sleep不能被唤醒

#### 1.2.2 yield方法

- yield方法的作用是暂停当前线程，以便其他线程有机会执行，不过不能指定暂停的时间，并且也不能保证当前线程马上停止。yield方法只是将Running状态转变为Runnable状态。
- 调度器可能会忽略该方法。
- 与sleep的区别：Thread.sleep()可以精确指定休眠的时间，而Thread.yield()依赖于CPU的时间片划分，在我的电脑上大约为20微秒；Thread.sleep()会抛出中断异常，且能被中断，而Thread.yield()不可以；

#### 1.2.3 join方法

- join方法的作用是父线程等待子线程执行完成后再执行，换句话说就是将异步执行的线程合并为同步的线程。
- join(long millis)方法的实现，可以看出join方法就是通过wait方法来将线程的阻塞，如果join的线程还在执行，则将当前线程阻塞起来，直到join的线程执行完成，当前线程才能执行。
- 不过有一点需要注意，这里的join只调用了wait方法，却没有对应的notify方法，原因是Thread的start方法中做了相应的处理，所以当join的线程执行完成以后，会自动唤醒主线程继续往下执行。


### 2. 进程的状态

![Thread_23](https://img-blog.csdn.net/20170820104536564?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcWljaGVuZzc3Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

- **新建态：** 对应于进程刚刚被创建时没有被提交的状态，并等待系统完成创建进程的所有必要信息。 进程正在创建过程中，还不能运行。操作系统在创建状态要进行的工作包括分配和建立进程控制块表项、建立资源表格（如打开文件表）并分配资源、加载程序并建立地址空间表等。创建进程时分为两个阶段，第一个阶段为一个新进程创建必要的管理信息，第二个阶段让该进程进入就绪状态。由于有了新建态，操作系统往往可以根据系统的性能和主存容量的限制推迟新建态进程的提交。

- **终止态：**进程已结束运行，回收除进程控制块之外的其他资源，并让其他进程从进程控制块中收集有关信息（如记帐和将退出代码传递给父进程）。类似的，进程的终止也可分为两个阶段，第一个阶段等待操作系统进行善后处理，第二个阶段释放主存。

- **活跃就绪：**是指进程在主存并且可被调度的状态。

  **静止就绪（挂起就绪）**：是指进程被对换到辅存时的就绪状态，是不能被直接调度的状态，只有当主存中没有活跃就绪态进程，或者是挂起就绪态进程具有更高的优先级，系统将把挂起就绪态进程调回主存并转换为活跃就绪。

  **活跃阻塞：**是指进程已在主存，一旦等待的事件产生便进入活跃就绪状态。

  **静止阻塞：**是指进程对换到辅存时的阻塞状态，一旦等待的事件产生便进入静止就绪状态。

### 3. 中断

- 线程中断即线程运行过程中被其他线程给打断了，它与 stop 最大的区别是：stop 是由系统强制终止线程，而线程中断则是给目标线程发送一个中断信号，如果目标线程没有接收线程中断的信号并结束线程，线程则不会终止，具体是否退出或者执行其他逻辑由目标线程决定。
- 对目标线程调用`interrupt()`方法可以请求中断一个线程，目标线程通过检测`isInterrupted()`标志获取自身是否已中断。如果目标线程处于等待状态，即目标线程调用Object 类的 wait 方法、Thread 类的 sleep 方法、Thread 类的 join 方法，该线程会捕获到`InterruptedException`；
- 目标线程检测到`isInterrupted()`为`true`或者捕获了`InterruptedException`都应该立刻结束自身线程；
- interrupt方法其实只是改变了中断状态而已。而sleep、wait和join这些方法的内部会不断的检查中断状态的值，从而自己抛出InterruptEdException。所以，如果在线程进行其他处理时，调用了它的interrupt方法，线程也不会抛出InterruptedException的，只有当线程走到了sleep, wait, join这些方法的时候，才会抛出InterruptedException。若是没有调用sleep, wait, join这些方法，或者没有在线程里自己检查中断状态或自己抛出InterruptedException，那InterruptedException是不会抛出来的。


```java
public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new MyThread();
        t.start();
        Thread.sleep(1000);
        t.interrupt(); // 中断t线程
        t.join(); // 等待t线程结束
        System.out.println("end");
    }
}

class MyThread extends Thread {
    public void run() {
        Thread hello = new HelloThread();
        hello.start(); // 启动hello线程
        try {
            hello.join(); // 等待hello线程结束
        } catch (InterruptedException e) {
            System.out.println("interrupted!");
        }
        hello.interrupt();
    }
}

class HelloThread extends Thread {
    public void run() {
        int n = 0;
        while (!isInterrupted()) {
            n++;
            System.out.println(n + " hello!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
```

- 通过标志位判断需要正确使用`volatile`关键字；
- `volatile`关键字解决了共享变量在线程间的可见性问题。

```java
public class Main {
    public static void main(String[] args)  throws InterruptedException {
        HelloThread t = new HelloThread();
        t.start();
        Thread.sleep(1);
        t.running = false; // 标志位置为false
    }
}

class HelloThread extends Thread {
    public volatile boolean running = true;
    public void run() {
        int n = 0;
        while (running) {
            n ++;
            System.out.println(n + " hello!");
        }
        System.out.println("end!");
    }
}
```

- **1、java.lang.Thread#interrupt**

  中断目标线程，给目标线程发一个中断信号，线程被打上中断标记。

  **2、java.lang.Thread#isInterrupted()**

  判断目标线程是否被中断，不会清除中断标记。

  **3、java.lang.Thread#interrupted**

  判断目标线程是否被中断，会清除中断标记。

## 十二、AQS(AbstractQueuedSynchronizer)：乐观锁

- `AQS`即是抽象的队列式的同步器，内部定义了很多锁相关的方法，我们熟知的`ReentrantLock`、`ReentrantReadWriteLock`、`CountDownLatch`、`Semaphore`等都是基于`AQS`来实现的。
- `AQS`中 维护了一个`volatile int state`（代表共享资源）和一个`FIFO`线程等待队列（多线程争用资源被阻塞时会进入此队列）。
- state 由于是多线程共享变量，所以必须定义成 volatile，以保证 state 的可见性, 同时虽然 volatile 能保证可见性，但不能保证原子性，所以 AQS 提供了对 state 的原子操作方法，保证了线程安全。
- 另外 AQS 中实现的 FIFO 队列（CLH 队列）其实是双向链表实现的，由 head, tail 节点表示，head 结点代表当前占用的线程，其他节点由于暂时获取不到锁所以依次排队等待锁释放。
- AQS内部维护一个state状态位，尝试加锁的时候通过CAS(CompareAndSwap)修改值，如果成功设置为1，并且把当前线程ID赋值，则代表加锁成功，一旦获取到锁，其他的线程将会被阻塞进入阻塞队列自旋，获得锁的线程释放锁的时候将会唤醒阻塞队列中的线程，释放锁的时候则会把state重新置为0，同时当前线程ID置为空。

```java
public abstract class AbstractQueuedSynchronizer
  extends AbstractOwnableSynchronizer
    implements java.io.Serializable {
    // 以下为双向链表的首尾结点，代表入口等待队列
    private transient volatile Node head;
    private transient volatile Node tail;
    // 共享变量 state
    private volatile int state;
    // cas 获取 / 释放 state，保证线程安全地获取锁
    protected final boolean compareAndSetState(int expect, int update) {
        // See below for intrinsics setup to support this
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }
    // ...
 }
```

![Thread_22](/Users/na/IdeaProjects/Technical summary/Image/Thread_22.webp)

- `Condition`是在`java 1.5`中才出现的，它用来替代传统的`Object`的`wait()`、`notify()`实现线程间的协作，相比使用`Object`的`wait()`、`notify()`，使用`Condition`中的`await()`、`signal()`这种方式实现线程间协作更加安全和高效。因此通常来说比较推荐使用`Condition`

## 十三、**synchronized**：悲观锁

### 1. 应用方式

- synchronized有三种方式来加锁，分别是：方法锁，对象锁synchronized(this)，类锁synchronized(Demo.Class)。其中在方法锁层面可以有如下3种方式：

  1. 修饰实例方法，作用于当前实例加锁，进入同步代码前要获得当前实例的锁

  2. 静态方法，作用于当前类对象加锁，进入同步代码前要获得当前类对象的锁

  3. 修饰代码块，指定加锁对象，对给定对象加锁，进入同步代码库前要获得给定对象的锁。

### 2. **Monitor**

- 什么是Monitor？我们可以把它理解为一个同步工具，也可以描述为一种同步机制。所有的Java对象是天生的Monitor，每个object的对象里 markOop->monitor() 里可以保存ObjectMonitor的对象。
- 对于同步块的实现使用了monitorenter和monitorexit指令：他们隐式的执行了Lock和UnLock操作，用于提供原子性保证。monitorenter指令插入到同步代码块开始的位置、monitorexit指令插入到同步代码块结束位置，jvm需要保证每个monitorenter都有一个monitorexit对应。这两个指令，本质上都是对一个对象的监视器(monitor)进行获取，这个过程是排他的，也就是说同一时刻只能有一个线程获取到由synchronized所保护对象的监视器线程执行到monitorenter指令时，会尝试获取对象所对应的monitor所有权，也就是尝试获取对象的锁；而执行monitorexit，就是释放monitor的所有权。

### 3. **Java对象**内存布局

- 在Hotspot虚拟机中，对象在内存中的布局分为三块区域：对象头、实例数据和对齐填充；Java对象头是实现synchronized的锁对象的基础，一般而言，synchronized使用的锁对象是存储在Java对象头里。它是轻量级锁和偏向锁的关键
- 对象的内存布局：

  - 对象头（Header）包含两部分

    - 运行时元数据
      - 哈希值（ HashCode ）
      - GC分代年龄
      - 锁状态标志
      - 线程持有的锁
      - 偏向线程ID
      - 偏向时间戳
    - 类型指针：指向类元数据的InstanceKlass，确定该对象所属的类型
    - 说明：如果是数组，还需记录数组的长度
  - 实例数据（Instance Data）：它是对象真正存储的有效信息，包括程序代码中定义的各种类型的字段（包括从父类继承下来的和本身拥有的字段） 
  - 对齐填充：仅起占位符作用。虚拟机的内存管理系统要求任何对象的大小必须是 8的倍数，如果没有对齐需要对齐填充补全。

### 4. 锁升级：存储在对象的内存中的对象头的markword中

![CAS_2](/Users/na/IdeaProjects/Technical summary/Image/CAS_2.png)

### 5. 偏向锁

- 偏向锁是指一段同步代码一直被一个线程所访问，那么该线程会自动获取锁。降低获取锁的代价。
- 当一个线程访问同步块并获取锁时，会在对象头和栈帧中的锁记录里存储锁偏向的线程ID，以后该线程在进入和退出同步块时不需要进行CAS操作来加锁和解锁，只需简单地测试一下对象头的Mark Word里是否存储着指向当前线程的偏向锁。如果测试成功，表示线程已经获得了锁。如果测试失败，则需要再测试一下Mark Word中偏向锁的标识是否设置成01（表示当前是偏向锁）：如果没有设置，则使用CAS竞争锁；如果设置了，则尝试使用CAS将对象头的偏向锁指向当前线程。执行同步块。
- 这个时候线程2也来访问同步块，也是会检查对象头的Mark Word里是否存储着当前线程2的偏向锁，发现不是，那么他会进入 CAS 替换，但是此时会替换失败，因为此时线程1已经替换了。替换失败则会进入撤销偏向锁，首先会去暂停拥有了偏向锁的线程1，进入无锁状态(01).偏向锁存在竞争的情况下就回去升级成轻量级锁。

### 6. 轻量级锁

- 轻量级锁是指当锁是偏向锁的时候，被另一个线程所访问，偏向锁就会升级为轻量级锁，其他线程会通过自旋的形式尝试获取锁，不会阻塞，提高性能。
- 在代码进入同步块的时候，如果同步对象锁状态为无锁状态（锁标志位为“01”状态），虚拟机首先将在当前线程的栈帧中建立一个名为锁记录（Lock Record）的空间，用于存储锁对象目前的Mark Word的拷贝。这个时候 线程1会尝试使用 CAS 将 mark Word 更新为指向栈帧中的锁记录（Lock Record）的空间指针。并且把锁标志位设置为 00(轻量级锁标志)，与此同时如果有另外一个线程2也来进行 CAS 修改 Mark Word，那么将会失败，因为线程1已经获取到该锁，然后线程2将会进行 CAS操作不断的去尝试获取锁，这个时候将会引起锁膨胀，就会升级为重量级锁，设置标志位为 10.

### 7. 重量级锁

- 重量级锁是指当锁为轻量级锁的时候，另一个线程虽然是自旋，但自旋不会一直持续下去，当自旋一定次数的时候，还没有获取到锁，就会进入阻塞，该锁膨胀为重量级锁。重量级锁会让其他申请的线程进入阻塞，性能降低。
- 重量级锁通过对象内部的监视器（monitor）实现，其中monitor的本质是依赖于底层操作系统的Mutex Lock实现，操作系统实现线程之间的切换需要从用户态到内核态的切换，切换成本非常高。主要是，当系统检查到锁是重量级锁之后，会把等待想要获得锁的线程进行**阻塞**，被阻塞的线程不会消耗cup。但是阻塞或者唤醒一个线程时，都需要操作系统来帮忙，这就需要从**用户态**转换到**内核态**，而转换状态是需要消耗很多时间的，有可能比用户执行代码的时间还要长。这就是说为什么重量级线程开销很大的。
- synchronized如何实现可重入？每个锁关联一个线程持有者和一个计数器。当计数器为0时表示该锁没有被任何线程持有，那么任何线程都都可能获得该锁而调用相应方法。当一个线程请求成功后，JVM会记下持有锁的线程，并将计数器计为1。此时其他线程请求该锁，则必须等待。而该持有锁的线程如果再次请求这个锁，就可以再次拿到这个锁，同时计数器会递增。当线程退出一个synchronized方法/块时，计数器会递减，如果计数器为0则释放该锁。
- synchronized实际上有两个队列waitSet和entryList：

  1. 当多个线程进入同步代码块时，首先进入entryList
  2. 有一个线程获取到monitor锁后，就赋值给当前线程，并且计数器+1
  3. 如果线程调用wait方法，将释放锁，当前线程置为null，计数器-1，同时进入waitSet等待被唤醒，调用notify或者notifyAll之后又会进入entryList竞争锁
  4. 如果线程执行完毕，同样释放锁，计数器-1，当前线程置为null

![Thread_20](/Users/na/IdeaProjects/Technical summary/Image/Thread_20.webp)

### 8. 说说 synchronized 关键字和 volatile 关键字的区别

- **volatile关键字**是线程同步的**轻量级实现**，所以**volatile性能肯定比synchronized关键字要好**。但是**volatile关键字只能用于变量而synchronized关键字可以修饰方法以及代码块**。synchronized关键字在JavaSE1.6之后进行了主要包括为了减少获得锁和释放锁带来的性能消耗而引入的偏向锁和轻量级锁以及其它各种优化之后执行效率有了显著提升，**实际开发中使用 synchronized 关键字的场景还是更多一些**。
- **多线程访问volatile关键字不会发生阻塞，而synchronized关键字可能会发生阻塞**
- **volatile关键字能保证数据的可见性，但不能保证数据的原子性。synchronized关键字两者都能保证。**
- **volatile关键字主要用于解决变量在多个线程之间的可见性，而 synchronized关键字解决的是多个线程之间访问资源的同步性。**

### 9. synchronized和lock有什么区别？

1. 原始构成

   - synchronized是关键字，属于jvm

     **monitorenter**，底层是通过monitor对象来完成，其实wait/notify等方法也依赖于monitor对象只有在同步或方法中才能掉wait/notify等方法

     **monitorexit**，两次：保证正常退出和异常退出

   - Lock是具体类，是api层面的锁（java.util.）

2. 使用方法

   - sychronized不需要用户取手动释放锁，当synchronized代码执行完后系统会自动让线程释放对锁的占用
   - ReentrantLock则需要用户去手动释放锁若没有主动释放锁，就有可能导致出现死锁现象，需要lock()和unlock()方法配合try/finally语句块来完成

3. 等待是否可中断

   - synchronized不可中断，除非抛出异常或者正常运行完成
   - ReentrantLock可中断，设置超时方法tryLock(long timeout, TimeUnit unit)，或者lockInterruptibly()放代码块中，调用interrupt()方法可中断。

4. 加锁是否公平

   - synchronized非公平锁
   - ReentrantLock两者都可以，默认公平锁，构造方法可以传入boolean值，true为公平锁，false为非公平锁

5. 锁绑定多个条件Condition

   - synchronized没有
   - ReentrantLock用来实现分组唤醒需要要唤醒的线程们，可以精确唤醒，而不是像synchronized要么随机唤醒一个线程要么唤醒全部线程。

### 10. 锁优化

#### 10.1 线程自旋和适应性自旋

- jdk官方人员发现，很多线程在等待锁的时候，在很短的一段时间就获得了锁，所以它们在线程等待的时候，并不需要把线程挂起，而是让他无目的的循环，一般设置10次。这样就避免了线程切换的开销，极大的提升了性能。
- 适应性自旋，是赋予了自旋一种学习能力，它并不固定自旋10次一下。他可以根据它前面线程的自旋情况，从而调整它的自旋，甚至是不经过自旋而直接挂起。

#### 10.2 锁消除

- 把不必要的同步在编译阶段进行移除。
- 通过指针逃逸分析（就是变量不会外泄），我们发现在这段代码并不存在线程安全问题，这个时候就会把这个同步锁消除。

#### 10.3 锁粗化

- 在这一段代码中，每一个append都需要同步一次，那么我可以把锁粗化到第一个append和最后一个append

#### 10.4 锁升级

## 十四、JUC

### 1. Atomic

- 该包下主要是一些原子变量类，仅依赖于Unsafe，并且被其他模块所依赖。

### 2. Locks

- 该包下主要是关于锁及其相关类，仅依赖于Unsafe或内部依赖，并且被其他高级模块所依赖。由于LockSupport类底层逻辑简单且仅依赖Unsafe，同时为其他高级模块所依赖，所以需要先了解LockSupport类的运行原理，然后重点研究AbstractQueuedSynchronizer框架，理解独占锁和共享锁的实现原理，并清楚Condition如何与AbstractQueuedSynchronizer进行协作，最后很容易就能理解ReentrantLock是如何实现的。

### 3. Collections

- 该包会依赖Unsafe和前两个基础模块，并且模块内部各个容器间相互较为独立，所以没有固定的学习顺序，理解编程中常用的集合类原理即可：ConcurrentHashMap、CopyOnWriteArrayList、CopyOnWriteArraySet、ArrayBlockingQueue、LinkedBlockingQueue（阻塞队列在线程池中有使用，所以理解常用阻塞队列的特性很重要）。

### 4. Executor

- 这一部分的核心是线程池的运行原理，也是实际应用中较多的部分，会依赖于前几个模块。首先了解Callable、Future、RunnableFuture三个接口间的关系以及FutureTask的实现原理，然后研究如何创建ThreadPoolExecutor，如何运行一个任务，如何管理自身的线程，同时了解RejectedExecutionHandler的四种实现差异，最后，在实际应用中学习如何通过调整ThreadPoolExecutor的参数来优化线程池。

### 5. Tools

- 这一部分是以前面几个模块为基础的高级特性模块，实际应用的场景相对较少，主要应用在多线程间相互依赖执行结果场景，没有具体的学习顺序，最好CountDownLatch、CyclicBarrier、Semaphore、Exchanger、Executors都了解下，对后面学习Guava的框架有帮助。

## 十五、SimpleDateFormat

- 声明SimpleDateFormat为static变量，那么它的Calendar变量也就是一个共享变量，可以被多个线程访问
- 假设线程A执行完calendar.setTime(date)，把时间设置成2019-01-02，这时候被挂起，线程B获得CPU执行权。线程B也执行到了calendar.setTime(date)，把时间设置为2019-01-03。线程挂起，线程A继续走，calendar还会被继续使用(subFormat方法)，而这时calendar用的是线程B设置的值了，而这就是引发问题的根源，出现时间不对，线程挂死等等。
- java.time.format.DateTimeFormatter是jdk新加入的日期格式化工具类，解决了SimpleDateFormat的线程安全问题，如果使用的是jdk8以上，强烈推荐使用。

![](https://pic4.zhimg.com/80/v2-8999628e3872222be7bd61371b8b7f77_720w.jpg)

## 十六、三个线程按顺序轮流打印n个数

```java
public class Main {

    public static void main(String[] args){
        new Thread(new Printer(0)).start();
        new Thread(new Printer(1)).start();
        new Thread(new Printer(2)).start();
    }

}

class Printer implements Runnable{
    //打印的数字，初始值
    static int num = 1;
    //最大值
    static final int MAX = 15;
    //线程id
    int id = 0;

    public Printer(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        synchronized (Printer.class) {
            while(num <= MAX) {
                //每行第一个数字与线程id对应关系
                if(num/5%3 == id) {
                    System.out.print("线程" + id + "打印：");
                    for(int i=0; i<5; i++) {
                        System.out.print(num++ + ",");
                    }
                    System.out.println();
                    //打印完数据，唤醒Printer.class等待队列上的线程
                    Printer.class.notifyAll();
                    continue;
                }
                //如果不属于自己的数，放弃CPU，挂在Printer.class对象的等待队列
                try {
                    Printer.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class Printer implements Runnable{
    //打印的数字，初始值
    private static volatile int num = 1;
    //线程id
    private int id;

    public Printer(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        synchronized (Printer.class) {
            while(num <= 60) {
                //每行第一个数字与线程id对应关系
                if(num / 5 % 3 == id) {
                    System.out.print("线程" + id + "打印：");
                    for(int i=0; i<5; i++) {
                        System.out.print(num++ + ",");
                    }
                    System.out.println();
                    //打印完数据，唤醒Printer.class等待队列上的线程
                    Printer.class.notifyAll();
                }else {
                    //如果不属于自己的数，放弃CPU，挂在Printer.class对象的等待队列
                    try {
                        Printer.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
```

