package jogadores;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;


public class GerenciadorJogadores {
    private List<Jogador> jogadores;
    
    public GerenciadorJogadores(){
        this.jogadores = new ArrayList<>();
    }


    public boolean adicionarJogador(Jogador jogador){
        if(jogadores.size() < 6){
            jogadores.add(jogador);
            return true;
        }
        return false;
    }

    Scanner scanner = new Scanner(System.in);

    public boolean validarTiposDeJogadores() {
        Set<Class<?>> tipos = new HashSet<>();
        for (Jogador j : jogadores) {
            tipos.add(j.getClass());
        }
        return tipos.size() >= 2;
    }
    public int lerQuantidadeJogadores() {
        int quantidade;
        do {
            System.out.print("Digite o número de jogadores (2 a 6): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Entrada inválida. Digite um número inteiro entre 2 e 6.");
                scanner.next();
            }
            quantidade = scanner.nextInt();
            scanner.nextLine(); // limpar a quebra de linha
            if (quantidade < 2 || quantidade > 6) {
                System.out.println("Número inválido. Deve ser entre 2 e 6.");
            }
        } while (quantidade < 2 || quantidade > 6);
        return quantidade;
    }

    public List<Jogador> getJogadores() {
    return jogadores;
}

    
}
