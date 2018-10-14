import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hotel {

    private static List<Quarto> quartos = new ArrayList<Quarto>();

    public Hotel(String [] tipos, float [] precos, int [] quantidade_por_tipo) {
        if ((tipos.length == precos.length) && (precos.length == quantidade_por_tipo.length)) {
            for (int i = 0; i < tipos.length; i++) {
                for (int j = 0; j < quantidade_por_tipo[i]; j++) {
                    Quarto quarto = new Quarto(tipos[i], precos[i]);
                    quartos.add(quarto);
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    public List<Quarto> getQuartosVagos() {
        return quartos.stream().filter(q ->  q.isVago()).collect(Collectors.toList());
    }

    public boolean reservarQuarto(int tipo_quarto, String nome_cliente) {
        boolean status = false;
        List<Quarto> busca_quartos = quartos.stream().filter(q -> q.getTipo().equals( Integer.toString(tipo_quarto)) && q.isVago()).collect(Collectors.toList());

        if ( busca_quartos.size() > 0) {
            if (busca_quartos.get(0).reservarQuarto(nome_cliente))
                status = true;
        }

        return status;
    }

}
