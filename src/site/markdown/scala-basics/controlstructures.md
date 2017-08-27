## Control Structures

A brief overview of Scalas control structures is discussed in this chapter. Scalas control structures allow to write concise code, and on the same time provide enough functionality to do the job.

### If Statements

_If_ statements serve for the purpose to choose between two alternatives. The _if_ statement evaluates a condition, and depending on this evaluation (it returns a _boolean_ value) it continues the execution either in the first or second branch.

This is a very common concept, found in all languages in some form or another.

Unlike in imperative languages, _if statements_ return a value:

~~~
val cond = true
val i = if (cond) 1 else 2
~~~

In the example above, i would be of value _1_. 

~~~
val r = 
   if (true) {
     ...
     x
   } else {
     ...
     y
   }
~~~

again, value _r_ would be of value _x_ here. 

What you have to consider is that code is executed only if the branch is really selected. In the example above for example, it is clear, already at compile time, that only the first branch is executed, no matter how often or in which context the code chunk resides. That means that the second branch is never evaluated, and thus could be (and in fact is) removed from the final program. 

Todays compiler aggressively analyze input programs for such optimizations, in order to provide the smallest and most efficient binary possible. This is an example for a very basic and easily understandable optimisation, but todays compilers and runtimes are far more capable of doing program optimisations. 

Important takeaway is that _if_ statements return a value and are a tool to choose between two code paths.

### For Expressions

Unlike in other languages, the _for_ expression in scala is much more powerful than just simply "looping" over a range of values. In fact, the _for_ expression in scala serves as a syntactic sugar for doing more complex programming tasks, like we will see in the examples below.

#### Iterate over a range of values

~~~
for (i <- 1 to 10) println(i)
~~~

Output:

~~~
1
2
3
4
5
6
7
8
9
10
~~~

This example shows one of the simplest forms to use the _for_ expression. But there is already quite some machinery involved. First, we use a _range_ expression to generate values from _1_ to _10_. Those values are then bound to the value _i_. In each Iteration, there is a side effect by printing out the current value of _i_ to the console, which yields then the output which you can see above.

The range statement (_1 to 10_) generates a _Range_ object which can serve as an generator for input values of the for expression. As one would expect, we can use other generators as well:

~~~
for (o <- List(1,2,3,4)) println(o)
for (u <- Array(10,20,30)) println(u)
for (x <- Seq("a","b","c")) println(u)
~~~

_List_, _Array_ and _Seq_ can be _traversed_, and one intuitively can use this property to bind values to the given value (_o_, _u_ and _x_). 

The data structures _List_, _Array_, _Seq_ and others are part of the standard collection library of Scala, and are tailored to be used with the _for_ expression. There are more _collection_ types, and you can even implement your own classes which can be traversed in the way described above. 

#### Iterate over more than one collection 

To give you an idea of the power of the _for_ expression, here is another example of what you can do:

~~~
for {i <- 1 until 10
     o <- Seq("a","b","c")
     u <- Vector(true,false)} println(s"$i: $o -> $u")
~~~

Try this out in the _REPL_, you will see that the _for_ expression can iterate over more than one collection. In fact, this idiom is used on a daily basis and is a very important tool to tackle programming problems in Scala.

#### Conditional evaluation

The for expression contains even more goodies:

~~~
for {i <- 1 to 100 if i % 3 == 0} println(i)
~~~

This shows that you can filter out values from the original generator, and only evaluate a subset of the original traversable sequence. In the example above, only values are printed which can be divided with _3_.

#### Returning results

Like the _if_ statement, also _for_ expressions can return results. This is a big win over other imperative languages, and comes in very handy if you want to transform data. In order to return values, one has to use the _yield_ keyword.

~~~
for {i <- 1 to 100 if i % 3 == 0} yield i
~~~

This returns a _Vector_ of values between 1 and 100 which can be divided with _3_. 

~~~
scala> for {i <- 1 to 100 if i % 3 == 0} yield (i)
res0: scala.collection.immutable.IndexedSeq[Int] = Vector(
3, 6, 9, 12, 15, 18, 21, 24, 27, 30, 33, 
36, 39, 42, 45, 48, 51, 54, 57, 60, 63, 
66, 69, 72, 75, 78, 81, 84, 87, 90, 93, 
96, 99)
~~~

### Try Catch Finally Clauses

Scala supports also _try_ - _catch_ statements known from _Java_, and again they can return values.

~~~
val resource = ...
val result = 
  try {
    f(resource)
  } catch {
    case _ : Throwable => // handle exception
  } finally {
    resource.close()
  }
~~~

The example above shows a common pattern for _try_ - _catch_ - _finally_ expressions. 

Lets assume that function _f()_  can throw an exception. If it doesn't, result will be the value which was returned by _f_.
If it throws an exception, one can handle this situation with the _case_ clauses and and possibly return a fallback value. 

In any case, the _finally_ block is executed and thus this is the place to put statements which should be executed no matter if an exception was thrown or not.

For this to make sense you have to recall that if some code throws an exception, the program execution stops at the statement and following statements are not executed in the given code block. As such, a _resource.close()_ wouldn't be executed. Have a look at following example:

~~~
val resource = ...
val result = 
  try {
    f(resource)  // 
    resource.close() // won' be called
                     // in case of an
                     // exception
  } catch {
    case _ : Throwable => // handle exception
  }
~~~

In this code, if _f_ throws an exception, the program execution will continue instantly in the _case_ statement, and the resource won't be closed. This is called a _resource leak_ and is often a source of a _memory leak_ and as such not a desirable behavior. 
  

### Pattern Matching

We've used pattern matching already when we spoke about _try_ - _catch_ clauses. In fact, _pattern matching_ is one cornerstone of programming in scala and as such ubiquitous. You can think of pattern matching as generalized _switch_ statement.

For example:

~~~
val x = 2
x match {
 case 0 => println("x is 0")
 case 1 => println("x is 1")
 case 2 => println("x is 2")
 case _ => println("x is some other value")
}
~~~

Here we have a value of type _Int_ which we pattern match against. The approach is always the same:

~~~
value match {
  <case clause 1>
  <case clause 2>
  <case clause 3>
}
~~~

That is, you enumerate your queries one after another, and 'ask' if _value_ has this or that value. The sequence of the case clauses is significant, meaning that the first match, from top to bottom, wins. 

Like the _for_ expressions, pattern matching is again a very important concept when doing programming in Scala. Pattern matching is a succinct way of decomposing a type, and can be seen as the counterpart of the concept of _constructors_. 

Have a look at following example:

~~~
sealed trait Expr
case class Factor(i : Int) extends Expr
case class Binary(left: Expr, right: Expr) extends Expr

def eval(expr : Expr) : String = { 
 expr match {
  case Factor(i) => s"$i"
  case Binary(left,right) => "B[" + eval(left) + "#" + eval(right) + "]"
 }
}

eval(Factor(1)) 
eval(Binary(Factor(1),Factor(2)))
eval(Binary(Binary(Factor(1),Factor(2)), Binary(Factor(3),Factor(4))))  
~~~

Here, we first create a _sealed trait_ hierachy containing two types, _Factor_ and _Binary_. Then, we define a simple function which takes an _Expr_ datatype and returns a _String_ datatype. The implementation is recursive and deconstructs the given _Expr_ depending on its _real_ datatype (either _Factor_ or _Binary_) and either returns a _String_ or calls the _eval_ function again using the newly bound values (_left_ and _right_). 

This small example shows how elegant one can deconstruct a given type, by simultaneously bind values to the deconstructed type. 

 