package tabuleiro;

import casas.*;
import factory.CasasFactory;
import jogadores.Jogador;
import java.util.*;
import app.GerenciadorJogadores;

public class Tabuleiro {
    private GerenciadorJogadores gerenciadorJogadores;
    private Random random;
    private ArrayList<Casa> casas;
    private ArrayList<Jogador> jogadores;
    private List<Jogador>[][] tabuleiroVisual;
    private boolean novoTabuleiro = true;
    private Scanner scanner = new Scanner(System.in);

    private int totalCasas;
    private int linhas;
    private int colunas;

    public Tabuleiro(int totalCasas) {
        this.random = new Random();
        this.gerenciadorJogadores = new GerenciadorJogadores();
        this.totalCasas = totalCasas;

        this.colunas = 10;
        this.linhas = totalCasas / 10;
        if (totalCasas % 10 != 0) {
            this.linhas++;
        }
        this.casas = new ArrayList<>();
        this.tabuleiroVisual = new List[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiroVisual[i][j] = new ArrayList<>();
            }
        }
    }
    public Tabuleiro(int totalCasas, boolean novoTabuleiro) {
        this.random = new Random();
        this.gerenciadorJogadores = new GerenciadorJogadores();
        this.totalCasas = totalCasas;
        this.casas = new ArrayList<>();
        criarTabNormal(totalCasas);
    }
    public void criarTabNormal(int totalCasas) {
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
        for (Jogador jogador : gerenciadorJogadores.getJogadores()) {
            if (jogador.getPosicao() >= totalCasas) {
                jogador.setPosicao(totalCasas);
            }
            if (jogador.getPosicao() < totalCasas) {
                int linha = jogador.getPosicao() / colunas;
                int coluna = jogador.getPosicao() % colunas;
                tabuleiroVisual[linha][coluna].add(jogador);
            }
        }
    }
    public void printTabuleiro(boolean novoTabuleiro) {
        
        System.out.println("\n--- Tabuleiro Visual ---");
        for (int i = 0; i < casas.size(); i++){
            if ((i % 10 == 0) && i != 0) {
                System.out.print("\n");
            }
            if(jogadores.get(i).getPosicao() == casas.get(i).getIndiceCasa()) {
                System.out.println(i + ".[ " + jogadores.get(i).getCor() + " ]");
            }
            System.out.print( i + ".[ "+ " " +" ]\t");
        }
        System.out.print("\n");
        legendaJogadores();
    }

    public void printTabuleiro() {
        System.out.println("\n--- Tabuleiro Visual ---");
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                int numeroCasa = i * colunas + j;
                if (numeroCasa >= totalCasas){
                    break;
                }
            System.out.print(numeroCasa + ".[" + tabuleiroVisual[i][j].size() + "]\t");
            }
        System.out.println();
        }
        int jogadoresNaUltimaCasa = 0;
        for (Jogador jogador : gerenciadorJogadores.getJogadores()) {
            if (jogador.getPosicao() == totalCasas) {
                jogadoresNaUltimaCasa++;
            }
        }
        System.out.println(totalCasas + ".[" + jogadoresNaUltimaCasa + "]");
        System.out.println("=================================");
        legendaJogadores();
    }

    public void legendaJogadores(){
    System.out.println("\nLegenda dos Jogadores:");
        for (Jogador jogador : gerenciadorJogadores.getJogadores()) {
            System.out.println(jogador.getClass().getSimpleName() + " | " + jogador.getCor() + " | Posição " + jogador.getPosicao());
        }
        System.out.println("=================================\n");
    }

    // os métodos abaixo provavelmnete vão desaparecer com a utilização do facade e refatoração geral de classes. 
    // por enquanto estarão aí pro funcionamento prévio do código

    public boolean adicionarJogador(Jogador jogador) {
        return gerenciadorJogadores.adicionarJogador(jogador);
    }



    public boolean inicarJogo() {
        return gerenciadorJogadores.validarTiposDeJogadores();
    }

    public void jogarRodada(boolean modoDebug) {
        List<Jogador> jogadores = gerenciadorJogadores.getJogadores();

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
            boolean repetirJogada;

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

                    if (jogador.getPosicao() >= totalCasas) {
                        vencer(jogador, jogadores);
                        return;
                    }
                    casasEspeciais(jogador, jogadores);
                    repetirJogada = false;
                } else {
                    System.out.println("=============================================");
                    System.out.println("Turno do jogador: " + jogador.getCor());
                    System.out.println("Pressione ENTER para jogar");
                    scanner.nextLine();
                    dados = jogador.rolarDados(random);
                    soma = dados[0] + dados[1];
                    System.out.println("Dados rolados: " + dados[0] + " + " + dados[1] + " = " + soma);
                    jogador.avancar(soma);

                    if (jogador.getPosicao() >= totalCasas) {
                        vencer(jogador, jogadores);
                        return;
                    }
                    casasEspeciais(jogador, jogadores);
                    repetirJogada = (dados[0] == dados[1]);
                }
                System.out.println("O jogador " + jogador.getCor() + " está na casa " + jogador.getPosicao());
                if (repetirJogada) {
                    System.out.println("O jogador " + jogador.getCor() + " tirou valores iguais e jogará novamente");
                }
            } while (repetirJogada);
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
    public void configCasas(int totalCasas) {
        int quantCasasEspeciais = lerInt("Digite quantas casas especiais você deseja adicionar");
        if(quantCasasEspeciais == 0){
            criarTabNormal(totalCasas);
            return;
        }
        for (int i = 0; i < quantCasasEspeciais; i++) {
            System.out.println("Digite a posição da " + i + 1 + "º casa especial que você deseja adicionar: ");
            int posCasaEspecial = scanner.nextInt();
            System.out.println("===============================================================================");
            System.out.println("Escolha o tipo da Casa Especial que estará na posição " + posCasaEspecial + " :");
            System.out.println("===============================================================================");
            int tipoCasa = lerInt("1 -  Casa Mágica\n2 -  Casa da Sorte\n3 -  Casa Stop\n4 -  Casa Surpresa\n5 -  Casa Volta");
            System.out.println("===============================================================================");
            for (int j = 0; j < totalCasas; j++) {
                // tabuleiro.getCasas().add(posCasaEspecial, CasasFactory.criarCasa(tipoCasa, posCasaEspecial));
            }
        }
    }
    public void config(int numJogadores) {

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
        for(int i = 0; i < totalCasas; i++) {
            if(pos == casas.get(i).getIndiceCasa()) {
                casas.get(pos).aplicarEfeito(jogador, jogadores);
            }
        }
    }
    /** Lê um inteiro genérico */
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

    /** Lê um inteiro dentro de um intervalo [min..max] */
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

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public List<Jogador> getJogadores() {
        return gerenciadorJogadores.getJogadores();
    }

    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.gerenciadorJogadores = new GerenciadorJogadores();
        for (Jogador j : jogadores) {
            this.gerenciadorJogadores.adicionarJogador(j);
        }
    }
}
