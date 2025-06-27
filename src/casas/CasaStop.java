package casas;
import jogadores.*;
import java.util.List;

public class CasaStop extends Casa {
    public CasaStop(int indiceCasa){
        super(indiceCasa);
    }
    @Override
    public void aplicarEfeito(Jogador jogador, List<Jogador> jogadores){
        jogador.setPularRodada(true);
        System.out.println("\nCasas.Casa Stop!!!");
        System.out.println("O jogador" + jogador.getCor() + " caiu na casa " + indiceCasa + " e vai pular a próxima rodada");
    }
}

