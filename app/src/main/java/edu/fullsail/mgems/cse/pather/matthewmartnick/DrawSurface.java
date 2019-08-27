package edu.fullsail.mgems.cse.pather.matthewmartnick;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;


public class DrawSurface extends SurfaceView implements View.OnTouchListener {


    Bitmap mBMPStart;
    private SurfaceHolder surfaceHolder;
    private Bitmap mBMPEnd;

    private Bitmap mBMPShakey;
    Paint paint = new Paint();
    Canvas c;

    NavCell[][] mCells;
    ArrayList<NavCell[][]> allCells = new ArrayList<>();
    ArrayList<Integer> indexCell = new ArrayList<>();
    NavCell from;

    public int mCellCols;
    public int mCellRows;


    public static int CELL_SIZE = 64;


    public DrawSurface(Context context) {
        super(context);
        init();
    }

    public DrawSurface(Context context,
                       AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawSurface(Context context,
                       AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }


    // Updated init code
    public void init() {
        surfaceHolder = getHolder();

        mBMPEnd = BitmapFactory.decodeResource(getResources(), R.drawable.end);
        mBMPStart = BitmapFactory.decodeResource(getResources(), R.drawable.start);
        mBMPShakey = BitmapFactory.decodeResource(getResources(), R.drawable.shakey);

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {


            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                Canvas c = holder.lockCanvas();

                Rect mFieldDim = new Rect();
                if (c != null) {
                    mFieldDim.set(0, 0, c.getWidth(), c.getHeight());
                    paint.setColor(Color.WHITE);
                    paint.setStrokeWidth(10);
                    paint.setStyle(Paint.Style.STROKE);
                    c.drawRect(mFieldDim, paint);
                }
                mCellCols = (int) Math.ceil((float) mFieldDim.width() / CELL_SIZE);
                mCellRows = (int) Math.ceil((float) mFieldDim.height() / CELL_SIZE);
                invalidate();


                // creates cells
                mCells = new NavCell[mCellRows][mCellCols];
                //System.out.println( "Rows " + mCellRows + " and Cols: " + mCellCols);
                for (int j = 0; j < mCellRows; j++) {

                    for (int i = 0; i < mCellCols; i++) {

                        mCells[j][i] = new NavCell();

                        int left = i * CELL_SIZE;
                        int top = j * CELL_SIZE;
                        int right = (i * CELL_SIZE) + CELL_SIZE;
                        int bottom = (j * CELL_SIZE) + CELL_SIZE;

                        paint.setColor(Color.RED);
                        paint.setStrokeWidth(3);
                        paint.setStyle(Paint.Style.STROKE);

                        c.drawRect(left, top, right, bottom, paint);
                        invalidate();
//                        allCells.add(mCells);
//                        System.out.println("Hello Matt" + allCells.size());
//                        indexCell.add(allCells.size());
//                        System.out.println("Hi Matt" + indexCell);

                    }
                }


                // Set blockers

                int midRow = mCellRows / 2;
                for (int j = 0; j < mCellRows; j++) {
                    for (int i = 0; i < mCellCols; i++) {
                        if (j == midRow && i > 0 && i < mCellCols - 2) {


                            int left = i * CELL_SIZE;
                            int top = j * CELL_SIZE;
                            int right = (i * CELL_SIZE) + CELL_SIZE;
                            int bottom = (j * CELL_SIZE) + CELL_SIZE;


                            c.drawRect(left, top, right, bottom, paint);
                            mCells[j][i].setImpassable();
                            noPassColor();
                            invalidate();
                        }
                    }
                }


                for (int in = 0; in < 10; in++) {
                    int k = new Random().nextInt(9 - 1) + 1;
                    int l = new Random().nextInt(15 - 1) + 1;

                    int left = k * CELL_SIZE;
                    int top = l * CELL_SIZE;
                    int right = (k * CELL_SIZE) + CELL_SIZE;
                    int bottom = (l * CELL_SIZE) + CELL_SIZE;


                    c.drawRect(left, top, right, bottom, paint);
                    mCells[l][k].setImpassable();
                    noPassColor();

                    invalidate();
                }

                // Start point
                int ranX = new Random().nextInt(mCellRows - 1) + 1;
                int ranY = new Random().nextInt(mCellCols - 1) + 1;

                int startY = (ranY * CELL_SIZE) - CELL_SIZE / 2;
                int startX = (ranX * CELL_SIZE) - CELL_SIZE / 2;
                from = new NavCell(startX, startY);

                System.out.println("From X = " + from.centroidX);
                drawStart(c, startX, startY);


//                // Connect cells
//                for (int j = 0; j < mCellRows; j++) {
//                    for (int i = 0; i < mCellCols; i++) {
//                        if (i > 0 && mCells[j][i - 1].isPassable())
//                            mCells[j][i].setNeighbor(0, mCells[j][i - 1]);
//
//                        if (j > 0 && mCells[j - 1][i].isPassable())
//                            mCells[j][i].setNeighbor(1, mCells[j - 1][i]);
//
//                        if (i < mCellCols - 1 && mCells[j][i + 1].isPassable())
//                            mCells[j][i].setNeighbor(2, mCells[j][i + 1]);
//
//                        if (j < mCellRows - 1 && mCells[j + 1][i].isPassable())
//                            mCells[j][i].setNeighbor(1, mCells[j + 1][i]);
//
//                    }
//
//                }

                //    System.out.println("thank god at least I am here");

                invalidate();
                holder.unlockCanvasAndPost(c);


            }


            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }
        });


        findViewById(R.id.dsField);
        setOnTouchListener(this);

    }


    public void noPassColor() {
        if (NavCell.mPassable == false) {
            paint.setColor(Color.RED);
            paint.setStrokeWidth(3);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
    }

    protected void drawStart(Canvas canvas, float X, float Y) {
        canvas.drawBitmap(mBMPStart, X, Y, null);
    }

    protected void drawEnd(Canvas canvas, float X, float Y) {
        canvas.drawBitmap(mBMPEnd, X, Y, null);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {


            float X = event.getX();
            float Y = event.getY();

            int touchX = (int) Y;
            int touchY = (int) X;

            int endRow = touchX / 64;
            int endCol = touchY / 64;

            int endX = (endRow * CELL_SIZE) + CELL_SIZE / 2;
            int endY = (endCol * CELL_SIZE) + CELL_SIZE / 2;


            NavCell to = new NavCell(endX, endY);


            NavCell.heuristic(from, to);
            System.out.println("To X = " + to.centroidX);


//                if (surfaceHolder.getSurface().isValid()) {
//                    Canvas canvas = surfaceHolder.lockCanvas();
//
//                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
//                    drawEnd(canvas, X, Y);
//
//                    surfaceHolder.unlockCanvasAndPost(canvas);
//                    invalidate();
//
//                }
        }

        setWillNotDraw(false);
        return true;


    }
}