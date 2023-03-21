package presentation.fx.model;

public class Instalacao {

	private String nome;
	private int capacidade;
	private boolean temLugaresSentados;
	
	public Instalacao(String nome, int capacidade, boolean temLugaresSentados) {
		
		this.nome = nome;
		this.capacidade = capacidade;
		this.temLugaresSentados = temLugaresSentados;
	}
	
	@Override
	public String toString() {
		
		String lugares = temLugaresSentados? " com ":" sem ";
		return nome + "; " + "Cap. " + capacidade + ", " +lugares + "lugares";
	}
	
	public String getNome() {
		return nome;
	}

}
