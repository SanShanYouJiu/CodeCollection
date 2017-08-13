# 第四章 锁的优化及注意事项

## 4.1 有助于提高“锁”性能的几点建议

### 4.1.1 减少锁持有时间

程序开发应尽可能的减少对某个锁的占用时间 以减少程序互斥的可能 
```java
 
 public  synchronized void syncMethod(){
   othercode1();
   mutextMethod();
   othercode2();
 }

```
这里假设只有mutextMethod方法是有同步需要的  而othercode1()和othercode2()并不需要同步控制 如果othercode1和othercode2是重量级的方法的话  使用整个个方法做同步 会导致等待线程大量增加 因为一个线程 在进入该方法时获得内部锁 只有在所有任务都执行完后 才会释放锁

一个较为优化的解决方案是 只在必要时进行同步 这样就能明显减少线程持有锁的时间 提高系统的吞吐量

```java
public void syncMethod2(){
    othercode1();
    synchronized(this){
        mutextMethod();
    }
}
```
 
在改进的代码中 只针对mutextMethod()方法做了同步 锁占用的时间相对较短 因此能有更改的并行度 这种技术手段在JDK的源码包中也可以很容易地找到 比如处理正则表达式的Pattern类
> 减小锁的持有时间有助于减低锁冲突的可能性 进而提高系统的并发能力

### 4.1.2 减小锁粒度
减小锁粒度也是削弱多线程锁竞争的有效手段 这种技术典型的使用场景就是ConcurrentHashMap类的实现 在3.3节中介绍了这个类 但是没有仔细的介绍 这节仔细的介绍一下

对于HashMap来说 最重要的俩个方法就是get()和put()。一种最自然的方法就是对HashMap加锁  必然可以得到一个线程安全的对象 但是这样做 我们就认为加锁粒度太大了 对于ConcurrentHashMap 它内部进一步细分为若干个小的HashMap 称之为段(SEGMENT) 默认情况下 一个ConcurrentHashMap被进一步细分为16个段 

如果需要在ConcurrentHashMap中增加一个新的表项 并不是将整个HashMap加锁 而是首先根据hashcode得到该表现应该存放到哪个段中 然后对该段加锁 并完成put()操作 只要被加入的表项不存放在同一个段中 则线程间便可以做到真正的并行 
  
  但是 减小锁粒度会引入一个新的问题 即：当系统需要取得全局锁时 其消耗的资源会比较多 仍然以ConcurrentHashMap类为例 虽然其put()方法很好地分离了锁 但是当试图访问ConcurrentHashMap全局信息时  就会需要同时取得所有段的锁方能顺利实施 比如ConcurrentHashMap的size()方法 它将返回ConcurrentHashMap的有效表项的数量 即ConcurrentHashMap的全部有效表项之和 要获取这个信息需要取得所有子段的锁 
  
  事实上 size()方法会先使用无锁的方式求和 如果失败才会尝试加锁的方法 但不管怎么说 在高并发场合ConcurrentHashMap的size()的性能依然要差于同步的HashMap
  
  因此 只有在类似size()获取全局信息的方法调用并不频繁时 这种减小锁粒度的方法才能真正意义上提高系统吞吐量
  

> ConcurrentHashMap在JDK1.8版本中大规模的重构了 这里的笔记只适用于JDK1.7版本

> 所谓减少锁粒度 就是指减少锁定对象的访问 从而减少锁冲突的可能性 进而提高系统的并发能力

### 4.1.3 读写分离锁来替换独占锁

使用ReadWriteLock可以提高系统的性能 使用读写分离锁来替代独占锁是减小锁粒度的一种特殊情况 那么 读写锁则是对系统功能点的分割

在读多写少的场合 读写锁对系统性能还是很有好处的 因为如果系统在读写数据时均只使用独占锁 那么读操作和写操作间 写操作和写操作间均不能做到真正的并发 并且需要互相等待 而读操作本身不会影响数据的完整性和一致性 因此 理论上讲 在大部分情况下 应该可以运行多线程同时读，读写锁正是实现了这种功能 

> 在读多写少的场合 使用读写锁可以有效提示系统的并发能力

### 4.1.4 锁分离

如果将读写锁的思想做进一步的延伸  就是锁分离 读写锁根据读写操作功能的不同进行了有效的锁分离 依据应用程序的功能特点 使用类似的分离思想 也可以对独占锁进行分离 一个典型的案例就是java.util.LinkedBlockingQueue的实现

在LinkedBlockingQueue的实现中 take()函数和put()函数分别实现了从队列中取得数据和往队列中增加数据的功能 虽然俩个函数都对当前队列进行了修改操作 但由于LinkedBlockingQueue是基于链表的 因此 俩个操作分别作用域队列的前端和尾端  从理论上说  俩者并不冲突

如果使用独占锁 则要求俩个操作进行时获取当前队列的独占锁 那么take()和put()操作就不可能真正的并发 在运行时 它们会彼此等待对方释放锁资源 在这种情况下 锁竞争会相对比较激烈 从而影响程序在高并发时的性能
因此 在JDK的实现中 并没有采用这样的方式 取而代之的是俩把不同的锁 分离了take()和put()操作


    /** Lock held by take, poll, etc */
        private final ReentrantLock takeLock = new ReentrantLock();
    
        /** Wait queue for waiting takes */
        private final Condition notEmpty = takeLock.newCondition();
    
        /** Lock held by put, offer, etc */
        private final ReentrantLock putLock = new ReentrantLock();
    
        /** Wait queue for waiting puts */
        private final Condition notFull = putLock.newCondition();
        
  以上代码片段 定义了takeLock和putLock 它们分别在take()操作和put()操作中使用 因此 take()函数和put()函数就此相互独立 它们之间不存在锁竞争关系 只需要在take()和take()间,put()和put()间分别对takeLock和putLock进行竞争 从而 削弱了锁竞争的可能性     
   
