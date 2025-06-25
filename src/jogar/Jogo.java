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

    public Jogo() {
        this.scanner = new Scanner(System.in);
        InstanciarTabuleiro instanciar = new InstanciarTabuleiro();
        this.tabuleiro = instanciar.configurarTabuleiro();
        this.coresDisponiveis = new ArrayList<>(
            Arrays.asList("Vermelho", "Verde", "Azul", "Amarelo", "Preto", "Branco")
        );
    }

    public void start() {               // Primeiro menu (Add jogador; Jogar; sair)
        int opcao;
        menuLoop: do {
            printMenu();

            opcao = lerInt("Escolha uma opção: ");
            switch (opcao) 
            {
                case 1:
                    if (tabuleiro.getJogadores().size() < 6 && podeAdicionar) {
                        fluxoAdicionarJogador();
                    } 
                    else {
                        System.out.println("Não é possível adicionar mais jogadores.");
                    }
                    break;
                case 2:
                    podeAdicionar = false;
                    apresentarSegundoMenu();
                    break menuLoop;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                        System.out.println("Opção inválida.");
            }
        } while (opcao != 3);

        scanner.close();
    }

    private void printMenu() {
        System.out.println("\n=============================================");
        if (tabuleiro.getJogadores().size() < 6 && podeAdicionar) {
            System.out.println("   1- Adicionar Jogador");}
        System.out.println("   2- Jogar");
        System.out.println("   3- Sair");
        System.out.println("=============================================");
    }

    private void fluxoAdicionarJogador() {
        System.out.println("\n--- Cadastro de Jogador ---");
        System.out.println("1- Azarado   2- Sortudo   3- Normal");
        int tipo = lerInt("Escolha o tipo: ", 1, 3);

        System.out.println("\n--- Escolha a cor ---");
        for (int i = 0; i < coresDisponiveis.size(); i++) {
            System.out.printf(" %d- %s%n", i + 1, coresDisponiveis.get(i));
        }
        int idxCor = lerInt("Escolha uma cor: ", 1, coresDisponiveis.size()) - 1;
        String nomeCor = coresDisponiveis.remove(idxCor);
        Cor cor = new Cor(nomeCor);
        Jogador j = JogadorFactory.criarJogador(tipo, cor);
        tabuleiro.adicionarJogador(j);
        System.out.println("Jogador " + j.getCor() + " adicionado.");
    }

    private void apresentarSegundoMenu() {              // Menu 2: Escolha do tipo de jogo
        validarInicioDeJogo();
        
        if (!tabuleiro.inicarJogo()) {
            System.out.println("Não foi atendida a condição: \n     - Ter ao menos 2 jogadores de tipos diferentes'\nAbortando Jogo...");
            podeAdicionar = true;
            start();  // Avaliar se isso é um método plausível ou "faz mal"
            return;
        }

        System.out.println("\n--- Iniciando Jogo ---");
        System.out.println("\nEscolha o modo de jogo: \n1- Inserir Casas   2- Rolar Dados");
        int opc = lerInt("Modo de jogo: ", 1, 2);
        boolean inserirCasas = (opc == 1);
        jogarNoModoDeJogo(inserirCasas);
    }

    private void validarInicioDeJogo(){

    }

    private void jogarNoModoDeJogo(boolean inserirCasas){
        while (true) {
            tabuleiro.jogarRodada(inserirCasas);
            tabuleiro.atualizarTabuleiroVisual();
            tabuleiro.imprimirTabuleiroVisual();

            Jogador vencedor = tabuleiro.verificarVencedor();
            if (vencedor != null) {
                System.out.println("\nO jogador " + vencedor.getCor() + " venceu! Fim da partida.");
                break;
            }
        }
    }

    private int lerInt(String prompt) {     // É o ler inteiro normal
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida.\n" + prompt);
            scanner.nextLine();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }

    private int lerInt(String prompt, int min, int max) {  // Usado pra ler a cor na linha do idxCor
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