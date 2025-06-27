// src/app/Main.java
package app;

import jogar.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jogo jogo = new Jogo();
        jogo.printMenuCasas();
        int numCasas = scanner.nextInt();
        jogo.configTabuleiro(numCasas);
        jogo.printTabuleiro();
        jogo.printMenuJogadores();
        int numJogadores = scanner.nextInt();
        jogo.config(numJogadores);

        jogo.start();
    }
}
