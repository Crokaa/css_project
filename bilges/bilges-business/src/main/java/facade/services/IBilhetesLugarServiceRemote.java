package facade.services;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import facade.dto.LugarInfo;
import facade.dto.PagamentoDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IBilhetesLugarServiceRemote {


	public List<Date> comprarBilheteComLugar(String nomeEvento) throws ApplicationException;

	public List<LugarInfo> escolheData(String nomeEvento, Date data) throws ApplicationException;

	public PagamentoDTO escolheLugar(String nomeEvento, Date data, List<LugarInfo> lugares, String email)
			throws ApplicationException;
	
	public int comprarBilhetesPasse(String nomeEvento) throws ApplicationException;

	public void escolherNumBilhetes(int numero, String nomeEvento, int minBPasse)
			throws ApplicationException;

	public PagamentoDTO efetuarCompraBilhetesPasse(String nomeEvento, String mail, int numero) throws ApplicationException;
	
}
