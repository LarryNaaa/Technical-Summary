## Springboot
### Spring框架基本实现思路
1. 创建数据库。
2. 创建实体类Entity，与数据库中的表项一一对应。
3. 创建接口，同于连接数据库与项目功能。
4. 编写mapper.xml文件（Mybatis）定义关联SQL语言与接口操作。
5. 创建Service类，为控制层提供服务。可以单独创建接口实现解耦。
6. 创建Controller类，连接页面请求和服务层，处理前端发来的请求。
7. 前端页面调用，使用jsp或html标准编写前端页面显示。

### Spring Data JPA
**Java Persistence API即Java持久化API，SpringData JPA，即Spring对JPA的封装**

优点：

1. 直接可以通过实体类与表、实体属性与表的字段之间的关联，自动为建表
      
2. 使用时不需要再考虑使用的是何种数据库，也基本不需要写sql语句

缺点：

JPA将ORM封装的太完善了，使得我们操作数据库的灵活性大大降低了。
比如我们如果想要实现多表关联的查询、多条件查询等等，都会变得更加复杂

#### Entity
**实体层: 存放的是实体类，属性值与数据库值保持一致，实现 setter 和 getter 方法。**
```java
@Entity @MappedSuperclass 
@Table(name)
```
@Entity与@MappedSuperclass都是声明实体类的注解

@Entity 表明这是一个实体类，所声明的实体类里面定义的所有属性，均与对应的表关联

@MappedSuperclass表示所注解的类为一个父类，子类可以通过继承该父类来扩展自己的属性

@Table(name)，表明该实体类与数据库中的某个表关联，但是如果表名和实体类名相同的话，@Table可以省略

#### 主键
```java
@Id
@GeneratedValue(strategy, generator)
@SequenceGenerator(name, sequenceName)
```
@Id和@GeneratedValue常常组合起来，用于主键声明，并指定主键生成策略和生成器
（若不指定，JPA会根据所引入驱动依赖的数据库进行默认策略的设置）

> @GeneratedValue注解有两个属性,分别是strategy和generator.

> > generator属性：generator属性的值是一个字符串,默认为"",其声明了主键生成器的名称

> > strategy属性：
提供四种值:

> > > -AUTO主键由程序控制, 是默认选项 ,不设置就是这个
 
> > > -IDENTITY 主键由数据库生成, 采用数据库自增长, Oracle不支持这种方式
 
> > > -SEQUENCE 通过数据库的序列产生主键, MYSQL不支持
 
> > > -Table 提供特定的数据库产生主键, 该方式更有利于数据库的移植

@SequenceGenerator注解，表示主键会采用系列号的方式生成

#### 映射
```java
@Column(name,unique,nullable,length,insertable,updatable)
@JoinTable(name) @JoinColumn(name)
```
> @Column指定一个属性所映射的字段信息:

> > name
定义了被标注字段在数据库表中所对应字段的名称；

> > unique
表示该字段是否为唯一标识，默认为false。如果表中有一个字段需要唯一标识，则既可以使用该标记，也可以使用@Table标记中的@UniqueConstraint。

> > nullable
表示该字段是否可以为null值，默认为true。

> > insertable
表示在使用“INSERT”脚本插入数据时，是否需要插入该字段的值。

> > updatable
表示在使用“UPDATE”脚本插入数据时，是否需要更新该字段的值。insertable和updatable属性一般多用于只读的属性，例如主键和外键等。这些字段的值通常是自动生成的。

> > columnDefinition（大多数情况，几乎不用）
表示创建表时，该字段创建的SQL语句，一般用于通过Entity生成表定义时使用。（也就是说，如果DB中表已经建好，该属性没有必要使用。）

> > table
表示当映射多个表时，指定表的表中的字段。默认值为主表的表名。

> > length
表示字段的长度，当字段的类型为varchar时，该属性才有效，默认为255个字符。

> > precision和scale
precision属性和scale属性表示精度，当字段类型为double时，precision表示数值的总长度，scale表示小数点所占的位数。

若存在外键，可以使用@JoinTable(name) @JoinColumn(name)组合进行定义，属性与@Column类似

#### 关系
```java
@OneToOne @OneToMany @ManyToOne @ManyToMany
@OrderBy
```
前四个注解用在属性上，分别表示表与该属性的对应关系。
> @OneToOne: 一对一关系

> > targetEntity属性表示默认关联的实体类型，默认为当前标注的实体类；
> > cascade属性表示与此实体一对一关联的实体的联级样式类型。联级样式上当对实体进行操作时的策略。
> > > 说明：在定义关系时经常会涉及是否定义Cascade(级联处理)属性，担心造成负面影响.
       ·不定义,则对关系表不会产生任何影响
       ·CascadeType.PERSIST （级联新建）
       ·CascadeType.REMOVE （级联删除）
       ·CascadeType.REFRESH （级联刷新）
       ·CascadeType.MERGE （级联更新）中选择一个或多个。
       ·还有一个选择是使用CascadeType.ALL ，表示选择全部四项
