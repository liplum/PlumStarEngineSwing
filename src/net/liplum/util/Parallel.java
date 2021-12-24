package net.liplum.util;

public interface Parallel<First, Second> {

    void setFirstAt(int index, First f);

    void setSecondAt(int index, Second s);

    void setElementAt(int index, First f, Second s);

    void addFirst(First e);

    void addSecond(Second e);

    void add(First f, Second s);

    First getFirstAt(int index);

    Second getSecondAt(int index);

    void remove(int index);

    void clear();

    int size();
}
