# Algorithm Learning
**算法学习 代码记录**  
非LC代码均带有对数器验证正确性

### [Sort](./sort): 排序方法
- [冒泡排序](./sort/BubbleSort.java)
- [插入排序](./sort/InsertionSort.java)
- [选择排序](./sort/SelectionSort.java)
- [快速排序](./sort/QuickSort.java)： 荷兰国旗问题， 随机快速排序；递归 + 非递归版本
- [归并排序](./sort/MergeSort.java)：递归 + 非递归
- [堆排序](./sort/HeapSort.java)

### [链表排序](listSort/): https://leetcode-cn.com/problems/insertion-sort-list/solution/dui-lian-biao-jin-xing-cha-ru-pai-xu-by-leetcode-s/
- [链表的插入排序](listSort/InsertionSortList.java)： 
  - 空间O（N），额外使用数组 **【出错，arrayList拼接回链表后，忘记对最后一个节点的next指针，设置为空，造成环】**
  - O(1)空间复杂度， 在链表上进行节点的插入，设置多个变量。具体看代码和相关题解（数组的插入为O（N）因为要向后移，链表的插入为O（1），但是需要遍历为O（N），所以总复杂度不变）。 dummyHead（防止换头）， lastSorted，curr，prev
### [Heap](./heap): 堆
- [堆的实现](./heap/HeapImplementation.java)：弹入，弹出，上浮，下沉
- [加强堆实现](heap/HeapGreater.java) 允许对堆内元素的值进行修改，加强堆会对修改后的元素进行调整，维持其大/小根堆的组织。
### [binarySearch](binarySearch): 二分法相关应用
- [二分法检测元素在数组的位置](binarySearch/BSExist.java): 为配合对数器检查，实现查找【等于区间最左元素】。
- [二分法 获取 >=N 的最左元素](binarySearch/BSNearLeft.java)
- [二分法 获取 <=N 的最右元素](binarySearch/BSNearRight.java)
- [二分法 无序数组，相邻元素不相等 求 任意【局部最小】](binarySearch/BSAwesome.java)

### 异或专题 -> 奇偶类问题： 异或即是无进位相加
- [Swap](xor/Swap.java)： 不创建额外变量进行两个数字的交换。
- [BitCounts](xor/BitCounts.java): 统计一个数字二进制中，1的个数. 异或方法： 【a & (-a) = 最右侧1】 + 再异或最右侧1，即可消除掉。（也可取反与） 
- [一个数奇数次，其他数字偶数次](xor/OddTimesEvenTimes1.java)：a^a=0; a^0=a
- [两个数字奇数次，其他数字偶数次](xor/OddTimesEvenTimes2.java)：全部异或后，结果为两数二进制中不同的位置。使用**【a & -a】**得到最右侧的1.从而区分。
- [奇偶进阶KM问题：arr中，只有一种数出现了K次，其他数都出现了M次](xor/KM.java): KM问题，M > 1,K < M。**O（1）额外空间复杂度。** 包含两个版本： 完整版（包含K次数字不存在情况）及精简版（数组元素严格遵循KM的出现次数）。

### [MergeSort扩展问题](mergeSortRelatedQuestions)：都可以扩展为 数组中 每个元素 左侧范围/右侧范围 大/小 的相关问题， 最终整合整个数组结果 的问题（但是注意，最终都要转换为类似，对于左组的**每一个元素**，右组所满足要求元素的 **个数**）。思路：当问题问 关于数组中每一个数，右边怎么怎么样的时候， 就可以往mergeSort上靠！ 本质：mergeSort把比较信息变成了有序的东西，这个有序的东西可以帮助很快的求很多事情。
- [小和 问题](mergeSortRelatedQuestions/SmallSum.java)：**关注 【右侧范围】 有多少个数 比当前元素 大** ：给定一个数组，对数组中每个元素arr[i]，计算其左侧元素arr[j]满足(j < i && arr[j] < arr[i])的所有元素累加和，最终计算出整体数组对应的总累加和。每一个数左边比它小的数字之和 -》每一个数字右边有**多少个**比它大的数字。
  从左往右进行比较，左组元素拷贝的时候计算右组个数。注意：左右组相等时，先拷贝右边
