package net.liplum.attribute;

import java.awt.event.MouseEvent;

public interface MouseMotionListener extends java.awt.event.MouseMotionListener {

    @Override
    default void mouseDragged(MouseEvent arg0) {
    }

    @Override
    default void mouseMoved(MouseEvent arg0) {
    }

}
