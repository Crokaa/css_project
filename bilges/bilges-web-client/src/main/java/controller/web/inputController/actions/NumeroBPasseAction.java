package controller.web.inputController.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facade.dto.LugarInfo;
import facade.exceptions.ApplicationException;
import facade.services.IBilhetesLugarServiceRemote;
import presentation.web.model.CompraBLugaresModel;
import presentation.web.model.CompraBPasseModel;
import presentation.web.model.LugarModel;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@Stateless
public class NumeroBPasseAction extends Action {

	@EJB private IBilhetesLugarServiceRemote bilheteService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		CompraBPasseModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (isInputValid(model)) {
			try {
				
				int max = bilheteService.comprarBilhetesPasse(model.getNomeEvento());
				model.setMaxQuantidade(String.valueOf(max));
				
			} catch (ApplicationException e) {

				model.clearFields();
				model.addMessage("Erro ao ir buscar número de bilhetes passe. " + e.getMessage());
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesPasse/nomeEvento.jsp").forward(request, response);
				return;
			}

		}
		else {
			model.clearFields();
			model.addMessage("Erro de validação dos dados. \n");
			request.setAttribute("model", model);			
			request.getRequestDispatcher("/bilhetesPasse/nomeEvento.jsp").forward(request, response);
			return;
		}
		
		
		request.setAttribute("model", model);
		request.getRequestDispatcher("/bilhetesPasse/quantidadePasses.jsp").forward(request, response);
	}
	
	
	
	
	
	
	/**
	 * Validate the input request
	 * 
	 * @param nch The model object to be field.
	 * @return True if the fields are inputed correctly
	 */	
	private boolean isInputValid(CompraBPasseModel model) {		

		boolean result = isFilled(model, model.getNomeEvento(), "Falta indicar o nome do evento. \n");

		return result;
	}
	
	private CompraBPasseModel createHelper(HttpServletRequest request) {
		// Create the object model
		CompraBPasseModel model = new CompraBPasseModel();

		// fill it with data from the request
		model.setNomeEvento(request.getParameter("evento"));
		
		
		return model;
	}

}