- [逆序对问题](mergeSortRelatedQuestions/ReversePair.java)：**关注 【右侧范围】 有多少个数 比当前元素 小** https://leetcode-cn.com/problems/shu-zu-zhong-de-ni-xu-dui-lcof/ 。从右往左进行拷贝，依然是 左组元素拷贝的时候，计算右组个数。注意：左右组相等时，先拷贝右边。
- 【小和问题】 + 【逆序对问题】 -》 mergeSort过程中，只计算每一个元素作为**左组姿态**（123x|456789, 则对于x，456789将会是 x作为左组姿态下 遇到的右组总和），求右组的数量情况时，最终结果即为 在整个数组范围上，该元素 右侧所有元素的情况。**换句话说，该元素在 左组姿态的情况下， “456789”右侧【全部数字】 都会在 过程中的 【右组】中【仅有一次】的出现。**  
  **下面的两个问题**，因为是【非元素本身】之间进行比较了，所以 将 【计算过程】 与 【Merge过程】 分开即可。
- [大于两倍问题 - BiggerThanRightTwice](mergeSortRelatedQuestions/BiggerThanRightTwice.java): Q： 求每一个数num 右边有多少个数*2之后 <num。**类似 逆序对 问题（一个元素 右侧范围中 小于当前元素个数），但是coding方式变了：** **小和问题和逆序对问题由于是直接比较，所以可以在merge左右组的时候 *同时* 进行计算**，而"大于右侧两倍"问题，**则只需要将 计算部分（for循环遍历左组，对于每一个【i】使用while循环找到满足条件的右组边界） 与 merge左右组部分 分开 即可**。  
  PS：涉及一个技巧：双指针（指向左右组的开头） 不回退的技巧（因为左组有序，右组有序，所以必然不回退）， 时间复杂度O（N）。**说明一点：**不回退技巧**来自于 **单调性**（【单调性】往往伴随着【不回退】），因为左组小到大单调，右组也有序，所以比较左右组时可以不回退。
