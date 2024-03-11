public class CountingSort {
    public static int[] sort(int[] array) {
        int k = 0;
        
        int size = array.length;
        for (int i = 0; i < size; i++) {
            if (array[i] > k) {
                k = array[i];
            }
        }
        
        int[] count = new int[k + 1];
        int[] output = new int[array.length];

        for (int i = 0; i < size; i++) {
            count[array[i]]++;
        }

        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }

        for (int i = size - 1; i >= 0; i--) {
            output[count[array[i]] - 1] = array[i];
            count[array[i]]--;
        }

        return output;
    }
}
