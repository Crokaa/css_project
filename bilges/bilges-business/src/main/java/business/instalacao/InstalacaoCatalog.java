/**
 * 
 */
package business.instalacao;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import business.tipo_evento.TipoEvento;
import facade.exceptions.ApplicationException;
import facade.exceptions.InstalacaoDoesntSuportTipoEventoException;
import facade.exceptions.InstalacaoNotValidException;



/**
 * Classe permite gerir as instalacoes
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */
@Stateless
public class InstalacaoCatalog {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Devolve todas as instalacoes que se encontram na base de dados
	 * 
	 * @return lista das instalacoes existentes 
	 */
	public Collection<Instalacao> getInstalacoes() {

		TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.FIND_ALL, Instalacao.class);

		return query.getResultList();

	}


	/**
	 * Devolve a instalacao com o nome e tipo de evento dado 
	 * tipoEvento
	 * 
	 * @param nomeInstalacao nome da instalacao
	 * @param tipoEvento tipo de evento
	 * @return instalacao com nome nomeInstalacao e tipo de evento tipoEvento 
	 * @throws InstalacaoNotValidException quando instalacao com nome 
	 * nomeInstalacao nao existe na base de dados
	 * @throws InstalacaoDoesntSuportTipoEventoException quando instalacao nao 
	 * suporta o tipo dado
	 */
	public Instalacao getInstalacaoByNameComTipo(String nomeInstalacao, TipoEvento tipoEvento) throws ApplicationException {
		
		try {
		TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.FIND_BY_NAME, Instalacao.class);
		query.setParameter(Instalacao.NAME, nomeInstalacao);

		Instalacao i = query.getSingleResult();
		
		if (!i.realizaTipoEvento(tipoEvento)) {
			throw new InstalacaoDoesntSuportTipoEventoException("Instalacao com nome " + nomeInstalacao +
					" nao suporte tipo de evento" + tipoEvento.getNome());
		}
		return i;
		}
		catch(NoResultException e) {
			throw new InstalacaoNotValidException("Instalacao com nome " + nomeInstalacao + " nao existe");
		}
		catch(InstalacaoDoesntSuportTipoEventoException e) {
			throw new InstalacaoNotValidException("Instalacao nao suporta o tipo de evento");
		}
	}
	
	
	/**
	 * Devolve a instalacao com o nome dado
	 * 
	 * @param nomeInstalacao nome da instalacao
	 * @return instalacao com nome nomeInstalacao
	 */
	public Instalacao getInstalacaoByName(String nomeInstalacao) {

		TypedQuery<Instalacao> query = em.createNamedQuery(Instalacao.FIND_BY_NAME, Instalacao.class);
		query.setParameter(Instalacao.NAME, nomeInstalacao);
		Instalacao i = query.getSingleResult();
		em.persist(i);
		return i;

	}
}
