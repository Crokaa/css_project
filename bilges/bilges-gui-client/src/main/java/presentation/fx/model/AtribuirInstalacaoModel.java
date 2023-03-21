package presentation.fx.model;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AtribuirInstalacaoModel {

	//private final StringProperty nomeEvento;
	private final FloatProperty precoBIndividual;
	private final FloatProperty precoBPasse;

	private final ObjectProperty<String> eventoSelecionado;
	private final ObservableList<String> eventos;

	private final ObjectProperty<Instalacao> instalacaoSelecionada;
	private final ObservableList<Instalacao> instalacoes;

	public AtribuirInstalacaoModel(IEventServiceRemote es) {

		//nomeEvento = new SimpleStringProperty();
		precoBIndividual = new SimpleFloatProperty();
		precoBPasse = new SimpleFloatProperty();

		eventos = FXCollections.observableArrayList();
		instalacoes = FXCollections.observableArrayList();
		
		try {
			es.getInstalacoes().forEach(i -> instalacoes.add(new Instalacao(i.getNome(), i.getCapacidade(), i.isTemLugaresSentados())));
			es.getNomesEvento().forEach(e -> eventos.add(e));
		}
		catch (ApplicationException e) {
			System.out.println("Exception" + e.getMessage());
		}

		instalacaoSelecionada = new SimpleObjectProperty<>(null);
		eventoSelecionado = new SimpleObjectProperty<>(null);
	}

	//	public String getNomeEvento() {
	//		return nomeEvento.get();
	//	}

	//	public StringProperty nomeEventoProperty() {
	//		return nomeEvento;
	//	}

	public void clearProperties() {

	}

	public ObservableList<Instalacao> getInstalacoes() {
		return instalacoes;
	}

	public Instalacao getSelectedInstalacao() {
		return instalacaoSelecionada.get();
	}

	public void setSelectedInstalacao(Instalacao value) {
		instalacaoSelecionada.set(value);
	}


	public ObservableList<String> getEventos() {
		return eventos;
	}

	public void setSelectedEvento(String value) {
		eventoSelecionado.set(value);
	}

	public String getSelectedEvento() {
		return eventoSelecionado.get();
	}

	public FloatProperty precoBIProperty() {

		return precoBIndividual;
	}

	public FloatProperty precoBPProperty() {

		return precoBPasse;
	}
	
	public float getPrecoIndividual() {
		return precoBIndividual.get();
	}
	
	public float getPrecoPasse() {
		return precoBPasse.get();
	}

}
