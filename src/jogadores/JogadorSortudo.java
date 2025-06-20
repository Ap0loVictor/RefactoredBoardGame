package jogadores;

import java.util.Random;


public class JogadorSortudo extends Jogador {
    public JogadorSortudo(String cor){
        super(cor);
    }
    @Override
    public int[] rolarDados(Random random) {
        int dado1;
        int dado2;
        do { 
            dado1 = random.nextInt(6) + 1;
            dado2 = random.nextInt(6) + 1;
        } while (dado1 + dado2 < 7);
        return new int[]{dado1, dado2};

    }
}
