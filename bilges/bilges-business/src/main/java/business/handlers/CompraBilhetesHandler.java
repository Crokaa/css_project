package business.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

import business.bilhete.BilheteCatalog;
import business.bilhete.BilheteIndividual;
import business.bilhete.BilhetePasse;
import business.bilhete.BilheteSentado;
import business.bilhete.EstadoBilhete;
import business.compra.CompraCatalog;
import business.compra.PagamentoInfo;
import business.evento.DiaEvento;
import business.evento.Evento;
import business.evento.EventoCatalog;
import facade.dto.LugarInfo;
import facade.dto.PagamentoDTO;
import facade.exceptions.ApplicationException;
import facade.exceptions.EventoNoBilhetesPasseException;
import facade.exceptions.EventoNotValidException;
import facade.exceptions.TooMuchBilhetesPasseException;

/**
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */

@Stateless
public class CompraBilhetesHandler {
	
	
	@EJB EventoCatalog eventoCatalog;
	
	@EJB BilheteCatalog bilheteCatalog;
	
	@EJB CompraCatalog compraCatalog;

	public List<Date> datasDisponiveis(String nomeEvento) throws ApplicationException {

		try {

			Evento evento = eventoCatalog.getEventoByName(nomeEvento);

			if(!evento.getTipoEvento().isSentados())
				throw new ApplicationException("Evento nao tem lugares marcados");


			List<BilheteSentado> bilhetes = bilheteCatalog.getBilhetesSentadosDoEvento(evento);



			if(bilhetes.isEmpty())
				throw new ApplicationException("Evento nao tem bilhetes ah venda");
			
			List<Date> datasDisponiveis = new ArrayList<>();

			for(BilheteSentado b : bilhetes) {
				Date dia = b.getDiaEvento().getInicio();
				if(!datasDisponiveis.contains(dia) && b.isLivre())
					datasDisponiveis.add(dia);
			}


			return datasDisponiveis;

		}catch(Exception e) {
			throw e;
		}
	}

	public List<LugarInfo> getLugaresDisponiveis(String nomeEvento, Date data) throws ApplicationException {

		Evento evento;
		try {
			evento = eventoCatalog.getEventoByName(nomeEvento);
		} catch (ApplicationException e) {
			throw e;
		}
		
		List<BilheteSentado> bilhetes = bilheteCatalog.getBilhetesSentadosDoEvento(evento);
		
		return bilhetes.stream()
				.filter(b -> b.isLivre() && b.getDiaEvento().getInicio().equals(data))
				.map(b -> new LugarInfo(b.getLugar().getFila(), b.getLugar().getCadeira()))
				.sorted()
				.collect(Collectors.toList());
	}
	

	public PagamentoDTO reservaBilhetes(String nomeEvento, Date data, List<LugarInfo> lugares, String email) 
			throws ApplicationException {
		
		PagamentoInfo info;

		try {
			
			Evento evento = eventoCatalog.getEventoByName(nomeEvento);
			
			List<BilheteSentado>  bilhetes = bilheteCatalog.getBilhetesSentadosDoEvento(evento);

			List<BilheteSentado> bilhetesAReservar = bilhetes
					.stream()
					.filter(b -> lugares.contains
							(new LugarInfo(b.getLugar().getFila(), b.getLugar().getCadeira())) && b.isLivre() 
							&& b.getDiaEvento().getInicio().equals(data))
					.collect(Collectors.toList());

			if(bilhetesAReservar.isEmpty()) {
				throw new ApplicationException("Bilhetes nao disponiveis");
			}

			info = compraCatalog.reserva(bilhetesAReservar, email);

		}catch(Exception e) {
			throw e;
		}
		
		String [] infoSplit = info.toString().split(" ");

		return new PagamentoDTO(Integer.parseInt(infoSplit[1]), Integer.parseInt(infoSplit[3]), Float.parseFloat(infoSplit[5]));

	}

	public int comprarBilhetePasse(String nomeEvento) throws ApplicationException{
		int minBPasse;
		
		try {

			if (eventoCatalog.nomeIsUnique(nomeEvento)) 
				throw new EventoNotValidException("Evento nao eh valido");

			Evento evento = eventoCatalog.getEventoByName(nomeEvento);

			if(!evento.aceitaBilhetesPasse())
				throw new EventoNoBilhetesPasseException("Evento nao aceita bilhetes passe");	
			
			Map<DiaEvento, List<BilheteIndividual>> diasBilhetes = getBilhetesDeEvento(evento);

			minBPasse = diasBilhetes.get(evento.getDiasEvento().get(0)).size();

			for(List<BilheteIndividual> lista : diasBilhetes.values())
				if(lista.size() < minBPasse)
					minBPasse = lista.size();


		}catch(Exception e) {
			throw e;
		}
		return minBPasse;
	}

	
	public void indicarNumBilhetes(int numero, String nomeEvento, int minBPasse) throws ApplicationException {

		try {
			
			Evento evento = eventoCatalog.getEventoByName(nomeEvento);

			if(numero > minBPasse)
				throw new TooMuchBilhetesPasseException("Nao existe esse numero de bilhetes passe");
			
			Map<DiaEvento, List<BilheteIndividual>> diasBilhetes = getBilhetesDeEvento(evento);

			bilheteCatalog.criaBilhetesPasse(diasBilhetes, numero, evento);

		}catch(Exception e) {
			throw new ApplicationException(e.getMessage());
		}
	}
	
	public Map<DiaEvento, List<BilheteIndividual>> getBilhetesDeEvento(Evento evento){
		List<BilheteIndividual> bilhetes;
		
		bilhetes = bilheteCatalog.getBilhetesIndividuaisDeEvento(evento);

		Map<DiaEvento, List<BilheteIndividual>> diasBilhetes = new HashMap<>();

		for(DiaEvento dia : evento.getDiasEvento())
			diasBilhetes.put(dia, new ArrayList<>());

		for(BilheteIndividual b : bilhetes) 
			if(b.isLivre())
				diasBilhetes.get(b.getDiaEvento()).add(b);
		
		return diasBilhetes;
	}

	public PagamentoDTO efetuaCompraBilhetesPasse(String nomeEvento, String mail, int numero) throws ApplicationException {
		
		PagamentoInfo info;
		
		try {
			Evento evento = eventoCatalog.getEventoByName(nomeEvento);
			
			List<BilhetePasse> bilhetesPasse = bilheteCatalog.getBilhetesPasseByNome(evento);
			
			info = compraCatalog.criaCompraBilhetesPasse(bilhetesPasse, numero, mail, evento.getPrecoBPasse());
			
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		
		String[] infoSplit = info.toString().split(" ");
		
		return new PagamentoDTO(Integer.parseInt(infoSplit[1]), Integer.parseInt(infoSplit[3]), Float.parseFloat(infoSplit[5]));
	}

}

