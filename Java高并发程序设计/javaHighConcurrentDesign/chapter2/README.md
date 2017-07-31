# Java并发程序基础

## 2.1线程的六个基本状态
NEW RUNNABLE BLOCKED,WAITING,TIMED_WAITING,TERMINATED

## 2.2 线程基本操作
 
### 2.2.1  新建线程
>一些简单的基本的耗字的就不写了 

这里主要是Thread与Runnable区别 以及Start开始线程方法 推荐使用Runnable接口进行编写并发代码 这也是最常见的方式

### 2.2.2 终止线程
stop方法(该方法会立即终止该线程)被废弃了 原因是太过暴力 可能导致数据不一致的问题 
因为在stop方法会立即结束锁然后立即释放锁 而这些锁是用来维持对象的一致性的 如果写数据写到一半 并强行终止的话  那么对象就会被写坏 另外一个等待该锁的就会读到这个不一致的对象 悲剧就发生了 
 
 相关代码请见 [StopThreadUnsafe][1]   
 
 那怎么改进呢才合适呢 其实只需要自己决定线程何时退出就可以了 
 增加一个stopMe的volatile字段 再自定义一个是否StopMe的方法 
 
 相关代码请见 [StopThreadSafe][2]
 
 ### 2.2.3 线程中断
 在java中 线程中断是一个重要的线程协作机制 中断就是让目标线程停止运行的意思 实际并非如此  严格的讲 线程中断并不会使线程退出 而是给线程发送一个通知 告知目标线程 有人希望你退出 至于目标线程接到通知如何处理 则完全由目标线程自己决定  如果中断后 无条件退出 就会遇到stop方法的老问题 
 
 与中断线程有关的三个方法
 
 1. interrupt() //中断线程
 2. isInterrupted() //判断是否中断
 3. interrupted() //判断是否被中断 并清除当前中断状态
 
 中断方法乍看与上面的stop标记的手法非常相似 但是中断更为强力 比如 如果在循环体中 出现了类似wait()或者sleep()这样的操作 则只能用中断来识别了

 相关代码请见 [InterruptTest][3]
 
 ### 2.2.4 等待与通知
 
 wait方法与notify方法
 notify是随机的唤醒一个线程 notifyAll会唤醒所有等待的线程
 
 这俩个方法的关键在一个监听器 在使用wait方法前必须获得object对象的监听器 wait方法执行后 会释放这个监听器 这样做的目的是使得其他等待在object对象上的线程不至于因为第一个线程的休眠而全部无法正常执行 在第二个线程使用notify前也必须获得一个object的监听器 然后获得这个监听器后 notify就会尝试去唤醒一个等待线程   在线程被唤醒后 第一件事是尝试获得这个监听器 而不是执行后续代码 如果暂时无法获得 就等待这个监听器 获得了之后 才能真正的继续执行
  
 相关代码请见 [SimpleWN][4]
 
 
 ### 2.2.5 suspend与resume方法
 
 suspend与resume方法 现在已经是不推荐的操作了 不推荐使用的原因是suspend会导致线程暂停的同时 不会去释放任何锁资源  此时  其他任何线程想要访问被它暂用的锁时 都会被牵连 导致无法正常运行 直到对应的线程上进行了resume操作  被挂起的线程才能继续 从而其他所有阻塞在相关锁上的线程也可以继续执行  但是，如果resume操作意外的在suspend之前的执行了  那么挂起的线程很难有机会继续执行  并且 它占用的锁不会释放 因此可能会导致整个系统工作不正常  而且 对于被挂起的线程 从它的线程状态来看 居然还是Runnable  也会严重影响对系统当前状态的判断
 
 相关代码请见 [BadSuspend][5]  
 
 如果需要一个可靠的suspend函数的话 可以利用wait与notify方法 
  给出一个标记变量suspendMe  表示当前线程是否被挂起  同时增加了suspendMe和resumeMe俩个方法 分别用于挂起线程与继续执行线程
 
 相关代码请见 [GoodSuspend][6]
 
 ### 2.2.6 等待线程结束(join)与谦让(yield)
 
 Join有俩个不同参数的方法 
 - 一个是默认的无限等待 一直阻塞当前线程 直到目标线程执行完毕
 - 第二个方法给出了一个最大等待时间 如果超过给定时间目标线程还在执行  当前线程也会因为“等不及了”，而继续往下执行
 
 相关代码请见 [JoinMain][7]
 
 有关Join 补充一点 join的本质是让调用线程wait在当前线程对象实例上 
 下面是JDK中join实现的核心代码片段
    
      while(isAlive()){
    wait(0);
    }
    
