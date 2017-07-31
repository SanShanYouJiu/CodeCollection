# JDK并发包

>这章的难点在于解析并发包下并发容器的源码 
其中主要还是ConcurrentLinkedQueue类
## 3.1 多线程的团队控制:同步控制

### 3.1.1 synchronized的功能扩展 :重入锁
重入锁完全替代synchronized关键字  在JDK 5.0早期的版本中 重入锁的性能远远好过synchronized 不过JDK6开始 JDK在synchronized做了大量的优化 使得俩者性能差距不大

重入锁简单的使用案例入戏

相关代码请见 ReenterLock



与synchronized相比 重入锁有着显示的操作过程 也是因为这样 重入锁对逻辑控制的灵活性要远远好于synchronized 但值得注意的是,在退出临界区时 必须记得释放锁 否则 其他线程就别想访问临界区了

为什么要重入锁 因为锁是可以重入 也就是反复进入的 

    lock.lock();
    lock.lock();
    try{
    i++
    }finally{
    lock.unlock();
    lock.unlock();
    }
    
 在这种情况下 一个线程连续俩次获得同一把锁 是允许的 如果不允许这么操作的话  那么同一个线程在第二次获得锁时就会死锁 但是需要注意的是 如果同一个线程多次获得锁 那么在释放锁的时候 也必须释放相同次数 如果释放锁的次数多 那么会得到一个IllegalMonitorStateException异常 反之  如果锁释放的次数少了 那么相当于线程还持有这个锁  因此 其他线程也无法进入临界区
 除了上面的灵活性外  重入锁还提供了一些高级功能 比如 重入锁就提供中断处理的能力
 
#### 中断响应

 对于synchronized来说 如果一个线程等待锁 那么结果只有俩种情况 要么继续执行 要么它就是保持等待
 而使用重入锁 则提供了另外一种可能  那就是线程可以被中断  也就是在等待锁的过程中 程序可以根据需要取消对锁的请求。 有些时候  这么做是非常有必要的
 中断正式提供了一套机制  如果一个线程正在等待锁 那么它依旧可以收到一个通知 被告知元素是否再等待 可以停止工作了 这种情况对处理死锁是有一定帮助的 
  
  下面的代码产生了一个死锁 但得益与锁中断 我们可以很轻易的解决这个死锁
  
  相关代码请见 IntLock
  
  在这个代码中 统一使用lockInterruptibly()方法
  这是一个可以对中断进行响应的锁申请操作 即在等待锁的过程中 可以响应中断 
 
#### 锁申请等待限时
 
 除了等待外部通知 要避免死锁还有另外一种方法 那就是限时等待   给定一个等待时间 让线程自动放弃  这对系统来说是有意义的   我们可以使用tryLock方法进行一次限时的等待
 
 相关代码请见 TimeLock
 
 ReentrantLock.tryLock()方法也可以不带参数直接运行 在这种情况下 当前线程会尝试获得锁 如果锁并未被其他线程占用  则申请锁会成功  并立即返回true  如果锁被其他线程占用 则当前线程不会进行等待 而是立即返回false    
 这种模式不会引起线程等待 因此也不会产生死锁     
 
 相关代码请见TryLock
  
  
#### 公平锁
   公平锁不会产生饥饿 只要你排队 最终还是可以得到资源的 如果我们使用synchronized关键字来实现锁控制  那么产生的锁就是非公平的 而重入锁允许我们队其公平性进行设置
   
  相关代码请见 FairLock
   
  公平锁看起来的确非常的优美 但是实现公平锁必然要求系统维护一个有序队列 因此公平锁的实现成本比较高 性能也相对非常低下 因此 默认情况下 锁是非公平的 如果没有特别的需求 也不需要使用公平锁 公平锁和非公平锁在线程调度上也是非常不一样的 
   
   就重入锁的实现来看  主要集中在Java层面  在重入锁的实现中 主要包含3个元素
   1. 原子状态 原子状态使用CAS操作来存储当前所的状态  判断锁是否被别的线程持有
   2. 等待队列 所有没有请求到锁的线程 会进入等待队列进行等待  待有线程释放锁后 系统就能从等待对象唤醒一个线程 继续工作
   3. 阻塞原语pack()和unpack() 用来挂起和恢复线程 没有得到线程的锁会被挂起 有关pack()和unpack的详细介绍 也可以参考阻塞工具类 LockSupport
   
   
### 3.1.2 重入锁的好搭档:Condition条件
   
   Condition是与重入锁相关联的  通过Lock接口(重入锁就实现了这一接口)的Condition newCondition()方法可以生成一个与当前重入锁绑定的Condition实例 利用Condition对象 我们就可以让线程在合适的时间等待 或者在某一个特定的时刻得到通知 继续执行
   
   具体方法查文档‘、吧  
   
   例子如下

   相关代码请见 ReenterLockCondition
   
   与Object的wait()和notify()方法一样 
   在signal()方法被调用后 一般需要释放相关的锁 谦让给被唤醒的线程 让他可以继续执行 比如本例的31-33行 就释放了重入锁 如果省略了第33行 那么 虽然已经唤醒了线程t1 但是由于它无法重新获得锁 因而也就无法真正的继续执行
  
  
