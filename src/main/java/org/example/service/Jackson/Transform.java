package org.example.service.Jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.example.model.TierList;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Transform {

    private final ObjectMapper mapper = new ObjectMapper();

    public void setJson(String filePath,TierList tierList) {
        File fichierJson = new File(filePath);
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fichierJson,tierList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TierList getJson(String filePath) {
        File fichierJson = new File(filePath);
        try {
            return mapper.readValue(fichierJson, TierList.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public byte[] convertImageEnByte(Image image){
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            ImageIO.write(bImage, "png", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
