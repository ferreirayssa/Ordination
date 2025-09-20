# Análise de Algoritmos de Ordenação

Este projeto é uma ferramenta de benchmark para analisar e comparar o desempenho de diferentes algoritmos de ordenação em Java. Ele executa testes com vários tipos e tamanhos de vetores, visualiza os resultados em um gráfico interativo e gera um relatório em PDF.

---

## 💻 Tecnologias Utilizadas

* **Java**: Linguagem de programação principal.
* **JFreeChart**: Biblioteca para a geração dos gráficos de linha.
* **Swing**: Toolkit para a criação da interface gráfica.
* **iText**: Biblioteca para a criação e manipulação de documentos PDF.

---

## ⚙️ Como Funciona

O projeto é composto por três classes principais, cada uma com uma função específica:

1.  **`Processo.java`**:
    * Esta classe é o coração do projeto. Ela lê um plano de execução de testes a partir do arquivo `processo.csv`.
    * Executa os algoritmos de ordenação e salva todos os resultados no arquivo `resultados.csv`.

2.  **`Gráfico.java`**:
    * Esta classe é a interface de visualização. Ela lê os dados do `resultados.csv` gerado pelo `Processo.java`.
    * Utiliza a biblioteca JFreeChart para criar um gráfico de linhas dinâmico.
    * A interface permite ao usuário selecionar o tipo de dataset (ex: "aleatorio_com_rep") para visualizar a performance dos algoritmos nesse cenário específico.

3.  **`PDF.java`**:
    * Esta classe automatiza a geração de um relatório.
    * Lê os mesmos dados do `resultados.csv` e organiza-os em tabelas formatadas.
    * Gera um documento PDF (`Resultados.pdf`) que resume o desempenho de todos os algoritmos para cada dataset.
    * O relatório inclui uma conclusão final que sumariza os achados da análise.

---

## 📊 Algoritmos Analisados

O projeto avalia o desempenho dos seguintes algoritmos de ordenação, cada um implementado em uma classe separada para modularidade e clareza:

* **Bubble Sort**: Um algoritmo de complexidade $O(n^2)$, ideal para fins didáticos.
* **Insertion Sort**: Eficiente para vetores pequenos ou quase ordenados, com complexidade $O(n^2)$ no pior caso.
* **Selection Sort**: Conhecido por seu número fixo de trocas, também com complexidade $O(n^2)$.
* **Merge Sort**: Um algoritmo de divisão e conquista com complexidade $O(n \log n)$ em todos os casos.
* **Quick Sort**: Geralmente o mais rápido na prática, com complexidade média de $O(n \log n)$.
* **Heap Sort**: Um algoritmo de ordenação por comparação com complexidade $O(n \log n)$ garantida.

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para rodar o projeto em sua IDE.

### Pré-requisitos

* JDK 8 ou superior instalado.
* Uma IDE como IntelliJ IDEA, Eclipse ou VS Code.
* As bibliotecas **JFreeChart**, **JCommon** e **iText** (ou `itextpdf`) precisam ser adicionadas ao seu projeto. Se você estiver usando Maven ou Gradle, adicione as dependências apropriadas.

### Passos de Execução

1.  **Clone o repositório** para sua máquina local.
2.  **Abra o projeto em sua IDE** e garanta que as dependências do JFreeChart, JCommon e iText foram resolvidas.
3.  **Execute a classe `Processo.java`** primeiro. Esta etapa é essencial, pois ela cria o arquivo `resultados.csv`, que as outras classes dependem. A execução pode levar alguns minutos.
4.  Para visualizar os resultados, **execute a classe `Gráfico.java`**. Uma janela com o gráfico interativo será aberta.
5.  Para gerar o relatório em PDF, **execute a classe `PDF.java`**. O arquivo `Resultados.pdf` será criado na pasta `recursos`.

---

## 📂 Estrutura de Arquivos

Aqui está o README.md atualizado com a estrutura de arquivos exata que você forneceu na imagem.

README.md
Markdown

# Análise de Algoritmos de Ordenação

Este projeto é uma ferramenta de benchmark para analisar e comparar o desempenho de diferentes algoritmos de ordenação em Java. Ele executa testes com vários tipos e tamanhos de vetores, visualiza os resultados em um gráfico interativo e gera um relatório em PDF.

