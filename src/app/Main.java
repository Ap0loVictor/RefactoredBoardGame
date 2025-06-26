// src/app/Main.java
package app;

import jogar.*;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jogo jogo = new Jogo();
        jogo.printMenuInicial();
        int numCasas = scanner.nextInt();
        jogo.configTabuleiro(numCasas);
        jogo.start();
    }
}
