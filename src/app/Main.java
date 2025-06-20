package app;
import jogadores.*;
import tabuleiro.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        Tabuleiro tabuleiro = new Tabuleiro();
        int opc, opcTipo;
        ArrayList<String> coresDisponiveis = new ArrayList<>();
        coresDisponiveis.addAll(Arrays.asList("Vermelho", "Verde", "Azul", "Amarelo", "Preto", "Branco"));
        int opcCor;
        boolean flagAd = true;
        Jogador vencedor = null;

        do {
            vencedor = tabuleiro.verificarVencedor();
            System.out.println("=============================================");
            if (tabuleiro.getJogadores().size() < 6 && flagAd) {
                System.out.println("        1- Adicionar Jogadores.Jogador");
            }
            if(vencedor == null){
                System.out.println("        2- Jogar");
            }
            System.out.println("        3- Sair");
            System.out.println("=============================================");
            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Digite apenas números.");
                scanner.nextLine();
                System.out.print("Escolha uma opção: ");
            }
            opc = scanner.nextInt();

            if (opc == 1) {
                System.out.println("=============================================");
                System.out.println(" - Qual o tipo do jogador ?");
                System.out.println("   1 - Azarado\n   2 - Sortudo\n   3 - Normal ");
                System.out.println("=============================================");
                System.out.print("Escolha uma opção: ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Entrada inválida. Digite apenas números.");
                    scanner.nextLine();
                    System.out.print("Escolha uma opção: ");
                }
                opcTipo = scanner.nextInt();
                scanner.nextLine(); // consumir quebra de linha

                System.out.println("=============================================");
                System.out.println("Qual a cor do jogador ?");
                for (int i = 0; i < coresDisponiveis.size(); i++) {
                    System.out.println((i + 1) + " - " + coresDisponiveis.get(i));
                }
                System.out.println("=============================================");
                System.out.print("Escolha uma cor: ");
                while (true) {
                     if (!scanner.hasNextInt()) {
                        System.out.println("Entrada inválida. Digite apenas números.");
                        scanner.nextLine();
                        System.out.print("Escolha uma cor: ");
                        continue;
                    }
                    opcCor = scanner.nextInt();
                    scanner.nextLine(); // consumir o '\n'

                    if (opcCor < 1 || opcCor > coresDisponiveis.size()) {
                        System.out.println("Número inválido. Escolha uma cor disponível na lista.");
                        System.out.print("Escolha uma cor: ");
                        continue;
                    }
                    break;
                }  

            String escolhaFeita = coresDisponiveis.get(opcCor - 1);
            coresDisponiveis.remove(opcCor - 1);
            Jogador jogador = null;
                switch (opcTipo) {
                    case 1:
                        jogador = new JogadorAzarado(escolhaFeita);
                        break;
                    case 2:
                        jogador = new JogadorSortudo(escolhaFeita);
                        break;
                    case 3:
                        jogador = new JogadorNormal(escolhaFeita);
                        break;
                    default:
                        System.out.println("Opção de tipo inválida.");
                        continue;
                }
                tabuleiro.adicionarJogador(jogador);
            } else if (opc == 2) {
                flagAd = false;
                int opc2;
                if (tabuleiro.inicarJogo()) {
                    do {
                        System.out.println("=============================================");
                        System.out.println("1 - Inserir Casas.Casa\n2 - Rolar Dados");
                        System.out.println("=============================================");
                        System.out.print("Escolha uma opção: ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Entrada inválida. Digite apenas números.");
                            scanner.nextLine();
                            System.out.print("Escolha uma opção: ");
                        }
                        opc2 = scanner.nextInt();
                        scanner.nextLine();
                        if (opc2 == 1) {
                            tabuleiro.jogarRodada(true);
                        } else if (opc2 == 2) {
                            tabuleiro.jogarRodada(false);
                        }

                        tabuleiro.atualizarTabuleiroVisual();
                        tabuleiro.imprimirTabuleiroVisual();

                        vencedor = tabuleiro.verificarVencedor();
                        if (vencedor != null) {
                            System.out.println("O jogador " + vencedor.getCor() + " venceu o jogo! Comece uma nova partida!");
                            opc = 3;
                            break;
                        }

                    } while (true);
                } else {
                    System.out.println("Não podem haver apenas jogadores do mesmo tipo\nAbortando Jogo!");
                    opc = 3;
                }
            } else if (opc != 3) {
                System.out.println("Opção inválida.");
            }

        } while (opc != 3);

        scanner.close();
    }
}
