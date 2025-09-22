Análise de Algoritmos de Ordenação em Java
📝 Resumo do Projeto
Este projeto é uma ferramenta de benchmark completa para analisar e comparar o desempenho de diferentes algoritmos de ordenação em Java. Ele foi concebido para fornecer uma análise objetiva do comportamento desses algoritmos em diversas condições, como diferentes tamanhos de vetores e tipos de dados (ordenados, aleatórios, com repetições, etc.). O sistema automatiza todo o processo: desde a execução dos testes e coleta dos dados até a visualização interativa dos resultados e a geração de relatórios profissionais em PDF.

💻 Tecnologias Utilizadas
Java: A linguagem principal do projeto, escolhida por sua robustez e ampla adoção no desenvolvimento de aplicações de benchmark.

JFreeChart: Uma biblioteca poderosa para a geração de gráficos de linha, permitindo a criação de visualizações interativas e de alta qualidade dos dados de desempenho.

Swing: O toolkit padrão do Java para a criação de interfaces gráficas (GUI). É utilizado para construir a janela interativa onde os gráficos são exibidos.

iText: Uma biblioteca robusta para a criação e manipulação de documentos PDF. Essencial para automatizar a geração de relatórios de fácil compartilhamento.

⚙️ Estrutura e Funcionamento
O projeto é modular e dividido em classes, cada uma com uma responsabilidade bem definida. Essa abordagem facilita a manutenção e a expansão futura do código.

Processo.java
Esta classe é o coração do sistema. Ela gerencia toda a lógica de execução e coleta de dados.

Leitura do Plano de Execução: Lê um plano de testes detalhado a partir do arquivo processo.csv. Este arquivo define os parâmetros para cada teste, como o algoritmo a ser usado, o tamanho do vetor e o tipo de dados (aleatorio, quase_ordenado, etc.).

Geração e Execução: Para cada cenário definido no plano, a classe gera um vetor com as características solicitadas e, em seguida, executa o algoritmo de ordenação correspondente.

Coleta de Métricas: Durante a execução, o tempo de processamento é medido com alta precisão.

Gravação dos Resultados: Todos os resultados dos testes (algoritmo, tipo de vetor, tamanho do vetor, tempo de execução, etc.) são salvos de forma organizada no arquivo resultados.csv, que serve como a fonte de dados para as classes de visualização e relatório.

Grafico.java
Esta classe é a interface de visualização interativa.

Leitura de Dados: Lê os dados brutos de desempenho do arquivo resultados.csv gerado pela classe Processo.java.

Criação da Interface Gráfica: Utiliza a biblioteca Swing para criar uma janela com menus interativos.

Visualização Dinâmica: Com o JFreeChart, a classe cria um gráfico de linhas que permite ao usuário selecionar o tipo de conjunto de dados (ex: "aleatório_com_rep" ou "quase_ordenado") para visualizar e comparar o desempenho dos algoritmos especificamente naquele cenário. Essa interatividade é crucial para a análise aprofundada.

PDF.java
Esta classe automatiza a geração do relatório final.

Consolidação de Dados: Lê os mesmos dados do arquivo resultados.csv e os organiza de forma estruturada.

Criação de Tabelas: Utiliza a biblioteca iText para formatar os dados em tabelas claras e fáceis de ler. Cada tabela representa um cenário de teste (por exemplo, "Vetor Aleatório de 1000 elementos").

Resumo e Conclusão: Além das tabelas, o relatório inclui uma conclusão final que resume as principais observações da análise, como quais algoritmos se saíram melhor em determinados cenários e por quê.

Exportação: O relatório é salvo como um documento PDF chamado Resultados.pdf na pasta recursos, pronto para ser compartilhado ou arquivado.

📊 Algoritmos Analisados
O projeto avalia o desempenho dos seguintes algoritmos de ordenação, cada um implementado em uma classe separada para garantir modularidade e clareza no código.

Bubble Sort: Um algoritmo de complexidade O(n 
2
 ). Embora não seja eficiente para grandes conjuntos de dados, é ideal para fins didáticos.

Insertion Sort: Eficiente para vetores pequenos ou quase ordenados, com complexidade O(n 
2
 ) no pior caso.

Selection Sort: Conhecido por seu número fixo de trocas, também com complexidade O(n 
2
 ).

Merge Sort: Um algoritmo de divisão e conquista com complexidade O(nlogn) em todos os casos (pior, médio e melhor), tornando-o uma escolha muito estável.

Quick Sort: Geralmente o mais rápido na prática, com complexidade média de O(nlogn). Contudo, seu pior caso é O(n 
2
 ), embora seja raro.

Heap Sort: Um algoritmo de ordenação por comparação com complexidade O(nlogn) garantida em todos os casos, sendo uma alternativa robusta ao Quick Sort.

🚀 Como Executar o Projeto
Siga os passos abaixo para rodar o projeto em seu ambiente de desenvolvimento (IDE).

Pré-requisitos
JDK 8 ou superior: Certifique-se de ter o Java Development Kit instalado e configurado corretamente.

IDE: Uma IDE como IntelliJ IDEA, Eclipse ou VS Code com suporte a Java.

Bibliotecas: As bibliotecas JFreeChart, JCommon e iText (ou itextpdf) precisam ser adicionadas ao seu projeto.

Adicionando as Dependências
Se você estiver usando um gerenciador de dependências como Maven ou Gradle, adicione as seguintes dependências ao seu arquivo de configuração:

Maven (pom.xml):

XML

<dependency>
    <groupId>org.jfree</groupId>
    <artifactId>jfreechart</artifactId>
    <version>1.5.3</version>
</dependency>
<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
</dependency>
Gradle (build.gradle):

Groovy

dependencies {
    implementation 'org.jfree:jfreechart:1.5.3'
    implementation 'com.itextpdf:itextpdf:5.5.13.3'
}
Se preferir, você pode baixar os arquivos .jar diretamente e adicioná-los manualmente ao seu projeto através das configurações da sua IDE.

Passos de Execução
Clone o repositório para sua máquina local.

Abra o projeto em sua IDE e garanta que todas as dependências foram resolvidas com sucesso.

Execute a classe Processo.java primeiro. Esta etapa é crucial, pois ela cria o arquivo resultados.csv, que as outras classes dependem. A execução pode levar alguns minutos, dependendo do número de testes definidos em processo.csv.

Para visualizar os resultados, execute a classe Grafico.java. Uma nova janela com o gráfico interativo será aberta, permitindo a exploração visual dos dados.

Para gerar o relatório em PDF, execute a classe PDF.java. O arquivo Resultados.pdf será criado na pasta recursos do projeto, contendo um resumo completo da análise.
