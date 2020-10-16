# C++

## C++ types
### Fundamental Types
Available without any additional declaration Example: int, bool
#### Boolean (bool)
One value between true (1) or false (0)

Used to represent logical conditions or results of logical operations

bool b1 {1 == 0};

#### Character (e.g., char)
Different types are available (char, signed char, unsigned char, wchar_t)

Almost always char has 8 bit

7 bit are enough to represent ASCII

signed vs unsigned char:

• A char may be represented either as signed or unsigned

• Implementation-defined behavior (Windows vs Linux, 32 vs 64 bit, arm vs x86)



### User-Defined Types
Introduced by the user and/or by a library Example: std::vector

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