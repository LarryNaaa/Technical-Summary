# Threads

Threads allows a program to operate more efficiently by doing multiple things at the same time.

Threads can be used to perform complicated tasks in the background without interrupting the main program.

## Thread creation by extending the Thread class

![Thread](/Users/na/IdeaProjects/Technical summary/Image/Thread.png)

We create a class that extends the java.lang.Thread class. This class overrides the run() method available in the Thread class. A thread begins its life inside run() method. We create an object of our new class and call start() method to start the execution of a thread. Start() invokes the run() method on the Thread object.

```java
class MultithreadingDemo extends Thread 
{ 
    public void run() 
    { 
        try
        { 
            // Displaying the thread that is running 
            System.out.println ("Thread " + 
                  Thread.currentThread().getId() + 
                  " is running"); 
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    } 
} 
  
// Main Class 
public class Multithread 
{ 
    public static void main(String[] args) 
    { 
        int n = 8; // Number of threads 
        for (int i=0; i<n; i++) 
        { 
            MultithreadingDemo object = new MultithreadingDemo(); 
            object.start(); 
        } 
    } 
} 
```

## Thread creation by implementing the Runnable Interface
We create a new class which implements java.lang.Runnable interface and override run() method. Then we instantiate a Thread object and call start() method on this object.

```java
class MultithreadingDemo implements Runnable 
{ 
    public void run() 
    { 
        try
        { 
            // Displaying the thread that is running 
            System.out.println ("Thread " + 
                                Thread.currentThread().getId() + 
                                " is running"); 
  
        } 
        catch (Exception e) 
        { 
            // Throwing an exception 
            System.out.println ("Exception is caught"); 
        } 
    } 
} 
  
// Main Class 
class Multithread 
{ 
    public static void main(String[] args) 
    { 
        int n = 8; // Number of threads 
        for (int i=0; i<n; i++) 
        { 
            Thread object = new Thread(new MultithreadingDemo()); 
            object.start(); 
        } 
    } 
} 
```

## What is the difference between Thread Class and Runnable Interface?
1. If we extend the Thread class, our class cannot extend any other class because Java doesn’t support multiple inheritance. But, if we implement the Runnable interface, our class can still extend other base classes.

2. We can achieve basic functionality of a thread by extending Thread class because it provides some inbuilt methods like yield(), interrupt() etc. that are not available in Runnable interface.

