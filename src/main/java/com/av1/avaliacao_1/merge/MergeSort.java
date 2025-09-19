package com.av1.avaliacao_1.merge;

public class MergeSort {
    public static void sort(int[] a) {
        int[] aux = new int[a.length];
        sortRec(a, aux, 0, a.length - 1);
    }

    private static void sortRec(int[] a, int[] aux, int l, int r) {
        if (l >= r) return;
        int m = (l + r) >>> 1;
        sortRec(a, aux, l, m);
        sortRec(a, aux, m + 1, r);
        if (a[m] <= a[m + 1]) return;
        merge(a, aux, l, m, r);
    }

    private static void merge(int[] a, int[] aux, int l, int m, int r) {
        for (int i = l; i <= r; i++) aux[i] = a[i];
        int i = l, j = m + 1, k = l;
        while (i <= m && j <= r) a[k++] = (aux[i] <= aux[j]) ? aux[i++] : aux[j++];
        while (i <= m) a[k++] = aux[i++];
       
    }
}
