package business.bilhete;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import business.evento.Evento;

/**
 * Classe que representa um bilhete passe
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity
public class BilhetePasse extends Bilhete {
	
	@OneToMany
	@JoinColumn
	private List<BilheteIndividual> bilhetes;
	
	/**
	 * Construtor necessario pelo JPA
	 */
	public BilhetePasse() {
		bilhetes = new ArrayList<>();
	}
	
	/**
	 * Construtor de bilhete passe com evento e preco
	 * @param evento - evento ao qual se destina o bilhete
	 * @param precoBPasse - preco do bilhete passe
	 */
	public BilhetePasse(Evento evento, float precoBPasse) {
		super(evento, precoBPasse);
		bilhetes = new ArrayList<>();
	}

	/**
	 * Coloca um bilhete individual no bilhete passe
	 * @param bilheteIndividual - bilhete a colocar
	 */
	public void putBilhete(BilheteIndividual bilheteIndividual) {
		bilhetes.add(bilheteIndividual);		
	}

	/**
	 * Devolve todos os bilhetes deste bilhete passe
	 * @return lista com os bilhetes individuais deste bilhete passe
	 */
	public List<BilheteIndividual> getBilhetesIndividuais() {
		return bilhetes;
	}
   
}
