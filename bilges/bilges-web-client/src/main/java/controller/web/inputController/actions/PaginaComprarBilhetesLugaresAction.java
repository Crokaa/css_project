package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.CompraBLugaresModel;


/**
 * Handles the novo cliente event
 * 
 * @author fmartins
 *
 */
@Stateless
public class PaginaComprarBilhetesLugaresAction extends Action {

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		CompraBLugaresModel model = new CompraBLugaresModel();
		request.setAttribute("model", model);
		request.getRequestDispatcher("/bilhetesLugares/nomeEvento.jsp").forward(request, response);
	}

}
