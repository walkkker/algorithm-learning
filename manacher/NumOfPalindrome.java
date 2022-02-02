package manacher;

public class NumOfPalindrome {
    public int countSubstrings(String s) {
        char[] old = s.toCharArray();
        char[] str = manacherString(old);
        int[] pArr = new int[str.length];
        int R = 0;
        int C = -1;
        int ans = old.length;
        for (int i = 0; i < str.length; i++) {
            pArr[i] = i < R ? Math.min(R - i, pArr[2 * C - i]) : 1;
            while (i - pArr[i] > -1 && i + pArr[i] < str.length) {
                if (str[i - pArr[i]] == str[i + pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }

            ans += (pArr[i] - 1) / 2;
        }
        return ans;
    }

    public char[] manacherString(char[] old) {
        char[] ans = new char[2 * old.length + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : old[i / 2];
        }
        return ans;
    }

}
