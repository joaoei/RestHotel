public interface IReserva {
    public String listarQuartosDisponiveis();

    public String reservarQuarto(int tipo_quarto, String nome_cliente);
}