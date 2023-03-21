package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dto.LugarInfo;
import facade.dto.PagamentoDTO;
import facade.exceptions.ApplicationException;
import facade.services.IBilhetesLugarServiceRemote;
import presentation.web.model.CompraBLugaresModel;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@Stateless
public class PagamentoAction extends Action {
	
	private static final String ERRO_LUGAR_VAZIO = "Falta indicar os lugares pretendidos. \n";
	private static final String ERRO_FORMATO_LUGAR = "Os lugares não estão no formato certo. \n"; 
	private static final String ERRO_MAIL_VAZIO = "É necessário indicar um email. \n";

	@EJB private IBilhetesLugarServiceRemote bilheteService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		CompraBLugaresModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (isInputValid(model)) {
			try {
				
				String nomeEvento = model.getNomeEvento();
				String [] dataSplit = model.getDataEscolhida().split("/");
				int [] dataInt =  new int[3];
				
				for(int i = 0; i < dataSplit.length; i++)
					dataInt[i] = Integer.parseInt(dataSplit[i]);
				
				@SuppressWarnings("deprecation")
				Date data = new Date(dataInt[2] - 1900, dataInt[1] - 1, dataInt[0]);
				
				List<String> lugaresString = model.getLugares();
				List<LugarInfo> lugares = new ArrayList<>();
				for(String s : lugaresString) {
					String [] lugarSplit = s.split("-");
					lugares.add(new LugarInfo(lugarSplit[0], Integer.parseInt(lugarSplit[1])));
				}
					
				PagamentoDTO pgm = bilheteService.escolheLugar(nomeEvento, data, lugares, model.getEmail());
				model.setEntidade(String.valueOf(pgm.getEntidade()));
				model.setReferencia(String.valueOf(pgm.getReferencia()));
				model.setPreco(String.valueOf(pgm.getPreco()));
				
			} catch (ApplicationException e) {
				
				model.clearFields();
				model.addMessage("Erro ao ir buscar as datas. " + e.getMessage());
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesLugares/nomeEvento.jsp").forward(request, response);
				return;
			}

		}
		else {
			List<String> ms = model.getMessages();
			String[] errosLugar = {ERRO_LUGAR_VAZIO, ERRO_FORMATO_LUGAR};
			String[] erroMail = {ERRO_MAIL_VAZIO};
			String[] erroTudoVazio = {ERRO_LUGAR_VAZIO, ERRO_FORMATO_LUGAR, ERRO_MAIL_VAZIO};
			
			if ((ms.size() == 2 && ms.containsAll(Arrays.asList(errosLugar))) ||
				(ms.size() == 2 && ms.stream().anyMatch(s -> Arrays.asList(errosLugar).contains(s))
						&& ms.stream().anyMatch(s -> Arrays.asList(erroMail).contains(s))) ||
				(ms.size() == 1 && ms.stream().anyMatch(s -> Arrays.asList(erroTudoVazio).contains(s)))	 ||
				(ms.size() == 3 && ms.containsAll(Arrays.asList(erroTudoVazio))) ){
			
				model.setLugares(new ArrayList<String>());
				model.setEmail("");
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesLugares/lugares.jsp").forward(request, response);
				return;
			}
			model.clearFields();
			model.addMessage("Erro de validação dos dados.");
			request.setAttribute("model", model);			
			request.getRequestDispatcher("/bilhetesLugares/lugares.jsp").forward(request, response);
			return;
		}
		
		
		request.setAttribute("model", model);
		request.getRequestDispatcher("/bilhetesLugares/infoPagamento.jsp").forward(request, response);
	}
	
	
	
	
	
	
	/**
	 * Validate the input request
	 * 
	 * @param nch The model object to be field.
	 * @return True if the fields are inputed correctly
	 */	
	private boolean isInputValid(CompraBLugaresModel model) {		

		boolean result = isFilled(model, model.getNomeEvento(), "Falta indicar o nome do evento.");
		result = isFilled(model, model.getDatas(), "Datas não estão preenchidas. \n") && result ;
		result = areDate(model, model.getDatas(), "Datas não estão no formato certo. \n") && result;
		result = isFilled(model, model.getDataEscolhida(), "Falta indicar a data do evento. \n") && result;
		result = isDate(model, model.getDataEscolhida(), "A data não está no formato certo. \n") && result;
		result = isFilled(model, model.getLugares(), ERRO_LUGAR_VAZIO) && result;
		result = isLugares(model, model.getLugares(), ERRO_FORMATO_LUGAR) && result;
		result = isFilled(model, model.getEmail(), ERRO_MAIL_VAZIO) && result;

		return result;
	}
	
	private CompraBLugaresModel createHelper(HttpServletRequest request) {
		// Create the object model
		CompraBLugaresModel model = new CompraBLugaresModel();

		// fill it with data from the request
		model.setNomeEvento(request.getParameter("evento"));
		ArrayList<String> datas = new ArrayList<>();
		for (String s : request.getParameterValues("datas")) {
			datas.add(s);
		}
		model.setDatas(datas);
		model.setDataEscolhida(request.getParameter("dataEscolhida"));
		List<String> lugares = new ArrayList<>();
		for(String s : request.getParameterValues("lugarEscolhido"))
			lugares.add(s);
		model.setLugares(lugares);
		model.setEmail(request.getParameter("email"));
		
		
		return model;
	}

}

