package presentation.fx.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataEvento {

	private final Date inicio;
	private final Date fim;

	public DataEvento(Date inicio, Date fim) {
		
		this.inicio = inicio;
		this.fim = fim;
	}
	
	@Override
	public String toString() {
		 
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String data = formatter.format(inicio);  
	    
	    SimpleDateFormat f = new SimpleDateFormat("HH:mm");
	    String HorasInicio = f.format(inicio);
	    String HorasFim = f.format(fim);
	    
	    return data + " " + "Das " + HorasInicio + " as " + HorasFim;
	}
	
	public Date getInicio() {
		return inicio;
	}
	
	public Date getFim() {
		return fim;
	}
}
