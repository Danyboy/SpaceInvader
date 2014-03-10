package com.efnez.SpaceInvader;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created with IntelliJ IDEA.
 * User: Dany
 */
public class SpaceActivity extends Activity {
    private MyBitmapView myBitmapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        myBitmapView = new MyBitmapView(this);
        setContentView(myBitmapView);
    }

//    @Override
//    protected void onPause() {
//        // The following call pauses the rendering thread.
//        // If your OpenGL application is memory intensive,
//        // you should consider de-allocating objects that
//        // consume significant memory here.
//        super.onPause();
//        myBitmapView.onPause();
//    }
//
//    @Override
//    protected void onResume() {
//        // The following call resumes a paused rendering thread.
//        // If you de-allocated graphic objects for onPause()
//        // this is a good place to re-allocate them.
//        super.onResume();
//        myBitmapView.onResume();
//    }
}