目的是让代码更少，更好理解
1. 合乎逻辑，没有反逻辑反直觉的东西
2. 更少的逻辑，就是说更少的if else
3. 更准确的现实世界映射，更准确的命名
4. 更抽象，一段代码可以做更多的东西
6. 需要记忆的东西更少，对于阅读一块有完整逻辑的代码
7. 当具体的实现需要修改时，影响的单元最小，就是修改的class数量最少，每一个被修改的class都需要再一次单元测试

# 创建型模式 ConCreate

在这里我们了解，Abstract Factory, Builder, Factory Method, Prototype, Singleton

最简单的构建模式
val foo = Foo()
foo的方法实现，在运行时都是确定的 

但问题是，难道不是所有的对象都是这样构建的吗？我们有其他选择吗？
是这样的，但是在这里我们只考虑在一个代码块内

```
{
    val foo = ? 
    foo 的行为是怎么样的
}
```

## Abstract Factory
1. kit

```
(factory:FactoryInterface) {

    val foo = factory.createFoo()
    val bar = factory.createBar()
    
    对于foo有两种或以上不同的实现，但是这里我们不需要区分
    不同的调用这段代码就可以实现不同的功能
    
}
```


## Builder

```
{

    val foo = FooBuilder()
                .part1()
                .part2()
                .build()
    
    对于一个比较难构建的对象我们可以选择这种方式
    
}
```

## Factory Method
as "Virtual Constructor"

```
{

    val foo = createFoo()
    
    由子类决定使用foo的哪一种实现
    
}
```

## Prototype


```
(foo:Foo) {

    val newFoo = foo.copy()
    
    克隆出一个相同的对象，原来的对象保持不变
   
}
```

## Singleton

```
{

    val foo = foo.getSigleton()
    
    一个进程只有一个foo对象
   
}
```
