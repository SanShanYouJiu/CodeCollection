# 第六章 Java8与并发

## 6.1 Java8的函数式编程简介


### 6.1.1 函数作为一等公民
函数可以作为另外一个函数的返回值 这也是函数式编程的特点

### 6.1.2 无副作用

函数的副作用指的是在调用过程中 除了给出了返回值外 还修改了函数状态 比如 函数在调用过程中 修改了某一个全局状态  函数式编程认为，函数的副作用应该被尽量避免 
> 显示函数指函数与外界交换数据的唯一渠道就是参数和返回值 显示函数不会去读取或者修改函数的外部状态 与之相对的是隐式函数 隐式函数除了参数和返回值外 还会读取外部信息 或者可能修改外部信息

完全的无副作用实际上做不到的 因为系统总是需要获取或者修改外部信息的

###  6.1.3 申明式的（Declarative）

函数式编程是申明式的编程方式，相对于命令式（Imperative)而言 命令式的程序设计喜欢大量使用可变对象和指令 
在申明式的编程范式 你不再需要提供明确的指令操作 所有的细节指令将会更好地被程序库所封装 你要做的只是提出你的需求 申明你的用意即可

```java
  int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9,10};
        for (int i : arr) {
            System.out.println(i);
        }
```

与之对应的申明式代码如下
```java
  int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9,10};
 Arrays.stream(arr).forEach((final int x)->{
            System.out.println(x);
        });
```
在此 我们只是简单的申明了我们的用意 有关循环以及判断是否结束等操作都被简单地封装在程序库中

### 6.1.4 不变的对象
在函数式编程中 几乎所有传递的对象都不会被轻易修改
例子如下
```java
    static int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9,10};
     Arrays.stream(arr).map((x)->x=x+1).forEach(System.out::println);
    System.out.println();
    Arrays.stream(arr).forEach(System.out::println);
```

在使用函数式编程时  这种状态是一种常态 几乎所有的对象都拒绝被修改 这非常类似于不变模式

### 6.1.5 易于并行
由于对象都处于不变的状态 因此函数式编程更加易于并行  我们之所以要关注线程安全 一个很重要的原因是当多个线程对同一个对象进行写操作 容易将这个对象"写坏" 但是 由于对象是不变的 因此 在多线程环境下 也就没有必要进行任何同步操作

###  6.1.6 更少的代码
通常情况下 函数式编程更加简明扼要 代码更少

## 6.2 函数式编程基础

Java 8 提出了函数式接口的概念 所谓函数式接口 简单来说 就是只定义了的单一抽象方法的接口 
    
```java
@FunctionalInterface
public interface Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see     java.lang.Thread#run()
     */
    public abstract void run();
}
```
注释FunctionInterface用于表明Runnable是一个函数式接口 该接口被定义为只包含一个抽象方法run()  因此它符合函数式接口的设计 如果一个函数满足函数式接口的定义 那么即使不标注为@FunctionInterface 编译器依然会把它看做函数式接口 这有点像@Overried注释 如果你的函数符合重载的要求 无论你是否标注了@Overried 编译器都识别这个重载函数 但一旦你进行了标注  而实际的代码不符合规范 那么就会得到一个编译错误 

这里需要强调的是 函数式接口只能有一个抽象方法 而不是只能有一个方法 这份俩点来说  在java8中 接口运行存在实例方法 比如默认方法 静态方法 其次 如何被java.lang.Object实现的方法 都不能视为抽象方法  

### 6.2.2 接口默认方法
### 6.2.4 方法引用
## 6.3 一步一步走入函数式编程

> 关于这几章其实我在博客中的另外一篇博客 [JAVA8新特性总结][1]中已经介绍过了
 
## 6.4 并行流与并行排序

### 6.4.1 使用并行流过滤数据

