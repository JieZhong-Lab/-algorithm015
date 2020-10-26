public class ExerciseBitOperator {
    // 191. 位1的个数
    // you need to treat n as an unsigned value
    public int hammingWeight_2(int n) {
        int count = 0;
        while (n != 0) { //n可能时负数
            count++;
            n = n & (n - 1); //清零最低位的1
        }
        return count;
    }

    public int hammingWeight(int n) {
        int mask = 1;
        int count = 0;
        for (int i =0; i < 32; i++) {
            if ((n & mask) != 0) count++; //说明mask的位置是1
            mask <<= 1;
        }
        return count;
    }

    //231. 2的幂
    public boolean isPowerOfTwo(int n) {
        if (n == 0) return false;
        long x = (long)n; //n有可能时负数
        return (x & (x - 1)) == 0; // n & (n - 1) 清除最低位的0
    }

    //190. 颠倒二进制位
    public int reverseBits(int n) {
        int ans = 0;
        for (int i = 0; i < 32; i++) {
            ans = (ans << 1) + (n & 1);
            n >>= 1;
        }
        return ans;
    }

    //338. 比特位计数
    //给定一个非负整数 num。对于 0 <= i <= num 范围中的每个数字 i ，计算其二进制数中的 1 的数目并将它们作为数组返回。
    public int[] countBits(int num) {
        int[] bitsCnt = new int[num + 1];
        for (int i = 1; i <= num; i++) {
            bitsCnt[i] = bitsCnt[i & (i - 1)] + 1;
        }
        return bitsCnt;
    }
    
    public static void main(String args[]) {
        ExerciseBitOperator ebit = new ExerciseBitOperator();
        ebit.isPowerOfTwo(-2147483648);
    }
}
