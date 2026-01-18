package treemap;

public class AVL {
    // AVL 定义： 左右子树高度差不超过1


    // 【STEP1】 AVL节点的描述
    // Comparable<T> 接口里面有描述，要实现 CompareTo()方法，说白了就是(a-b的值，对应上-1,0,1) a < b， 那么返回 -1； a==b, return 0; a > b， return 1
    public static class AVLNode<K extends Comparable<K>, V> {
        K k;
        V v;
        AVLNode<K, V> l;
        AVLNode<K, V> r;
        int h;         // 平衡因子, 高度

        public AVLNode(K key, V val) {
            k = key;
            v = val;
            h = 1;
        }
    }

    public static class AVLTreeMap<K extends Comparable<K>, V> {
        // 默认属性就两个： 头结点 + size
        AVLNode<K, V> root;
        int size;

        public AVLTreeMap() {
            root = null;
            size = 0;
        }

        // 添加右旋和左旋这两个操作
        // 针对 节点cur 进行右旋操作： 右旋左孩子 -> 假设当前对父节点进行右旋，那么 左孩子成为新的父节点，原来的父节点成为新的父节点的右孩子。
        private AVLNode<K, V> rightRotate(AVLNode<K, V> cur) {
            // 步骤分层： 三步小连接 + 平衡因子的调整（说白了就是，谁的孩子发生了变化，那么谁就要进行调整）
            AVLNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            // 只有  cur 和 left 的 孩子发生了变化， 所以要重新计算他们的高度
            // 有个顺序哈，先算 cur的，再算 left。 因为 先把孩子的h搞正确，才能把父节点的h搞正确。
            cur.h = Math.max((cur.l == null ? 0 : cur.l.h), (cur.r == null ? 0 : cur.r.h)) + 1;
            left.h = Math.max((left.l == null ? 0 : left.l.h), (left.r == null ? 0 : left.r.h)) + 1;
            // 因为换头了，所以一定要返回新的头节点
            return left;
        }

        // 添加左旋函数 - 左旋右孩子
        private AVLNode<K, V> leftRotate(AVLNode<K, V> cur) {
            AVLNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            cur.h = Math.max((cur.l == null ? 0 : cur.l.h), (cur.r == null ? 0 : cur.r.h)) + 1;
            right.h = Math.max((right.l == null ? 0 : right.l.h), (right.r == null ? 0 : right.r.h)) + 1;
            return right;
        }


        // 很重要的，违规判断
        // 你传给我一个头节点，我给你检查 是哪一种的违规， 然后调整成平衡的二叉树，返回 调整后新的二叉树的头结点
        private AVLNode<K, V> maintain(AVLNode<K, V> cur) {
            if (cur == null) {
                return null;
            }
            // 先检查 左树和右树的高度, 从而可以查看 是否存在违规
            int leftHeight = cur.l == null ? 0 : cur.l.h;
            int rightHeight = cur.r == null ? 0 : cur.r.h;
            // 如果 左子树与右子树的高度差 > 1，那么就违背了AVL的平衡指标， AVL就需要进行平衡性的调整
            if (Math.abs(leftHeight - rightHeight) > 1) {
                // 要判断的，判断是 LL, LR, RL, RR 哪一种违规
                // 咋判断呀？如果是 左树高，那么只可能是LL,LR； 如果是右树高，那么只可能是RR,RL；
                if (leftHeight > rightHeight) {
                    int LL = cur.l.l == null ? 0 : cur.l.l.h;
                    int LR = cur.l.r == null ? 0 : cur.l.r.h;
                    if (LL >= LR) {
                        cur = rightRotate(cur);
                    } else {
                        cur.l = leftRotate(cur.l);
                        cur = rightRotate(cur);
                    }
                } else {
                    int RR = cur.r.r == null ? 0 : cur.r.r.h;
                    int RL = cur.r.l == null ? 0 : cur.r.l.h;
                    if (RR >= RL) {
                        cur = leftRotate(cur);
                    } else {
                        cur.r = rightRotate(cur.r);
                        cur = leftRotate(cur);
                    }
                }
            }
            return cur;
        }

