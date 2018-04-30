package gui;

import gui.Browser;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

//Create an instance of an application
public class MainGUi extends Application {
	private Scene scene;

	@Override
	public void start(Stage stage) {
		// create the scene
		stage.setTitle("Tournament Application");
		scene = new Scene(new Browser(), 1200, 950, Color.web("#666970"));
		Image img = new Image("/webTable/resources/seddyboy.jpg");
		stage.getIcons().add(img);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
