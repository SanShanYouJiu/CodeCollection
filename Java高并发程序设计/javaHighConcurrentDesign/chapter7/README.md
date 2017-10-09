# 第7章 使用AKKA构建高并发程序
写出一个高并发并且可扩展的应用是很难的 那么是否有一个好的框架可以帮助我们轻松构建这么一个应用呢 Akka提供了这么一个方式 Akka是遵循Apache2许可的开源人员 这意味你可以无偿并且几乎没有限制的使用它 包括应用商业环境

Akka是使用scala创建的 但是Scala和java一样 都是jvm上的内容 都可以互相调用 但是实际使用中 还是推荐使用Scala来进行Akka的编写 

Akka提供了一种Actor的并发模型 其粒度比线程更小 可以在代码中启用极其大量的Actor

其次 Akka中提供了一套容错机制 运行在Actor出现异常时进行一些恢复或者重置操作

最后 通过Akka不仅可以在单机上构建高并发程序 也可以在网络上构建分布式程序
并提供位置透明的Actor定位服务

## 7.1 新并发模型:Actor

在使用Akka中 基本就可以忘记线程了 当你使用Akka时 就有了一个全新的执行单元-Actor
Actor可以比喻为一个人 多个人之间可以通过语言交流 

传统Java并行程序 还是完全基于对象的方法 我们还是通过对象的方法调用进行信息的传递 这时 如果对象的方法会修改对象本身的状态 那么在多线程情况下 就有可能出现对象状态的不一致  所以我们就必须对这类方法调用进行同步 当然 同步往往是以牺牲性能为代价的 


在Actor模型中  我们失去了对象的方法调用 我们不是通过调用Actor对象的某一个方法来告诉Actor你需要做什么 而是给Actor发生一条消息 当一个Actor收到消息后 它有可能会根据消息的内容做出某些行为 包括更改自身状态 但是 在这种情况下 这个状态的更改是Actor自己进行的 并不是由外界强迫进行的 
## 7.2 Akka之Hello World

一个Acotr的实现
[Greeter.java][1]
[HelloWorld.java][2]

上述代码中 定义了一个换一种Greeter 继承自UntypedActor（它自然是Akka中的核心成员了） UntypedActor就是我们说的Actor 之所以说是无类型 是因为还有一种有类型 有类型的Actor可以使用系统中的 其他类型构造 可以缓解Java单继承的问题 因为你在继承UntypedActor后 就不能再继承系统中其他类了  如果你一定想这么做  那么就只能选择有类型的Actor 否则UntypedActor就是你的首选 

在HelloWorld.java中又实现了一个HelloWorld的Actor 其中的preStart()方法为Akka的回调方法 在Actor启动前 会在Akka框架调用，完成一些初始化的工作 
在这里 由于创建Greeter时使用的是HelloWorld的上下文 因此 它属于HelloWorld的子Actor
onReceive()函数是为HelloWorld的消息处理函数 

主函数如下：
[HelloWorldMain.java][3]
在主函数中 创建了ActorSystem 表示管理和维护Actor的系统 一般来说 一个应用程序只需要一个ActorSystem就够用了 ActorSystem.create()的第一个参数‘hello’为系统名称 第2个参数为配置文件
通过AcotorSystem创建一个顶级的Acotor(HelloWorld)

可以看到 当使用Actor的时候 关注点已经不在线程上了 实际上 线程调度已经被Akka框架进行了封装 只需关注Actor对象即可 而Actor对象之间的交流和普通对象的函数调用有明显区别 它们是通过显示的消息发送来传递消息的 

当系统有多个Actor存在时 Akka会自动在线程池中选择线程来执行我们的Actor 因此 当多个不同的Actor可能被同一个线程执行 同时 一个Actor也有可能被不同线程执行 因此 一个值得注意的地方是：不要在一个Actor中执行耗时的代码 这样可能会导致其他Actor的调度出现问题 
## 7.3 有关消息投递的一些说明

