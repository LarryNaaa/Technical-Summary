# CAS

![CAS_1](/Users/na/IdeaProjects/Technical summary/Image/CAS_1.png)

## 对象的内存布局

### 对象头（Header）

包含两部分

- 运行时元数据(markword 8字节)
  - 哈希值（ HashCode ）
  - GC分代年龄：占四个bit，gc分代回收年龄阈值最大15，cms默认6
  - 锁状态标志
  - 线程持有的锁
  - 偏向线程ID
  - 偏向时间戳
- 类型指针(class pointer 4字节)：指向类元数据的InstanceKlass，确定该对象所属的类型
- 说明：如果是数组，还需记录数组的长度

### 实例数据（Instance Data）

说明：它是对象真正存储的有效信息，包括程序代码中定义的各种类型的字段（包括从父类继承下来的和本身拥有的字段） 规则：

- 相同宽度的字段总被分配在一起
- 父类中定义的变量会出现在子类之前
- 如果CompactFields参数为true（默认为true），子类的窄变量可能插入到父类变量的空隙

### 对齐填充（Padding）

不是必须的，也没特别含义，仅仅起到占位符作用，CPU读取对象时，对象的整体字节数需要被8整除，如果不能则对其填充，提高效率

![创建对象的方式_1](/Users/na/IdeaProjects/Technical summary/Image/创建对象的方式_1.webp)

## Object 0 = new Object()在内存中占多少个字节？

对象头占12个字节(markword占8个，class pointer占4个)，实例数据如果没有成员变量占0个字节(如果有另算，如int占4个字节，引用类型String占4个字节)，对其填充占4个(确保对象的整体字节数是8的倍数)，引用是压缩class pointer占4个字节(JVM自动开启，如果关闭则占8个字节)

## 锁-->markword

![CAS_2](/Users/na/IdeaProjects/Technical summary/Image/CAS_2.png)

流程总结：https://www.bilibili.com/video/BV1xK4y1C7aT?p=4&spm_id_from=pageDriver P4 22-23min开始

## synchronized实现过程

1. java代码：synchronized
2. 字节码：monitorenter monitorexit
3. JVM执行过程中自动升级锁
4. 汇编：lock compxchg(compare and exchange)

## volatile

![CAS_9](/Users/na/IdeaProjects/Technical summary/Image/CAS_9.png)

![CAS_7](/Users/na/IdeaProjects/Technical summary/Image/CAS_7.png)

![CAS_8](/Users/na/IdeaProjects/Technical summary/Image/CAS_8.png)

## ThreadLocal

![CAS_11](/Users/na/IdeaProjects/Technical summary/Image/CAS_11.png)

![CAS_12](/Users/na/IdeaProjects/Technical summary/Image/CAS_12.png)

使用完ThreadLocal后需手动回收ThreadLocal对象！

WeakHashMap



![CAS_4](/Users/na/IdeaProjects/Technical summary/Image/CAS_4.png)

![CAS_5](/Users/na/IdeaProjects/Technical summary/Image/CAS_5.png)

![CAS_6](/Users/na/IdeaProjects/Technical summary/Image/CAS_6.png)

