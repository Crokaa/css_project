package facade.dto;

import java.io.Serializable;

public class InstalacaoDTO implements Serializable{

	private static final long serialVersionUID = -9214562194033323646L;
	private String nome;
	private int capacidade;
	private boolean temLugaresSentados;
	
	public InstalacaoDTO(String nome, int capacidade, boolean temLugaresSentados) {
		this.nome = nome;
		this.capacidade = capacidade;
		this.temLugaresSentados = temLugaresSentados;
	}
	
	public String getNome() {
		return nome;
	}
	
	public int getCapacidade() {
		return capacidade;
	}

	public boolean isTemLugaresSentados() {
		return temLugaresSentados;
	}
}
