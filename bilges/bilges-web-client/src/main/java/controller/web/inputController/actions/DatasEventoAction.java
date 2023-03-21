package controller.web.inputController.actions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class DatasEventoAction extends Action {
	
	@EJB private IBilhetesLugarServiceRemote bilheteService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		CompraBLugaresModel model = new CompraBLugaresModel();
		model.setNomeEvento(request.getParameter("evento"));
		
		if (isInputValid(model)) {
			try {
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				List<Date> datas = bilheteService.comprarBilheteComLugar(model.getNomeEvento());
				List<String> datasString = new ArrayList<>();
				for(Date d : datas) {
					datasString.add(formatter.format(d));
				}
				
				
				model.setDatas(datasString);
			} catch (ApplicationException e) {
				model.clearFields();
				model.addMessage("Erro ao ir buscar as datas. \n" + e.getMessage());
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesLugares/nomeEvento.jsp").forward(request, response);
				return;
				
				
			}

		}
		else {
			//se entrou então o evento não tem nada, logo o clear não é preciso
			model.addMessage("Erro de validação dos dados.");
			request.setAttribute("model", model);			
			request.getRequestDispatcher("/bilhetesLugares/nomeEvento.jsp").forward(request, response);
			return;
		}
		
		
		request.setAttribute("model", model);
		request.getRequestDispatcher("/bilhetesLugares/datasEvento.jsp").forward(request, response);
	}
	
	/**
	 * Validate the input request
	 * 
	 * @param nch The model object to be field.
	 * @return True if the fields are inputed correctly
	 */	
	private boolean isInputValid(CompraBLugaresModel model) {		

		boolean result = isFilled(model, model.getNomeEvento(), "Falta indicar o nome do evento. \n");

		return result;
	}

}
