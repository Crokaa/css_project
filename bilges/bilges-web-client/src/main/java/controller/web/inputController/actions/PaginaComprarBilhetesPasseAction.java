package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.CompraBLugaresModel;
import presentation.web.model.CompraBPasseModel;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@Stateless
public class PaginaComprarBilhetesPasseAction extends Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		CompraBPasseModel model = new CompraBPasseModel();
		request.setAttribute("model", model);
		request.getRequestDispatcher("/bilhetesPasse/nomeEvento.jsp").forward(request, response);
	}

}
