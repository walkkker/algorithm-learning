package manacher;

public class ShortestPalindrome {

    public String shortestPalindrome(String s) {
        //【错误点3】 注意题目给的限定条件，特殊处理空字符串情况
        if (s.length() == 0) {
            return "";
        }
        char[] old = s.toCharArray();
        char[] str = manacherString(old);
        int N = str.length;
        int[] pArr = new int[N];
        int L = N;
        int C = -1;
        for (int i = N - 1; i >= 0; i--) {
            //【错误点1】 从右至左过程中，注意变量的改变： 比如 R - i 变成 i - L(一定要谁大减谁)
            pArr[i] = i > L ? Math.min(i - L, pArr[C * 2 - i]) : 1;
            while (i - pArr[i] > -1 && i + pArr[i] < N) {
                //System.out.println(i - pArr[i]);
                //System.out.println(i + pArr[i]);
                if(str[i - pArr[i]] == str[i + pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            if (i - pArr[i] < L) {
                L = i - pArr[i];
                C = i;
            }
            // 【错误点2】 这里搞错了， 因为是到不了的，开区间，所以要 ==-1
            if (L == -1) {
                break;
            }
        }
        int ansPos = C;
        int oldPos = ansPos / 2;
        int oldEnd = (ansPos + pArr[ansPos] - 2) / 2;
        int addNum = old.length - oldEnd - 1;
        char[] ans = new char[addNum + old.length];
        int index = 0;
        for (int i = old.length - 1; i > oldEnd; i--) {
            ans[index++] = old[i];
        }
        for (int i = 0; i < old.length; i++) {
            ans[index++] = old[i];
        }
        return String.valueOf(ans);
    }

    public char[] manacherString(char[] old) {
        char[] ans = new char[old.length * 2 + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : old[i / 2];
        }
        return ans;
    }
}
