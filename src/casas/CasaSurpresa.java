package casas;
import jogadores.*;
import java.util.Random;
import java.util.List;

public class CasaSurpresa extends Casa {
    private static final Random random = new Random();
    
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
                System.out.println("O jogador " + novoJogador.getCor() + " agora é um jogador normal !");
                break;
            case 2:
                novoJogador = new JogadorSortudo(cor);
                System.out.println("Carta sorteada: jogador sortudo !");
                System.out.println("O jogador " + novoJogador.getCor() + " agora é um jogador sortudo !");

                break;
            case 3:
                novoJogador = new JogadorAzarado(cor);
                System.out.println("Carta sorteada: jogador azarado !");
                System.out.println("O jogador " + novoJogador.getCor() + " agora é um jogador azarado !");
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

// 13:
// Casas.Casa surpresa, o jogador deve tirar uma carta aleatória que o fará mudar de
// tipo de jogador de acordo com a carta.
