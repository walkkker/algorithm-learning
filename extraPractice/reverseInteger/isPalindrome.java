package extraPractice.reverseInteger;

public class isPalindrome {

    // 仅使用一半的数字 进行判断回文； 需要特殊判断 10的倍数的数字，且注意 0 的问题
    public boolean isPalindrome(int x) {
        // 10的倍数进行判断（其实就是 最后一位不能为0，为0必须特殊处理成false），比如说 2200， 10
        // ！！！很重要： 就是说，结尾部分为0的情况下，他不影响左侧部分（最后一位为非0的左侧部分）的回文，后面求得 返回答案就会针对 左侧是否回文进行返回。（左侧回文的话，下面的判断就会返回true）  但是！！！，结尾为0必是false，所以一定要提前特殊情况处理。
        if ((x != 0 && x % 10 == 0) || x < 0) {
            return false;
        }
        int ans = 0;
        while (ans < x) {
            int tmp = x % 10;
            x /= 10;
            ans = ans * 10 + tmp;
        }

        return x == ans || x == ans / 10;
    }


    // 次优法： 全部反转，然后比较两者是否相同。 注意条件判断是否溢出的位置
    public boolean isPalindrome2(int x) {
        if (x < 0) {
            return false;
        }
        int ans = 0;
        int copy = x;
        // 整数反转的代码
        while (copy != 0) {
            // 一开始这个位置放在了 2 位置，放错了。 放在该位置用以检查每次进入循环时的ans 是否不会溢出，若不会溢出，则可进行后续加法。
            if (ans > Integer.MAX_VALUE / 10 || ans < Integer.MIN_VALUE / 10) {  // 此时 x 的逆序为溢出。 X存在，而逆序溢出，所以X！=逆序
                return false;
            }
            int tmp = copy % 10;
            copy /= 10;
            ans = ans * 10 + tmp;
            // 2
            // 2 位置的错误在于，如果最后一次的数字没有溢出，但是也满位数了，那么就会出错。实际不要这种情况。
        }
        return x == ans;
    }
}
