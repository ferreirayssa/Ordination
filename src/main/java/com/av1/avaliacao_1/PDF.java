package com.av1.avaliacao_1;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class PDF {

    private static final String RESULTS_CSV = "src/main/resources/static/results.csv";
    private static final String REPORTS_DIR = "src/main/resources";

    public static void main(String[] args) {
        System.out.println("Iniciando a geração do relatório PDF a partir de " + RESULTS_CSV + "...");

        try {
            // Passo 1: Verificar se o arquivo CSV de resultados existe.
            if (!Files.exists(Paths.get(RESULTS_CSV))) {
                System.err.println("Arquivo de resultados não encontrado: " + RESULTS_CSV);
                System.err.println("Por favor, execute a classe Process primeiro para gerar os resultados.");
                return;
            }

            // Passo 2: Ler e organizar os dados do CSV.
            // Estrutura: Dataset -> Tamanho -> Algoritmo -> Resultado (Tempo ou Status)
            Map<String, Map<Integer, Map<String, String>>> allResults = new TreeMap<>();

            try (BufferedReader br = new BufferedReader(new FileReader(RESULTS_CSV))) {
                String line = br.readLine(); // Pular cabeçalho
                while ((line = br.readLine()) != null) {
                    if (line.isBlank()) continue;
                    String[] parts = line.split(",", -1);

                    int size = Integer.parseInt(parts[0].trim());
                    String datasetName = parts[1].trim();
                    String algorithm = parts[2].trim();
                    String timeMs = parts[3].trim();
                    String status = parts[4].trim();

                    String resultValue = "OK".equals(status) ? timeMs : status;

                    allResults.putIfAbsent(datasetName, new TreeMap<>());
                    Map<Integer, Map<String, String>> datasetResults = allResults.get(datasetName);

                    datasetResults.putIfAbsent(size, new HashMap<>());
                    datasetResults.get(size).put(algorithm, resultValue);
                }
            }

            if (allResults.isEmpty()) {
                System.err.println("Nenhum resultado encontrado no arquivo " + RESULTS_CSV);
                return;
            }

            // Passo 3: Gerar o documento PDF.
            Files.createDirectories(Paths.get(REPORTS_DIR));
            String outputPath = REPORTS_DIR + File.separator + "Resultados.pdf";

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font datasetFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

            document.add(new Paragraph("Relatório de Algoritmos de Ordenação", titleFont));
            document.add(new Paragraph("Resultados completos baseados no arquivo: " + RESULTS_CSV));
            document.add(new Paragraph(" "));

            String[] algorithmsInOrder = {"Bubble", "Insertion", "Selection", "Merge", "Heap", "Quick"};

            // Passo 4: Criar uma tabela para cada dataset.
            for (Map.Entry<String, Map<Integer, Map<String, String>>> datasetEntry : allResults.entrySet()) {
                String datasetName = datasetEntry.getKey();
                Map<Integer, Map<String, String>> resultsForDataset = datasetEntry.getValue();

                document.add(new Paragraph("Resultados para o Dataset: " + datasetName, datasetFont));
                document.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(algorithmsInOrder.length + 1);
                table.setWidthPercentage(100);
                table.setSpacingAfter(20f); // Espaço depois da tabela

                // Cabeçalho da Tabela
                table.addCell("n");
                for (String algo : algorithmsInOrder) {
                    table.addCell(algo + "Sort (ms)");
                }

                // Corpo da Tabela
                for (Map.Entry<Integer, Map<String, String>> sizeEntry : resultsForDataset.entrySet()) {
                    Integer size = sizeEntry.getKey();
                    Map<String, String> algoResults = sizeEntry.getValue();

                    table.addCell(String.valueOf(size));
                    for (String algo : algorithmsInOrder) {
                        String value = algoResults.getOrDefault(algo, "N/A");
                        table.addCell(value);
                    }
                }
                document.add(table);
            }

            // ---------- Conclusão ----------
            document.add(new Paragraph("Conclusão:", titleFont));
            document.add(new Paragraph(
                    "A análise dos resultados confirma a superioridade de desempenho dos algoritmos de complexidade O(n log n) " +
                            "(MergeSort, HeapSort, QuickSort) em comparação com os algoritmos de complexidade O(n^2) " +
                            "(BubbleSort, InsertionSort, SelectionSort), especialmente para grandes volumes de dados. " +
                            "As diferenças de desempenho entre os datasets (crescente, decrescente, aleatório) também são notáveis, " +
                            "afetando principalmente o QuickSort (pior caso em dados ordenados) e o InsertionSort (melhor caso em dados quase ordenados)."));

            document.close();
            System.out.println("Relatório PDF completo gerado com sucesso em: " + outputPath);

        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao gerar o relatório PDF:");
            e.printStackTrace();
        }
    }
}