### 3.1.3 允许多个线程同时访问：信号量
  信号量为多线程提供了更为强大的控制方法 广义上说 信号量是对锁的扩展 无论是内部锁synchronized还是重入锁ReentrantLock 一次都只允许一个线程访问一个资源 ，而信号量却可以指定多个线程 同时访问某一个资源 信号量主要提供以下构造函数
   
    
        public Semaphore(int permist)
        public Semaphore(int permise,boolean fair)  //第二个参数可以指定是否公平
        
  在构建信号量对象时 必须要指定信号量的准入数  即同时能申请多少个许可 每当线程每次只申请一个许可时 这就相当于指定了同时有多少个线程可以访问某一个资源 
     
     public void acquire()
     public void acquireUninterruptibly()
     public void  tryAcquire()
     public void  tryAcquire(long timeout,Timeout unit)
     public void  release()
     
   acquire()方法尝试获得一个准入的许可 若无法获得 则线程会等待 直到有线程释放一个许可 或者当前线程被中断 。acquireUninterruptibly()方法和acquire()方法类似  但是不响应中断 tryAcquire()会尝试获得一个许可 如果成功返回true 失败则是false  它不会进行等待 立即返回
   release()用于线程访问资源结束后 释放一个许可 以使其他等待许可的线程可以选择资源返回
   
 相关代码请见 SemaphoreDemo
 
### 3.1.4 ReadWriteLock 读写锁

ReadWriteLock是JDK5提供的读写分离锁 读写分离锁可以有效的帮助减少锁竞争 以提升系统开销 
如果使用重入锁或者内部锁 所有的读读与读写和写写之间都是要串行操作  由于读操作不会对数据完整性造成破坏 这种等待显然是不合理的 所以读写锁就有了发挥功能的余地 

下表是对写锁的访问约束

   |  \ | 读 | 写|
  |:--- | :----: |---:|
  |读 | 非阻塞 | 阻塞|
 | 写 | 阻塞 | 阻塞|
  
- 读 -读不互斥 读读之间不阻塞
- 读-写互斥:读阻塞写，写也会阻塞读
- 写-写互斥：写写阻塞

相关代码请见 ReadWriteLockDemo

### 3.1.5 倒计时器：CountDownLatch

 这个工具通常用来控制线程等待 它可以让某一个线程等到直到倒计时结束 再开始执行

 CountDownLatch的构造函数接受一个整数作为参数 即当前这个计数器的计数个数
    
        public CountDownLatch(int count)

相关代码请见 CountDownLatchDemo
  
### 3.1.6 循环栅栏  :CyclicBarrier

CyclicBarrier是另外一种多线程并发控制实用工具 和CountDownLatch非常类似 它也可以实现线程间的计数等待 但它的功能比CountDownLatch更加复杂且强大
  
CyclicBarrier可以理解为循环栅栏  栅栏是一种障碍物 前面的Cyclic意为循环 也就是说这个计数器可以反复使用 比如 假设我们将计数器设置为10 那么凑齐第一批10个线程后 计数器将归零 然后继续接着凑齐下一批的10个线程 这就是循环栅栏内在的含义
比CountDownLatch略微强大一些 CyclicBarrier可以接受一个参数作为barrierAction 所谓barrierAction就是当计数器一次计数完成后 系统会执行的动作

    public CyclicBarrier(int parties,Runnable barrierAction)
    
 相关代码请见CyclicBarrierDemo
    
这里会抛出俩个异常 一个是InterruptedException 也就是等待中断 线程被中断 这是一个非常通用的异常 第二个异常则是CyclicBarrier的BrokenBarrierException 一旦遇到这个异常  则表示当前的CyclicBarrier已经破损了 可能系统已经没有办法等待所有线程到期了 如果继续等待 可能就是徒劳无功  
这个异常就可以避免其他9个线程进行永久的 无谓的等待 

### 3.1.7 线程阻塞工具类:LockSupport
    
LockSupport是一个非常方便实用的线程阻塞工具，它可以在线程内任意位置上线程让出线程阻塞，和Thread.suspend()相比 它弥补了由于resume()在前发生 导致线程无法继续执行的情况 和Object.wait()相比 它不需要先伙食某发对象的锁 也不会抛出InterruptedException异常
用LockSupport重写第二章提到的suspend()永久卡死线程的例子

相关代码请见LockSupportDemo

