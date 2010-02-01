package es.eucm.eadventure.prototypes.gui.hud;

import android.graphics.Canvas;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;

public class HUDstate {
	
	public static final int ActionsState = 0;
	public static final int HiddenState = 1;
	public static final int InventoryState = 2;
	public static final int MagnifierState = 3;
	public static final int ShowingInventoryState = 4;
	
	
	protected HUD stateContext ;
	
	public HUDstate(HUD stateContext) {
		this.stateContext = stateContext;
	}
	
	public void doDraw(Canvas c) {
	}
	
    public void update( long elapsedTime ) {   	
    }
	
	public boolean processTap(UIEvent e){
		return false;
	}
	public boolean processPressed(UIEvent e){
		return false;
	}
	public boolean processUnPressed(UIEvent e){
		return false;
	}
	public boolean processFling(UIEvent e){
		return false;
	}
	public boolean processScrollPressed(UIEvent e){
		return false;
	}
	
	

}
