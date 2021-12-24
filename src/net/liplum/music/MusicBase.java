package net.liplum.music;

public abstract class MusicBase implements Cloneable {

    @Override
    public abstract MusicBase clone();

    public abstract void play();

    public abstract void loop();

    public abstract void stop();
}
