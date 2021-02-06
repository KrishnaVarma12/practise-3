package ueb;

public class Tools {
    
    public static int containsAt(int[] array, int value) {
        if (array == null) {
            throw new IllegalArgumentException("the given array is null. Please ensure it must be not null");
        }
        int position = -1;
        for (int i = 0; i < array.length && position == -1; i++) {
            if (array[i] == value) {
                position = i;
            }
        }
        return position;
    }


    public static int[] deleteElementAt(int[] array, int idx) {
        if (array == null || (idx < 0 || idx > array.length - 1)) {
            throw new IllegalArgumentException("either given array or position idx is INVALID");
        }
        int position1 = 0;
        int position2 = 0;
        int[] arrayAfterDeletion = new int[array.length - 1];
        while (position1 < array.length) {
            if (position1 != idx) {
                arrayAfterDeletion[position2] = array[position1];
                position2++;
            }
            position1++;
        }
        return arrayAfterDeletion;
    }

   
    public static int[] insertElementAt(int[] array, int idx, int value) {
        if (array == null || (idx < 0 || idx > array.length)) {
            throw new IllegalArgumentException("either given array or value is INVALID");
        }
        int position1 = 0;
        int position2 = 0;
        int[] arrayAfterInsertion = new int[array.length + 1];
        while (position2 <= array.length) {
            if (idx == position2) {
                arrayAfterInsertion[position2] = value;
            } else {
                arrayAfterInsertion[position2] = array[position1];
                position1++;
            }
            position2++;
        }
        return arrayAfterInsertion;
    }

  
    public static int getLengthOfLongestArray(int[][] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("either given array or value is INVALID");
        }
        int longestLength = -1;
        for (int[] a : array) {
            if (a != null && a.length > longestLength) {
                longestLength = a.length;
            }
        }
        return longestLength;
    }
}
