public class ExerciseW8 {
    //896. 单调数列
    public boolean isMonotonic(int[] A) {
        if (A == null || A.length == 0) return false;
        int len = A.length;
        if (len < 3) return true;

        return isIncrease(A) || isDecrease(A);
    }

    private boolean isIncrease(int[] A) {
        for (int i = 1; i < A.length; i++) {
            if (A[i-1] > A[i]) return false;
        }
        return true;
    }

    private boolean isDecrease(int[] A) {
        for (int i = 1; i < A.length; i++) {
            if (A[i-1] < A[i]) return false;
        }
        return true;
    }

    public boolean isMonotonic_2(int[] A) {
        if (A == null || A.length == 0) return false;
        int len = A.length;
        if (len < 3) return true;
        //先判断整体趋势
        boolean check = A[0] > A[len - 1];
        for (int i = 1; i < len; i++) {
            if (A[i-1] == A[i]) continue;
            if (A[i-1] > A[i] != check) return false;
        }
        return true;
    }

    //796. 旋转字符串
    public boolean rotateString_2(String A, String B) {
        if (A.equals(B)) return true;
        if (A.length() != B.length()) return false;
        int len = A.length();

        for (int i = 0; i < len; i++) {
            String s1 = A.substring(0, i);
            String s2 = A.substring(i, len);
            if (B.equals(s2 + s1)) return true;
        }
        return false;
    }
    public boolean rotateString(String A, String B) {
        if (A.equals(B)) return true;
        if (A.length() != B.length()) return false;

        return (A + A).contains(B);
    }
    //KMP
}
