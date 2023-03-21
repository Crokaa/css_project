package controller.web.inputController.actions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.CompraBLugaresModel;
import presentation.web.model.Model;

/**
 * An abstract HTTP action request handler. 
 * Think of it as an operation in the SSD diagram.
 * It has an init method, because objects are
 * create from the prototype (vide UseCaseFrontController)
 * and its easier to use a no parameters construct.
 * 
 * It allows subclasses to define how to handle individual 
 * actions.
 * 
 * We need to store the http request context, since
 * actions are not http servlets and do not have access to
 * the request data.
 *  
 * @author fmartins
 * @author malopes
 */
public abstract class Action {

	/**
	 * Strategy method for processing each request
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException;
	
	// the following methods might need to be refactored in order to avoid
	// duplicated code
	protected boolean isInt(Model help, String num, String message) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			help.addMessage(message);
			return false;
		}
	}

	protected int intValue(String num) {
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	protected boolean isFilled (Model helper, String value, String message) {
		if (value == null || value.equals("")) {
			helper.addMessage(message);
			return false;
		}
		return true;
	}
	
	protected boolean isFilled(Model helper, ArrayList<String> value, String message) {
		if (value == null) {
			helper.addMessage(message);
			return false;
		}
		for (String s : value) {
			if (s == null || s.equals("")) {
				helper.addMessage(message);
				return false;
			}
		}
		return true;
	}
	
	protected boolean isDate (Model helper, String value, String message) {
		if (value == null || value.equals("")) {
			helper.addMessage(message);
			return false;
		}
		try {
			
			Date.parse(value);
		}catch(Exception e) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * Verifica se as strigs em value podem ser instancias de lugarInfo
	 * @param helper
	 * @param value
	 * @param string
	 * @return
	 */
	protected boolean isLugares(Model helper, ArrayList<String> value, String message) {
		if (value == null) {
			helper.addMessage(message);
			return false;
		}
		for (String s : value) {
			
			String[] lugar = s.split("-");
			
			if (lugar == null || lugar.length != 2 || !isInt(helper, s, message)) {
				helper.addMessage(message);
				return false;
			}
				
		}
		return true;
	}
	
	
	protected boolean areDate(Model helper, ArrayList<String> value, String message) {
		if (value == null) {
			helper.addMessage(message);
			return false;
		}
		for (String s : value) {
			
			if (!isDate(helper, s, message))
				return false;
		
				
		}
		return true;
	}
	


}
