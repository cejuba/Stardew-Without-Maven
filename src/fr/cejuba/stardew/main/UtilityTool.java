package fr.cejuba.stardew.main;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class UtilityTool {

    public WritableImage scaleImage(Image inputImage, int targetWidth, int targetHeight) {
        WritableImage scaledImage = new WritableImage(targetWidth, targetHeight);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = scaledImage.getPixelWriter();

        double scaleX = inputImage.getWidth() / targetWidth;
        double scaleY = inputImage.getHeight() / targetHeight;

        for (int y = 0; y < targetHeight; y++) {
            for (int x = 0; x < targetWidth; x++) {
                int argb = reader.getArgb((int) (x * scaleX), (int) (y * scaleY));
                writer.setArgb(x, y, argb);
            }
        }

        return scaledImage;
    }
}
