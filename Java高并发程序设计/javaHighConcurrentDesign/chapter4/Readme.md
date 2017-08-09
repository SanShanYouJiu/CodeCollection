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
减小锁粒度也是削弱多线程锁竞争的有效手段 这种技术电信的使用场景就是ConcurrentHashMap类的实现 在3.3节中介绍了这个类 但是没有仔细的介绍 这节仔细的介绍一下

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
 
 锁偏向是一种针对加锁操作的优化手段 它的核心思想是：如果一个线程获得了锁 那么锁就进入了偏向模式  当这个线程再次请求锁时 无须再做任何同步操作 这样就节省了大量相关锁申请的操作 从而提高了程序性能  因此 对于几乎没有锁竞争的场合 偏向锁有比较好的优化效果 因为连续多次极有可能是同一个线程请求相同的锁 而对于锁竞争比较激烈的场合  其他效果不佳