在简单的将原来的suspend()和resume()方法用park()和unpark()方法做了替换 当然也无法保证unpark()方法会发生在park()方法之前 但是  它自始至终都可以正常的结束 不会因为park()方法而导致线程永久性的挂起
这是因为LockSupport类使用类似信号量的机制。它为每一个线程准备了一个许可，如果许可可用 那么park()方法会立即返回 并且消费这个许可(也就是将许可变为不可用) 如果许可不可用 就会阻塞  而unpark()则会使得一个许可变为可用(但是和信号量不同的是，许可不能累加，你不能拥有超过一个许可 它拥有只有一个)
  
  这个特点使得：即使unpark()操作发生在park()之前 它也可以使下一层的park()操作立即返回 这也就是上述代码可顺利结束的主要原因 
  同时 处于park()挂起状态的显存不会像suspend()那样还给出一个令人费解的Runnable的状态 它会非常明确地给出一个WAITING状态 甚至还会标注是park()引起的
  这使得分析问题时变得格外方便 此外 如果你使用park(Object)函数 还可以为当前线程设置一个阻塞对象 这个阻塞对象会出现在线程Dump中 这样在分析问题时 就更加方便了 
  
  比如 如果我们将上述代码第21行的park()方法改为
        
        LockSupport.park(this);
 
 这样在线程dump中就可以看到类似
 
 除了有定时阻塞的功能外 LockSupport.park()还能支持中断影响 但是和其他接受中断的函数很不一样,LockSupport.park()不会抛出InterruptedException异常 它只是会默默的返回 但是我们可以从Thread.interrupted()等方法获得中断标记
  
 
 相关代码请见LockSupportIntDemo
 
 
## 3.2 线程复用：线程池
多线程的软件设计方法确实可以最大限度的发挥现代多核处理器的计算能力 提高生产系统的吞吐量和性能 但是 若不加控制和管理的随意使用线程 对系统的性能反而会产生不利影响

首先 虽然与进程相比，线程是一种轻量级的工具。但其创建和关闭依然需要花费时间 如果每一个小的任务都创建一个线程，很有可能出现创建和销毁线程所占用的时间大于该线程真实工作使所消耗时间的情况 反而会得不偿失

其次 线程本身也是要占用内存空间 大量的线程会强占宝贵的内存资源 如果处理不当 可能会导致Out of Memory异常 即便没有 大量的线程回收也给GC代理很大的压力 延长GC的停顿时间

因此 对线程的使用必须掌握一个度 在有限的范围内 增加线程的数量可以明显提高系统的吞吐量 但一旦超出了这个范围 大量的线程只会拖垮应用系统 因此 在生成环境中使用线程 必须对其加以控制和管理

### 3.2.1 什么是线程池
>想仔细了解的查百科吧

对创建的线程进行复用 

### 3.2.2 不要重复发明轮子：JDK对线程池的支持

为了更好的控制多线程 JDK提供了一套Executor框架  帮助开发人员有效地进行线程控制 其本质就是一个多线程

 ![此处输入图片的描述][1]
以上成员均在java.util.concurrent包中 是JDK并发包的核心类 其中ThreadPoolExecutor类表示一个线程池 Executors类则扮演着线程池工厂的角色 通过Executors可以取得一个拥有特定功能的线程池 从UML图中可知 ThreadPoolExecutor类实现了Executor接口  因此通过这个接口 任何Runnable的对象都可以被ThreadPoolExecutor线程池调度 

- Executor 执行器接口，该接口定义执行Runnable任务的方式。

- ExecutorService 该接口定义提供对Executor的服务。

- ScheduledExecutorService 定时调度接口。

- AbstractExecutorService 执行框架抽象类。

- ThreadPoolExecutor JDK中线程池的具体实现。

- Executors 线程池工厂类

Executor框架提供了各种类型的线程池 主要有以下工厂方法:

    public static ExecutorService newFixedThreadPool(int nThreads)
    public static ExecutorService newSingleThreadExecutor()
    public static ExecutorService newCachedThreadPool()
    public static ScheduledExecutorService newSingleThreadScheduleExecutor()
    public static ScheduledExecutorService newScheduledThreadPool(int corePoolSize)
    

以上工厂方法分别返回具有不同工作特性的线程池

- newFixedThreadPool()方法 该方法返回一个固定线程数量的线程池 该线程池中的线程数量始终不变 有新任务 若有空线程 就用 没有空线程 这个新任务就放到一个任务队列 等有线程空闲去处理任务队列的任务
- newSingleThreadExecutor()方法：该方法返回一个只有一个线程的线程池 若多就放任务队列 一个个按顺序来
- newCacheThreadPool()方法：该方法返回一个可根据实际情况调整线程数量的线程池 线程池的线程数量不确定 但若有空闲线程可以复用 则会优先使用可复用的线程  若所有线程均在工作 又有新的任务提交 则会创建新的线程处理任务 所有线程在当前任务执行完毕后 将返回线程池进行复用
- newSingleThreadScheduledExecutor()方法：该方法返回一个ScheduledExecutorService对象 线程池大小为1 ScheduledExecutorService接口在ExecutorService接口之上扩展了在给定时间执行某任务的功能 
- newScheduledThreadPool()方法：该方法也返回一个ScheduledExecutorService对象 但该线程池也可以指定线程数量

**固定大小的线程池**

相关代码请见ThreadPoolDemo

