package casas;

import jogadores.Jogador;
import java.util.List;

public class CasaJogaNovamente extends Casa {
    public CasaJogaNovamente(int indiceCasa) {
        super(indiceCasa);
    }

    @Override
    public void aplicarEfeito(Jogador jogadorAtual, List<Jogador> jogadores) {
        System.out.println("Casa Jogar Novamente!!!\nO jogador " + jogadorAtual.getCor() + " caiu na casa Jogar Novamente.");
        jogadorAtual.setJogarNovamente(true);
    }
}
