package com.efnez.SpaceInvader;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * Created with IntelliJ IDEA.
 * User: Dany
 */
public class SpaceActivity extends Activity {
    private ImageView invaderOne;
    private ImageView invaderThree;
    private ImageView invaderTwo;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space_activity);
        showInvaders();
    }

    public void showInvaders() {
        invaderOne = (ImageView) findViewById(R.id.imageView);
        invaderTwo = (ImageView) findViewById(R.id.imageView1);
        invaderThree = (ImageView) findViewById(R.id.imageView2);

        invaderOne.setImageResource(R.drawable.invader1);
        invaderTwo.setImageResource(R.drawable.invader2);
        invaderThree.setImageResource(R.drawable.invader3);

    }
}