**计划任务**
另外一个值得注意的是newScheduledThreadPool()方法 它返回一个ScheduleExecutorService对象  可以根据时间需要对现场进行调度 它的一些主要方法如下

-   public ScheduledFuture<?> schedule(Runnable command,
                                         long delay, TimeUnit unit);
                                         
-   public ScheduledFuture<?> scheduleAtFixedRate(Runnable command,
                                                    long initialDelay,
                                                    long period,
                                                    TimeUnit unit);
-   public ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                        long initialDelay,
                                                        long delay,
                                                        TimeUnit unit);
                                                   
与其他几个线程池不同 ScheduledExecutorService并不一定会立即安排执行任务 它其实是起到了计划任务的作用 它会在指定的时间 对任务进行调度 

方法schedule()会在给定时间 对任务进行一次调度 方法scheduleAtFixedRate()和scheduleWithFixedDelay()会对任务进行周期性的调度 但是俩者有一点区别

对于FixedRate方式来说 任务调度的频率是一定的 它是以上一个次任务开始执行时间为起点 之后的period时间 调度下一次任务 而FixDelay则在上一个任务结束后 再经过delay时间进行任务调度 这样说可能会比较模糊
 FixRate是隔多长时间周期执行是包括内部代码的运行时间 而FixDelay则是不包括内部代码的运行时间 而是隔多长时间运行一次 
 具体的话看官方文档吧 以及下面的例子

相关代码请见ScheduledExecutorServiceDemo

这里有一个有意思的地方  如果任务的执行时间超过调度时间 会发生什么情况呢？比如 这里调度是每隔2秒 如果任务执行8秒 会出现什么情况呢 这种周期太短的情况 那么任务就会在上一个任务结束后 立即被调用 可以想象 如果用FixDelay就会变成10秒了

另外一个值得注意的问题 调度程序实际上并不保证任务会无限期的持续调用 如果任务本身抛出了异常 那么后续所有执行都会中断 因此 如果你想让你的任务持续稳定的执行 那么做好异常处理就非常重要了 否则 你很有可能观察到你的调度器无疾而终
>注意 如果任务遇到异常 那么后续的所有子任务都会停止调度 因此 必须保证异常被及时处理 为周期性任务的稳定调度提供条件

### 3.2.3 刨根究底：核心线程池的内部实现
> 这个太麻烦就不写多了 就写一些我认为关键的地方 

无论是newFixedThreadPool()方法 newSingleThreadExecutor()方法还是newCachedThreadPool()方法  虽然看起来创建的线程有着完全不同的功能特点 但其内部实现均使用了ThreadPoolExecutor实现 

     public ThreadPoolExecutor(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) 
                                  
  函数的参数含义如下
- corePoolSize：指定了线程池中的线程数量
- maximumPoolSize：指定了线程池中的最大线程数量
- keepAliveTime：当线程池数量超过corePoolSize时 多余的空闲线程的存活时间 即 超过corePoolSize的空闲线程 在多长时间内 会被销毁
- unit：keepAliveTime的单位
- workQueue:任务队列，被条件但尚未被执行的任务
- threadFactory：线程工厂 用于创建线程 一般使用默认的即可
- handler：拒绝策略 当任务太多来不及处理 如何拒绝服务

上述参数中 只有workQueue和handler需要进行详细说明
参数workQueue是指提交单未执行的任务队列 它是一个BlockingQueue接口的对象 仅用于存放Runnable对象 根据功能介绍 在ThreadPoolExecutor的构造函数中可使用以下几种BlockingQueue

- 直接提交的队列:SynchronousQueue
- 有界的任务队列：ArrayBlockingQueue
- 无界的任务队列：LinkedBlockingQueue
- 优先任务队列：PriorityBlockingQueue

### 3.2.4 超负载了怎么办：拒绝策略

- AbortPolicy策略：该策略会直接抛出异常 阻止系统正常工作
- CallerRunsPolicy策略：只要线程池未关闭，该策略直接在调用者线程中，运行当前被丢弃的任务。显然这样做不会真的丢弃任务，但是，任务提交线程的性能极有可能会急剧下降
- DiscardOledestPolicy策略：该策略将丢弃最老的一个请求，也就是即将被执行了的一个任务 并尝试再次提交当前任务
- DiscardPolicy策略：该策略默默地丢弃无法处理的任务，不予任何处理 如果允许任务丢失 我觉得这可能是最好的一种方案了吧

以上内置的策略均实现了RejectedExecutionHandler接口 若以上策略仍无法满足实际应用需要，完全可以自己扩展RejectedExecutionHandler接口，RejectedExecutionHandler的定义如下

        void rejectedExecution(Runnable r, ThreadPoolExecutor executor);
        
  下面的代码简单的演示了自定义线程池和拒绝策略的使用
  
  相关代码请见RejectThreadPoolDemo
  
