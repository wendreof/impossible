package com.example.wlf.wimpossible;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Impossible extends SurfaceView implements Runnable {

    boolean running = false;
    Thread renderThread = null;
    SurfaceHolder holder;
    Paint paint;

    public Impossible( Context context )
    {
        super( context );
        paint = new Paint();
        holder = getHolder();
    }

    private void drawPlayer(Canvas canvas)
    {
        paint.setColor(Color.GREEN);
        canvas.drawCircle(100, 100, 100, paint);
    }

    public void resume()
    {
        running = true;
        renderThread = new Thread(this);
        renderThread.start();
    }

    @Override
    public void run()
    {
        while ( running )
        {
            //verifica se a tela já está pronta
            if ( !holder.getSurface().isValid() )

                continue;

            //bloqueia o canvas
            Canvas canvas = holder.lockCanvas();

            //desenha o player
            drawPlayer( canvas );

            //atualiza e libera o canvas
            holder.unlockCanvasAndPost( canvas );
        }
    }


}
