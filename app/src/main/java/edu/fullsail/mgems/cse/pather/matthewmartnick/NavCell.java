package edu.fullsail.mgems.cse.pather.matthewmartnick;

import android.graphics.Point;
import android.graphics.Rect;

import java.util.ArrayList;

public class NavCell {

    public static boolean mPassable = true;
    // Setup
//    private Rect mBounds;
//    private Point mCentroid;
//    private NavCell[] mNeighbors;
//
//    // Pathing
//    private float mCost;
//    private float mCostFinal;
//    private NavCell mPrevious;
//
//    private int GridRow;
//    private int GridColumn;
//

    public int centroidX;
    public int centroidY;

    public NavCell(int A, int B) {
        this.centroidX = A;
        this.centroidY = B;
    }

    public NavCell() {

    }


    public static void setImpassable() {
        mPassable = false;
        // System.out.println("setImpassable called");
    }


    public static boolean isPassable() {
        //System.out.println("isImpassable called");
        //mPassable = true;
        return mPassable;
    }

    public void setNeighbor(int i, NavCell navCell) {
        //System.out.println("setNeighbor called");
    }

    public static float heuristic(NavCell from, NavCell to) {

//        Point p1 = from;
//        Point p2 = to.getCentroid();
        float dx = (to.centroidX - from.centroidX) * (to.centroidX - from.centroidX);
        float dy = (to.centroidY - from.centroidY) * (to.centroidY - from.centroidY);
        System.out.println("distance to cover x  " + dx);
        return (float) Math.sqrt(dx + dy);
    }



/*

    public void update(float cost, float costFinal, NavCell prev) {
        mCost = cost;
        mCostFinal = costFinal;
        mPrevious = prev;
    }

    public void reset() {
        mCost = mCostFinal = Float.MAX_VALUE;
        mPrevious = null;
    }

    public static Comparator<NavCell> NavCellByFinalCostComparator = new Comparator<NavCell>() {
        @Override
        public int compare(NavCell x, NavCell y) {

            if (x.mCostFinal < y.mCostFinal) {
                return -1;
            }
            if (x.mCostFinal > y.mCostFinal) {
                return 1;
            }
            return 0;
        }
    };

*/


}
