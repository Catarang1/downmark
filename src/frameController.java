import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

public class FrameController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;

    private double xDrag = 0.0; 
    private double yDrag = 0.0; 
    private double xResize = 0.0; 
    private double yResize = 0.0; 

    @FXML private HBox content;
    @FXML private VBox booklist;
    @FXML private WebView contentView;
    @FXML private VBox draggableArea;
    @FXML private Button exit;
    @FXML private Ellipse exit_graphic;
    @FXML private Button maxi;
    @FXML private Ellipse maxi_graphic;
    @FXML private Button mini;
    @FXML private Ellipse mini_graphic;
    @FXML private ListView<Path> pageList;
    @FXML private Circle resizer;
    @FXML private Text title;

    private InputStream pageListFontStream = App.class.getResourceAsStream("./res/Cubano.ttf");
    private Font pageListFont = Font.loadFont(pageListFontStream, 15);

    private InputStream bookListFontStream = App.class.getResourceAsStream("./res/Cubano.ttf");
    private Font bookListFont = Font.loadFont(bookListFontStream, 20);

    public static String htmlSource = 
        new Scanner(Downmark.class.getResourceAsStream("./res/header.htm"), "UTF-8")
            .useDelimiter("\\A")
                .next();

    @FXML
    void initialize() {
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
    }

    private void setupContent() {
        
        DataManager.loadBookPaths();
        pageList.setItems(DataManager.pages);
        pageList.setCellFactory(arg0 -> {
            return new ListCell<Path>() {
                { // constructor
                    setPrefWidth(0); // avoids the issues
                }
                @Override protected void updateItem(Path item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null || !empty) {
                        int indexOfCell = pageList.getItems().indexOf(item);
                        setFont(pageListFont);
                        setText(item.getFileName().toString());
                        setTextFill(Palette.COLORS[indexOfCell % Palette.COLORS.length]);
                        setGraphicTextGap(14.0);
                        setGraphic(Palette.makePageIcon(indexOfCell));
                        setOnMouseClicked(e -> {
                            String content = DataManager.loadPageContent(item);
                            contentView.getEngine().loadContent(content);
                        });
                    } else {
                        setText(null);
                        setGraphic(null);
                    }
                }
            };
        });

        contentView.getEngine().loadContent(htmlSource);

        booklist.getChildren().clear();
        for (Book book : DataManager.books) {
            VBox node = makeBookIcon(book);
            booklist.getChildren().add(node);
        }

    }

    private VBox makeBookIcon(Book book) {
        Random rand = new Random();
        int randColorIndex = rand.nextInt(Palette.ICON_COLORS.length);
        Text t = new Text(book.getAbbreviation());
        t.setFont(bookListFont);
        t.setFill(Palette.ICON_COLORS[randColorIndex][1]);
        VBox node = new VBox(t);
        node.setAlignment(Pos.CENTER);
        node.setMinSize(38, 38);
        node.setMaxSize(38, 38);
        node.setBackground(Palette.makeBackground(Palette.ICON_COLORS[randColorIndex][0], 8, 0));
        node.setOnMouseClicked(e -> {
            for (Node ico : booklist.getChildren()) ico.getStyleClass().remove("selected");
            node.getStyleClass().add("selected");
            pageList.getItems().clear();
            DataManager.loadPages(book.getPath());
            pageList.setItems(DataManager.pages);
        });
        return node;
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
