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
            case "AZUL": return "\u001B[34m";
            case "VERMELHO": return "\u001B[31m";
            case "AMARELO": return "\u001B[33m";
            case "VERDE": return "\u001B[32m";
            case "PRETO": return "\u001B[30m";
            case "BRANCO": return "\u001B[37m";
            default:
                return "";
        }
    }
    
    @Override
    public String toString() {
        return exibirColorido();
    }
    
}
