package ACAutomation;

import java.util.ArrayList;

public class NumOfStringSubseq {
    // 暴力双指针
    public int numMatchingSubseq1(String s, String[] words) {
        char[] origin = s.toCharArray();
        int ans = 0;
        for (String word : words) {
            char[] str = word.toCharArray();
            int x = 0;
            int y = 0;
            for (; x < origin.length; x++) {
                if (origin[x] == str[y]) {
                    y++;
                }
                if (y == str.length) {
                    ans++;
                    break;
                }
            }
        }
        return ans;
    }

    // 更优解： 桶的思想  ==》 从而只需要 遍历一遍 原字符串，不需要多次遍历
    // 错误解，错在迭代中remove操作。 但是有借鉴意义。 其实只要创建一个新的arrayList,然后全部 正常分发就可以。 没有必要，非要在自身上进行操作。
//    public static int numMatchingSubseq(String s, String[] words) {
//        ArrayList<Node>[] counts = new ArrayList[26];
//        for (int i = 0; i < 26; i++) {
//            counts[i] = new ArrayList<>();
//        }
//        // 初始化 桶 O（words.length）
//        for (String word : words) {
//            int bucket = word.charAt(0) - 'a';
//            counts[bucket].add(new Node(word, 0));
//        }
//
//        int ans = 0;
//        // 开始遍历 s, 遍历过程中， 如果 word子序列完成，则收集
//        for (int i = 0; i < s.length(); i++) {
//            int curChar = s.charAt(i) - 'a';
//            for (int j = 0; j < counts[curChar].size(); j++) {
//                Node node = counts[curChar].get(j);
//                int wordLen = node.word.length();
//                node.index++;
//                if (node.index == wordLen) {
//                    counts[curChar].remove(j);
//                    ans++;
//                } else {
//                    if (node.word.charAt(node.index - 1) != node.word.charAt(node.index)) {
//                        counts[curChar].remove(j);
//                        counts[node.word.charAt(node.index) - 'a'].add(node);
//                    }
//                }
//            }
//        }
//
//        return ans;
//    }

    // 更优解： 桶的思想  ==》 从而只需要 遍历一遍 原字符串，不需要多次遍历
    // 正确解
    public int numMatchingSubseq(String s, String[] words) {
        ArrayList<Node>[] buckets = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            buckets[i] = new ArrayList<>();
        }
        // 初始化 桶 O（words.length）
        for (String word : words) {
            int bucket = word.charAt(0) - 'a';
            buckets[bucket].add(new Node(word, 0));
        }

        int ans = 0;
        // 开始遍历 s, 遍历过程中， 如果 word子序列完成，则收集
        for (int i = 0; i < s.length(); i++) {
            int curChar = s.charAt(i) - 'a';
            // 【错误点，重要点！！！】下面这一段很重要： 为了避免迭代器失效，所以不适用remove方法的替代方案。
            // 创建tmp复制原list，然后 原位置创建新的空list. 对tmp中的元素 向buckets正常进行分发，单次循环结束后，tmp自动销毁。
            ArrayList<Node> tmp = buckets[curChar];
            buckets[curChar] = new ArrayList<>();
            for (Node node : tmp) {
                node.index++;
                // 如果 当前 node.word 全部遍历完成，说明 该 word 为子序列
                if (node.index == node.word.length()) {
                    ans++;
                } else {
                    // index < length, 还有未检验的字符。 所以需要放入到对应的桶中
                    int newBucket = node.word.charAt(node.index) - 'a';
                    buckets[newBucket].add(node);
                }
            }
        }
        return ans;
    }

    public class Node {
        String word;
        int index;

        public Node(String s, int i) {
            word = s;
            index = i;
        }
    }
}
