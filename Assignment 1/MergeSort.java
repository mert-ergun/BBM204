public class MergeSort {
    public static <T extends Comparable<T>> T[] mergeSort(T[] array) {
        if (array.length > 1) {
            int mid = array.length / 2;
            T[] left = (T[]) new Comparable[mid];
            T[] right = (T[]) new Comparable[array.length - mid];

            for (int i = 0; i < mid; i++) {
                left[i] = array[i];
            }
            for (int i = mid; i < array.length; i++) {
                right[i - mid] = array[i];
            }

            mergeSort(left);
            mergeSort(right);

            int i = 0;
            int j = 0;
            int k = 0;

            while (i < left.length && j < right.length) {
                if (left[i].compareTo(right[j]) < 0) {
                    array[k] = left[i];
                    i++;
                } else {
                    array[k] = right[j];
                    j++;
                }
                k++;
            }

            while (i < left.length) {
                array[k] = left[i];
                i++;
                k++;
            }

            while (j < right.length) {
                array[k] = right[j];
                j++;
                k++;
            }
        }
        return array;
    }
}
