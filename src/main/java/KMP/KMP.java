package KMP;

public class KMP {

    // 第一部分，针对模式串的 next函数， 获得next数组。 该next数组包含了模式串的局部匹配信息。
    // 即对应 位置 i， i 之前的 字符串部分中，前缀与后缀字符串的最长匹配长度
    public static int[] getNextArr(char[] match) {
        // 首先 特殊情况处理，正常处理方式 是 当长度 >= 2时，所以 长度为1时，特殊处理一下
        if (match.length == 1) {
            return new int[]{-1};
        }
        // 首先当然是 创建 next 数组
        int[] next = new int[match.length];
        // 默认规定 前两项的设置
        next[0] = -1;
        next[1] = 0;
        // O（M）的时间复杂度。 遍历match数组的每一个位置，所以起始点设为2（前两项已经设置完毕）
        int i = 2;
        // ！！！当前是哪个位置的值在和i-1位置的字符比较，这也是核心，每次都集中在 cn 与 i - 1的比较上面
        int cn = 0;
        // cn == check number. 对应每一个i，cn对应 next[i - 1]，即最长前缀的下一个位置 或调整后的前缀的下一个位置
        // 我们要做的事情就是 探索 cn 是否 == match[i - 1], 如果相等的话，那么 next[i] == cn + 1;
        // 说白了 充分利用 i-1位置的信息 （包括 match[i - 1] 与 match[cn]的比较, 以及 next[i - 1] + next[next[i - 1]] 递推如此得到的新的cn）
        while (i < match.length) {
            // 第一个点 cn 与 i - 1 比对，成功的话， next[i]直接确定，同时更新cn为i+1做准备，即可
            // 如果不相同，分两种情况： 【1】cn == 0, 此时 next[i] = 0;  【2】cn > 0， 那么此时 match[cn] != match[i - 1]后，cn可以进行跳转， cn = next[cn]；
            if (match[i - 1] == match[cn]) {
                next[i++] = ++cn;
            } else {
                if (cn > 0) {
                    cn = next[cn];
                } else { // cn == 0
                    next[i++] = 0;
                }
            }
        }
        return next;
    }

    public static int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < s2.length() || s1.length() == 0 || s2.length() == 0) {
            return -1;
        }
        char[] str = s1.toCharArray();
        char[] match = s2.toCharArray();
        int[] next = getNextArr(match);
        int x = 0;
        int y = 0;
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else {
                if (next[y] != -1) {
                    y = next[y];
                } else {
                    x++;
                }
            }
        }
        return y == match.length ? x - y : -1;
    }



    // for test
    public static String getRandomString(int possibilities, int size) {
        char[] ans = new char[(int) (Math.random() * size) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
        }
        return String.valueOf(ans);
    }

    public static void main(String[] args) {
        int possibilities = 5;
        int strSize = 20;
        int matchSize = 5;
        int testTimes = 5000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(possibilities, strSize);
            String match = getRandomString(possibilities, matchSize);
            if (getIndexOf(str, match) != str.indexOf(match)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
