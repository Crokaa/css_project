package presentation.web.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompraBLugaresModel extends Model {

	private String nomeEvento;

	private String referencia;
	private String entidade;
	private String preco;
	private ArrayList<String> lugares;
	private String email;
	private ArrayList<String> datas;
	private String dataEscolhida;


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
	
	public ArrayList<String> getDatas(){
		return datas;
	}
	
	public String getDataEscolhida() {
		return dataEscolhida;
	}
	
	public void setNomeEvento(String nEvento) {
		this.nomeEvento = nEvento;
	}
	
	public ArrayList<String> getLugares(){
		return lugares;
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
	
	public void setLugares(List<String> l) {
		lugares = (ArrayList<String>) l;
	}
	
	public void setEmail(String e) {
		this.email = e;
	}
	
	public void setDatas(List<String> datas) {
		this.datas = (ArrayList<String>) datas;
	}
	
	public void setDataEscolhida(String d) {
		dataEscolhida = d;
	}



	public void clearFields() {
		nomeEvento = "";
		referencia = "";
		entidade = "";
		preco = "";
		email = "";


	}

}
