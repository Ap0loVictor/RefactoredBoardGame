package casas;
import jogadores.*;
import java.util.Random;
import java.util.List;

public class CasaSurpresa extends Casa {
    private static final Random random = new Random();
    private static final String THE_PLAY = "O JOGADOR ";
    public CasaSurpresa(int indiceCasa) {
        super(indiceCasa);
    }

    @Override
    public void aplicarEfeito(Jogador jogador, List<Jogador> jogadores) {

    System.out.println("\nCasas.Casa Surpresa!!!\nO jogador deve sortear uma carta para mudar seu tipo.");

        int indice = jogadores.indexOf(jogador);
        int sorteio = random.nextInt(3) + 1;
        Cor cor = jogador.getCor();
        int posicao = jogador.getPosicao();
        Jogador novoJogador = null;
        switch (sorteio) {
            case 1:
                novoJogador = new JogadorNormal(cor);
                System.out.println("Carta sorteada: jogador normal !");
                System.out.println( THE_PLAY + novoJogador.getCor() + " AGORA É UM JOGADOR NORMAL!");
                break;
            case 2:
                novoJogador = new JogadorSortudo(cor);
                System.out.println("Carta sorteada: jogador sortudo !");
                System.out.println(THE_PLAY + novoJogador.getCor() + " AGORA É UM JOGADOR SORTUDO!");

                break;
            case 3:
                novoJogador = new JogadorAzarado(cor);
                System.out.println("Carta sorteada: jogador azarado !");
                System.out.println(THE_PLAY + novoJogador.getCor() + " AGORA É UM JOGADOR AZARADO!");
                break;
            default:
                break;
        }
        if (novoJogador != null) {
            novoJogador.setPosicao(posicao);
            jogadores.set(indice, novoJogador);
        }
    }
}