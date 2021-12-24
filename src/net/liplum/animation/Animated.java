package net.liplum.animation;

import net.liplum.attribute.IRender;
import net.liplum.attribute.IUpdate;

public interface Animated extends IRender, IUpdate {

    void reset();

    void setEndAfterPlaying();

    void setKeepLastFrame();

    void setEndTime(long sumTime);

    boolean isLastFrame();

    int getX();

    void setX(int x);

    int getY();

    void setY(int y);


}