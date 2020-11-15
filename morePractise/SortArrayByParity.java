import java.util.Arrays;

public class SortArrayByParity {
    //905. 按奇偶排序数组
    public int[] sortArrayByParity(int[] A) {
        Integer[] B = new Integer[A.length];
        for (int i = 0; i < A.length; i++) {
            B[i] = A[i];
        }
        Arrays.sort(B, (a, b) -> Integer.compare(a%2, b%2));

        for (int i = 0; i < A.length; i++) {
            A[i] = B[i];
        }
        return A;
    }

    public int[] sortArrayByParity_2(int[] A) {
        int ans[] = new int[A.length];
        int idx = 0;
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 0)
                ans[idx++] = A[i];
        }
        for (int i = 0; i < A.length; i++) {
            if (A[i] % 2 == 1)
                ans[idx++] = A[i];
        }
        return ans;
    }

    public int[] sortArrayByParity_3(int[] A) {
        int i = 0, j = A.length - 1;
        while (i < j) {
            if (A[i] % 2 > A[j] % 2) { //A[i] % 2 = 1; A[j] %2 = 0;
                int tmp = A[i];
                A[i] = A[j];
                A[j] = tmp;
                //i++; j--;
            } 
            // else if (A[i] % 2 < A[j] % 2) {
            //     i++; j--;
            // } else {
            //     if (A[i] % 2 == 0) i++;
            //     if (A[j] % 2 == 1) j--;
            // }
            //(0, 1) - i, j 位置都正确, 则i++；j--
            //(0, 0) - i位置正确，i++，j不动
            //(1, 1) - j位置正确，j--，i不动
            if (A[i] % 2 == 0) i++;
            if (A[j] % 2 == 1) j--;
        }
        return A;
    }

    //922. 按奇偶排序数组 II
    public int[] sortArrayByParityII(int[] A) {
        int[] ans = new int[A.length];

        int i = 0;
        for (int a : A) {
            if (a % 2 == 0) {
                ans[i] = a;
                i += 2;
            }
        }
        i = 1;
        for (int a : A) {
            if (a % 2 == 1) {
                ans[i] = a;
                i += 2;
            }
        }
        return ans;
    }

    public static void main(String args[]) {
        SortArrayByParity sByParty = new SortArrayByParity();
        int[] A = new int[] {1, 3, 2, 5};
        int ans[] = sByParty.sortArrayByParity_3(A);
        System.out.println(Arrays.toString(ans));
    }
}
