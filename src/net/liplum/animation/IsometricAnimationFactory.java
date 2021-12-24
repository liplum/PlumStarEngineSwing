package net.liplum.animation;

import java.awt.image.BufferedImage;

/**
 * @author 普冷姆plum
 * 会产生每帧持续时间等长的动画
 */
public class IsometricAnimationFactory {

    private long duration = 0;

    /**
     * @param duration 每帧相隔的时间
     */
    public IsometricAnimationFactory(long duration) {
        this.duration = duration;
    }

    public Animation newAnimation(int AnimationX, int AnimationY, BufferedImage... bs) {
        Frame[] fs = new Frame[bs.length];
        for (int i = 0; i < bs.length; ++i)
            fs[i] = new Frame(bs[i], duration);
        return new Animation(AnimationX, AnimationY, fs);
    }
}
