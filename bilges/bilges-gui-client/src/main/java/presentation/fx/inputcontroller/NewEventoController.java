package presentation.fx.inputcontroller;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.DataEvento;
import presentation.fx.model.NewEventoModel;
import presentation.fx.model.TipoDeEvento;

public class NewEventoController extends BaseController{

	@FXML
	private ComboBox<TipoDeEvento> tiposEventoComboBox;

	@FXML
	private TextField nomeEventoTextField;

	@FXML
	private TextField numEmpresaTextField;

	@FXML
	private DatePicker diaEvento;

	@FXML
	private Button adicionaDataButton;

	@FXML
	private ListView<DataEvento> listaDatas;

	@FXML
	private Spinner<Integer> horaInicio;

	@FXML
	private Spinner<Integer> minutosInicio;

	@FXML
	private Spinner<Integer> horaFim;

	@FXML
	private Spinner<Integer> minutosFim;

	private NewEventoModel eventoModel;

	private IEventServiceRemote eventService;
	
	@FXML
	void criarEventoAction(ActionEvent event) {

		List<Entry<Date, Date>> datasEntries = new ArrayList<>();
		listaDatas.getItems()
		.forEach(dia -> datasEntries.add(new AbstractMap.SimpleEntry<>(dia.getInicio(), dia.getFim())));

		String errorMessages = validateInput(datasEntries);

		if (errorMessages.length() == 0) {
			try {
			
				String tipo = this.eventoModel.getSelectedTipoEvento().getNome();
				String nome = this.eventoModel.getNome();
				long numEmpresa = this.eventoModel.getNumRegistoEmpresa();
				
				for(DataEvento dia : listaDatas.getItems()) {
					System.out.println(dia);
				}
				
				this.eventService.criarNovoEvento(tipo, nome, datasEntries, numEmpresa);
				
				this.eventoModel.clearProperties();
				listaDatas.getItems().clear();
				tiposEventoComboBox.getSelectionModel().clearSelection();
				diaEvento.getEditor().clear();
				diaEvento.setValue(null);

				showInfo(i18nBundle.getString("new.event.success"));

			}catch (ApplicationException e) {
				System.out.println(e.getMessage());
				showInfo(e.getMessage());
			}
		} else
			showError(i18nBundle.getString("new.event.error.validating") + ":\n" + errorMessages);

	}

	@FXML
	void tipoEventoSelected(ActionEvent event) {

		this.eventoModel.setSelectedTipoEvento(this.tiposEventoComboBox.getValue());
	}

	@FXML
	void addData(ActionEvent event) {

		if(diaEvento.getValue() == null) {
			showError(i18nBundle.getString("new.event.error.day"));
			return;
		}

		int ano = diaEvento.getValue().getYear();
		int mes = diaEvento.getValue().getMonthValue();
		int dia = diaEvento.getValue().getDayOfMonth();
		int horaInicioValue = horaInicio.getValue();
		int minutosInicioValue = minutosInicio.getValue();
		int horaFimValue = horaFim.getValue();
		int minutosFimValue = minutosFim.getValue();

		Date dataInicio = new Date(ano - 1900, mes - 1, dia, horaInicioValue, minutosInicioValue);
		Date dataFim = new Date(ano - 1900, mes - 1, dia, horaFimValue, minutosFimValue);

		if(dataFim.before(dataInicio)){
			showError(i18nBundle.getString("new.event.invalid.hours"));
			return;
		}

		listaDatas.getItems().add(new DataEvento(dataInicio, dataFim));
	}

	@FXML
	void removeData(ActionEvent event) {

		listaDatas.getItems().remove(this.listaDatas.getSelectionModel().getSelectedItem());
	}

	public void setModel(NewEventoModel eventoModel) {

		this.eventoModel = eventoModel;
		tiposEventoComboBox.setItems(eventoModel.getTiposEvento());
		tiposEventoComboBox.setValue(eventoModel.getSelectedTipoEvento());
		nomeEventoTextField.textProperty().bindBidirectional(eventoModel.nomeProperty());
		numEmpresaTextField.textProperty().bindBidirectional(eventoModel.numEmpresaProperty(), new NumberStringConverter());
	}

	private String validateInput(List<Entry<Date, Date>> datasEntries) {

		StringBuilder sb = new StringBuilder();
		String nome = this.eventoModel.getNome();

		if (this.eventoModel.getSelectedTipoEvento() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.event.invalid.tipoEvento"));
		}
		if (nome == null || nome.isEmpty()) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.event.invalid.nome"));
		}
		if (this.eventoModel.getNumRegistoEmpresa() <= 0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.event.invalid.numEmpresa"));
		}
		if(listaDatas.getItems().isEmpty()){
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.event.invalid.diasEvento"));
		}
		else if(!datesAreValid(datasEntries)) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("new.event.invalid.diasConsecutivos"));
		}

		return sb.toString();
	}

	private boolean datesAreValid(List<Entry<Date, Date>> datas) {

		Date inicio;
		Date fim;

		for (int i = 0; i < datas.size(); i++) {

			inicio = datas.get(i).getKey();
			fim = datas.get(i).getValue();
			
			if(i > 0) {
				System.out.println(inicio);
				Calendar c = Calendar.getInstance();
				c.setTime(inicio);
				c.add(Calendar.DATE, -1);
				
				if (!c.getTime().equals(datas.get(i-1).getKey())) {
					return false;
				}
			}
		}

		return true;
	}

	public void setEventService(IEventServiceRemote eventService) {
		this.eventService = eventService;
	}

}
