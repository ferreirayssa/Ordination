package com.av1.avaliacao_1;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Grafic extends JFrame {

    private static final String RESULTS_CSV = "src/main/resources/static/results.csv";
    private JFreeChart chart;

    public Grafic() {
        super("Comparação de Algoritmos de Ordenação");

        // 1. Descobre os datasets disponíveis a partir do CSV
        Set<String> availableDatasets = getAvailableDatasets();
        if (availableDatasets.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Não foram encontrados datasets no arquivo: " + RESULTS_CSV + "\nExecute a classe Process primeiro.",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Configura o layout principal da janela
        setLayout(new BorderLayout());

        // 3. Cria o painel de controle com um JComboBox (menu dropdown) para selecionar o dataset
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Selecione o Dataset:"));
        JComboBox<String> datasetSelector = new JComboBox<>(availableDatasets.toArray(new String[0]));
        controlPanel.add(datasetSelector);
        add(controlPanel, BorderLayout.NORTH);

        // 4. Cria o gráfico inicial com o primeiro dataset da lista
        String initialDataset = availableDatasets.iterator().next();
        XYSeriesCollection dataset = createDatasetFor(initialDataset);
        this.chart = ChartFactory.createXYLineChart(
                "Comparação de Desempenho - " + initialDataset,
                "Tamanho do vetor (n)",
                "Tempo (ms)",
                dataset);
        customizeChart(this.chart, dataset);
        ChartPanel panel = new ChartPanel(chart);
        add(panel, BorderLayout.CENTER);

        // 5. Adiciona um "ouvinte" para atualizar o gráfico quando um novo item for selecionado
        datasetSelector.addActionListener(e -> {
            String selectedDataset = (String) datasetSelector.getSelectedItem();
            if (selectedDataset != null) {
                updateChart(selectedDataset);
            }
        });
    }

    /**
     * Atualiza o gráfico com os dados do dataset recém-selecionado.
     * @param datasetName O nome do dataset a ser exibido.
     */
    private void updateChart(String datasetName) {
        XYSeriesCollection newDataset = createDatasetFor(datasetName);
        chart.getXYPlot().setDataset(newDataset);
        chart.setTitle("Comparação de Desempenho - " + datasetName);
        customizeChart(chart, newDataset);
    }

    /**
     * Aplica estilos customizados (cores e espessura das linhas) ao gráfico.
     * @param chart O gráfico a ser customizado.
     * @param dataset O conjunto de dados usado para mapear séries para estilos.
     */
    private void customizeChart(JFreeChart chart, XYSeriesCollection dataset) {
        XYPlot plot = chart.getXYPlot();
        var renderer = (XYLineAndShapeRenderer) plot.getRenderer();

        // Define uma espessura de linha maior para melhor visualização
        BasicStroke thickStroke = new BasicStroke(2.5f);

        // Define um mapa de cores para garantir consistência entre os algoritmos
        Map<String, Color> colorMap = Map.of(
            "Bubble",    new Color(227, 26, 28),    // Vermelho
            "Insertion", new Color(255, 127, 0),   // Laranja
            "Selection", new Color(253, 191, 111), // Amarelo-Laranja
            "Merge",     new Color(31, 120, 180),   // Azul
            "Heap",      new Color(106, 61, 154),   // Roxo
            "Quick",     new Color(51, 160, 44)    // Verde
        );

        // Itera sobre as séries presentes no gráfico e aplica os estilos
        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            String seriesName = (String) dataset.getSeriesKey(i);
            renderer.setSeriesStroke(i, thickStroke);
            renderer.setSeriesPaint(i, colorMap.getOrDefault(seriesName, Color.BLACK));
            // Desativa as formas (pontos) para deixar o gráfico mais limpo
            renderer.setSeriesShapesVisible(i, false);
        }
    }

    /**
     * Lê o arquivo CSV para encontrar todos os nomes de datasets únicos.
     * @return Um conjunto (Set) com os nomes dos datasets.
     */
    private Set<String> getAvailableDatasets() {
        Set<String> datasets = new LinkedHashSet<>(); // Usa LinkedHashSet para manter a ordem de inserção
        if (!Files.exists(Paths.get(RESULTS_CSV))) {
            return datasets; // Retorna conjunto vazio, o construtor tratará a mensagem de erro
        }
        try (BufferedReader br = new BufferedReader(new FileReader(RESULTS_CSV))) {
            br.readLine(); // Pula o cabeçalho
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length > 1) {
                    datasets.add(parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao ler os datasets do arquivo CSV.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        return datasets;
    }

    /**
     * Cria uma coleção de dados para o gráfico, lendo o CSV e filtrando por um dataset específico.
     * @param datasetToChart O nome do dataset para filtrar.
     * @return Uma XYSeriesCollection contendo os dados para o gráfico.
     */
    private XYSeriesCollection createDatasetFor(String datasetToChart) {
        XYSeries bubbleSeries = new XYSeries("Bubble");
        XYSeries insertionSeries = new XYSeries("Insertion");
        XYSeries selectionSeries = new XYSeries("Selection");
        XYSeries mergeSeries = new XYSeries("Merge");
        XYSeries quickSeries = new XYSeries("Quick");
        XYSeries heapSeries = new XYSeries("Heap");
        
        try (BufferedReader br = new BufferedReader(new FileReader(RESULTS_CSV))) {
            br.readLine(); // Pula o cabeçalho
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] parts = line.split(",", -1);
                // Filtra pelo dataset selecionado e garante que o teste foi bem-sucedido ("OK")
                if (!parts[1].trim().equals(datasetToChart) || !"OK".equals(parts[4].trim())) {
                    continue;
                }
                int size = Integer.parseInt(parts[0].trim());
                String algorithm = parts[2].trim();
                double timeMs = Double.parseDouble(parts[3].trim());
    
                switch (algorithm) {
                    case "Bubble":    bubbleSeries.add(size, timeMs); break;
                    case "Insertion": insertionSeries.add(size, timeMs); break;
                    case "Selection": selectionSeries.add(size, timeMs); break;
                    case "Merge":     mergeSeries.add(size, timeMs); break;
                    case "Quick":     quickSeries.add(size, timeMs); break;
                    case "Heap":      heapSeries.add(size, timeMs); break;
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao ler os dados do arquivo de resultados.", "Erro", JOptionPane.ERROR_MESSAGE);
            return new XYSeriesCollection(); // Retorna um dataset vazio em caso de erro
        }
    
        // Adiciona apenas as séries que contêm dados para não poluir o gráfico
        XYSeriesCollection dataset = new XYSeriesCollection();
        if (!bubbleSeries.isEmpty()) dataset.addSeries(bubbleSeries);
        if (!insertionSeries.isEmpty()) dataset.addSeries(insertionSeries);
        if (!selectionSeries.isEmpty()) dataset.addSeries(selectionSeries);
        if (!mergeSeries.isEmpty()) dataset.addSeries(mergeSeries);
        if (!quickSeries.isEmpty()) dataset.addSeries(quickSeries);
        if (!heapSeries.isEmpty()) dataset.addSeries(heapSeries);
    
        return dataset;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Grafic example = new Grafic();
            // Se o construtor retornou cedo por erro, não mostra uma janela vazia
            if (example.getContentPane().getComponentCount() == 0) {
                return;
            }
            example.setSize(800, 600);
            example.setLocationRelativeTo(null);
            example.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            example.setVisible(true);
        });
    }
}