        // 搞定了 基本的平衡操作之后，就可以进行 add和delete操作了
        // 注意，平衡性的调整 发生在 add新的节点 或 delete中 替代删除节点的所在位置的上面所有节点
        // 函数功能：该函数 再添加完头节点并且旋转之后， 返回 对应head子树的 最新的头节点.
        // 注意这是个递归函数。
        // 主问题： 将 (key, value) 添加到 cur为头节点的这棵树，调整平衡之后返回新的头节点
        // 分解子问题： 如果key < cur.key，那么添加到左子树。 否则添加到右子树中。 然后更新高度，返回调整后的头节点
        private AVLNode<K, V> add(AVLNode<K, V> cur, K key, V value) {
            // 当到达空节点时，不再往下走了，创建新的节点并返回对应地址
            if (cur == null) {
                return new AVLNode<K, V>(key, value);
            } else {
                if (key.compareTo(cur.k) < 0) {
                    cur.l = add(cur.l, key, value);
                } else {
                    cur.r = add(cur.r, key, value);
                }

                // 更新高度
                cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
                // 左右子树都是 avl树，所以此时 只需要保证 cur为头节点的子树 也是AVL树即可
                return maintain(cur);
            }
        }

        // 上游已经保证 AVL树中 包含有 这个key
        private AVLNode<K, V> delete(AVLNode<K, V> cur, K key) {
            if (key.compareTo(cur.k) < 0) {
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.k) > 0) {
                cur.r = delete(cur.r, key);
            } else {       // 此时找到这个对应key值的节点
                // 删除BST节点的四种情况
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else {   // 左右子树都不为空，此时 寻找BST 的后继节点(右子树的最左节点)来代替cur的位置， 然后后继节点以上的位置都要检查
                    AVLNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    cur.r = delete(cur.r, des.k);
                    des.l = cur.l;
                    des.r = cur.r;
                    cur = des;
                }
            }
            // 解决完 当前问题后，别忘了 更新cur
            if (cur != null) {
                cur.h = Math.max(cur.l == null ? 0 : cur.l.h, cur.r == null ? 0 : cur.r.h) + 1;
            }
            return maintain(cur);
        }


        // 寻找离 key 最近的 AVL节点， 存在两种可能返回 （存在key时是key节点，不存在key时就是最近节点）
        private AVLNode<K, V> findLastIndex(K key) {
            AVLNode<K, V> pre = null;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else if (key.compareTo(cur.k) > 0) {
                    cur = cur.r;
                } else {
                    break;
                }
            }
            return pre;
        }

        private AVLNode<K, V> findLastNoBigIndex(K key) {
            AVLNode<K, V> ans = root;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    cur = cur.l;
                } else {
                    ans = cur;
                    cur = cur.r;
                }
            }
            return ans;
        }

        //      >= key
        private AVLNode<K, V> findLastNoSmallIndex(K key) {
            AVLNode<K, V> ans = root;
            AVLNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.k) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.k) < 0) {
                    ans = cur;
                    cur = cur.l;
                } else {
                    cur = cur.r;
                }
            }
            return ans;
        }

        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            // 存在两个情况
            // 【1】如果 key 已经存在，那么 直接更改对应的 value
            // 【2】如果 key 不存在，那么 执行 插入操作
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                lastNode.v = value;
            } else {
                // 千万别忘了 size
                size++;
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (containsKey(key)) {
                size--;
                root = delete(root, key);
            }
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.k) == 0) {
                return true;
            } else {
                return false;
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && lastNode.k.compareTo(key) == 0) {
                return lastNode.v;
            } else {
                return null;
            }
        }

        public int size() {
            return size;
        }


        // 第一个key ==》 BST的最左节点
        public K firstKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.k;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.k;
        }

        // <= key 的最大  floor 下面
        public K floorKey(K key) {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastNoBigIndex(key);
            return lastNode == null ? null : lastNode.k;
        }


        // >= key 的最小   ==》 ceiling 网上看
        public K ceilingKey(K key) {
            if (root == null) {
                return null;
            }
            AVLNode<K, V> lastNode = findLastNoSmallIndex(key);
            return lastNode == null ? null : lastNode.k;
        }

    }

}
