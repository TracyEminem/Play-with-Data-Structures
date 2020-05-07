# 《玩转数据结构》 笔记

作者： bruski

时间： 2020/03/23

## 简单时间复杂度分析

大O描述的是算法的运行时间与输入数据之间的关系。

- O(1)
- O(n): 线性关系
- O(n^2)
- ...

为什么要用大O，叫做O(n)? 

大O描述的是渐进时间复杂度，即 n 趋近于无穷的情况，所以可以忽略低阶项、常数。

```
T = 2*n + 1   O(n)
T = 2000 * n + 10000   O(n)
T = 1 * n * n + 0  O(n^2)
T = 1 * n * n + 300 * n + 100  O(n^2)
```

## 02 数组

数组主要用于“索引有语义”的情况，如第几个xx。

基于Java的数组，封装Array类，增加 增删改查 功能

类内部自行维护数组与状态

- 实现头部、尾部插入，实现任意位置插入
- 支持指定位置删除、删除头部、删除尾部
- 判断是否存在元素
- 查找元素下标
- 泛型支持
    - 外部实例化, ```Array<T> arr = new Array<>();``
    - 内部实现 ```E[] data = (E[]) new Object[capacity]```
- 动态数组：动态开辟空间
    - 创建更大空间的数组，内部data改变引用
    - 插入元素时，如果数组满了，扩容为原来的2倍
    - 删除元素时，如果元素内存在的数量等于当前容量的一半，就缩容回原来的一半。
    
### 数组的方法时间复杂度分析

#### 添加操作

时间复杂度： O(n)

```
addLast(e) -> O(1)                |
addFirst(e) -> O(n)               | 最坏情况：O(n)
add(index, e) -> O(n/2) = O(n)    |
```

#### 删除操作

时间复杂度： O(n)

```
removeLast(e) -> O(1)                |
removeFirst(e) -> O(n)               | 最坏情况：O(n)
remove(index, e) -> O(n/2) = O(n)    |
```

#### 修改操作

平均时间复杂度： O(n) 

已知索引(最好)： O(1)
未知索引(最坏)： O(n)

```
set(index, e) 
```

#### 查询操作

平均时间复杂度： O(n) 

已知索引(最好)： O(1)
未知索引(最坏)： O(n)

```
get(index) -> O(1)
contains(index) -> O(n)
find(index) -> O(n)
```

#### resize 时间复杂度分析

```
addLast(e) -> O(1)                | 最好情况：O(1) 最坏情况：O(n)  resize: O(n)
addFirst(e) -> O(n)               | O(n)
add(index, e) -> O(n/2) = O(n)    | O(n)
```

##### 均摊复杂度

对于addLast方法，只有超过了数组容量才会触发扩容操作

假设capacity = n, n + 1 次 addLast, 触发resize, 总共进行 2n + 1 次基本操作

用**均摊复杂度（amortized time complexity）**的概念 -> 平均每次 addLast 操作，进行 2次基本操作

则 **addLast 的均摊复杂度为 O(1)**!

##### 复杂度震荡

同时看 addLast 与 resizeLast ，在满的时候，每次操作都会触发 resize, 复杂度突增为 O(n);

因为删除的缩容resize过于着急(Eager)

解决方案: 采用更懒惰的方案Lazy： 当 size == capacity / 4 时，才将capacity减半； 同时要防止capacity缩为 0

## 03 栈与队列

栈: 数组的子集，一种后进先出 Last In First Out (LIFO)的结构，只能从一端添加，从同一端取出，这个端称为 `栈顶`。

### 栈的应用

撤销操作Undo: 原理就是用栈存储输入，撤销时最近的输入出栈。

程序调用的系统栈：用栈记录系统调用的中断位置，从而能完成函数嵌套调用。

### 复杂度分析

ArrayStack<E> 基于动态数组的栈

- void push(E) -> O(1) 均摊
- E pop() ->  O(1) 均摊
- E peek() ->  O(1)
- int getSize() ->  O(1)
- boolean isEmpty() ->  O(1)

### 算法题：括号匹配

需要判断一组符号是否正确匹配且闭合 如

```
() -> true
{(]} -> false
```

思路： 使用栈结构，遍历输入的字符串，逐一取字符，如果是左括号`([{` 入栈，直到遇到右括号 `)]}` 出栈比对：

- 如果遇到左括号而栈里为空，说明不是左括号打头，不构成包裹，返回false；
- 如果括号类型不匹配，返回false；
- 否则继续遍历；

如果遍历结束：

- 栈里还不为空，说明没匹配完，返回false；
- 否则，说明匹配成功，返回true

### 3-5 队列

队列 FIFO 先进先出

接口
```
public interface Queue<E> {
    int getSize();

