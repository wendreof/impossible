package com.example.wlf.wimpossible;

import android.content.Context;
import android.view.SurfaceView;

public class Impossible extends SurfaceView implements Runnable {

    boolean running = false;
    Thread renderThread = null;

    public Impossible( Context context )
    {
        super( context );
    }


    @Override
    public void run()
    {
        while ( running )
        {
            System.out.println( "Impossible Running!" );
        }
    }

    public void resume()
    {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }
}