```java
 public E take() throws InterruptedException {
        E x;
        int c = -1;
        final AtomicInteger count = this.count;
        final ReentrantLock takeLock = this.takeLock;
        takeLock.lockInterruptibly(); //不能有俩个线程同时取数据
        try {
            while (count.get() == 0) {//如果当前没有可用数据 一直等待
                notEmpty.await(); //等待 put()操作的通知
            }
            x = dequeue();//取得第一个数据
            c = count.getAndDecrement(); //数量减一 原子操作 因为会和put()函数同时访问count 注意：变量c是count减一前的值 
            if (c > 1)
                notEmpty.signal();//通知其他take()操作
        } finally {
            takeLock.unlock();//释放锁
        }
        if (c == capacity)
            signalNotFull();//通知put()操作 已有空余空间
        return x;
    }
```

函数put()的实现如下
  
```java
  public void put(E e) throws InterruptedException {
        if (e == null) throw new NullPointerException();
        int c = -1;
        Node<E> node = new Node<E>(e);
        final ReentrantLock putLock = this.putLock;
        final AtomicInteger count = this.count;
        putLock.lockInterruptibly(); //不能有俩个线程同时进行put()
        try {
            while (count.get() == capacity) {//如果队列满了
                notFull.await();//等待
            }
            enqueue(node);//插入数据
            c = count.getAndIncrement();//更新总数 变量c是count加1前的值
            if (c + 1 < capacity)
                notFull.signal();//有足够的空间 通知其他线程
        } finally {
            putLock.unlock();//释放锁
        }
        if (c == 0)
            signalNotEmpty();//插入成功后 通知take()操作取数据
    }

```
通过takeLock和putLock俩把锁 LinkedBlockingQueue实现了取数据和写数据的分离 使俩者在真正意义上成为可并发的操作

### 4.1.5 锁粗化

通常情况下 为了保证多线程间的有效并发 会要求每个线程持有锁的时间尽量短 即在使用完公共资源后 应该立即释放锁 只有这样  等待在这个锁上的其他线程才能尽早的获得资源执行任务 但是 如果对同一个锁不停的进行请求，同步和释放 其本身也会消耗系统宝贵的资源 反而不利于性能的优化

为此 虚拟机在遇到一连串连续对同一锁不断进行请求和释放的操作时，便会把所有的锁操作整合成对锁的一次请求 从而减少对锁的请求同步次数 这个操作叫锁的粗化 
```java
public void demoMethod(){
    synchronized(){
    // do sth
}

//做其他不需要的同步的工作 但能很快执行完毕
synchronized(lock){
    //do sth
}
}
```

会被整合为如下形式
```java

public void demoMethod(){
   //整合成一次锁请求
   synchronized(lock){
       //do sth
       //做其他不需要的同步的工作 但能很快执行完毕
   }
}
```
在开发过程中 大家也应该有意识地在合理的场合进行锁的粗化 尤其当在循环内请求锁时 以下是一个循环内请求锁的例子 在这种情况下 意味着每次循环都有申请锁和释放锁的操作 但在这种情况下 显然是没有必要的
```java
for(int i =0;i<CIRCLE;i++){
    synchronized(lock){
        
    }
}

```
所以 一种更合理的做法应该是在外层只请求一次锁
```java
synchronized(lock){
for(int i=0;i<CIRCLE;i++){
    
}
}
```
>注意 性能优化是根据运行时的真是情况对各个资源点进行权衡折中的过程 锁粗化的思想和减少锁持有时间是相反的 但在不同的场合 它们的效果并不相同 所以大家需要根据实际情况 进行权衡
## 4.2 Java虚拟机对锁优化所做的努力

### 4.2.1 锁偏向
 
 锁偏向是一种针对加锁操作的优化手段 它的核心思想是：如果一个线程获得了锁 那么锁就进入了偏向模式  当这个线程再次请求锁时 无须再做任何同步操作 这样就节省了大量相关锁申请的操作 从而提高了程序性能  因此 对于几乎没有锁竞争的场合 偏向锁有比较好的优化效果 因为连续多次极有可能是同一个线程请求相同的锁 而对于锁竞争比较激烈的场合  其效果不佳  因为在竞争激烈的场合 最有可能的情况是每次都是不同的线程来请求相同的锁 这样偏向模式会失效 因此还不如不启用偏向锁 使用Java虚拟机参数-XX:+UseBiasedLocking可以开启偏向锁
 
### 4.2.2 轻量级锁
 如果偏向锁失败 虚拟机并不会立即挂起线程 它还会使用一种称之为轻量级锁的优化手段,轻量级锁的操作也很轻便 它只是简单的将对象头部作为指针  指向持有锁的线程堆栈的内部 来判断一个线程是否持有对象锁 如果线程获得轻量级锁成功 则可以顺利进入临界区 如果轻量级锁加锁失败 则表示其他线程抢先争夺到了锁 那么当前线程的锁请求就会膨胀为重量级锁
 
 [偏向锁与轻量锁的讲解][1]