    boolean isEmpty();

    void enqueue(E e);

    E dequeue();

    E getFront();
}
```

#### 数组队列

```
public public class ArrayQueue<E> implements Queue<E> {
    int getSize(); // 时间复杂度: O(1)

    boolean isEmpty(); // 时间复杂度: O(1)

    void enqueue(E e); // 时间复杂度 O(1) 均摊

    E dequeue(); // 时间复杂度： O(n)

    E getFront(); // 时间复杂度 O(1)
}
```

#### 循环队列

基于数组，维护 front 和 tail 指针

front == tail 队列为空
(tail + 1) % size == front 队列满

因为队列满和队列空条件要区别，所以会浪费一个空间

```
public class LoopQueue<E> implements Queue<E> {
    int getSize(); // 时间复杂度: O(1)

    boolean isEmpty(); // 时间复杂度: O(1)

    void enqueue(E e); // 时间复杂度 O(1) 均摊

    E dequeue(); // 时间复杂度： O(1)均摊 // 根据tail指针直接取

    E getFront(); // 时间复杂度 O(1)
}
```

#### 数组队列与循环队列

100000次出队操作, 循环队列 26s 数组队列 0.1s

## 04 链表

动态数据结构，不支持随机访问，不适合索引有语义的情况

```
class Node {
    E e;
    Node next;
}
```

- 从链表头部添加数据
- 在链表中间添加元素: 关键找到要添加节点的前一个节点，顺序很重要

### 第一种实现

只设立尾结点，在插入头的时候，需要单独处理

### 设立虚拟头结点

头结点不存储数据，用于指示位置

### 链表中的删除

找到待删除节点的前一个节点， prev

将prev的next指向要删除节点的Next：

```
target = prev.next;
prev.next = target.next;
target.next = null;
```

> 补充删除链表节点的办法
> 在要删除的节点处，拷贝后一个节点的值，然后链接到下下个节点处
> ```
> target.val = target.next.val;
> target.next = target.next.next;
> ```

### 时间复杂度

添加操作 O(n)

- addLast(e) O(n)
- addFirst(e) O(1)
- add(index, e) O(n/2) = O(n)

删除操作 O(n)

- removeLast(e) O(n)
- removeFirst(e) O(1)
- remove(index, e) O(n/2) = O(n)

修改操作 O(n)

- set(index, e);

查找操作 O(n)

- get(index)  O(n)
- contains(e)  O(n)

总结，只对链表头操作，就可以达到 O(1) 复杂度；且动态不占内存

### 数组栈 VS 链表栈

时间复杂度相同，都是O(1)，但链表栈的new操作耗时

### 数组队列 VS 链表队列

数组队列 O(n^2) 链表队列 O(1)

## 05 递归

本质上，将原来的问题，转化为更小的同一问题

### 递归写法

1. 求解最基本问题
2. 将原问题拆解为更小的问题
3. 返回递归结果

### 链表与递归

#### 例子：删除所有值为特定值的节点

将链表看成 头结点 + 剩余部分

如果头结点为要删除的值，则返回处理后的剩余部分
否则 返回 头结点 + 处理后的剩余部分

近乎和链表的所有操作，都可以使用递归的形式完成

对链表的增删盖茶进行递归实现

### 5-5 递归运行的机制：微观解读

程序调用的系统栈

递归调用有代价：函数调用+系统栈空间

### 双链表

每个节点，都有两个指针

```
class Node {
    E e;
    Node next, prev;
}
```

### 循环链表

增加虚拟头结点的双向链表

### 数组链表

```
class Node {
    E e;
    int next;
}
```

## 06 二分搜索树

属于二叉树的一种，每个节点有左儿子与右儿子，其中每个节点的左子树都小于当前节点的值，右子树都大于当前节点的值。

不包含重复元素。

### 插入元素

递归操作：对当前根节点进行插入

如果当前传入节点为null，将传入的值创建新节点，返回。
否则 如果要插入的值小于当前节点值，递归左子树，返回递归结果
如果要插入的值大于当前节点值，递归右子树，返回递归结果
如果相等，什么都不做。

### 遍历操作

就是把所有节点都访问一遍。

递归操作。

### 前序遍历（深度优先）

先访问节点，再访问左子树、右子树。

```
function traverse(node):
    if (node == null)
        return;

    print(node);
    traverse(node.left);
    traverse(node.right);
