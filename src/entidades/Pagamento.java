/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package entidades;

/**
 *
 * @author gusta
 */
public enum Pagamento {
    CARTAO_DE_CREDITO(0),
    DINHEIRO(1),
    BOLETO(2),
    DEPOSITO(3),
    CONVENIO(4);

    private int numero;

    Pagamento(int numero) {
        this.numero = numero;
    }

    public static Pagamento selecionarPagamento(int numero) {
        switch (numero) {
            case 0:
                return CARTAO_DE_CREDITO;
            case 1:
                return DINHEIRO;
            case 2:
                return BOLETO;
            case 3:
                return DEPOSITO;
            case 4:
                return CONVENIO;
            default:
                throw new IllegalArgumentException("Número de pagamento inválido: " + numero);
        }
    }
}