## Lifecycle and States of a Thread
![States of a Thread](https://pic2.zhimg.com/v2-54ad049834f12e2f839f14c51fab3299_b.jpg)

### New State
**Thread state for a thread which has not yet started.**

When a new thread is created, it is in the new state. The thread has not yet started to run when thread is in this state. When a thread lies in the new state, it’s code is yet to be run and hasn’t started to execute.

### Runnable State
**A thread in the runnable state is executing in the Java virtual machine but it may be waiting for other resources from the operating system such as processor.**

A thread that is ready to run is moved to runnable state. In this state, a thread might actually be running or it might be ready run at any instant of time. It is the responsibility of the thread scheduler to give the thread, time to run.
A multi-threaded program allocates a fixed amount of time to each individual thread. Each and every thread runs for a short while and then pauses and relinquishes the CPU to another thread, so that other threads can get a chance to run. When this happens, all such threads that are ready to run, waiting for the CPU and the currently running thread lies in runnable state.

### Blocked/Waiting State
**A thread in the blocked state is waiting for a monitor lock to enter a synchronized block/method or reenter a synchronized block/method after calling Object.wait().**

**A thread is in the waiting state due to calling one of the following methods:**

> + Object.wait with no timeout
> + Thread.join with no timeout
> + LockSupport.park

**A thread in the waiting state is waiting for another thread to perform a particular action.**

When a thread is temporarily inactive, then it’s in one of these two states. For example, when a thread is waiting for I/O to complete, it lies in the blocked state. It’s the responsibility of the thread scheduler to reactivate and schedule a blocked/waiting thread. A thread in this state cannot continue its execution any further until it is moved to runnable state. Any thread in these states does not consume any CPU cycle.

A thread is in the blocked state when it tries to access a protected section of code that is currently locked by some other thread. When the protected section is unlocked, the schedule picks one of the thread which is blocked for that section and moves it to the runnable state. Whereas, a thread is in the waiting state when it waits for another thread on a condition. When this condition is fulfilled, the scheduler is notified and the waiting thread is moved to runnable state.

If a currently running thread is moved to blocked/waiting state, another thread in the runnable state is scheduled by the thread scheduler to run. It is the responsibility of thread scheduler to determine which thread to run.

### Timed Waiting State
**Thread state for a waiting thread with a specified waiting time. A thread is in the timed waiting state due to calling one of the following methods with a specified positive waiting time:**
> + Thread.sleep
> + Object.wait with timeout
> + Thread.join with timeout
> + LockSupport.parkNanos
> + LockSupport.parkUntil

A thread lies in timed waiting state when it calls a method with a time out parameter. A thread lies in this state until the timeout is completed or until a notification is received. For example, when a thread calls sleep or a conditional wait, it is moved to a timed waiting state.

### Terminated State
**Thread state for a terminated thread. The thread has completed execution.**

A thread terminates because of either of the following reasons:

1. Because it exists normally. This happens when the code of thread has entirely executed by the program.
2. Because there occurred some unusual erroneous event, like segmentation fault or an unhandled exception.

A thread that lies in a terminated state does no longer consumes any cycles of CPU.

## Methods of Thread
### run()
If this thread was constructed using a separate Runnable run object, then that Runnable object’s run method is called; otherwise, this method does nothing and returns

### start()
Causes this thread to begin execution; the Java Virtual Machine calls the run method of this thread

### wait()
The thread releases ownership of this monitor and waits until another thread notifies threads waiting on this object's monitor to wake up either through a call to the notify() method or the notifyAll() method. The thread then waits until it can re-obtain ownership of the monitor and resumes execution.

### sleep()
This method causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds. The thread does not lose ownership of any monitors. It sends the current thread into the “Not Runnable” state for a specified amount of time.

### What is the difference between sleep() and wait() method?
1. Wait() method belongs to Object class, but Sleep() method belongs to Thread class.
2. Wait() releases the lock on an object, but Sleep() does not.
3. Wait() can be called on object itself, but Sleep() can be called on thread.
4. Wait() will wake up until call notify(), notifyAll() from object, Sleep() will wake up until at least time expire or call interrupt
5. Wait() can lead program to get spurious wakeups, but Sleep() cannot.

### yield()
The thread is not doing anything particularly important and if any other threads or processes need to be run, they should run. Otherwise, the current thread will continue to run.

### What is the difference between sleep() and yield() method?
yield:() indicates that the thread is not doing anything particularly important and if any other threads or processes need to be run, they can. **Otherwise, the current thread will continue to run.**

sleep(): causes the thread to definitely stop executing for a given amount of time; **if no other thread or process needs to be run, the CPU will be idle (and probably enter a power saving mode).**

### join()
java.lang.Thread class provides the join() method which allows one thread to wait until another thread completes its execution. If join() is called on a Thread instance, the currently running thread will block until the Thread instance has finished executing.


## synchronized
All synchronized blocks synchronized on the same object can only have one thread executing inside them at a time. All other threads attempting to enter the synchronized block are blocked until the thread inside the synchronized block exits the block.

**We can use synchronized keyword in the class on defined methods or blocks.** Synchronized keyword can not be used with variables or attributes in class definition.

```java
// Only one thread can execute at a time. 
// sync_object is a reference to an object
// whose lock associates with the monitor. 
// The code is said to be synchronized on
// the monitor object
synchronized(sync_object)
{
   // Access shared variables and other
   // shared resources
}
```

This synchronization is implemented in Java with a concept called monitors. Only one thread can own a monitor at a given time. When a thread acquires a lock, it is said to have entered the monitor. All other threads attempting to enter the locked monitor will be suspended until the first thread exits the monitor.

When a method is declared as synchronized; the thread holds the monitor or lock object for that method’s object. If another thread is executing the synchronized method, your thread is blocked until that thread releases the monitor.

### Object level lock and Class level lock
#### Object level lock
Object level lock is mechanism when we want to **synchronize a non-static method or non-static code block** such that only one thread will be able to execute the code block on given instance of the class. This should always be done to make instance level data thread safe.

```java
public class DemoClass
{
    public synchronized void demoMethod(){}
}
 
or
 
public class DemoClass
{
    public void demoMethod(){
        synchronized (this)
        {
            //other thread safe code
        }
    }
}
 
or
 
public class DemoClass
{
    private final Object lock = new Object();
    public void demoMethod(){
        synchronized (lock)
        {
            //other thread safe code
        }
    }
}
```
#### Class level lock
Class level lock prevents multiple threads to enter in synchronized block in any of all available instances of the class on runtime. This means if in runtime there are 100 instances of DemoClass, then only one thread will be able to execute demoMethod() in any one of instance at a time, and all other instances will be locked for other threads.

Class level locking should always be done to make static data thread safe. As we know that static keyword associate data of methods to class level, so use locking at static fields or methods to make it on class level.

```java
public class DemoClass
{
    //Method is static
    public synchronized static void demoMethod(){
 
    }
}
 
or
 
public class DemoClass
{
    public void demoMethod()
    {
        //Acquire lock on .class reference
        synchronized (DemoClass.class)
        {
            //other thread safe code
        }
    }
}
 
or
 
public class DemoClass
{
    private final static Object lock = new Object();
 
    public void demoMethod()
    {
        //Lock object is static
        synchronized (lock)
        {
            //other thread safe code
        }
    }
}
```


