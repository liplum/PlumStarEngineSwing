package net.liplum.state;

import net.liplum.attribute.IRender;
import net.liplum.attribute.IKey;
import net.liplum.attribute.IMouse;
import net.liplum.attribute.IMouseMotion;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class InteractionalStateMachineBase<Owner, StateType extends InteractionalStateBase<?>> extends
        StateMachineBase<Owner, StateType> implements IKey, IMouse, IMouseMotion, IRender {

    public InteractionalStateMachineBase(Owner o) {
        super(o);
    }

    @Override
    public void update(long delta) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.update(delta);
    }

    @Override
    public void render(Graphics g) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.render(g);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.mouseDragged(e);

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.mouseMoved(e);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.mouseClicked(e);

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.mouseReleased(e);

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.mouseEntered(e);

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.mouseExited(e);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.keyTyped(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.keyPressed(e);

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.keyReleased(e);
    }

    public void execute() {
        if (!stateStack.isEmpty())
            for (StateType state : stateStack)
                state.execute();
    }
}
