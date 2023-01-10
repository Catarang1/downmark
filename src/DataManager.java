import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataManager {

    private static final Path DATAPATH = Paths.get("data");
    public static List<Book> books;
    public static ObservableList<Path> pages = FXCollections.observableArrayList();

    public static void loadBookPaths() {
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
            books =  Files.list(DATAPATH).map(Book::new).toList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPages(Path p) {
        pages.clear();
        try {
            
            List<Path> mdFiles = Files.walk(p, 1)
                .filter(path -> !Files.isDirectory(path))
                .filter(x -> x.toString().endsWith(".md"))
                .collect(Collectors.toList());

            pages.addAll(mdFiles);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String loadPageContent(Path p) {
        try {
            String content = new Scanner(new FileInputStream(p.toAbsolutePath().toString()), "UTF-8")
                .useDelimiter("\\A")
                    .next();

            String htmlSource = 
                new Scanner(Downmark.class.getResourceAsStream("./res/header.htm"), "UTF-8")
                    .useDelimiter("\\A")
                        .next();
            
            content = Downmark.decode(content);
            htmlSource = htmlSource.replace("content", content);
            return htmlSource;        
        } catch (FileNotFoundException e) {
            return "failed to load page content";
        }
    }


}
