package net.liplum.attribute;

import java.awt.event.MouseEvent;

public interface MouseListener extends java.awt.event.MouseListener {

    @Override
    default void mouseClicked(MouseEvent e) {
    }

    @Override
    default void mouseEntered(MouseEvent e) {
    }

    @Override
    default void mouseExited(MouseEvent arg0) {
    }

    @Override
    default void mousePressed(MouseEvent e) {
    }

    @Override
    default void mouseReleased(MouseEvent e) {
    }

}
