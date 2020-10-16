# C++

## Midterm dry-run 1

### What is variable shadowing? Give an example
we have a variable inside a nested block that has the same name as a variable in an outer block, the nested variable “hides” the outer variable in areas where they are both in scope. This is called name hiding or shadowing.

```c++
#include <iostream>
 
int main()
{ // outer block
    int apples { 5 }; // here's the outer block apples
 
    { // nested block
        // apples refers to outer block apples here
        std::cout << apples << '\n'; // print value of outer block apples
 
        int apples{ 0 }; // define apples in the scope of the nested block
 
        // apples now refers to the nested block apples
        // the outer block apples is temporarily hidden
 
        apples = 10; // this assigns value 10 to nested block apples, not outer block apples
 
        std::cout << apples << '\n'; // print value of nested block apples
    } // nested block apples destroyed
 
 
    std::cout << apples << '\n'; // prints value of outer block apples
 
    return 0;
} // outer block apples destroyed
```
### How is it possible to initialize an array of int with the numbers 1, 5, 3, 7?
int v1[4] = {1, 5, 3, 7};

### In which ways can you access individual members of a struct? Give an example
```c++
Address jd;
jd.name = "Jim Dandy";
void f(Address &addr) {
   addr.name = "Jim Dandy";
}
void f(Address *addr) {
   addr->name = "Jim Dandy";
   // or
   (*addr).name = "Jim Dandy";
}
```

### What will this snippet of code print to the terminal?
```c++
int x = 10;

switch(x) {

       case 1:

                std::cout << “hello” << std::endl;

       case 10:

                std::cout << “this is ” << std::endl;

       case 11:

                std::cout << “an exam” << std::endl;

}
```

this is
an exam

### What does the socket option REUSEADDR do?
by default the kernel blocks a socket port for few minutes after the socket destruction: to reuse the port immediatelly use the option REUSEADDR

## Midterm dry-run 2
### What is a server and what is a client? 
If a server program runs a host with ip ip1 listening at port port1, port1 must be known to the clients

A client program runs in a different or in the same host and accesses the server, as it knows the server ip and port number.

### In the compilation process, what is the translation unit?
The preprocessor takes a C++ source code file and deals with the #includes, #defines and other preprocessor directives. The output of this step is a "pure" C++ file without preprocessor directives, the translation unit.

### What is the purpose of a class?


### What is the effect of the inline keyword? Is it guaranteed?
Inline keyword suggests to the compiler to generate new code for every function call, instead than of a single code in memory that is pointed to by every function call. This enables an optimization in terms of execution speed. The compiler is not required to respect the inline keyword

### What is lazy evaluation? Why is it useful? To which problems can it lead? Give an example.
 lazy evaluation is an evaluation strategy which delays the evaluation of an expression until its value is needed and which also avoids repeated evaluations. It always check the first condition in expressions with logical operators. Evaluate the second only it the first is not enough. 

```c++
bool c1 = false;
bool c2 = true;
if (c1 && c2)
{ // c2 is not evaluated }
if (c2 && c1)
{ // both evaluated }
if (c1 && someFunction(c2))
{ 
// someFunction(c2) is not evaluated
// this may be done on purpose (always comment it!)
// it could also lead to errors if not done on 
purpose
}
```