### 3.2.5 自定义线程创建：ThreadFactory
 
 ThreadFactory是一个借口 它只有一个方法，用来创建线程
 
    Thread newThread(Runnable r)

  当线程池需要新建线程时 就会调用这个方法
  自定义线程池可以帮助我们做不少事，比如 等我们可以追踪线程池究竟在何时创建了多少线程，也可以自定义线程的名称，组以及优先级等信息，设置可以任性地将所有线程设置为守护线程。总之，使用自定义线程池可以让我们更加自由地设置池中所有线程的状态
  
  相关代码请见ThreadFactoryDemo
   
### 3.2.6 我的应用我做主：扩展线程池
 
 虽然JDK已经帮我们实现了这个稳定的高性能线程池 但如果我们需要对这个线程池做一些扩展 比如 我们想监控每个任务的开始和结束时间 或者其他一些自定义的增强功能 这个就可以通过ThreadPoolExecutor扩展的功能来实现 它提供了beforeExecutor(),afterExecute()和terminated()三个接口对线程池进行控制
    
  在默认的ThreadPoolExecutor实现中，提供了空的beforeExecute()和afterExecute()实现，在实际应用中。可以对其扩展来实现对线程池运行状态的跟踪 输出一些有用的调试信息， 以帮助系统故障诊断，这对多线程程序输出错误排查是很有帮助的
  下面有个例子
  
  相关代码请见ExTreadPool
  
### 3.2.7 合理的选择：优化线程池线程数量

线程池的大小对系统的性能有一定影响 过大或者过小的线程数量都无法发挥最优的性能 但是线程池大小的确定也不需要做的非常精准 因为只要避免极大和极小俩种情况 线程池的带下对系统的性能并不会影响太大 ，一般来说 确定线程池的大小需要考虑CPU数量 内存大小等因素 在《Java Concurrency in Practice》 一书中给出了一个估算线程池大小的经验公式

$$Ncpu=Cpu的数量$$
$$Ucpu=目标CPU的使用率,0<=Ucpu<=1$$    
$$W/C=等待时间与计算时间的比率$$    
 
 在Java中 可以通过
    
        Runtime.getRuntime().availableProcessors()
 取得可用的CPU数量
        
### 3.2.8 堆栈去哪里了：在线程池中寻找堆栈
 先看一个简单的错误案例

相关代码请见DivTask

          public class DivTask implements Runnable {
              int a,b;
          
              public DivTask(int a, int b) {
                  this.a=a;
                  this.b=b;
              }
              
              @Override
              public void run() {
                  double re = a / b;
                  System.out.println(re);
              }
          }
 
 如果程序运行了这个任务，那么我们期望它可以打印出给定俩个数的商。现在我们构造几个这样的任务 希望程序可以为我们计算一组给定数组的商
 
    ThreadPoolExecutor pools = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
             
            for (int i = 0; i < 5; i++) {
                pools.submit(new DivTask(100, i));
            }
 上述代码将DivTask提交到线程池 从这个for循环来看 我们应该会得到5个结果分别是100除以给定的i后的商 但如果真的运行程序 就发现全部结果是
        
        33.0
        50.0
        100.0
        25.0
 只有4个输出 缺少了一个值  这个缺少的值很有可能是由于除以0导致的
    
 因此 使用线程池虽然是件好事 但是还得处处留意这些“坑” 线程池很有可能会“吃”掉程序抛出的异常 导致我们对程序的错误一无所知
        
向线程池讨回异常堆栈（异常堆栈是非常重要的 类似水手的指南针）
- 一种最简单的方法，就是放弃submit()，改用execute()
- 另外一种用Future对象进行接收 `Futere re =pools.submit(new DivTask(100,i));re.get();`
上面两种方法都可以得到部分堆栈信息 
注意 是部分 这是因为从这俩个异常堆栈中我们只能知道异常是在哪里抛出的 但是我们还希望得到另外一个更重要的信息 那就是这个任务到底是在哪里提交的？而任务的具体提交位置已经被线程池完全淹没了
 解决方法就是我们扩展自己的ThreadPoolExecutor线程池 在它调度任务之前 先保存一下提交任务线程的堆栈信息

相关代码请见TraceThreadPoolExecutor
相关代码请见DivTask
        
### 3.2.9 分而治之：Fork/Join框架
“分而治之”这是一个非常有效的处理大量的数据的方法 也是一个归并排序的实现思想 注明的MapReduce也是采取了分而治之的思想

Fork一词原始含义是吃饭用的叉子 ，也有分叉的意思。在Linux平台中 fork()函数用来创建子进程
使得系统进程可以多一个执行分支。在Java中也沿用了类似的命名方式
而Join()的含义在之前的章节已经介绍 这里也是相同的意思 表示等待 也就是使用fork()后系统多了一个执行分支（线程）,所以需要等待这个执行分支执行完毕 才有可能得到最终的结果 因此join()就表示等待   

