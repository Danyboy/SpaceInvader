package com.efnez.SpaceInvader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MenuActivity extends Activity {
    /**
     * Called when the activity is first created.
     */

    private Button button;
    private ImageView image;
    private Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        showMenu();
    }


    public void showMenu() {
        image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.space_invader);
        intent = new Intent(this, SpaceActivity.class);

        button = (Button) findViewById(R.id.startBattle);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                startActivity(intent);
            }
        });

    }

}
