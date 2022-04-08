package net.liplum.util;

import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTools {

    /**
     * @param g      background
     * @param paster paster
     * @param dx     x offset
     * @param dy     y offset
     * @param width  the target width of paster
     * @param height the target height of paster
     */
    public static void pasterImage(Graphics g, BufferedImage paster, int dx, int dy, int width, int height) {
        g.drawImage(paster, dx, dy, width, height, null);
    }

    /**
     * @param color        the background. If null, use default.
     * @param paster       the paster. If null, no paster
     * @param dx           x offset
     * @param dy           y offset
     * @param pasterWidth  the target width of paster
     * @param pasterHeight the target height of paster
     * @return an ARGB image
     */
    public static BufferedImage newBufferedImage(int width, int height, @Nullable Color color, @Nullable BufferedImage paster, int dx, int dy, int pasterWidth, int pasterHeight) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        if (color != null)
            g.setColor(color);
        g.fillRect(0, 0, width, height);
        if (paster != null)
            pasterImage(g, paster, dx, dy, pasterWidth, pasterHeight);
        return image;
    }
}
