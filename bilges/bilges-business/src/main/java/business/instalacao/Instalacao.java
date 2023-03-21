package business.instalacao;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import business.evento.DiaEvento;
import business.tipo_evento.TipoEvento;




/**
 * Classe que representa uma instalacao
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity
@Inheritance
@NamedQueries({
	@NamedQuery(name=Instalacao.FIND_ALL, 
			query="SELECT i FROM Instalacao i"),
	@NamedQuery(name=Instalacao.FIND_BY_NAME, 
	query="SELECT i FROM Instalacao i WHERE i.nome = :" + Instalacao.NAME),

})
public class Instalacao {

	public static final String FIND_ALL = "Instalacao.findAll";

	public static final String FIND_BY_NAME = "Instalacao.findByName";

	public static final String NAME = "nome";

	@Column(nullable = false, unique = true)
	private int numRegisto;

	@Column(nullable = false, unique = true)
	private String nome;

	@Column(nullable = false)
	private int capacidade;

	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="INSTALACAO_ID")
	private List<DiaEvento> datasOcupadas;

	@Id
	@GeneratedValue
	private int id;
	
	@Version
    private long version;

	
	/**
	 * Construtor vazio da classe
	 */
	public Instalacao() {
		//caso de uso nao implementado
	}

	
	/**
	 * Verifica se a instalacao tem lugares sentados
	 * 
	 * @return true caso tenha lugares entados, false caso contrario
	 */
	public boolean lugaresSentados() {
		return false;
	}

	
	/**
	 * Devolve a capacidade da instalacao
	 * 
	 * @return capacidade instalacao
	 */
	public int getCapacidade() {
		return capacidade;
	}

	
	/**
	 * Devolve o nome da instalacao
	 * 
	 * @return nome da instalacao
	 */
	public String getNome() {
		return nome;
	}

	
	/**
	 * Verifica se a instalacao realiza o tipo de evento dado
	 * 
	 * @param tipoEvento tipo de evento
	 * @return true caso instalacao suporte o tipoEvento, false caso contrario
	 */
	public boolean realizaTipoEvento(TipoEvento tipoEvento) {

		return !tipoEvento.isSentados();
	}

	
	/**
	 * Verifica se a instalacao se encontra livre durante os dias de evento 
	 * dados
	 * 
	 * @param datasEvento dias de evento a verificar
	 * @return true caso a instalacao esteja livre durante todos os dias de 
	 * evento em datasEvento, false caso contrario
	 */
	public boolean datasLives(List<DiaEvento> datasEvento) {

		//System.out.println(datasOcupadas);

		List<Date> datas = datasOcupadas.stream().map(d -> d.getDataSemHoras()).collect(Collectors.toList());
		/*
		for (DiaEvento date : datasOcupadas) {
			System.out.println(1);
			System.out.println(date);
		}*/

		for (DiaEvento d : datasEvento) { 
			for (DiaEvento dOcupada : datasOcupadas) {
				if (dOcupada.temConflito(d)) {
					return false;
				}
			}
		}

		return true;
	}

	
	/**
	 * Atribui novas datas ah instalacao, ou seja, a instalacao passa a estar
	 * ocupada tambem nestas datas
	 * 
	 * @param datasEvento datas
	 */
	public void setNovasDatasEventoOcupadas(List<DiaEvento> datasEvento) {

		// nao garante ordem
		//List<Date> datas = datasEvento.stream().map(d -> d.getDataSemHoras()).collect(Collectors.toList());

		datasOcupadas.addAll(datasEvento);

	}



}
