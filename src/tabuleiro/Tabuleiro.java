package tabuleiro;

import casas.*;
import factory.CasasFactory;
import jogadores.Jogador;
import java.util.*;
public class Tabuleiro {
    private Random random;
    private ArrayList<Casa> casas;
    private ArrayList<Jogador> jogadores; // Essa merda não tá sendo usada ; Gui:KKKKKKKKK calma cara
    private List<Jogador>[][] tabuleiroVisual;
    private Scanner scanner = new Scanner(System.in);
    private int totalCasas;
    private int linhas;
    private int colunas;

    public Tabuleiro(int totalCasas) {
        this.random = new Random();
        this.totalCasas = totalCasas;
        this.jogadores = new ArrayList<>();
        this.casas = new ArrayList<>();
        this.colunas = 10;
        this.linhas = totalCasas / 10;
        if (totalCasas % 10 != 0) {
            this.linhas++;
        }
        this.tabuleiroVisual = new List[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiroVisual[i][j] = new ArrayList<>();
            }
        }
        inicializarTabuleiroVisual();
        criarTabNormal(totalCasas);
    }
    public void criarTabNormal(int totalCasas) {
        casas = new ArrayList<>();
        for (int i = 0; i < totalCasas; i++) {
            casas.add(i, CasasFactory.criarCasa(0,i));
        }
    }
    public void atualizarTabuleiroVisual() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiroVisual[i][j].clear();
            }
        }
        for (Jogador jogador : getJogadores()) {
            int pos = jogador.getPosicao();
            if (pos >= totalCasas) {
                pos = totalCasas - 1;
                jogador.setPosicao(pos); // força ficar na última casa
            }
            int linha = pos / colunas;
            int coluna = pos % colunas;
            tabuleiroVisual[linha][coluna].add(jogador);
        }
    }
    public void printTabuleiro() {

        System.out.println("\n--- Tabuleiro Visual ---");
        for (int i = 0; i < casas.size(); i++) {
            if ((i % 10 == 0) && i != 0) {
                System.out.print("\n");
            }
            StringBuilder conteudo = new StringBuilder();
            for (Jogador j : getJogadores()) {
                if (j.getPosicao() == i) {
                    if (conteudo.length() > 0) {
                        conteudo.append(", ");
                    }
                    conteudo.append(j.getCor());
                }
            }
            if (conteudo.length() > 0) {
                System.out.print(i + ".[ " + conteudo + " ]\t");
            } else {
                System.out.print(i + ".[  ]\t");
            }
        }
        legendaJogadores();
    }

    public void legendaJogadores(){
    System.out.println("\nLegenda dos Jogadores:");
        for (Jogador jogador : getJogadores()) {
            System.out.println(jogador.getClass().getSimpleName() + " | " + jogador.getCor() + " | Posição " + jogador.getPosicao());
        }
    }
    public boolean adicionarJogador(Jogador jogador){
        if(this.jogadores.size() < 6){
            this.jogadores.add(jogador);
            return true;
        }
        return false;
    }
    public boolean iniciarJogo() {
        Set<Class<?>> tipos = new HashSet<>();
        for (Jogador j : jogadores) {
            tipos.add(j.getClass());
        }
        return tipos.size() >= 2;
    }
    public void jogarRodada(boolean modoDebug) {
        ArrayList<Jogador> jogadores = getJogadores();

        if (jogadores.isEmpty()) {
            System.out.println("Adicione jogadores para poder jogar uma nova partida");
            return;
        }

        for (Jogador jogador : jogadores) {
            if (jogador.isPularRodada()) {
                jogador.setPularRodada(false);
                System.out.println("O jogador " + jogador.getCor() + " está pulando a rodada");
                continue;
            }

            jogador.setJogadas(jogador.getJogadas() + 1);

            boolean tirouNumIguais = false;
            boolean caiuNaCasaJogaDenovo;
            do {
                int[] dados = null;
                int soma = 0;

                if (modoDebug) {
                    int opc = -1;
                    while (opc < 0) {
                        System.out.println("=============================================");
                        System.out.print("Informe o número de casas que o jogador " + jogador.getCor() + " deve avançar: ");
                        String entrada = scanner.nextLine();
                        try {
                            opc = Integer.parseInt(entrada);
                            if (opc < 0) {
                                System.out.println("Digite um número positivo.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Digite apenas números inteiros.");
                        }
                    }
                    jogador.avancar(opc);
                    if (jogador.getPosicao() >= this.totalCasas) {
                        jogador.setPosicao(this.totalCasas - 1);
                    }

                    if (jogador.getPosicao() >= this.totalCasas - 1) {
                        vencer(jogador, jogadores);
                        return;
                    }
                    casasEspeciais(jogador, jogadores);
                    caiuNaCasaJogaDenovo = jogador.isJogarNovamente();
                    jogador.setJogarNovamente(false);
                } else {
                    System.out.println("=============================================");
                    System.out.println("Turno do jogador: " + jogador.getCor());
                    System.out.println("Pressione ENTER para jogar");
                    scanner.nextLine();
                    dados = jogador.rolarDados(random);
                    soma = dados[0] + dados[1];
                    System.out.println("Dados rolados: " + dados[0] + " + " + dados[1] + " = " + soma);
                    jogador.avancar(soma);
                    if (jogador.getPosicao() >= this.totalCasas) {
                        jogador.setPosicao(this.totalCasas - 1);
                    }

                    if (jogador.getPosicao() >= this.totalCasas - 1) {
                        vencer(jogador, jogadores);
                        return;
                    }
                    casasEspeciais(jogador, jogadores);
                    tirouNumIguais = (dados[0] == dados[1]);
                    caiuNaCasaJogaDenovo = jogador.isJogarNovamente();
                }
                System.out.println("O jogador " + jogador.getCor() + " está na casa " + jogador.getPosicao());
                if (tirouNumIguais) {
                    System.out.println("O jogador " + jogador.getCor() + " tirou valores iguais e jogará novamente");
                }
            } while (tirouNumIguais || caiuNaCasaJogaDenovo);
        }
    }
    public int perguntarNumeroDeCasas() {
        System.out.print("Digite o número de casas do tabuleiro: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Entrada inválida. Digite um número inteiro:");
            scanner.next(); // não libera entrada inválida
        }

        int casas = scanner.nextInt();
        scanner.nextLine(); // limpa a quebra de linha

        while (casas <= 0) {
            System.out.print("Número deve ser maior que zero. Tente novamente: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número inteiro:");
                scanner.next();

                casas = scanner.nextInt();
                scanner.nextLine(); // limpa a quebra de linha
            }
        }
        return casas;
    }
    private void inicializarTabuleiroVisual() {
        this.colunas = 10;
        this.linhas = totalCasas / 10;
        if (totalCasas % 10 != 0) {
            this.linhas++;
        }
        this.tabuleiroVisual = new List[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiroVisual[i][j] = new ArrayList<>();
            }
        }
    }
    public void configCasas(int totalCasas) {
        this.totalCasas = totalCasas;
        criarTabNormal(totalCasas);
        inicializarTabuleiroVisual();
        int quantCasasEspeciais = lerInt("\nDigite quantas casas especiais você deseja adicionar: ", 0, totalCasas-1);
        if(quantCasasEspeciais == 0){
            criarTabNormal(totalCasas);
            return;
        }
        for (int i = 0; i < quantCasasEspeciais; i++) {
            int posCasaEspecial = adicionarPosCasaEspecial("\nDigite a posição (1 a " + (totalCasas -1) + ") da " + (i + 1) + "º casa especial que você deseja adicionar: ");
            System.out.println("\n    ----Escolha do tipo da Casa Especial que estará na posição " + posCasaEspecial + " ----\n");
            int tipoCasa = lerInt("1 - Casa Mágica\n2 - Casa da Sorte\n3 - Casa Stop\n4 - Casa Surpresa\n5 - Casa Volta\n6 - Casa Azar\n7 - Casa Jogar Novamente\n\nTipo escolhido: ", 1, 7);
            casas.set(posCasaEspecial, CasasFactory.criarCasa(tipoCasa, posCasaEspecial));
        }
    }
    public int adicionarPosCasaEspecial(String prompt) {
        int posicao;
        do {
            posicao = lerInt(prompt, 1, totalCasas - 1);
            if(existeCasa(posicao)){
                System.out.println("Já existe uma casa especial na posição " + posicao + "\nTente Novamente!");
            }
        } while (existeCasa(posicao));
        return posicao;
    }
    public boolean existeCasa(int posCasaEspecial) {
        Casa casa = casas.get(posCasaEspecial);
        return !(casa instanceof CasaNormal);
    }
    private void vencer(Jogador jogador, List<Jogador> jogadores) {
        System.out.println("=============================================");
        System.out.println("O jogador " + jogador.getCor() + " venceu!");
        for (Jogador j : jogadores) {
            System.out.println("Número de jogadas do jogador " + j.getCor() + ": " + j.getJogadas());
        }
        System.out.println("=============================================");
    }

    public void casasEspeciais(Jogador jogador, List<Jogador> jogadores) {
        int pos = jogador.getPosicao();
        if (pos >= 0 && pos < casas.size()) {
            casas.get(pos).aplicarEfeito(jogador, jogadores);
        }
    }

    public int lerInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida. " + prompt);
            scanner.nextLine();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
    public int lerInt(String prompt, int min, int max) {
        int val;
        do {
            val = lerInt(prompt);
            if (val < min || val > max) {
                System.out.printf("Valor deve estar entre %d e %d.%n", min, max);
            }
        } while (val < min || val > max);
        return val;
    }
    public void setCasas(ArrayList<Casa> casas) {
        this.casas = casas;
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }
    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }
    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}

