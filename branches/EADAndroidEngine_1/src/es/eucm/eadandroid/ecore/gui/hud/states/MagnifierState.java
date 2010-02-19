package es.eucm.eadandroid.ecore.gui.hud.states;

import android.graphics.Canvas;
import android.view.MotionEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Magnifier;

public class MagnifierState extends HUDstate{
	

	Magnifier magnifier;
	
	public MagnifierState(HUD stateContext , Magnifier mag) {
		super(stateContext);
		magnifier = mag;
	}
	
	@Override
	public void doDraw(Canvas c) {
		magnifier.doDraw(c);
	}
	
	@Override
	public boolean processPressed(UIEvent e) {	
		
		MotionEvent m = ((PressedEvent) e).event;
		
		magnifier.show();
		magnifier.updateMagPos((int)m.getX(),(int)m.getY());
		return true;
	}

	@Override
	public boolean processScrollPressed(UIEvent e) {
		
		MotionEvent m = ((ScrollPressedEvent) e).eventDst;
		
		magnifier.show();
		magnifier.updateMagPos((int)m.getX(),(int)m.getY());
		return true;
	}
	
	public boolean processUnPressed(UIEvent e) {
		magnifier.hide();
		stateContext.setState(HUDstate.HiddenState);
		return true;
	}

}
