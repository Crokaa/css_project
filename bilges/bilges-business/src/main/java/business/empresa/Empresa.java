/**
 * 
 */
package business.empresa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import business.tipo_evento.TipoEvento;

/**
 * Classe que representa uma empresa
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */


@Entity
@NamedQueries({
	@NamedQuery(name=Empresa.FIND_BY_NUM, 
			query="SELECT e FROM Empresa e WHERE e.numRegisto = :" + Empresa.NUM),

})
public class Empresa {

	public static final String FIND_BY_NUM = "Empresa.findByNum";

	public static final String NUM = "numRegisto";

	@Column(unique = true, nullable = false)
	private String nome;

	@Enumerated(EnumType.STRING)
	@ElementCollection
	private List<TipoEvento> tiposEventos;

	@Column(unique = true, nullable = false)
	private long numRegisto;

	@Id
	@GeneratedValue
	private int id;

	/**
	 * Diz se empresa eh certificada
	 * @param tipoEvento - tipo do evento
	 * @return se a empresa eh certificada ou seja se tem este tipo de eventos
	 */
	public boolean isCertificada(TipoEvento tipoEvento) {
		
		return tiposEventos.contains(tipoEvento);
	}

}