整个Akka应用是由消息驱动的 消息是除了Actor之外最重要的核心组件 作为在并发程序中的核心组件 在Actor之间传递应该满足不变性 也就是不变模式  因为可变模式无法高效的在并发环境使用 理论上Akka的消息可以使用任何对象实例 但实际使用中 强烈推荐使用不可变模式 

实际上 对于消息投递 可以有3种不同的策略
 
 - 第一种 称为最多一次传递 每条消息最多投递一次 在这种情况 偶尔会有投递失败 从而导致消息丢失
 - 第二种 称为最少一次投递 每一条消息至少会被投递一次 直到成功为止 在一些偶然的场合 接受者可能会受到重复的消息 但不会发生消息丢失 
 - 第三种 称为精准的消息传递，也就是所有的消息精准地投递并成功接收一次  既不会有丢失 也不会重复接收
 很明显 第一种性能最好 第二种其次 第三种 成本最高 最难以实现


那么是否真的需要保证消息投递的可靠性呢
答案是否定的 实际上 我们没有必要在Akka层保证消息的可靠性 这样做  成本太高了 也是没有必要的 消息的可靠性更应该在应用的业务层去维护 因为也许在有些时候 丢失一些消息完全是符合应用要求的 因此在使用Akka时 需要在业务层对此进行保证 

此外 对于消息投递Akka可以在一定程度上保证顺序性 比如Actor A1向A2顺序发送M1，M2和M3三条消息 Actor A3向A2顺序发送了M4，M5和M6三条消息 

1. 如果M1没有丢失 那它一定先于M2和M3被A2收到
2. 如果M2没有丢失 那它一定先于M3被A2收到
3. 如果M4没有丢失 那它一定先于M5和M6被A2收到
4. 如果M5没有丢失 那它一定先于M6被A2收到
5. 对A2来说 来自A1和A3的消息可能交织在一起 没有顺序保证

在这里 值得注意的一点是，这种消息投递规则不具备可传递性 比如：
Actor A向C发生M1，接着Actor A向B发送了M2，B将M2转发给Actor C那么在这种情况下 C收到M1和M2的先后顺序是没有保证的

## 7.4 Actor的生命周期
一个Actor在actorOf()函数被调用后开始建立 Actor实例创建后 会回调preStart()方法 在这个方法里面 可以进行一些资源的初始化工作 在Actor的工作过程中 可能会出现一些异常  这种情况下 Actor会重启 当Actor被重启时 会回调preRestart()方法 （在老的实例上）接着系统会创建一个新的Actor对象实例（虽然是新的实例，但它们都表示同一个Actor）当新的Actor实例创建后 会回调postRestart()方法 表示启动完成 同时新的实例将会代替旧的实例 停止一个Actor也有很多方式 你可以调用Stop()方法或者给Actor发送一个PosionPill Actor停止后 postStop()方法会被调用 同时这个Actor的监听者会受到一个Terminated消息

下面是一个既带有生命周期回调函数的Actor
[MyWorker.java][4]
另外为MyWoker指定了一个监听者
[WatcherActor.java][5]
本质上，它也是一个Actor 但不同的是 它会在它的上下文中watch一个Actor 如果将来这个被监视的Actor的退出终止 WatchActor就能收到一条Terminated消息 在这里 我们将简单地打印终止消息Terminated的相关Actor路径 并且关闭整个ActorSystem

主函数如下
[DeadMain.java][6]
注意在创建WatchActor的时候 第一个参数为要创建的Actor类型 第2个参数为这个Actor的构造函数的参数(在这里 就是要调用WatchActor的构造函数)

## 7.5 监督策略
如果一个Actor在执行过程中发生意外 比如没有处理某些异常 导致出错 那么这个时候该怎么办 
对于这种情况 Akka框架给予了我们足够的控制权 在Akka框架内 父Actor可以对子Actor进行监督 监控Actor的行为是否有异常  大体上 监督策略可以分为俩种  一种是OneForOneStrategy的监督 另外一种是AllForOneStrategy

