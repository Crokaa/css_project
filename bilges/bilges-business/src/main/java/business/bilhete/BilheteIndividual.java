package business.bilhete;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import business.evento.DiaEvento;
import business.evento.Evento;

/**
 * Classe que representa um bilhete individual
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity
public class BilheteIndividual extends Bilhete{

	@Enumerated(EnumType.STRING)
	protected EstadoBilhete estado;

	@ManyToOne(optional = true) 
	@JoinColumn
	private DiaEvento data;

	/**
	 * Construtor necessario pelo JPA
	 */
	BilheteIndividual() {
	}
	
	/**
	 * Diz se o bilhete esta livre
	 * @return true se estado do bilhete for livre, false caso contrario
	 */
	public boolean isLivre() {
		return estado.equals(EstadoBilhete.LIVRE);
	}

	/**
	 * Reserva o bilhete
	 */
	public void reservar() {
		this.estado = EstadoBilhete.RESERVADO;
	}

	/**
	 * Devolve o dia do evento
	 * @return dia do evento a que o bilhete se destina
	 */
	public DiaEvento getDiaEvento() {
		return data;
	}
	
	/**
	 * Constutor de bilhete individual com evento, dia do evento, preco e estado
	 * @param evento - evento ao qual o bilhete se destina
	 * @param d - dia a que se realiza
	 * @param precoBilhete - preco do bilhete
	 * @param estado - estado a que o bilhete se encontra assim que construido
	 */
	public BilheteIndividual(Evento evento, DiaEvento d, Float precoBilhete, EstadoBilhete estado) {

		super(evento, precoBilhete);
		this.data = d;
		this.estado = estado;
	}

	/**
	 * Devolve o estado do bilhete
	 * @return estado do bilhete
	 */
	public EstadoBilhete getEstado() {
		return estado;
	}

	/**
	 * Coloca o bilhete com um estado dado
	 * @param estado - estado a qual o bilhete fica
	 */
	public void setEstado(EstadoBilhete estado) {
		this.estado = estado;
	}
	
		
}
