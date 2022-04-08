package net.liplum.music;

public interface IMusic extends Cloneable {

    IMusic clone();

    void play();

    void loop();

    void stop();
}
