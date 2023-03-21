package presentation.fx.model;

public class TipoDeEvento {

	private final String nome;
	private final int dimensaoMAX;
	private final boolean sentado;

	TipoDeEvento(String n, int dm, boolean s) {
		this.nome = n;
		this.dimensaoMAX = dm;
		this.sentado = s;
	}
	
	public boolean isSentado(){
		return sentado;
	}
	
	public String getNome() {
		return nome;
	}
	
	@Override
	public String toString() {
		String tipo = isSentado()? " com ":" sem ";
		return this.nome + "; capacidade: " + this.dimensaoMAX + tipo + "lugares sentados";
	}
}
