package com.av1.avaliacao_1;

import java.util.*;
import java.util.function.Consumer;

import com.av1.avaliacao_1.bubble.BubbleSort;
//import com.av1.avaliacao_1.heap.HeapSort;
import com.av1.avaliacao_1.insertion.InsertionSort;
import com.av1.avaliacao_1.merge.MergeSort;
//import com.av1.avaliacao_1.quick.QuickSort;
import com.av1.avaliacao_1.selection.SelectionSort;

public class Avaliacao1Application {

    enum Dataset { ALEATORIO, CRESCENTE, DECRESCENTE }

    public static void main(String[] args) {
        // escolha do dataset
        Dataset tipo = Dataset.ALEATORIO; // mude para CRESCENTE ou DECRESCENTE
        boolean repeticao = true;         // true=com repetição, false=sem repetição

        // tamanhos solicitados
        int[] tamanhos = {100_000, 160_000, 220_000, 280_000, 340_000, 400_000,
                          460_000, 520_000, 580_000, 640_000, 700_000};

        System.out.printf("Dataset: %s | repeticao=%s%n", tipo, repeticao);
        System.out.println("Algoritmo        N        Tempo(ms)");
        System.out.println("-----------------------------------");

        for (int n : tamanhos) {
            int[] base = gerarDados(n, tipo, repeticao);

            testar("Bubble",    base, BubbleSort::sort);
            testar("Insertion", base, InsertionSort::sort);
            testar("Selection", base, SelectionSort::sort);
            testar("Merge",     base, MergeSort::sort);
        //    testar("Quick",     base, QuickSort::sort);
    //        testar("Heap",      base, HeapSort::sort);

            System.out.println();
        }
    }

    // ------- geração dos dados (com/sem repetição + tipo) -------
    private static int[] gerarDados(int n, Dataset tipo, boolean repeticao) {
        Random rnd = new Random(42); // use uma seed fixa para reprodutibilidade
        int[] arr = new int[n];

        switch (tipo) {
            case ALEATORIO:
                if (repeticao) {
                    for (int i = 0; i < n; i++) arr[i] = rnd.nextInt(n);
                } else {
                    // permutação de 0..n-1
                    for (int i = 0; i < n; i++) arr[i] = i;
                    // Fisher-Yates
                    for (int i = n - 1; i > 0; i--) {
                        int j = rnd.nextInt(i + 1);
                        int t = arr[i]; arr[i] = arr[j]; arr[j] = t;
                    }
                }
                break;

            case CRESCENTE:
                for (int i = 0; i < n; i++) arr[i] = repeticao ? i / 2 : i;
                break;

            case DECRESCENTE:
                for (int i = 0; i < n; i++) arr[i] = repeticao ? (n - i) / 2 : (n - i);
                break;
        }
        return arr;
    }

    private static void testar(String nome, int[] base, Consumer<int[]> metodo) {
        int[] arr = Arrays.copyOf(base, base.length);
        long ini = System.nanoTime();
        metodo.accept(arr);
        long fim = System.nanoTime();
        double ms = (fim - ini) / 1_000_000.0;
        System.out.printf("%-10s %9d %12.3f%n", nome, arr.length, ms);
    }
}
