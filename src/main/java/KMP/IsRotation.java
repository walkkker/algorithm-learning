package KMP;

// https://leetcode-cn.com/problems/rotate-string/
public class IsRotation {
    public boolean rotateString(String s, String goal) {
        if(s == null || goal == null || s.length() != goal.length()) {
            return false;
        };
        String newS = s + s;
        return getIndexOf(newS, goal) != -1;
    }

    // KMP Algorithm
    public int[] getNextArr(char[] match) {
        if (match.length == 1) {
            return new int[]{-1};
        }
        int[] next = new int[match.length];
        next[0] = -1;
        next[1] = 0;
        int i = 2;
        int cn = 0;
        while (i < match.length) {
            if (match[i - 1] == match[cn]) {
                next[i++] = ++cn;
            } else {
                if (cn > 0) {
                    cn = next[cn];
                } else {
                    next[i++] = 0;
                }
            }
        }
        return next;
    }

    // 以后这里使用String s1, String s2;  函数内部使用的char[]变量名为 str, match
    public int getIndexOf(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() < 1 || s1.length() < s2.length()) {
            return -1;
        }
        char[] str = s1.toCharArray();
        char[] match = s2.toCharArray();
        // 【错误点1： 漏了下面这句】
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
}
