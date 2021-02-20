# HashMap



```java
private static int roundUpToPowerOf2(int number) {
        // assert number >= 0 : "number must be non-negative";
        return number >= MAXIMUM_CAPACITY
                ? MAXIMUM_CAPACITY
                : (number > 1) ? Integer.highestOneBit((number - 1) << 1) : 1;
    }
```

为什么是2的次幂

头插法快？

Jdk 1.7 多线程put方法扩容时会出现循环链表

fail-fast机制：使用迭代器对hashmap中元素进行remove时如果调用hashmap的remove方法会导致modcount不等于expectmodcount，进而抛出concurrentmodificationexception。应该使用迭代器的remove方法，改方法会在remove元素后更新expectmodcount。根本原因在于多线程情况下，一个线程修改hashmap一个线程读hashmap导致的数据不一致，因此hashmap通过modcount和expectmodcount来检查这种数据不一致的并发修改错误。



jdk1.7和1.8的区别

- 1.7 头插法，1.8尾插法(遍历链表，如果找到结束循环，覆盖并返回旧值；如果没找到则创建新node)
- 扩容：1.7 size >= threshold && table[bucketIndex] != null 1.8 size >= threshold（链表通过与操作分配到两个位置，低位链表和高位链表；红黑树上的节点既是树节点又是双向链表的节点，）







红黑树：数组长度大于64且链表长度大于等于8 小于等于6转换为链表



get：先hash再判断子节点最后比较key

## 红黑树

- 每个节点的颜色是红或黑
- 根节点是黑
- 每个为null的叶子节点是黑
- 一个红色节点，它的两个儿子为黑
- 每个节点到叶子节点的路径上包含相同数目的黑节点



### 插入：新节点为红色

- 父节点是黑色，不用调整
- 父节点是红色：
  - 叔叔节点是黑色或是null，旋转+变色
  - 叔叔节点是红色，父节点+叔叔节点变黑色，祖父节点变红色

```java
static <K,V> TreeNode<K,V> balanceInsertion(TreeNode<K,V> root,
                                                    TreeNode<K,V> x) {
            x.red = true;
            // x是新插入的节点，xp是父节点，xpp是祖父节点，xppl祖父节点的左儿子，xppr祖父节点的右儿子
            // 循环递归判断红黑树是否合适
            for (TreeNode<K,V> xp, xpp, xppl, xppr;;) {
                // 判断新插入的节点是不是根节点
                if ((xp = x.parent) == null) {
                    x.red = false;
                    return x;
                }
                // 父节点为黑色，不同调整
                else if (!xp.red || (xpp = xp.parent) == null)
                    return root;
                // 父节点是红色:
                // 父节点为祖父节点的左儿子:
                if (xp == (xppl = xpp.left)) {
                    // 叔叔节点是红色: 
                    if ((xppr = xpp.right) != null && xppr.red) {
                        // 父节点+叔叔节点变黑色，祖父节点变红色
                        xppr.red = false;
                        xp.red = false;
                        xpp.red = true;
                        // 继续递归
                        x = xpp;
                    }
                    // 叔叔节点是黑色或是null:
                    else {
                        // 新插入的节点是父节点的右孩子，先左旋再右旋
                        if (x == xp.right) {
                            root = rotateLeft(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        // 新插入的节点是父节点的左孩子，右旋
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateRight(root, xpp);
                            }
                        }
                    }
                }
                // 父节点为祖父节点的右儿子:
                else {
                    if (xppl != null && xppl.red) {
                        xppl.red = false;
                        xp.red = false;
                        xpp.red = true;
                        x = xpp;
                    }
                    else {
                        if (x == xp.left) {
                            root = rotateRight(root, x = xp);
                            xpp = (xp = x.parent) == null ? null : xp.parent;
                        }
                        if (xp != null) {
                            xp.red = false;
                            if (xpp != null) {
                                xpp.red = true;
                                root = rotateLeft(root, xpp);
                            }
                        }
                    }
                }
            }
        }
```

