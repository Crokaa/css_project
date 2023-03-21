package business.empresa;


import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Classe que permite gerir empresas
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Stateless
public class EmpresaCatalog {
	
	@PersistenceContext
	private EntityManager em;

	/**
	 * Diz se o numero de registo eh valido
	 * @param numRegistoEmpresa - numero de registo da empresa
	 * @return true se o numero eh valido, false caso contrario
	 */
	public boolean numIsValid(long numRegistoEmpresa) {
		
		TypedQuery<Empresa> query = em.createNamedQuery(Empresa.FIND_BY_NUM, Empresa.class);
		query.setParameter(Empresa.NUM, numRegistoEmpresa);
		
		return !query.getResultList().isEmpty();
		
	}

	/**
	 * Devolve a empresa a partir de um numero de registo
	 * @param numRegistoEmpresa - numero de registo da empresa
	 * @return empresa com o numero de registo dado
	 */
	public Empresa getEmpresaByNum(long numRegistoEmpresa) {
		
		TypedQuery<Empresa> query = em.createNamedQuery(Empresa.FIND_BY_NUM, Empresa.class);
		query.setParameter(Empresa.NUM, numRegistoEmpresa);
		Empresa e = query.getSingleResult();
		em.persist(e);
		return e;
	}

}
