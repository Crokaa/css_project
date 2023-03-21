package presentation.fx.model;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewEventoModel {

	private final StringProperty nome;
	private final LongProperty numRegistoEmpresa;

	private final ObjectProperty<TipoDeEvento> tipoSelecionado;
	private final ObservableList<TipoDeEvento> tiposDeEvento;


	public NewEventoModel(IEventServiceRemote es) {

		nome = new SimpleStringProperty();
		numRegistoEmpresa = new SimpleLongProperty();

		tiposDeEvento = FXCollections.observableArrayList();
		if(es != null) {
			try {

				es.getTiposEvento().forEach(t -> tiposDeEvento.add(new TipoDeEvento(t.getNome(), t.getDimensaoMAX(), t.isSentado())));

			} catch (ApplicationException e) {
				//nao ha tipos
			}
		}

		tipoSelecionado = new SimpleObjectProperty<>(null);
	}

	public String getNome() {
		return nome.get();
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public ObservableList<TipoDeEvento> getTiposEvento() {
		return tiposDeEvento;
	}

	public TipoDeEvento getSelectedTipoEvento() {
		return tipoSelecionado.get();
	}

	public LongProperty numEmpresaProperty() {

		return numRegistoEmpresa;
	}
	
	public long getNumRegistoEmpresa() {
		return numRegistoEmpresa.get();
	}
	
	

	public void clearProperties() {
		nome.set("");
		numEmpresaProperty().set(0);
		tipoSelecionado.set(null);
	}

	public void setSelectedTipoEvento(TipoDeEvento value) {
		tipoSelecionado.set(value);
	}

}
