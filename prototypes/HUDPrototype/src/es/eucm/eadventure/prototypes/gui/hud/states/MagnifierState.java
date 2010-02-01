package es.eucm.eadventure.prototypes.gui.hud.states;

import android.graphics.Canvas;
import android.view.MotionEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.PressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;
import es.eucm.eadventure.prototypes.gui.hud.HUD;
import es.eucm.eadventure.prototypes.gui.hud.HUDstate;
import es.eucm.eadventure.prototypes.gui.hud.elements.Magnifier;

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
