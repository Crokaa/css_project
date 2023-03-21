package presentation.fx.inputcontroller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import facade.exceptions.ApplicationException;
import facade.services.IEventServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.AtribuirInstalacaoModel;
import presentation.fx.model.Instalacao;

public class AtribuirInstalacaoController extends BaseController{

    @FXML
    private ComboBox<Instalacao> instalacoesComboBox;

    @FXML
    private ComboBox<String> eventosComboBox;
    
    @FXML
    private CheckBox passe;

    @FXML
    private DatePicker dataVendaDatePicker;

    @FXML
    private TextField precoBIndividualTextField;

    @FXML
    private TextField precoBPasseTextField;
    
    private IEventServiceRemote eventService;
    
    private AtribuirInstalacaoModel instalacaoModel;
    
    @FXML
    void atribuirInstalacao(ActionEvent event) {

    	String errorMessages = validateInput();
    	
    	if (errorMessages.length() == 0) {
			try {
			
				String evento = this.instalacaoModel.getSelectedEvento();
				String instalacao = this.instalacaoModel.getSelectedInstalacao().getNome();
				LocalDate inicioVendaLD = dataVendaDatePicker.getValue();
				Date inicioVenda = Date.from(inicioVendaLD.atStartOfDay(ZoneId.systemDefault()).toInstant());
				float precoI = this.instalacaoModel.getPrecoIndividual();
				float precoP = this.instalacaoModel.getPrecoPasse();
		    
				if(this.passe.selectedProperty().getValue())
					this.eventService.atribuirInstalacao(evento, instalacao, inicioVenda, precoI, precoP);
				else
					this.eventService.atribuirInstalacao(evento, instalacao, inicioVenda, precoI);
		    	
				this.instalacaoModel.clearProperties();
				showInfo(i18nBundle.getString("atribuirInstalacao.success"));
				eventosComboBox.getSelectionModel().clearSelection();
				instalacoesComboBox.getSelectionModel().clearSelection();
				dataVendaDatePicker.getEditor().clear();
				passe.selectedProperty().set(false);
				dataVendaDatePicker.setValue(null);

			}catch (Exception e) {
				//mudar para exceptions especificas
				System.out.println(e);
				showInfo("Internal error" + e.getMessage());
			}
		} else
			showError(i18nBundle.getString("atribuirInstalacao.error.validating") + ":\n" + errorMessages);
    }

    @FXML
    void instalacaoSelected(ActionEvent event) {
    	this.instalacaoModel.setSelectedInstalacao(this.instalacoesComboBox.getValue());

    }
    
    @FXML
	void eventoSelected(ActionEvent event) {
    	this.instalacaoModel.setSelectedEvento(this.eventosComboBox.getValue());
	}

	public void setModel(AtribuirInstalacaoModel instalacaoModel) {
		
		this.instalacaoModel = instalacaoModel;
		
		instalacoesComboBox.setItems(instalacaoModel.getInstalacoes());
		instalacoesComboBox.setValue(instalacaoModel.getSelectedInstalacao());
		
		eventosComboBox.setItems(instalacaoModel.getEventos());
		eventosComboBox.setValue(instalacaoModel.getSelectedEvento());
		
		
		precoBIndividualTextField.textProperty().bindBidirectional(instalacaoModel.precoBIProperty(), new NumberStringConverter());
		precoBPasseTextField.textProperty().bindBidirectional(instalacaoModel.precoBPProperty(), new NumberStringConverter());
	}
	
	private String validateInput() {

		StringBuilder sb = new StringBuilder();

		if (this.instalacaoModel.getSelectedInstalacao() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("atribuirInstalacao.invalid.instalacao"));
		}
		if (this.instalacaoModel.getSelectedEvento() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("atribuirInstalacao.invalid.evento"));
		}
		if (this.instalacaoModel.getPrecoIndividual() < 0.0) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("atribuirInstalacao.invalid.precos"));
		}
		if (passe.selectedProperty().getValue() && (this.instalacaoModel.getPrecoPasse() < 0.0)) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("atribuirInstalacao.invalid.precos"));
		}
		if (dataVendaDatePicker.getValue() == null) {
			if (sb.length() > 0)
				sb.append("\n");
			sb.append(i18nBundle.getString("atribuirInstalacao.invalid.data"));
		}
		
		return sb.toString();
	}

	public void addEvento(String nomeEvento) {

		eventosComboBox.getItems().add(nomeEvento);
	}
	
	public void setEventService(IEventServiceRemote eventService) {
		this.eventService = eventService;
	}
	
	public void updateEventos() {

		List<String> listaEventos = this.eventosComboBox.getItems();
		listaEventos.clear();
		
		try {
			Iterable<String> eventos = this.eventService.getNomesEvento();
			
			for(String e : eventos) {
				listaEventos.add(e);
			}
						
		} catch (ApplicationException e) {
			
			showInfo("Internal error" + e.getMessage());
		} 
		
	}

}
