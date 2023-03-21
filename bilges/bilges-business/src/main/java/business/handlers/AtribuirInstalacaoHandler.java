/**
 * 
 */
package business.handlers;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.bilhete.BilheteCatalog;
import business.bilhete.EstadoBilhete;
import business.evento.DiaEvento;
import business.evento.Evento;
import business.evento.EventoCatalog;
import business.instalacao.Instalacao;
import business.instalacao.InstalacaoCatalog;
import business.instalacao.InstalacaoComLugares;
import business.lugar.Lugar;
import facade.dto.InstalacaoDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.CapacidadeNotValidException;
import facade.exceptions.DateNotValidException;
import facade.exceptions.InstalacaoNotAvailableException;
import facade.exceptions.PrecoNotValidException;

@Stateless
public class AtribuirInstalacaoHandler {

	@EJB
	private InstalacaoCatalog catalogoInstalacao;
	
	@EJB
	private EventoCatalog catalogoEventos;

	@EJB
	private BilheteCatalog catalogoBilhetes;
	
	/*
	 * Devolve Lista de InstalacaoDTO
	 */
	public Collection<InstalacaoDTO> instalacoes(){

		return catalogoInstalacao.getInstalacoes().stream().map(i -> new InstalacaoDTO(
				i.getNome(), i.getCapacidade(), i.lugaresSentados())).collect(Collectors.toList());
	}
	
	public Collection<String> getNomeEventos(){

		return catalogoEventos.getEventos()
				.stream()
				.map(e -> e.getNome())
				.collect(Collectors.toList());
	}

	/**
	 * Atribui instalacao a evento
	 * @param nomeEvento
	 * @param nomeInstalacao
	 * @param inicioVenda
	 * @param precoBilhete
	 * @param precoBilhetePass
	 * @throws ApplicationException
	 */
	public void atribuirInstalacao(String nomeEvento, String nomeInstalacao, Date inicioVenda,
			Float precoBilhete, Float precoBilhetePass) throws ApplicationException{

		try {

			Evento evento = catalogoEventos.getEventoByNameSemInstalacao(nomeEvento);	

			Instalacao instalacao = catalogoInstalacao.getInstalacaoByNameComTipo(nomeInstalacao, evento.getTipoEvento());

			if (!instalacao.datasLives(evento.getDiasEvento())) {

				throw new InstalacaoNotAvailableException("A instalacao ja se encontra ocupada");	
			}

			if (instalacao.lugaresSentados() && instalacao.getCapacidade() > evento.getTipoEvento().getDimMax()) {
				throw new CapacidadeNotValidException("Capacidade nao respeitada");	
			}

			if (!evento.dataVendaValida(inicioVenda)) {
				throw new DateNotValidException("Data invalida");	
			}

			if (precoBilhete < 0) {
				throw new PrecoNotValidException("Preco bilhete tem que ser maior que 0");
			}

			if (precoBilhetePass != null) {
				if (precoBilhetePass < 0) {
					throw new PrecoNotValidException("Preco bilhete passe tem que ser maior que 0");
				}

				evento.setPrecoBilhete(precoBilhetePass);
			}


			evento.setInstalacao(instalacao);
			instalacao.setNovasDatasEventoOcupadas(evento.getDiasEvento());
			
			//emitir bilhetes com lugares
			if (evento.getTipoEvento().isSentados()) {

				//se eh sentados entao a instalacao eh com lugares
				InstalacaoComLugares in = (InstalacaoComLugares) instalacao;

				for (DiaEvento d: evento.getDiasEvento()) {

					for (Lugar l : in.getLugares()) {

						catalogoBilhetes.criaBilheteSentado(evento, d, l, precoBilhete, EstadoBilhete.LIVRE);
						
					}
				}
			}
			else {
				
				int n = Math.min(evento.getTipoEvento().getDimMax(), instalacao.getCapacidade());

				for (DiaEvento d: evento.getDiasEvento()) {

					for (int i = 0; i < n; i++) {
						catalogoBilhetes.criaBilheteIndividual(evento, d, precoBilhete, EstadoBilhete.LIVRE);
					}
				}
			}
		}
		catch (Exception e) {

			throw e;
		}
	}


	/**
	 * Atribui instalacao a evento
	 * @param nomeEvento
	 * @param instalacao
	 * @param inicioVenda
	 * @param precoBilhete
	 * @throws ApplicationException
	 */
	public void atribuirInstalacao(String nomeEvento, String instalacao, Date inicioVenda,
			float precoBilhete) throws ApplicationException {

		atribuirInstalacao(nomeEvento, instalacao, inicioVenda, precoBilhete, null);
	}

}
