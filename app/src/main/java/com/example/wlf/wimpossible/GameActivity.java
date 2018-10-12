package com.example.wlf.wimpossible;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class GameActivity extends AppCompatActivity implements View.OnTouchListener {

    Impossible view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.activity_game);

        //Logical
        view = new Impossible( this );

        view.setOnTouchListener( this );

        //View Setup
        setContentView( view );

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        view.resume();
    }

    @Override
    public boolean onTouch( View v, MotionEvent motionEvent )
    {
        if ( motionEvent.getX() < 100 && motionEvent.getY() > 290 && motionEvent.getY() < 310 )
        {
            view.init();
        }

        //Exit
        if ( motionEvent.getX() < 100 && motionEvent.getY() > 490 && motionEvent.getY() < 510 )
        {
            System.exit( 0 );
        }

        // Incrementa em 10 pixel a posição
        // Vertical do player e o placar
        view.moveDown( 10 );
        view.addScore( 100 );

        return true;
    }
}
