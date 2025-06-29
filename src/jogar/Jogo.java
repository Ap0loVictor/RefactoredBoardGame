package jogar;

import factory.JogadorFactory;
import jogadores.*;
import tabuleiro.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import casas.Casa;

public class Jogo {
    private Random random;
    private final Scanner scanner;
    private final Tabuleiro tabuleiro;
    private final List<String> coresDisponiveis;
    private boolean podeAdicionar = true;
    private boolean temVencedor = false; 
    private static final String THE_PLAYER = "O jogador ";

    public Jogo() {
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.tabuleiro = new Tabuleiro(0);
        this.coresDisponiveis = new ArrayList<>(
            Arrays.asList("Vermelho", "Verde", "Azul", "Amarelo", "Cinza", "Rosa"));
    }

    public void printMenuCasas() {
        System.out.println("\n -------- CORRIDA MALUCA! ------\n");
        System.out.print("Digite o número de casas que preferir para iniciar a corrida : ");
    }

    public void configTabuleiro(int totalCasas) { // Facade
        tabuleiro.inicializarTabuleiros(totalCasas);
        int quantCasasEspeciais = lerInt("\nDigite quantas casas especiais você deseja adicionar: ", 0, totalCasas-1);
        if(quantCasasEspeciais == 0){
            tabuleiro.criarTabNormal(totalCasas);
            return;
        }
        preencherCasasEspeciais(quantCasasEspeciais, totalCasas);
    }
    public void preencherCasasEspeciais(int quantCasasEspeciais, int totalCasas) { // Facade
        for (int i = 0; i < quantCasasEspeciais; i++) {
            int posCasaEspecial = adicionarPosCasaEspecial( i, totalCasas);
            System.out.println("\n    ----Escolha do tipo da Casa Especial que estará na posição " + posCasaEspecial + " ----\n");
            int tipoCasa = lerInt("1 - Casa Mágica\n2 - Casa da Sorte\n3 - Casa Stop\n4 - Casa Surpresa\n5 - Casa Volta\n6 - Casa Azar\n7 - Casa Jogar Novamente\n\nTipo escolhido: ", 1, 7);
            tabuleiro.setarCasaEspecial(posCasaEspecial, tipoCasa);
        }
    }
    public int adicionarPosCasaEspecial(int i, int totalCasas) { // Facade
        int posicao;
        do {
            posicao = lerInt("\nDigite a posição (1 a " + (totalCasas -1) + ") da " + (i + 1) + "º casa especial que você deseja adicionar: ", 1, totalCasas - 1);
            if(tabuleiro.existeCasa(posicao)){
                System.out.println("Já existe uma casa especial na posição " + posicao + "\nTente Novamente!");
            }
        } while (tabuleiro.existeCasa(posicao));
        return posicao;
    }
    