### 4.2.3 自旋锁
 锁膨胀后，虚拟机为了避免线程真实地在操作系统层面挂起 虚拟机还会在做最后的努力
---自旋锁 由于当前线程暂时无法获得锁 但是什么时候可以获得锁是一个未知数 
 也许在几个CPU时钟周期后 就可以得到锁 如果这样 简单粗暴地挂起线程可能是一种得不偿失的操作 因此 系统会进行一次赌注：它会加上在不久的将来 线程可以得到这把锁 因此 虚拟机会让当前线程做几个空循环（这也是自旋的含义）在经过若干次循环后 如果可以得到锁 那么就顺利进入临界区 如果还不能获得锁 才会真实地将线程在操作系统层面挂起
 
### 4.2.4 锁消除
 锁消除是一种更彻底的锁优化 Java虚拟机在JIT编译时  通过对运行上下文的扫描 去除不可能存在共享资源竞争的锁 通过锁消除 可以节省毫无意义的请求锁时间 
 
 如果不可能存在竞争 为什么程序还要加上锁呢 这是因为在Java软件开发的过程中 我们必然会使用一些JDK的内置API，比如StringBuffer，Vector等 你在使用这些类的时候  也许根本不会考虑这些对象到底内部是如何实现的 比如 你很有可能在一个不可能存在并发竞争的场合使用Vector 而众所周知 Vector内部使用了synchronized请求锁

```java
 public String[] createStrings(){
  Vector<String> v =new Vecotr<String>();
  for(int i=0;i<100;i++){
   v.add(Integer.toString(i);
  }
  return v.toArray(new String[]{});
 }
```
比如在这种情况下 Vector的实例对象v只是一个局部变量 局部变量是在栈上的 属于线程私有的数据 因此不可能被其他线程访问 所以 在这种情况下 Vector内部所有加锁同步都是没有必要的 如果虚拟机检测到这种情况 就会将这些无用的操作去除

锁消除涉及的一项关键技术为逃逸分析 所谓逃逸分析就是观察某一个变量是否会逃出某一个作用域
在本例中 变量v显然没有逃出createStrings()函数之外 以此为基础 虚拟机才可以大胆地将v内部的加锁操作去除 如果createStrings()返回的不是String数组 而是v本身 那么就认为变量v逃逸出了当前函数 也就是说v有可能被其他线程访问 如果是这样 虚拟机就不能消除v中的锁操作

逃逸分析必须要在-server模型下进行 可以使用-XX:DoEscapeAnalysis参数打开逃逸分析 使用-XX:+EliminateLocks参数可以打开锁消除
## 4.3 人手一只笔：ThreadLocal
 除了控制资源的访问外 我们还可以通过增加资源来保证所有对象的线程安全 
### 4.3.1 ThreadLocal的简单实用
从ThreadLocal的名字上可以看到 这是一个线程的局部变量 也就是说只有当前线程可以访问 既然是只有当前线程可以访问的数据 自然是线程安全的

下面看一个简单的示例
相关代码请见 [ThreadLocalDemo][2]

从这里也可以看到 为每一个线程人手分配一个对象的工作并不是由ThreadLocal来完成的 而是需要在应用层面保证的 如果在应用上为每一个线程分配了相同的对象实例 那么ThreadLocal也不能保证线程安全 这点也需要大家注意

>注意：为每一个线程分配不同的对象 需要在应用层面保证 ThreadLocal只是起到了简单的容器作用


### 4.3.2 ThreadLocal的实现原理
我们需要关注的 自然是ThreadLocal的set()方法和get()方法 从set()方法说起
```java
  public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }   
```
可以看到这个方法set时 先获得当前线程对象  然后通过getMap()拿到线程的ThreadLocalMap，并将值设入ThreadLocalMap 而ThreadLocalMap就理解为一个Map就好   但是它是定义在Thread内部的成员 
    
    ThreadLocal.ThreadlocalMap threadLocals =null;
    
 而设置到ThreadLocal中的数据 也正是写入了threadLocals这个Map 其中 key为ThreadLocal当前对象 value就是我们需要的值 而threadLocals本身就保存了当前所在线程的所有“局部变量”，也就是一个ThreadLocal变量的集合

在进行get()操作时 自然就是将这个Map中的数据拿出来

```java
     public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
```   
 
首先 get()方法也是先取得当前线程的ThreadLocalMap对象 然后 通过将自己做为key取得内部的实际数据
在了解ThreadLocal的内部实现后 我们自然会引出一个问题 那就是这些变量是维护在Thread类内部的（ThreadLocalMap定义所在类)，这也意味着只有线程不退出 对象的引用将一直存在
当线程退出时 Thread类会进行一些清理工作 其中就包括清理ThreadLocalMap 

