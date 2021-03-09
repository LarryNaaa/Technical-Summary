# Leetcode

[TOC]

## 1. 剪绳子

给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m-1] 。请问 k[0]*k[1]*...*k[m-1] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。

```java
// dp n^2
public int cuttingRope(int n) {
        if(n == 2) return 1;
        if(n == 3) return 2;
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        for(int i = 4; i <= n; i++){
            int max = 0;
            for(int j = 1; j <= i / 2; j++){
                max = Math.max(max, dp[j] * dp[i - j]);
            }
            dp[i] = max;
        }
        return dp[n];
    }
```

## 2. 跳跃游戏

给定一个非负整数数组 `nums` ，你最初位于数组的 **第一个下标** 。

数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个下标。

```java
public boolean canJump(int[] nums) {
        int max = 0;
        for(int i = 0; i < nums.length; i++){
            if(i <= max){
                max = Math.max(max, i + nums[i]);
                if(max >= nums.length - 1) return true;
            }
        }
        return false;
    }
```

## 3. [盛最多水的容器](https://leetcode-cn.com/problems/container-with-most-water/)

给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。

```java
public int maxArea(int[] height) {
        int l = 0, r = height.length - 1;
        int res = 0;
        while(l < r){
            res = Math.max(res, (r - l) * Math.min(height[l], height[r]));
            if(height[l] <= height[r]){
                l++;
            }else{
                r--;
            }
        }
        return res;
    }
```

## 4. [接雨水](https://leetcode-cn.com/problems/trapping-rain-water/)

给定 *n* 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

```java
// 对于每一列来说，他能存的雨水量是他 左边最高墙和右边最高墙中较低的那堵墙的高度减去自身墙的高度 。
// 所以可以用数组记录每列左右最高墙的高度，然后计算每一列可以存的雨水量。
public int trap(int[] height) {
        if(height.length < 2) return 0;

        int[] left = new int[height.length];
        int[] right = new int[height.length];

        for(int i = 0; i < height.length; i++){
            left[i] = i == 0 ? height[0] : Math.max(left[i-1], height[i]);
        }

        for(int j = height.length - 1; j >= 0; j--){
            right[j] = j == height.length - 1 ? height[height.length - 1] : Math.max(right[j+1], height[j]);
        }

        int res = 0;
        for(int i = 0; i < height.length; i++){
            res += Math.min(left[i], right[i]) - height[i];
        }

        return res;
    }
```

## 5. [柠檬水找零](https://leetcode-cn.com/problems/lemonade-change/)

在柠檬水摊上，每一杯柠檬水的售价为 5 美元。

顾客排队购买你的产品，（按账单 bills 支付的顺序）一次购买一杯。

每位顾客只买一杯柠檬水，然后向你付 5 美元、10 美元或 20 美元。你必须给每个顾客正确找零，也就是说净交易是每位顾客向你支付 5 美元。

注意，一开始你手头没有任何零钱。

如果你能给每位顾客正确找零，返回 true ，否则返回 false 。

```java
public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for(int bill : bills){
            if(bill == 5){
                five++;
            }else if(bill == 10){
                if(five == 0) return false;
                five--;
                ten++;
            }else if(bill == 20){
                if(ten >= 1 && five >= 1){
                    ten--;
                    five--;
                }else if(five >= 3){
                    five = five - 3;
                }else{
                    return false;
                }
            }
        }
        return true;
    }
```

## 6. LRU

- 运用你所掌握的数据结构，设计和实现一个  LRU (最近最少使用) 缓存机制 。
  实现 LRUCache 类：

  - LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
  - int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
  - void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。

```java

public class LRUCache {
    class DLinkedNode {
        int key;
        int value;
        DLinkedNode prev;
        DLinkedNode next;
        public DLinkedNode() {}
        public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
    }

    private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
    private int size;
    private int capacity;
    private DLinkedNode head, tail;

    public LRUCache(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        // 使用伪头部和伪尾部节点
        head = new DLinkedNode();
        tail = new DLinkedNode();
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            return -1;
        }
        // 如果 key 存在，先通过哈希表定位，再移到头部
        moveToHead(node);
        return node.value;
    }

    public void put(int key, int value) {
        DLinkedNode node = cache.get(key);
        if (node == null) {
            // 如果 key 不存在，创建一个新的节点
            DLinkedNode newNode = new DLinkedNode(key, value);
            // 添加进哈希表
            cache.put(key, newNode);
            // 添加至双向链表的头部
            addToHead(newNode);
            ++size;
            if (size > capacity) {
                // 如果超出容量，删除双向链表的尾部节点
                DLinkedNode tail = removeTail();
                // 删除哈希表中对应的项
                cache.remove(tail.key);
                --size;
            }
        }
        else {
            // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
            node.value = value;
            moveToHead(node);
        }
    }

    private void addToHead(DLinkedNode node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(DLinkedNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(DLinkedNode node) {
        removeNode(node);
        addToHead(node);
    }

    private DLinkedNode removeTail() {
        DLinkedNode res = tail.prev;
        removeNode(res);
        return res;
    }
}
```

