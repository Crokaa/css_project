package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import facade.services.IEventServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.AtribuirInstalacaoController;
import presentation.fx.inputcontroller.NewEventoController;
import presentation.fx.model.AtribuirInstalacaoModel;
import presentation.fx.model.NewEventoModel;

public class Startup extends Application {

	private static IEventServiceRemote eventService;

	@Override 
	public void start(Stage stage) throws IOException {

		BorderPane root = new BorderPane();

		//ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("en", "UK"));
		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));

		FXMLLoader eventoLoader = new FXMLLoader(getClass().getResource("/fxml/criarEvento.fxml"), i18nBundle);
		root.setLeft(eventoLoader.load());
		NewEventoController eventoController = eventoLoader.getController();

		NewEventoModel eventoModel = new NewEventoModel(eventService);
		eventoController.setModel(eventoModel);
		eventoController.setEventService(eventService);
		eventoController.setI18NBundle(i18nBundle);

		FXMLLoader instalacaoLoader = new FXMLLoader(getClass().getResource("/fxml/atribuirInstalacao.fxml"), i18nBundle);
		root.setRight(instalacaoLoader.load());
		AtribuirInstalacaoController instalacaoController = instalacaoLoader.getController();

		AtribuirInstalacaoModel instalacaoModel = new AtribuirInstalacaoModel(eventService);
		instalacaoController.setModel(instalacaoModel);
		instalacaoController.setEventService(eventService);
		instalacaoController.setI18NBundle(i18nBundle);

		Scene scene = new Scene(root, 1000, 650);
		stage.setTitle(i18nBundle.getString("application.title"));
		stage.setScene(scene);
		stage.show();
	}

	public static void startGUI(IEventServiceRemote eventService) {

		Startup.eventService = eventService;
		launch();
	}
}