```java
    /**在线程退出前 由系统回调 进行资源清理
    /
     private void exit() {
            if (group != null) {
                group.threadTerminated(this);
                group = null;
            }
            target = null;
            //加速资源清理
            threadLocals = null;
            inheritableThreadLocals = null;
            inheritedAccessControlContext = null;
            blocker = null;
            uncaughtExceptionHandler = null;
        }
```
因此 如果我们使用线程池 那就意味着当前线程未必会退出（比如固定大小的线程池，线程总是存在） 如果这样 将一些大大的对象设置到ThreadLocal中(它实际保存在线程持有的ThreadLocal Map内) 可能会使系统出现内存泄露的可能(这里的意思是：你设置对象到ThreadLocal中 但是不清理它 在你使用几次后 这个对象也不再有用了 但是它却无法被回收)
此时 如果你希望及时回收对象 最好使用ThreadLocal.remove()方法将这个变量移出 就像我们有时候为了加速垃圾回收 会特意写出类似obj=null的代码 如果这么做  obj指向的对象就会更容易的被垃圾回收器发现 从而加速垃圾回收

 同理 如果对于ThreadLocal的变量 我们也手动将其设置为null 比如tl=null 那么这个ThreadLocal对于的所有线程的局部变量都有可能被回收 
 
 相关代码请见 [ThreadLocalDemo_GC][3]

要了解这里的回收机制 我们需要更进一步了解ThreadLocal.ThreadLocalMap的实现  ThreadLocalMap是一个类似WeakHashMap的东西

ThreadLocalMap的实现使用了弱引用 弱引用是比强引用弱的多的引用  Java虚拟机在垃圾回收时 如果发现弱引用 就立即回收 ThreadLocalMap内部是由一系列Entry构成 每一个Entry都是WeakReference<ThreadLocal>
```java
  static class Entry extends WeakReference<ThreadLocal<?>> {
            /** The value associated with this ThreadLocal. */
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
```
  这里的参数k就是Map的key v就是Map的value 其中k也就是ThreadLocal实例 作为弱引用使用(super(k)就是调用了WeakReference的构造函数) 因此 虽然这里使用ThreadLocal作为Map的key 但是实际上 它并不真的持有ThreadLocal的引用 而当ThreadLocal的外部强引用被回收时 ThreadLocalMap中的key就变为null 当系统进行ThreadLocalMap清理时（比如将新的变量加入表 就会自动进行一次清理 虽然JDK不一定会进行一次彻底的扫描但显然在我们这个案例中 它奏效了） 就会自然将这些垃圾数据回收 
### 4.3.3 对性能有何帮助

为每一个线程分配一个独立的对象对系统性能也许是有帮助的 当然 这也不一定 这完全取决于共享对象的内部逻辑 如果共享对象对于竞争的处理容易引起性能损失
我们还是应该考虑使用ThreadLocal为每个线程分配单独的对象 一个典型的案例就是在多线程下使用随机数

相关代码请见  [RandomThreadLocalTest][4]

## 4.4 无锁
人是分为乐天派与悲观派的 那么对并发控制的处理也是分为乐观与悲观的

 锁就是一种悲观的策略 它总是假设每一次的临界区操作会产生冲突，因此，必须对每次操作都小心翼翼 如果有多个线程同时需要访问临界区资源 就宁可牺牲性能让线程进行等待 所以说锁会阻塞线程执行 
 
 而无锁是一种乐观的策略 它总是假设对资源的访问是没有冲突的 既然没有冲突 自然不需要等待 所以所有的线程都可以在不停顿的状态下持续执行 那遇到冲突怎么办?无锁的策略使用一种叫比较交换的技术（CAS CompareAndSwap)来鉴别线程冲突 一旦检测到冲突产生 就重试当前操作直到没有冲突为止
 
### 4.4.1 与众不同的并发策略:比较交换（CAS)
与锁相比 使用比较交换 简称为CAS会使程序看起来复杂一些 但由于其非阻塞性 它对死锁问题天生免疫 并且 线程间的相互影响也远远比基于锁的方式要小 更为重要的是  使用无锁的方式完全没有锁竞争代理的系统开销 也没有线程间频繁调度带来的开销 因此  它要比基于锁的方式拥有更优越的性能
 
 CAS算法的过程是这样的：它包含3个参数CAS(V,E,N),V表示要更新的变量 E表示预期值 N表示新值 仅当V值等于E值时 才会将V的值更新为N 如果V值和E值不同 则说明已经有其他线程做了更新 则当前线程什么都不做 最后 CAS返回当前V的真实值 CAS操作是抱着乐观的态度进行的 它总是认为自己可以独立完成操作
  当多个线程同时使用CAS操作一个变量时 只有一个会胜出 并成功更新 其他均会失败 失败的线程不会被挂起 仅是被告知失败  并且允许再次尝试 当然也允许失败的线程放弃操作 基于这样的原理 CAS操作即使没有锁 也可以发现其他线程对当前线程的干扰 并进行恰当的处理 
  
  简单的说,CAS需要你额外给出一个期望值 也就是你认为这个变量现在应该是什么样子的 如果变量不是你想象的那样 那说明它已经被别人修改过了 你就重新读取 再次尝试修改就好了 
  
  在硬件层面 大部分的现代处理器都已经支持原子化的CAS指令 在JDK5.0以后 虚拟机便可以使用这个指令来实现并发操作和并发数据结构 并且 这种操作在虚拟机中可以说是无处不在
  
### 4.4.2 无锁的线程安全整数 AtomicInteger
为了让Java程序员能够受益于CAS等CPU指令 JDK并发包中有一个atomic包 里面实现了一些直接使用CAS操作的线程安全类型