可以看到 它让调用线程在当前线程对象上进行等待 当线程执行完成后 被等待的线程也会在退出前调用notifyAll()通知所有的等待线程继续执行  因此 值得注意的一点是：不要在应用程序中 在Thread的对象实例上使用类似wait()或者notify()等方法 因为这很有可能会影响到系统API的 或者被系统API所影响

Thread.yield（）方法  它的定义如下
    
        public static native void yield();
        
 这是一个静态方法  一旦执行 它会使当前线程让出CPU  但要注意 让出CPU并不表示当前线程不执行了 当前线程在让出CPU后 还会执行CPU资源的争夺 但是是否能够被再次分配到 就不一定了 因此 对Thread.yield调用就好像是在说：我已经完成了一些重要的工作 我应该是可以休息一下了 ，可以给其他线程一些工作机会了
        
  如果你觉得一个线程不是那么重要 或者优先级非常低 而且又害怕它会占用太多的CPU资源 那么可以在适当的时候调用Thread.yield() 给予其他重要线程更多的工作机会 
  
## 2.3 volatile与Java内存模型(JMM)  

volatile的语义是 易变的 不稳地的 这也正是使用volatile关键字的语义 

当你使用volatile去声明一个变量时  就等于告诉了虚拟机 这个变量极有可能会被某些程序或者线程修改 为了确保这个变量被修改后 应用程序范围内的所有线程都能够“看到”这个改动，虚拟机就必须采用一些特殊的手段  保证这个变量的可见性等特点

volatile对保证操作的原子性是有非常大的帮助的 但是 需要注意的是,volatile并不能代替锁 ,它也无法保证一些复合操作的原子性 

相关代码请见 [VolatileAtomicTest][8]

volatile也可以保证数据的可见性和有序性 

相关代码请见 [NoVisibility][9]

## 2.4分门别类的管理：线程组

相关代码请见  [ThreadGroupName][10]   

## 2.5 驻守后台：守护线程(Daemon)

守护线程是一种特殊的线程 就和它的名字一样 它是系统的守护者 在后台默默地运行一些系统性的服务 比如垃圾回收线程 JIT线程就可以理解为守护线程 与之相对应的就是用户线程 用户线程可以认为是系统的工作线程 它会完成这个程序应该要完成的业务操作 如果用户线程全部结束了  这意味着这个程序实际上无事可做了 守护线程要守护的对象已经不存在 那么整个应用程序就自然应该结束 因此 当一个Java应用内 只有守护线程时 Java虚拟机就会自然退出

相关代码请见 [DaemonDemo][11]

守护线程必须在线程start()之前设置 否则会得到一个IllegalThreadStateException异常 然后程序和线程依然可以运行 只不过被当做了用户线程而已 

## 2.6 先干重要的事：线程优先级

Java的线程可以有自己的优先级 优先级高的在竞争线程时会更有优势 更可能抢占资源 当然 这只是一个概率问题 运气不好 也抢不到 这个线程的优先级调度和底层操作系统有密切的关系 在各个平台上表现不一 并且这种优先级产生的后果也可能不容易预测 无法精准控制 因此 在要求严格的场合 还是需要自己在应用层解决线程调度问题

在Java中使用1-10表示线程优先级 一般可以使用内置的三个静态标量表示

      public final static int MIN_PRIORITY = 1;
      
      public final static int NORM_PRIORITY = 5;
    
      public final static int MAX_PRIORITY = 10;
      
  数字越高则优先级越大 但有效范围在1-10 高优先级的线程倾向于更快的完成
  
  相关代码请见 [PriorityDemo][12]    
  
