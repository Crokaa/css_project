package application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.handlers.AtribuirInstalacaoHandler;
import business.handlers.CriarEventoHandler;
import business.tipo_evento.TipoEvento;
import facade.dto.InstalacaoDTO;
import facade.dto.TipoEventoDTO;
import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
@Stateless
public class EventoService implements IEventServiceRemote{

	@EJB
	private CriarEventoHandler eventoHandler;
	
	@EJB
	private AtribuirInstalacaoHandler instalacaoHandler;
	
	public Iterable<TipoEventoDTO> getTiposEvento() throws ApplicationException {
		
		Collection<TipoEvento> tipos = this.eventoHandler.criarEvento();
		
		List<TipoEventoDTO> tiposDTO = new ArrayList<>();
		
		for(TipoEvento tipo : tipos) {
			tiposDTO.add(new TipoEventoDTO(tipo.getNome(), tipo.getDimMax(),tipo.isSentados()));
		}
		
		return tiposDTO;
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void criarNovoEvento(String tipo, String nome, List<Entry<Date, Date>> datas, long numRegistoEmpresa)
			throws ApplicationException {
		
		this.eventoHandler.criarNovoEvento(tipo, nome, datas, numRegistoEmpresa);
	}

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void atribuirInstalacao(String nomeEvento, String instalacao, Date inicioVenda, float precoBilhete)
			throws ApplicationException {
		
		this.instalacaoHandler.atribuirInstalacao(nomeEvento, instalacao, inicioVenda, precoBilhete);
	}
	
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void atribuirInstalacao(String nomeEvento, String instalacao, Date inicioVenda, float precoBilhete,
			float precoBilhetePasse) throws ApplicationException {
		
		this.instalacaoHandler.atribuirInstalacao(nomeEvento, instalacao, inicioVenda, precoBilhete, precoBilhetePasse);
	}

	public Iterable<String> getNomesEvento() throws ApplicationException {
		
		return this.instalacaoHandler.getNomeEventos();
	}

	public Iterable<InstalacaoDTO> getInstalacoes() throws ApplicationException {
		
		return this.instalacaoHandler.instalacoes();
	}

}
