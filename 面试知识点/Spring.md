# Spring

[TOC]

## 1. IoC - Inversion of Control

-  控制反转IoC(Inversion of Control)，是一种设计思想，DI(依赖注入)是实现IoC的一种方法。没有IoC的程序中 , 我们使用面向对象编程 , 对象的创建与对象间的依赖关系完全硬编码在程序中，对象的创建由程序自己控制，控制反转后将对象的创建转移给第三方，个人认为所谓控制反转就是：获得依赖对象的方式反转了。
-  **IoC将原本在程序中手动创建对象的控制权，交由Spring框架来管理。** **IoC 容器是 Spring 用来实现 IoC 的载体， IoC 容器实际上就是个Map（bean name，bean object）,Map 中存放的是各种对象。**
-  **IOC 容器就像是一个工厂一样，当我们需要创建一个对象的时候，只需要配置好配置文件/注解即可，完全不用考虑对象是如何被创建出来的。**
- **Spring IoC的初始化过程：**
- 1. **Resource**定位并加载配置文件
  
  2. 解析配置文件中的bean节点，一个bean节点对应一个BeanDefinition对象（这个对象会保存我们在Bean节点内配置的所有内容，比如id，全限定类名，依赖值等等）
  
  3. BeanDefinition集合生成（BeanDefinition对象内包含生成这个对象所需要的所有参数）所有非懒加载的单例对象，其余的会在使用的时候再实例化对应的对象，注册到**BeanFactory**的beanDefinitionMap

![Spring_1](/Users/na/IdeaProjects/Technical summary/Image/Spring_1.png)

  - IOC其实就是工厂模式+Java的反射机制
  - **DI—Dependency Injection，即“依赖注入”**：**组件之间依赖关系**由容器在运行期决定，形象的说，即**由容器动态的将某个依赖关系注入到组件之中**。**依赖注入的目的并非为软件系统带来更多功能，而是为了提升组件重用的频率，并为系统搭建一个灵活、可扩展的平台。**通过依赖注入机制，我们只需要通过简单的配置，而无需任何代码就可指定目标需要的资源，完成自身的业务逻辑，而不需要关心具体的资源来自何处，由谁实现。
  - Spring IoC 的容器构建流程

