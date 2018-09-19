## 集合框架

### 1. 定义及用途

* 集合是Java中保存大量对象的引用的一种方式，同样可以用来保存大量对象的引用的还有数组
* 集合能够持有对象
* Java中的集合框架的实现是Java容器类相关类库，容器类有两种划分
  * Collection
    * 一个包含独立元素的序列
    * 序列的每一个位置都包含一个独立的元素
    * 各个元素之间是无序的，是可重复的，是可以为null的。
  * Map
    * 一个每一组数据都是键值对的容器
    * 并能够通过其键来查找其对应值；
    * 因为允许通过键查找，所以键应该具有唯一性，若重复则查找结果就不确定了

### 2. 相对同类产品优势

* 暂无

### 3. 核心知识点

#### 3.1. Collection接口

* Collection接口继承了Iterable接口，该接口主要有一个iterator方法返回一个Iterator迭代器对象，该迭代器只支持单向移动的访问数据
* 继承了Collection接口的接口有：List、Queue、Set

##### 3.1.1 List

* List是有序的Collection
* 会默认按元素的添加顺序给每个元素设置一个索引，增删改查均可基于索引操作
* 继承list接口的有：AbstractList 及LinkedList，常用的ArrayList继承自AbstractList。
* ArrayList
  * 是基于数组实现的，其底层实现为一个长度动态增长的Object[]数组，
  * 因此其具有访问快，增删慢的特点；
* LinkedList
  * 除了List接口外还实现了Deque接口
* ListIterator迭代器
  * List接口提供了特殊的迭代器ListIterator，
  * ListIterator支持双向移动访问元素，支持插入和替换元素，还能够从指定位置开始获取ListIterator

---

##### 3.1.2. Queue

* 不允许随机访问其中间的元素 ，只能从队首访问的Collection，一般来说队列都应该是FIFO的。
* 继承关系：
  * Deque接口继承了Queue
  * AbstractQueue实现了Queue接口
  * 常用的PriorityQueue继承自AbstractQueue
* Deque接口
  * 代表一个"双端队列"，
  * 双端队列可以同时从两端来添加、删除元素，
  * Deque的实现类既可以当成队列使用、也可以当成栈使用
* PriorityQueue
  * 不是一个比较标准的队列实现，
  * PriorityQueue保存队列元素的顺序并不是按照加入队列的顺序，而是按照队列元素的某种功能权重进行重新排序

---

##### 3.1.3 Set

* Set是元素不重复的Collection。
* Set继承关系：
  * 实现了Set接口的有HashSet LinkedHashSet，
  * SortedSet接口继承了Set接口，
  * NavigableSet接口继承了SortedSet接口，
  * 常用的TreeSet实现了NavigableSet接口

---

#### 3.2. Map接口

* 间接基于Map接口实现的类有很多,常用的有：TreeMap,HashMap，LinkedHashMap
  * TreeMap
    * 存储key-value对(节点)时，需要根据key对节点进行排序，
    * 可以保证所有的key-value对处于有序状态
  * HashMap
    * 用于快速访问
  * LinkedHashMap
    * 能够保持元素插入的顺序
    * 提供快速访问的能力

### 4. 常见面试题

* Java集合框架是什么？说出一些集合框架的长处？

* * 集合：代表了一个自然数据项，比如一组手牌(牌的集合)、邮件文件夹(邮件的集合

  * Java用来表示和操作集合的统一的架构就是java集合框架
  * 长处/优点：
    * 减少编程的工作量
    * 提高程序的速度与质量
    * 允许无关APIs的互操作
    * 促进软件的复用

* 你如何自己实现List，Set和Map?

* HashMap和TreeMap在性能上有什么样的差别呢？你比较倾向于使用哪一个?

  * Java里的TreeMap用一个红黑树来保证key/value的排序。红黑树是平衡二叉树。保证二叉树的平衡性，使得插入，删除和查找都比较快，时间复杂度都是O(log n)。不过它没有HashMap快，HashMap的时间复杂度是O(1)，但是TreeMap的优点在于它里面键值是排过序的，这样就提供了一些其他的很有用的功能。
  * 使用无序的HashSet和HashMap，还是使用有序的TreeSet和TreeMap，主要取决于你的实际使用场景
    * 如果插入和更新都比较频繁的话，那么保证元素的有序可以提高快速和频繁查找的性能。
    * 如果对于排序操作（例如产生一个报表）的要求不是很频繁的话，那么把数据以无序的方式存储，然后在需要排序的时候用Collections.sort(…)来进行排序，会比用有序的方式来存储可能会更加高效

* 如何权衡是用无序的数组还是有序的数组呢？

  * 有序数组最大的优点在于n比较大的时候，搜索元素所花的时间O(log n)比无序素组所需要的时间O(n)要少很多。
  * 有序数组的缺点在于插入的时间开销比较大（一般是O(n))，因为所有比插入元素大的值都要往后移动。而无序数组的插入时间开销是常量时间，也就是说，插入的速度和元素的数量无关

* 怎么实现一个不可变集合？

  * Collections.unmodifiableSet()

* 为何Collection不从Cloneable和Serializable接口继承？

  * 由于Collection是一个抽象表现，把克隆或序列化放到集合的全部实现中也是没有意义的。
  * 当与详细实现打交道的时候，克隆或序列化的语义和含义才发挥作用。所以，详细实现应该决定怎样对它进行克隆或序列化，或它能否够被克隆或序列化

* 为何Map接口不继承Collection接口？

  * 虽然Map接口和它的实现也是集合框架的一部分。但Map不是集合。集合也不是Map

