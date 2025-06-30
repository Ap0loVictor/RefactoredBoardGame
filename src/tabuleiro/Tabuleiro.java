package tabuleiro;

import casas.*;
import factory.CasasFactory;
import jogadores.Jogador;
import java.util.*;
public class Tabuleiro {
    
    private ArrayList<Casa> casas;
    private ArrayList<Jogador> jogadores; 
    private List<Jogador>[][] tabuleiroVisual;
    private int totalCasas;
    private int linhas;
    private int colunas;
    private boolean numerosIguais = false; 

    public Tabuleiro(int totalCasas) {
        
        this.totalCasas = totalCasas;
        this.jogadores = new ArrayList<>();
        this.casas = new ArrayList<>();
        this.colunas = 10;
        this.linhas = totalCasas / 10;
        if (totalCasas % 10 != 0) {
            this.linhas++;
        }
        this.tabuleiroVisual = new List[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiroVisual[i][j] = new ArrayList<>();
            }
        }
        inicializarTabuleiroVisual();
        criarTabNormal(totalCasas);
    }
    public void criarTabNormal(int totalCasas) {
        casas = new ArrayList<>();
        for (int i = 0; i < totalCasas; i++) {
            casas.add(i, CasasFactory.criarCasa(0,i));
        }
    }
    public void atualizarTabuleiroVisual() { 
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiroVisual[i][j].clear();
            }
        }
        for (Jogador jogador : getJogadores()) {
            int pos = jogador.getPosicao();
            if (pos >= totalCasas) {
                pos = totalCasas - 1;
                jogador.setPosicao(pos); 
            }
            int linha = pos / colunas;
            int coluna = pos % colunas;
            tabuleiroVisual[linha][coluna].add(jogador);
        }
    }
    
    public boolean adicionarJogador(Jogador jogador){
        this.jogadores.add(jogador);
        return true;
    }
    public boolean iniciarJogo() { 
        Set<Class<?>> tipos = new HashSet<>();
        for (Jogador j : jogadores) {
            tipos.add(j.getClass());
        }
        return tipos.size() >= 2;
    }
    
    private void inicializarTabuleiroVisual() {
        this.colunas = 10;
        this.linhas = totalCasas / 10;
        if (totalCasas % 10 != 0) {
            this.linhas++;
        }
        this.tabuleiroVisual = new List[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                tabuleiroVisual[i][j] = new ArrayList<>();
            }
        }
    }

    public void inicializarTabuleiros(int totalCasas) {  
        this.totalCasas = totalCasas;
        criarTabNormal(totalCasas);
        inicializarTabuleiroVisual();
    }
   
    public void setarCasaEspecial(int posCasaEspecial, int tipoCasa) { 
            casas.set(posCasaEspecial, CasasFactory.criarCasa(tipoCasa, posCasaEspecial));
    }
    
    public boolean existeCasa(int posCasaEspecial) {  
        Casa casa = casas.get(posCasaEspecial);
        return !(casa instanceof CasaNormal);
    }

    public void casasEspeciais(Jogador jogador, List<Jogador> jogadores) {
        int pos = jogador.getPosicao();
        if (pos >= 0 && pos < casas.size()) {
            casas.get(pos).aplicarEfeito(jogador, jogadores);
        }
    }
    
    public boolean isNumerosIguais() {
        return numerosIguais;
    }
    public void setNumerosIguais(boolean numerosIguais) {
        this.numerosIguais = numerosIguais;
    }

    public int getTotalCasas() {
        return totalCasas;
    }

    public void setCasas(ArrayList<Casa> casas) {
        this.casas = casas;
    }

    public ArrayList<Casa> getCasas() {
        return casas;
    }
    public ArrayList<Jogador> getJogadores() {
        return jogadores;
    }
    public void setJogadores(ArrayList<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}

