package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

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
import presentation.web.model.CompraBPasseModel;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@Stateless
public class PagamentoPasseAction extends Action {
	
	private static final String ERRO_NUM_BILHETES_VAZIO = "Falta indicar o número de bilhetes. \n";
	private static final String ERRO_FORMATO_NUM_BILHETES = "O número não é um inteiro";
	private static final String ERRO_MAIL_VAZIO = "É necessário indicar um email. \n";

	@EJB private IBilhetesLugarServiceRemote bilheteService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
		
		CompraBPasseModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (isInputValid(model)) {
			try {
				
				String nomeEvento = model.getNomeEvento();
				int maxQty = Integer.parseInt(model.getMaxQuantidade());
				
				int numB = Integer.parseInt(model.getQuantidade());
				bilheteService.escolherNumBilhetes(numB, nomeEvento, maxQty);
				PagamentoDTO pgm = bilheteService.efetuarCompraBilhetesPasse(nomeEvento, model.getEmail(), numB);
				model.setEntidade(String.valueOf(pgm.getEntidade()));
				model.setReferencia(String.valueOf(pgm.getReferencia()));
				model.setPreco(String.valueOf(pgm.getPreco()));
				
			} catch (ApplicationException e) {

				model.clearFields();
				model.addMessage("Erro ao comprar bilhetes passe. " + e.getMessage());
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesPasse/nomeEvento.jsp").forward(request, response);
				return;
			}

		}
		else {
			
			List<String> ms = model.getMessages();
			String[] errosQuantidade = {ERRO_FORMATO_NUM_BILHETES, ERRO_NUM_BILHETES_VAZIO};
			String[] erroMail = {ERRO_MAIL_VAZIO};
			String[] erroTudoVazio = {ERRO_FORMATO_NUM_BILHETES, ERRO_NUM_BILHETES_VAZIO, ERRO_MAIL_VAZIO};
			if ((ms.size() == 2 && ms.containsAll(Arrays.asList(errosQuantidade))) ||
				(ms.size() == 2 && ms.stream().anyMatch(s -> Arrays.asList(errosQuantidade).contains(s))
						&& ms.stream().anyMatch(s -> Arrays.asList(erroMail).contains(s))) ||
				(ms.size() == 1 && ms.stream().anyMatch(s -> Arrays.asList(erroTudoVazio).contains(s)))	 ||
				(ms.size() == 3 && ms.containsAll(Arrays.asList(erroTudoVazio))) ){
				
				model.setQuantidade("");
				model.setEmail("");
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesPasse/quantidadePasses.jsp").forward(request, response);
				return;
			}
			
			model.clearFields();
			model.addMessage("Erro de validação dos dados.");
			request.setAttribute("model", model);			
			request.getRequestDispatcher("/bilhetesPasse/nomeEvento.jsp").forward(request, response);
			return;
		}
		
		
		request.setAttribute("model", model);
		request.getRequestDispatcher("/bilhetesPasse/infoPagamento.jsp").forward(request, response);
	}
	
	
	
	
	
	
	/**
	 * Validate the input request
	 * 
	 * @param nch The model object to be field.
	 * @return True if the fields are inputed correctly
	 */	
	private boolean isInputValid(CompraBPasseModel model) {		

		boolean result = isFilled(model, model.getNomeEvento(), "O nome do evento não está preenchido.");
		result = isFilled(model, model.getQuantidade(), ERRO_NUM_BILHETES_VAZIO) && result;
		result = isInt(model, model.getQuantidade(), ERRO_FORMATO_NUM_BILHETES) && result ;
		result = isFilled(model, model.getMaxQuantidade(), "O número de bilhetes passe não está definido. \n") && result;
		result = isInt(model, model.getMaxQuantidade(), "O número de bilhetes passe não está no formato correto.") && result;	
		result = isFilled(model, model.getEmail(), ERRO_MAIL_VAZIO) && result;
		
		return result;
	}
	
	private CompraBPasseModel createHelper(HttpServletRequest request) {
		// Create the object model
		CompraBPasseModel model = new CompraBPasseModel();

		// fill it with data from the request
		model.setNomeEvento(request.getParameter("evento"));
		model.setEmail(request.getParameter("email"));
		model.setQuantidade(request.getParameter("qty"));
		model.setMaxQuantidade(request.getParameter("maxQty"));
		
		
		return model;
	}

}

