package presentation.web.model;

public class CompraBPasseModel extends Model {

	private String nomeEvento;

	private String referencia;
	private String entidade;
	private String preco;
	private String email;
	
	private String quantidade;
	private String maxQuantidade;


	public String getNomeEvento() {
		return nomeEvento;
	}
	

	public String getEntidade() {
		return entidade;
	}


	public String getReferencia() {
		return referencia;
	}


	public String getPreco() {
		return preco;
	}
	
	
	public String getEmail() {
		return email;
	}
	
	
	public String getQuantidade() {
		return quantidade;
	}
	
	
	public String getMaxQuantidade() {
		return maxQuantidade;
	}
	
	
	
	public void setNomeEvento(String nEvento) {
		this.nomeEvento = nEvento;
	}
	

	public void setReferencia(String r) {
		referencia = r;
	}

	public void setEntidade(String e) {
		entidade = e;
	}		
	public void setPreco(String p) {
		preco = p;
	}
	
	
	public void setEmail(String e) {
		this.email = e;
	}
	
	
	public void setQuantidade(String q) {
		this.quantidade = q;
	}
	
	
	public void setMaxQuantidade(String mq) {
		this.maxQuantidade = mq;
	}
	



	public void clearFields() {
		nomeEvento = "";
		referencia = "";
		entidade = "";
		preco = "";
		email = "";
		quantidade = "";
		maxQuantidade = "";


	}

}
