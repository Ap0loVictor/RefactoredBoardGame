package casas;

import jogadores.Jogador;

import java.util.List;

public class CasaNormal extends Casa {
    public CasaNormal (int pos){
        super(pos);
    }

    @Override
    public void aplicarEfeito(Jogador jogadorAtual, List<Jogador> jogadores) {
        System.out.print("");
    }
}
