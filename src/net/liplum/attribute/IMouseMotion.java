package net.liplum.attribute;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public interface IMouseMotion extends MouseMotionListener {

    @Override
    default void mouseDragged(MouseEvent arg0) {
    }

    @Override
    default void mouseMoved(MouseEvent arg0) {
    }

}
