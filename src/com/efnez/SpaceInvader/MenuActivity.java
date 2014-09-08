package com.efnez.SpaceInvader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
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

    public void showMenu() {

        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.green_triangle);
        intent = new Intent(this, SpaceActivity.class);

        button = (Button) findViewById(R.id.startBattle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(intent);
            }
        });

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

    public MenuView(Context context, int X, int Y) {
        super(context);
        greenTriangle = new GreenTriangleShip(X / 2, Y / 2);
        intent.putExtra("x", X);
        intent.putExtra("y", Y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(greenTriangle.drawTriangle(), greenTriangle.trianglePainter);
        if (greenTriangle.y > MenuActivity.Y - MenuActivity.Y / 3){
            startBattle();
        }
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
