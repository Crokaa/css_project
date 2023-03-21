package presentation.web.model;

public class LugarModel extends Model{

	private String fila;
	private int cadeira;
	
	public String getFila() {
		return fila;
	}
	
	public int getCadeira() {
		return cadeira;
	}
	
	public void setFila(String f) {
		fila = f;
	}
	
	public void setCadeira(int c) {
		cadeira = c;
	}
	
	public void clearFields() {
		fila = "";
		cadeira = -1;
	}
}
