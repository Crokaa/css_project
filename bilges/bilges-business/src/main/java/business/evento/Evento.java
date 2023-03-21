package business.evento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import business.empresa.Empresa;
import business.instalacao.Instalacao;
import business.tipo_evento.TipoEvento;
import business.utils.DateUtils;

/**
 * Classe representa um evento
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Entity

@NamedQueries({
	@NamedQuery(name=Evento.FIND_ALL, 
		query="SELECT i FROM Evento i WHERE i.instalacao = NULL"),
	@NamedQuery(name=Evento.FIND_BY_NAME,
		query="SELECT e FROM Evento e WHERE e.nome = :" + Evento.NAME),
})


public class Evento {

	public static final String FIND_BY_NAME = "Event.findByName";
	public static final String NAME = "nome";
	public static final String FIND_ALL = "Evento.findAll";
	
	@Basic
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TipoEvento tipoEvento;

	@Column(unique = true, nullable = false)
	private String nome;

	@OneToMany(cascade= CascadeType.ALL)
	@JoinColumn(name="EVENTO_ID")
	private List<DiaEvento> datas;

	@ManyToOne(optional = false)
	private Empresa empresa;

	@ManyToOne(optional = true)
	@JoinColumn
	private Instalacao instalacao;

	@Column(nullable = false)
	private boolean acceptBilhetePasse;

	@Column
	private float precoBilhetePasse;

	@Id
	@GeneratedValue
	private int id;
	
	@Version
    private long version;

	/**
	 * Construtor vazio da classe
	 */
	Evento() {
	}
	
	/**
	 * Construtor da classe
	 * 
	 * @param tipoEvento tipo do evento
	 * @param nome nome do evento
	 * @param datas lista de datas em que o evento acontece
	 * @param empresa empresa que realiza o evento
	 */
	public Evento(TipoEvento tipoEvento, String nome, List<Map.Entry<Date, Date>> datas, Empresa empresa) {

		this.tipoEvento = tipoEvento;
		this.nome = nome;
		this.empresa = empresa;

		this.datas = new ArrayList<>();
		for (Map.Entry<Date,Date> d : datas) 
			this.datas.add(new DiaEvento(d.getKey(), d.getValue()));

	}

	
	/**
	 * Verifica se a data de venda de bilhetes valida, ou seja, � no futuro e 
	 * se � antes da primeira data do evento
	 * 
	 * @param inicioVenda data de venda dos bilhetes
	 * @return true se a data eh valida, dalse caso contrario
	 */
	public boolean dataVendaValida(Date inicioVenda) {

		return inicioVenda.after(DateUtils.getCurrentTime()) && inicioVenda.before(datas.get(0).getDataSemHoras());
	}

	
	/**
	 * Devolve o preco do bilhete passe do evento
	 * 
	 * @return preco bilhete passe
	 */
	public float getPrecoBPasse() {
		return precoBilhetePasse;
	}

	
	/**
	 * Devolve o tipo de evento do evento
	 * @return tipo de evento
	 */
	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}


	/**
	 * Verifica se o evento aceita bilhetes passe
	 * 
	 * @return true caso o evento aceite bilhetes passo, false caso 
	 * contrario
	 */
	public boolean aceitaBilhetesPasse() {
		return acceptBilhetePasse;
	}
	
	
	/**
	 * Devolve os dias em que o evento ocorre
	 * 
	 * @return lista dos dias do evento
	 */
	public List<DiaEvento> getDiasEvento() {
		return datas;
	}

	
	/**
	 * Devolve o nome do evento
	 * 
	 * @return nome do evento
	 */
	public String getNome() {
		return nome;
	}

	
	/**
	 * Verifica se o evento tem uma instalacao atribuida
	 * 
	 * @return true se instalacao esta tribuida, false caso contrario
	 */
	public boolean instalacaoAtribuida() {
		return (instalacao != null);
	}

	
	/**
	 * Atribui uma instalacao ao evento
	 * 
	 * @param instalacao instalacao a atribuir
	 */
	public void setInstalacao(Instalacao instalacao) {
		this.instalacao = instalacao;
	}

	
	/**
	 * Atribui um preco ao bilhete passe e atualiza que o evento aceita 
	 * bilhetes passe
	 * 
	 * @param precoBilhetePass preco do bilhete passe
	 */
	public void setPrecoBilhete(Float precoBilhetePass) {
		this.precoBilhetePasse = precoBilhetePass;
		acceptBilhetePasse = true;
	}

}