对于OneForOneStrategy的策略 父Actor只会对出问题的子Actor进行处理 比如重启或者停止 而对于AllForOneStrategy 父Actor会对出问题的子Actor以及它所有的兄弟类进行处理 很显然 对于AllForStrategy策略 它更适合对各个Actor联系紧密的场景 如果多个Actor间只要一个Actor出现故障 则宣告整个任务的失败 就比较适合使用AllForStrategy  否则 在更多的场景中 应该使用OneForOneStrategy
当然  这也是Akka中的默认策略

要指定这些监督行为 只要构造一个自定义的监督策略即可
首先定义一个父Actor 它作为所有子Actor的监督者
[Supervisor.java][7]
上述代码    定义了一个OneForOneStrategy监督策略 在这个策略中 运行Actor在遇到错误后 在1分钟内进行3次重试  如果超过这个频率 那么就会直接杀死actor

32-34行覆盖父类的supervisorStrategy()方法 设置使用自定义的监督策略
第39行用来新建一个名为restartActor的子Actor 这个子Actor就由当前的supervisor进行监督 当Supervisor接受一个Props对象时  就会更加这个Props配置生成一个restartActor

RestartActor的实现如下
[RestartActor.java][8]
定义了一些Actor的生命周期的回调接口 目的是更好的观察Actor的活动情况 在32-34行模拟了一些异常情况 第42行会抛出NullPointerException 而44行会抛出ArithmeticException

在主函数里面有一点要进行说明 就是49-53行 向Restart发送了100条RESTART信息  这会使得RestartActor抛出NullPointerException

这里粘贴一部分的输出结果
    
        preStart hashcode:1062883844
        meet NullPointerException,restart
        preReStart hashcode:1062883844
        preStart hashcode:1915158180
        postRestart hashcode:1915158180

第一行preStart表示RestartActor正在初始化 注意hashcode为1062883844
接着遇到了NullPointerException 根据自定义的策略 这将导致它重启
因此 就有了preRestart  因为preRestart在正是重启之前调用 因此HashCode还是1062883844  表示当前Actor和上一个Actor还是同一个实例 
接着就进入了preStart hashcode已经变为了1915158180 说明已经不是一个实例 系统已经为这个RestartActor生成了新的实例 原有的实例因为重启已经被回收  这说明同一个RestartActor在系统的工作始终 未必能保持同一个实例 重启完成后 调用postRestart（）方法
实际上 Actor重启后的preStart()方法 就是在postRestart()中调用的(Actor父类的postRestart（）会调用preStart()方法）

## 7.6 选择Actor
在一个ActorSystem中 可能存在大量的Actor 如何才能有效地对大量Actor进行批量管理和通信呢  Akka为我们提供了一个ActorSelection类 用来批量进行消息发送 

下面只写示意代码
```java
for(int i=0;i<WORDER_COUNT;i++){
 workers.add(system.actorOf(Props.create(MyWorker.class,i),"worker_"+i);
}
ActorSelection selection=getContext().actorSelection("/user/worker_*");
selection.tell(5,getSelf());
```
上述代码 批量生成了大量Actor 接着 我们要给这些worker发送信息 通过actorSelection()方法提供的选择通配符 可以得到代表所有满足条件的ActorSelection 最后通过这个ActorSelection实例 便可以向所有worker Actor发送消息

## 7.7. 消息收件箱（Inbox)
我们知道 所有Actor之间的通信都是通过消息来进行的 这是否意味着我们必须构建一个Actor来控制整个系统呢  不一定需要这么做  Akka框架已经为我们准备了一个叫做‘收件箱’的组件 使用收件箱 可以很方便地对Actor进行消息发送和接收 大大方便了应用程序与Actor之间的交互

[MyWorker.java][9]

在上述代码中 与这个MyWorker Actor交互的 并不是一个Actor 而是一个邮箱 邮箱的使用很简单 在上述代码中 根据ActorSystem绑定了一个Inbox 接着使用邮箱监视MyWorker 这样就能在MyWoker停止后得到一个消息通知 在45-47行 通过邮箱向MyWoker发送消息 
第48到59行 进行消息接受 如果发现MyWorker已经停止工作 则关闭整个ActorSystem

## 7.8 消息路由
Akka提供了非常灵活的消息发送机制  有时候 我们也许会使用一组Actor而不是一个Actor来提供一项服务 这一组Actor组中的所有Actor都是对等的 也就是说你可以找任何一个Actor来为你服务 在这种情况下 为了快速有效的找到合适的Actor 或者说如何更为合理调度这些消息 才可以使负载均衡地分配在这一组Actor

为了解决这个问题 Akka使用了一个路由器组件（Router)来封装消息的调度 系统提供了几种消息路由策略 比如 轮训选择Actor进行消息发送
随机消息发送 将消息发送给最为空闲的Actor 甚至在组内广播消息

