package casas;
import jogadores.Jogador;
import java.util.List;
    
public abstract class Casa 
{
    protected int indiceCasa;
    protected Casa (int indiceCasa){
        this.indiceCasa = indiceCasa;
    }
    public abstract void aplicarEfeito(Jogador jogadorAtual, List<Jogador> jogadores);
    
    public int getIndiceCasa() {
        return indiceCasa;
    }
    public void setIndiceCasa(int indiceCasa){
        this.indiceCasa = indiceCasa;
    }
}