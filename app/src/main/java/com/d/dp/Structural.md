## Structural Patterns

```
fooSay(foo:Foo) {
    foo.hello()
}
```
真正hello代码在哪里
1. Foo直接实现
2. Foo的还是子类实现，还是Foo有调用了其他对象的hello()方法 

最简单的方式，我们可以使用继承给出 foo 的不同实现。

但是，这时子类和父类被绑定在一起，之后在修改子类和父类的时候就出现更多的限制。

### Adapter
as "Wrapper"

```

class BarFoo(bar:Bar) : Foo {
    hello() {
        // add some thing
        bar.hello()
        // add some thing
    }
}

在不修改Bar的情况下，让bar实现了Foo的接口

fooDo(BarFoo(bar))

```

### Bridge
as Handle/Body

单一的继承关系无法满足复杂的需求
抽象方法的实现有时需要在运行时决定
有两套继承逻辑，这两套逻辑的实现是交叉的
....


```
class Foo() {
    hello()
}

class FooImp() {
    hello()
    niHao()
}

Foo 和 FooImp 之间的关系就是 Bridge, 他们之间没有具体的依赖组合关系

class WhiteFooImp() : FooImp() {}
class BlackFooImp() : FooImp() {}

Foo 的子类可以自由组合 Foo 的具体实现

class GirlFoo() : Foo() {
    fooImp = WhiteFooImp()
}
class BoyFoo(fooImp:FooImp) : Foo() {}

```

### Composite

例如　View ViewGroup,
ViewGroup 可以互相组合
View 虽然继承 ViewGroup, 但是做了功能的退化

所以子类不一定要完全实现父类的方法
如果需要可以设计残疾的子类

```
class Box() {
    boxs = Box[]
}

class Block() : Box() {
    boxs = null
}

```

### Decorator
as Wrapper

在不改变原始类的情况下对他进行　`扩展`(这是目的不同于adapter的地方)

```

class Foo() {}

class HelloDecorator(foo:Foo) : Foo() {
    hello()
}

class ByeDecorator(foo:Foo) : Foo() {
    bye()
}

```

### Facade
为子系统提供一系列接口通过一个类
接口的定义要在一个higher-level 为了更方便的使用

### Flyweight
当对象的元素比较小时，在一个进程中我们可能会创建大量对象
这时我们可以制作一个对象池，共享这些对象，减少内存的占用

例如java 中的 ` string constant pool.`

### Proxy
as Surrogate

我们什么情况下需要代理
1. 当我们需要一个对象，但是无法立即获取它。如需要下载的image
2. 但一个对象很大，我们暂时不需要完全加载他


```

class FooProxy() {
    val smartReference
    
    通过smartReference proxy 可以获得真实的Foo
}


```