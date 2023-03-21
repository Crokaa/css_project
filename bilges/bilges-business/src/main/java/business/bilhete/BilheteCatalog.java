package business.bilhete;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import business.evento.DiaEvento;
import business.evento.Evento;
import business.lugar.Lugar;


/**
 * Classe que permite gerir bilhetes
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */
@Stateless
public class BilheteCatalog {

	@PersistenceContext
	private EntityManager em;

	/**
	 * Cria bilhetes passe
	 * @param diasBilhetes - mapa com os dias do evento e os bilhetes a que correspondem a esses dias
	 * @param numero - numero de bilhetes passe a criar
	 * @param evento - evento dos bilhetes
	 * @return lista de bilhetes passe
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public List<BilhetePasse> criaBilhetesPasse(Map<DiaEvento, List<BilheteIndividual>> diasBilhetes, int numero, Evento evento) {

		List<BilhetePasse> bilhetesPasse = new ArrayList<>();
		try {
			int i = numero;
			int j = 0;
			while(i > 0) {
				BilhetePasse bp = new BilhetePasse(evento, evento.getPrecoBPasse());
				bilhetesPasse.add(bp);
				for(DiaEvento dia : diasBilhetes.keySet())
					bilhetesPasse.get(j).putBilhete(diasBilhetes.get(dia).get(j));
				
				em.persist(bilhetesPasse.get(j));

				for(BilheteIndividual bilInd : bp.getBilhetesIndividuais()) {
					bilInd.setEstado(EstadoBilhete.RESERVADO);
					em.merge(bilInd);
				}
				j++;
				i--;
			}
		}catch(Exception e) {
			return bilhetesPasse;
		}
		return bilhetesPasse;
	}


	/**
	 * Devolve os bilhetes de um evento pelo estado dado e pelo seu tipo
	 * @param evento - evento do qual se querem os bilhetes
	 * @param c - tipo do bilhete
	 * @param e - estado do bilhete
	 * @requires evento != null && c != null && e != null
	 * @return lista de bilhetes do evento, tipo e estado dados
	 */
	public List<BilheteIndividual> getBilhetesIndividuaisDeEvento(Evento evento) {

		List<BilheteIndividual> bilhetes = new ArrayList<>();

		try {
			TypedQuery<Bilhete> query = em.createNamedQuery(Bilhete.FIND_ALL_BY_EVENTO_BY_NAME, Bilhete.class);
			query.setParameter(Bilhete.EVENTO_NAME, evento);

			List<Bilhete> bils = query.getResultList();

			for(Bilhete b : bils)
				if(b instanceof BilheteIndividual)
					bilhetes.add((BilheteIndividual) b);
			
		} catch (Exception e1) {
			System.err.print(e1.getMessage());
			return bilhetes;
		}
		return bilhetes;
	}

	public List<BilheteSentado> getBilhetesSentadosDoEvento(Evento evento) {

		List<BilheteSentado> bilhetes = new ArrayList<>();

		try {
			TypedQuery<Bilhete> query = em.createNamedQuery(Bilhete.FIND_ALL_BY_EVENTO_BY_NAME, Bilhete.class);
			query.setParameter(Bilhete.EVENTO_NAME, evento);

			List<Bilhete> bils = query.getResultList();

			for(Bilhete b : bils)
				if(b instanceof BilheteSentado)
					bilhetes.add((BilheteSentado) b);
			
		} catch (Exception e1) {
			System.err.print(e1.getMessage());
			return bilhetes;
		}
		return bilhetes;
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void criaBilheteSentado(Evento evento, DiaEvento d, Lugar l, Float precoBilhete, EstadoBilhete estado) {

		BilheteSentado b = new BilheteSentado(evento, d, l, precoBilhete, estado);
		em.persist(b);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void criaBilheteIndividual(Evento evento, DiaEvento d, Float precoBilhete, EstadoBilhete estado) {

		BilheteIndividual b = new BilheteIndividual(evento, d, precoBilhete, estado);
		em.persist(b);
	}


	public List<BilhetePasse> getBilhetesPasseByNome(Evento evento) {
		List<BilhetePasse> bilhetes = new ArrayList<>();

		try {

			TypedQuery<Bilhete> query = em.createNamedQuery(Bilhete.FIND_ALL_BY_EVENTO_BY_NAME, Bilhete.class);
			query.setParameter(Bilhete.EVENTO_NAME, evento);

			List<Bilhete> bils = query.getResultList();

			for(Bilhete b : bils) {
				System.out.println(b.getPreco());
				if(b instanceof BilhetePasse)
					bilhetes.add((BilhetePasse) b);
			}
			
		}catch(Exception e1) {
			return bilhetes;
		}
		return bilhetes;
	}

}
