package application;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.compra.PagamentoInfo;
//import business.compra.PagamentoInfo;
import business.handlers.CompraBilhetesHandler;
import facade.dto.LugarInfo;
import facade.dto.PagamentoDTO;
import facade.exceptions.ApplicationException;
import facade.services.IBilhetesLugarServiceRemote;

@Stateless
public class BilhetesService implements IBilhetesLugarServiceRemote{

	@EJB
	private CompraBilhetesHandler bilhetesHandler;

	@Override
	public List<Date> comprarBilheteComLugar(String nomeEvento) throws ApplicationException {

		return bilhetesHandler.datasDisponiveis(nomeEvento);
	}

	@Override
	public List<LugarInfo> escolheData(String nomeEvento, Date data) throws ApplicationException {
		return bilhetesHandler.getLugaresDisponiveis(nomeEvento, data);
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public PagamentoDTO escolheLugar(String nomeEvento, Date data, List<LugarInfo> lugares, String email) throws ApplicationException {
		return bilhetesHandler.reservaBilhetes(nomeEvento, data, lugares, email);

	}

	@Override
	public int comprarBilhetesPasse(String nomeEvento) throws ApplicationException {
		return bilhetesHandler.comprarBilhetePasse(nomeEvento);
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void escolherNumBilhetes(int numero, String nomeEvento, int minBPasse) throws ApplicationException {
		bilhetesHandler.indicarNumBilhetes(numero, nomeEvento, minBPasse);
	}

	@Override
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public PagamentoDTO efetuarCompraBilhetesPasse(String nomeEvento, String mail, int numero) throws ApplicationException {
		return bilhetesHandler.efetuaCompraBilhetesPasse(nomeEvento, mail, numero);
	}


}