在实际使用中 如果毫无顾忌使用fork()开启线程进行处理 那么很有可能导致系统开启过多的线程而严重影响性能 所以 JDK中 给出一个ForkJoinPool线程池 对于fork()方法并不急着开启线程 而是提交给ForkJoinPool线程池进行处理 以节省系统资源
由于线程池的优化，提交的任务和线程数量并不是一对一的关系。在绝大多数情况下，一个物理线程时间上是需要出来多个逻辑任务的 因此 每个线程必然需要拥有一个任务队列。因此 在实际过程中 会遇到一种情况 线程A已经处理完自己的任务了 但是线程B还有一堆没有处理 于是A就可以去帮助B 从线程B的任务队列拿出一个任务过了处理 尽可能达到平衡 
>一个值得的地方是 当线程试图帮助别人时 总是从任务队列的底部开始拿数据，而线程视图执行自己的任务时，则是从相反的顶部开始拿 因此这种行为也十分有利于避免数据竞争

ForkJoinPool的一个重要接口

    public <T> Future<T> submit(Callable<T> task);
     
你可以向ForkJoinPool线程池提交一个ForkJoinTask任务 所谓ForkJoinTask任务就是支持fork()分析以及join()等待的任务 ForkJoinTask有俩个重要的子类，RecursiveAction和RecursiveTask。它们分别表示没有返回值的任务和可以携带返回值的任务

相关代码请见CountTask

此外,ForkJoin线程池使用了一个无锁的栈来管理空闲线程 如果一个工作线程暂时取不到可用的任务 则可能会挂起 挂起的线程将会被压入线程池维护的栈中 待将来有任务可用时 再从栈中唤醒这些线程

## 3.3 不要重复的发明轮子：JDK的并发容器

### 3.3.1 超好用的工具类：并发集合简介
JDK提供的这些容器大部分在java.util.concurrent包中 
- ConcurrentHashMap:这是一个高效的并发HashMap 可以理解为一个线程安全的HashMap
- CopyOnWriteArrayList：这是一个List 从名字看是ArrayList一族的 在读多邪少的场合 这个List性能非常好 远远好于Vector
- ConcurrentLinkedQueue：高效的并发队列，使用链表实现 可以看做一个线程安全的LinkedList
- BlockingQueue ：这是一个借口 JDK内部通过链表 数组 等方式实现了这个接口  表示阻塞队列 非常适合用于作为数据共享的通道
- ConcurrentSkipListMap:跳表的实现 这是一个Map 使用跳表的数据结构进行快速查找

### 3.3.2 线程安全的HashMap

让一个线程不安全的HashMap如何变成线程安全的HashMap 一种可行方案就是使用`Collections.synchronizedMap()`方法包装我们的HashMap

    public   Map map = Collections.synchronizedMap(new HashMap<>());
   
 这个内部实现的方法就是实现一个
        
          public V get(Object key) {
                    synchronized (mutex) {return m.get(key);}
                }
 很明显通过一个mutex作为监听对象的来进行锁 从而实现线程安全
 如果并发级别不高 一般也够用 但是 在高并发的环境中 我们也有必要寻求新的解决方案  
 一个更加专业的并发HashMap是ConcurrentMap 它位于java.util.concurrent包内 它专门为并发进行性能优化 因此 更加适合多线程的场合
 
### 3.3.3 有关List的线程安全
 队列 链表也是极其常用 几乎所有的应用程序都会与之相关 在Java中 ArrayList与Vector都是使用数组作为其内部实现 俩者最大的不同在于Vector是线程安全的 而ArrayList不是
 
### 3.3.4 高效读写的队列：深度剖析ConcurrentLinkedQueue 
队列Queue也是常用的数据结构之一 在JDK中提供了一个ConcurrentLinkedQueue类用来实现高并发的队列
ConcurrentLinkedQueue应该算是高并发环境中性能最好的队列就可以了 它之所以有很好的性能 是因为内部复杂的实现

> 这里需要无锁操作的一些知识

ConcurrentLinkedQueue内部定义结点Node

```java  
        private static class Node<E> {
            volatile E item;
            volatile Node<E> next;
```
item用来表示目标元素 next字段表示当前Node的下一个元素 这属于数据结构的基础了 

对Node进行操作时 使用了CAS操作(CAS是无锁操作相关的知识)
    
```java            
    
        boolean casItem(E cmp, E val) {
                return UNSAFE.compareAndSwapObject(this, itemOffset, cmp, val);
            }
    
            void lazySetNext(Node<E> val) {
                UNSAFE.putOrderedObject(this, nextOffset, val);
            }
    
            boolean casNext(Node<E> cmp, Node<E> val) {
                return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
            }
```

ConcurrentLinkedQueue有俩个重要的字段 head和tail 分别表示链表的头部和尾部  它们都是Node类型 对于head来说 它永远不会为null 并且通过head以及succ()后继方法一定能完整地遍历整个链表 对于tail来说 它自然应该表示队列的末尾

但ConcurrentLinkedQueue的内部实现非常的复杂 它允许在运行多个不同的状态 以tail为例 一般来说 我们期望tail总是为链表的末尾 但实际上 tail的更新并不是及时的 而是可能会产生拖延现象 每次更新会跳跃俩个元素
 
