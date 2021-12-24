package net.liplum.state;

import net.liplum.attribute.IUpdate;

/**
 * @param <StateMachineType> 此状态的容器
 * @author 普冷姆plum
 * @version 1.1
 */
public abstract class StateBase<StateMachineType extends StateMachineBase<?, ?>> implements IUpdate {

    /**
     * 此状态机的容器
     */
    protected StateMachineType stateMachine;

    /**
     * @param SM 此状态的容器
     */
    public StateBase(StateMachineType SM) {
        stateMachine = SM;
    }

    /**
     * 执行此状态的当前行为
     */
    public abstract void execute();

    /**
     * <pre>
     * 进入状态时自动调用3 <font color=red>注：不保证此方法一定会被调用
     */
    public abstract void enter();

    /**
     * <pre>
     * 离开状态时自动调用 <font color=red>注：此方法一定会被调用
     */
    public abstract void exit();

    /**
     * @return 获得此状态的容器
     */
    public StateMachineType getStateMachine() {
        return stateMachine;
    }

    /**
     * @param sm 此状态的容器
     */
    public void setStateMachine(StateMachineType sm) {
        this.stateMachine = sm;
    }

}
