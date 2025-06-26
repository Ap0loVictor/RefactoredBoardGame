package jogar;

import factory.JogadorFactory;
import jogadores.*;
import tabuleiro.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
public class Jogo {
    private final Scanner scanner;
    private final Tabuleiro tabuleiro;
    private final List<String> coresDisponiveis;
    private boolean podeAdicionar = true;
    private GerenciadorJogadores gerenciadorJogadores = new GerenciadorJogadores();
    private InstanciarTabuleiro instanciar = new InstanciarTabuleiro();
    public Jogo() {
        this.scanner = new Scanner(System.in);
        this.tabuleiro = instanciar.configurarTabuleiro();
        this.coresDisponiveis = new ArrayList<>(
                Arrays.asList("Vermelho", "Verde", "Azul", "Amarelo", "Preto", "Branco")
        );
    }

    /** Inicia todo o fluxo do jogo */
    public void start() {
        int opc;
        do {
            Jogador vencedor = gerenciadorJogadores.verificarVencedor(instanciar.perguntarNumeroDeCasas());
            printMenu(vencedor == null);

            opc = lerInt("Escolha uma opção: ");
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
    public Tabuleiro configTabuleiro(int numCasas) {
        return tabuleiro;
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
        System.out.println("\n--- Cadastro de Jogador ---");
        System.out.println("1- Azarado   2- Sortudo   3- Normal");
        int tipo = lerInt("Escolha o tipo: ");

        System.out.println("\n--- Escolha a cor ---");
        for (int i = 0; i < coresDisponiveis.size(); i++) {
            System.out.printf(" %d- %s%n", i + 1, coresDisponiveis.get(i));
        }
        int idxCor = lerInt("Escolha uma cor: ", 1, coresDisponiveis.size()) - 1;
        String cor = coresDisponiveis.remove(idxCor);
        Jogador j = JogadorFactory.criarJogador(tipo, cor);
        tabuleiro.adicionarJogador(j);
        System.out.println("Jogador " + j.getCor() + " adicionado.");
    }

    private void fluxoJogar() {
        if (!tabuleiro.inicarJogo()) {
            System.out.println("Não podem haver apenas jogadores do mesmo tipo. Abortando Jogo!");
            return;
        }

        System.out.println("\n--- Iniciando Jogo ---");
        while (true) {
            System.out.println("\n1- Inserir Casas   2- Rolar Dados");
            int opc = lerInt("Escolha uma opção: ");
            boolean inserirCasas = (opc == 1);
            tabuleiro.jogarRodada(inserirCasas);

            tabuleiro.atualizarTabuleiroVisual();
            tabuleiro.imprimirTabuleiroVisual();

            Jogador vencedor = gerenciadorJogadores.verificarVencedor(instanciar.perguntarNumeroDeCasas());
            if (vencedor != null) {
                System.out.println("\nO jogador " + vencedor.getCor() + " venceu! Fim da partida.");
                break;
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
}