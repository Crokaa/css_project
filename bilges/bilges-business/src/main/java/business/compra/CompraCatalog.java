package business.compra;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import business.bilhete.BilhetePasse;
import business.bilhete.BilheteSentado;
import business.utils.DateUtils;
import facade.exceptions.ApplicationException;

/**
 * Classe que permite gerir compras/reservas
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Stateless
public class CompraCatalog {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Reserva bilhetes
	 * @param bilhetesAReservar - a lista de bilhetes a reserva
	 * @param email - email de quem fez a reserva
	 * @return informacao de pagamento da reserva
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public PagamentoInfo reserva(List<BilheteSentado> bilhetesAReservar, String email) {

		bilhetesAReservar.forEach(b -> { 
										b.reservar(); 
										em.merge(b);
									});

		Compra c= new Compra(bilhetesAReservar, false, DateUtils.getCurrentTime(), email);
		em.persist(c);

		return c.getPagamentoInfo();
	}

	/**
	 * Cria os bilhetes passe
	 * @param bilhetesPasse - lista dos bilhetes passe
	 * @param numero - numero de bilhetes a criar
	 * @param mail - mail de quem fez a compra/reserva
	 * @param precoBPasse - preco base de cada bilhete passe
	 * @return informacao da compra/reserva dos bilhetes passe
	 * @throws ApplicationException
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public PagamentoInfo criaCompraBilhetesPasse(List<BilhetePasse> bilhetesPasse, int numero, String mail, float precoBPasse) throws ApplicationException {
		try {
			Compra cp = new Compra(bilhetesPasse, DateUtils.getCurrentTime(), true, mail, numero * precoBPasse);
			em.persist(cp);
			return cp.getPagamentoInfo();
		}catch(Exception e) {
			throw new ApplicationException("Internal error", e);
		}
	}

}
