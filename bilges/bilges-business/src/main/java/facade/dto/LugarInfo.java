package facade.dto;

import java.io.Serializable;

public class LugarInfo implements Comparable<LugarInfo>, Serializable {

	private static final long serialVersionUID = -8183878881701127076L;

	private String fila;
	
	private int cadeira;
	
	public LugarInfo(String fila, int cadeira) {
		
		this.fila = fila;
		this.cadeira = cadeira;
	}
	
	public int getCadeira() {
		return cadeira;
	}
	
	public String getFila() {
		return fila;
	}
	
	@Override
	public int compareTo(LugarInfo lugar) {

		if(this.fila.compareTo(lugar.fila) == 0) {
			return this.cadeira - lugar.cadeira;
		}
		else
			return this.fila.compareTo(lugar.fila);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LugarInfo))
			return false;
		LugarInfo other = (LugarInfo) obj;
		if (cadeira != other.cadeira)
			return false;
		if (fila == null) {
			if (other.fila != null)
				return false;
		} else if (!fila.equals(other.fila))
			return false;
		return true;
	}

	/**
	 * Gerado automaticamente
	 * para cumprir contrato do equals
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cadeira;
		result = prime * result + ((fila == null) ? 0 : fila.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return this.fila+"-"+this.cadeira;
	}
	
}
