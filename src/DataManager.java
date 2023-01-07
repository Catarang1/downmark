import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataManager {

    public static final Path DATAPATH = Paths.get("data");
    static Set<Path> loadedBookPaths;

    public static void loadValidBooks() {
        if (!Files.exists(DATAPATH)) return;
        try {
            loadedBookPaths = Files.list(DATAPATH)
                .collect(Collectors.toSet());
        } catch (IOException e) {e.printStackTrace();};
        
        loadedBookPaths.forEach(path -> {
            try (Stream<Path> bookContents = Files.list(path)) {
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
    public static void loadPages() {
        
    }
}
