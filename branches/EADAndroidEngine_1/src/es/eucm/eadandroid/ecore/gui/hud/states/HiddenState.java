package es.eucm.eadandroid.ecore.gui.hud.states;

import android.graphics.Canvas;
import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.TapEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Inventory;
import es.eucm.eadandroid.ecore.gui.hud.elements.Wave;

public class HiddenState extends HUDstate{
	
	Wave wave;
	
	public HiddenState(HUD stateContext, Wave wave) {
		super(stateContext);
		this.wave = wave;
	}
	
	public void doDraw(Canvas c) {
		wave.doDraw(c);
	}
	
    public void update( long elapsedTime ) {   	
    	wave.update(elapsedTime);
    }
	
	@Override
	public boolean processPressed(UIEvent e) {	
		MotionEvent m = ((PressedEvent) e).event;
		process(m);
		return stateContext.processPressed(e);
	}

	@Override
	public boolean processScrollPressed(UIEvent e) {
		MotionEvent m = ((ScrollPressedEvent) e).eventSrc;
		process(m);
		return stateContext.processScrollPressed(e);
	}
	
	private void process(MotionEvent e) {
		
		wave.hide();
		
		if (e.getY() > Inventory.UNFOLD_REGION_POSY) 
			stateContext.setState(HUDstate.MagnifierState,null);
		else stateContext.setState(HUDstate.ShowingInventoryState,null);
	}
	
	public boolean processTap(UIEvent e){
		
		TapEvent ev = (TapEvent) e;
		int srcX = (int) ev.event.getX();
		int srcY = (int) ev.event.getY();
		
		wave.updatePosition(srcX,srcY);
		
		return false;
	}
	public boolean processUnPressed(UIEvent e){
		return false;
	}
	public boolean processFling(UIEvent e){
		return false;
	}
	public boolean processOnDown(UIEvent e) {
		return false;
	}
	
	

}
