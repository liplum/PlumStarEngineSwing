package net.liplum.state;

import net.liplum.attribute.IUpdate;

public abstract class StateBase<StateMachineType extends StateMachineBase<?, ?>> implements IUpdate {

    protected StateMachineType stateMachine;

    public StateBase(StateMachineType SM) {
        stateMachine = SM;
    }

    public abstract void execute();

    public abstract void enter();

    public abstract void exit();

    public StateMachineType getStateMachine() {
        return stateMachine;
    }

    public void setStateMachine(StateMachineType sm) {
        this.stateMachine = sm;
    }
}
