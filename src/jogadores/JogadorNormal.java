package jogadores;

import java.util.Random;

public class JogadorNormal extends Jogador {
    public JogadorNormal(String cor){
        super(cor);
    }
    @Override
    public int[] rolarDados(Random random) {
        int dado1;
        int dado2;
        dado1 = random.nextInt(6) + 1;
        dado2 = random.nextInt(6) + 1;
        return new int[]{dado1, dado2};         
    }
}
