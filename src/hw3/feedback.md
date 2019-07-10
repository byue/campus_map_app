### HW3 Feedback

**CSE 331 17sp**

**Name:** <Bryan Yue> (byue)

**Graded By:** <Stephanie Yuan> (<jingyy3@cs.washington.edu>)

### Score: 93/100
---

**Problem 3 - HolaWorld:** 5/5


**Problem 4 - RandomHello:** 9/10
- -1 Should use rand.nextInt(array.length) instead of rand.nextInt(5). That way there will be one less literal to modify if the array is modified later.

 
**Problem 5 - Testing (Fibonacci) Java Code with JUnit :** 5/5



**Problem 6 - Answering Questions about the (Fibonacci) Code:** 15/15

- Question 1: 5/5

- Question 2: 5/5

- Question 3: 5/5


**Problem 7 - Implementation:** 59/65

- Ball.java: 5/5

- BallContainer.java:  23/25

  -2: getVolume() returns a double value whereas in your implementation returns an int. The integer truncation will cause the test to fail when there's a ball of double volume is added to the container.
  
- Box.java: 31/35

  -2: Incorrect use of TreeSet. When there're two unique balls of same volume added to the set, TreeSet will consider they are the same thus one ball isn't added to the set.
 -2: Failed IllegalAdd test.
 