## 7. [有序数组的平方](https://leetcode-cn.com/problems/squares-of-a-sorted-array/)

给你一个按 **非递减顺序** 排序的整数数组 `nums`，返回 **每个数字的平方** 组成的新数组，要求也按 **非递减顺序** 排序。

```java
public int[] sortedSquares(int[] nums) {
        int index = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] < 0){
                index = i;
            }else{
                break;
            }
        }

        int l = index, r = index + 1, i = 0;
        int[] res = new int[nums.length];
        while(l >= 0 || r <= nums.length - 1){
            if(l < 0){
                res[i] = nums[r] * nums[r];
                r++;
            }else if(r >= nums.length){
                res[i] = nums[l] * nums[l];
                l--;
            }else if(nums[l] * nums[l] <= nums[r] * nums[r]){
                res[i] = nums[l] * nums[l];
                l--;
            }else{
                res[i] = nums[r] * nums[r];
                r++;
            }
            i++;
        }
        return res;
    }
```

## 8. 一个数据先递增再递减，找出数组不重复的个数，比如 [1, 3, 9, 1]，结果为3，不能使用额外空间，复杂度o(n)

```java
public class Solution {
    public int diffnum(int[] nums){
        int n = nums.length;
        if(n == 0 || nums == null){
            return 0;
        }
        int left = 0;
        int right = n - 1;
        int sum = 0;
        while(left <= right){
            if(nums[left] == nums[right]){
                sum++;
                int temp = nums[left];
                while(left <= right && nums[right] == temp)
                    right--;
                while(left <= right && nums[left] == temp)
                    left++;
            }
            else if(nums[left] < nums[right]){
                sum++;
                int temp = nums[left];
                while(left <= right && nums[left] == temp)
                    left++;
            }
            else{
                sum++;
                int temp = nums[right];
                while(left <= right && nums[right] == temp)
                    right--;
            }
        }
        return sum;
    }
}
```

## 9. [下一个排列](https://leetcode-cn.com/problems/next-permutation/)

实现获取 下一个排列 的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。

如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。

```java
public void nextPermutation(int[] nums) {
        if(nums.length < 2) return;

        int i = nums.length - 2;
        while(i >= 0 && nums[i] >= nums[i+1]){
            i--;
        }

        if(i >= 0){
            int j = nums.length - 1;
            while(j >= i && nums[i] >= nums[j]){
                j--;
            }
            swap(nums, i, j);
        }

        reverse(nums, i + 1, nums.length - 1);
    }

    public void swap(int[] nums, int i , int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public void reverse(int[] nums, int i, int j){
        while(i < j){
            swap(nums, i++, j--);
        }
    }
```

## 10. [单词搜索](https://leetcode-cn.com/problems/word-search/)

给定一个二维网格和一个单词，找出该单词是否存在于网格中。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。

```java
class Solution {
    public boolean exist(char[][] board, String word) {
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j] == word.charAt(0)){
                    if(dfs(board, word, i, j, 0)) return true;
                }
            }
        }
        return false;
    }

    public boolean dfs(char[][] board, String word, int i, int j, int k){
        if(k == word.length()) return true;

        if(i < 0 || j < 0 || i >= board.length || j >= board[0].length) return false;

        if(board[i][j] == '#') return false;

        if(board[i][j] != word.charAt(k)) return false;

        char temp = board[i][j];
        board[i][j] = '#';

        boolean ans = dfs(board, word, i + 1, j, k + 1) ||
        dfs(board, word, i - 1, j, k + 1) ||
        dfs(board, word, i, j + 1, k + 1) ||
        dfs(board, word, i, j - 1, k + 1);

        board[i][j] = temp;

        return ans;
    }
}
```

## 11. **和为s的连续正整数序列**

输入一个正整数 target ，输出所有和为 target 的   **连续正整数**   序列（至少含有两个数）。   序列内的数字由小到大排列   ，不同序列按照首个数字从小到大排列。   