```java  
 
     public class PrimeUtil {

    public static boolean isPrime(int number) {
        int  tmp =number;
        if (tmp < 2) {
            return  false;
        }
        for (int i=2;Math.sqrt(tmp) >=i;i++) {
            if (tmp % i == 0) {
                return  false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println( IntStream.range(1, 1000000).parallel().filter(PrimeUtil::isPrime).count());
        }
}
```
可以使用parallel()方法得到一个并行流 接着 在并行流进行过滤 此时 PrimeUtil.isPrime()会被多线程并发调用 应用于流的所有元素

### 6.4.2 从集合得到并行流
在函数式编程中 我们可以从集合得到一个流或者并行流 
```java

        List<Student> ss = new AskThread();
        double ave=ss.stream().mapToInt(s->s.score).avarage().getAsDouble();
```
在集合对象List中 我们使用stream()方法可以得到一个流 如果希望将这段代码并行化  则可以使用parallelStream()函数
```java
     double ave=ss.parallelStream().mapToInt(s->s.score).avarage().getAsDouble();
```
### 6.4.3 并行排序
除了并行流外  对于普通数组 Java8中也提供了简单的并行功能 比如 对于数组排序 有Arrays.sort()方法 当然这是串行排序 在Java8中也有新增的Arrays.paralleSort()
```java
 int[] arr = new int[10];
 Arrays.parallelSort(arr);
```
除了并行排序外 Arrays中还增加了一些API用于数组中数据的赋值 

```java
Random r = new Random();
Arrays.setAll(arr, (i) -> r.nextInt());
//并行版本的setAll
Arrays.parallelSetAll(arr, (i) -> r.nextInt());
```
 
## 6.5 增强的Future：CompletableFuture

CompleteableFuture是Java8新增的一个超大型工具类 为什么说它大呢  一方面是实现了Future接口 更重要的是实现了CompletionStage接口
这个接口含有多达约40种方法 之所以这么多方法 视为了函数式编程的流式调用准备的 通过CompletionStage提供的接口 我们可以在一个执行结果上多次流式调用  以此得到最终结果

### 6.5.1 完成了就通知我
CompletableFutre与Future一样 可以作为函数调用的契约 如果你向CpmpletableFuture请求一个数据 如果数据还没有准备好 请求线程就会等待  而让人惊喜的是 CompletableFuture是可以手动设置完成状态的 
相关代码请见[AskThread.java][2]

### 6.5.2 异步执行任务
通过将CCpmletableFuture提供的进一步封装 我们很容易实现Future模式那样的异步调用  
```java
  public  static Integer calc(Integer para){
        try {
            //模拟一个长时间的执行
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return para/2;
    }
 public static void main(String[] args) throws ExecutionException, InterruptedException {
        final CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> calc(50));
        System.out.println(future.get());
        }

```
上述代码中 使用了一个CompletableFuture.supplyAsync()方法构造一个CompletableFuture实例 在supplyAsync()函数中 它会在一个新的线程中  执行传入的参数  在这里 它会执行calc()方法 而calc()方法执行是比较慢的 但是这不影响CompletableFuture实例的构造速度 因此supplyAsync()会理解返回  它返回的CompletableFuture对象实例 在supplyAsync()函数中 它会在一个新的线程中 执行传入的参数 但这不影响CompletableFuture实例的构造速度 因此supplyAsync()会立即返回 
它返回的CompletableFuture对象实例就可以作为这次调用的契约 在将来的任何场合 用于获得最终的计算结果 
如果当前计算没有完成 则调用get()方法的线程会等待

在CompletableFuture中 类似的工厂方法有以下几个
```java
public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)

public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,
                                                   Executor executor)

public static CompletableFuture<Void> runAsync(Runnable runnable,
                                               Executor executor)

public static CompletableFuture<Void> runAsync(Runnable runnable,
                                               Executor executor)
```       
其中supplyAsync()方法用于那些需要有返回值的场景 比如计算某个数据等 而runAsync()方法用于没有返回值的场景 比如 仅仅是简单地执行一个异步任务

在这俩个方法中 都有一个方法可以接受Executor参数 这就使我们可以让Suppilier<U>或者Runnable在指定的线程池中工作 如果不指定 则在默认的系统公共的ForkJoinPool.common线程池中执行
                                                   
