package com.example.wlf.wimpossible;

import android.content.Context;
import android.graphics.BitmapFactory;
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

    private int playerY = 300,  playerX = 300, playerRadius = 50;
    private float enemyX, enemyY, enemyRadius = 50;
    private double distance;
    private boolean gameover;
    private int score;

    public Impossible( Context context )
    {
        super( context );
        paint = new Paint();
        holder = getHolder();
    }

    private void drawPlayer(Canvas canvas)
    {
        paint.setColor( Color.GREEN );
        //canvas.drawCircle( playerX, playerY, 50, paint );
        canvas.drawBitmap( BitmapFactory.decodeResource( getResources(),
                R.drawable.nave ), playerX-50, playerY-50, null );
    }

    private void drawEnemy( Canvas canvas )
    {
        paint.setColor( Color.RED );
        enemyRadius++;

        canvas.drawCircle( enemyX, enemyY, enemyRadius, paint );
    }

    public void resume()
    {
        running = true;
        renderThread = new Thread( this );
        renderThread.start();
    }

    private void checkCollision( Canvas canvas )
    {
        //calcula a hipotenusa

        distance = Math.pow( playerY - enemyY, 2 ) + Math.pow( playerX - enemyX, 2 );
        distance = Math.sqrt( distance );

        //verifica distância entre os raios
        if ( distance <= playerRadius + enemyRadius )
        {
            gameover = true;
        }
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
            //canvas.drawColor( Color.BLACK );
            canvas.drawBitmap( BitmapFactory.decodeResource( getResources(), R.drawable.sky ),
                    0, 9, null );

            //desenha o player e o inimigo
            drawPlayer( canvas );
            drawEnemy( canvas );

            //detecta a colisao
            checkCollision( canvas );

            //game over
            if ( gameover )
            {
                stopGame( canvas );
                break;
            }

            //atualiza o placar
            drawScore( canvas );

            //Restart e Exit
            drawButtons( canvas );

            //atualiza e libera o canvas
            holder.unlockCanvasAndPost( canvas );
        }
    }

    public void moveDown( int pixels )
    {
        playerY += pixels;
    }

    private void stopGame( Canvas canvas )
    {
        paint.setStyle( Paint.Style.FILL );
        paint.setColor( Color.LTGRAY );
        paint.setTextSize( 100 );
        canvas.drawText(  "Game Over!",  50, 150, paint );
    }

    public void addScore( int points )
    {
        score += points;
    }

    public void drawScore( Canvas canvas )
    {
        paint.setStyle( Paint.Style.FILL );
        paint.setColor( Color.WHITE );
        paint.setTextSize( 50 );
        canvas.drawText( String.valueOf( score ), 50 , 200, paint);
    }

    public void drawButtons ( Canvas canvas )
    {
        //Restart
        paint.setStyle( Paint.Style.FILL );
        paint.setColor( Color.WHITE );
        paint.setTextSize( 30 );
        canvas.drawText( "Restart", 50, 300, paint );

        //Exit
        paint.setStyle( Paint.Style.FILL );
        paint.setColor( Color.WHITE );
        paint.setTextSize( 30 );
        canvas.drawText( "Exit", 50, 500, paint );
    }

    public void init()
    {
        enemyX = enemyY = enemyRadius =0;
        playerX = playerY = 300;
        playerRadius = 50;
        gameover = false;
    }

}
