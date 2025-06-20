package jogadores;

import java.util.Random;


public class JogadorAzarado extends Jogador {
    public JogadorAzarado(String cor){
        super(cor);
    }
    @Override
    public int[] rolarDados(Random random) {
        int dado1;
        int dado2;
        do {
            dado1 = random.nextInt(6) + 1;
            dado2 = random.nextInt(6) + 1;
        } while (dado1 + dado2 > 6);
        return new int[]{dado1, dado2};
    }
}