* Enumeration和Iterator接口的差别？

  * Enumeration的速度是Iterator的两倍，也使用更少的内存。
  * Iterator更加安全，由于当一个集合正在被遍历的时候。它会阻止其他线程去改动集合

* 为何迭代器没有一个方法能够直接获取下一个元素。而不须要移动游标？

  * 它能够在当前Iterator的顶层实现。
  * 用得非常少，假设将它加到接口中，每一个继承都要去实现它。这没有意义。 

* 遍历一个List有哪些不同的方式？

  * 使用for-each循环
  * 使用迭代器
    * 使用迭代器更加线程安全。由于它能够确保，在当前遍历的集合元素被更改的时候。它会抛出ConcurrentModificationException

* 通过迭代器fail-fast属性，你明确了什么？ 
  * 每次我们尝试获取下一个元素的时候，Iterator fail-fast属性检查当前集合结构里的不论什么改动。假设发现不论什么改动。它抛出ConcurrentModificationException
  * Collection中全部Iterator的实现都是按fail-fast来设计的（ConcurrentHashMap和CopyOnWriteArrayList这类并发集合类除外）。
* fail-fast与fail-safe有什么差别？
  * Iterator的fail-fast属性与当前的集合共同起作用，因此它不会受到集合中不论什么改动的影响。 
  * Java.util包中的全部集合类都被设计为fail-fast的。而java.util.concurrent中的集合类都为fail-safe的。 
  * Fail-fast迭代器抛出ConcurrentModificationException，而fail-safe迭代器从不抛出ConcurrentModificationException。 
* 在Java中，HashMap是怎样工作的？
  * HashMap在静态内部类Node impliments Map.Entry 实现中存储key-value对。 
  * HashMap使用哈希算法。在put和get方法中。它使用hashCode()和equals()方法。当我们通过传递key-value对调用put方法的时候。HashMap使用Key hashCode()和哈希算法来找出存储key-value对的索引。 
  * Entry存储在LinkedList中，所以假设存在entry。它使用equals()方法来检查传递的key是否已经存在。假设存在，它会覆盖value。假设不存在。它会创建一个新的entry然后保存,
  * 当我们通过传递key调用get方法时，它再次使用hashCode()来找到数组中的索引，然后使用equals()方法找出正确的Entry，然后返回它的值。
  * HashMap默认的初始容量是16，负荷系数是0.75，阀值是为负荷系数乘以容量
  * 尝试加入一个entry，假设map的大小比阀值大的时候，HashMap会对map的内容进行又一次哈希。且使用更大的容量。容量总是2的幂
* ArrayList和LinkedList有何差别？
  * ArrayList和LinkedList两者都实现了List接口
  * ArrayList是由Array所支持的基于一个索引的数据结构，所以它提供对元素的随机訪问。复杂度为O(1)，但LinkedList存储一系列的节点数据。每一个节点都与前一个和下一个节点相连接。所以。虽然有使用索引获取元素的方法，内部实现是从起始点開始遍历，遍历到索引的节点然后返回元素。时间复杂度为O(n)。比ArrayList要慢。
  * 与ArrayList相比，在LinkedList中插入、加入和删除一个元素会更快。由于在一个元素被插入到中间的时候，不会涉及改变数组的大小，或更新索引。
  * LinkedList比ArrayList消耗很多其他的内存，由于LinkedList中的每一个节点存储了前后节点的引用。
* 并发集合类是什么？
  * Java1.5并发包（java.util.concurrent）包括线程安全集合类，同意在迭代时改动集合。
* BlockingQueue是什么？
  * Java.util.concurrent.BlockingQueue是一个队列，在进行检索或移除一个元素的时候，它会等待队列变为非空；当在加入一个元素时，它会等待队列中的可用空间。 
  * BlockingQueue接口是Java集合框架的一部分，主要用于实现生产者-消费者模式。我们不须要操心等待生产者有可用的空间。或消费者有可用的对象。由于它都在BlockingQueue的实现类中被处理了。 
  * Java提供了集中BlockingQueue的实现，比方ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue,、SynchronousQueue等。 
* Comparable和Comparator接口是什么？
  * Comparable和Comparator接口被用来对对象集合或者数组进行排序。
  * Comparable接口被用来提供对象的自然排序。我们能够使用它来提供基于单个逻辑的排序。 
    * 实现了Comparable接口的类可以和自己比较
    * 一个实现了Comparable接口的类如何比较，则依赖compareTo方法的实现
  * Comparator接口被用来提供不同的排序算法，我们能够选择须要使用的Comparator来对给定的对象集合进行排序。 
    * 一个对象不支持自己和自己比较（没有实现Comparable接口），但是又想对两个对象进行比较
    * 一个对象实现了Comparable接口，但是开发者认为compareTo方法中的比较方式并不是自己想要的那种比较方式
    * 实现Comparator接口的实现类只能是两个相同的对象

### 5. 综述

* 集合是Java编程中最常用的库类
* 集合能够持有大量对象，提供了各种类型的接口来方便持有对象，可根据实际需要选择合适的接口来实现

### 6. 参考资料

* [面试准备2-介绍一下集合框架](https://www.jianshu.com/p/69aedb5428cb)

* [Java集合框架面试问题集锦](http://www.importnew.com/871.html)

* [java面试题------40个Java集合面试问题和答案](https://www.cnblogs.com/tlnshuju/p/7324703.html)

* [Comparable和Comparator的区别](http://www.cnblogs.com/xrq730/p/4850140.html)