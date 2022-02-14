package treemap;

public class SizeBalancedTree {
    // 只要孩子节点改变，就要进行maintain
    public static class SBTNode<K extends Comparable<K>, V> {
        K key;
        V value;
        SBTNode<K, V> l;
        SBTNode<K, V> r;
        int size;

        public SBTNode(K k, V v) {
            key = k;
            value = v;
            size = 1;
        }
    }

    public static class SizeBalancedTreeMap<K extends Comparable<K>, V> {
        SBTNode<K, V> root;

        public SizeBalancedTreeMap() {
            root = null;
        }

        public SBTNode<K, V> rightRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            return left;
        }

        public SBTNode<K, V> leftRotate(SBTNode<K, V> cur) {
            SBTNode<K, V> right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l == null ? 0 : cur.l.size) + (cur.r == null ? 0 : cur.r.size) + 1;
            return right;
        }

        public SBTNode<K, V> maintain(SBTNode<K, V> cur) {
            // 【STEP1】 这一句千万不要忘了，因为有可能会传进来 null 节点让调平
            if (cur == null) {
                return null;
            }
            // 【STEP2】 获取六个位置大小
            int leftSize = cur.l != null ? cur.l.size : 0;
            int LLSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int LRSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rightSize = cur.r != null ? cur.r.size : 0;
            int RRSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;
            int RLSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            // 【判断是哪一种违规，然后进行相应的旋转，不要忘了 对孩子节点更改的节点 递归使用maintain】
            if (LLSize > rightSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (LRSize > rightSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                // cur cur.l cur.r 的孩子都发生了变化，都要递归进行调平操作
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (RRSize > leftSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (RLSize > leftSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }


        // 在以 cur 为头结点的树上， 插入 (key. value), 返回添加完成并调整平衡后的头结点 ==》 递归
        public SBTNode<K, V> add(SBTNode<K, V> cur, K key, V value) {
            if (cur == null) {
                return new SBTNode(key, value);
            }
            // 千万不要忘了
            cur.size++;
            if (key.compareTo(cur.key) < 0) {
                cur.l = add(cur.l, key, value);
            } else if (key.compareTo(cur.key) > 0) {
                cur.r = add(cur.r, key, value);
            }
            return maintain(cur);
        }

        // cur 这棵树上，删除 key节点
        // 返回新的头部
        public SBTNode<K, V> delete(SBTNode<K, V> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.key) < 0) {
                cur.l = delete(cur.l, key);
            } else if (key.compareTo(cur.key) > 0) {
                cur.r = delete(cur.r, key);
            } else {   // 当 找到当前结点与 key值相同时， 四种处理情况
                if (cur.l == null && cur.r == null) {
                    cur = null;
                } else if (cur.l != null && cur.r == null) {
                    cur = cur.l;
                } else if (cur.l == null && cur.r != null) {
                    cur = cur.r;
                } else {
                    // 左右不为空， 取cur 的后继结点 来替代（找到，并删除，再替代）。记住替代的时候，更新 l,r,size
                    SBTNode<K, V> des = cur.r;
                    while (des.l != null) {
                        des = des.l;
                    }
                    cur.r = delete(cur.r, des.key);
                    des.l = cur.l;
                    des.r = cur.r;
                    des.size = cur.size;
                    cur = des;
                }
            }
            // cur = maintain(cur);
            return cur;
        }


        // 下面是对 BST 的常用操作
        public SBTNode<K, V> getIndex(K key) {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else if (key.compareTo(cur.key) > 0) {
                    cur = cur.r;
                } else {
                    return cur;
                }
            }
            return null;
        }

        private SBTNode<K, V> findFloorIndex(K key) {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            SBTNode<K, V> ans = null;
            while (cur != null) {
                if (cur.key.compareTo(key) < 0) {
                    ans = cur;
                    cur = cur.r;
                } else if (cur.key.compareTo(key) > 0) {
                    cur = cur.l;
                } else {
                    ans = cur;
                    break;
                }
            }
            return ans;
        }


        //  >= key
        private SBTNode<K, V> findCeilingIndex(K key) {
            if (root == null) {
                return null;
            }
            SBTNode<K, V> cur = root;
            SBTNode<K, V> ans = null;
            while (cur != null) {
                if (cur.key.compareTo(key) > 0) {
                    ans = cur;
                    cur = cur.l;
                } else if (cur.key.compareTo(key) < 0) {
                    cur = cur.r;
                } else {
                    ans = cur;
                    break;
                }
            }
            return ans;
        }

        public void put(K key, V value) {
            SBTNode<K, V> tmp = getIndex(key);
            if (tmp != null) {
                tmp.value = value;
            } else {
                root = add(root, key, value);
            }
        }

        public V get(K key) {
            SBTNode<K, V> ans = getIndex(key);
            return ans == null ? null : ans.value;
        }

        public void remove(K key) {
            SBTNode<K, V> tmp = getIndex(key);
            if (tmp != null) {
                root = delete(root, key);
            }
        }

        public boolean containsKey(K key) {
            SBTNode<K ,V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) > 0) {
                    cur = cur.r;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else {
                    return true;
                }
            }
            return false;
        }

        // 很大的特点: 可以找到对应的 index； 注意 因为每个节点都有size， 所以起始节点是从 1 开始算的
        // 递归函数
        private SBTNode<K, V> getIndex(SBTNode<K, V> cur, int index) {
            int curIndex = (cur.l != null ? cur.l.size : 0) + 1;
            if (index == curIndex) {
                return cur;
            } else if (index < curIndex) {
                return getIndex(cur.l, index);
            } else {
                return getIndex(cur.r, index - curIndex);
            }
        }

        public int size() {
            return root == null ? 0 : root.size;
        }

        // 【错误点】
        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid index");
            }
            // 注意，因为 getIndex 里面，范围是 [1, N]， 但是 对外接口是 [0, N - 1]. 所以要做一层映射
            // 即 找0 -》 找1；  找2 -》 找三
            return getIndex(root, index + 1).key;
        }

        public V getIndexValue(int index) {
            return getIndex(root, index + 1).value;
        }


        public K firstKey() {
            SBTNode<K, V> cur = root;
            while (cur.l != null) {
                cur = cur.l;
            }
            return cur.key;
        }

        public K lastKey() {
            SBTNode<K, V> cur = root;
            while (cur.r != null) {
                cur = cur.r;
            }
            return cur.key;
        }

        public K floorKey(K key) {
            return findFloorIndex(key).key;
        }

        public K ceilingKey(K key) {
            return findCeilingIndex(key).key;
        }


        public int lessEqualKeySize(K key) {
            SBTNode<K, V> cur = root;
            int ans = 0;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans += cur.size - (cur.r == null ? 0 : cur.r.size);
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.l;
                } else {
                    ans += cur.size - (cur.r == null ? 0 : cur.r.size);
                    cur = cur.r;
                }
            }
            return ans;
        }

    }

}