- [区间和的个数](mergeSortRelatedQuestions/CountOfRangeSum.java)： https://leetcode.com/problems/count-of-range-sum/ 。 **转换为 必须以每个元素为结尾时，满足区间和的数量。** 转换为 左侧范围中，满足转换后区间和的元素数量。 =》 前缀和，单调性+滑动窗口+指针不回退技巧，关键点变成：求每一个元素**左侧范围**中满足 转换后区间和的数量。（mergeSort就是会让每个元素作为右组姿态下，在merge左组的过程中经历其左侧范围的每个元素仅有一次） 。  我要左侧的数字，那我就 遍历右组元素的时候，算左组个数。
 PS: 转换为 以每个元素为结尾，就变成了 求 左侧 的问题。 区间和 == 前缀和。  
  **【注意事项】** [注意前缀和数组值溢出问题](https://leetcode-cn.com/problems/count-of-range-sum/solution/qu-jian-he-wen-ti-qian-zhui-he-gui-bing-1u7x9/) !!! 题目给定 单个元素范围 【-2^31 <= nums[i] <= 2^31 - 1】，所以正常方式求 【前缀和】时 要使用【Long类型】进行存储。
### linkedList:链表相关问题（笔试时可以优先使用HashSet或HashMap来实现，面试时则使用额外空间复杂度为O（1）的方法。）
- [反转链表](linkedList/ReverseLinkedList.java)： 递归 + 非递归
- [删除给定值的节点](linkedList/DeleteGivenValue.java)：可能位于开头（分别考虑），可能位于中间。
- [链表 中位数](linkedList/LinkedListMid.java)： 快慢指针： 上中+中， 下中+中， 上中前+中前， 下中前+中前
- [是否是回文链表](linkedList/IsPalindromeList.java)： 判断一个链表value是否是回文的。回文代表 正着读反着读结果相同。O（N）栈，逆序。面试需 O（1）空间复杂度：取中位节点，后半部分逆序，头尾指针向中移动，沿途判断是否相等。
- [将链表中的数字 分为 【小中大】 三部分](linkedList/SmallEqualBigger.java)： O(1)额外空间复杂度，分成小中大三个list，总共六个首尾指针，最后进行整合
- [CopyListWithRandom,带有random指针的链表复制](linkedList/CopyListWithRandom.java)： O（1）额外空间复杂度：1 1‘ 2 2’ 3 3‘。https://leetcode.com/problems/copy-list-with-random-pointer/
- [给出任意两个链表头节点，返回相交节点](linkedList/FindFirstIntersectNode.java)：此类问题笔试方法为HashSet，空间复杂度为O(N)。下列为面试方法，空间复杂度优化为**O(1)**。该大问题下包含以下四个小问题。
  - 【0】获得链表第一个入环节点（无则返回null）： 快慢指针 O(1)额外空间复杂度，快慢指针一起走第一次相遇时停止。slow不动，fast从head重走，此时slow,fast全部步长为1，第一个相遇节点即为相交节点。
  - 【1】两个无环链表相交节点： O(1)额外空间复杂度，通过两个链表最后一个节点判断是否存在相交。有的话，先走长链表多余部分，然后长短链表一起走，第一个相同节点即是相交节点
  - 【2】两个有环链表相交节点 ： 四种情况考虑：
    - （1）相交节点在非环部分 
    - （2）相交节点在同一个入环节点（12可合并） 
    - （3）两个链表的入环节点都在环上，但不同位置 
    - （4）无相交节点 
  - 【3】一个有环链表与一个无环链表不可能相交 
- [ReverseNodesInKGroup - K个一组翻转链表](linkedList/ReverseNodesInKGroup.java)： https://leetcode.222com/problems/reverse-nodes-in-k-group/ 。 讲究代码设计，涉及到 链表反转函数设计（实现 给定head,tail的范围部分，实现链表反转）
- [AddTwoNumbers](linkedList/AddTwoNumbers.java)： https://leetcode.com/problems/add-two-numbers/ 。 使用较长链表作为返回链表。 使用"%" + "/"计算当前节点值和进位值。 **经历三个阶段**： 1.短链表走完 2. 长链表剩余部分走完 3. 最后元素是否有进位，决定是否创建新节点
- [合并两个有序链表-MergeTwoSortedLinkedList](linkedList/MergeTwoSortedLinkedList.java)： https://leetcode.com/problems/merge-two-sorted-lists 。
- [合并K个有序链表-MergeKSortedLists](linkedList/MergeKSortedLists.java)： https://leetcode.com/problems/merge-k-sorted-lists/ 使用优先级队列，小根堆

### QueueImplementation:队列实现
- [单链表实现队列](queueImplementation/LinkedListToQueue.java)： 尾插法 （先进先出，后进后出）: 在poll函数中，注意一个场景，当size==1时出队后，此时head==null,要同时调整tail=null。所以，**要在每次poll函数调用出队元素后中，检查是否head==null，从而决定是否调整tail.
- [RingArray数组实现队列](queueImplementation/RingArrayToQueue.java)：offeri, polli, **size**控制offeri,polli之间的关系
- [两个栈实现队列](queueImplementation/TwoStackImplementQueue.java)： pushStack,popStack + pushToPop()保证只有当popStack为空时，就将pushStack中的所有元素全部pop到popStack中去

### StackImplementation:栈的实现
- [单链表实现栈](stackImplementation/LinkedListToStack.java)： 头插法 （后进先出，先进后出）.单链表实现队列也好，单链表实现栈也好，都要注意 push时，考虑 空(head==null)/非空 两种情况！！！ 并且size!
- [数组实现栈](): 不具体实现了。只需要一个变量size, size既表示 当前stack中元素数量，也表示要新插入元素的位置。 [0, size),push时，arr[size++] = value; 弹出时，return arr[--size]
- [一个队列实现栈](stackImplementation/OneQueueImplementStack.java): 入队元素前面所有元素 出队， 重新入队，从而实现后进先出
- [两个队列实现栈](stackImplementation/TwoQueueImplementStack.java)： queue和help， 引用对换
- [最小栈](stackImplementation/GetMinStack.java)：https://leetcode-cn.com/problems/min-stack/ O（1）从栈中获得最小值. 创建两个栈，dataStack（存储原始数据），minStack辅助栈，所有 <= 栈顶的元素 都加到minStack中

### dequeImplementation:双端队列实现
- [双向链表实现双端队列+双端队列实现队列和栈](dequeImplementation/DoubleLinkedListToDeque.java):
  1. 单链表无法实现 双端队列（尾部出无法实现）
  2. 双向链表 实现 双端队列 需要满足要求： （1）Node具有前后指针 （2）双端队列CLASS具有 **头尾指针**
  3. 实现栈： 从头处 入栈，出栈 =》 push->offerFirst, pop->pollFirst
  4. 实现队列： 队尾入队，队首出队 =》 offer->offerLast, poll->pollFirst  
***下列对于上述的 单链表的栈实现和队列实现一并适用，犯过错！***
  5. **!!!依然是关于头插法，尾插法； 但是要记住，头插法尾插法 都需要 额外讨论【head==null】的情况**   
     =》分情况讨论 =》  
     **第一种情况分支： head == null时，执行head=cur,tail=cur;   
     第二种情况分支：head != null，执行头插或尾插法**
  6. 往外出元素的时候 poll()方法/pop()，一定要先检查 当前数据结构是否为 empty了 =》 可以用 **if (head==null) {}** 来检验是否当前为空。 此外，注意 head==tail时 弹出元素的情况，分类讨论。

### 二叉树： 灵活运用递归序
- [递归遍历二叉树](binaryTree/RecursiveTraversalBT.java)： 递归序，任何节点都会到达自己三次。 前序，中序，后序 就是按照 递归序中 第一次到达，第二次到达和第三次到达的时机 进行节点value的打印。
- [非递归遍历二叉树](binaryTree/UnRecursiveTraversalBT.java)： 前序，中序，后序。具体注释见代码。
- [层序遍历](binaryTree/LevelTraversalBT.java)
- [层序遍历确定每层节点](binaryTree/LevelTraversalBT.java)：用以实现 层序遍历的过程中 的 分层打印。 只需要添加两个变量curEnd + nextEnd;
- [二叉树最宽的层有多少个节点](binaryTree/TreeMaxWidth.java)
- [序列化与反序列化二叉树](binaryTree/SerialiseAndReconstructBT.java)： 【本质都是用队列，存储节点序列化的值】二叉树可以通过先序、后序或者按层遍历的方式序列化和反序列化，但是，二叉树无法通过中序遍历的方式实现序列化和反序列化。 -> **关键就是，序列化字符串的遍历结果中，包含null表示空节点。** （从而就可表示唯一的二叉树，并支持反序列化，在deserial时，遇到"null"直接返回null，不再新建节点）
- [EncodeNaryTreeToBT](binaryTree/EncodeNaryTreeToBinaryTree.java)：https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree . 将N叉树 序列化为 二叉树，并能够反序列化。 解法：将N叉树的每个节点的所有孩子（List<Node> children），转化为 二叉树中对应节点的 【左树右边界】。
- [二叉树的后继节点](binaryTree/SuccessorNode.java):【错题】二叉树节点拥有指向父节点的指针，给定任意一个节点，求中序遍历的后继节点
- [paperFolding](binaryTree/PaperFolding.java)：折纸问题，假象二叉树进行中序遍历打印（并且能够使每个节点的左右孩子 按照要求进行打印，例如 左孩子打印“凹”，右孩子打印“凸”），递归思想很经典，值得复习。  
    此方法能够 实现 假象当前来到一个头节点，左孩子为0，右孩子为1的 打印出 前序，中序，后序节点。 能够按输入的层数，进行满二叉树的结果打印。

### 二叉树的递归套路： 树型dp。 本质就是 左右子树的信息整合， 后序遍历
- [判断完全二叉树](BTTraversalQuestions/isCBT.java)：【中等难度，复习】CBT（堆就是完全二叉树）：对于一层而言 1.要么这一层是满的 2.如果不满，那也必须最后一层不满，即便最后一层不满，最后一层也是必须从左到右依次变满的
- [判断平衡二叉树](BTTraversalQuestions/isBalanced.java)：平衡二叉树是指，对于任何一个节点，左树和右树的最大高度相差的绝对值 不大于1 （<=1） && 左右子树也都是平衡树；
- [判断是否是满二叉树](BTTraversalQuestions/isFull.java)
- [判断二叉搜索树](BTTraversalQuestions/isBST.java)：**该题包含上游 对 返回null的处理，我自己进行了代码优化，可以反复看一看加固记忆。**
  **树型dp：目标结果考虑： 与X有关（以X为头节点的最优解），与X无关（左子树，右子树有最优解） 两种情况影响最终要求的结果，这是分析的起点**
    ```
    (1)只有最后要求的答案那个的INFO中的属性，在process中是最后求得，其他属性先求，并且好求。并且！，例如公共祖先问题，ancestor的求解会用到 先求出的 containsA和containsB！
    (2)特别注意process中的 base case： 如果可以直接返回new Info()，那么正常处理即可；如果包含诸如max,min之类的，那么返回null，则在上游，要特殊处理null的问题。可以多看看代码。
    (3)对于null 的处理分为两种：   
        【1】对于(int max, int min)类型，首先将max设为当前结点的值max=cur.val，然后if (left != null) {max=max(max,left)}； 接着if (right != null) {max=max(right,max)}
        【2】对于 (boolean isBST)类型，可以依据满足BST的条件 定义多个boolean 变量（这几个boolean变量都满足，则isBST=true），每个变量赋值中，进行空判断，如果为空则返回true，不为空，则left.max<cur.val的结果返回。（具体看isBS后面相关代码）
    ```    
- [最大二叉搜索子树的大小](BTTraversalQuestions/MaxSubBSTSize.java)
- [最大二叉搜索子树的头节点](BTTraversalQuestions/MaxSubBSTHead.java)
- [二叉树中两个节点之间的最远距离](BTTraversalQuestions/MaxDistance.java)： 【关键】判断两种情况： 当前节点X， 1.与节点X有关 2.与节点X无关（最大距离 在 左树/右树）
- [最近公共祖先](BTTraversalQuestions/LowestAncestor.java): version1: 给定两个节点进行对比; v2:给定两个值进行比对，二叉树中每个节点的值都不相等
- [派对的最大快乐值](BTTraversalQuestions/MaxHappy.java): 【经典，dp，学习】
- [二叉树的最小深度](BTTraversalQuestions)： 【面试，错题】当时做错了，很简单的递归套路，但是 要考虑到 左右子树为0时，Math.min()，不可取双边。需要单独讨论，left 与 right，为0情况下，不同的返回值。

### 贪心算法 : 解法上基本都是 **【排序】（使用比较器）或【堆】（使用哈夫曼编码/ 大根堆配合小根堆）**
```
    (1)贪心算法就是 通过 【取得每一个步中的局部最优（这个局部最优就是制定的贪心策略）】， 【最终组合起来就是 全局最优解的方法】。所以贪心策略也有可能就是错的。
    (2)该算法 存在 错误的概率，所以 是一种类似于猜的算法。
    (3)虽然在大多数情况下是有用的，但是 我们想要知道 贪心算法 是否是可行方案时， 只要我们能够举出一种反例（意思就是局部最优，无法组成全局最优），那么就说明 该问题使用贪心算法无效。
   ```
- [给定一个由字符串组成的数组strs，必须把所有的字符串拼接起来，返回所有的拼接结果中，字典序最小的结果](greedyAlgorithm/LowestLexicography.java):字典序就是java里面字符串的排序方式。
- [会议室的最佳安排](greedyAlgorithm/BestArrange.java)(具体题目都写在MyBase中): 排序， 以结束时间早晚排序 为 贪心策略
- [Light](greedyAlgorithm/Light.java): 分析放灯的情况，选择最贪心的位置进行放灯（比如说 三个位置 灯放中间）
- [IPO](greedyAlgorithm/IPO.java): 
  - Initial Public Offering(首次公开募股). https://leetcode-cn.com/problems/ipo/ . 
  - 大小堆。 小堆：按成本排序。 大堆：可以购买的项目中，按profit排序。
  - 策略： 每次都现根据cost在可以选择的项目中，选取profit最大的。最后到达K个项目，结束。
- [最小代价分割金条](greedyAlgorithm/LessMoneySplitGold.java): 哈夫曼编码，堆。 策略：【每次选最小的两个合并】，然后组成新的数组。每次合并就是成本。最终合并成只有一个数字的时候，即得到最终答案。
### 并查集: 图的连通性问题。
```
如果你一开始所有的样本量是N， 只要你 findHead 这件事情，它的调用次数 到达了O（N）或超过了O（N）（足够频繁），那么单次均摊下来，查询代价O(1)。他有可能某一个时刻花费时间非常长，但是均摊下来非常省。
说白了，并查集，查询只要到达了样本量这个规模，单次非常的快，就是O（1）.
```
- [并查集实现-哈希表](unionFindSet/UnionFindSet.java): 
  - 建立三个关系映射表：【1】普通元素到包装成对象的节点的映射（从而可以避免元素相同值的问题，转换成对象是为了使用不同对象的地址值各不相同，从而区分每一个节点） 【2】parent表 【3】size表。 
  - 关键点就是 围绕 代表节点（findFather-任意节点的代表节点的查找，isSameSet-判断两个节点代表节点是否相同，union-将小size的代表节点挂到大size的代表节点下面）。
- [朋友的圈子/省份数量](unionFindSet/NumberOfProvinces.java):
  - https://leetcode-cn.com/problems/number-of-provinces/ . 
  - 数组形式实现并查集。
- [岛屿数量](unionFindSet/NumOfIslands.java): 
  - 测试链接：https://leetcode.com/problems/number-of-islands/
  

### TopK
- [快排方法](topK/quickSort.java):



## [额外练习题](extraPractice)
### 链表部分：（链表的题通常需要注意两点：舍得用变量，千万别想着节省变量，否则容易被逻辑绕晕。 2. head 有可能需要改动时，先增加一个 假head，返回的时候直接取 假head.next，这样就不需要为修改 head 增加一大堆逻辑了。Dummy Node 哑节点）
- [删除链表中重复的结点](extraPractice/linkedList/DeleteDuplicates.java): https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii/solution/
- [链表内指定区间反转](extraPractice/linkedList/ReverseBetween.java): 给定一个链表，给定位置 M， N， 将 [M,N]进行反转，并将头节点返回。
- [二叉搜索树与双向链表](extraPractice/linkedList/TreeToDoublyList.java): https://leetcode-cn.com/problems/er-cha-sou-suo-shu-yu-shuang-xiang-lian-biao-lcof/【错题，经典】 1. 使用到了全局变量，head,pre 2.灵活运用了 中序递归遍历，值得到代码中学习思路

### 二叉树
- [对称二叉树](extraPractice/binaryTree/IsSymmetric.java): 【面试题】一个树的左子树与右子树镜像对称 **->** 两个树在什么情况下互为镜像？ 递归 + 非递归（重要）

### 双指针
- [删除有序数组中的重复项](extraPractice/doublePointer/RemoveDuplicates.java): 【easy,错题】 https://leetcode-cn.com/problems/remove-duplicates-from-sorted-array/ . 【双指针】， 完成区域+无效区域。 错的原因在于题意没理解好。