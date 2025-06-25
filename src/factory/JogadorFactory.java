package factory;
import jogadores.*;
public class JogadorFactory {
    private JogadorFactory(){

    }
    public static Jogador criarJogador(int opcTipo, Cor cor) {
        switch (opcTipo) {
            case 1:
                return new JogadorAzarado(cor);
            case 2:
                 return new JogadorSortudo(cor);
            case 3:
                return new JogadorNormal(cor);
            default:
                throw new IllegalArgumentException("Cor inválida ! " + cor);
        }
    }
}