>注意 在Java8中 新增了ForkJoinPool.commonPool()方法 它可以获得一个公共的ForkJoin线程池 这个公共的线程池中的所有线程都是Daemon线程 这意味着如果主线程退出 这些线程无论是否执行完毕 都会退出系统 

### 6.5.3 流式调用
```java
  public  static Integer calc(Integer para){
        try {
            //模拟一个长时间的执行
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        return para/2;
    }

public static void main(String[] args) throws ExecutionException, InterruptedException {
   CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50)).thenApply((i) -> Integer.toString(i))
                .thenApply((str)->"\""+str+"\"")
                .thenAccept(System.out::println);
        fu.get();
}
```
上述代码中 使用supplyAsync()函数执行一个异步任务 接着连续使用流式调用对任务的处理结果进行再加工 直到最后结果输出
### 6.5.4 CompletableFuture中的异常处理
CompletableFuture提供了一个异常处理方法execptionally();
```java
  public  static Integer calc(Integer para){
        return para/0;
    }
    
public static void main(String[] args) throws ExecutionException, InterruptedException {

         CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .exceptionally(ex->{
                    System.out.println(ex.toString());
                    return 0;
                })
                .thenApply((i) -> Integer.toString(i))
                .thenApply((str)->"\""+str+"\"")
                .thenAccept(System.out::println);
        fu.get();
}
```
在上述代码中 第8行对当前的CompletableFuture进行异常处理 如果没有异常发生 则CompletableFuture就会返回原有的结果 如果遇到了异常 就可以在exceptionally()中处理异常 并返回一个默认的值 
###  6.5.5 组合多个CompletableFuture
CompletableFuture还允许你将多个CompletableFuture进行组合 一种方法是使用thenCompose()

一个CompletableFuture可以在执行完成后 将执行结果通过Function传递给下一个CompletionStage进行处理(Function接口返回新的CompletionStage实例)
```java

public  static Integer calc(Integer para){
        return para/2;
}
    
public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> fu = CompletableFuture.supplyAsync(() -> calc(50))
                .thenCompose((i) -> CompletableFuture.supplyAsync(() -> calc(i)))
                .thenApply((str) -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        fu.get();
}        
```

另外一种组合多个CompletableFuture的方法是thenCombine() 
```java
public  static Integer calc(Integer para){
        return para/2;
}

public static void main(String[] args) throws ExecutionException, InterruptedException {
      CompletableFuture<Integer> intFuture =CompletableFuture.supplyAsync(()->calc(50));
        CompletableFuture<Integer> intFuture2 =CompletableFuture.supplyAsync(()->calc(25));

        CompletableFuture<Void> fu =intFuture.thenCombine(intFuture2,(i,j)->(i+j)).thenApply((str)->"\""+str+"\"")
                .thenAccept(System.out::println);
        fu.get();
    }
}
```
上述代码中 首先生成俩个CompletableFuture实例 接着使用thenCombine（）组合将这俩个CompletableFuture 将这俩者的执行结果进行累加 并将其累加结果转换为字符串 

## 6.6 读写锁的改进：StampedLock
StampedLock是java8引入的一种新的锁机制 简单的理解 可以认为它是读写锁的一个改进版本 读写锁虽然分离了读与写 使得读与读之间可以完全并发  但是 读和写之间依然是冲突的 读锁会完全阻塞写锁 它使用的依然是悲观的锁策略 如果有大量的读线程 也有可能会引起写线程的“饥饿”
而StampedLock是一种乐观的读策略 这种乐观的锁非常类似无锁的操作  使得乐观锁完全不会阻塞写线程
### 6.6.1 StampedLock使用示例

相关代码请见[Point.java][3]

