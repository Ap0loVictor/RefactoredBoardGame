package casas;
import jogadores.*;
import java.util.List;

public class CasaSorte extends Casa {
    public CasaSorte(int indiceCasa){
        super(indiceCasa);
    }
    @Override
    public void aplicarEfeito(Jogador jogador, List<Jogador> jogadores){
        System.out.println("\nCasas.Casa da Sorte!!!\nO jogador" + jogador.getCor() + " Caiu na casa da sorte.");
        if(!(jogador instanceof JogadorAzarado)){
            jogador.avancar(3);
             System.out.println("O jogador " + jogador.getCor() + " andou três casas a frente");
        } else {
            System.out.println("O jogador  " + jogador.getCor() + " é azarado e nao pode andar tres casas a frente");
        }
    }
}

// 5, 15 e 30:
// Casas da sorte: ande 3 casas para frente desde que ele não seja um jogador
// azarado;