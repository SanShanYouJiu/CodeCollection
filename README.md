
# C语言数据结构与Java版本
> #### 看过一些大话数据结构对应的翻译版本 但看到的不是作者在原书的解题思路上进行翻译的 而是另外一些的解题思路因此增加了很多不一样的东西
>#### 于是我在一边学习这本书的同时一边翻译了java版本   保证大体主干没有改变  只有为了兼容的一些Java部分特殊加的内容 并且加上了注释 

- 待更新状态
- 第三章线性表部分已完成翻译 
- 第四章栈与队列部分已完成翻译
- 第五章暴力破解算法并没有进行翻译（很简单 没有必要）   主要的KMP算法完成翻译
- 第六章树部分完成翻译 树的表示法没有进行翻译（同暴力破解算法）
- 第7章 图部分已完成翻译 
- 第8章 查找部分完成翻译  
 

# 设计模式与设计原则
> #### 设计模式与设计原则的代码都是在看设计模式之禅的时候写下的 
> #### 主要是来给自己增加对设计模式的熟练感的 加上本人也有记笔记的习惯 也算对本书来说自己的一个个人总结吧 
> 

----------

>  总计436个类与34份文档 在测试开闭原则时使用了jmock测试
>  maven地址如下

	  <dependency>
            <groupId>org.jmock</groupId>
            <artifactId>jmock-junit4</artifactId>
            <version>2.5.1</version>
        </dependency>

- 6个设计原则 
 1. [单一职责原则][1]
 2. [里氏替换原则][2]
 3. [依赖倒置原则][3]
 4. [接口隔离原则][4]
 5. [迪米特原则][5]
 6. [开闭原则][6]
 
- 23个设计模式
 - 创建型模式 
 1. [单例模式][7]
 2. [工厂方法模式][8]
 3. [抽象工厂模式][9]
 4. [原型模式][10]
 5. [建造者模式][11]
 - 结构型模式
 1. [装饰模式][23]
 2. [适配器模式][24]
 3. [组合模式][25]
 4. [门面模式][26]
 5. [享元模式][27]
 6. [桥梁模式][28]
 7. [代理模式][29]
 - 行为型模式
 1.  [策略模式][12]
 2. [状态模式][13]
 3. [解释器模式][14]
 4. [责任链模式][15]
 5. [中介者模式][16]
 6. [命令模式][17]
 7. [观察者模式][18]
 8. [访问者模式][19]
 9. [备忘录模式][20]
 10. [迭代器模式][21]
 11. [模板方法模式][22]


----------


- 后加了5种新的设计模式
 1. [规格模式][30]
 2. [对象池模式][31]
 3. [雇工模式][32]
 4. [黑板模式][33]
 5. [空对象模式][34]
 
 
# Java高并发程序设计
 
>这的确是本不错的书  

- [第二章 Java并行程序基础][35]
- [第三章 JDK并法包][36]
- [第四章 锁的优化及注意事项][37]
- [第五章 并行模式与算法][38]
- [第六章 Java8与并发][39]
- [第七章 使用Akka构建高并发程序][40]


  [1]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/PrincipleDesign/SRP
  [2]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/PrincipleDesign/LSP
  [3]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/PrincipleDesign/DIP
  [4]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/PrincipleDesign/ISP
  [5]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/PrincipleDesign/LoD
  [6]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/PrincipleDesign/OCP
  [7]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/SingletonPattern
  [8]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Factory
  [9]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/AbstractFactory
  [10]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Prototype
  [11]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/builder
  [12]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Strategy
  [13]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/State
  [14]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Interpreter
  [15]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/ResponsibilityChain
  [16]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Mediator
  [17]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Command
  [18]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Observer
  [19]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Visitor
  [20]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Memo
  [21]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Iterator
  [22]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/TemplateModel
  [23]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Decoratecom/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/ResponsibilityChain
  [24]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Adapter
  [25]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Composite
  [26]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Facade
  [27]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Flyweight
  [28]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Bridge
  [29]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/DesignModel/Proxy
  [30]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/NewDesign/Specification
  [31]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/NewDesign/ObjectPool
  [32]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/NewDesign/Employees
  [33]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/NewDesign/BlackBoard
  [34]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/%E8%AE%BE%E8%AE%A1%E6%A8%A1%E5%BC%8F%E4%B8%8E%E8%AE%BE%E8%AE%A1%E5%8E%9F%E5%88%99/NewDesign/NullObject
  [35]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter2
  [36]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter3
  [37]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter4
  [38]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter5
  [39]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter6
  [40]: https://github.com/SanShanYouJiu/CodeCollection/tree/master/Java%E9%AB%98%E5%B9%B6%E5%8F%91%E7%A8%8B%E5%BA%8F%E8%AE%BE%E8%AE%A1/javaHighConcurrentDesign/chapter7