在上述代码中 使用了StampedLock.trtOptimisticRead()方法 这个方法表示试图尝试一次乐观锁 它会返回一个类似于时间戳的邮戳整数stamp 这个stamp就可以作为这一次锁获取的凭证
其中有一个validate()方法 这个方法用来判断这个stamp是否在读过程发生期间被修改过 如果stamp没有被修改过 则认为这次读取是有效的 就可以进行数据处理 反之 如果stamp不可用 则意味着在读取的过程中 可能被其他线程改写了数据 因此 有可能出现脏读 如果出现这种情况 我们可以像处理CAS操作那样在一个死循环中一直使用乐观锁 直到成功为止
也可以升级锁的级别  在本例中 就升级为了悲观锁 如果当前对象正被修改 读操作就会导致线程被挂起

可以看到 StampedLock通过引入乐观锁来增加系统的并行度
### 6.6.2 StampedLock的小陷阱

StampedLock内部实现时  使用类似CAS操作的死循环反复尝试的策略
在它挂起线程时 使用的是Unsafe.park()函数 而park()函数在遇到线程中断时 会直接返回（注意，不同于Thread.sleep()它不会直接抛出异常)
而在StampedLock的死循环逻辑中 没有处理有关中断的逻辑 因此 这就会导致阻塞在park()上的线程被中断后 会再次进入循环 而当退出条件得不到满足时 就会发生疯狂占用CPU的情况 这一点值得注意 
下面的例子演示了这个问题
[StampedLockCPUDemo.java][4]

### 6.6.3 有关StampedLock的实现思想
StampedLock的内部实现是基于CLH锁的 CLH锁是一种自旋锁 它保证没有饥饿发生 并且可以保证FIFO(First-In-First-Out)的服务顺序

CLH锁的基本思想如下：
锁维护一个等待线程队列 所有申请锁 但是没有成功的线程都记录在这个队列中 每一个节点(一个节点代表一个线程) ,保存一个标志位(Locked),用于判断当前线程是否已经释放锁

当一个线程试图获得锁，取得当前等待队列的尾部结点作为其前序节点  并使用类似如下代码判断前序节点是否已经成功释放锁
```java
while(pred.locked){
}
```
只要前序节点(pred)没有释放锁 则表示当前线程还不能继续运行 因此会自旋等待
反之 如果前序线程已经释放锁 则当前线程可以继续执行 
释放锁时 也遵循这个逻辑 线程会将自身节点的locked位置标记为false 那么后续等待的线程就能继续执行了

StampedLock正是基于这种思想  但是实现上更为复杂
在StampedLock内部 会维护一个等待链表队列
```java
    static final class WNode {
        volatile WNode prev;
        volatile WNode next;
        volatile WNode cowait;    // list of linked readers
        volatile Thread thread;   // non-null while possibly parked
        volatile int status;      // 0, WAITING, or CANCELLED
        final int mode;           // RMODE or WMODE
        WNode(int m, WNode p) { mode = m; prev = p; }
    }
    /** Head of CLH queue */
    private transient volatile WNode whead;
    /** Tail (last) of CLH queue */
    private transient volatile WNode wtail;

```
上述代码中 WNode为链表的基本元素 每一个WNode表示一个等待线程 字段whead和wtail分别指向等待链表的头部和尾部

另外一个很重要的字段state
```java
    /** Lock sequence/state */
    private transient volatile long state;

```
字段state表示当前锁的状态 它是一个long型 有64位 其中 倒数第8位表示写锁状态 如果该位为1 表示当前由写锁占领
```java
   public long tryOptimisticRead() {
        long s;
        return (((s = state) & WBIT) == 0L) ? (s & SBITS) : 0L;
    }
```
一次成功的乐观锁必须保证当前锁没有写锁占用 其中WBIT用来获取写锁状态位 值为0X80 如果成功 则返回当前state的值（末尾7位清零，末尾7位表示当前正在读取的线程数量）
如果在乐观锁读后 有线程申请了写锁 那么state的状态就会改变
```java
  public long writeLock() {
        long s, next;  // bypass acquireWrite in fully unlocked case only
        return ((((s = state) & ABITS) == 0L &&
                 U.compareAndSwapLong(this, STATE, s, next = s + WBIT)) ?
                next : acquireWrite(false, 0L));
    }

```
上述代码第4行 设置写锁位为1（通过加上WBIT（0x80）) 这样 就会改变state的取值 那么在乐观锁确认时（validate）时 就会发现这个改动 导致乐观锁失效
```java
   public boolean validate(long stamp) {
        U.loadFence();
        return (stamp & SBITS) == (state & SBITS);
    }
```
上述validate()函数比较当前stamp和发生乐观锁时取得的stamp，如果不一致 则宣告乐观锁失败

