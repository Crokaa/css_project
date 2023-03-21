package business.instalacao;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import business.lugar.Lugar;
import business.tipo_evento.TipoEvento;

/**
 * Classe representa uma instalacao que tem lugares marcados
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */
@Entity
public class InstalacaoComLugares extends Instalacao{

	@OneToMany
	@JoinColumn(name="INSTALACAO_ID")
	private List<Lugar> lugares;
	
	
	/**
	 * Construtor vazio da classe
	 */
	public InstalacaoComLugares() {
	}
	
	
	@Override
	public boolean lugaresSentados() {
		return true;
	}
	
	@Override
	public boolean realizaTipoEvento(TipoEvento tipoEvento) {
		
		return tipoEvento.isSentados();
	}
	
	
	/**
	 * Devolve os lugares que a instalacao tem 
	 * 
	 * @return lista de lugares da instalacao
	 */
	public Collection<Lugar> getLugares() {
		return lugares;
	}
}
