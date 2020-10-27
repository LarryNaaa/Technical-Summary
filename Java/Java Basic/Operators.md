# Operators

## Logical Operators
Logical operators are used to determine the logic between variables or values

### Logical and(&&)
Returns true if both statements are true

### Logical or(||)
Returns true if one of the statements is true

### Logical not(!)
Reverse the result, returns false if the result is true

## Bitwise Operators
Bitwise operators are used to perform binary logic with the bits of an integer or long integer.

### AND(&)
Sets each bit to 1 if both bits are 1

### OR(|)
Sets each bit to 1 if any of the two bits is 1

### NOT(~)
Inverts all the bits

### XOR(^)
Sets each bit to 1 if only one of the two bits is 1

### Zero-fill left shift(<<)
Shift left by pushing zeroes in from the right and letting the leftmost bits fall off

### Signed right shift(>>)
Shift right by pushing copies of the leftmost bit in from the left and letting the rightmost bits fall off

### Zero-fill right shift(>>>)
Shift right by pushing zeroes in from the left and letting the rightmost bits fall off

## What is the different between & and &&?
1. & is a bitwise operator and compares each operand bitwise. It is a binary AND Operator and copies a bit to the result if it exists in both operands.
2. && is a logical AND operator and operates on boolean operands. If both the operands are true, then the condition becomes true otherwise it is false.
