package jogadores;

import java.util.Random;
public abstract class Jogador {
    protected Cor cor;
    protected int posicao;
    protected boolean pularRodada;
    protected int jogadas;
    protected Jogador(Cor cor){
        this.cor = cor;
        this.posicao = 0;
        this.pularRodada = false;
        this.jogadas = 0;
    }
    public void avancar(int casas){
        posicao += casas;
    }
    public abstract int[] rolarDados(Random random);

    // métodos getters e setters
    public int getJogadas() {
        return jogadas;
    }
    public void setJogadas(int jogadas) {
        this.jogadas = jogadas;
    }
    public void setPularRodada(boolean pularRodada) {
        this.pularRodada = pularRodada;
    }
    public boolean isPularRodada() {
        return pularRodada;
    } 
    public Cor getCor() {
        return cor;
    }
    public void setCor(Cor cor) {
        this.cor = cor;
    }
    public int getPosicao() {
        return posicao;
    }
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

}