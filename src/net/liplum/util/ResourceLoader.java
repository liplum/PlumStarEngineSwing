package net.liplum.util;

import net.liplum.music.Music;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Objects;

public class ResourceLoader {
    public static InputStream loadInJar(Class<?> clz, String name) {
        return clz.getResourceAsStream(name);
    }

    public static InputStream loadInThisJar(String name) {
        return ResourceLoader.class.getResourceAsStream(name);
    }

    public static BufferedImage loadImage(String folderName, String fileName) {
        BufferedImage img = null;
        String filePath = "/" + folderName + "/" + fileName;
        try {
            img = ImageIO.read(loadInThisJar(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img;
    }

    public static BufferedImage loadImage(File file) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return img;
    }


    public static Music loadAudio(String folderName, String fileName) {
        Music music;
        String filePath = "/" + folderName + "/" + fileName;

        music = new Music(filePath);
        return music;
    }
}
