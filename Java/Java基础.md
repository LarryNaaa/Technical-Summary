# Java基础

## equals 和 hashcode

```java
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        Coder c1 = new Coder("zhangsan",10);
        Coder c2 = new Coder("zhangsan", 10);

        System.out.println(c1.equals(c2));

        System.out.println("c1: " + c1.hashCode()+ "c2: " + c2.hashCode());

        System.out.println(c1.hashCode() == c2.hashCode());

        Set<Coder> set = new HashSet<>();
        set.add(c1);
        System.out.println(set.contains(c2));;


    }


}

class Coder{
    String name;
    int age;

    Coder(String name, int age){
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }else if(obj instanceof Coder){
            Coder coder = (Coder) obj;
            return coder.name.equals(this.name) && coder.age == this.age;
        }else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + name.hashCode();
        result = result * 31 + age;

        return result;
    }
}
```



## 深拷贝和浅拷贝

```java
public class ShallowCopy {
    public static void main(String[] args) throws CloneNotSupportedException {
        Student s1 = new Student("张三", 12);
        Teacher t1 = new Teacher("赵老师", 30, s1);
        Teacher t2 = (Teacher) t1.clone();

        System.out.println(t1.toString());
        System.out.println(t2.toString());

        s1.name = "李四";

        System.out.println(t1.toString());
        System.out.println(t2.toString());
    }
}

class Student implements Cloneable{
    String name;
    int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Teacher implements Cloneable{
    String name;
    int age;
    Student student;

    Teacher(String name, int age, Student student){
        this.name = name;
        this.age = age;
        this.student = student;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // return super.clone();
        Teacher t = (Teacher) super.clone();
        t.student = (Student) t.student.clone();
        return t;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", student=" + student +
                '}';
    }
}
```

## 内部类和静态内部类

```java
public class Outer {
    String n1 = "外部类实例变量";
    static String n2 = "外部类类变量";
    public static void main(String[] args) {
        StaticInner staticInner = new StaticInner();
        staticInner.f1();

        Outer outer = new Outer();
        Inner inner = outer.new Inner();
        inner.f1();
    }

    public static class StaticInner{
        String name1 = "静态内部类";
        String name2 = n2;

        public void f1(){
            System.out.println("我是静态内部类");
        }
    }

    public class Inner{
        String name1 = n1;
        String name2 = n2;

        public void f1(){
            System.out.println("我是内部类");
        }
    }
}
```

## Comparable和Comparater

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        User u1 = new User("zhangsan", 23, 80);
        User u2 = new User("lisi", 23, 80);
        User u3 = new User("wangwu", 19, 100);

        List<User> list = new ArrayList<>();
        list.add(u1);
        list.add(u2);
        list.add(u3);

        Collections.sort(list);

//        Collections.sort(list, (a, b)-> {
//            if(a.grade == b.grade){
//                if(a.age == b.age){
//                    return a.name.compareTo(b.name);
//                }
//                return b.age - a.age;
//            }
//            return b.grade - a.grade;
//        });

//        Collections.sort(list, new Comparator<User>() {
//            @Override
//            public int compare(User a, User b) {
//                if(a.grade == b.grade){
//                    if(a.age == b.age){
//                        return a.name.compareTo(b.name);
//                    }
//                    return b.age - a.age;
//                }
//                return b.grade - a.grade;
//            }
//        });

        System.out.println(list.toString());
    }
}

class User implements Comparable{
    String name;
    int age;
    int grade;

    public User(String name, int age, int grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    @Override
    public int compareTo(Object obj) {
        User o = (User) obj;
        if(this.grade == o.grade){
            if(this.age == o.age){
                return this.name.compareTo(o.name);
            }
            return o.age - this.age;
        }
        return o.grade - this.grade;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", grade=" + grade +
                '}';
    }

}
```

