import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.List;

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

    //1356. 根据数字二进制下 1 的数目排序
    public int[] sortByBits(int[] arr) {
        if (arr == null || arr.length < 2) return arr;
        List<Integer> list = new ArrayList<>();
        for (int n : arr) list.add(n);
        Collections.sort(list, new Comparator<Integer>(){
            @Override
            public int compare(Integer n1, Integer n2) {
                int cnt1 = Integer.bitCount(n1), cnt2 = Integer.bitCount(n2);
                return cnt1 == cnt2 ? n1 - n2 : cnt1 - cnt2; 
            }
        });
        
        System.out.println(Integer.bitCount(1024));
        for (int i = 0; i < arr.length; i++) 
            arr[i] = list.get(i);
        return arr;
    }

    public int[] sortByBits_2(int[] arr) {
        if (arr == null || arr.length < 2) return arr;
        int len = arr.length;
        int[] myArr = new int[len];
        for (int i = 0; i < len; i++) {
            myArr[i] = Integer.bitCount(arr[i]) * 1000000 + arr[i];
        }
        Arrays.sort(myArr);
        for (int i = 0; i < len; i++) {
            myArr[i] = myArr[i] % 1000000;
        }
        return myArr;
    }
    
    
    public static void main(String args[]) {
        ExerciseBitOperator ebit = new ExerciseBitOperator();
        ebit.isPowerOfTwo(-2147483648);
    }
}
