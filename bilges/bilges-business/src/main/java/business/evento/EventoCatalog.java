/**
 * 
 */
package business.evento;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.empresa.Empresa;
import business.tipo_evento.TipoEvento;
import facade.exceptions.ApplicationException;
import facade.exceptions.EventoAlreadyHasInstalacaoException;
import facade.exceptions.EventoNotValidException;


/**
 * Classe que permite gerir os Eventos
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */
@Stateless
public class EventoCatalog {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Verifica se nao existe nenhum evento com o nome dado na base de dados
	 * 
	 * @param nome nome do evento
	 * @return true se nao existe outro evento com o mesmo nome, false caso 
	 * contrario
	 */
	public boolean nomeIsUnique(String nome) {

		TypedQuery<Evento> query = em.createNamedQuery(Evento.FIND_BY_NAME, Evento.class);
		query.setParameter(Evento.NAME, nome);

		return query.getResultList().isEmpty();
	}


	/**
	 * Cria um novo evento
	 * 
	 * @param tipoEvento tipo de evento do evento
	 * @param nome nome do evento
	 * @param datas datas em que o evento ocorre
	 * @param empresa empresa k realiza o evento
	 * @return 
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void novoEvento(TipoEvento tipoEvento, String nome, List<Map.Entry<Date,Date>> datas, Empresa empresa) {

		Evento novoEvento = new Evento(tipoEvento, nome, datas, empresa);
		em.persist(novoEvento);
	}


	/**
	 * Devolve o evento com o nome especificado
	 * 
	 * @param nomeEvento nome do evento
	 * @return evento com o nome dado
	 * @throws ApplicationException quando nao existe na base de dados um
	 * evento com nome nomeEvento
	 */
	public Evento getEventoByName(String nomeEvento) throws ApplicationException {

		try {
			TypedQuery<Evento> query = em.createNamedQuery(Evento.FIND_BY_NAME, Evento.class);
			query.setParameter(Evento.NAME, nomeEvento);
			return query.getSingleResult();
		} catch (Exception e) {
			throw new EventoNotValidException ("Evento com nome " + nomeEvento + " nao existe", e);
		}
	}


	/**
	 * Devolve o evento com o nome nomeEvento sem instalacao atribuida
	 * 
	 * @param nomeEvento nome do evento
	 * @return evento com nome nomeEvento
	 * @throws EventoNotValidException quando nao existe evento com nome nomeEvento
	 * @throws EventoAlreadyHasInstalacaoException quando o evento ja tem instalacao atribuida
	 */
	public Evento getEventoByNameSemInstalacao(String nomeEvento) throws ApplicationException {

		try {

			Evento e = getEventoByName(nomeEvento);

			if (e.instalacaoAtribuida()) {
				throw new EventoAlreadyHasInstalacaoException("Evento com nome " + nomeEvento + " ja tem instalacao atribuida");
			}

			return e;
		} catch (EventoNotValidException | EventoAlreadyHasInstalacaoException e) {
			throw e;
		}

	}

	public Collection<Evento> getEventos() {

		TypedQuery<Evento> query = em.createNamedQuery(Evento.FIND_ALL, Evento.class);

		return query.getResultList();

	}


}
