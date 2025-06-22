package factory;
import casas.*;
public class CasasFactory {
    private CasasFactory() {}
    public static Casa criarCasa(int pos) {
        if(pos == 10 || pos == 25 || pos == 38) {
            return new CasaStop(pos);
        }
        if(pos == 13){
            return new CasaSurpresa(pos);
        }
        if(pos == 5 || pos == 15 || pos == 30){
            return new CasaSorte(pos);
        }
        if(pos == 17 || pos == 27){
            return new CasaVolta(pos);
        }
        if(pos == 20 || pos == 35){
            return new CasaMagica(pos);
        }
        return new CasaNormal(pos);
    }
}