```java
 public boolean offer(E e) {
        checkNotNull(e);
        //创建入队节点
        final Node<E> newNode = new Node<E>(e);
       //t为tail节点，p为尾节点，默认相等，采用失败即重试的方式，直到入队成功
        for (Node<E> t = tail, p = t;;) {
            //获得p的下一个节点
            Node<E> q = p.next;
             // 如果下一个节点是null,也就是p节点就是尾节点
            if (q == null) {
                // p是最后一个结点
                if (p.casNext(null, newNode)) {
                     //每俩次更新一下tail
                    if (p != t)  
                        casTail(t, newNode);   
                    return true;
                }
                // CAS竞争失败 再尝试
            }
               //遇到哨兵结点 从head开始遍历 也可能是刚初始化
               //但如果tail被修改 则使用tail(因为tail可能被修改对了)
            else if (p == q)
                p = (t != (t = tail)) ? t : head;
            else
                // 取下一个结点 或者最后一个结点
                p = (p != t && t != (t = tail)) ? t : q;
        }
    }

```
>能看懂就看懂吧 不懂的话看原书就好 这里写起来字太多 就说些关键的

第二个判断p==q的情况 这种情况是遇到了哨兵结点导致的 所谓哨兵结点 就是next指向自己的结点 这种结点没什么价值 主要表示要删除的结点 或者空结点 当遇到哨兵结点时 无法通过next获得后继元素 就直接返回head 从链表头部开始遍历 但一旦发生在执行过程中 tail被其他线程修改的情况 则进行一次“打赌” 使用新的tail作为链表末尾（这样就避免了重新查找tail的开销)
 那么有的人就会对这个语句会不明白了
 ` p = (t != (t = tail)) ? t : head;` 
 这句代码虽然只有一行 首先！=不是原子操作 它是可以被中断的 也就是说 在执行‘！=’时 程序会先拿t的值 再执行t=tail,并取得新的t的值 然后比较这俩个值是否相等 在单线程中 t!=t这种语句显然不会成立 但是在并发环境中 有可能在获得左边t值后，右边的t值就被其他线程修改 这样t!=t就成立  这里就是这种情况 如果在比较过程中 tail被其他线程修改 当它被再次赋值给t时 就会导致等式左边的t和右边的t不同 如果俩个t不同 表示tail在中断被其他线程篡改 这时 我们就可以用新的tail作为链表末尾 这就是这里等式右边的t 但如果tail没有被修改 则返回head 要求从头部开始 重新查找尾部
 
 下边来看 哨兵结点如何产生的
 
```java
  public E poll() {
        restartFromHead:
        for (;;) {
            for (Node<E> h = head, p = h, q;;) {
                E item = p.item;

                if (item != null && p.casItem(item, null)) {
                    // Successful CAS is the linearization point
                    // for item to be removed from this queue.
                    if (p != h) // hop two nodes at a time
                        updateHead(h, ((q = p.next) != null) ? q : p);
                    return item;
                }
                else if ((q = p.next) == null) {
                    updateHead(h, p);
                    return null;
                }
                else if (p == q)
                    continue restartFromHead;
                else
                    p = q;
            }
        }
    }
```   
>这里写起来又得很麻烦 推荐还是看原书吧 写一些点 这个代码如果看懂之前的offer看这个应该是比较容易了
 
 
首先假设加了一个元素在链表中 当前的head的item是null的 使用直接跳到最后p=q 注意在第二个判断中q=p.next 所以这时候p就是p.next了 那么第二次循环item显然不是null的 那么才会去执行`p.casItem(item.null)`这条语句 成功了就往下走 p当然不等于链表的head了 所以就更新头 而原有的head就被设置为哨兵了 

这其实也能感觉到CAS操作设计非常复杂 好处是性能提升 但是难度也是一大跨度

### 3.3.5 高效读取：不变模式下的CopyOnWriteArrayList
很多应用场景下 读远远大于写 这也是之前的读写锁说的话 
为了将读取的性能发挥到极致 JDK中提供了CopyOnWriteArrayList类 对它来说 读取完全不用加锁 并且更好的消息是 写入也不会阻塞读操作 只有写入与写入之间需要同步等待 
其实就是在写入操作时 进入一次自我复制 换句话说 当这个List需要修改时 我不修改原有的内容 而是对原有的数据进行一次复制 将修改的内容写入副本中 写完之后 再将修改完的副本替换原来的数据 这样就可以保证写不影响读了

       public E get(int index) {
            return get(getArray(), index);
        }
       final Object[] getArray() {
             return array;
        }   
       public E get(int index) {
              return get(getArray(), index);
       }

读取代码没有然后同步控制和所操作 理由就是内部数据array不会发生修改 只会被另外一个array替换因此可以保证数据安全
写入就麻烦了
```java
 public boolean add(E e) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            Object[] elements = getArray();
            int len = elements.length;
            Object[] newElements = Arrays.copyOf(elements, len + 1);
            newElements[len] = e;
            setArray(newElements);
            return true;
        } finally {
            lock.unlock();
        }
    }
```
写入操作用锁 当然这个锁仅限于控制写-写的情况 其重点在于 进行了内部元素的玩转复制 因此 会生成一个新的数组newElements 然后 天魂 而且array是volatile变量 会立即发现

