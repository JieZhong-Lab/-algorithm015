public class Sort {
    //选择排序
    public void selectionSort(int[] arr) {
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) { //每次循环后将第i小的元素放好
            int min_idx = i;
            //用来记录第i个到第len-1个元素中，最小的那个元素的下标
            for (int j = i; j < len; j++) {
                if (arr[j] < arr[min_idx]) min_idx = j;
            }
            //将第i小的元素放在第i个位子上，并将原来占着第i个位置的元素挪到后面
            int tmp = arr[i];
            arr[i] = arr[min_idx];
            arr[min_idx] = tmp;
        }
    }
    //插入排序
    public void insertionSort(int[] arr) {
        int size = arr.length;
        for (int i = 1; i < size; i++) {
            //arr[i]最左的无序元素，每次
            int curr = arr[i];
            //循环前面的有序元素，将arr[i]放到合适的位置
            int j = i - 1;
            //arr[i] 和前面的有序部分[0, i - 1]比较, 放到维持有序的位置
            while (j >= 0 && arr[j] > curr) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = curr;
            // for (int j = 0; j < i; i++) {
            //     if (arr[i] < arr[j]) {
            //         //arr[i]放到位置j，原下标j到i-1的元素都往后移一个位子
            //         int tmp = arr[i];
            //         for (int k = i; k > j; k--) arr[k] = arr[k-1];
            //         arr[j] = tmp;
            //         break;
            //     }
            // }
        }
    }
    //冒泡排序
    public void bubbleSort(int[] arr) {
        int size = arr.length;
        for (int i = size - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
    }

    //归并排序
    public void mergeSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        mergeSort(arr, 0, arr.length - 1);
    }
    public void mergeSort(int[] arr, int s, int e) {
        if (s >= e) return;

        int mid = s + (e - s)/2;
        mergeSort(arr, s, mid);
        mergeSort(arr, mid + 1, e);
        merge(arr, s, mid, e);
    }

    private void merge(int[] arr, int s, int mid, int e) {
        int[] tmp = new int[e - s + 1];
        int p = 0, p1 = s, p2 = mid + 1;
        while (p1 <= mid && p2 <= e) {
            tmp[p++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1 <= mid) {
            tmp[p++] = arr[p1++];
        }
        while (p2 <= e) {
            tmp[p++] = arr[p2++];
        }

        for (int i = 0; i < e - s + 1; i++) {
            arr[s + i] = tmp[i];
        }
    }

    //快速排序
    public void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(int[] arr, int s, int e) {
        if (s >= e) return;
        /**
         * 从第K个元素开始分，把K挪到适当的位置，使得比K小的元素都在K左边，比K大的元素都在K右边
         * 把K左边的部分快速排序
         * 把K右边的部分快速排序
         */
        int K = arr[s]; 
        int i = s, j = e;
        int tmp = 0;
        while (i != j) {
            while (j > i && arr[j] >= K) --j;
            //swap
            tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
            while (i < j && arr[i] <= K) ++i;
            //swap
            tmp = arr[i]; arr[i] = arr[j]; arr[j] = tmp;
        } //处理完之后，arr[i] == K
        quickSort(arr, s, i - 1);
        quickSort(arr, i + 1, e);
    }

    public static void main(String args[]) {
        Sort sort = new Sort();
        int[] arr = new int[] {3, 1, 4, 2, 0};
        sort.bubbleSort(arr);

        for (int n : arr) System.out.print(n + ",");
    }
}
