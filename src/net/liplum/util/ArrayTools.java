package net.liplum.util;

import org.javatuples.Pair;

public class ArrayTools {

    public static class Array2DIterator {

        int maxRowCount, maxColumnCount;

        /**
         * @param maxRowCount    the length of 2D array's row.
         * @param maxColumnCount 2D array's the length of 2D array's column.
         */
        public Array2DIterator(int maxRowCount, int maxColumnCount) {
            this.maxRowCount = maxRowCount;
            this.maxColumnCount = maxColumnCount;
        }

        /**
         * @param curRow    2D array's index 1
         * @param curColumn 2D array's index 2
         * @return 1D array's index
         */
        public int getIndexAt2D(int curRow, int curColumn) {
            return curRow * maxColumnCount + curColumn;
        }

        /**
         * @param curIndex 1D array's index.
         * @return a Pair&lt;Row , Column>
         */
        public Pair<Integer, Integer> getPairAt1D(int curIndex) {
            return new Pair<Integer, Integer>(curIndex / maxColumnCount, curIndex % maxColumnCount);
        }


        public int getRowAt1D(int curIndex) {
            return curIndex / maxColumnCount;
        }

        public int getColumnAt1D(int curIndex) {
            return curIndex % maxColumnCount;
        }

    }
}