> > fetch属性是该实体的加载方式，有两种：LAZY和EAGER。
> > optional属性表示关联的实体是否能够存在null值。默认为true，表示可以存在null值。如果为false，则要同时配合使用@JoinColumn标记。
> > mappedBy属性用于双向关联实体时，标注在不保存关系的实体中。


@OrderBy同样使用在属性上，表示@oneToMany以及@ManyToMany，即一对多或者多对多时，字段的排序方式.

#### Validation 验证
1.在相关的实体类的相关字段添加用于充当验证条件的注解

Validation相关注解:

@Null 限制只能为null

@NotNull 限制必须不为null

@AssertFalse 限制必须为false

@AssertTrue 限制必须为true

@DecimalMax(value) 限制必须为一个不大于指定值的数字

@DecimalMin(value) 限制必须为一个不小于指定值的数字

@Digits(integer,fraction) 限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction

@Future 限制必须是一个将来的日期

@Max(value) 限制必须为一个不大于指定值的数字

@Min(value) 限制必须为一个不小于指定值的数字

@Past 限制必须是一个过去的日期

@Pattern(value) 限制必须符合指定的正则表达式

@Size(max,min) 限制字符长度必须在min到max之间

@Past 验证注解的元素值（日期类型）比当前时间早

@NotEmpty 验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）

@NotBlank 验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格

@Email 验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式

2.在controller层的方法的要校验的参数上添加@Valid注解

3.编写全局异常捕捉类

@ControllerAdvice 全局异常处理

@ExceptionHandler 注解用来指明异常的处理类型

### Repository
**数据访问层: 对数据库进行持久化操作，他的方法使针对数据库操作的，基本上用的就是增删改查**

加上@EnableJpaRepositories，即可开启JPA的repository的支持，
Spring 就会发现以下repository的扩展：

> CrudRepository<T, ID> 提供基本增删改查方法

> PagingAndSortingRepository<T, ID>用于分页、排序方法

> JpaRepository<T, ID> 用于持久化等方法

如果使用@Repository注解，则需继承以上三个类。

#### 查询方式
查询方法，默认只提供了查询所有或者按照ID查询的方法。

若想要查询更丰富的内容，比如按照某个条件查询、统计记录数、排序展示、
多条件查询、查询第一条或最后一条等，这么多复杂的场景，依旧不需要写sql，
我们只需要在repository中定义如下格式的方法就可以轻而易举的实现:
```java
find…By… / read…By… / query…By… / get…By…
count…By…
…OrderBy…[Asc / Desc]
And / Or / IgnoreCase
Top / First / Distinct 
```

### Service
**业务逻辑层: 存放业务逻辑处理，不直接对数据库进行操作，有接口和接口实现类，提供 controller 层调用方法。**

### Controller
**Web控制层: 导入service层，调用service方法，controller通过接受前端传来的参数进行业务操作，在返回一个制定的路径或数据表。**

### Annotations

#### @Bean
indicates that a method produces a bean to be managed by Spring.

#### @Service
indicates that an annotated class is a service class.

#### @Repository
indicates that an annotated class is a repository, which is an abstraction of data access and storage.

#### @Configuration
indicates that a class is a configuration class that may contain bean definitions.

#### @Controller
marks the class as web controller, capable of handling the requests.

#### @RequestMapping
maps HTTP request with a path to a controller method.

#### @Autowired
marks a constructor, field, or setter method to be autowired by Spring dependency injection.

#### @SpringBootApplication
enables Spring Boot autoconfiguration and component scanning.

#### @RestController
It's a convenience annotation that combines @Controller and @ResponseBody – which eliminates the need to annotate every request handling method of the controller class with the @ResponseBody annotation.

Every request handling method of the controller class automatically serializes return objects into HttpResponse.

```java
@RestController
@RequestMapping("books-rest")
public class SimpleBookRestController {
    
    @GetMapping("/{id}", produces = "application/json")
    public Book getBook(@PathVariable int id) {
        return findBookById(id);
    }
 
    private Book findBookById(int id) {
        // ...
    }
}
```

#### @RequestBody
@RequestBody annotation maps the HttpRequest body to a transfer or domain object, enabling automatic deserialization of the inbound HttpRequest body onto a Java object.

Spring automatically deserializes the JSON into a Java type, assuming an appropriate one is specified.

```java
	@PostMapping("/request")
public ResponseEntity postController(
  @RequestBody LoginForm loginForm) {
 
    exampleService.fakeAuthenticate(loginForm);
    return ResponseEntity.ok(HttpStatus.OK);
}
```

#### @ResponseBody
@ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.

```java
@Controller
@RequestMapping("/post")
public class ExamplePostController {
 
    @Autowired
    ExampleService exampleService;
 
    @PostMapping("/response")
    @ResponseBody
    public ResponseTransfer postResponseController(
      @RequestBody LoginForm loginForm) {
        return new ResponseTransfer("Thanks For Posting!!!");
     }
}
```

#### @RequestMapping
@RequestMapping annotation is used to map web requests onto specific handler classes and/or handler methods.

