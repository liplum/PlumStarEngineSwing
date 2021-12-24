package net.liplum.attribute;

import java.awt.event.KeyEvent;

public interface KeyListener extends java.awt.event.KeyListener {

    @Override
    default void keyPressed(KeyEvent e) {
    }

    @Override
    default void keyReleased(KeyEvent e) {
    }

    @Override
    default void keyTyped(KeyEvent e) {
    }

}
