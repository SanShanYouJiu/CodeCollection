# 第5章 并行模式与算法

## 5.1 探讨单例模式
 
单例模式是一个对象创建模式  用于产生一个对象的具体实例 它可以确保系统中一个类只产生一个实例 在Java中 这样的行为能带来俩大好处

- 对于频繁使用的对象 可以省略new操作花费的时间 这对于那些重量级对象而言 是非常可观的一笔系统开销
- 由于new操作的次数减少 因而对系统内存的使用频率也会降低 这将减轻GC压力 缩短GC停顿时间

严格来说  单例模式与并行没有直接的关系 

下面是一个单例的实现
```java
 public class Singleton{
  private Singleton(){
  System.out.println("Singleton is create");
 }
 privat static Singleton instance =new Singleton();
 
 public static Singleton getInstance(){
    return instance;
 }
```
要保证系统中不会有人意外创建多余的实例 因此 我们把Sington的构造函数设置为private 这点非常重要 这就警告所有的开发人员 不能随便创建这个类的实例 从而有效避免该类被错误的创建

第二点 instance对象必须是private并且static的 如果不是privat 那么instance的安全性无法得到保证 一个小小的以外就可能使得instance变成null 其次 因为工程方法getInstance()必须是static的 因此对于的instnace也必须是static

 但是这种方式有一点不足 就是Singleton构造函数 或者说Sington实例在什么时候创建是不受控制的 对于静态成员instance 它会在类第一次初始化的时候被创建 这个时刻并不一定是getInstance()方法第一次被调用的时候
 
 比如
```java
 public class Singleton{
 public static int STATUS=1;
 
  private Singleton(){
  System.out.println("Singleton is create");
 }
 privat static Singleton instance =new Singleton();
 
 public static Singleton getInstance(){
    return instance;
 }
```
注意 这个单例还包含一个表示状态的静态成员STATUS 此时 在相同任何地方应用这个STATUS都会导致instance实例被创建（任何对Singleton方法或者字段的引用 都会导致类初始化 并创建intance实例 但是类初始化只有一次 因此instance实例永远只会被创建一次）

比如
    
    System.out.println(Singleton.STATUS);
上述println会打印出

    Singleton is  create 
    1
可以看到 即使系统没有要求创建单例 new Singleton（）也会被调用

如果你想精准控制instance的创建时间 那么这种方法就不太友善了 
有一种新的方法 一种支持延迟加载的策略 它慧慧在instance背第一次使用时 创建对象 具体实现如下
 
 相关代码请见 [LazySingleton.java][1]

LazySingleton的核心思想如下 最初 并不需要实例化instance 而当getInstance()方法被第一次调用时  创建单例对象 为了防止对象被多次创建 我们不得不需使用synchronized进行方法同步 这种实现的好处是充分利用了延迟加载 只在真正需要时创建对象 但坏处也很明显并发加锁竞争激烈的场合对性能会产生一定的影响

此外 还有一种被称为双重检测模式的方法可以用于创建单例 这里不打算介绍 这是一种不好又复杂的方法 甚至在低JDK中不能保证正确性 
有一种方法可以结合二者之优势

相关代码请见[StaitcSingleton.java][2]

以上代码实现了一个单例 并且同时拥有前俩种方法的优点 首先getInstance()方法中没有锁 这使得在高并发环境下性能卓越 其次 只有在getInstance()方法被第一次调用时  StaticSingleton的实例才会被创建 因为这种方法巧妙地使用了内部类和类的初始化方式 内部类SingletonHolder被申明为private 这使得我们不可能在外部访问并初始化它 而我们值可能在getInstance()内部对SingletonHolder类进行初始化 利用虚拟机的类初始化机制创建单例

## 5.2 不变模式
多线程对同一个对象进行读写操作时  为了保证对象数据的一致性和正确性 有必要对对象进行同步 而同步操作对系统性能是有相当的损耗的 可以使用一种不会改变的对象 依靠对象的不变形 可以确保其在没有同步操作时的多线程环境中依然始终保持内部状态的一致性和正确性  这就是不变模式

