package es.eucm.eadandroid.ecore.gui.hud;

import android.graphics.Canvas;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;

public class HUDstate {
	
	public static final int ActionsState = 0;
	public static final int HiddenState = 1;
	public static final int InventoryState = 2;
	public static final int MagnifierState = 3;
	public static final int ShowingInventoryState = 4;
	public static final int DraggingState = 5;
	
	
	protected HUD stateContext ;
	
	public HUDstate(HUD stateContext) {
		this.stateContext = stateContext;
	}
	
	public void doDraw(Canvas c) {
	}
	
    public void update( long elapsedTime ) {   	
    }
	
	public boolean processTap(UIEvent e){
		return true;
	}
	public boolean processPressed(UIEvent e){
		return true;
	}
	public boolean processUnPressed(UIEvent e){
		return true;
	}
	public boolean processFling(UIEvent e){
		return true;
	}
	public boolean processScrollPressed(UIEvent e){
		return true;
	}
	public boolean processOnDown(UIEvent e) {
		return true;
	}
	public boolean processLongPress(UIEvent e) {
		return true;
	}
	

}