其中  最常用的一个类 应该就是AtomicInteger 你可以把它看做是一个整数 但是与Integer不同 它是可变的 并且是线程安全的 对其进行修改等任何操作 都是用CAS指令进行的 这里简单列举一些AtomicInteger的一些主要方法 对于其他原子类 操作也是非常相似的

    public final int get()//取得当前值
    public final void  set(int newValue)//设置当前值
    public final int  getAndSet(int newValue)//设置新值 并返回旧值
    public final boolean compareAndSet(int expect,int u)//如果当前值为expect 则设置为u
    public final int getAndIncrement()//当前值加1并返回旧值
    public final int getAndDecrement()//当前值减1并返回旧值
    public final int getAndAdd(int delta)//当前值增加delta，返回旧值
    public final int incrementAndGet() //当前值加1 返回新值
    public final int decrementAndGet() //当前值减1 返回新值
    public final int addAndGet(int delta)//当前值增加delta 返回旧值
    
 就内部实现上来说 AtomicInteger中保存了一个核心字段
    
        private volatile int value;
 
  它代表了AtomicInteger的当前实际值 此外还有一个
  
     pirvate static final long valueObject;
  
 它保存了value字段在AtomicInteger对象中的偏移量 后面你会看到 这个偏移量是实现AtomicInteger的关键
 
 下面的代码是AtomicInteger的使用示例
 
 相关代码请见 [AtomicIntegerDemo][5]
  
  使用AtomicInteger会比使用锁有更好的性能 这里就不进行测试了
  
  和AtomicInteger类似的类还有AtomicLong用来代码long类型 AtomicBoolean表示boolean型 AtomicReference表示对象引用
  
### 4.4.3 Java中的指针:Unsafe类
     
     public final boolean compareAndSet(int expect, int update) {
            return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
        }
  在这里，我们看到了一个特殊的变量unsafe 它是sun.misc.Unsafe类型  从名字看 这个类应该是封装了一些不安全的操作 那什么操作是不安全的呢 学习过C或者c++都知道 指针是不安全的 这也是在Java中把指针去除的重要原因 如果指针指错了位置或者计算指针偏移量出错 结果可能是灾难性的 你很有可能覆盖别人的内存 导致系统崩溃
  
而这里的Unsafe就是封装了一些类似指针的操作 compareAndSwapInt()方法是一个natvie 方法 它的几个参数含义如下

    public final native boolean compareAndSwapInt(Object o, long offset, int expected, int x);
    
 第一个参数o为给定的对象 offset为对象内的偏移量(其实就是一个字段到对象头部的偏移量 通过这个偏移量可以快速定位字段),expected表示期望值 x表示要设置的值 如果指定的字段的值等于expected 那么就会把它设置为x
  
  不难看出,compareAndSwapInt()方法的内部 比如是使用CAS原子指令完成的  此外 Unsafe类还提供了一些方法 
  
    //获得给定对象偏移量上的int值
    public native int getInt(Object o,long offset);
    //设置给定对象偏移量上的int值
    public native void putInt(Object o,long offset,int x);
    //获得字段在对象中的偏移量
    public native void objectFieldOffset(Field f);
    //设置给定对象的int值 使用volatile语义
    public native void putIntVolatile(Object o,long offset,int x);
    //获得给定对象对象的int值，使用volatile语义
    public native int getIntVolatile(Object o,long offset);
    //和putIntVolatile()一样 但是它要求被操作字段就是volatile类型的
    public native void putOrderdInt(Object o,long offset,int x);
    
    
在之前的3.3.4 深度剖析ConcurrentLinkedQueue一节中描述的ConcurrentLinkedQueue实现 应该对ConcurrentLinkedQueue中的Node还有些印像 Node的有一些CAS操作也是使用Unsafe类来是实现的
这里就可以看到  虽然Java派遣了指针 但是在关键时刻 类似指针的技术还是必不可少的 这里底层的Unsafe实现就是最好的例子 但是很不幸 JDK的开发人员不希望大家使用这个类 获得Unsafe实例的方法就是调动其工厂方法getUnsafe()，但是他的实现却是这样的
    
     public static Unsafe getUnsafe() {
            Class var0 = Reflection.getCallerClass();
            if(!VM.isSystemDomainLoader(var0.getClassLoader())) {
                throw new SecurityException("Unsafe");
            } else {
                return theUnsafe;
            }
        }
        
  注意 这里的代码如果是ClassLoader不为null 就会抛出异常 拒绝工作 因此 这也使得我们自己的应用程序无法直接使用Unsafe类 它是一个JDK内部使用的专属类
  
 >注意：根据Java类加载器的工作原理 应用程序的类由AppLoader加载 而系统核心类 如rt.jar中的类由Bootstrap类加载器加载 Bootstrap加载器没有Java对象的对象  因此试图获得这个类加载器会返回null  所以  当一个类的类加载器为null时 说明它是Bootstrap加载的 而这个类极有可能是rt.jra中的类  

### 4.4.4 无锁的对象引用：AtomicReference
AtomicReference和AtomicInteger非常类似 不同之处就在与AtomicInteger是对整数的封装 而AtomicReference则对应普通的对象引用
也就是它可以保证你在修改对象引用是的线程安全性 

之前说过 线程判断被修改对象是否可以正确写入的条件是对象的当前值和期望值是否一致 这个逻辑从一般意义上是对的 但是有一个小小的意外 这个就是ABA问题 当你获得对象当前数据后 在准备修改为新值前  对象的值被其他线程连续修改了俩次 而经过这俩次修改后 对象的值又恢复为旧值 这样 当前线程就无法正确判断这个对象究竟是否被修改过 