不变模式天生就是多线程友好的 它的核心思想是 一旦一个对象被创建 则它的内部状态永远不会发生改变 所以 没有一个线程可以修改其内部状态和数据  同时其内部状态也绝不会自行发生改变 基于这些特性 对不变对象的多线程操作不需要进行同步控制

同时还需要注意 不变对象和只读属性是有一定的区别的 不变模式是比只读属性具有更强的一致性和不变形 对只读属性的对象而言 对象本身不能被其他线程修改 但是对象的自身状态却可能自行修改

因此 不变模式的主要使用场景需要满足以下2个条件：

- 当对象被创建后 其内部状态和数据不再发生任何变化
- 对象需要被共享 被多线程频繁访问

在Javayuy中 不变模式的实现很简单 为确保对象被创建后 不发生任何改变 并保证不变模式正常工作 只需要注意以下4点

- 去除setter方法以及所有修改自身属性的方法
- 将所有属性设置为私有 并用final标记 确保其不可修改
- 确保没有子类可以重载它的行为
- 有一个可以创建完整对象的构造函数

下面代码实现了一个不变的产品对象 它拥有序列号 名称 和价格三个属性
    
[Product.java][3]    

在JDK中 不变模式用的非常广泛 其中 最为典型的就是java.lang.String类 此外 所有元数据包装类 都是使用不变模式实现的  

由于基本数据类型和String类型在实际的软件开发中应用极其广泛 使用不变模式 所有实例的方法都不需要同步操作 保证了多线程下的性能

> 不变模式通过回避问题而不是解决问题的态度来处理多线程并发访问控制

## 5.3 生产者-消费者模式
生产者-消费者模式是一个经典的多线程设计模式 它为多线程间的协作提供了良好的解决方案 在生产者-消费者模式中 通常有两类线程  即若干个生产者线程和若干个消费者线程 生成者线程负责提交用户请求 消费者线程则负责处理生产者提交的任务 生产者和消费者之间通过共享内存缓冲区来进行通信
> 生产者-消费者模式中的内存缓存区的主要功能是数据在多线程间的共享 此外 通过该缓冲区 可以缓解生成者和消费者之间的性能差

生产者-消费者模式的核心组件是共享内存缓冲区 它作为生产者和消费者间的通信桥梁
 

角色|作用
--|--
生产者|用于提交用户请求 提取用户任务 并装入内存缓冲区
消费者|在内存缓冲区中提取并处理任务
内存缓冲区|缓冲生产者提交的任务或数据 供消费者使用

其中 BlockingQueue充当了共享内存缓冲区 用于维护任务或数据队列

![此处输入图片的描述][4]
> BlockingQueue在第三章

相关代码请见[BlcokingQueue][5]

## 5.4 高性能的生产者-消费者：无锁的实现
BlockingQueue用于实现生产者和消费者一个不错的选择 它可以很自然的实现作为生产者和消费者的内存缓冲区 
但是BlockingQueue并不是一个高性能的实现 它完全使用锁和阻塞等待实现线程间的同步 在高并发场合 它的性能并不是特别的卓越 就像之前已经提过的ConcurrentLinkedQueue是一个高性能的队列 但是BlockingQueue只是为了方便数据共享



### 5.4.1 无锁的缓存框架：Disruptor

Disruptor是由LMAX公司开发的一款高效的无锁内存队列 它使用无锁的方式实现了一个环形队列 非常适合于实现生产者和消费者模式 比如事件和消息的发布 在Disruptor中 别出心裁的使用了环形队列（RingBuffer）来代替普通线性队列 这个环形队列内部实现为一个普通的数组 对于一般的队列 势必要提供队列同步head和尾部tail俩个指针 用于出队入队 增加了线程协作的复杂度 但是如果队列是环形的 则只需要对外提供一个当前位置cursor 利用这个指针即可以入队也可以进行出队操作 由于环形队列的缘故 队列的总大小必须事先指定  不能动态扩展 为了能快速从一个序列对应数组的实际位置（每次有元素入队 序列就加1），Disruptor要求我们必须将数组的大小设置为2的整数次方这样通过sequence&（queueSize-1）就能立即定位到实际的元素位置index  这个要比取余(%)操作快得多