```

#### 非递归实现

利用栈结构，记录要按前序访问的节点，通过后进先出，实现左右子树的前序遍历。

### 中序遍历（深度优先）

先访问左子树，再访问节点、右子树。

**中序遍历的结果：顺序排序**

```
function traverse(node):
    if (node == null)
        return;

    traverse(node.left);
    print(node);
    traverse(node.right);
```

### 后序遍历（深度优先）

先访问左子树，再右子树、访问节点。

**应用：后序遍历为二分搜索数释放内存**

```
function traverse(node):
    if (node == null)
        return;

    traverse(node.left);
    print(node);
    traverse(node.right);
```


### 层序遍历（广度优先）

利用队列，在每个节点遍历时，将左右子节点入队，实现层级的遍历

### 删除二叉搜索数最大最小元素

最小元素：一直往左子树走，最左的节点

最大元素：一直往右子树走，最右的节点

### 删除任意值所在的节点

判断删除的值比当前节点大、小或相等，

- 小： 递归左子树
- 大： 递归右子树

找到要删除的节点

- 如果当前节点左子树为空：删除后将当前节点右子树返回
- 如果当前节点右子树为空：删除后将当前节点左子树返回
- 如果当前节点左右子树不为空：
    - 有两种方式：找到当前点的前驱点（即左子树的最右节点）或后继点（即右子树的最左节点），都能保证二叉搜索数的性质
    - 后继节点法：
        - 找到当前节点的后继节点（即右子树的最小值节点），在右子树中删除该后继节点，返回该后继节点
        - 将后继节点的左指针指向将当前节点的左子树
        - 将后继节点的右指针指向将当前节点的删除了后继节点的右子树
    - 返回后继节点
    
### 二分搜索树的顺序性

floot / ceil

rank 排名

select

维护size的二分搜索树

维护depth的二分搜索树

支持重复元素的二分搜索树 维护count或插入左子树

## 07 集合 Set

二分搜索树不存放重复元素，是实现集合的好结构。

典型应用：客户统计、词汇量统计

### 二分搜索树实现

### 链表实现

### 复杂度分析

二分搜索树可能退化成链表

#### LinkedListSet

增 add - O(n)

查 contains - O(n)

删 remove - O(n)

#### BSTSet

平均 O(logn)

最差 O(n) (退化成链表,此时h=n) 

设树的高度为 h

增 add - O(h)

查 contains - O(h)

删 remove - O(h)

##### 高度与节点数关系

满二叉树： `第h-1层： n = 2 ^ (h - 1)`

h层一共： `2^0 + 2^1 + ... + 2^(h-1) = 2^h - 1 个`

`h = log2(n+1) -> O(log(n))`

log(n) 与 n： n越大, 差距越大

#### 有序集合和无序集合

有序集合：基于搜索树的实现

无序集合：基于哈希表的实现

## 07 映射 Map

- 链表Map
- 二分搜索树Map

### 时间复杂度分析

|操作|LinkedListMap|BSTMap|
|:--:|:--:|:--:|
|增add|O(n)|O(h)|
|删remove|O(n)|O(h)|
|改set|O(n)|O(h)|
|查get|O(n)|O(h)|
|查contains|O(n)|O(h)|
|平均|O(logn)|O(n)|

### 有序映射 VS 无序映射

### 多重映射