一般来说 发生这种情况的概率很小 而且即使发生了 可能也不是什么大问题 比如 我们只是很简单地做一个数值加法 即使我在取得期望值后 这个数字被不断的修改 只要它最终改回了我的期望值 我的加法计算就不会出错 也就是说 当你修改个对象没有过程的状态信息 所有的信息都只保存与对象的数值本身

但是 在现实中 还可能存在另外一种场景 就是我们是否能修改对象的值 不仅取决于当前值 还和对象的过程变化有关 这时 AtomicRenference就无能无力了

举个例子 如果有一家点 为了挽留客户 决定为贵宾卡余额小于20元的客户一次性赠送20元 刺激消费者充值与消费
 但条件时  每个客户只能被赠送一次
 使用AtomicReference演示这个场景
 
 相关代码请见 [AtomicReferenceDemo][6]
 
 这里就会出现一个问题  用户正好在进行消费 就在赠予金额的同时 他进行了一次消费 使得总金额又小于20元 并且正好累计消费了20元 使得消费，赠予后的金额等于消费前，赠予前的金额 这时 后台的赠予进程就会误以为这个账户还没有赠予 所以 存在被多次赠予的可能 
 
    余额小于20元 充值成功，余额:39元
    大于10元
    成功消费10元，余额:29
    大于10元
    成功消费10元，余额:19
    大于10元
    成功消费10元，余额:9
    余额小于20元 充值成功，余额:29元
    大于10元
    成功消费10元，余额:39
    余额小于20元 充值成功，余额:39元
    
从输出中可以看到  这个账号先后被反复充值 其原因正是因为账号余额被反复修改 修改后的值等于原有的值 使得CAS操作无法正确判断当前数据状态

虽然说这种情况出现的概率不大 但是依然是有可能出现的 因此 当业务确实可能出现这种情况时 我们也必须多加防范 体贴的JDK也已经为我们考虑到了这种情况 使用AtomicStampedReference就可以很好地解决这个问题



 
### 4.4.5 带有时间戳的对象引用：AtomicStampedReference
 
 AtomicReference无法解决上述问题的根本是因为对象在修改的过程中 丢失了状态信息 对象值本身与状态被画上了等号 因此 我们只要能记录对象在修改过程中的状态值 就可以很好的解决对象被反复修改导致线程无法正确判断对象状态的问题
 
 AtomicStampedReference就是这么做的 它内部不仅维护对象值  还维护了一个时间戳（我这里把它称之为时间戳，实际上它可以使任何一个整数来表示状态值） 当AtomicStampedReference对应的数值被修改时 除了更新数据本身外 还必须要更新时间戳 当AtomicStampedReference设置新对象时 对象值以及时间戳必须满足期望值 写入才会成功 因此 即使对象值被反复读写 写回原值 只有时间戳发生变化 就能防止不恰当的写入

AtomicStampedReference的几个API在AtomicReference的基础上新增了有关时间戳的信息

    
    //比较设置 参数以此为：期望值 写入新值 期望时间戳 新时间戳
    public boolean compareAndSet(V expectedReference,V new Reference,int expectedStamp,int newStamp)
    //获得当前对象引用
    public V getReference()
    //获得当前时间戳
    public int getStamp9)
    //设置当前对象引用和时间戳
    public void set(V newReference,int newStamp)
    
有了AtomicStampedReference这个法宝 我们就再也不用担心对象被写坏
使用AtomicStampedReference来修正AtomicReferenceDemo的问题

相关代码请见 [AtomicStampedReferenceDemo][7]

我们使用AtomicStampedReference代替原来的AtomicReference 首先获得账户的时间戳 后续的赠予操作以这个时间戳为依据  如果赠予成功 则修改时间戳 使得系统不可能发生二次赠予的情况 消费线程也是类似 每次操作 都使得时间戳加1 使之不可能重复

### 4.4.6  数组也能无锁：AtomicIntegerArray 
 
 除了提供基本数据类型外 JDK还为我们提供了数组等复合结构 当前可用的原子数组有:AtomicIntegerArray,AtomicLongArray和AtomicReferenceArray,分别表示整数数组 long类型数组和普通的对象数组
 
 AtomicIntegerArray本质上是对int[]类型的封装 使用Unsafe类通过CAS的方式控制int[]在多线程下的安全性 它提供了以下几个核心API
 
    
    //获得数组第i个下标的元素
    public final int get(int i)
    //获得数组的长度
    public final int length()
    //将数组第i个下标设置为newVlaue，并返回旧的值
    public final int getAndSet(int i,int newValue)
    //进行CAS操作 如果第i个下标的元素等于expect，则设置为update，设置成功返回true
    public final boolean compareAndSet(int i,int expect,int update)
    //将第i个下标的元素加1
    public final int getAndIncrement(int i)
    //将第i个下标的元素减1
    public final int getAndDecrement(int i)
    //将第i个下标的元素增加delta(delta可以是负数)
    public final int getAndAdd(int i,int delta)
    
    
 
 相关代码请见 [AtomicIntegerArrayDemo][8]
 
### 4.4.7 让普通变量也享受原子操作:AtomicIntegerFieldUpdater

有时候,由于初期考虑不周 或者后期的需求变化 一些普通变量可能也会有线程安全的需求 如果改动不大 我们可以简单地修改程序中的每一个使用或者读取这个变量的地方 但显然，这样不符合软件设计中的一条重要原则 ---开闭原则 也就是系统对功能的增加应该是开发的 而对修改应该是相对保守的