如果大家不理解上面的sequence&（queueSize-1） 在这里简单说明一下 如果queueSize是2的整数次幂 则这个数字的二进制表示比如是10，100,1000 等形式 因此queueSize-1的二进制是一个全1的数字 因此它可以将sequnce限定在queueSize-1的范围内 并且不会有任何一位是浪费的

![此处输入图片的描述][6]
 相关代码请见[Disruptor案例][7]
Disruptor至少要比BlockingQueue要高一个量级以上

### 5.4.3 提高消费者的响应时间：选择合适的策略
当有新数据在Disruptor的环形缓冲区中产生时 消费者如何知道这些新产生的数据呢 或者说 消费者如何监控缓冲区中的信息呢 为此 Disruptor提供了几种策略 这些策略由WaitStrategy接口封装 主要有以下几种实现

- BlockingWaitStrategy：这是默认的策略 使用BlockingWaitStrategy和使用BlockingQueue是非常类似的 它们都使用锁和条件(Condition)进行数据的监控和线程的唤醒 因为涉及到线程的切换 BlockingWaitStrategy策略是最节省CPU 但是在高并发下性能表现最糟糕的一种等待策略

- SleepingWaitStrategy：这个策略也是对CPU使用率非常保守的  它会在循环中不断等待数据 它会先进行自旋等待 如果不成功 则使用Thread.yiled()让出cpu 并最终使用LockSupport.parkNanos(1)进行线程休眠 以确保不占用太多的CPU数据 因此 这个策略对于数据处理可能产生比较高的平均延时 它比较适合于延时要求不是特别高的场合 好处是它对生产者线程影响最小 典型的应用场景是异步日志

- YiedldingWaitStrategy:这个策略用于低延时的场合 消费者线程会不断循环监控缓冲区变化 在循环内部 它会使用Thread.yield()让出CPU给别的线程执行时间 如果你需要一个高性能的系统 并且对延时有较为严格的要求 则可以考虑这种策略 使用这种策略时 相当于你的消费者线程变身为一个内部执行了Thread.yield()的死循环 因此 你最好有多余消费者线程数量的逻辑CPU数量（这里的逻辑CPU 指的是“双核四线程”中的四线程 否则 整个应用程序恐怕都会受到影响）

- BusySpinWaitStrategy:这个是最疯狂的等待策略 它就是一个死循环！ 消费者线程会尽最大努力疯狂的监控缓冲区的变化 因此 它会吃掉所有的CPU资源 你只有在延时非常苛刻的场合可以考虑使用它（或者说 你的系统真的非常繁忙） 因为在这里你等同开启了一个死循环监控 所以你的物理CPU必须要大于消费者线程数 注意 这里说的是物理CPU 不是超线程技术模拟的俩个逻辑核 另外一个逻辑核显然会受到这种超密集计算的影响而不能正常工作

### 5.4.4 CPU cache的优化：解决伪共享问题

除了使用CAS和提供了各种不同的等待策略来提高系统的吞吐量外 Disruptor大有优化到底的气势 甚至尝试解决CPU缓存的伪共享问题

什么是伪共享问题 为了提高CPU的速度 CPU有一个高速缓存cache 在高速缓存中 读写数据最小单位为缓存行（Cache line） 它是从主存（memory）复制到缓存（Cache）的最小单位 一般为32字节到128字节

如果俩个变量存放在一个缓存行中 在多线程访问时可能会相互影响彼此的性能 
![此处输入图片的描述][8]

为了不使这种情况发生 一种可行的方法就是在变量的前后都先占据一定的位置（叫做padding吧） 这样 当内存被读入缓存时 这个缓存行 只有这个变量是实际有效的  因此就不会发生多个线程修改缓存行中不同变量而导致变量全体失效的情况

相关代码请见 [FlaseSharing.java][9]

