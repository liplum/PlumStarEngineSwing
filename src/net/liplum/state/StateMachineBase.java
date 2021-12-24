package net.liplum.state;

import net.liplum.attribute.IUpdate;

import java.util.LinkedList;

/**
 * @param <Owner>     此状态机的拥有者
 * @param <StateType> 此状态机存储 状态类型
 * @author 普冷姆plum
 * @version 2.0 - 使用 状态栈 状态机版本
 */
public abstract class StateMachineBase<Owner, StateType extends StateBase<?>> implements IUpdate {

    /**
     * 此状态机的拥有者
     */
    protected Owner owner;

//	/**
//	 * 当前处于的状态
//	 */
//	protected volatile StateType curState;
//	/**
//	 * 上一次处于的状态
//	 */
//	protected volatile StateType preState;

    protected LinkedList<StateType> stateStack = new LinkedList<>();

    /**
     * @param o 此状态机的拥有者
     */
    public StateMachineBase(Owner o) {
        this.owner = o;
    }

    /**
     * <pre>
     *
     * @param newState 要添加的新状态 默认会 调用 新状态 的 {@linkplain StateBase#enter() 进入方法}
     *                 此方法会 状态机 的末尾 添加一个 新状态 但不会改变 状态机 中的 其他状态
     */
    public void addNewState(StateType newState) {
        addNewState(newState, true);
    }

    /**
     * <pre>
     *
     * @param newState 要添加的 新状态
     * @param canEnter 是否要 调用 新状态 的 {@linkplain StateBase#enter() 进入方法} 此方法会 状态机
     *                 的末尾 添加一个 新状态 但不会改变 状态机 中的 其他状态
     */
    public void addNewState(StateType newState, boolean canEnter) {
        stateStack.add(newState);
        if (canEnter)
            stateStack.getLast().enter();
    }

    /**
     * <pre>
     *
     * @param newState 要覆盖的新状态 默认会 调用 新状态 的 {@linkplain StateBase#enter()}  进入方法}
     *                 此方法会使用 新状态 覆盖 状态机 的 最后一个状态
     */
    public void replaceNewState(StateType newState) {
        replaceNewState(newState, true);
    }

    /**
     * <pre>
     *
     * @param newState 要覆盖的新状态
     * @param canEnter 是否要 调用 新状态 的 {@linkplain StateBase#enter() 进入方法} 此方法会使用 新状态
     *                 覆盖 状态机 的 最后一个状态
     */
    public void replaceNewState(StateType newState, boolean canEnter) {
        if (!stateStack.isEmpty()) {
//		退出当前状态
            stateStack.getLast().exit();

            stateStack.removeLast();
        }

        addNewState(newState, canEnter);

    }

    /**
     * <pre>
     * 使 状态机 回到上一个状态 默认会 调用 上一个 状态 的 {@linkplain StateBase#enter() 进入方法}
     */
    public void returnToPrevious() {
        returnToPrevious(true);
    }

    /**
     * <pre>
     * 使 状态机 回到上一个状态
     *
     * @param canEnterAgain 是否要重新调用 上一个状态 的 {@linkplain StateBase#enter() 进入方法}
     */
    public void returnToPrevious(boolean canEnterAgain) {
        if (!stateStack.isEmpty())
            replaceNewState(stateStack.getLast(), canEnterAgain);
    }

    /**
     * 清空状态机
     */
    public void clear() {
        for (StateType state : stateStack)
            state.exit();
        stateStack.clear();
    }

    /**
     * 移除状态机最底部的 状态
     */
    public void removeBottom() {
        stateStack.getFirst().exit();
        stateStack.removeFirst();
    }

    /**
     * 移除状态机最顶部的 状态
     */
    public void removeTop() {
        stateStack.getLast().exit();
        stateStack.removeLast();
    }

    /**
     * @return 此状态机的拥有者
     */
    public Owner getOwner() {
        return owner;
    }
}
