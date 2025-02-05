import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ImageLoader {
    public static ImageIcon loadImage(String imagePath) {
        try {
            if (imagePath.startsWith("http")) {
                // Загружаем изображение из URL
                URL url = new URL(imagePath);
                BufferedImage image = ImageIO.read(url);
                return new ImageIcon(image);
            } else {
                // Загружаем локальное изображение
                File file = new File("assets/" + imagePath);
                BufferedImage image = ImageIO.read(file);
                return new ImageIcon(image);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
