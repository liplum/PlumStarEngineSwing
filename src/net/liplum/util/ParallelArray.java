package net.liplum.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <First>  第一个参数
 * @param <Second> 第二个参数<br>
 *                 <font color=red>等位元素</font>：它是指在 两个不同的列表 中 满足互有单一映射关系 的两个元素 之一 。例如：在两个不同的数组中 具有相同索引 的 两个元素之一。<br>
 *                 <font color=red>AllelicElement</font>:It refers to one of two elements in two different lists that satisfy a single mapping relationship with each other.
 *                 For example, one of two elements with the same index in two different arrays.
 * @author 普冷姆plum
 */
public class ParallelArray<First, Second> implements Parallel<First, Second> {

    private final List<First> first;

    private final List<Second> second;
    private volatile int size;

    public ParallelArray(int size) {
        first = new ArrayList<>(size);
        second = new ArrayList<>(size);
        updateSize();
    }

    public ParallelArray() {
        first = new ArrayList<>();
        second = new ArrayList<>();
        updateSize();
    }

    private void updateSize() {
        if (first.size() == second.size())
            this.size = first.size();
        else
            throw new RuntimeException("ParallelArray is inconsistent.");
    }

    private boolean checkIndex(int index) {
        return index < size;
    }

    public synchronized void setFirstAt(int index, First f) {
        if (checkIndex(index))
            first.set(index, f);
    }

    public synchronized void setSecondAt(int index, Second s) {
        if (checkIndex(index))
            second.set(index, s);
    }

    public synchronized void setElementAt(int index, First f, Second s) {
        if (checkIndex(index)) {
            first.set(index, f);
            second.set(index, s);
        }
    }

    /**
     * 注意：此方法会为 另一个 等位元素 设为 null<br>
     * Note:this method will set "null" for another AllelicElement
     */
    public synchronized void addFirst(First e) {
        first.add(e);
        second.add(null);
        updateSize();
    }

    /**
     * 注意：此方法会为 另一个 等位元素 设为 null<br>
     * Note:this method will set "null" for another AllelicElement
     */
    public synchronized void addSecond(Second e) {
        first.add(null);
        second.add(e);
        updateSize();
    }


    /**
     * 注意：此方法会同时添加 一对 等位元素<br>
     * Note:this method will add a pair of AllelicElements
     */
    public synchronized void add(First f, Second s) {
        first.add(f);
        second.add(s);
        updateSize();
    }


    public synchronized First getFirstAt(int index) {
        if (checkIndex(index))
            return first.get(index);
        else
            throw new IndexOutOfBoundsException("超出ParallelArray的first数组下标了。");
    }

    public synchronized Second getSecondAt(int index) {
        if (checkIndex(index))
            return second.get(index);
        else
            throw new IndexOutOfBoundsException("超出ParallelArray的second数组下标了。");
    }

    public synchronized void remove(int index) {
        first.remove(index);
        second.remove(index);
        updateSize();
    }

    public synchronized void clear() {
        first.clear();
        second.clear();
        updateSize();
    }

    public synchronized int size() {
        updateSize();
        return size;
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            str.append("<").append(i).append(">");

            if (first.get(i) != null)
                str.append(first.get(i).toString());
            else
                str.append("empty");

            str.append(":");


            if (second.get(i) != null)
                str.append(second.get(i).toString());
            else
                str.append("empty");

            str.append(";");
        }
        str.append("]");
        return str.toString();
    }
}


