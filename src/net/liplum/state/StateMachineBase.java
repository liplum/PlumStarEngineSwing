package net.liplum.state;

import net.liplum.attribute.IUpdate;

import java.util.LinkedList;

public abstract class StateMachineBase<Owner, StateType extends StateBase<?>> implements IUpdate {

    protected Owner owner;

    protected LinkedList<StateType> stateStack = new LinkedList<>();

    public StateMachineBase(Owner o) {
        this.owner = o;
    }

    public void addNewState(StateType newState) {
        addNewState(newState, true);
    }

    public void addNewState(StateType newState, boolean canEnter) {
        stateStack.add(newState);
        if (canEnter)
            stateStack.getLast().enter();
    }

    public void replaceNewState(StateType newState) {
        replaceNewState(newState, true);
    }

    public void replaceNewState(StateType newState, boolean canEnter) {
        if (!stateStack.isEmpty()) {
            stateStack.getLast().exit();

            stateStack.removeLast();
        }

        addNewState(newState, canEnter);

    }

    public void returnToPrevious() {
        returnToPrevious(true);
    }

    public void returnToPrevious(boolean canEnterAgain) {
        if (!stateStack.isEmpty())
            replaceNewState(stateStack.getLast(), canEnterAgain);
    }

    public void clear() {
        for (StateType state : stateStack)
            state.exit();
        stateStack.clear();
    }

    public void removeBottom() {
        stateStack.getFirst().exit();
        stateStack.removeFirst();
    }

    public void removeTop() {
        stateStack.getLast().exit();
        stateStack.removeLast();
    }

    public Owner getOwner() {
        return owner;
    }
}