乐观锁失败后 可以提高锁级别 升级为悲观锁 
```java
  public long readLock() {
        long s = state, next;  // bypass acquireRead on common uncontended case
        return ((whead == wtail && (s & ABITS) < RFULL &&
                 U.compareAndSwapLong(this, STATE, s, next = s + RUNIT)) ?
                next : acquireRead(false, 0L));
    }
```
悲观锁会尝试设置state状态（第4行） 它会将state加1（前提是读线程数量没有溢出，对于读线程数量溢出的情况 会使用辅助的readerOverflow进行统计 这里不讨论）用于统计线程的数量 如果失败 则进入acquireRead（）二次尝试锁获取

在acquireRead()中 线程会在不同条件下进行若干次自旋 试图通过CAS操作获得锁 如果自旋宣告失败 则会启用CLH队列 将自己加入到队列中 之后再启用自旋 如果发现自己成功获得了读锁 则会进一步把自己cowait队列中的读线程全部激活(使用Unsafe.unpark()方法) 如果最终依然无法成功获得读锁 则会使用Unsafe.park()方法挂起当前线程
方法acquireWrite()和acquireRead()也非常类似 也是通过自旋尝试 加入等待队列 直至最终Unsafe.park()方法挂起线程的逻辑进行的 释放锁时与加锁动作想法 以unlockWrite()为例
```java
 public void unlockWrite(long stamp) {
        WNode h;
        if (state != stamp || (stamp & WBIT) == 0L)
            throw new IllegalMonitorStateException();
        state = (stamp += WBIT) == 0L ? ORIGIN : stamp;
        if ((h = whead) != null && h.status != 0)
            release(h);
    }
```
上述代码第5行 将写标识位清理 如果state发生溢出 则退回到初始值
接着 如果等待队列不为空 则从等待队列中激活一个线程（绝大多数情况下是第一个等待线程）继续执行（第7行）
##6.7 原子类的增强

### 6.7.1 更快的原子类
在AtomicInteger类中 它们都是在一个死循环中 不断尝试修改目标值 直到修改成功 如果竞争不激烈的情况下 修改成功率很高 否则 修改失败的概率就会很高 在大量修改失败时 这些原子操作就会进行多次循环尝试 因此性能就会受到影响

那么当竞争激烈的时候 有一种方案可以使用热点分离 将竞争的数据进行分解 提高系统的性能 基于这种思路 虽然CAS操作中没有锁 但是像减小锁粒度这种分离热点的思想依然可以使用 
一种可行的方案就是仿造ConcurrentHashMap 将热点数据分离 比如 可以将AtomicInteger的内部核心数据value分离成一个数组 每个线程访问时  通过哈希等算法映射到其中一个数字进行计数 而最终的计算结果 则为这个数组的求和累加  
而LongAddrer正是使用了这种思想

在实际的操作中 LongAdder并不会一开始就动用数组进行处理 而是将所有数据都先记录在一个称为base的变量中 如果在多线程条件下 大家修改base都没有冲突 那么也没有必要扩展为cell数组 但是一旦发现base修改发生冲突 就会初始化cell数组 使用新的策略 如果使用cell数组更新后 发现某一个cell上的更新依然发生冲突 那么系统就会尝试创新的cell 或者将cell的数量加倍 以减少冲突的可能

