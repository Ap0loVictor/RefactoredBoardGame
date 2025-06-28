// src/app/Main.java
package app;
import jogadores.*;
import jogar.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GerenciadorJogadores gJ = new GerenciadorJogadores();
        Jogo jogo = new Jogo();
        jogo.printMenuCasas();
        int numCasas = scanner.nextInt();
        jogo.configTabuleiro(numCasas);
        int numJogadores = gJ.lerQuantidadeJogadores();
        jogo.config(numJogadores);
        jogo.printTabuleiro();
        jogo.start();
    }
}
