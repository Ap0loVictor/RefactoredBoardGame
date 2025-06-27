package factory;
import casas.*;
public class CasasFactory {
    private CasasFactory() {}
    public static Casa criarCasa(int tipo, int pos) {
        switch (tipo) {
            case 1:
                return new CasaMagica(pos);
            case 2:
                return new CasaSorte(pos);
            case 3:
                return new CasaStop(pos);
            case 4:
                return new CasaSurpresa(pos);
            case 5:
                return new CasaVolta(pos);
            case 6:
                return new CasaAzar(pos);
            case 7:
                return new CasaJogaNovamente(pos);
            default:
                return new CasaNormal(pos);
        }
    }
}
