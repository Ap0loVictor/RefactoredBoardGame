package casas;

import jogadores.Jogador;
import jogadores.JogadorSortudo;
import java.util.List;

public class CasaAzar extends Casa {
    static final int NUM_CASAS_VOLTA = 3 ;
    public CasaAzar(int indiceCasa){
        super(indiceCasa);
    }

    @Override
    public void aplicarEfeito(Jogador jogadorAtual, List<Jogador> jogadores) {
        System.out.println("Casa Azar!!!\nO jogador " + jogadorAtual.getCor() + " caiu na Casa Azar." );
        if(jogadorAtual instanceof JogadorSortudo) {
            System.out.println("O jogador " + jogadorAtual.getCor() + " é sortudo, portanto não volta três casas.");
            return;
        }
        jogadorAtual.voltar(NUM_CASAS_VOLTA);
        System.out.println("O jogador " + jogadorAtual.getCor() +  " retornou três casas");
    }

}