[WatchActor.java][10]
在上面的代码中定义了路由器组件Router 在构造Router时  需要指定路由策略和一组被路由的Actor(Routee) 这里使用了RoundRobinRoutingLogic路由策略  也就是对所有的Routee进行轮询消息发送 在本例中 Routee是由5个MyWorker Actor构成

当有消息需要传递给这5个MyWorker时  只需要将消息投递给这个Router即可 Router就会根据给定的消息路由策略进行消息投递 当一个MyWorker停止工作时 还可以简单地从其将工作组移出 在这里 如果发现没有可用的Actor 就会直接关闭系统

主函数如下：
[RouteMain.java][11]

除了RoundRobinRoutingLogic外 还可以尝试BroadcastRoutingLogic广播策略 RandomRoutingLogic随机投递策略 ，SmallestMailBoxRoutingLogic空闲Actor优先投递策略

## 7.9 Actor的内置状态转换
在很多场景下 Actor的业务逻辑可能比较复杂 
Actor可能需要根据不同的状态对同一条消息作出不同的处理 Akka已经为我们考虑到了这一点
一个Actor内部消息处理函数可以拥有多个不同的状态 在特定的状态下 可以对同一消息进行不同的处理 状态之间也可以任意切换

下面模拟一个婴儿作为例子
[BabyActor.java][12]
在上述代码中 使用了become()方法用于切换Actor的状态 方法become()接受一个Procedure参数 Procedure在这里可以表示一种Actor的状态 同时  更重要的是它封装了在这种状态下的消息处理逻辑

在上面这个例子中 定义了俩种Prodcedure 一种是angry 另外一个是happy
在初始状态下 BabyActor没有开心也没有生气 因此angry处理函数和happy处理函数都不会工作 当BabyActor接受到消息时  会用onReceive()方法来处理这个消息

在onReceive（）函数中 当处理SLEEP消息时 就会切换当前Actor为angry 如果是play消息 则切换状态为happy

一旦完成状态切换 当后续有新的消息送达时  就不会再由onReceive()处理了 由于angry和happy都是消息处理函数 因此 后续的消息就直接交由当前状态处理 从而很好地封装了Actor的多个不同处理逻辑

由此可见 Akka为Actor提供了灵活的状态切换机制 处于不同状态的Actor可以绑定不同的消息处理函数进行消息处理 
这对构造结构化应用有着重要的帮助


## 7.10 询问模式：Actor中的Future

由于Actor之间都是异步消息通信的 当你发送一条消息给一个Actor后 你通常只能等待Actor的返回 与  与同步方法不同 在你发送异步消息后 接受消息的Actor 可能还根本来不及处理你的消息 而调用方已经返回了 
这种模式与我们之间提到的Future模式非常相像  不同之处只是在传统的异步调用中 我们进行的是函数调用  但是在这里 我们发送了一条消息

[AskMain.java][13]
上述代码给出了俩处在Actor交互中使用Future的例子

上述代码使用aks()方法给worker发送消息 方法ask()不会等待worker处理 会立即返回一个Future对象 
在第34行 使用Await方法等待worker的返回 接着在35行打印结果