![](https://pic2.zhimg.com/80/v2-0f3589f313ed34f47c4d8ed132e84925_720w.jpg)

## 2. Spring AOP

- 将业务模块所共同调用的非业务逻辑（例如事务处理、日志管理、权限控制等）封装起来**，便于**减少重复代码**，**降低模块间的耦合度**，并**有利于未来的可拓展性和可维护性。

- 本质是通过动态代理来实现的，主要有以下几个步骤：

  1、获取增强器，例如被 Aspect 注解修饰的类。

  2、在创建每一个 bean 时，会检查是否有增强器能应用于这个 bean，简单理解就是该 bean 是否在该增强器指定的 execution 表达式中。如果是，则将增强器作为拦截器参数，使用动态代理创建 bean 的代理对象实例。

  3、当我们调用被增强过的 bean 时，就会走到代理类中，从而可以触发增强器，本质跟拦截器类似。

- **Spring AOP就是基于动态代理的**，如果要代理的对象，实现了某个接口，那么Spring AOP会使用**JDK Proxy**，去创建代理对象，只能对实现了接口的类生成代理的原因是通过 JDK 动态代理生成的类已经继承了 Proxy 类，所以无法再使用继承的方式去对类实现代理；而对于没有实现接口的对象，Spring AOP会使用**Cglib** ，这时候Spring AOP会使用 **Cglib** 通过**继承目标对象产生代理子对象**，代理子对象中继承了目标对象的方法，并可以对该方法进行增强。

- **Join Point(连接点)**：Java执行流中的每个方法调用可以看成是一个连接点。
- **切入点(Point Cut)**：**从所有的连接点中挑选需要被切入的切入点。**

![Spring_2](/Users/na/IdeaProjects/Technical summary/Image/Spring_2.jpg)

- Java程序执行流加入了代理模式，使得所有的方法调用都经过了代理对象。Spring AOP负责控制着整个容器内部的代理对象。

- 代理对象给Spring AOP提供类名和方法签名，Spring AOP 根据代理对象提供的类型名和方法签名，搜索对应的切入点，返回处理建议(Advice)，代理对象根据处理建议执行操作。

- AOP中的五大通知：

  前置通知（Before advice）：在目标方法执行之前执行

  后置通知（after）：在目标方执行完成后执行，如果目标方法异常，则后置通知不再执行

  异常通知（After throwing advice）：目标方法抛出异常的时候执行  

  最终通知（After returning advice）：在目标方法返回结果后运行，不管目标方法是否有异常都会执行，相当于try。。catch。。finally中的finally

  环绕通知（round）：可以控制目标方法是否执行

![Spring_3](/Users/na/IdeaProjects/Technical summary/Image/Spring_3.jpg)

### 2.1 AOP 代理失效

(1) 在一个类内部调用时，被调用方法的 AOP 声明将不起作用。

(2) 对于基于接口动态代理的 AOP 事务增强来说，由于接口的方法都必然是 public ，这就要求实现类的实现方法也必须是 public 的（不能是 protected、private 等），同时不能使用 static 的修饰符。所以，可以实施接口动态代理的方法只能是使用 public 或 public final 修饰符的方法，其他方法不可能被动态代理，相应的也就不能实施 AOP 增强，换句话说，即不能进行 Spring 事务增强了。

(3) 基于 CGLib 字节码动态代理的方案是通过扩展被增强类，动态创建其子类的方式进行 AOP 增强植入的。由于使用 final、static、private 修饰符的方法都不能被子类覆盖，这些方法将无法实施 AOP 增强。所以方法签名必须特别注意这些修饰符的使用，以免使方法不小心成为事务管理的漏网之鱼。

(4) 该例中的方法符合上述条件，但注解仍然失效，主要原因是在于同一类中的方法互相调用，调用者指向当前对象，所以无论是接口代理还是 cglib 代理都无法织入增强实现。

> 解决方法：
>
> 1. 将需要进行AOP管理的方法，在独立的类中定义，即可解决此类问题。在特定的情况，可以定独立的事务层
> 2. 强制将AOP对象进行设置，在内部方法调用中，通过AopContext 获取代理对象。本质是在AopContext中存放了代理对象，代码对象是放存在threadlocal中的。AopContext的实现如下： (1).在进入代理对象之后通过AopContext.serCurrentProxy(proxy)暴露当前代理对象到ThreadLocal，并保存上次ThreadLocal绑定的代理对象为oldProxy。 (2).接下来我们可以通过 AopContext.currentProxy() 获取当前代理对象。 (3).在退出代理对象之前要重新将ThreadLocal绑定的代理对象设置为上一次的代理对象，即 AopContext.serCurrentProxy(oldProxy)。

## 3. Spring AOP 和 AspectJ AOP 有什么区别？

- **Spring AOP 属于动态代理，运行时增强，而 AspectJ 是静态代理，编译时增强。** Spring AOP 基于代理(Proxying)，而 AspectJ 基于字节码操作(Bytecode Manipulation)。

## 4. Spring 中的 bean 的作用域有哪些?

- singleton : 唯一 bean 实例，Spring 中的 bean 默认都是单例的。
- prototype : 每次请求都会创建一个新的 bean 实例。
- request : 每一次HTTP请求都会产生一个新的bean，该bean仅在当前HTTP request内有效。
- session : 每一次HTTP请求都会产生一个新的 bean，该bean仅在当前 HTTP session 内有效。
- global-session：全局session作用域，仅仅在基于portlet的web应用中才有意义，Spring5已经没有了。Portlet是能够生成语义代码(例如：HTML)片段的小型Java Web插件。它们基于portlet容器，可以像servlet一样处理HTTP请求。但是，与 servlet 不同，每个 portlet 都有不同的会话

## 5. Spring 中的单例 bean 的线程安全问题了解吗？

- 常见的有两种解决办法：
  1. 在Bean对象中尽量避免定义可变的成员变量（不太现实）。
  2. 在类中定义一个ThreadLocal成员变量，将需要的可变成员变量保存在 ThreadLocal 中（推荐的一种方式）。

## 6. Spring 中的 bean 生命周期?

- Spring容器 从XML 文件中读取bean的定义，并实例化bean。
- Spring根据bean的定义填充所有的属性。
- 如果这个Bean实现了BeanNameAware接口，会调用它实现的setBeanName(String beanId)方法，此处传递的是Spring配置文件中Bean的ID
- 如果这个Bean实现了BeanFactoryAware接口，会调用它实现的setBeanFactory()，传递的是Spring工厂本身（可以用这个方法获取到其他Bean）
- 如果这个Bean实现了ApplicationContextAware接口，会调用setApplicationContext(ApplicationContext)方法，传入Spring上下文，该方式同样可以实现步骤4，但比4更好，以为ApplicationContext是BeanFactory的子接口，有更多的实现方法
- 如果有和加载这个 Bean 的 Spring 容器相关的 `BeanPostProcessor` 对象，执行`postProcessBeforeInitialization()` 方法
- 如果Bean实现了`InitializingBean`接口，执行`afterPropertiesSet()`方法。
- 如果 Bean 在配置文件中的定义包含 init-method 属性，执行指定的方法。
- 如果有和加载这个 Bean的 Spring 容器相关的 `BeanPostProcessor` 对象，执行`postProcessAfterInitialization()` 方法
- 经过以上的工作后，Bean将一直驻留在应用上下文中给应用使用，直到应用上下文被销毁
- 当要销毁 Bean 的时候，如果 Bean 实现了 `DisposableBean` 接口，执行 `destroy()` 方法。
- 当要销毁 Bean 的时候，如果 Bean 在配置文件中的定义包含 destroy-method 属性，执行指定的方法。

![Spring_5](https://upload-images.jianshu.io/upload_images/18992122-3c6aed76b93108f2.png?imageMogr2/auto-orient/strip|imageView2/2/format/webp)

## 7. 说说自己对于 Spring MVC 了解?

- 客户端（浏览器）发送请求，直接请求到 `DispatcherServlet`。
- `DispatcherServlet` 根据请求信息调用 `HandlerMapping`，解析请求对应的 `Handler`。
- 解析到对应的 `Handler`（也就是我们平常说的 `Controller` 控制器）后，开始由 `HandlerAdapter` 适配器处理。
- `HandlerAdapter` 会根据 `Handler`来调用真正的处理器开处理请求，并处理相应的业务逻辑。
- 处理器处理完业务后，会返回一个 `ModelAndView` 对象，`Model` 是返回的数据对象，`View` 是个逻辑上的 `View`。
- `ViewResolver` 会根据逻辑 `View` 查找实际的 `View`。
- `DispaterServlet` 把返回的 `Model` 传给 `View`（视图渲染）。
- 把 `View` 返回给请求者（浏览器）

## 8. Spring 框架中用到了哪些设计模式？

- **工厂设计模式** : Spring使用工厂模式通过 `BeanFactory`、`ApplicationContext` 创建 bean 对象。
- **代理设计模式** : Spring AOP 功能的实现。
- **单例设计模式** : Spring 中的 Bean 默认都是单例的。
- **模板方法模式** : Spring 中 `jdbcTemplate`、`hibernateTemplate` 等以 Template 结尾的对数据库操作的类，它们就使用到了模板模式。
- **包装器设计模式** : 我们的项目需要连接多个数据库，而且不同的客户在每次访问中根据需要会去访问不同的数据库。这种模式让我们可以根据客户的需求能够动态切换不同的数据源。
- **观察者模式:** Spring 事件驱动模型就是观察者模式很经典的一个应用。
- **适配器模式** :Spring AOP 的增强或通知(Advice)使用到了适配器模式、spring MVC 中也是用到了适配器模式适配`Controller`。

## 9. @Component 和 @Bean 的区别是什么？

- 作用对象不同: `@Component` 注解作用于类，而`@Bean`注解作用于方法。
- @Component注解表明一个类会作为组件类，并告知Spring要为这个类创建bean。
- @Bean注解告诉Spring这个方法将会返回一个对象，这个对象要注册为Spring应用上下文中的bean。通常方法体中包含了最终产生bean实例的逻辑。
- `@Component`通常是通过类路径扫描来自动侦测以及自动装配到Spring容器中（我们可以使用 `@ComponentScan` 注解定义要扫描的路径从中找出标识了需要装配的类自动装配到 Spring 的 bean 容器中）。`@Bean` 注解通常是我们在标有该注解的方法中定义产生这个 bean,`@Bean`告诉了Spring这是某个类的示例，当我需要用它的时候还给我。
- `@Bean` 注解比 `Component` 注解的自定义性更强，而且很多地方我们只能通过 `@Bean` 注解来注册bean。比如当我们引用第三方库中的类需要装配到 `Spring`容器时，则只能通过 `@Bean`来实现。

## 10. 将一个类声明为Spring的 bean 的注解有哪些?

我们一般使用 `@Autowired` 注解自动装配 bean，要想把类标识成可用于 `@Autowired`注解自动装配的 bean 的类,采用以下注解可实现：

- `@Component` ：通用的注解，可标注任意类为 `Spring` 组件。如果一个Bean不知道属于拿个层，可以使用`@Component` 注解标注。
- `@Repository` : 对应持久层即 Dao 层，主要用于数据库相关操作。
- `@Service` : 对应服务层，主要涉及一些复杂的逻辑，需要用到 Dao层。
- `@Controller` : 对应 Spring MVC 控制层，主要用户接受用户请求并调用 Service 层返回数据给前端页面。

## 11. Spring 管理事务的方式有几种？

- 编程式事务，在代码中硬编码。(不推荐使用)
- 声明式事务，在配置文件中配置（推荐使用）

**声明式事务又分为两种：**

1. 基于XML的声明式事务
2. 基于注解的声明式事务

## 12. Spring 事务中的隔离级别有哪几种?

**TransactionDefinition 接口中定义了五个表示隔离级别的常量：**

- **TransactionDefinition.ISOLATION_DEFAULT:** 使用后端数据库默认的隔离级别，Mysql 默认采用的 REPEATABLE_READ隔离级别 Oracle 默认采用的 READ_COMMITTED隔离级别.
- **TransactionDefinition.ISOLATION_READ_UNCOMMITTED:** 最低的隔离级别，允许读取尚未提交的数据变更，**可能会导致脏读、幻读或不可重复读**
- **TransactionDefinition.ISOLATION_READ_COMMITTED:** 允许读取并发事务已经提交的数据，**可以阻止脏读，但是幻读或不可重复读仍有可能发生**
- **TransactionDefinition.ISOLATION_REPEATABLE_READ:** 对同一字段的多次读取结果都是一致的，除非数据是被本身事务自己所修改，**可以阻止脏读和不可重复读，但幻读仍有可能发生。**
- **TransactionDefinition.ISOLATION_SERIALIZABLE:** 最高的隔离级别，完全服从ACID的隔离级别。所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，**该级别可以防止脏读、不可重复读以及幻读**。但是这将严重影响程序的性能。通常情况下也不会用到该级别。

## 13. Spring 事务中哪几种事务传播行为?

- 1）REQUIRED：Spring 默认的事务传播级别，如果上下文中已经存在事务，那么就加入到事务中执行，如果当前上下文中不存在事务，则新建事务执行。

  2）REQUIRES_NEW：每次都会新建一个事务，如果上下文中有事务，则将上下文的事务挂起，当新建事务执行完成以后，上下文事务再恢复执行。
  
  3）SUPPORTS：如果上下文存在事务，则加入到事务执行，如果没有事务，则使用非事务的方式执行。

  4）MANDATORY：上下文中必须要存在事务，否则就会抛出异常。

  5）NOT_SUPPORTED ：如果上下文中存在事务，则挂起事务，执行当前逻辑，结束后恢复上下文的事务。
  
  6）NEVER：上下文中不能存在事务，否则就会抛出异常。

  7）NESTED：嵌套事务。如果上下文中存在事务，则嵌套事务执行，如果不存在事务，则新建事务。

- **支持当前事务的情况：**

  - **TransactionDefinition.PROPAGATION_REQUIRED：** 如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。**在外围方法未开启事务的情况下`Propagation.REQUIRED`修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。** **在外围方法开启事务的情况下`Propagation.REQUIRED`修饰的内部方法会加入到外围方法的事务中，所有`Propagation.REQUIRED`修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚。**
  - **TransactionDefinition.PROPAGATION_SUPPORTS：** 支持当前事务，如果当前没有事务，就以非事务方式执行。外部方法没事务，内部方法也没事务；外部方法有事物，内部方法也有事务。
  - **TransactionDefinition.PROPAGATION_MANDATORY：** 如果当前存在事务，则加入该事务；如果当前不存在事务，则抛出异常。一定要在事务中去调用，否则会抛出异常。

  **不支持当前事务的情况：**

  - **TransactionDefinition.PROPAGATION_REQUIRES_NEW：** 新建事务，如果当前存在事务，把当前事务延缓。**在外围方法未开启事务的情况下`Propagation.REQUIRES_NEW`修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。** **在外围方法开启事务的情况下`Propagation.REQUIRES_NEW`修饰的内部方法依然会单独开启独立事务，且与外部方法事务也独立，内部方法之间、内部方法和外部方法事务均相互独立，互不干扰。**
  - **TransactionDefinition.PROPAGATION_NOT_SUPPORTED：** 以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。NOT_SUPPORTED修饰的方法其本身是没有事务的。
  - **TransactionDefinition.PROPAGATION_NEVER：** 以非事务方式执行，如果当前存在事务，则抛出异常。外部方法有事务，内部方法抛异常。

  **其他情况：**

  - **TransactionDefinition.PROPAGATION_NESTED：** 如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。**在外围方法未开启事务的情况下`Propagation.NESTED`和`Propagation.REQUIRED`作用相同，修饰的内部方法都会新开启自己的事务，且开启的事务相互独立，互不干扰。** **在外围方法开启事务的情况下`Propagation.NESTED`修饰的内部方法属于外部事务的子事务，外围主事务回滚，子事务一定回滚，而内部子事务可以单独回滚而不影响外围主事务和其他子事务**

## 14. 注解

- @RestController 返回JSON 或 XML 形式数据：只返回对象，对象数据直接以 JSON 或 XML 形式写入 HTTP 响应(Response)中，这种情况属于 RESTful Web服务，这也是目前日常开发所接触的最常用的情况（前后端分离）。
- @Controller +@ResponseBody 返回JSON 或 XML 形式数据：`@ResponseBody` 注解的作用是将 `Controller` 的方法返回的对象通过适当的转换器转换为指定的格式之后，写入到HTTP 响应(Response)对象的 body 中，通常用来返回 JSON 或者 XML 数据，返回 JSON 数据的情况比较多。
- controller层方法前用@RequestMapping
- @GetMapping/@PostMapping/@DeleteMapping/@PutMapping/@PatchMapping(@RequestMapping(method = RequestMethod.GET))

## 15. 解决spring加载缓慢的问题

- 扫描注解Bean，写比较精确的扫描路径，这样扫描的class会很少。如扫描@Service和@Repository：

  ```java
  <**context:component-scan base-package="com.sishuok.es.**.repository,com.sishuok.es.**.service,com.sishuok.es.**.extra"**>
  ```

- 延迟加载bean，常见的方式是在配置文件中在上加：default-lazy-init="true"。在 Spring 中，默认情况下所有定的 bean 及其依赖项目都是在应用启动时创建容器上下文是被初始化的。通过设置全局懒加载，我们可以减少启动时的创建任务从而大幅度的缩减应用的启动时间。但全局懒加载的缺点可以归纳为以下两点：

  - Http 请求处理时间变长。 这里准确的来说是第一次 http 请求处理的时间变长，之后的请求不受影响（说到这里自然而然的会联系到 spring cloud 启动后的第一次调用超时的问题）。
  - 错误不会在应用启动时抛出。

- 移除调试阶段不相干的bean，有些bean在调试阶段我们并不需要，如我们在测试用户模块时，可能不需要测试权限模块；此时我们可以把不相干的bean移除掉；具体配置请参考最后。

- 项目分模块开发，如果项目模块比较多，可以考虑放弃注解，而使用xml配置方式+约定。因为实际做项目时可能把配置分到多个配置文件，此时我尝试了下合并到一个，几乎没啥速度提升，所以还是分开存好。

## 16. 循环依赖

### 16.1 什么是循环依赖

```java
@Component
public class A {
    // A中注入了B
 @Autowired
 private B b;
}

@Component
public class B {
    // B中也注入了A
 @Autowired
 private A a;
}
```

### 16.2 什么情况下循环依赖可以被处理？

1. 出现循环依赖的Bean必须要是单例
2. 依赖注入的方式不能全是构造器注入的方式

| 依赖情况               | 依赖注入方式                                       | 循环依赖是否被解决 |
| :--------------------- | :------------------------------------------------- | :----------------- |
| AB相互依赖（循环依赖） | 均采用setter方法注入                               | 是                 |
| AB相互依赖（循环依赖） | 均采用构造器注入                                   | 否                 |
| AB相互依赖（循环依赖） | A中注入B的方式为setter方法，B中注入A的方式为构造器 | 是                 |
| AB相互依赖（循环依赖） | B中注入A的方式为setter方法，A中注入B的方式为构造器 | 否                 |

为什么在下表中的第三种情况的循环依赖能被解决，而第四种情况不能被解决呢？

提示：Spring在创建Bean时默认会根据自然排序进行创建，所以A会先于B进行创建

### 16.3 Spring如何解决的循环依赖？

- **Spring** 是利用了 **三级缓存** 来解决循环依赖的，其实现本质是通过提前暴露已经实例化但尚未初始化的 `bean` 来完成的。
- 这三级缓存分别指：
  - singletonFactories ：第三级缓存，存的是`Bean工厂对象`，用来生成`半成品的Bean`并放入到二级缓存中。用以解决循环依赖。
  - earlySingletonObjects ：第二级缓存，存放`半成品的Bean`，`半成品的Bean`是已创建对象，但是未注入属性和初始化。用以解决循环依赖。
  - singletonObjects：第一级缓存，存放初始化完成的Bean。

- 让我们来分析一下“A的某个field或者setter依赖了B的实例对象，同时B的某个field或者setter依赖了A的实例对象”这种循环依赖的情况：
  - A完成实例化，进入singletonFactories中
  - A在注入属性时，发现依赖B
  - B完成实例化，进入singletonFactories中
  - B在注入属性时，发现依赖A，从singletonFactories中发现A，完成注入，A从singletonFactories移除并进入到earlySingletonObjects中，B从singletonFactories移除并进入singletonObjects中
  - A在singletonObjects中发现B，完成注入，A从earlySingletonObjects移除并进入singletonObjects中
  - A和B均完成实例化和初始化，且A和B获取的都是对方的引用
- Spring不能解决“A的构造方法中依赖了B的实例对象，同时B的构造方法中依赖了A的实例对象”这类问题了！因为加入singletonFactories三级缓存的前提是执行了构造器，所以构造器的循环依赖没法解决。因为A中构造器注入了B，那么A在关键的方法addSingletonFactory()之前就去初始化了B，导致三级缓存中根本没有A，所以会发生死循环，Spring发现之后就抛出异常了。

### 16.4 为什么要使用三级缓存呢？二级缓存能解决循环依赖吗？

- 所以如果没有AOP的话确实可以两级缓存就可以解决循环依赖的问题，如果加上AOP，两级缓存是无法解决的，不可能每次执行singleFactory.getObject()方法都给我产生一个新的代理对象，所以还要借助另外一个缓存来保存产生的代理对象。
- 因为AService是单例的，每次执行singleFactory.getObject()方法又会产生新的代理对象，假设这里只有一级和三级缓存的话，我每次从三级缓存中拿到singleFactory对象，执行getObject()方法又会产生新的代理对象，这是不行的，因为AService是单例的，所有这里我们要借助二级缓存来解决这个问题，将执行了singleFactory.getObject()产生的对象放到二级缓存中去，后面去二级缓存中拿，没必要再执行一遍singletonFactory.getObject()方法再产生一个新的代理对象，保证始终只有一个代理对象。

![Spring_4](https://upload-images.jianshu.io/upload_images/18992122-bac2b241258060e7.png?imageMogr2/auto-orient/strip|imageView2/2/format/webp)

## 17. @ControllerAdvice全局异常处理

- 在Service层使用try-catch对异常进行捕获并处理的代码，其存在如下缺陷：

  1. 由于Service层是与Dao层进行交互的，我们通常都会把事务配置在Service层，当操作数据库失败时，会抛出异常并由Spring事务管理器帮我们进行回滚，而当我们使用try-catch对异常进行捕获处理时，这时Spring事务管理器并不会帮我们进行回滚，而代码也会继续往下执行，这时我们就有可能读取到脏数据。

  2. 由于我们在Service层对异常进行了捕获处理，在Controller层调用该Service层的相关方法时，并不能把其相关异常信息抛给Controller层，也就无法把异常信息展示给前端页面，而当用户进行相关操作失败时，也就无法得知其操作失败的缘由。

- 在Controller层使用try-catch对异常进行捕获并处理的代码，其存在如下缺陷：

  1. 由于Controller层是与Service层进行交互的，这样一来，在Controller层调用Service层抛出异常的方法时，我们都需要在Controller层的方法体中，写一遍try-catch代码来捕获处理Service层抛出的异常，就会使得代码很难看而且也很难维护。

  2. 对于Service层抛出的不同异常，那么Controller层的方法体中需要catch多个异常分别进行处理。

  3. 这里就会有人说了，我直接在Controller层的方法上使用 throws关键字 把Service层的异常继续往上抛不就行了，还不需要使用try-catch来进行捕获处理，确实是可以，但是这样会把大量的异常信息带到前端页面，对用户来说是非常不友好的

- 使用@ControllerAdvice/@RestControllerAdvice + @ExceptionHandler注解能够实现全局处理Controller层的异常，并能够自定义其返回的信息(前提：Controller层不使用try-catch对异常进行捕获处理)

  优缺点：

  - 优点：将 Controller 层的异常和数据校验的异常进行统一处理，减少模板代码，减少编码量，提升扩展性和可维护性。

  - 缺点：能处理 Controller 层的异常 (未使用try-catch进行捕获) 和 @Validated 校验器注解的异常，但是对于 Interceptor（拦截器）层的异常 和 Spring 框架层的异常，就无能为力了。

- @ExceptionHandler可以捕获到controller中指定的异常并进行处理

  1. 写在普通的controller中：只能识别并处理该controller中的指定异常

  2. 写在@ControllerAdvice注解的controller中：可以识别并处理所有@ControllerAdvice覆盖的controller（默认是覆盖所有）中的指定异常

- 首先，`ExceptionHandlerExceptionResolver`是`Spring MVC`缺省被启用的一个`HandlerExceptionResolver`,它会被作为一个组合模式`HandlerExceptionResolver bean`中的一个元素进入到`bean`容器中。ExceptionHandlerExceptionResolver实现了接口InitializingBean,所以它在实例化时会被初始化。该过程中，它就会搜集所有的@ControllerAdvice注解类中使用@ExceptionHandler定义的异常处理控制器方法以供随后工作时使用。

- 接下来，`DispatcherServlet`初始化时，会搜集所有`HandlerExceptionResolver bean`记录到自己的策略组件属性`List<HandlerExceptionResolver> handlerExceptionResolvers`。

- 然后，处理某个请求时某个异常发生了。`DispatcherServlet`会遍历`handlerExceptionResolvers`中每个`HandlerExceptionResolver`对象试图对该异常进行处理。

- 当这类异常交给ExceptionHandlerExceptionResolver解析时，它会首先查看发生异常的方法所在的controller类中是否有合适的@ExceptionHanlder方法，然后看所有的@ControllerAdvice类中是否有合适的@ExceptionHanlder方法,如果有,ExceptionHandlerExceptionResolver就会使用相应的方法处理该异常。

## 18. 事务的挂起

- 在ThreadLocal 对象里，将资源对象绑定或移出当前线程对应的 resources 来实现的。
- 根据数据源获取当前的Connection，并在resource中移除该Connection。之后会将该Connection存储到TransactionStatus对象中。在事务提交或者回滚后，会将TransactionStatus 中缓存的Connection重新绑定到resource中。

## 19. SpringBoot 启动流程

- 引入相关`Starters`和相关依赖后，再编写一个启动类，然后在这个启动类标上`@SpringBootApplication`注解，在`main`函数中调用`SpringApplication.run(MainApplication.class, args);`

```java
@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
```

- 通过 `SpringFactoriesLoader` 加载 `META-INF/spring.factories` 文件，获取并创建 `SpringApplicationRunListener` 对象

  然后由 `SpringApplicationRunListener` 来发出 starting 消息

  创建参数，并配置当前 SpringBoot 应用将要使用的 Environment

  完成之后，依然由 `SpringApplicationRunListener` 来发出 environmentPrepared 消息

  创建 `ApplicationContext`

  初始化 `ApplicationContext`，并设置 Environment，加载相关配置等

  由 `SpringApplicationRunListener` 来发出 `contextPrepared` 消息，告知SpringBoot 应用使用的 `ApplicationContext` 已准备OK

  将各种 beans 装载入 `ApplicationContext`，继续由 `SpringApplicationRunListener` 来发出 contextLoaded 消息，告知 SpringBoot 应用使用的 `ApplicationContext` 已装填OK

  refresh ApplicationContext，完成IoC容器可用的最后一步

  由 `SpringApplicationRunListener` 来发出 started 消息

  完成最终的程序的启动

  由 `SpringApplicationRunListener` 来发出 running 消息，告知程序已运行起来了

- 从`spring.factories`配置文件中**加载`EventPublishingRunListener`对象**，该对象拥有`SimpleApplicationEventMulticaster`属性，即在SpringBoot启动过程的不同阶段用来发射内置的生命周期事件;

- **准备环境变量**，包括系统变量，环境变量，命令行参数，默认变量，`servlet`相关配置变量，随机值以及配置文件（比如`application.properties`）等;

- 控制台**打印SpringBoot的`bannner`标志**；

- **根据不同类型环境创建不同类型的`applicationcontext`容器**，因为这里是`servlet`环境，所以创建的是`AnnotationConfigServletWebServerApplicationContext`容器对象；

- 从`spring.factories`配置文件中**加载`FailureAnalyzers`对象**,用来报告SpringBoot启动过程中的异常；

- **为刚创建的容器对象做一些初始化工作**，准备一些容器属性值等，对`ApplicationContext`应用一些相关的后置处理和调用各个`ApplicationContextInitializer`的初始化方法来执行一些初始化逻辑等；

- **刷新容器**，这一步至关重要。比如调用`bean factory`的后置处理器，注册`BeanPostProcessor`后置处理器，初始化事件广播器且广播事件，初始化剩下的单例`bean`和SpringBoot创建内嵌的`Tomcat`服务器等等重要且复杂的逻辑都在这里实现，主要步骤可见代码的注释，关于这里的逻辑会在以后的spring源码分析专题详细分析；

- **执行刷新容器后的后置处理逻辑**，注意这里为空方法；

- **调用`ApplicationRunner`和`CommandLineRunner`的run方法**，我们实现这两个接口可以在spring容器启动后需要的一些东西比如加载一些业务数据等;

- **报告启动异常**，即若启动过程中抛出异常，此时用`FailureAnalyzers`来报告异常;

- 最终**返回容器对象**，这里调用方法没有声明对象来接收。

## 20. Spring，SpringMVC，SpringBoot，SpringCloud有什么区别和联系？

- Spring是一个一站式的轻量级的java开发框架，核心是控制反转（IOC）和面向切面（AOP），针对于开发的WEB层(springMvc)、业务层(Ioc)、持久层(jdbcTemplate)等都提供了多种配置解决方案；
- SpringMVC是Spring基础之上的一个MVC框架，主要处理web开发的路径映射和视图渲染，属于Spring框架中WEB层开发的一部分；通过Dispatcher Servlet, ModelAndView 和 View Resolver，开发web应用变得很容易。主要针对的是网站应用程序或者服务开发——URL路由、Session、模板引擎、静态Web资源等等。
- Spring配置复杂，繁琐，所以推出了Spring boot，约定优于配置，简化了spring的配置流程。集成了快速开发的Spring多个插件，同时自动过滤不需要配置的多余的插件，简化了项目的开发配置流程，一定程度上取消xml配置，是一套快速配置开发的脚手架，能快速开发单个微服务；SpringBoot框架相对于SpringMVC框架来说，更专注于开发微服务后台接口，不开发前端视图；
  - 为了解决java开发中的，繁多的配置、底下的开发效率，复杂的部署流程，和第三方技术集成难度大的问题，产生了spring boot。
  - springboot 使用 “习惯优于配置”的理念让项目快速运行起来，使用springboot很容易创建一个独立运行的jar，内嵌servlet容器
  - springboot的核心功能一：独立运行spring项目，springboot可以以jar包的形式独立运行，运行一个springboot项目只需要 java -jar xxx.jar 来运行
  - springboot的核心功能二：内嵌servlet容器，可以内嵌tomcat，接天jetty，或者undertow，这样我们就可以不用war包形式部署项目
  - springboot的核心功能三，提供starter简化maven配置，spring提供了一系列starter pom 来简化maven的依赖加载， 当使用了 spring-boot-starter-web时，会自动加载所需要的依赖包
  - springboot的核心功能三：自动配置spring sprintboot 会根据在类路径的jar包，类，为jar包中的类自动配置bean，这样会极大的减少使用的配置，会根据启动类所在的目录，自动配置bean
- Spring Cloud构建于Spring Boot之上，是一个关注全局的服务治理框架。SpringCloud大部分的功能插件都是基于SpringBoot去实现的，SpringCloud关注于全局的微服务整合和管理，将多个SpringBoot单体微服务进行整合以及管理；SpringCloud依赖于SpringBoot开发，而SpringBoot可以独立开发；

## 21. spring bean 注入过程

- 1） 在某一时刻Spring调用了Bean工厂的getBean(beanName)方法。beanName可能是simpleController,或者simpleService，simpleDao，顺序没关系（因为后面会有依赖关系的处理）。我们假设simpleController吧
- 2）getBean方法首先会调用Bean工厂中定义的getSingleton(beanName)方法，来判断是否存在该名字的bean单例，如果存在则返回，方法调用结束（spring默认是单例，这样可以提高效率）
- 3) 否则，Spring会检查是否存在父工厂，如果有则返回，方法调用结束
- 4) 否则，Spring会检查bean定义（BeanDefinition实例，用来描述Bean结果，component-scan扫描后，就是将beanDefinition实例放入Bean工厂，此时还没有被实例化）是否有依赖关系，如果有，执行1）步，获取依赖的bean实例
- 5） 否则，Spring会尝试创建这个bean实例，创建实例前，Spring会检查调用的构造器，并实例化该Bean，（通过Constructor.newInstance(args)进行实例化）
- 6) 实例化完成后，Spring会调用Bean工厂的populateBean方法来填充bean实例的属性，也就是自动装配。populateBean方法便是调用了BeanPostProcessor实例来完成属性元素的自动装配工作
- 7）在元素装配过程中，Spring会检查被装配的属性是否存在自动装配的其他属性，然后递归调用getBean方法，知道所有@Autowired的元素都被装配完成。如在装配simpleController中的simpleService属性时，发现SimpleServiceImpl实例中存在@Autowired属性simpleDao,然后调用getBean(simpleDao)方法，同样会执行1）----7）整个过程。所有可以看成一个递归过程。
- 8）装配完成后，Bean工厂会将所有的bean实例都添加到工厂中来。

