package jogadores;

public class Cor {
    private String nomeCor;

    public Cor(String nomeCor) {
        this.nomeCor = nomeCor.toUpperCase();
    }

    public String exibirColorido() {
        return this.aplicarCor() + nomeCor + "\u001B[0m"; // cor ANSI + nome da cor + reset do terminal para não dar problema
    }

    public String aplicarCor() {
        switch (nomeCor) {
            case "VERMELHO": return "\u001B[31m";
            case "VERDE": return "\u001B[32m";
            case "AZUL": return "\u001B[38;5;39m"; 
            case "AMARELO": return "\u001B[33m";
            case "PRETO": return "\u001B[30m";
            case "ROSA": return "\u001B[95m";
            default:
                return "";
        }
    }

    @Override
    public String toString() {
        return exibirColorido();
    }
    
}