简单的分析一个increment（）方法的内部实现
```java
 public void increment() {
        add(1L);
    }
 public void add(long x) {
        Cell[] as; long b, v; int m; Cell a;
        if ((as = cells) != null || !casBase(b = base, b + x)) {
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[getProbe() & m]) == null ||
                !(uncontended = a.cas(v = a.value, v + x)))
                longAccumulate(x, null, uncontended);
        }
    }
    
```
它的核心是第4行的add()方法 最开始cells为null 因此数据会向base增加 但是如果对base的操作冲突 则会进入第7行 并设置冲突标记uncontended为true 接着 
如果判断cells数组不可用 或者当前线程对应的cell为null 则直接进入longAccumulate（)方法 否则会尝试使用CAS方法更新对应的cell数据 如果成功 则退出 失败则进入longAccumulate()方法

longAccumulate()方法比较复杂 其大致内容为根据需要创建新的cell或者对cell数组进行扩容 以减少冲突

下面进行一个例子简单的对LongAdder，原子类以及同步锁进行性能测试 测试方法是使用多个线程对同一个整数进行累加 观察使用3种不同方法所消耗的时间
[LongAdderDemo.java][5]

这本书说的是LongAdder的表现最好 但是可能是因为我是i5的cpu只有双核 速度表现并不理想 最好的是原子类 
LongAdder的另外一个优化手段就是避免了伪共享 在第5章有有关伪共享的问题 但是 需要注意的是 LongAdder中并不是直接使用padding这种看起来比较碍眼的做法 而是引入了一种新的注释'@sun.misc.Contended'
```java
  @sun.misc.Contended static final class Cell {
        volatile long value;
        Cell(long x) { value = x; }
        final boolean cas(long cmp, long val) {
            return UNSAFE.compareAndSwapLong(this, valueOffset, cmp, val);
        }

        // Unsafe mechanics
        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> ak = Cell.class;
                valueOffset = UNSAFE.objectFieldOffset
                    (ak.getDeclaredField("value"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }
```
可以看到 在上述代码第一行申明了Cell类为sun.misc.Contended 这将会使得Java虚拟机自动为Cell解决伪共享问题 
当然 在我们的代码中也可以使用sun.misc.Contened来解决伪共享问题 但是需要额外使用虚拟机参数-XX:-RestrictConteded 否则 这个注释将被忽略

### 6.7.2 LongAdder的功能增强版：LongAccumulator 

LongAccumulator是LongAdder的亲兄弟 它们有公共的Striped64 因此 LongAccumulator的内部的优化方式和LongAdder是一样的 它们都有一个long型的整数进行分割 存储在不同的变量中 以防止多线程竞争 俩者的主要逻辑是类似的 但是LongAccumulator是LongAdder的功能扩展 对于LongAdder来说 它只是每次对给定的整数执行一次加法 而LongAccumulator则可以用任意函数操作

可以使用下面的构造函数创建一个LongAccumulator实例
```java
    public LongAccumulator(LongBinaryOperator accumulatorFunction,
                           long identity) 
```
 第一个参数accumulatorFunction就是需要执行的二元函数（接受俩个long行参数并返回long），第二个参数是初始值
 下面那个例子展示了LongAccumulator的使用 它将通过多线程访问若干个整数 并返回遇到的最大的那个数字
 
 相关代码请见[LongAccumulatorDemo.java][6]  
  
在上述代码中 构造了LongAccumulator实例 并且过滤了最大值 因此传入Long::max函数句柄 当有数据通过accumulate()方法传入LongAccumulator后 LongAccumulator会通过Long::max识别最大值并且保存在内部 在第24行 通过longValue()函数对所有的cell进行了Long::max操作 得到最大值

  [1]: http://sanshanyoujiu.coding.me/2017/07/23/JAVA8%E6%96%B0%E7%89%B9%E6%80%A7/
  [2]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter6/parallel/AskThread.java
  [3]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter6/parallel/Point.java
  [4]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter6/parallel/StampedLockDemo.java
  [5]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter6/parallel/LongAdderDemo.java
  [6]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter6/parallel/LongAccumulatorDemo.java