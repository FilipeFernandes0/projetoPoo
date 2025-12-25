package projetoPOO;

import java.time.LocalDate;

public class Movimento {
    private String descricao;
    private double valor;
    private LocalDate data;
    private String categoria;
    private String formaPagamento; // <--- NOVO CAMPO
    private String tipo; // "Receita" ou "Despesa"

    // Construtor atualizado
    public Movimento(String descricao, double valor, LocalDate data, String categoria, String formaPagamento, String tipo) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
        this.categoria = categoria;
        this.formaPagamento = formaPagamento;
        this.tipo = tipo;
    }

    // Getters
    public String getDescricao() { return descricao; }
    public double getValor() { return valor; }
    public LocalDate getData() { return data; }
    public String getCategoria() { return categoria; }
    public String getFormaPagamento() { return formaPagamento; } // <--- NOVO GETTER
    public String getTipo() { return tipo; }

    @Override
    public String toString() {
        return tipo + ": " + descricao + " | " + valor + "€ | " + categoria + " | Pag: " + formaPagamento;
    }
    
 // Método para converter o objeto numa linha de texto (CSV)
    public String paraLinhaFicheiro() {
        return tipo + ";" + descricao + ";" + valor + ";" + data + ";" + categoria + ";" + formaPagamento;
    }

    // Método estático para recriar o objeto a partir de uma linha
    public static Movimento deLinhaFicheiro(String linha) {
        String[] campos = linha.split(";");
        // Ordem: 0:Tipo, 1:Desc, 2:Valor, 3:Data, 4:Cat, 5:Pag
        return new Movimento(
            campos[1], // Descrição
            Double.parseDouble(campos[2]), // Valor
            java.time.LocalDate.parse(campos[3]), // Data
            campos[4], // Categoria
            campos[5], // Pagamento
            campos[0]  // Tipo
        );
    }
}