所以在原子包里还有一个实用的工具类AtomicIntegerFieldUpdater 它可以让你不改动原有代码的基础上 让普通的变量也享受CAS操作带来的线程安全性 这样你可以修改极少的代码，来获得线程安全的保证

根据数据类型的不同 这个Updater有三种 分别是AtomicIntegerFieldUpdater,AtomicLongFieldUpdater和AtomicReferenceFieldUpdater 顾名思义 它们分别可以对int,long和普通对象进行CAS修改


相关代码请见 [AtomicIntegerFieldUpdaterDemo][9]


虽然AtomicIntegerField很好用 但是还有几个注意事项：

- 第一 Updater只能修改它可见访问内的变量 因为Updater使用反射 如果变量不可见 就会出错 比如如果score申明为private 就是不可行的

- 第二 为了确保变量被正确的读取 它必须是volatile类型的 如果我们原有代码中未申明这个类型 那么简单地申明一下就行 这不会引起什么问题

- 第三 由于CAS操作会通过对象实例中的偏移量直接进行赋值 因此 它不支持static字段(Unsafe.objectFieldOffset()不支持静态变量)

### 4.4.8 挑战无锁算法:无锁的Vector实现
> 这段讲我很迷 以后再补吧 这里讲的是 Amino CBB 实现的LockFreeVector 我不知道作者在这里主要讲Vector的扩容机制的目的是什么  可能是因为get与push_back俩个方法是最关键的俩个方法把 有兴趣的自己翻书吧


### 4.4.9 让线程之间互相帮助:细看SynchronousQueue的实现
在对线程池的介绍中 提到了一个非常特殊的等待队列SynchronousQueue 
SynchronousQueue的容量为0
 任何一个对SynchronousQueue的写需要等待一个SynchronousQueue的读 反之亦然 因此 SynchronousQueue与其说是一个队列 不如说是一个数据交换通道 
 

SynchronousQueue中有大量的无锁操作
对SynchronousQueue来说 它将put()和take()俩个功能截然不同的操作抽象为一个共同的方法Transferer.transfer() 从字面上看  它就是数据传递的意思 
它的完整签名如下
    
         E transfer(E e, boolean timed, long nanos) 
         
 当参数e未非空时 表示当前操作传递给一个消费者 如果为空 则表示当前操作需要请求一个数据 timed参数决定是否存在timeout时间 nanos决定了timeout的时长 如果返回值为非空 则表示数据已经接受或者正常提供 如果为空 则表示失败（超时或者失败）
   
   SynchronousQueue内部会维护一个线程等待队列 
   
 Trasferer.transfer()函数的实现是SynchronousQueue的核心 它大体分为三个步骤
   1. 如果等待队列为空 或者队列中的节点的类型和本次操作是一致的 那么将当前操作压入队列等待 比如等待队列中是读线程等待 本次操作也是读 因此这俩个读都需要等待 进入等待队列的线程可能会被挂起 它们会等待一个‘匹配’操作
   2. 如果等待队列中的元素和本次操作互补(比如等待操作是读，而本次操作是写) 那么就可以插入一个‘完成’状态节点 并且让他‘匹配’到一个等待节点上 接着弹出这俩个节点 并且使得对于的俩个线程继续执行 
   3. 如果线程发现等待队列的节点就是‘完成’节点 那么帮助这个节点完成任务 其流程和步骤2是一致的
 
 步骤一的实现如下 代码参考JDK 1.8.0_141
```java
SNode h = head;
                if (h == null || h.mode == mode) {  // 如果队列为空 或者模式相同
                    if (timed && nanos <= 0) {      // 不进行等待
                        if (h != null && h.isCancelled())
                            casHead(h, h.next);     //取消处理行为
                        else
                            return null;
                    } else if (casHead(h, s = snode(s, e, h, mode))) {
                        SNode m = awaitFulfill(s, timed, nanos);
                        if (m == s) {               // 等待被取消
                            clean(s);
                            return null;
                        }
                        if ((h = head) != null && h.next == s)
                            casHead(h, s.next);     // 帮助s的fulfiller
                        return (E) ((mode == REQUEST) ? m.item : s.item);
                    }
```
  第一行SNode表示等待队列的节点 内部封装了当前线程，next节点，匹配节点，数据内容等信息 第二行 判断当前等待队列为空 或者队列中的元素的模式与本次操作相同 第8行 生成一个新的节点并置于队列头部 这个节点就代表当前线程  如果入队成功 则执行第9行的awaitFulfill()函数，该函数被唤醒后(表示已经读取到数据或者自己尝试的数据已经被别的线程读取)在14-15行尝试帮助对应的线程完成俩个头部节点的出队操作（仅仅是友情帮助) 并在最后 返回读取或者写入的数据

