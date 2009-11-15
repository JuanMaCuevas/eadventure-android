package com.juego;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

public class VistaJuego1 extends View {

	// Cuantos píxels nos moveremos a cualquier dirección cuando se pulse cada tecla
    private static int PASO_MOVIMIENTO = 5;

    // Dimensiones de la pantalla del dispositivo
    private int anchoPantalla, altoPantalla;
    
    // Posición de la imagen que moveremos por la pantalla
    private int posX, posY;
    
    // Imagen que dibujaremos en la pantalla
    private Drawable nube; 

    // Dimensiones de la imagen que dibujaremos
    private int anchoNube, altoNube; 



	public VistaJuego1(Context context, AttributeSet attrs) {
		super(context, attrs);

		nube = context.getResources().getDrawable(R.drawable.nube);
        anchoNube = nube.getIntrinsicWidth();   // Obtenemos el ancho de la nube
        altoNube = nube.getIntrinsicHeight();    // Obtenemos el alto de la nube

        setFocusable(true);   // Para asegurarnos que recibimos las pulsaciones de las teclas

        //setBackground(R.drawable.fondo);


	}



	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		nube.setBounds(posX, posY, posX+anchoNube, posY+altoNube);
        nube.draw(canvas);

        invalidate(posX-PASO_MOVIMIENTO, posY-PASO_MOVIMIENTO, posX+anchoNube+PASO_MOVIMIENTO, posY+altoNube+PASO_MOVIMIENTO); 


	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		// Suponemos que vamos a procesar la pulsación
        boolean procesada = true;
        
        switch(keyCode){
        case KeyEvent.KEYCODE_DPAD_UP:
            posY-=PASO_MOVIMIENTO;
            break;
        case KeyEvent.KEYCODE_DPAD_DOWN:
            posY+=PASO_MOVIMIENTO;
            break;
        case KeyEvent.KEYCODE_DPAD_LEFT:
            posX-=PASO_MOVIMIENTO;
            break;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            posX+=PASO_MOVIMIENTO;
            break;
        default:
            // Si hemos llegado aquí, significa que no hay pulsación que nos interese
            procesada = false;
            break;
        }
        
        // Si nos salimos de la pantalla, corregimos la posición
        // para que no aparezca fuera.
        if(posY<0)
            posY=0;
        if(posX<0)
            posX=0;
        if(posY+altoNube>altoPantalla)
            posY=altoPantalla-altoNube;
        if(posX+anchoNube>anchoPantalla)
            posX=anchoPantalla-anchoNube;
        
        return procesada;


	}



	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		// Una vez que conocemos nuestro ancho y alto.
        anchoPantalla = w;
        altoPantalla = h;
        posX = (anchoPantalla / 2)-(anchoNube/2);
        posY = (altoPantalla / 2)-(altoNube/2);

        
	}

}
