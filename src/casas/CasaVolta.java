package casas;
import jogadores.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CasaVolta extends Casa {
    public CasaVolta(int indiceCasa) {
        super(indiceCasa);
    }

    @Override
    public void aplicarEfeito(Jogador jogador, List<Jogador> jogadores) {
        Scanner s = new Scanner(System.in);
        System.out.println("\nCasas.Casa Restart!!!");
        System.out.println("Escolha um jogador para voltar ao início do jogo: ");
        ArrayList<Jogador> outroJogadores = new ArrayList<>();
        for (Jogador jogador2 : jogadores) {
            if (!jogador2.equals(jogador)) {
                outroJogadores.add(jogador2);
            }
        }

        if (outroJogadores.isEmpty()) {
            System.out.println("Não há outros jogadores para escolher.");
            return;
        }

        for (int i = 0; i < outroJogadores.size(); i++) {
            System.out.println((i + 1) + " - " + outroJogadores.get(i).getCor());
        }

        int escolha = -1;
        while (true) {
            System.out.print("Digite o número do jogador escolhido: ");
            if (s.hasNextInt()) {
                escolha = s.nextInt();
                s.nextLine();
                if (escolha >= 1 && escolha <= outroJogadores.size()) {
                    break;
                } else {
                    System.out.println("Escolha inválida. Digite um número entre 1 e " + outroJogadores.size() + ".");
                }
            } else {
                System.out.println("Entrada inválida. Digite um número válido.");
                s.next(); // descarta entrada inválida
            }
        }

        Jogador escolhido = outroJogadores.get(escolha - 1);
        escolhido.setPosicao(0);
        System.out.println("O jogador " + escolhido.getCor() + " foi escolhido e está na posição zero.");
    }
}
