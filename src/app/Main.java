package app;
import jogar.*;
public class Main {
    public static void main(String[] args) {
        Jogo jogo = new Jogo();
        jogo.printMenuCasas();
        int numCasas = jogo.lerInt(" ");
        jogo.configTabuleiro(numCasas);
        int numJogadores = jogo.lerInt("Digite o número de jogadores (2 a 6): ", 2, 6);
        jogo.config(numJogadores);
        jogo.printTabuleiro();
        jogo.start();
    }
}
