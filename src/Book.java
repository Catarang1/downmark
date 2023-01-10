import java.nio.file.Path;

import javafx.scene.image.Image;

public class Book {
    
    private Path path;
    private String name;
    private String abbreviation; 
    private Image icon;


    public Book(Path path) {
        this.path = path;
        this.name = path.getFileName().toString().toLowerCase();

        String[] nameParts = name.split(" ");
        StringBuilder sb = new StringBuilder(64);
        for (String part : nameParts)  sb.append(part.charAt(0));
        this.abbreviation = sb.toString().toUpperCase();

        // String imgFileName;
        // if (Files.exists(path.resolve("icon.png")))
        //     imgFileName = "icon.png";
        // else if (Files.exists(path.resolve("icon.jpg")))
        //     imgFileName = "icon.jpg";
        // else if (Files.exists(path.resolve("icon.gif")))
        //     imgFileName = "icon.gif";
        // else 
        //     imgFileName = null;

        // if (imgFileName != null) {
        //     Path imgPath = path.resolve(imgFileName);
        //     try (FileInputStream inputstream = new FileInputStream(path.resolve(imgPath).toString())) {
        //         icon = new Image(inputstream);
        //         return;
        //     } catch (IOException e) {
        //         e.printStackTrace();
        //     } 
        // }
        icon = null;
    }   
    

    public Path getPath() {
        return this.path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Image getIcon() {
        return this.icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }

    @Override
    public String toString() {
        return this.abbreviation;
    }


}
