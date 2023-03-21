package facade.dto;

import java.io.Serializable;

public class TipoEventoDTO implements Serializable {

	private static final long serialVersionUID = 1813913233431175257L;
	private final String nome;
	private final int dimensaoMAX;
	private final boolean sentado;

	public TipoEventoDTO(String n, int dm, boolean s) {
		this.nome = n;
		this.dimensaoMAX = dm;
		this.sentado = s;
	}

	public String getNome() {
		return nome;
	}

	public int getDimensaoMAX() {
		return dimensaoMAX;
	}

	public boolean isSentado() {
		return sentado;
	}

}