滑动窗口 ：窗口左右两端都只能向右移动，当和小于sum时，j++，和大于sum时，i++，和等于sum就记录下窗口中i —j 中的序列，然后窗口继续后移，查找下一个满足条件的序列。

```java
class Solution {
    public int[][] findContinuousSequence(int target) {
        int i = 1;
        int j = 1;
        int sum = 0;
        List<int[]> list = new ArrayList<>();
        //序列是由小到大排列，所以如果i>target/2，那么i+i+1肯定大于target
        while(i <= target/2){    
            if(sum < target){
                sum += j;
                j++;
            }else if(sum > target){
                sum -= i;
                i++;
            }else{
                int[] res = new int[j - i];
                for(int z = i; z < j;z++){
                    res[z - i] = z;
                }
                list.add(res);
                // 左边界向右移动
                sum -= i;
                i++;
            }
        }
        return list.toArray(new int[list.size()][]);
    }
}
```

## 12. 某一个大文件被拆成了N个小文件，每个小文件编号从0至N-1，相应大小分别记为S(i)。给定磁盘空间为C，试实现一个函数从N个文件中连续选出若干个文件拷贝到磁盘中，使得磁盘剩余空间最小。

滑动窗口 ：每次记录窗口内的总和，和小于C，记录剩余空间，再窗口右端右移，和大于C，就窗口左端右移，小于C情况下比较剩余空间取最小值。

```java
public class Solution {
    public int[] findMin(int[] s,int c){
        int i = 0;
        int j = 0;
        int minValue = Integer.MAX_VALUE;
        int sum = 0;
        int left = 0;
        int right = 0;
        while(j <= s.length){
            if(sum <= c){
               j++;
               sum += s[j];
               minValue = Math.min(minValue,c - sum);
               if(minValue == c - sum){
                   left = i;
                   right = j;
               }
            }else{
                i++;
                sum -= s[i];
            }
        }
        int nums = new int[right - left];
        for(int k = left;k < right;k++){
            nums[k - left] = s[k];
        }
        return nums;
    }
}
```

## 13. 最小k个数

给定一个数组，找出其中最小的K个数。例如数组元素是4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。如果K>数组的长度，那么返回一个空的数组

```java

```

## 14. [编辑距离](https://leetcode-cn.com/problems/edit-distance/)

给你两个单词 word1 和 word2，请你计算出将 word1 转换成 word2 所使用的最少操作数 。

你可以对一个单词进行如下三种操作：

插入一个字符
删除一个字符
替换一个字符

```java
public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for(int i = 0; i <= word1.length(); i++){
            dp[i][0] = i;
        }
        for(int i = 0; i <= word2.length(); i++){
            dp[0][i] = i;
        }

        for(int i = 1; i <= word1.length(); i++){
            for(int j = 1; j <= word2.length(); j++){
                if(word1.charAt(i-1) == word2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.min(
                        dp[i-1][j-1], Math.min(dp[i-1][j], dp[i][j-1])
                    ) + 1;
                }
            }
        }

        return dp[word1.length()][word2.length()];
    } 
```

## 15. [合并两个有序数组](https://leetcode-cn.com/problems/merge-sorted-array/)

给你两个有序整数数组 nums1 和 nums2，请你将 nums2 合并到 nums1 中，使 nums1 成为一个有序数组。

初始化 nums1 和 nums2 的元素数量分别为 m 和 n 。你可以假设 nums1 的空间大小等于 m + n，这样它就有足够的空间保存来自 nums2 的元素。

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
        if(n < 1) return;
        if(m < 1) {
            for(int i = 0; i < n; i++){
                nums1[i] = nums2[i];
            }
        }
        int p1 = m - 1, p2 = n - 1;
        int p = m + n - 1;
        while(p1 >= 0 && p2 >= 0){
            nums1[p--] = (nums1[p1] >= nums2[p2]) ? nums1[p1--] : nums2[p2--];
        }

        if(p1 < 0){
            while(p >= 0){
                nums1[p--] = nums2[p2--];
            }
        }

        if(p2 < 0){
            while(p >= 0){
                nums1[p--] = nums1[p1--];
            }
        }
    }
