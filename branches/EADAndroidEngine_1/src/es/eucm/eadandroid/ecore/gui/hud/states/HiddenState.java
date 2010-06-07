package es.eucm.eadandroid.ecore.gui.hud.states;

import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Inventory;

public class HiddenState extends HUDstate{
	
	
	public HiddenState(HUD stateContext) {
		super(stateContext);
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
		if (e.getY() > Inventory.UNFOLD_REGION_POSY) 
			stateContext.setState(HUDstate.MagnifierState,null);
		else stateContext.setState(HUDstate.ShowingInventoryState,null);
	}
	
	public boolean processTap(UIEvent e){
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