步骤二的实现如下
```java
  } else if (!isFulfilling(h.mode)) { // 是否处于fulfill状态
                    if (h.isCancelled())            // 如果以前取消了
                        casHead(h, h.next);         // 弹出并重试
                    else if (casHead(h, s=snode(s, e, h, FULFILLING|mode))) {
                        for (;;) { // 一直循环到匹配(match)或者没有等待者
                            SNode m = s.next;       // m是s的匹配者
                            if (m == null) {        // 已经没有等待者了
                                casHead(s, null);   // 弹出fulfill节点
                                s = null;           // 下一次使用新的节点
                                break;              // 重新开始主循环
                            }
                            SNode mn = m.next;
                            if (m.tryMatch(s)) {
                                casHead(s, mn);     // 弹出s和m
                                return (E) ((mode == REQUEST) ? m.item : s.item);
                            } else                  // match失败
                                s.casNext(m, mn);   // 帮助删除节点
                        }
                    }    
```
首先判断头部节点是否处于Fulfill模式  如果是 进入步骤三 否则 就视自己为对应的fulfill线程 第4行 生成一个SNode节点  设置为fulfill模式并将其压入队列头部 接着 设置m(原始的队列头部)为s的匹配节点 这个tryMatch()操作将会激活一个等待线程 并将m传递给那个线程 如果设置成功 则表示数据投递完成 将s和m俩个节点弹出即可 如果tryMatch()失败 则表示已经有其他线程帮我完成了操作 那么简单得删除m节点即可 因为这个节点已经被投递 不需要再次处理 然后 再次跳转到第5行的循环体 进行下一个等待线程的匹配和数据投递 直到队列中没有等待线程为止

```java
  } else {                            // 帮助一个fulfiller
                    SNode m = h.next;               // m 是h的match
                    if (m == null)                  // 没有等待者
                        casHead(h, null);           // 弹出fulfill节点
                    else {
                        SNode mn = m.next;
                        if (m.tryMatch(h))          // 尝试match
                            casHead(h, mn);         // 弹出h和m
                        else                        // match失败
                            h.casNext(m, mn);       // 帮助删除节点
                    }
                }
```
 上述代码的执行原理与步骤2是完全一致的 唯一的不同是步骤3不会返回  因为步骤3进行工作是帮助其他线程尽快投递它们的数据 而自己并没有完成对应的操作 因此 线程进入步骤3后 再次进入大循环体 才能步骤1开始重新判断和投递数据
 
 
 从整个数据投递的过程中可以看到 在SynchronousQueue中 参与工作的所有线程不仅仅是竞争资源的关系 更重要的是 它们彼此之间还会互相帮助 在一个线程内部 可能会帮助其他线程完成它们的工作 这种模式可以更大程度上减少饥饿的可能 提供系统整体的并行度
 
## 4.5 有关死锁的问题

在一般情况下 使用锁的情况一般比无锁要多 而且在复杂的业务系统中 使用无锁的难度也是非常的高 但是使用锁 就会引起一个问题 --那就是死锁

什么是死锁 死锁就是俩个或者多个线程 相互占用对方需要的资源 而都不进行释放 导致彼此之间都相互等待对方释放资源 产生了无限制等待的现象 死锁一旦发生 如果没有外力介入 这种等待将永远存在 从而对程序的产生严重的影响

用来描述死锁问题的一个有名场景就是‘[哲学家就餐][10]’问题

假设有五位哲学家围坐在一张圆形餐桌旁，做以下两件事情之一：吃饭，或者思考。吃东西的时候，他们就停止思考，思考的时候也停止吃东西。餐桌中间有一大碗意大利面，每两个哲学家之间有一只餐叉。因为用一只餐叉很难吃到意大利面，所以假设哲学家必须用两只餐叉吃东西。他们只能使用自己左右手边的那两只餐叉。哲学家就餐问题有时也用米饭和筷子而不是意大利面和餐叉来描述，因为很明显，吃米饭必须用两根筷子。

哲学家从来不交谈，这就很危险，可能产生死锁，每个哲学家都拿着左手的餐叉，永远都在等右边的餐叉（或者相反）。
如图 ![哲学家就餐][11]

假设最简单的情况 就是只有2个哲学家 A和B A左手拿着其中一只叉子 B也一样 这样他们的右手都在等待对方的叉子 并且这种等待会继续 从而导致线程无法运转
下面用一个简单的例子模拟这个过程
相关代码请见 [DeadLock][12]

如果在实际环境中 遇到了这种情况 通常的表现就是相关的进程不再工作 并且CPU占用率为0(因为死锁的显存不占用CPU)，不过这种表现线性只能猜测问题 如果想要确认问题 还需要使用JDK提供的一套专业工具
 我们可以使用jps命令得到java进程的ID 接着使用jstack命令得到线程的线程堆栈

想要避免死锁 除了使用无锁的函数外 另外一种有效的方法就是使用第三章介绍的重入锁 通过重入锁的中断或者限时等待可以有效避免死锁代理的问题 



相关代码请见 [DeadLockInterruptSolve][13]
相关代码请见 [DeadLockTimeLockSolve][14]


  [1]: http://www.cnblogs.com/paddix/p/5405678.html
  [2]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/ThreadLocalDemo.java
  [3]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/ThreadLocalDemo_Gc.java
  [4]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/RandomThreadLocalTest.java
  [5]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/AtomicIntegerDemo.java
  [6]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/AtomicReferenceDemo.java
  [7]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/AtomicStampedReferenceDemo.java
  [8]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/AtomicIntegerArrayDemo.java
  [9]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/AtomicIntegerFieldUpdaterDemo.java
  [10]: https://zh.wikipedia.org/wiki/%E5%93%B2%E5%AD%A6%E5%AE%B6%E5%B0%B1%E9%A4%90%E9%97%AE%E9%A2%98
  [11]: https://upload.wikimedia.org/wikipedia/commons/thumb/7/7b/An_illustration_of_the_dining_philosophers_problem.png/200px-An_illustration_of_the_dining_philosophers_problem.png
  [12]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/DeadLock.java
  [13]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/DeadLockInterruptSolve.java
  [14]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4/DeadLockTimeLockSolve.java