```

## 16. [最大数](https://leetcode-cn.com/problems/largest-number/)

给定一组非负整数 nums，重新排列它们每个数字的顺序（每个数字不可拆分）使之组成一个最大的整数。

注意：输出结果可能非常大，所以你需要返回一个字符串而不是整数。

```java
public String largestNumber(int[] nums) {
        if(nums.length < 2) return nums[0] + "";
        String[] strs = new String[nums.length];
        for(int i = 0; i < strs.length; i++){
            strs[i] = nums[i]+"";
        }
        Arrays.sort(strs, (a, b) -> {
            String s1 = a + b;
            String s2 = b + a;
            return s2.compareTo(s1);
        });
        if(strs[0].equals("0")) return "0";
        StringBuilder res = new StringBuilder();
        for(String str : strs){
            res.append(str);
        }
        return res.toString();
    }
```

## 17. [数组中的第K个最大元素](https://leetcode-cn.com/problems/kth-largest-element-in-an-array/)

在未排序的数组中找到第 **k** 个最大的元素。请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。

```java
class Solution {
    int res = -1;
    public int findKthLargest(int[] a, int K) {
        // write code here
        quick(a, 0, a.length - 1, a.length - K);
        return res;
    }
    
    public void quick(int[] a, int L, int R, int K){
        int pivot = a[L];
        int l = L, r = R;
        
        while(l < r){
            while(l < r && a[r] >= pivot){
                r--;
            }
            if(l < r){
                a[l] = a[r];
            }
            while(l < r && a[l] <= pivot){
                l++;
            }
            if(l < r){
                a[r] = a[l];
            }
            if(l >= r){
                a[l] = pivot;
            }
        }
        
        if(l == K){
            res = a[l];
        }else if(l < K){
            quick(a, l + 1, R, K);
        }else{
            quick(a, L, l - 1, K);
        }
    }
}
```

## 18. 二叉树的之字形层序遍历

给定一个二叉树，返回该二叉树的之字形层序遍历，（第一层从左向右，下一层从右向左，一直这样交替）

```java
public ArrayList<ArrayList<Integer>> zigzagLevelOrder (TreeNode root) {
        // write code here
        ArrayList<ArrayList<Integer>> res = new ArrayList<>();
        if(root == null) return res;
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        
        while(!q.isEmpty()){
            int size = q.size();
            ArrayList<Integer> list = new ArrayList<>();
            for(int i = 0;i < size; i++){
                TreeNode temp = q.poll();
                if((res.size() + 1) % 2 != 0){
                    list.add(temp.val);
                }else{
                    list.add(0, temp.val);
                }
                if(temp.left != null) q.offer(temp.left);
                if(temp.right != null) q.offer(temp.right);
            }
            res.add(list);
        }
        
        return res;
    }
```

## 19. 最近公共祖先节点

给定一棵二叉树以及这棵树上的两个节点 o1 和 o2，请找到 o1 和 o2 的最近公共祖先节点。 

```java
public int lowestCommonAncestor (TreeNode root, int o1, int o2) {
        // write code here
        TreeNode res = dfs(root, o1, o2);
        return res.val;
    }
    
    public TreeNode dfs (TreeNode root, int o1, int o2) {
        // write code here
        if(root == null) return null;
        if(root.val == o1 || root.val == o2) return root;
        
        TreeNode left = dfs(root.left, o1, o2);
        TreeNode right = dfs(root.right, o1, o2);
        
        if(left != null && right != null){
            return root;
        }else if(left != null){
            return left;
        }else{
            return right;
        }
        
    }
```

## 20. 大数加法

以字符串的形式读入两个数字，编写一个函数计算它们的和，以字符串形式返回。

```java
public String solve (String s, String t) {
        // write code here
        StringBuilder res = new StringBuilder();
        int a = s.length() - 1, b = t.length() - 1;
        int carry = 0;
        while(a >= 0 || b >= 0 || carry != 0){
            int sn = a < 0 ? 0 : s.charAt(a--) - '0';
            int tn = b < 0 ? 0 : t.charAt(b--) - '0';
            int sum = sn + tn + carry;
            res.insert(0, sum % 10);
            carry = sum / 10;
        }
        return res.toString();
    }
