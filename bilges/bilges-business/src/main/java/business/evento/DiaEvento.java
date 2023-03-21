package business.evento;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Classe representa um dia da classe Evento
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 */

@Entity
public class DiaEvento  {

	@Id
	@GeneratedValue
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fim;

	
	/**
	 * Construtor vazio da classe
	 */
	DiaEvento() {

	}


	/**
	 * Construtor da classe
	 * @param inicio data com hora que indica o incio do evento
	 * @param fim data com hora que indica o fim do evento
	 */
	public DiaEvento(Date inicio, Date fim) {
		this.inicio = inicio;
		this.fim = fim;
	}
	
	
	/**
	 * Indica a data do evento com o dia, m�s e ano
	 * @return data que ocorre o evento
	 */
	@SuppressWarnings("deprecation")
	public Date getDataSemHoras() {
		
		return new Date(inicio.getYear(), inicio.getMonth(), inicio.getDate());
	}

	
	/**
	 * Indica a data com hora, dia, m�s e ano em que o evento comeca
	 * @return data em que o evento comeca
	 */
	public Date getInicio() {
		return inicio;
	}

	
	/**
	 * Verifica se o DiaEvento atual interseta outro DiaEvento, ou seja, se
	 * eles se sobrepoem (conflito)
	 * @param d DiaEvento a ver se tem conflito
	 * @return true se existe conflito, false caso contrario
	 */
	public boolean temConflito(DiaEvento d) {

		//nao sao no mesmo dia
		if (!this.getDataSemHoras().equals(d.getDataSemHoras())) {
			return false;
		}

		// inicio e fim antes do inicio do outro Dia OU inicio depois do fim
		return  !(d.inicio.after(fim) || (d.inicio.before(inicio) && d.fim.before(inicio)));

	}

}
