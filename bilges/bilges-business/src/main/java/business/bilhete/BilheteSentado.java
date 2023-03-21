package business.bilhete;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import business.evento.DiaEvento;
import business.evento.Evento;
import business.lugar.Lugar;

/**
 * Classe que representa um bilhete sentado
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity
public class BilheteSentado extends BilheteIndividual {
	
	@OneToOne(optional = true)
	private Lugar lugar;

	/**
	 * Constutor necessario pelo JPA
	 */
	BilheteSentado() {

	}

	/**
	 * Construtor de bilhete sentado com evento, dia do evento, lugar, preco e estado do bilhete
	 * @param evento - evento ao qual o bilhete se destina
	 * @param d - dia do evento
	 * @param l - lugar ao qual o bilhete se destina
	 * @param precoBilhete - preco do bilhete
	 * @param estado - estado inicial do bilhete
	 */
	public BilheteSentado(Evento evento, DiaEvento d, Lugar l, Float precoBilhete, EstadoBilhete estado) {

		super(evento, d, precoBilhete, estado);
		this.lugar = l;
	}

	/**
	 * Devolve o lugar ao qual o bilhete se destina
	 * @return lugar do bilhete
	 */
	public Lugar getLugar() {
		return lugar;
	}
}
