package business.tipo_evento;

/**
 * Classe representa tipos de eventos
 * 
 * @author Catarina Lima 52787
 * @author Andre Silva 52809
 * @author Joao Oliveira 52839
 *
 */
public enum TipoEvento {
	
	TETEATETE ("TeteATete", 6, true),
	BANDOSENTADO ("BandoSentado", 1000, true),
	MULTIDAOEMPE ("MultidaoEmPe", 500000, false);
	
	private final String nome;
	private final int dimensaoMAX;
	private final boolean sentado;
	
	
	/**
	 * Construtor da classe
	 * 
	 * @param n nome do tipo de evento
	 * @param dm dimensao maxima de assistencia do tipo de evento
	 * @param s se o tipo de evento suporta lugares sentados
	 */
	private TipoEvento(String n, int dm, boolean s) {
		this.nome = n;
		this.dimensaoMAX = dm;
		this.sentado = s;
	}
	
	
	/**
	 * Devolve o nome do tipo de evento
	 * 
	 * @return nome do tipo do evento
	 */
	public String getNome() {
		return nome;
	}
	
	
	/**
	 * Devolve a dimensao maxima de assistencia do tipo de evento
	 * 
	 * @return dimensao maxima de assistencia
	 */
	public int getDimMax() {
		return dimensaoMAX;
	}
	
	
	/**
	 * Verifica se o tipo de evento suporta lugares sentados
	 * 
	 * @return true caso suporte lugares sentados, false caso contrario
	 */
	public boolean isSentados() {
		return sentado;
	}
	
	
	@Override
	public String toString() {
		String s = "Tipo de evento: " + nome;
		if (sentado) {
			s += " Suporte lugares sentados.";
		}
		else {
			s += " Suporta lugares em pe.";
		}
		
		s += " Tem uma assistencia maxima de " + dimensaoMAX;
		return s;
	}
	

	

}