## 22. BeanFactory 和 FactoryBean 的区别

- BeanFactory：Spring 容器最核心也是最基础的接口，本质是个工厂类，用于管理 bean 的工厂，最核心的功能是加载 bean，也就是 getBean 方法，通常我们不会直接使用该接口，而是使用其子接口。
- FactoryBean：该接口以 bean 样式定义，但是它不是一种普通的 bean，它是个工厂 bean，实现该接口的类可以自己定义要创建的 bean 实例，只需要实现它的 getObject 方法即可。
- FactoryBean 被广泛应用于 Java 相关的中间件中，如果你看过一些中间件的源码，一定会看到 FactoryBean 的身影。
- 一般来说，都是通过 FactoryBean#getObject 来返回一个代理类，当我们触发调用时，会走到代理类中，从而可以在代理类中实现中间件的自定义逻辑，比如：RPC 最核心的几个功能，选址、建立连接、远程调用，还有一些自定义的监控、限流等等。

## 23. Spring 事务的实现原理

- Spring 事务的底层实现主要使用的技术：AOP（动态代理） + ThreadLocal + try/catch。

  动态代理：基本所有要进行逻辑增强的地方都会用到动态代理，AOP 底层也是通过动态代理实现。

  ThreadLocal：主要用于线程间的资源隔离，以此实现不同线程可以使用不同的数据源、隔离级别等等。

  try/catch：最终是执行 commit 还是 rollback，是根据业务逻辑处理是否抛出异常来决定。

