package ACAutomation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ACAutomationCode {
    // 前缀树上 做 KMP
    // 前缀树的节点
    public static class Node{
        String end;
        boolean endUse;
        Node[] nexts;
        // 千万不要忘了 非常重要的 fail指针
        Node fail;

        public Node() {
            end = null;
            endUse = false;
            nexts = new Node[26];
            fail = null;
        }
    }

    public static class ACAutomation{
        Node root;
        public ACAutomation() {
            root = new Node();
        }

        // 前缀树的插入过程
        public void insert(String word) {
            char[] str = word.toCharArray();
            Node cur = root;
            for (int i = 0; i < str.length; i++) {
                int path = str[i] - 'a';
                if (cur.nexts[path] == null) {
                    cur.nexts[path] = new Node();
                }
                cur = cur.nexts[path];
            }
            // 前缀树 上述循环退出时， cur 落在 最后的终止节点上，对应最后一个字符
            cur.end = word;
        }

        // 当前缀树上 全部word都插入完毕后， 前缀树建立完毕。 此时，构建fail指针
        // 构建 fail 指针的过程，其实 就是 BFS。 因为 这样能够保证 最大前缀和最大后缀的不断匹配
        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);

            // 弹出父亲，设置子节点的fail指针
            while (!queue.isEmpty()) {
                Node cur = queue.poll();
                for (int i = 0; i < 26; i++) {
                    if (cur.nexts[i] != null) { // cur.nexts[i] 这个节点 （或者说对应的 字符路径） 存在
                        // 1. 默认设置 fail 指向 root，后续更正的话 则更正
                        cur.nexts[i].fail = root;
                        Node cFail = cur.fail;
                        while (cFail != null) {
                            if (cFail.nexts[i] != null) {
                                cur.nexts[i].fail = cFail.nexts[i];
                                break;
                            }
                            cFail = cFail.fail;
                        }
                        // 完成 cur.nexts[i] 的 fail 指针 设置后，不要忘了 将其加入到队列中
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public List<String> containWords(String content) {
            char[] str = content.toCharArray();
            Node cur = root;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                int index = str[i] - 'a';
                while (cur != null && cur.nexts[index] == null) {
                    cur = cur.fail;
                }

                // 没有找到 匹配的点
                if (cur == null) {
                    cur = root; // 重定向到 root；下一轮从新匹配
                } else { // 找到了匹配的点
                    cur = cur.nexts[index];

                    // 到达了匹配的点 之后，就开始 沿着 fail走一圈
                    Node follow = cur;

                    // 沿途的 fail节点 收集一遍
                    while (follow != null) {
                        // 这里面就是对 字符串的 处理
                        if (follow.endUse) {
                            break;
                        }
                        if (follow.end != null) {
                            ans.add(follow.end);
                            follow.endUse = true;
                        }
                        follow = follow.fail;
                    }
                }
            }
            return ans;
        }


        public static void main(String[] args) {
            ACAutomation ac = new ACAutomation();
            ac.insert("dhe");
            ac.insert("he");
            ac.insert("abcdheks");
            // 设置fail指针
            ac.build();

            List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
            for (String word : contains) {
                System.out.println(word);
            }
        }


    }
}
