package com.av1.avaliacao_1.quick;

import java.util.Arrays;
import java.util.Random;

public class QuickSortExample {


    public static int[] gerarArray(int tamanho, int limite) {
        Random random = new Random();
        int[] arr = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            arr[i] = random.nextInt(limite);
        }
        return arr;
    }

    // ---------- QUICKSORT ----------
    public static void quickSort(int[] arr, int inicio, int fim) {
        if (inicio < fim) {
            int pi = particionar(arr, inicio, fim);

            quickSort(arr, inicio, pi - 1);
            quickSort(arr, pi + 1, fim);
        }
    }

    private static int particionar(int[] arr, int inicio, int fim) {
        int pivo = arr[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {
            if (arr[j] <= pivo) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }

        int temp = arr[i + 1];
        arr[i + 1] = arr[fim];
        arr[fim] = temp;

        return i + 1;
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        int[] arr = gerarArray(10, 100);
        System.out.println("Array original: " + Arrays.toString(arr));

        quickSort(arr, 0, arr.length - 1);
        System.out.println("Ordenado com QuickSort: " + Arrays.toString(arr));
    }
}
