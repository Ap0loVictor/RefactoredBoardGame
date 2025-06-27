package casas;
import jogadores.*;
import java.util.List;

public class CasaSorte extends Casa {
    static final int NUM_CASAS_AVANCA = 3;
    public CasaSorte(int indiceCasa){
        super(indiceCasa);
    }
    @Override
    public void aplicarEfeito(Jogador jogador, List<Jogador> jogadores){
        System.out.println("\nCasa da Sorte!!!\nO jogador" + jogador.getCor() + " Caiu na casa da sorte.");
        if(!(jogador instanceof JogadorAzarado)){
            jogador.avancar(NUM_CASAS_AVANCA);
             System.out.println("O jogador " + jogador.getCor() + " andou três casas a frente");
        } else {
            System.out.println("O jogador  " + jogador.getCor() + " é azarado e nao pode andar tres casas a frente");
        }
    }
}
