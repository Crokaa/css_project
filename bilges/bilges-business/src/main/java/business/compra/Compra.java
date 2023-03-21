package business.compra;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import business.bilhete.Bilhete;
import business.utils.MockPagamento;

/**
 * Classe que representa uma compra/reserva
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity
public class Compra {

	@OneToMany @JoinColumn
	private List<Bilhete> bilhetes = new ArrayList<>();

	@Column(nullable = false)
	private boolean isBilhetePasse;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date dataReserva;

	@Column(nullable = false)
	private boolean pago;

	@Column(nullable = false)
	private String mail;

	@Embedded
	private PagamentoInfo pagamentoInfo;

	@Id
	@GeneratedValue
	private int id;

	/**
	 * Construtor necessario pelo JPA
	 */
	Compra() {

	}

	/**
	 * Contrutor da compra com os bilhetes desta, se eh um bilhete passe, data da reserva e o mail
	 * @param bilhetes - bilhetes que se vao comprar
	 * @param isBilhetePasse - se eh um bilhete passe ou nao
	 * @param dataReserva - data da qual se realiza a reserva
	 * @param mail - mail de quem fez a reserva/compra
	 */
	public Compra(List<? extends Bilhete> bilhetes, boolean isBilhetePasse, Date dataReserva, String mail) {
		
		this.bilhetes.addAll(bilhetes);
		this.isBilhetePasse = isBilhetePasse;
		this.dataReserva = dataReserva;
		this.mail = mail;
		this.pago = false;
		
		float preco = 0;
		
		for(Bilhete b : bilhetes) {
			preco += b.getPreco();
		}
		
		this.pagamentoInfo = new PagamentoInfo(MockPagamento.getEntidade(), MockPagamento.getReferencia(), preco);
	}
	
	/**
	 * Devolve a informacao do pagamento
	 * @return informacao de pagamento da reserva/compra
	 */
	public PagamentoInfo getPagamentoInfo() {
		return pagamentoInfo;
	}
	
	/**
	 * Construtor de compra com bilhetes passe, data da reserva, se eh um bilhete passe, mail do cliente que comprou e preco
	 * @param bilhetesPasse - lista de bilhetes passe
	 * @param dataReserva - data da reserva
	 * @param isBilhetePasse - se eh um bilhete passe
	 * @param mail - mail de quem realizou a compra/reserva
	 * @param preco - preco da compra
	 */
	public Compra(List<? extends Bilhete> bilhetesPasse, Date dataReserva, boolean isBilhetePasse, String mail, float preco) {
		
		this.bilhetes.addAll(bilhetesPasse);
		this.isBilhetePasse = isBilhetePasse;
		this.mail = mail;
		this.dataReserva = dataReserva;
		this.pago = false;
		
		this.pagamentoInfo = new PagamentoInfo(MockPagamento.getEntidade(), MockPagamento.getReferencia(), preco);
			
	}

}
