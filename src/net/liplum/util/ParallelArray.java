package net.liplum.util;

import java.util.ArrayList;
import java.util.List;

/**
 * AllelicElement means the one of two elements in two different lists that has a single mapping relationship with each other.<br/>
 * For example, one of two elements with the same index in two different arrays.
 */
public class ParallelArray<First, Second> implements Parallel<First, Second> {

    private final List<First> firsts;

    private final List<Second> seconds;
    private volatile int size;

    public ParallelArray(int size) {
        firsts = new ArrayList<>(size);
        seconds = new ArrayList<>(size);
        updateSize();
    }

    public ParallelArray() {
        firsts = new ArrayList<>();
        seconds = new ArrayList<>();
        updateSize();
    }

    private void updateSize() {
        if (firsts.size() == seconds.size())
            this.size = firsts.size();
        else
            throw new RuntimeException("ParallelArray is inconsistent.");
    }

    private boolean checkIndex(int index) {
        return index < size;
    }

    public synchronized void setFirstAt(int index, First f) {
        if (checkIndex(index))
            firsts.set(index, f);
    }

    public synchronized void setSecondAt(int index, Second s) {
        if (checkIndex(index))
            seconds.set(index, s);
    }

    public synchronized void setElementAt(int index, First f, Second s) {
        if (checkIndex(index)) {
            firsts.set(index, f);
            seconds.set(index, s);
        }
    }

    /**
     * It will set another AllelicElement as null
     */
    public synchronized void addFirst(First e) {
        firsts.add(e);
        seconds.add(null);
        updateSize();
    }

    /**
     * It will set another AllelicElement as null
     */
    public synchronized void addSecond(Second e) {
        firsts.add(null);
        seconds.add(e);
        updateSize();
    }


    /**
     * It will add a pair of AllelicElements
     */
    public synchronized void add(First f, Second s) {
        firsts.add(f);
        seconds.add(s);
        updateSize();
    }

    public synchronized First getFirstAt(int index) {
        if (checkIndex(index))
            return firsts.get(index);
        else
            throw new IndexOutOfBoundsException(index + " is over than the length" + size + " of parallelArray");
    }

    public synchronized Second getSecondAt(int index) {
        if (checkIndex(index))
            return seconds.get(index);
        else
            throw new IndexOutOfBoundsException(index + " is over than the length" + size + " of parallelArray");
    }

    public synchronized void remove(int index) {
        firsts.remove(index);
        seconds.remove(index);
        updateSize();
    }

    public synchronized void clear() {
        firsts.clear();
        seconds.clear();
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

            if (firsts.get(i) != null)
                str.append(firsts.get(i).toString());
            else
                str.append("empty");

            str.append(":");


            if (seconds.get(i) != null)
                str.append(seconds.get(i).toString());
            else
                str.append("empty");

            str.append(";");
        }
        str.append("]");
        return str.toString();
    }
}


