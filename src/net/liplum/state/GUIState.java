package net.liplum.state;

public abstract class GUIState extends InteractionalStateBase<GUIStateMachine> {

    public GUIState(GUIStateMachine StateMachine) {
        super(StateMachine);
    }
}
