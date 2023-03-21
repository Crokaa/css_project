package business.bilhete;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import business.evento.Evento;

/**
 * Classe que representa um bilhete
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity
@Inheritance

@NamedQueries({
@NamedQuery(name=Bilhete.FIND_ALL_BY_EVENTO_BY_NAME, 
	query="SELECT b FROM Bilhete b WHERE b.evento = :" + Bilhete.EVENTO_NAME),
})

public abstract class Bilhete {


	public static final String FIND_ALL_BY_EVENTO_BY_NAME = "Bilhete.findByEventoByNome";

	public static final String EVENTO_NAME = "evento";

	private float preco;

	@ManyToOne(optional = false) 
	@JoinColumn
	protected Evento evento;

	@Id 
	@GeneratedValue
	private int id;
	
	@Version
    private long version;


	/**
	 * Construtor necessario pelo JPA
	 */
	Bilhete() {
	}

	/**
	 * Construtor de bilhete com evento atribuido e o preco do bilhete
	 * @param evento - evento ao qual o bilhete se destina
	 * @param precoBilhete - preco do bilhete
	 */
	protected Bilhete(Evento evento, Float precoBilhete) {
		this.evento = evento;
		this.preco = precoBilhete;
	}

	/**
	 * Retorna o preco o bilhete
	 * @return preco do bilhete
	 */
	public float getPreco() {

		return preco;
	}

}
