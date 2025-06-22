package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import jogadores.Jogador;


public class GerenciadorJogadores {
    private List<Jogador> jogadores;
    
    public GerenciadorJogadores(){
        this.jogadores = new ArrayList<>();
    }


    public boolean adicionarJogador(Jogador jogador){
        if(jogadores.size() < 6){
            jogadores.add(jogador);
            return true;
        }
        return false;
    }

    Scanner scanner = new Scanner(System.in);

    public boolean validarTiposDeJogadores() {
        Set<Class<?>> tipos = new HashSet<>();
        for (Jogador j : jogadores) {
            tipos.add(j.getClass());
        }
        return tipos.size() >= 2;
    }

    public Jogador verificarVencedor(int numCasas) {
    for (Jogador jogador : jogadores) {
        if (jogador.getPosicao() >= numCasas - 1) {
            return jogador;
        }
    }
    return null;
}

    private void exibirVencedor(Jogador jogador, ArrayList<Jogador> jogadores){
        System.out.println("=============================================");
        System.out.println("O jogador " + jogador.getCor() + " venceu !");
        for (Jogador jogadore : jogadores) {
            System.out.println("Número de jogadas do jogador " + jogadore.getCor() + ": " + jogadore.getJogadas());
        }
        System.out.println("=============================================");
    }

    public List<Jogador> getJogadores() {
    return jogadores;
}

    
}