### 3.3.6 数据共享通道：BlockingQueue
前面提到 是用ConcurrentQueue作为高性能的队列的
并发是追求高性能的 但是多线程的开发模式还会引入一个问题 如何进行多个线程间的数据共享呢 
一般来说 我们希望整个系统是松散耦合的 

把这个BlockingQueue当做一个‘意见箱’ 双方都放东西 但是双方解耦 保证系统平滑过渡
BlockingQueue是一个接口 主要还是在Blocking上 这个意思就是阻塞 
BlockingQueue会让服务线程在队列为空时 进行等待 当有新的消息进入队列后 自动将线程唤醒

我们主要还是用ArrayBlockingQueue这个实现类来说明
向队列中压入元素可以使用offer()和put()方法 对于offer方法 如果当期队列已经满了 它就会返回false 如果没有满 则执行正常的入队操作 所以我们不讨论这个方案 关注put方法 put方法也是将元素压入队列末尾 但如果队列满了 它会一直等待 直到队列中有空闲的位置

从队列中弹出元素可以用poll()方法和take()方法 它们都从队列的头部获得一个元素 不同之处在于 如果队列为空 poll()方法之间返回null,而take()方法会等待 直到队列内有可用元素
因此put方法和take方法才是提醒Blocking的关键 为了做好等待和通知俩件事 在ArrayBlockingQueue定义了如下字段
    
           final ReentrantLock lock;
        
            private final Condition notEmpty;
        
            private final Condition notFull;
当执行take()操作时 如果队列为空 则让当前线程等待在notEmpty上 新元素入队时 则执行一次notEmpty上的通知        
```java
    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();
            return dequeue();
        } finally {
            lock.unlock();
        }
    }
```  
这里如果为空就等待 等待新元素的插入 唤醒notEmpty
```java
   private void enqueue(E x) {
        // assert lock.getHoldCount() == 1;
        // assert items[putIndex] == null;
        final Object[] items = this.items;
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        notEmpty.signal();
    }
```
同理 对Put()操作也一样 当队列满是 需要让压入线程等待

```java
      public void put(E e) throws InterruptedException {
             checkNotNull(e);
             final ReentrantLock lock = this.lock;
             lock.lockInterruptibly();
             try {
                 while (count == items.length)
                     notFull.await();
                 enqueue(e);
             } finally {
                 lock.unlock();
             }
         }
```
 这里如果为空就等待 等待元素的删除 唤醒notFull  
```java
   private E dequeue() {
        // assert lock.getHoldCount() == 1;
        // assert items[takeIndex] != null;
        final Object[] items = this.items;
        @SuppressWarnings("unchecked")
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        if (itrs != null)
            itrs.elementDequeued();
        notFull.signal();
        return x;
    }
```   
### 3.3.6 跳表（SkipList）
跳表是一种可以用来快速查找的数据结构 有点类似于平衡树 它们都可以对元素进行快速的查找 但一个重要的区别是：对平衡树的插入和删除往往很可能导致平衡树进行一次全局的调整 对跳表的插入和删除只需要对整个数据结构的局部进行操作即可 这样带来的好处是：在高并发的情况下 你会需要一个全局锁来保证整个平衡树的线程安全 而对于跳表 你只需要部分锁即可 这样 在高并发的环境下 你就可以拥有更好的性能 而就查询的性能而言 跳表的时间复杂度也是O(log n) 所以在并发数据结构中 JDK使用跳表来实现一个Map

跳表的另外一个特点是随机算法 跳表的本质是同时维护了多个链表 并且链表是分层的  如下图所示 

![此处输入图片的描述][2]

跳表所有的元素都是排序的 查找时也是如图所示 从顶级链表开始找 一旦发现被查找的元素大于当前链表中的取值 就会转入下一层链表继续找 这也就是说 查找的过程是跳跃式的


因此 很显然 跳表是一种使用空间换时间的算法

使用链表实现Map和使用哈希算法实现Map的另外一个不同之处是：哈希并不会保存元素的顺序 而跳表所有的元素都排序的 因此在对跳表进行遍历时 你会得到一个有序的结果 所以 如果你的应用需要有序性 那么跳表就是你不二的选择 


跳表的内部结构有几个关键数据结构组成 一个是Node 一个是Index
Node则就是key value 还有一个next指向下一个Node Index就是索引 内部包装了Node 同时增加了向下引用与向上应用   此外 对于每一层的表头
还需要记录当前处于哪一层 为此 还需要一个称为HeadIndex的数据结构  表示链表头部的第一个Index 它继承于Inndex
                                 


  [1]: http://images2015.cnblogs.com/blog/453361/201601/453361-20160125021323270-912734702.png
  [2]: http://www.spongeliu.com/wp-content/uploads/2010/09/2.png
  
 