## 2.7  线程安全的概念与synchronized

volatile不能真正保证线程安全 它只能确保一个线程修改了数据后 其他线程能够看到这个改动 但当俩个线程同时修改某一个数据时  却依然会产生冲突 

相关代码请见 [AccountingVol][13]

要从根本解决这个问题 我们就必须保证多个线程对i进行操作时完全同步 也就是说 当线程A在写入时 线程B不仅不能写 同时也不能读  因为在线程A写完之前 线程B读取的一定是一个过期数据 Java中 提供了一个重要的关键字synchronized来实现这个功能

关键字synchronized的作用是实现线程间的同步 它的工作是对同步的代码加锁 使得每一次 只有一个线程进入同步代码块 从而保证线程间的安全性 

关键字synchronized的可以有多种用法 这里做一个简单的整理
- 指定加锁对象：对给定对象加锁 进入同步代码前要获得给定对象的锁
- 直接作用域实例对象：相当与对当前实例加锁，进入同步代码钱要获得当前实例的锁
- 直接作用域静态方法：相当于对当前类加锁 进入同步代码前要获得当前类的锁

相关代码请见 [AccountingSync][14]

一种错误的加锁方式 

相关代码请见 [AccountingSyncBad][15]

除了用于线程同步，确保线程安全外，synchronized还可以保证线程间可见性和有序性 从可见性的角度上讲 synchronized可以完全替代volatile的功能 只是使用上没有volatile方便 就有序性而言 由于synchronized限制的代码都是串行执行的所以不用担心有序性问题 

## 2.8 程序中的幽灵:隐蔽的错误

### 2.8.1 无提示的错误案例

    int v1=1073741827;
    int v2=1473741575;
    int ave=(v1+v2)/2;
    System.out.println(ave);

这里就会出现一个错误 这个错误是因为int的溢出问题 这种问题就是无提示的错误案例 这种问题非常难找  不能得到异常与相关的错误日志 

### 2.8.2 并发下的ArrayList

相关代码请见 [ArrayListMultiThread][16]

这里会出现三种结果
1. 正常结束 最终大小确实2000000
2. 抛出一个越界异常 这是因为ArrayList在扩容过程中 内部的一致性被破坏，但没有锁的保护 另一个线程访问到了不一致的内部状态 导致出现了越界问题
3. 出现了一个非常隐蔽的错误 出现了一个值  比如 1793758
这个是由于多线程访问冲突 使得保存容器大小的变量被多线程不正常的访问 同时俩个线程也同时对ArrayList的同一个位置进行赋值导致的 这种问题 很不幸 是没有错误提示的错误 而且 也不一定能复现


### 2.8.3 并发下诡异的HashMap

相关代码请见 [HashMapMultiThread][17]

这里在Jdk8之前的系统中会出现3个问题 
1. 程序正常结束 结果也正常
2. 程序正常结束 结果不正常
3. 程序永远无法结束

第3个问题在JDK8中被修复了    即使这样 贸然使用HashMap依然会导致内部数据不一致   最简单的解决方案是使用ConcurrentHashMap

### 2.8.4 初学者常见问题：错误的加锁

相关代码请见 [BadLockOnInteger][18]

这个问题其实就是加错了锁 内部的Integer对象是一个不变对象 每次赋值都是创造一个新的对象 所以换个锁对象就好 


  [1]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/StopThreadUnsafe.java
  [2]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/StopThreadSafe.java
  [3]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/InterruptTest.java
  [4]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/SimpleWN.java
  [5]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/BadSuspend.java
  [6]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/GoodSuspend.java
  [7]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/JoinMain.java
  [8]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/VolatileAtomicTest.java
  [9]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/NoVisibility.java
  [10]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/ThreadGroupName.java
  [11]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/DaemonDemo.java
  [12]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/PriorityDemo.java
  [13]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/AccountingVol.java
  [14]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/AccountingSync.java
  [15]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/AccountingSyncBad.java
  [16]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/ArrayListMultiThread.java
  [17]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/HashMapMultiThread.java
  [18]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2/BadLockOnInteger.java