public class CountingSort {
    public static int[] countingSort(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        int[] count = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            count[array[i]]++;
        }

        int z = 0;
        for (int i = 0; i < count.length; i++) {
            while (count[i] > 0) {
                array[z] = i;
                z++;
                count[i]--;
            }
        }
        return array;
    }
}
