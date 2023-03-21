package facade.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import facade.dto.InstalacaoDTO;
import facade.dto.TipoEventoDTO;
import facade.exceptions.ApplicationException;


@Remote
public interface IEventServiceRemote {
	
	public void criarNovoEvento(String tipo, String nome, List<Map.Entry<Date,Date>> datas, long numRegistoEmpresa)
			throws ApplicationException;
	
	public void atribuirInstalacao(String nomeEvento, String instalacao, Date inicioVenda,
			float precoBilhete, float precoBilhetePasse) throws ApplicationException;

	public void atribuirInstalacao(String nomeEvento, String instalacao, Date inicioVenda,
			float precoBilhete) throws ApplicationException;

	public Iterable<TipoEventoDTO> getTiposEvento() throws ApplicationException;
	
	public Iterable<String> getNomesEvento() throws ApplicationException;
	
	public Iterable<InstalacaoDTO> getInstalacoes() throws ApplicationException;
	
}
