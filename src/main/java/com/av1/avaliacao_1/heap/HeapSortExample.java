package com.av1.avaliacao_1.heap;

import java.util.Random;

public class HeapSortExample {

    // Função para gerar array aleatório
    public static int[] gerarArray(int tamanho, int limite) {
        Random random = new Random();
        int[] arr = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            arr[i] = random.nextInt(limite); // números entre 0 e limite-1
        }
        return arr;
    }

    // ---------- HEAPSORT ----------
    public static void sort(int[] arr) {
        int n = arr.length;

        // Constrói o heap máximo
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        // Extrai elementos do heap um por um
        for (int i = n - 1; i > 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    private static void heapify(int[] arr, int n, int i) {
        int maior = i;
        int esq = 2 * i + 1;
        int dir = 2 * i + 2;

        if (esq < n && arr[esq] > arr[maior]) {
            maior = esq;
        }
        if (dir < n && arr[dir] > arr[maior]) {
            maior = dir;
        }

        if (maior != i) {
            int troca = arr[i];
            arr[i] = arr[maior];
            arr[maior] = troca;

            heapify(arr, n, maior);
        }
    }
}
