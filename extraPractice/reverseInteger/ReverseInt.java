package extraPractice.reverseInteger;

public class ReverseInt {
    public int reverse(int x) {
        int ans = 0;
        while (x != 0) {
            // 特别重要，判断溢出
            if (ans > Integer.MAX_VALUE / 10 || ans < Integer.MIN_VALUE / 10) {
                return 0;
            }
            int tmp = x % 10;       // 计算出 当前的最后一位数字
            x /= 10;                //去除当前已经计算出的最后一位
            ans = ans * 10 + tmp;   // 将之前的数字*10 + 当前； 这句话特别重要
        }
        return ans;
    }



    // 第二种判断溢出的方法： 每次首先记录ans当前的值，为last; 然后正常进行添加ans = ans * 10 + tmp
    // 此时，如果 没有发生溢出，那么 ans / 10 == last; 如果发生了溢出，那么 ans / 10 != last；
    public int reverse2(int x) {
        int ans = 0;
        while (x != 0) {
            // 特别重要，判断溢出
            int tmp = x % 10;  // 计算出 当前的最后一位数字
            x /= 10;           //去除当前已经计算出的最后一位
            int last = ans;
            ans = ans * 10 + tmp; // 将之前的数字*10 + 当前； 这句话特别重要
            if (ans / 10 != last) {
                return 0;
            }
        }
        return ans;
    }
}
