package jogadores;

import java.util.Random;
public abstract class Jogador {
    protected String cor;
    protected int posicao;
    protected boolean pularRodada;
    protected boolean jogarNovamente;
    protected int jogadas;
    protected Jogador(String cor){
        this.cor = cor;
        this.posicao = 0;
        this.pularRodada = false;
        this.jogadas = 0;
    }
    public void avancar(int casas){
        this.posicao += casas;
    }
    public void voltar(int casas) {
        this.posicao -= casas;
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
    public String getCor() {
        return cor;
    }
    public void setCor(String cor) {
        this.cor = cor;
    }
    public int getPosicao() {
        return posicao;
    }
    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public boolean isJogarNovamente() {
        return jogarNovamente;
    }

    public void setJogarNovamente(boolean jogarNovamente) {
        this.jogarNovamente = jogarNovamente;
    }
}