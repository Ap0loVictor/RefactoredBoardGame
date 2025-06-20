package tabuleiro;

import casas.*;
import jogadores.Jogador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

import java.util.Set;
import java.util.List;

public class Tabuleiro {
    private ArrayList<Jogador> jogadores; 
    private int[] casas;
    private Random random;
    private List<Jogador>[][] tabuleiroVisual; 
    
    
    // private Casas.CasaSorte casaSorte = new Casas.CasaSorte(1)


    public Tabuleiro(){
        this.casas = new int[40];
        this.random = new Random();
        this.jogadores = new ArrayList<>(); 

        this.tabuleiroVisual = new List[4][10];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                tabuleiroVisual[i][j] = new ArrayList<>();
            }
        }
    }

    public void atualizarTabuleiroVisual() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 10; j++) {
                tabuleiroVisual[i][j].clear();
            }
        }
        for (Jogador jogador : jogadores) {
            if (jogador.getPosicao() >= 40) {
                jogador.setPosicao(40);
            }
            if(jogador.getPosicao()<40){
                int linha = jogador.getPosicao() / 10;
                int coluna = jogador.getPosicao() % 10;
                tabuleiroVisual[linha][coluna].add(jogador);
            }
        }
    }

    public void imprimirTabuleiroVisual() {
        System.out.println("\n============= tabuleiro.Tabuleiro Visual =============");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    int numeroCasa = i * 10 + j;
                    System.out.print(numeroCasa + ".[" + tabuleiroVisual[i][j].size() + "]\t"); 
                }
                System.out.println();
            }
            int jogadoresNaCasa40 = 0;
                for (Jogador jogador : jogadores) {
                  if (jogador.getPosicao() == 40) {
                    jogadoresNaCasa40++;
                    }
                }
        System.out.println("40.[" + jogadoresNaCasa40 + "]");
        System.out.println("=================================");

        System.out.println("\nLegenda dos Jogadores:");
            for (Jogador jogador : jogadores) {
                System.out.println(jogador.getClass().getSimpleName() + " | " + jogador.getCor() + " | Posição " + jogador.getPosicao());
            }
        System.out.println("=================================\n");

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
        if (tipos.size() < 2) {
            return false;
        }
        return true;
}
    public boolean inicarJogo(){
        if(validarTiposDeJogadores()){
            return true;
        } else {
            return false;
        }
    }
    public Jogador verificarVencedor() {
    for (Jogador jogador : jogadores) {
        if (jogador.getPosicao() >= casas.length - 1) {
            return jogador;
        }
    }
    return null;
}

    public void jogarRodada(boolean modoDebug){
            if (jogadores.isEmpty()) {
                System.out.println("Adicione jogadores para poder jogar uma nova partida");
                return;
            }
            
            for (Jogador jogador : jogadores) {
            if(jogador.isPularRodada()){
                jogador.setPularRodada(false);
                System.out.println("O jogador " + jogador.getCor() + " está pulando a rodada");
                continue;
            }

            jogador.setJogadas(jogador.getJogadas() + 1);
            boolean repetirJogada;

            do {
                int[] dados = null;
                int soma = 0;

                if(modoDebug){
                    int opc = -1;
                    while (opc < 0) {
                    System.out.println("=============================================");
                    System.out.print("Informe o número de casas que o jogador " + jogador.getCor() + " deve avançar: ");
                    String entrada = scanner.nextLine();
                    try {
                        opc = Integer.parseInt(entrada);
                        if (opc < 0) {
                            System.out.println("Digite um número positivo.");
                        }
                    } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Digite apenas números inteiros.");
                        }
                    }   
                    jogador.avancar(opc);

                    if(jogador.getPosicao() >= 40){
                        vencer(jogador, jogadores);
                        return;
                    }
                    casasEspeciais(jogador, jogadores);
                    repetirJogada = false;
                } else {
                    System.out.println("=============================================");
                    System.out.println("Turno do jogador: " + jogador.getCor());
                    System.out.println("Pressione ENTER para jogar");
                    scanner.nextLine(); 
                    dados = jogador.rolarDados(random);
                    soma = dados[0] + dados[1];
                    System.out.println("Dados rolados: " + dados[0] + " + " + dados[1] + " = " + soma);
                    jogador.avancar(soma);
                
                      if(jogador.getPosicao() >= 40){
                            vencer(jogador, jogadores);
                            return;
                        }
                    casasEspeciais(jogador, jogadores);
                    repetirJogada = (dados[0] == dados[1]);
                }
                System.out.println("O jogador " + jogador.getCor()  + " esta na casa " + jogador.getPosicao() );
                if(repetirJogada){
                    System.out.println("O jogador " + jogador.getCor() + " tirou valores iguais e jogará novamente");
                }
            } while (repetirJogada);
        }

    }
    private void vencer(Jogador jogador, ArrayList<Jogador> jogadores){
        System.out.println("=============================================");
        System.out.println("O jogador " + jogador.getCor() + " venceu !");
        for(int i = 0; i < jogadores.size();  i++){
            System.out.println("Número de jogadas do jogador " + jogadores.get(i).getCor() + ": " +jogadores.get(i).getJogadas());
        }
        System.out.println("=============================================");
    }

    public void casasEspeciais(Jogador jogador, ArrayList<Jogador> jogadores){

        int pos = jogador.getPosicao();
        if(pos == 10 || pos == 25 || pos == 38){
            CasaStop casaStop = new CasaStop(pos);
            casaStop.aplicarEfeito(jogador, jogadores);
        }
        if(pos == 13){
            CasaSurpresa casaSurpresa = new CasaSurpresa(pos);
            casaSurpresa.aplicarEfeito(jogador, jogadores);
        }
        if(pos == 5 || pos == 15 || pos == 30){
            CasaSorte casaSorte = new CasaSorte(pos);
            casaSorte.aplicarEfeito(jogador, jogadores);
        }
        if(pos == 17 || pos == 27){
                CasaVolta casaVolta = new CasaVolta(pos);
                casaVolta.aplicarEfeito(jogador, jogadores);
        }
        if(pos == 20 || pos == 35){
            CasaMagica casaMagica = new CasaMagica(pos);
            casaMagica.aplicarEfeito( jogador, jogadores);
        }
    }
    
    public int[] getCasas() {
        return casas;
    }
    public void setCasas(int[] casas) {
        this.casas = casas;
    }
    public Random getRandom() {
        return random;
    }
    public void setRandom(Random random) {
        this.random = random;
    }
    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }
    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