- Spring 事务的核心逻辑伪代码如下：

```java
public void invokeWithinTransaction() {

 // 1.事务资源准备

 try {

 // 2.业务逻辑处理，也就是调用被代理的方法

    } catch (Exception e) {

 // 3.出现异常，进行回滚并将异常抛出

    } finally {

 // 现场还原：还原旧的事务信息

    }

 // 4.正常执行，进行事务的提交

 // 返回业务逻辑处理结果

}
```

![](https://pic4.zhimg.com/80/v2-491bf433440cc224a409dcb3e579257b_720w.jpg)

## 24. @Resource 和 @Autowire 的区别

- 1、@Resource 和 @Autowired 都可以用来装配 bean

  2、@Autowired 默认按类型装配，默认情况下必须要求依赖对象必须存在，如果要允许null值，可以设置它的required属性为false。

  3、@Resource 如果指定了 name 或 type，则按指定的进行装配；如果都不指定，则优先按名称装配，当找不到与名称匹配的 bean 时才按照类型进行装配。

## 25. springboot自动装配原理

- `@SpringBootApplication`注解：
  
  - **`@ComponentScan`** **注解：**自动扫描并加载符合条件的Bean到容器中，这个注解会默认扫描声明类所在的包开始扫描，例如：类`cn.shiyujun.Demo`类上标注了`@ComponentScan` 注解，则`cn.shiyujun.controller`、`cn.shiyujun.service`等等包下的类都可以被扫描到
  -   **`@SpringBootConfiguration`注解：**底层是**Configuration**注解，支持**JavaConfig**的方式来进行配置(**使用Configuration配置类等同于XML文件**)。
  -   EnableAutoConfiguration注解：利用`@Import`注解，将所有符合自动装配条件的bean注入到IOC容器中
- EnableAutoConfiguration注解：

```java
@Import({AutoConfigurationImportSelector.class})
public @interface EnableAutoConfiguration {
    String ENABLED_OVERRIDE_PROPERTY = "spring.boot.enableautoconfiguration";

    Class<?>[] exclude() default {};

    String[] excludeName() default {};
}
```

- 进入类`AutoConfigurationImportSelector`，观察其`selectImports`方法，这个方法执行完毕后，Spring会把这个方法返回的类的全限定名数组里的所有的类都注入到IOC容器中

```java
/**
* 方法用于给容器中导入组件
**/
@Override
public String[] selectImports(AnnotationMetadata annotationMetadata) {
    if (!isEnabled(annotationMetadata)) {
        return NO_IMPORTS;
    }
    AutoConfigurationMetadata autoConfigurationMetadata = AutoConfigurationMetadataLoader
        .loadMetadata(this.beanClassLoader);
    AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(
        autoConfigurationMetadata, annotationMetadata);  // 获取自动配置项
    return StringUtils.toStringArray(autoConfigurationEntry.getConfigurations());
}

// 获取自动配置项
protected AutoConfigurationEntry getAutoConfigurationEntry(
    AutoConfigurationMetadata autoConfigurationMetadata,
    AnnotationMetadata annotationMetadata) {
    if (!isEnabled(annotationMetadata)) {
        return EMPTY_ENTRY;
    }
    AnnotationAttributes attributes = getAttributes(annotationMetadata);
    List < String > configurations = getCandidateConfigurations(annotationMetadata,
        attributes);  //  获取一个自动配置 List ，这个 List 就包含了所有自动配置的类名
    configurations = removeDuplicates(configurations);
    Set < String > exclusions = getExclusions(annotationMetadata, attributes);
    checkExcludedClasses(configurations, exclusions);
    configurations.removeAll(exclusions);
    configurations = filter(configurations, autoConfigurationMetadata);
    fireAutoConfigurationImportEvents(configurations, exclusions);
    return new AutoConfigurationEntry(configurations, exclusions);
}

//   获取一个自动配置 List ，这个 List 就包含了所有的自动配置的类名
protected List < String > getCandidateConfigurations(AnnotationMetadata metadata,
    AnnotationAttributes attributes) {
    // 通过 getSpringFactoriesLoaderFactoryClass 获取默认的 EnableAutoConfiguration.class 类名，传入 loadFactoryNames 方法
    List < String > configurations = SpringFactoriesLoader.loadFactoryNames(
        getSpringFactoriesLoaderFactoryClass(), getBeanClassLoader());
    Assert.notEmpty(configurations,
        "No auto configuration classes found in META-INF/spring.factories. If you " +
        "are using a custom packaging, make sure that file is correct.");
    return configurations;
}

// 默认的 EnableAutoConfiguration.class 类名
protected Class<?> getSpringFactoriesLoaderFactoryClass() {
	return EnableAutoConfiguration.class;
}
```

- 首先注意到 selectImports 方法，其实从方法名就能看出，这个方法用于给容器中导入组件，然后跳到 getAutoConfigurationEntry 方法就是用于获取自动配置项的。
- 再来进入 getCandidateConfigurations 方法就是 获取一个自动配置 List ，这个 List 就包含了所有的自动配置的类名 。
- 再进入 SpringFactoriesLoader 类的 loadFactoryNames 方法，跳转到 loadSpringFactories 方法发现 ClassLoader 类加载器指定了一个 FACTORIES_RESOURCE_LOCATION 常量，它指定的是 jar 包类路径下 META-INF/spring.factories 文件。
- 然后利用PropertiesLoaderUtils 把 ClassLoader 扫描到的这些文件的内容包装成 properties 对象，从 properties 中获取到 EnableAutoConfiguration.class 类（类名）对应的值，然后把他们添加在容器中。

## 26. SpringBoot条件注解@Conditional

- @Conditional注解可以根据代码中设置的条件装载不同的bean，根据满足某一个特定条件创建一个特定的Bean。
- @ConditionalOnBean：存在某个bean，实例化
- @ConditionalOnMissingBean：不存在某个bean的时候实例化。
- @ConditionalOnClass：存在某个类时，才会实例化一个Bean
- @ConditionalOnMissingClass：不存在某个类时，才会实例化一个Bean
- @ConditionalOnProperty(prefix = “syj”, name = “algorithm”, havingValue = “token”)：当存在配置文件中以syj为前缀的属性，属性名称为algorithm，然后它的值为token时才会实例化一个类。
- @ConditionalOnJava：如果是Java应用
- @ConditionalOnWebApplication：如果是Web应用

## 27. Spring Boot Starters启动器

- Starters可以理解为启动器，它包含了一系列可以集成到应用里面的依赖包，你可以一站式集成Spring及其他技术，而不需要到处找示例代码和依赖包。如你想使用Spring JPA访问数据库，只要加入spring-boot-starter-data-jpa启动器依赖就能使用了。
- Spring Boot官方的启动器都是以spring-boot-starter-命名的，代表了一个特定的应用类型。
- 第三方的启动器不能以spring-boot开头命名，它们都被Spring Boot官方保留。一般一个第三方的应该这样命名，像mybatis的mybatis-spring-boot-starter。