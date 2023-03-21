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
import facade.exceptions.ApplicationException;
import facade.services.IBilhetesLugarServiceRemote;
import presentation.web.model.CompraBLugaresModel;
import presentation.web.model.LugarModel;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@Stateless
public class LugaresDataAction extends Action {
	
	private static final String ERRO_DATA_VAZIA = "Falta indicar a data do evento. \n";
	private static final String ERRO_FORMATO_DATA = "Data não se encontra no formato correto. \n";

	@EJB private IBilhetesLugarServiceRemote bilheteService;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		CompraBLugaresModel model = createHelper(request);
		request.setAttribute("model", model);
		
		if (isInputValid(model)) {
			try {
				
				String [] dataSplit = model.getDataEscolhida().split("/");
				int [] dataInt =  new int[3];
				
				for(int i = 0; i < dataSplit.length; i++)
					dataInt[i] = Integer.parseInt(dataSplit[i]);
				
				@SuppressWarnings("deprecation")
				List<LugarInfo> lugares = bilheteService.escolheData(model.getNomeEvento(), new Date(dataInt[2] - 1900, dataInt[1] - 1, dataInt[0]));
				List<String> lugaresString = new ArrayList<>();
				
				for(LugarInfo l : lugares) {
					lugaresString.add(l.toString());
				}
				
				model.setLugares(lugaresString);
				
			} catch (ApplicationException e) {
				model.clearFields();
				model.addMessage("Erro ao ir buscar os lugares disponíveis. \n" + e.getMessage());
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesLugares/nomeEvento.jsp").forward(request, response);
				return;
			}

		}
		else {
			
			List<String> ms = model.getMessages();
			String[] errosData = {ERRO_DATA_VAZIA, ERRO_FORMATO_DATA};
			if (ms.size() == 2 && ms.containsAll(Arrays.asList(errosData))) {
				model.setDataEscolhida("");
				request.setAttribute("model", model);			
				request.getRequestDispatcher("/bilhetesLugares/datasEvento.jsp").forward(request, response);
				return;
			}
			
			model.clearFields();
			model.addMessage("Erro de validação dos dados.");
			request.setAttribute("model", model);			
			request.getRequestDispatcher("/bilhetesLugares/nomeEvento.jsp").forward(request, response);
			return;
		}
		
		
		request.setAttribute("model", model);
		request.getRequestDispatcher("/bilhetesLugares/lugares.jsp").forward(request, response);
	}
	
	
	
	
	
	
	/**
	 * Validate the input request
	 * 
	 * @param nch The model object to be field.
	 * @return True if the fields are inputed correctly
	 */	
	private boolean isInputValid(CompraBLugaresModel model) {		
/*
		boolean result = isFilled(model, model.getNomeEvento(), "Falta indicar o nome do evento. \n");
		result = isFilled(model, model.getDatas(), "Datas não estão preenchidas. \n") && result;
		result = areDate(model, model.getDatas(), "Datas não estão no formato certo. \n") && result;
		result = isFilled(model, model.getDataEscolhida(), ERRO_DATA_VAZIA) && result;
		result = isDate(model, model.getDataEscolhida(), ERRO_FORMATO_DATA) && result;*/

		return true;
	}
	




	private CompraBLugaresModel createHelper(HttpServletRequest request) {
		// Create the object model
		CompraBLugaresModel model = new CompraBLugaresModel();

		// fill it with data from the request
		model.setNomeEvento(request.getParameter("evento"));
		model.setDataEscolhida(request.getParameter("dataEscolhida"));
		
		
		return model;
	}

}