在代码的55行 准备了7个long型变量来填充缓存 实际上 只有VolatileLong.value是被使用的 而那写p1,p2等仅仅用于将数组中第一个VolatileLong.value和第二个VolatileLong.value分开 防止它们进入同一个缓存行
> 注意 由于各个JDK版本内部实现不一致 在某些JDK版本中（比如JDK8）会自动优化不使用的字段 这将直接导致这种padding的伪共享问题解决方案失效 更多详细内容到第6章有关LongAddr的介绍

在Disruptor内部充分考虑了这个问题 
```java
public final class PaddedLong extends MutableLong
{
    public volatile long p1, p2, p3, p4, p5, p6 = 7L;

    /**
     * Default constructor
     */
    public PaddedLong()
    {
    }

    /**
     * Construct with an initial value.
     *
     * @param initialValue for construction
     */
    public PaddedLong(final long initialValue)
    {
        super(initialValue);
    }

    public long sumPaddingToPreventOptimisation()
    {
        return p1 + p2 + p3 + p4 + p5 + p6;
    }
}
```

## 5.5 Future模式

Future模式是多线程开发中非常常见的一种设计模式 它的核心思想是异步调用
![此处输入图片的描述][10]

### 5.5.1 Future模式的主要角色

参与者|作用
--|--
Main|系统启动 调用Client发出请求
Client|返回Data对象 立即返回FutreData并开启ClientThread线程装配RealData
FutureData|Future数据 构造很快 但是是一个虚拟的数据 需要装配RealData
RealData|真实数据 其构造是比较慢的
![此处输入图片的描述][11]

### 5.5.2 Future模式的简单实现
有一个核心接口Data 这就是客户端想要的数据 
在Futre模式中 这个接口有俩个重要的实现 一个是RealData  也就是真实数据 一个是FutureData 只是用来提取RealData的一个订单
因此FutureData是可以立即返回的
```java
public interface Data {
    public String getResult();
}
```
FuturData实现了一个快速返回 它只是一个包装 或者说是一个RealData的虚拟实现 因此 它可以很快被构造并返回 当使用FutureData的getResult()方法时  如果实际的数据没有准备好 那么程序就会被阻塞  等待RealData准备好并注入到FutureData中 才最终返回数据
>FuturData是Future模式的关键 它实际上是真实数据RealData的代理 封装了获取RealData的等待过程

相关代码请见[Future模式][12]

### 5.5.3 Jdk内部的Future模式
RunnablFuture继承了Future和Runnable俩个接口 其中run()方法用于构造真实的数据 它有一个具体的实现FutureTask类 
FutureTask有一个内部类Sync 一些实质性的工作 会委托给Sync类实现 而Sync类最终会调用Callable接口 完成实际数据的组装工作

![此处输入图片的描述][13]
Callable接口只有一个方法call() 它会发货需要构造的实际数据 这个Callable接口也是这个Future框架和应用程序之间的重要接口 如果我们要实现自己的业务系统 通常需要实现自己的Callable对象 此外FutureTask类也与应用密切关联 
 
[JDK内部的Futute模式][14]

## 5.6 并行流水线

并发算法虽然可以充分发挥多核CPU的性能 但不幸的是 并非所有的计算都可以改造成并发的形式 简单的说 执行过程中有数据相关性的运算都是无法完美并行化的

比如(B+C)*B/2 这个过程就无法并行的 原因是 如果B+C无法完成 则永远算不出（B+C)*B 这就是数据相关性 如果线程执行过程中 所需的数据存在这种依赖关系 那么 就没有办法将它们完美的并行化

遇到这种情况 补救措施就是采用日常生活中的流水线思想
相关代码请见[并行计算][15]

## 5.7 并行搜索
搜索是几乎每个软件都有个功能 对于有序数据 通常可以采用二分法 对于无序数据 只能挨个查找

给定一个数组 要查找满足条件的元素 对于串行程序来说 只要遍历一下数组就可以得到结果 但如果要使用并行方式 则需要额外增加一些线程间的通信机制 使各个线程可以有效的运行

