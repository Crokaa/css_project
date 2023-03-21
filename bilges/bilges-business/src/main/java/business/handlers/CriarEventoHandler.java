
package business.handlers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.empresa.Empresa;
import business.empresa.EmpresaCatalog;
import business.evento.EventoCatalog;
import business.tipo_evento.TipoEvento;
import business.utils.DateUtils;
import facade.exceptions.ApplicationException;
import facade.exceptions.DatesAreNotValidException;
import facade.exceptions.EmpresaNotCertifadeException;
import facade.exceptions.EmpresaNotValidException;
import facade.exceptions.NomeIsNotUniqueException;
import facade.exceptions.TipoIsNotValidException;


@Stateless
public class CriarEventoHandler {

	@EJB
	private EventoCatalog catalogoEventos;
	
	@EJB
	private EmpresaCatalog catalogoEmpresas;

	public Collection<TipoEvento> criarEvento() {

		List<TipoEvento> ls = new ArrayList<>();

		for (TipoEvento t : TipoEvento.values()) {
			ls.add(t);
		}

		return ls;
	}

	/**
	 * Cria evento novo
	 * @param tipo
	 * @param nome
	 * @param datas
	 * @param numRegistoEmpresa
	 * @throws ApplicationException
	 */
	public void criarNovoEvento(String tipo, String nome, List<Map.Entry<Date,Date>> datas, long numRegistoEmpresa) throws ApplicationException {

		TipoEvento tipoEvento;

		try {
			
			tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
		}
		catch (IllegalArgumentException | NullPointerException e)  {
			throw new TipoIsNotValidException("Tipo nao eh valido");
		}

		try {

			if (!catalogoEventos.nomeIsUnique(nome)) {
				throw new NomeIsNotUniqueException("Jah existe evento com esse nome");
			}

			if (!datesAreValid(datas)) {
				throw new DatesAreNotValidException("Datas nao sao validas");
			}

			if (!catalogoEmpresas.numIsValid(numRegistoEmpresa)) {
				throw new EmpresaNotValidException("Empresa nao existe");
			}

			Empresa empresa = catalogoEmpresas.getEmpresaByNum(numRegistoEmpresa);

			if (!empresa.isCertificada(tipoEvento)) {

				throw new EmpresaNotCertifadeException("Empresa nao eh certificada para o evento");
			}

			catalogoEventos.novoEvento(tipoEvento, nome, datas, empresa);

		}
		catch(Exception e) {

			System.out.println(e.getMessage());
			throw new ApplicationException("Internal Error: " + e.getMessage());
		}

	}

	/**
	 * verifica se datas sao validas
	 * @param datas
	 * @return
	 */
	private boolean datesAreValid(List<Entry<Date, Date>> datas) {

		if (datas.get(0).getKey().before(DateUtils.getCurrentTime()))
			return false;

		Date inicio;
		Date fim;

		for (int i = 0; i < datas.size(); i++) {

			inicio = datas.get(i).getKey();
			fim = datas.get(i).getValue();
			
			//System.out.println(inicio);
			//System.out.println(fim);

			if(fim.before(inicio))
				return false;
			
			//tem k ser o mesmo dia
			//nao necessariamente, o primeiro caso tem meia noite e fica proximo dia
			
//			if (inicio.getDay() != fim.getDay() || inicio.getMonth() != fim.getMonth() || 
//					inicio.getYear() != fim.getYear()) 
//				return false;

			if ((i > 0) && inicio.before(datas.get(i-1).getKey())) 
				return false;

		}
		return true;
	}

}
