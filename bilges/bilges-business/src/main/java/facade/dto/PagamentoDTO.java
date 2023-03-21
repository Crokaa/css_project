package facade.dto;

import java.io.Serializable;

/**
 * Classe que representa ua informacao de um pagamento
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */


public class PagamentoDTO implements Serializable{
	
	private static final long serialVersionUID = -8183878881701127076L;

	private int referencia;
	
	private int entidade;
	
	private float precoTotal;
	
	public int getReferencia() {
		return referencia;
	}

	public int getEntidade() {
		return entidade;
	}

	public float getPreco() {
		return precoTotal;
	}

	public PagamentoDTO(int entidade, int referencia, float preco) {
		this.entidade = entidade;
		this.referencia = referencia;
		this.precoTotal = preco;
	}
	
	/**
	 * Informacao do pagamento em String
	 * @return informacao do pagamento
	 */
	@Override
	public String toString() {
		return "Entidade: "+ this.entidade + " Ref: " + this.referencia + " Valor: " + this.precoTotal;
	}

}