一种简单的策略就是将原始数据集合按照期望的线程数进行分割，如果我们计划使用俩个线程进行搜索 那么就可以把一个数组或集合分割成俩个 每个线程各自的独立搜索 当其中有一个线程找到数据后 立即返回结果即可

相关代码请见 [SearchDemo.java][16]
## 5.8 并行排序
排序是一个非常常用的操作 在应用程序运行时  无时无刻不在排序
当排序元素有很多时  若使用并行算法代替串行算法 显然可以更加有效的利用CPU  但将串行算法改造为并行算法并非易事  甚至会极大的增强原有算法的复杂度
这里介绍几个简单平行排序算法

### 5.8.1 分离数据相关性：奇偶交换排序
奇偶排序是对冒泡排序的并行改造

>在[SerialSort.java][17]中有相关冒泡排序的代码

对于奇偶排序来说 它将排序分为俩个阶段 奇交换与偶交换  对于奇交换来说 它总是比较奇数索引以及相邻的后续元素 而偶交换总是比较偶数索引和其相邻的后续元素 并且 奇交换与偶交换会成对出现 这样才能保证比较和交换涉及到数组中的每一个元素
奇偶交换的串行实现也在[SerialSort.java][18]中有相关代码

这样的代码虽然是串行代码 但是已经很好改造为并行模式了 

相关代码请见[OddEventSort.java][19]

### 5.8.2 改进的插入排序：希尔排序
插入排序也是一种很常用的排序算法 
>在[SerialSort.java][20]中有相关插入排序的代码
简单的插入排序是很难并行化的 因为这一次的数据插入依赖上一次得到的有序排列 因此多个步骤是无法并行的

希尔排序将整个数组根据间隔h分割为若干个子数组 子数组相互穿插在一起 每一次的排序时 分别对每一个子数组进行排序

在每一组排序完成后 可以递减h的值 进行下轮更加精细的排序 直到h为1 此时等价于一次插入排序

并行排序的一个主要优点是，即使一个较小的元素在数组的末尾 由于每次元素移动都以h为间隔进行 因此数组末尾的小元素可以在很少的交换次数下 就被置换到最接近元素最终位置的地方

希尔排序的串行实现
相关代码请见[SerialSort.java][21]

希尔排序就很好改造为并行程序了
相关代码请见[ShellSort.java][22]


##5.9 并行算法:矩阵算法
> 同第四章的无锁Vector一样 不好找具体工具 就不再说明了

## 5.10 准备好了再通知我：网络NIO
Java NIO是NEW IO的简称 它是一种可以替代Java IO的一套新的IO机制 它提供了一套不同于java标准的IO的操作机制 严格来说 NIO与并发无直接的关系 但是 使用NIO技术可以大大的提高线程的使用效率

Java NIO涉及的基础内容有通道(Channel)和缓冲区（Buffer）,文件IO和网络IO 有关通道，缓冲区以及文件IO在这里不打算进行详细的介绍 


### 5.10.1 基于Socket的服务端的多线程模式
这里 以一个简单的Echo服务器为例 对于Echo服务器 它会读取客户端的一个输入 并将这个输入原封不动的返回给客户端 

相关代码请见 [MultiThreadEchoServer.java][23]
这是一个支持多线程的服务端的核心内容 它的特点是 在相同可支持的线程访问内  可以尽量多地支持客户端的数量 同时和单线程服务器相比 它可以更好的支持多核CPU 
相关代码请见[MultiThreadEchoClient.java][24]

对于绝大部分应用来说 这种模式可以很好地工作 但是 如果想让你的程序工作更加高效 就必须知道这个模式一个重大的弱点 那就是倾向于让CPU进行IO等待 
下面有个清晰的例子
[HeavySocketClient.java][25]
之所以处理的慢 并不是因为服务端有多少繁重的业务 而仅仅是因为服务线程在等待IO而已 让高速运转的CPU去等待极其低效的网络IO是非常不合算的行为 
是不是可以将网络IO的等待时间从线程中分离出来呢？

### 5.10.2 使用NIO进行网络编程
>[一个NIO入门链接][26]

