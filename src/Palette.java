import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

public class Palette {
    public static Color EXIT = Color.web("#e10a35");
    public static Color MAXI = Color.web("#0cc258");
    public static Color MINI = Color.web("#ffb200");
    public static Color EXIT_HOVER = Color.web("#9e0422");
    public static Color MAXI_HOVER = Color.web("#067936");
    public static Color MINI_HOVER = Color.web("#7c5604");

    public static Color ORANGE = Color.web("#ec6553");
    public static Color GREEN = Color.web("#76ff65");
    public static Color PURPLE = Color.web("#ff7edb");
    public static Color YELLOW = Color.web("#eab308");
    public static Color BLUE = Color.web("#3abdfc");

    public static Color[] COLORS = new Color[]{ORANGE, GREEN, PURPLE, YELLOW, BLUE};

    public static String SVG_PAGE_CONTENT = "M -7 -6 Q -7 -9 -4 -9 L 4 -9 Q 7 -9 7 -6 L 7 6 Q 7 9 4 9 L -4 9 Q -7 9 -7 6 Z M 3 -9 L 3 -7 Q 3 -5 5 -5 L 7 -5 M -4 1 L 4 1 M -3 -2 L 3 -2 M -2 4 L 2 4";
    
    public static SVGPath makePageIcon(int index) {
        SVGPath page = new SVGPath();
        page.setContent(Palette.SVG_PAGE_CONTENT);
        page.setFill(null);
        page.setStrokeWidth(1.6);
        page.setStroke(Palette.COLORS[index % Palette.COLORS.length]);
        return page;
    }
}
