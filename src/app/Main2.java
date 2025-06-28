// src/app/Main.java
package app;

import jogar.*;
import tabuleiro.Tabuleiro;

import java.util.Scanner;
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Jogo jogo = new Jogo();
        Tabuleiro tabuleiroNovo = new Tabuleiro(40);
        jogo.printMenuJogadores();
        int numJogadores = scanner.nextInt();
        jogo.config(numJogadores);
        tabuleiroNovo.printTabuleiro();

    }
}
