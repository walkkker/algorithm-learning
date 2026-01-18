package unionFindSet;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 对于下面这个代码，经检验，加不加Node这个类没有区别，可以去掉，部分代码稍作更改即可。 只需要保证传入的List<V>中的元素
 * 值（引用类型就是地址值不同）各不相同即可。从而使得 HashMap能够正确找到。
 *
 * 删除Node的原因就在于，nodes这张表也是 通过 HashMap对 传入的值进行映射的，当List<V>中存在重复值时，Node依然会撞上无法区分。所以没有区别。
 */

public class UnionFindSet {

    // 首先创建
    public static class Node<V> {
        V val;

        public Node(V value) {
            val = value;
        }
    }

    public static class UnionFind<V> {
        HashMap<V, Node<V>> nodes;
        HashMap<Node<V>, Node<V>> parentMap;
        HashMap<Node<V>, Integer> sizeMap;
        public UnionFind(List<V> values) {
            nodes = new HashMap<>(); // 某一个值 对应的 节点
            parentMap = new HashMap<>(); // <当前节点，父节点>
            sizeMap = new HashMap<>(); // <某一集合的代表节点， 该集合的大小>
            for (V val : values) {
                nodes.put(val, new Node(val));
            }
        }

        public Node<V> findFather(Node<V> cur) {
            // 1. 建立 栈 记录沿途节点
            // 2. 将栈中元素，直接连到 父节点下面
            Stack<Node<V>> stack = new Stack<>();
            while (cur != parentMap.get(cur)) {
                stack.push(cur);
                cur = parentMap.get(cur);
            }
            // 退出时， cur为 代表节点，即为返回值
            // 在返回前，进行 并查集的优化，将沿途节点的父节点全部更新为代表节点
            while (!stack.isEmpty()) {
                Node<V> tmp = stack.pop();
                parentMap.put(tmp, cur);
            }
            return cur;
        }


        public boolean isSameSet(V a, V b) {
            return findFather(nodes.get(a)) == findFather(nodes.get(b));
        }

        public void union(V a, V b) {
            Node<V> aHead = findFather(nodes.get(a));
            Node<V> bHead = findFather(nodes.get(b));
            // 如果 aHead == bHead，说明代表节点相同，不需要进行union
            // 如果 不相等，则需要进行union
            if (aHead != bHead) {
                int aSize = sizeMap.get(aHead);
                int bSize = sizeMap.get(bHead);
                Node<V> big = aSize >= bSize ? aHead : bHead;
                Node<V> small = big == aHead ? bHead : aHead;
                // union操作 更新 parentMap, sizeMap
                parentMap.put(small, big);// 直接 小挂大 ，更新父节点
                sizeMap.put(big, aSize + bSize);
                sizeMap.remove(small);
            }
        }

        public int sets() {
            return sizeMap.size();
        }

    }



}