```

## 21. [K 个一组翻转链表](https://leetcode-cn.com/problems/reverse-nodes-in-k-group/)

给你一个链表，每 k 个节点一组进行翻转，请你返回翻转后的链表。

k 是一个正整数，它的值小于或等于链表的长度。

如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        //定义一个头指针，用来将后面所有节点统一处理
        ListNode dum = new ListNode();
        dum.next = head;
        //用来标记每一次反转部分的前一个结点
        ListNode pre = dum;
        //运动指针，扫描要反转的部分
        ListNode end = dum;

        while(end.next != null){
            //每次扫描完要反转的部分，如果end为空说明达到尾部，直接break
            for(int i = 0; i < k && end != null;i++) end = end.next;
            if(end == null) break;
            //标记要反转部分的开头节点
            ListNode start = pre.next;
            //标记反转链表后面部分的第一个节点，避免丢失
            ListNode next = end.next;
            //将要反转部分向后的指针断开，准备反转
            end.next = null;
            //反转完的链表返回反转后的头结点，接到pre的后面
            pre.next = reverse(start);
            //反转后start指针应该指向反转完成部分的末尾
            start.next = next;
            //让pre和end指针继续指向待反转链表的前一个节点
            pre = start;
            end = pre;
        }
        return dum.next;
    }
    public ListNode reverse(ListNode start){
        ListNode cur = start;
        ListNode pre1 = null;
        while(cur != null){
            ListNode temp = cur.next;
            cur.next = pre1;
            pre1 = cur;
            cur = temp;
        }
        return pre1;
    }
}

```

## 22. **二叉搜索树的第K大的节点**

二叉搜索树（二叉[排序]()树）满足：根节点大于左子树，小于右子树。那么[二叉树]()的   中序遍历序列就是一个递增序列   。为方便了找第K大的节点，我们可以调换左右子树的遍历顺序，这样遍历序列是一个递减序列，这样遍历到第K个元素就是我们要找的第K大的节点。

```java
class Solution {
    int res,index;
    public int kthLargest(TreeNode root, int k) {
        index = k;
        dfs(root);
        return res;
    }
    public void dfs(TreeNode root){
        if(root == null) return;
        dfs(root.right);
        if(index == 0) return;
        index--;
        if(index == 0) res = root.val;
        dfs(root.left);
    }
}
```

## 23. 重建二叉树

输入某二叉树的前序遍历和中序遍历的结果，请重建出该二叉树。假设输入的前序遍历和中序遍历的结果中都不含重复的数字。例如输入前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}，则重建二叉树并返回。

```java
public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if(pre.length < 1 || in.length < 1) return null;
        return helper(0, pre.length-1, 0, in.length-1, pre, in);
    }
    
    public TreeNode helper(int pl, int pr, int il, int ir, int [] pre,int [] in){
        if(pl > pr || il > ir) return null;
        TreeNode root = new TreeNode(pre[pl]);
        int iroot = 0;
        for(int i = il; i <= ir; i++){
            if(pre[pl] == in[i]){
                iroot = i;
            }
        }
        int length = iroot - il;
        root.left = helper(pl+1, pl + length, il, iroot-1, pre, in);
        root.right = helper(pl+length+1, pr, iroot+1, ir, pre, in);
        return root;
    }
```

## 24. [合并区间](https://leetcode-cn.com/problems/merge-intervals/)

以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。

```java
public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b)->{
            if(a[0] == b[0]){
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        
        int l = intervals[0][0];
        int r = intervals[0][1];
        
        List<int[]> ans = new ArrayList<>();
        
        for(int i = 1; i < intervals.length; ++i){
            int[] curr = intervals[i];
            
            if(l <= curr[0] && r >= curr[1]) continue;
            
            if(r >= curr[0] && r <= curr[1]) r = curr[1];
            
            if(curr[0] > r){
                ans.add(new int[] {l, r});
                l = curr[0];
                r = curr[1];
            }
        }
        
        ans.add(new int[] {l, r});
        
        return ans.toArray(new int[ans.size()][]);
    }
```

## 25. [矩形重叠](https://leetcode-cn.com/problems/rectangle-overlap/)

矩形以列表 [x1, y1, x2, y2] 的形式表示，其中 (x1, y1) 为左下角的坐标，(x2, y2) 是右上角的坐标。矩形的上下边平行于 x 轴，左右边平行于 y 轴。

如果相交的面积为 正 ，则称两矩形重叠。需要明确的是，只在角或边接触的两个矩形不构成重叠。

给出两个矩形 rec1 和 rec2 。如果它们重叠，返回 true；否则，返回 false 。

```java
public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        if (rec1[0] == rec1[2] || rec1[1] == rec1[3] || rec2[0] == rec2[2] || rec2[1] == rec2[3]) {
            return false;
        }
        return !(rec1[2] <= rec2[0] ||   // left
                 rec1[3] <= rec2[1] ||   // bottom
                 rec1[0] >= rec2[2] ||   // right
                 rec1[1] >= rec2[3]);    // top
    }
```

