import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class frameController {

    @FXML
    private ResourceBundle resources;

    private double xDrag = 0.0; 
    private double yDrag = 0.0; 
    private double xResize = 0.0; 
    private double yResize = 0.0; 

    @FXML private URL location;
    @FXML private VBox booklist;
    @FXML private WebView contentView;
    @FXML private VBox draggableArea;
    @FXML private Button exit;
    @FXML private Ellipse exit_graphic;
    @FXML private Button maxi;
    @FXML private Ellipse maxi_graphic;
    @FXML private Button mini;
    @FXML private Ellipse mini_graphic;
    @FXML private VBox pagelist;
    @FXML private Circle resizer;
    @FXML private Text title;

    @FXML
    void initialize() {
        assert booklist != null : "fx:id=\"booklist\" was not injected: check your FXML file 'frame.fxml'.";
        assert contentView != null : "fx:id=\"contentView\" was not injected: check your FXML file 'frame.fxml'.";
        assert draggableArea != null : "fx:id=\"draggableArea\" was not injected: check your FXML file 'frame.fxml'.";
        assert exit != null : "fx:id=\"exit\" was not injected: check your FXML file 'frame.fxml'.";
        assert maxi != null : "fx:id=\"maxi\" was not injected: check your FXML file 'frame.fxml'.";
        assert mini != null : "fx:id=\"mini\" was not injected: check your FXML file 'frame.fxml'.";
        assert pagelist != null : "fx:id=\"pagelist\" was not injected: check your FXML file 'frame.fxml'.";
        assert resizer != null : "fx:id=\"resizer\" was not injected: check your FXML file 'frame.fxml'.";
        assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'frame.fxml'.";

        setupHeader("Downmark");
        setupContent();
        setupFooter();
    }

    private void setupHeader(String title) {
        this.title.setText(title);
        makeWindowDraggableBy(draggableArea);
        mini.setOnMouseClicked(e -> App.STAGE.setIconified(true));
        maxi.setOnMouseClicked(e -> App.STAGE.setMaximized(!App.STAGE.isMaximized()));
        exit.setOnMouseClicked(e -> App.STAGE.close());

        mini.setOnMouseEntered(e -> mini_graphic.setFill(Palette.MINI_HOVER));
        mini.setOnMouseExited(e -> mini_graphic.setFill(Palette.MINI));
        maxi.setOnMouseEntered(e -> maxi_graphic.setFill(Palette.MAXI_HOVER));
        maxi.setOnMouseExited(e -> maxi_graphic.setFill(Palette.MAXI));
        exit.setOnMouseEntered(e -> exit_graphic.setFill(Palette.EXIT_HOVER));
        exit.setOnMouseExited(e -> exit_graphic.setFill(Palette.EXIT));
        System.out.println("ready!");
    }

    private void setupContent() {
        DataManager.loadValidBooks();
    }

    private void setupFooter() {
        resizer.setOnMousePressed(event -> {
            xResize = event.getScreenX();
            yResize = event.getScreenY();
        });

        resizer.setOnMouseDragged(event -> {
            double xResult = App.STAGE.getWidth() - (xResize - event.getScreenX());
            double yResult = App.STAGE.getHeight() - (yResize - event.getScreenY());

            App.STAGE.setWidth(xResult);
            App.STAGE.setHeight(yResult);
            xResize = event.getScreenX();
            yResize = event.getScreenY();
        });
    }

    private void makeWindowDraggableBy(Node n) {
        n.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xDrag = event.getSceneX();
                yDrag = event.getSceneY();
            }
        });
        n.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                App.STAGE.setX(event.getScreenX() - xDrag);
                App.STAGE.setY(event.getScreenY() - yDrag);
            }
        });
    }

}
