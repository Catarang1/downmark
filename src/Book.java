import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.scene.image.Image;

public class Book {
    
    private Path path;
    private Image icon;


    public Book(Path path) {
        this.path = path;
        boolean hasPngIco = Files.exists(path.resolve("icon.png"));
        boolean hasJpgIco = Files.exists(path.resolve("icon.jpg"));
        boolean hasGifIco = Files.exists(path.resolve("icon.gif"));
        try (FileInputStream inputstream = new FileInputStream("C:\\images\\image.jpg")) {
            Image image = new Image(inputstream);
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }    

}
