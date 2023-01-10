import java.io.IOException;

import javafx.application.Application;
import javafx.collections.ObservableArray;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

	public static final String version = "1.0r";
	public static final String operatingSystem = System.getProperty("os.name");
	public static final Runtime.Version javaVersion = Runtime.version();

    public static final int WINDOW_WIDTH = 1340;
    public static final int WINDOW_HEIGHT = 700;

	public static HBox ROOT;
	public static Scene SCENE;
	public static Stage STAGE;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		STAGE = primaryStage;
		FXMLLoader l = new FXMLLoader(App.class.getResource("frame.fxml"));
		ROOT = l.load();
		SCENE = new Scene(ROOT, WINDOW_WIDTH, WINDOW_HEIGHT, Color.TRANSPARENT);
		primaryStage.setTitle("Downmark");
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(SCENE);
		   
        primaryStage.show();
	}

}