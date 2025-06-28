package jogadores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


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

    public List<Jogador> getJogadores() {
    return jogadores;
}

    
}
