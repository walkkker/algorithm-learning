package treemap;

import java.util.ArrayList;

public class SkipListCode {

    public static class SkipListNode<K extends Comparable<K>, V> {
        K key;
        V val;
        ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v) {
            key = k;
            val = v;
            // 【错误点1】 忘了 要 new 出一个 ArrayList
            nextNodes = new ArrayList<>();
        }

        // 判断 当前 Key 是否小于 otherKey
        public boolean isKeyLess(K otherKey) {
            return otherKey != null && (key == null || key.compareTo(otherKey) < 0);
        }

        // 判断 当前 key 是否 == otherKey
        public boolean isKeyEqual(K otherKey) {
            return (key == null && otherKey == null)
                    || (key != null && otherKey != null && key.compareTo(otherKey) == 0);
        }
    }

    public static class SkipListMap<K extends Comparable<K>, V> {
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel; // 增删改查都是从 maxLevel 进行对应位置的查找

        public SkipListMap() {
            head = new SkipListNode<>(null, null);
            head.nextNodes.add(null);     // 添加第0层
            size = 0;
            maxLevel = 0;
        }

        // 最重要的两个基础功能：
        // 本层查找 =》 整棵树的查找
        // 首先说明： 什么叫小？ 即 【next != null && next.isKeyLess(key)】;
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        // 全局查找，最后跑到 0 层找到 < key 的 最右节点
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, level);
                level--;
            }
            return cur;
        }

        // 我自己改写的左老师的，不知道理解是否正确 ==》
        // 当查找一个key的节点时，从最高层往下找，如果高层已经找到了，就直接返回。 不用再去第0层找最右<key的，然后查看第0层这个节点的下一个节点是不是。
        private SkipListNode<K, V> getKeyIndex(K key) {
            SkipListNode<K, V> cur = head;
            int level = maxLevel;
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, level);
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                if (next != null && next.isKeyEqual(key)) {
                    return next;
                } else {
                    level--;
                }
            }
            return null;
        }


        public boolean containsKey(K key) {
            return getKeyIndex(key) != null;
        }

        // 查
        public V get(K key) {
            SkipListNode<K, V> find = getKeyIndex(key);
            return find == null ? null : find.val;
        }

        // 增 + 改 并在一起
        // 改就是存在的话，直接修改val； 不存在的话，那么就执行添加操作
        public void put(K key, V val) {
            // 先检查是否存在，不存在的话，那么 重新从 maxLevel 开始 依次往下插入
            SkipListNode<K, V> find = getKeyIndex(key);
            if (find != null) {
                find.val = val;
                return;
            } else {
                // 因为要插入新节点 所以size++
                size++;
                // 设置新节点
                SkipListNode<K, V> newNode = new SkipListNode<>(key, val);
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                // 【错误点2】
                // for (int i = 0; i < newNodeLevel; i++) { 这个是错误的， 要 <= newNodeLevel范围应该是 [0, newNodeLevel]
                for (int i = 0; i <= newNodeLevel; i++) {
                    newNode.nextNodes.add(null);
                }

                // 有可能 newNodeLevel > maxLevel, 因此要把 head节点的 nextNodes 补齐
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    maxLevel++;
                }

                // 现在就可以执行插入操作了， 从最高层开始寻找，当 当前层数 level <= newNodeLevel时，即可插入
                int level = maxLevel;
                SkipListNode<K, V> cur = head;
                while (level >= 0) {
                    cur = mostRightLessNodeInLevel(key, cur, level);
                    if (level <= newNodeLevel) {
                        newNode.nextNodes.set(level, cur.nextNodes.get(level));
                        cur.nextNodes.set(level, newNode);
                    }
                    level--;
                }
            }
        }

        public void remove(K key) {
            // 首先要确认 是否存在， 只有存在的情况下， 才会执行 remove
            if (containsKey(key)) {
                size--;
                int level = maxLevel;
                SkipListNode<K, V> cur = head;
                while (level >= 0) {
                    cur = mostRightLessNodeInLevel(key, cur, level);
                    SkipListNode<K, V> next = cur.nextNodes.get(level);
                    // 【错误点3】 没有考虑 next 为 null 的情况
                    //cur.nextNodes.set(level, next.nextNodes.get(level));
                    // 其实当 next == null || next不为key 时，cur level层指针不需要修改，因为本来下一个节点就不指向要删除节点
                    // 只有当next != null && next.isKeyEqual(key) 时， 说明 next节点 在 level层 是要删除节点
                    if (next != null && next.isKeyEqual(key)) {
                        cur.nextNodes.set(level, next.nextNodes.get(level));
                    }

                    // 要检查 是否 此 level 只剩 head节点，如果是的话，删除 head节点 level层的指针
                    if (cur == head && cur.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        // 千万不要忘了， maxLevel 也一定要 一并修改
                        maxLevel--; // maxLevel 始终 与 head节点的nextNodes 所拥有的size （即 跳表的链表层数 -> 由head节点发起的链表层数）相同
                    }
                    level--;
                }
            }
        }

        public int size() {
            return size;
        }


        public K firstKey() {
            SkipListNode<K, V> firstKey = head.nextNodes.get(0);
            return firstKey == null ? null : firstKey.key;
        }

        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                while (cur.nextNodes.get(level) != null) {
                    cur = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K ceilingKey(K key) {
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }

        public K floorKey(K key) {
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.key.compareTo(key) == 0 ? next.key : less.key;
        }

    }
}
