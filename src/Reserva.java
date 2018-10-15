import javax.ws.rs.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

@Path("/reserva")
public class Reserva implements IReserva {
    private static String[] tipos = {"1", "2"};
    private static float[] precos = {23.0f, 43.0f};
    private static int[] quantidade_por_tipo = {5, 5};


    private static Hotel hotel  = new Hotel(tipos, precos, quantidade_por_tipo);

    @Context
    private HttpServletResponse servletResponse;

    private void allowCrossDomainAccess() {
        if (servletResponse != null){
            servletResponse.setHeader("Access-Control-Allow-Origin", "*");
        }
    }

    // The Java method will process HTTP GET requests
    @GET
    // The Java method will produce content identified by the MIME Media type "text/plain"
    @Produces("text/plain")
    public String hotelCriado() {
        allowCrossDomainAccess();
        return "Hotel Criado";
    }

    @GET
    @Path("/listar_quartos")
    @Produces("application/json")
    @Override
    public String listarQuartosDisponiveis() {
        allowCrossDomainAccess();

        boolean status = true;
        String resp = "";
        // Array de quartos
        JSONArray quartos = new JSONArray();

        for (Quarto q : hotel.getQuartosVagos()) {
            JSONObject my_obj = new JSONObject();

            try {
                my_obj.put("vago", q.isVago());
                my_obj.put("tipo", q.getTipo());
                my_obj.put("preco_diaria", q.getPreco_diaria());
                my_obj.put("nome_cliente", q.getNome_cliente());

                quartos.put(my_obj);
            } catch (JSONException e) {
                status = false;
                resp = e.getMessage();
                break;
            }
        }

        JSONObject resposta = new JSONObject();

        if (status) {
            try {
                resposta.put("resposta", quartos);
                resposta.put("status", status);

                return resposta.toString();
            } catch (JSONException e) {
                return "\"status\": false, \"resposta\": \"" + e.getMessage() +  "\" ";
            }
        } else {
            return "\"status\": false, \"resposta\": \""+ resp +"\" ";
        }

    }

    @PUT
    @Path("/reservar")
    @Produces("application/json")
    @Override
    public String reservarQuarto(
            @QueryParam("tipo") int tipo_quarto,
            @QueryParam("nome") String nome_cliente) {
        allowCrossDomainAccess();

        boolean status = hotel.reservarQuarto(tipo_quarto, nome_cliente);

        if (status) {
            return "{\"status\": " +status+ ", \"resposta\": \"Quarto reservado com sucesso para o cliente "+ nome_cliente +"\"}";
        } else {
            return "{\"status\": " +status+ ", \"resposta\": \"Não foi possível reservar o quarto\"}";
        }
    }


}
