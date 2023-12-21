package com.lg.example.function;

public class Array {
    public static void main(String[] args) {
        int arr1[] = new int[]{1, 2, 3, 4, 5};
        int arr2[] = new int[]{7, 8, 9, 10, 11};

        for (int i = 0; i < 5; i++) {
            System.out.print(arr1[i]);
        }
        System.out.println();

        for (int i = 0; i < 5; i++) {
            System.out.print(arr2[i]);
        }
        System.out.println();

        System.arraycopy(arr2, 0, arr1, 0, 3);

        /*
        src: the source array.
                src ：源数组。
        srcPos: source array index from where copy will start
        srcPos ：从其开始复制的源数组索引
        dest: the destination array.
                dest ：目标数组。
        destPos: destination array index from where data will be copied.
                destPos ：从中复制数据的目标数组索引。
        length: the number of elements to copy.
        length ：要复制的元素数。
         */

        for (int i = 0; i < 5; i++) {
            System.out.print(arr1[i]);
        }


    }
}