---

## 💻 Tecnologias Utilizadas

* **Java**: Linguagem de programação principal.
* **JFreeChart**: Biblioteca para a geração dos gráficos de linha.
* **Swing**: Toolkit para a criação da interface gráfica.
* **iText**: Biblioteca para a criação e manipulação de documentos PDF.

---

## ⚙️ Como Funciona

O projeto é composto por três classes principais, cada uma com uma função específica:

1.  **`Processo.java`**:
    * Esta classe é o coração do projeto. Ela lê um plano de execução de testes a partir do arquivo `processo.csv`.
    * Executa os algoritmos de ordenação e salva todos os resultados no arquivo `resultados.csv`.

2.  **`Gráfico.java`**:
    * Esta classe é a interface de visualização. Ela lê os dados do `resultados.csv` gerado pelo `Processo.java`.
    * Utiliza a biblioteca JFreeChart para criar um gráfico de linhas dinâmico.
    * A interface permite ao usuário selecionar o tipo de dataset (ex: "aleatorio_com_rep") para visualizar a performance dos algoritmos nesse cenário específico.

3.  **`PDF.java`**:
    * Esta classe automatiza a geração de um relatório.
    * Lê os mesmos dados do `resultados.csv` e organiza-os em tabelas formatadas.
    * Gera um documento PDF (`Resultados.pdf`) que resume o desempenho de todos os algoritmos para cada dataset.
    * O relatório inclui uma conclusão final que sumariza os achados da análise.

---

## 📊 Algoritmos Analisados

O projeto avalia o desempenho dos seguintes algoritmos de ordenação, cada um implementado em uma classe separada para modularidade e clareza:

* **Bubble Sort**: Um algoritmo de complexidade $O(n^2)$, ideal para fins didáticos.
* **Insertion Sort**: Eficiente para vetores pequenos ou quase ordenados, com complexidade $O(n^2)$ no pior caso.
* **Selection Sort**: Conhecido por seu número fixo de trocas, também com complexidade $O(n^2)$.
* **Merge Sort**: Um algoritmo de divisão e conquista com complexidade $O(n \log n)$ em todos os casos.
* **Quick Sort**: Geralmente o mais rápido na prática, com complexidade média de $O(n \log n)$.
* **Heap Sort**: Um algoritmo de ordenação por comparação com complexidade $O(n \log n)$ garantida.

---

## 🚀 Como Executar o Projeto

Siga os passos abaixo para rodar o projeto em sua IDE.

### Pré-requisitos

* JDK 8 ou superior instalado.
* Uma IDE como IntelliJ IDEA, Eclipse ou VS Code.
* As bibliotecas **JFreeChart**, **JCommon** e **iText** (ou `itextpdf`) precisam ser adicionadas ao seu projeto. Se você estiver usando Maven ou Gradle, adicione as dependências apropriadas.

### Passos de Execução

1.  **Clone o repositório** para sua máquina local.
2.  **Abra o projeto em sua IDE** e garanta que as dependências do JFreeChart, JCommon e iText foram resolvidas.
3.  **Execute a classe `Processo.java`** primeiro. Esta etapa é essencial, pois ela cria o arquivo `resultados.csv`, que as outras classes dependem. A execução pode levar alguns minutos.
4.  Para visualizar os resultados, **execute a classe `Gráfico.java`**. Uma janela com o gráfico interativo será aberta.
5.  Para gerar o relatório em PDF, **execute a classe `PDF.java`**. O arquivo `Resultados.pdf` será criado na pasta `recursos`.

---

## 📂 Estrutura de Arquivos
.
├── pom.xml
├── origem/principal/
│   ├── java/com/av1/avaliacao_1/
│   │   ├── Avaliacao1Application.java
│   │   ├── Gráfico.java
│   │   ├── PDF.java
│   │   ├── RelatorioPdf.java
│   │   └── Processo.java
│   ├── mesclar/
│   │   └── MergeSort.java
│   └── recursos/
│       ├── Resultados.pdf (Gerado após a execução de PDF.java)
│       └── estatico/
│           ├── benchmark_plan.csv
│           ├── processo.csv
│           └── resultados.csv (Gerado após a execução de Processo.java)
└── README.md