首先知道NIO中的一个关键组件Channel(通道)Channel有点类似于流 一个Channel可以和文件或者网络Socket对应 如果Channel对应一个Socket 那么往这个Channel中写数据 就等于往Socket中写数据

和Channel一起使用的另外一个重要组件就是Buffer 大家可以简单的把Buffer理解成一个内存区或者Byte数组 数据需要包装成Buffer的形式才能和Channel交互(写入或读取）

另外一个与Channel密切相关的是Selector(选择器) 在Channel众多实现中 SelectableChannel实现 表示可被选择的通道 
  任何一个SelectableChannel都可以将自己注册到一个Selector中 这样这个Channel就能被Selector所管理  而一个Selector可以管理多个SelectableChannel 当SelectableChannel的数据准备好时 Selector就会接到通知 得到那写已经准备好的数据 而SocketChannel就是SelectableChannel的一种
  
这样的话 一个Selector可以由一个线程进行管理 而一个SocketChannel则可以表示一个客户端连接 因此就构成由一个或者极少数线程 来处理大量客户端连接的结构 当与客户端连接的数据没有准备好时 Selector会处于等待状态(不过 幸好 用于管理Selector的线程是极少量的）  而一旦有任何一个SocketChannel准备好了数据 Selector就能立即得到通知 获取数据进行处理

 相关代码请见[NioServerSocket.java][27]

### 5.10.3 使用NIO来实现客户端

相关代码请见    [NioSocketClient.java][28]
## 5.11  读完了再通知我：AIO
AIO是异步IO的缩小  即Asynchronized 虽然NIO在网络操作中 提供了非阻塞的方法 但是NIO的IO行为还是同步的 对于NIO来说 我们的业务线程是在IO操作准备好时 得到通知 接着就由这个线程自行进行IO操作 IO操作本身还是同步的

但是对AIO来说 就更进一步  它不是在IO准备好时再通知线程 而是在IO操作已经完成后 再给线程发出通知  因此AIO是完全不会阻塞的 此时  我们的业务逻辑将变为一个回调函数 等待IO操作完成后 由系统自动触发 

### 5.11.1 AIO EchoServer的实现

相关代码请见[AioEchoServer.java][29]

### 5.11.2 AIO Echo客户端实现

相关代码请见[AioEchoClient.java][30]


  [1]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/LazySingleton.java
  [2]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/StaticSingleton.java
  [3]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/Product.java
  [4]: http://img.blog.csdn.net/20170629174908752?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvaGVfc2h1YWkyMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center
  [5]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/blocingQueue
  [6]: https://modeshape.files.wordpress.com/2014/04/ringbuffer-31.png
  [7]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/disruptor
  [8]: https://www.ibm.com/developerworks/cn/aix/library/au-aix-multicore-multiprocessor/false_sharing.png
  [9]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/disruptor/FalseSharing.java
  [10]: http://img.blog.csdn.net/20141112192253656
  [11]: http://static.zybuluo.com/csqiang1992/4hdkathsfqkngvyd5ndus1az/futrue-core-impl.png
  [12]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/future
  [13]: http://www.joyhwong.com/wp-content/uploads/2016/11/123.jpg
  [14]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/future2
  [15]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/compute
  [16]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/search/SearchDemo.java
  [17]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/sort/SerialSort.java
  [18]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/sort/SerialSort.java
  [19]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/sort/OddEventSort.java
  [20]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/sort/SerialSort.java
  [21]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/sort/SerialSort.java
  [22]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/concurrent/sort/ShellSortDemo.java
  [23]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/io/MultiThreadEchoServer.java
  [24]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/io/MultiThreadEchoClient.java
  [25]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/io/https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/io/HeavySocketClient.java
  [26]: https://www.ibm.com/developerworks/cn/education/java/j-nio/j-nio.html#ibm-pcon
  [27]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/nio/NioServerSocket.java
  [28]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/nio/NioSocketClient.java
  [29]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/aio/AioEchoServer.java
  [30]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5/socket/aio/AioEchoClient.java