##### @RequestMapping with Class
We can use it with class definition to create the base URI. For example:
```java
@Controller
@RequestMapping("/home")
public class HomeController {

}
```
Now /home is the URI for which this controller will be used. This concept is very similar to servlet context of a web application.

##### @RequestMapping with Method
We can use it with method to provide the URI pattern for which handler method will be used. For example:

```java
@RequestMapping(value="/method0")
@ResponseBody
public String method0(){
	return "method0";
}
```
##### @RequestMapping with Multiple URI:
We can use a single method for handling multiple URIs, for example:
```java
@RequestMapping(value={"/method1","/method1/second"})
@ResponseBody
public String method1(){
	return "method1";
}
```
If you will look at the source code of RequestMapping annotation, you will see that all of it’s variables are arrays. We can create String array for the URI mappings for the handler method.

##### @RequestMapping with HTTP Method
Sometimes we want to perform different operations based on the HTTP method used, even though request URI remains same. We can use @RequestMapping method variable to narrow down the HTTP methods for which this method will be invoked. For example:
```java
@RequestMapping(value="/method2", method=RequestMethod.POST)
@ResponseBody
public String method2(){
	return "method2";
}
	
@RequestMapping(value="/method3", method={RequestMethod.POST,RequestMethod.GET})
@ResponseBody
public String method3(){
	return "method3";
}
```

##### @RequestMapping with Headers
We can specify the headers that should be present to invoke the handler method. For example:
```java
@RequestMapping(value="/method4", headers="name=pankaj")
@ResponseBody
public String method4(){
	return "method4";
}
	
@RequestMapping(value="/method5", headers={"name=pankaj", "id=1"})
@ResponseBody
public String method5(){
	return "method5";
}
```

##### @RequestMapping with Produces and Consumes
We can use header Content-Type and Accept to find out request contents and what is the mime message it wants in response. For clarity, @RequestMapping provides produces and consumes variables where we can specify the request content-type for which method will be invoked and the response content type. For example:
```java
@RequestMapping(value="/method6", produces={"application/json","application/xml"}, consumes="text/html")
@ResponseBody
public String method6(){
	return "method6";
}
```
Above method can consume message only with Content-Type as text/html and is able to produce messages of type application/json and application/xml.

##### @RequestMapping with @PathVariable
RequestMapping annotation can be used to handle dynamic URIs where one or more of the URI value works as a parameter. We can even specify Regular Expression for URI dynamic parameter to accept only specific type of input. It works with @PathVariable annotation through which we can map the URI variable to one of the method arguments. For example:
```java
@RequestMapping(value="/method7/{id}")
@ResponseBody
public String method7(@PathVariable("id") int id){
	return "method7 with id="+id;
}
	
@RequestMapping(value="/method8/{id:[\\d]+}/{name}")
@ResponseBody
public String method8(@PathVariable("id") long id, @PathVariable("name") String name){
	return "method8 with id= "+id+" and name="+name;
}
```
##### @RequestMapping with @RequestParam for URL parameters
Sometimes we get parameters in the request URL, mostly in GET requests. We can use @RequestMapping with @RequestParam annotation to retrieve the URL parameter and map it to the method argument. For example:
```java
@RequestMapping(value="/method9")
@ResponseBody
public String method9(@RequestParam("id") int id){
	return "method9 with id= "+id;
}
```
For this method to work, the parameter name should be “id” and it should be of type int.

##### @GetMapping/@PostMapping/@DeleteMapping/@PutMapping/@PatchMapping
@GetMapping annotation maps HTTP GET requests onto specific handler methods. It is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.GET).


#### @CrossOrigin
In any modern browser, the Cross-Origin Resource Sharing (CORS) is a relevant specification with the emergence of HTML5 and JS clients that consume data via REST APIs.

In many cases, the host that serves the JS (e.g., example.com) is different from the host that serves the data (e.g., api.example.com). In such a case, CORS enables cross-domain communication.

##### @CrossOrigin on the Controller
```java
@CrossOrigin(origins = "http://example.com", maxAge = 3600)
@RestController
@RequestMapping("/account")
public class AccountController {
 
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Account retrieve(@PathVariable Long id) {
        // ...
    }
 
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void remove(@PathVariable Long id) {
        // ...
    }
}
```
This time, we added @CrossOrigin on the class level. Consequently, both retrieve() and remove() methods have it enabled. We can customize the configuration by specifying the value of one of the annotation attributes: origins, methods, allowedHeaders, exposedHeaders, allowCredentials, or maxAge.

##### @CrossOrigin on a @RequestMapping-Annotated Handler Method
```java
@RestController
@RequestMapping("/account")
public class AccountController {
 
    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Account retrieve(@PathVariable Long id) {
        // ...
    }
 
    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void remove(@PathVariable Long id) {
        // ...
    }
}
```
In the example above, we only enabled CORS for the retrieve() method. We can see that we didn't set any configuration for the @CrossOrigin annotation, so it uses the defaults:

All origins are allowed

The HTTP methods allowed are those specified in the @RequestMapping annotation (for this example is GET)

The time that the preflight response is cached (maxAge) is 30 minutes




