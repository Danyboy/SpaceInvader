package com.efnez.SpaceInvader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity extends Activity {
    public static int Y ;
    public static int X ;
    /**
     * Called when the activity is first created.
     */

    private Button button;
    private ImageView image;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.menu_activity);
        getScreenDimension();

//        showMenu();

        setView();
    }

    private void getScreenDimension(){
        Display display = getWindowManager().getDefaultDisplay();
        X = display.getWidth();
        Y = display.getHeight();
    }

    private void setView(){
        MenuView menuView = new MenuView(this, X, Y);
        setContentView(menuView);
    }

    @Override
    protected void onResume() {
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        super.onResume();
        setView();
    }


}

class MenuView extends View{
    Triangle greenTriangle;
    Intent intent = new Intent(this.getContext(), SpaceActivity.class);
    Bitmap background1;

    public MenuView(Context context, int X, int Y) {
        super(context);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        background1 = BitmapFactory.decodeResource(getResources(), R.drawable.stars1);
        greenTriangle = new GreenTriangleShip(X / 2, Y - Y / 3);
        intent.putExtra("x", X);
        intent.putExtra("y", Y);
    }
    boolean runOnce = true;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(background1, -400, 0, greenTriangle.trianglePainter);

        canvas.drawPath(greenTriangle.drawTriangle(), greenTriangle.trianglePainter);
        canvas.drawPath(setGreenAim(MenuActivity.X / 2, MenuActivity.Y / 2), getPointer());
        if (MySpaceRender.getDistance(greenTriangle.x, greenTriangle.y, MenuActivity.X / 2, MenuActivity.Y / 2) < 20 && runOnce){
            startBattle();
            runOnce = false;
        }
    }

    private Paint getPointer() {
        Paint trianglePainter = new Paint();

        trianglePainter.setStrokeWidth(4);
        trianglePainter.setColor(Color.GREEN);
        trianglePainter.setStyle(Paint.Style.STROKE);
        trianglePainter.setAntiAlias(true);
        return trianglePainter;
    }

    private Path setGreenAim(int x, int y){
        Path triangle;
        float triangleLength = 120;
        Point a = new Point((int) (x - triangleLength / 2), (int) (y + triangleLength / 2));
        Point b = new Point((int) (x + triangleLength / 2), (int) (y + triangleLength / 2));
        Point c = new Point((int) x, (int) (y - triangleLength / 2));

        triangle = new Path();
        triangle.setFillType(Path.FillType.EVEN_ODD);
        triangle.moveTo(b.x, b.y);
        triangle.lineTo(b.x, b.y);
        triangle.lineTo(c.x, c.y);
        triangle.lineTo(a.x, a.y);
        triangle.lineTo(b.x, b.y);
        triangle.close();
        return triangle;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        greenTriangle.x = e.getX();
        greenTriangle.y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                invalidate();
        }
        return true;
    }

    private void startBattle(){
        intent.putExtra("x", greenTriangle.x);
        intent.putExtra("y", greenTriangle.y);
        this.getContext().startActivity(intent);
    }

}