    public void start() {
        int opc;
        do {
            Jogador vencedor = verificarVencedor(tabuleiro.getCasas().size());
            printMenu(vencedor == null);
            opc = lerInt("Escolha uma opção: ", 1, 3);
            if (vencedor != null) {
               opc = 3;
            }
            switch (opc) {
                case 1:
                    if (tabuleiro.getJogadores().size() < 6 && podeAdicionar) {
                        fluxoAdicionarJogador();
                    } else {
                        System.out.println("Não é possível adicionar mais jogadores.");
                    }
                    break;
                case 2:
                    if (vencedor == null) {
                        podeAdicionar = false;
                        fluxoJogar();
                    } else {
                        System.out.println("Já há um vencedor! Reinicie para começar nova partida.");
                    }
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
            
        } while (opc != 3);

        scanner.close();
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

    public Jogador verificarVencedor(int numCasas) {
    for (Jogador jogador : tabuleiro.getJogadores()) {
        if (jogador.getPosicao() >= numCasas - 1) {
            return jogador;
        }
    }
    return null;
    }

    public void printTabuleiro() { // Facade
        ArrayList<Casa> casas = tabuleiro.getCasas();
        System.out.println("\n--- Tabuleiro Visual ---");
        for (int i = 0; i < casas.size(); i++) {
            if ((i % 10 == 0) && i != 0) {
                System.out.print("\n");
            }
            System.out.print(formatCasa(i));
        }
        legendaJogadores();
    }

    private String formatCasa(int index) {
        String jogadoresNaCasa = getJogadoresNaCasa(index);
        if (!jogadoresNaCasa.isEmpty()) {
            return index + ".[ " + jogadoresNaCasa + " ]\t";
        } else {
            return index + ".[  ]\t";
        }
    }

    private String getJogadoresNaCasa(int index) {
        StringBuilder conteudo = new StringBuilder();
        for (Jogador j : tabuleiro.getJogadores()) {
            if (j.getPosicao() == index) {
                if (conteudo.length() > 0) {
                    conteudo.append(", ");
                }
                conteudo.append(j.getCor());
            }
        }
        return conteudo.toString();
    }
    public void legendaJogadores(){  // FACADE
    System.out.println("\nLegenda dos Jogadores:");
        for (Jogador jogador : tabuleiro.getJogadores()) {
            System.out.println(jogador.getClass().getSimpleName() + " | " + jogador.getCor() + " | Posição " + jogador.getPosicao());
        }
    }

    public void config(int numJogadores) {
        for(int i = 0; i < numJogadores; i++) {
            fluxoAdicionarJogador();
        }
    }
    private void printMenu(boolean semVencedor) {
        System.out.println("\n=============================================");
        if (tabuleiro.getJogadores().size() < 6 && podeAdicionar) {
            System.out.println("   1- Adicionar Jogador");
        }
        if (semVencedor) {
            System.out.println("   2- Jogar");
        }
        System.out.println("   3- Sair");
        System.out.println("=============================================");
    }

    private void fluxoAdicionarJogador() {
        int tipo = escolherTipoJogador();

        System.out.println("\n--- Escolha a cor ---");
        for (int i = 0; i < coresDisponiveis.size(); i++) {
            System.out.printf(" %d- %s%n", i + 1, coresDisponiveis.get(i));
        }
        String cor;
        int idxCor = lerInt("Escolha uma cor: ", 1, coresDisponiveis.size()) - 1;
        cor = coresDisponiveis.remove(idxCor);
        Cor corJogador = new Cor(cor);
        Jogador j = JogadorFactory.criarJogador(tipo, corJogador);
        tabuleiro.adicionarJogador(j);
        System.out.println("Jogador " + j.getCor() + " adicionado.");
    }
    private int escolherTipoJogador() {
        System.out.println("\n--- Cadastro de Jogador ---");
        System.out.println("1- Azarado   2- Sortudo   3- Normal");
        return lerInt("Escolha o tipo: ", 1, 3);
    }
    

    private void fluxoJogar() {
        if (!tabuleiro.iniciarJogo()) {
            System.out.println("Não podem haver apenas jogadores do mesmo tipo. Abortando Jogo!");
            return;
        }

        System.out.println("\n--- Iniciando Jogo ---");
        System.out.println("\n1- Inserir Casas   2- Rolar Dados");
        int opc = lerInt("Escolha uma opção: ", 1, 2);
        boolean inserirCasas = (opc == 1);
        while (true) {
            jogarRodada(inserirCasas);
            tabuleiro.atualizarTabuleiroVisual();
            printTabuleiro();
            Jogador vencedor = verificarVencedor(tabuleiro.getCasas().size());
            if (vencedor != null) {
                break;
            }
        }
    }

    public void jogarRodada(boolean modoDebug) {  // Facade
        ArrayList<Jogador> jogadores = tabuleiro.getJogadores();

        for (Jogador jogador : jogadores) {
            if (verificarPulante(jogador)) {
                continue;
            }
            boolean caiuNaCasaJogaDenovo = false;
            jogarEnquantoJogaNovamente(jogador, jogadores, modoDebug, caiuNaCasaJogaDenovo);
            if (temVencedor) {
                break; 
            }
        }
    }
    public boolean verificarPulante(Jogador jogador) { 
        if (jogador.isPularRodada()) {
            jogador.setPularRodada(false);
            System.out.println(THE_PLAYER + jogador.getCor() + " está pulando a rodada");
            return true;
        }
        return false;
    }

    public void jogarEnquantoJogaNovamente(Jogador jogador, List<Jogador> jogadores, boolean modoDebug, boolean caiuNaCasaJogaDenovo) { // Facade
        do {
            jogador.setJogadas(jogador.getJogadas() + 1);
            int[] dados = null;
            int soma = 0;
            verificarModo(modoDebug, jogador, jogadores, dados, soma, caiuNaCasaJogaDenovo);
            System.out.println(THE_PLAYER + jogador.getCor() + " está na casa " + jogador.getPosicao());
            verificarNumeroIguais(tabuleiro.isNumerosIguais(), jogador);
            } while (tabuleiro.isNumerosIguais() || caiuNaCasaJogaDenovo);
    }

    public void verificarModo(boolean modoDebug, Jogador jogador, List<Jogador> jogadores, int[] dados, int soma, boolean caiuNaCasaJogaDenovo) { // Facade
        if (modoDebug) {
            jogarNoModo(modoDebug, jogador, jogadores, caiuNaCasaJogaDenovo);
        } 
        else {
            jogarNoModo(jogador, jogadores, dados, soma, caiuNaCasaJogaDenovo);
        }
    }

    public void jogarNoModo(boolean modoDebug, Jogador jogador, List<Jogador> jogadores, boolean caiuNaCasaJogaDenovo) { 
        int escolha = lerInt("\n ---- Informe a casa que o jogador " + jogador.getCor() + " deve estar:\n Casa escolhida: ", 0, tabuleiro.getTotalCasas()-1);
                jogador.setPosicao(escolha);
                if (verificarVencedor(jogador, jogadores)) {
                    return;
                }
                tabuleiro.casasEspeciais(jogador, jogadores);
                caiuNaCasaJogaDenovo = jogador.isJogarNovamente();
                jogador.setJogarNovamente(false);
        }
    public void jogarNoModo(Jogador jogador, List<Jogador> jogadores, int[] dados, int soma, boolean caiuNaCasaJogaDenovo) { // Facade
        lerEnter("\n ---- Turno do jogador: " + jogador.getCor() + " ----\nPressione ENTER para jogar");
                dados = jogador.rolarDados(random);
                soma = dados[0] + dados[1];
                System.out.println("Dados rolados: " + dados[0] + " + " + dados[1] + " = " + soma);                
                jogador.avancar(soma);
                if (verificarVencedor(jogador, jogadores)) {
                    return;
                }
                tabuleiro.casasEspeciais(jogador, jogadores);
                tabuleiro.setNumerosIguais(dados[0] == dados[1]);
                caiuNaCasaJogaDenovo = jogador.isJogarNovamente();
                jogador.setJogarNovamente(false);                  
    }
    public boolean verificarVencedor(Jogador jogador, List<Jogador> jogadores) { 
        if (jogador.getPosicao() >= tabuleiro.getTotalCasas()-1) {
            jogador.setPosicao(tabuleiro.getTotalCasas() - 1);
            vencer(jogador, jogadores);
            temVencedor = true;
            return true;
        }
        return false;
    }
    
    private void vencer(Jogador jogador, List<Jogador> jogadores) { 
        System.out.println("=============================================");
        System.out.println(THE_PLAYER + jogador.getCor() + " venceu!");
        for (Jogador j : jogadores) {
            System.out.println("Número de jogadas do jogador " + j.getCor() + ": " + j.getJogadas());
        }
        System.out.println("=============================================");
    }

    public void lerEnter(String prompt) {
        System.out.print(prompt);
        scanner.nextLine();
    }

    public void verificarNumeroIguais(boolean tirouNumIguais, Jogador jogador) { 
        if (tirouNumIguais) {
            System.out.println(THE_PLAYER + jogador.getCor() + " tirou valores iguais e jogará novamente");
        }
    }

    public List<String> getCoresDisponiveis() {
        return coresDisponiveis;
    }
}