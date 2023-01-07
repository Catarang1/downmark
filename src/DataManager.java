import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {

    private static final Path DATAPATH = Paths.get("data");
    public static List dataFolders;
    public static ObservableList books;

    public static void loadValidBooks() {
        try {
            Files.createDirectory(DATAPATH);
            
        } catch (IOException e) {
            if (e.getClass().equals(FileAlreadyExistsException.class)) 
                System.out.println("data folder exists");
            else if (e.getClass().equals(UnsupportedOperationException.class)) 
                System.err.println("attributes for data folder error");
            else if (e.getClass().equals(SecurityException.class)) 
                System.err.println("could not write data folder privacy");
            else {}
        }

        try{
            dataFolders =  Files.list(DATAPATH).map(Book::new).toList();
            books = FXCollections.observableList(dataFolders);
            System.out.println("datafolders len :: " + dataFolders.size());
            System.out.println("books len :: " + books.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
