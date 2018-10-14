public class Quarto {
    private boolean vago;
    private String tipo;
    private double preco_diaria;
    private String nome_cliente;

    public Quarto(String tipo, double preco_diaria) {
        this.vago = true;
        this.tipo = tipo;
        this.preco_diaria = preco_diaria;
        this.nome_cliente = "";
    }

    public boolean isVago() {
        return vago;
    }

    public void setVago(boolean vago) {
        this.vago = vago;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPreco_diaria() {
        return preco_diaria;
    }

    public void setPreco_diaria(double preco_diaria) {
        this.preco_diaria = preco_diaria;
    }

    public String getNome_cliente() {
        return nome_cliente;
    }

    public void setNome_cliente(String nome_cliente) {
        this.nome_cliente = nome_cliente;
    }

    public boolean reservarQuarto(String nomeCliente) {

        if (this != null && nomeCliente != null && !nomeCliente.equals("")) {

            if (!this.isVago()) {

                return false;

            } else {
                this.setVago(false);
                this.setNome_cliente(nomeCliente);

                return true;
            }

        }

        return false;

    }
}
