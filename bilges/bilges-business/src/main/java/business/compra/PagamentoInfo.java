package business.compra;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Classe que representa ua informacao de um pagamento
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Embeddable
public class PagamentoInfo {

	@Column(nullable = false)
	private int referencia;
	
	@Column(nullable = false)
	private int entidade;
	
	@Column(nullable = false)
	private float precoTotal;
	
	/**
	 * Constutor necessario pelo JPA
	 */
	public PagamentoInfo() {
		
	}
	
	/**
	 * Construtor com entidade, referencia e preco
	 * @param entidade - entidade de quem fez o pagamento
	 * @param referencia - referencia de quem fez o pagamento
	 * @param preco - preco a pagar/pago
	 */
	public PagamentoInfo(int entidade, int referencia, float preco) {
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
