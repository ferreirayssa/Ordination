package com.av1.avaliacao_1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Random;

// seus algoritmos
import com.av1.avaliacao_1.bubble.BubbleSort;
import com.av1.avaliacao_1.insertion.InsertionSort;
import com.av1.avaliacao_1.selection.SelectionSort;
import com.av1.avaliacao_1.merge.MergeSort;
import com.av1.avaliacao_1.quick.QuickSortExample;   // <- usa seu nome
import com.av1.avaliacao_1.heap.HeapSortExample;     // <- usa seu nome

public class BenchmarkExecutor {

    private static final String PLAN_CSV  = "src/main/resources/static/benchmark_plan.csv"; // gerado antes
    private static final String OUT_CSV   = "src/main/resources/static/ench_results.csv";  // resultados
    private static final long   SEED      = 42L;                  // reprodutível

    public static void main(String[] args) throws Exception {
        if (!Files.exists(Paths.get(PLAN_CSV))) {
            System.err.println("Não encontrei " + PLAN_CSV + ". Gere o CSV de plano antes.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(PLAN_CSV));
             BufferedWriter out = new BufferedWriter(new FileWriter(OUT_CSV))) {

            // cabeçalho de saída
            out.write("size,dataset,algorithm,time_ms,status");
            out.newLine();

            String header = br.readLine(); // pula cabeçalho do plano
            if (header == null) {
                System.err.println("CSV de plano vazio.");
                return;
            }

            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 3) continue;

                int size = Integer.parseInt(parts[0].trim());
                String dataset = parts[1].trim();
                String algorithm = parts[2].trim();

                try {
                    int[] base = generateDataset(size, dataset, SEED);
                    int[] arr  = Arrays.copyOf(base, base.length);

                    long t0 = System.nanoTime();
                    runAlgorithm(arr, algorithm);
                    long t1 = System.nanoTime();

                    double ms = (t1 - t0) / 1_000_000.0;
                    out.write(size + "," + dataset + "," + algorithm + ","
                              + String.format(java.util.Locale.US, "%.3f", ms) + ",OK");
                    out.newLine();

                    System.out.printf("OK  | size=%d | ds=%-20s | algo=%-9s | %8.3f ms%n",
                            size, dataset, algorithm, ms);

                } catch (SkipException se) {
                    out.write(size + "," + dataset + "," + algorithm + ",,SKIPPED");
                    out.newLine();
                    System.out.printf("SKP | size=%d | ds=%-20s | algo=%-9s | %s%n",
                            size, dataset, algorithm, se.getMessage());
                } catch (Throwable e) {
                    out.write(size + "," + dataset + "," + algorithm + ",,ERROR");
                    out.newLine();
                    System.out.printf("ERR | size=%d | ds=%-20s | algo=%-9s | %s%n",
                            size, dataset, algorithm, e.toString());
                }
            }
        }

        System.out.println("\nResultados escritos em: " + OUT_CSV);
    }

    // -------------------- geração dos datasets --------------------

    private static int[] generateDataset(int n, String name, long seed) {
        // datasets válidos:
        // "crescente_com_rep", "decrescente_com_rep", "aleatorio_com_rep",
        // "crescente_sem_rep", "decrescente_sem_rep", "aleatorio_sem_rep"
        switch (name) {
            case "crescente_com_rep":   return crescenteComRepeticao(n);
            case "decrescente_com_rep": return decrescenteComRepeticao(n);
            case "aleatorio_com_rep":   return aleatorioComRepeticao(n, new Random(seed));
            case "crescente_sem_rep":   return crescenteSemRepeticao(n);
            case "decrescente_sem_rep": return decrescenteSemRepeticao(n);
            case "aleatorio_sem_rep":   return aleatorioSemRepeticao(n, new Random(seed + 1));
            default: throw new IllegalArgumentException("Dataset inválido: " + name);
        }
    }

    private static int[] crescenteComRepeticao(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i / 2; // repete de 2 em 2
        return a;
    }

    private static int[] decrescenteComRepeticao(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = (n - i) / 2; // repete de 2 em 2
        return a;
    }

    private static int[] aleatorioComRepeticao(int n, Random rnd) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rnd.nextInt(n);
        return a;
    }

    private static int[] crescenteSemRepeticao(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    private static int[] decrescenteSemRepeticao(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = n - 1 - i;
        return a;
    }

    private static int[] aleatorioSemRepeticao(int n, Random rnd) {
        int[] a = crescenteSemRepeticao(n);
        for (int i = n - 1; i > 0; i--) { // Fisher–Yates
            int j = rnd.nextInt(i + 1);
            int t = a[i]; a[i] = a[j]; a[j] = t;
        }
        return a;
    }

    // -------------------- dispatch de algoritmos --------------------

    private static final int N2_SAFE_LIMIT = 200_000; // evita travar com O(n^2)

    private static void runAlgorithm(int[] arr, String algorithm) {
        boolean isN2 = algorithm.equals("Bubble")
                    || algorithm.equals("Insertion")
                    || algorithm.equals("Selection");

        if (isN2 && arr.length > N2_SAFE_LIMIT) {
            throw new SkipException("N > " + N2_SAFE_LIMIT + " para algoritmo O(n^2)");
        }

        switch (algorithm) {
            case "Bubble":    BubbleSort.sort(arr); break;
            case "Insertion": InsertionSort.sort(arr); break;
            case "Selection": SelectionSort.sort(arr); break;
            case "Merge":     MergeSort.sort(arr); break;
            case "Quick":     QuickSortExample.sort(arr); break;  // seus nomes
            case "Heap":      HeapSortExample.sort(arr); break;   // seus nomes
            default: throw new IllegalArgumentException("Algoritmo inválido: " + algorithm);
        }
    }

    // exceção simples para sinalizar "pular"
    private static class SkipException extends RuntimeException {
        SkipException(String msg) { super(msg); }
    }
}
