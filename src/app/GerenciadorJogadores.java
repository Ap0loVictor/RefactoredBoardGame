package app;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import jogadores.Jogador;
import tabuleiro.Tabuleiro;

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
        if (tipos.size() < 2) {
            return false;
        }
        return true;
    }

    public Jogador verificarVencedor(int numCasas) {
    for (Jogador jogador : jogadores) {
        if (jogador.getPosicao() >= numCasas - 1) {
            return jogador;
        }
    }
    return null;
}

    private void ExibirVencedor(Jogador jogador, ArrayList<Jogador> jogadores){
        System.out.println("=============================================");
        System.out.println("O jogador " + jogador.getCor() + " venceu !");
        for(int i = 0; i < jogadores.size();  i++){
            System.out.println("Número de jogadas do jogador " + jogadores.get(i).getCor() + ": " +jogadores.get(i).getJogadas());
        }
        System.out.println("=============================================");
    }

    public List<Jogador> getJogadores() {
    return jogadores;
}

    
}
