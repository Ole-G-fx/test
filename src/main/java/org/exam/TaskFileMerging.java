package org.exam;

import org.exam.sortStreaming.SortingMerge;

/**
 * Задача файловое слияние (TaskFileMerging.java)
 *     Класс предназначенный для запуска проекта (точка входа), указывается про сборке проекта
 */
public class TaskFileMerging {

    public static void main(String[] args) {

        SortingMerge sorting = new SortingMerge();
        sorting.start(args);
    }

    public static int removeDuplicates(int[] nums) {

        int k = 0;
        for (int i = 1; i < nums.length; i++)
            if (nums[k] != nums[i])
                nums[++k] = nums[i];
        return k + 1;
    }
}