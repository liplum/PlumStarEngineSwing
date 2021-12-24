package net.liplum.animation;

import net.liplum.util.ImageTools;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GradualAnimationFactory {

    public static Animation newGradualAnimation(int frameCount, int x, int y, int width, int height,
                                                Color colorUp, BufferedImage paster,
                                                int dx, int dy, int pasterZoomWidth, int pasterZoomHeight) {

        BufferedImage[] frames = new BufferedImage[frameCount * 2];

        frames[0] = ImageTools.newBufferedImage(width, height, colorUp, paster, dx, dy, pasterZoomWidth, pasterZoomHeight);

        Color c1 = colorUp;
        for (int i = 1; i < frameCount; ++i) {
            c1 = c1.darker().darker();
            frames[i] = ImageTools.newBufferedImage(width, height, c1, paster, dx, dy, pasterZoomWidth, pasterZoomHeight);
        }

        Color c2 = c1;
        for (int i = frameCount; i < (frameCount * 2) - 1; ++i) {
            c2 = c2.brighter().brighter();
            frames[i] = frames[i] = ImageTools.newBufferedImage(width, height, c2, paster, dx, dy, pasterZoomWidth, pasterZoomHeight);
        }

        frames[frameCount * 2 - 1] = ImageTools.newBufferedImage(width, height, colorUp, paster, dx, dy, pasterZoomWidth, pasterZoomHeight);

        net.liplum.animation.Frame[] fs = new net.liplum.animation.Frame[frames.length];

        for (int i = 0; i < fs.length; ++i) {
            fs[i] = new Frame(frames[i], 10);
        }

        return new Animation(x, y, fs);
    }
}
