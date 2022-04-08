package net.liplum.animation;

import java.awt.image.BufferedImage;

/**
 * Generate an animation which has the same interval.
 */
public class LinerAnimeFactory {

    private long duration = 0;

    /**
     * @param interval the interval between two frames
     */
    public LinerAnimeFactory(long interval) {
        this.duration = interval;
    }

    public Animation newAnime(int AnimationX, int AnimationY, BufferedImage... bs) {
        Frame[] fs = new Frame[bs.length];
        for (int i = 0; i < bs.length; ++i)
            fs[i] = new Frame(bs[i], duration);
        return new Animation(AnimationX, AnimationY, fs);
    }
}
