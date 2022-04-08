package net.liplum.state;

import net.liplum.attribute.IRender;
import net.liplum.attribute.IKey;
import net.liplum.attribute.IMouse;
import net.liplum.attribute.IMouseMotion;
import net.liplum.inputhandler.InputHandlerBase;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class InteractionalStateBase<StateMachineType extends InteractionalStateMachineBase<?, ?>>
        extends StateBase<StateMachineType> implements IKey, IMouse, IMouseMotion, IRender {

    protected volatile InputHandlerBase input;

    public InteractionalStateBase(StateMachineType SM) {
        super(SM);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (input != null)
            input.mouseDragged(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (input != null)
            input.mouseMoved(e);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (input != null)
            input.mouseClicked(e);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (input != null)
            input.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (input != null)
            input.mouseReleased(e);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (input != null)
            input.mouseEntered(e);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (input != null)
            input.mouseExited(e);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (input != null)
            input.keyTyped(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (input != null)
            input.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (input != null)
            input.keyReleased(e);

    }
}
