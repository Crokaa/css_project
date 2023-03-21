package business.lugar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Classe que representa um lugar
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity
public class Lugar {

	@Column(nullable = false)
	private int cadeira;

	@Column(nullable = false)
	private String fila;

	@Id
	@GeneratedValue
	private int id;

	public Lugar() {
		
	}
	
	/**
	 * Diz a fila a que o lugar pertence
	 * @return fila do lugar
	 */
	public String getFila() {
		return fila;
	}

	/**
	 * Diz a cadeira do lugar
	 * @return numero da cadeira
	 */
	public int getCadeira() {
		return cadeira;
	}

}
