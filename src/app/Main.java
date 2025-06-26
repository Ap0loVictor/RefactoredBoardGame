// src/app/Main.java
package app;
import java.util.Scanner;
import jogar.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numCasas;
        int numJogadores;
        Jogo jogo = new Jogo();
         numCasas = jogo.lerInt("Digite o número de casas para o tabuleiro: ");
         numJogadores = jogo.lerInt("Digite o número de jogadores: ");
         jogo.configTabuleiro(numCasas);
         jogo.start();


    }
}
