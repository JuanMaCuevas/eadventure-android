package es.eucm.eadandroid.ecore.gui.hud.states;

import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.ContextServices;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalScene;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.LongPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.TapEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Inventory;
import es.eucm.eadandroid.ecore.gui.hud.elements.Wave;

public class HiddenState extends HUDstate {
	
	Wave wave;
	
	public HiddenState(HUD stateContext, Wave wave) {
		super(stateContext);
		this.wave = wave;
	}
	
	@Override
	public void doDraw(Canvas c) {
		wave.doDraw(c);
	}
	
    @Override
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
	
	@Override
	public boolean processTap(UIEvent e){
		

		TapEvent ev = (TapEvent) e;
		int srcX = (int) ev.event.getX();
		int srcY = (int) ev.event.getY();
		
		wave.updatePosition(srcX,srcY);

		return false;
	}
	
	@Override
	public boolean processUnPressed(UIEvent e){
		return false;
	}
	@Override
	public boolean processFling(UIEvent e){
		return false;
	}
	@Override
	public boolean processOnDown(UIEvent e) {		
		return false;
	}	
	
	@Override
	public boolean processLongPress(UIEvent e) {		
		
		MotionEvent ev = ((LongPressedEvent)e).event;
		int srcX = (int) ev.getX();
		int srcY = (int) ev.getY();
		
		FunctionalScene functionalScene = Game.getInstance().getFunctionalScene( );
        if( functionalScene != null ) {
        	FunctionalElement elementInside = functionalScene.getElementInside( (int)((srcX - GUI.CENTER_OFFSET) / GUI.SCALE_RATIOX), (int)(srcY / GUI.SCALE_RATIOY), null );
        	
        	if (elementInside != null && elementInside.canBeDragged()){
        		
        		this.foundedVibration();
        		Game.getInstance().getActionManager().dragging(elementInside);
        		stateContext.setState(HUDstate.DraggingState,null);
        		return false;
        	}
        }
		
		return false;
	}	
	
	private void foundedVibration() {
		if (Game.getInstance().getOptions().isVibrationActive()){
			// Get instance of Vibrator from current Context
			Vibrator v = ContextServices.getInstance().getServiceVibrator(); 
			// Vibrate for 300 milliseconds
			v.vibrate(40);
		}

	}

}