在这种方法中 我们间接的将一个异步调用转为同步阻塞调用 虽然比较容易理解 但是在有些场合可能会出现性能问题 另外一种更有效的方法是使用pipe()函数

38行再次使用ask()方法询问worker  并传递数值6给worker  接着不进行等待 而是使用pipe()函数将这个future重定向到另外一个称为printer的actor pipe()函数不会阻塞程序运行 会立即返回


## 7.11 多个Actor同时修改数据：Agent

在实际开发中 很难避免 多个Actor需要访问同一个共享变量的情况

在Akka中 使用Agent的组件来实现这个功能 一个Agent提供了一个变量的异步更新 当一个Actor希望改变Agent的值时  它就会向这个Agent下发一个动作 当多个Actor同时改变Agent时  这些action将会在ExecutionContext中并发调度执行  在任意时刻 一个Agent最多只能执行一个action  对于某一个线程来说 它执行action的顺序与它的发生顺序一致 但对于不同线程来说 这些action可能会交织在一起
 Agent的修改可以使用俩个方法send()或者alter() 它们都可以向Agent发送一个修改动作 但是send（）方法没有返回值 而alter()方法会返回一个Future对象便于跟踪Agent的执行
 
 
 [CounterActor.java][14]
 上述代码定义了一个累加的Actor  在12-17行 定义了累计动作action addMapper 它的作用就是对Agent的值进行修改 这里简单的加1 
 
 CounterActor的消息处理函数onReceive()中  对全局的counterAgent进行累加操作 alter()指定了累加动作addMapper 由于我们希望在将来知道累加行为是否完成 因此在这里将返回的Future对象进行收集 完成任务后 Actor自行退出
 
 程序的主函数如下
 [AgentDemo.java][15]
 
 上述代码中 创建了10个CounterActor对象 在27-31行 使用Inbox与CounterActor进行通信  第29行将触发CounterActor进行累加操作 第35到45行将等待所有10个CounterAcotr运行结束 执行完成后 我们便已经收集了所有的future 在第47行 将所有的Future进行串行组合(使用sequence（）方法） 构造了一个整体的Future 并为它创建onCompete()回调函数 在所有的Agent操作执行完成后 onComplete()方法就会被调用 在这个例子中 我们简单地输出最终的counterAgent的值 
 
  
## 7.12  像数据库一样操作内存数据：软件事务内存
在一些函数式编程语言中 支持一种叫做软件事务内存（STM）的技术 什么是软件事务内存？ 这里的事务和数据库说的事务非常相似 具有隔离性 原子性和一致性 与数据库事务不同的是  内存事务不具备持久性（很显然内存数据不会保存下来）

在很多场合 某一项工作可能要由多个Actor协作完成 在这种协作事务中 如果一个Actor处理失败 根据事务的原子性 其他Actor所进行的操作必须要进行回滚 
下面来看是如何启动一个内存事务的：
[STMDemo.java][16]
这里新建了一个Coordinated协调者  并且将这个协调者当做消息发送给company  当company收到这个协调者消息后  自动成为这个事务的第一个成员
下面是代表公司账户的Actor
[CompanyActor.java][17]
首先判断是不是Coordinated 如果是Coordinated 则表示这是一个新事物的开始 则表示这是一个新事物的开始 接着
将调用Coordinated.coordinate()方法 将employee也加入到当前事务中 这样这个事务中就有俩个参与者了

调用了Coordinated.atomic()定义了原子执行块作为这个事务的一部分 在这个执行块中 对公司账户进行余额调整

作为转账接收方的雇员账户如下：
[EmployeeActor.java][18]
上述代码中 判断消息是否为Coordinated 如果是Coordinated 则当前Actor会自动加入Coordinated指定的事务 

在这里 俩个Actor都已经加入到同一个协调事务Coordinated中了  因此当公司账户出现异常后 雇员账户的余额就会回滚


## 7.13 一个有趣的粒子：并发粒子群的实现
粒子群算法（PSO）是一种进化算法 它与大名鼎鼎的遗传算法非常相似 可以用来解决一些优化问题
>[粒子群优化的具体解释][19] 注意wiki百科中文内容 需要翻墙阅读

### 7.13.3 粒子群算法能做什么
粒子群算法应用族多的场景就是进行最优化计算 实际上 以粒子群算法为代表的进化算法 可以说最优化方法中的通用方法 几乎一切最优化问题都可以通过这种随机搜索的模式解决 其成本低 难度小 效果好 因此颇受欢迎
下面就是有一个典型优化的问题 

假设有400万资金 要求4年用完 若存在第一年使用x万元 则可以得到效益√x万元（效益不能再使用） 当年不用的资金可存入银行 年利率为10% 尝试制定出资金的使用规划 使4年效益最大

很明显 对于此类问题 不同的方案得到结果可能会有很大的差异 
如果使用拉格朗日乘子法对方程组求解 可以得到第一年使用86.19万 第2年使用104.29万 第三年使用126.19万 第4年使用152.69万为这个问题的最优解 总效益达43.09万

由于求解过程过于复杂 需要对12个未知数和方程进行联立求解 比较难以实现 
对于这种问题就是粒子群算法的涉猎范围 当使用粒子群算法 我们可以先随机给出若干个满足提交的资金规划方案 接着 根据粒子群的演化公式 不断调整各个粒子的位置（粒子的每一个位置都代表一个方案）逐步探索更优的方案

### 7.13.4 使用Akka实现粒子群
使用Actor的模式与粒子群算法之间有天生契合度 粒子群算法由于涉及到多个甚至是极其大量的粒子参与运算 因此它隐含着并行计算的模式 其次 从直观上我们也可以知道 粒子群算法的求解精度或者说求解的质量 与参与运算的例子有着直接的关系 很显然 参与运算的粒子数量越多 得到的解自然也就够精确

如果采用传统的多线程的方式实现粒子群 一个最大的问题就是线程数量的可能是非常有限的 在当前这种应用场景中 我们希望可以有数万 甚至数十万的粒子  但是一台计算机 开启数万的线程是不可能的 就是可以 系统的效率也会非常的低  因此 使用多线程的模型无法很好地和粒子群的实现相融合

但Akka的actor不同 由于多个Actor可以复用一个线程 而Actor本身作为轻量级的并发执行单元可以有极其大量的存在 因此 我们就可以使用Actor来模拟整个粒子群计算的场景 

[Akka实现PSO代码][20]

代码本身没有什么特别需要说明的地方  
首先是俩个表示pBest和gBest的消息类型 用于多个Actor之间传递个体最优和全局最优

其次在PsoValue中 主要包括俩个信息 第一是表示投资规划的方案 即每一年分别需要投资多少钱 第二是这个投资方案的总收益 
在Fitness中的fitness()函数返回了给定投资方案的适应度 适应度也就是投资的收益 我们自然应该更倾向于选择适应度更高的投资方案

Bird就是基本粒子

MasterBird是用来管理和通知全局全优的

 

  [1]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/Helloworld/Greeter.java
  [2]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/Helloworld
  [3]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/Helloworld/HelloMainSimple.java
  [4]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/liftcycle/MyWorker.java
  [5]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/liftcycle/WatchActor.java
  [6]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/liftcycle/DeadMain.java
  [7]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/supervisor/Supervisor.java
  [8]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/supervisor/RestartActor.java
  [9]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/inbox/MyWorker.java
  [10]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/route/WatchActor.java
  [11]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/route/RouteMain.java
  [12]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/Baby/BabyActor.java
  [13]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/future/AskMain.java
  [14]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/Agent/CounterActor.java
  [15]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/Agent/AgentDemo.java
  [16]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/STM/STMDemo.java
  [17]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/STM/CompanyActor.java
  [18]: https://github.com/SanShanYouJiu/CodeCollection/blob/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/STM/EmployeeActor.java
  [19]: https://zh.wikipedia.org/wiki/%E7%B2%92%E5%AD%90%E7%BE%A4%E4%BC%98%E5%8C%96
  [20]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7/PSO