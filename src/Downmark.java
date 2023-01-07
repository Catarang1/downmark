import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Downmark {

    private static final Pattern header_regex = Pattern.compile("^#{1,6} .*$", Pattern.DOTALL);
    private static final Pattern hr_regex = Pattern.compile("^-{3}.*$", Pattern.DOTALL);
    private static final Pattern blockquote_regex = Pattern.compile("^> .*$", Pattern.DOTALL);
    private static final Pattern img_regex = Pattern.compile("^!\\[[^\\]]+\\]\\([^)]+\\).*$", Pattern.DOTALL);

    public static String mdFile = 
        new Scanner(Downmark.class.getResourceAsStream("./res/elements_test.md"), "UTF-8")
            .useDelimiter("\\A")
                .next();

    public static String decode(String mdString) {
        String[] lines = mdString.split("\n");
        int processed = 0;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].substring(0, lines[i].length() - 1);
            if (header_regex.matcher(line).matches()) {
                long order = line.chars().filter(c -> c == '#').count();
                lines[i] = line.replaceAll("^#+ ", "<h" + order + ">");
                processed++;
            } else if (hr_regex.matcher(line).matches()) {
                lines[i] = "<hr/>";
                processed++;
            } else if (blockquote_regex.matcher(line).matches()) {
                line = line.replaceAll("> ", "<blockquote>");
                line += "</blockquote>";
                lines[i] = line;
                processed++;
            } else if (img_regex.matcher(line).matches()) { 
                String src = ""; String alt = "";
                line = "<img src=\"" + src + "\" alt=\"" + alt +"\" >";
                System.out.println(line);
            }
        }

        System.out.println(processed + "/" +  lines.length);
        // for (String line : lines) {
        //     System.out.println(line);
        // }
        mdString = String.join("\n", lines);
        return String.join("\n", lines);
    }


    
    public static String htmlSource = 
        new Scanner(Downmark.class.getResourceAsStream("./res/header.htm"), "UTF-8")
            .useDelimiter("\\A")
                .next();

    public static String get() {

        String[] lines = htmlSource.split("\n");
        int bodyIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.contains("<body>")) {
                bodyIndex = i;
                break;
            }
        }

        String[] content = mdFile.split("\n");
        for (int i = 0; i < content.length; i++) {
            content[i] += "<br/>";
        }

        String[] head = Arrays.copyOfRange(lines, 0, bodyIndex);
        String[] footer = Arrays.copyOfRange(lines, bodyIndex, lines.length);
        String[] merged = Utils.concatArrays(head, content, footer);
        
        String.join("\n", merged);

        return String.join("\n", merged);
    }

    
}
