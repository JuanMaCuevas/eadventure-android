package es.eucm.eadandroid.ecore.gui.hud.states;

import android.graphics.Canvas;
import android.view.MotionEvent;
import es.eucm.eadandroid.common.data.chapter.Exit;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalAtrezzo;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalScene;
import es.eucm.eadandroid.ecore.control.functionaldata.functionalactions.FunctionalExit;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Magnifier;

public class MagnifierState extends HUDstate {	

	Magnifier magnifier;
	
	private int srcX, srcY;
	
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
		
		srcX = (int) m.getX();
		srcY = (int) m.getY();
		
		magnifier.show();
		magnifier.updateMagPos(srcX, srcY);
		
		return false;
	}

	@Override
	public boolean processScrollPressed(UIEvent e) {
		
		MotionEvent m = ((ScrollPressedEvent) e).eventDst;
		
		srcX = (int) m.getX();
		srcY = (int) m.getY();
		
		if (!magnifier.isShown())
			magnifier.show();
		magnifier.updateMagPos(srcX, srcY);		
				
		return false;
	}
	
	public boolean processUnPressed(UIEvent e) {
		
		magnifier.hide();
		
		FunctionalElement elementOver = Game.getInstance().getActionManager().getElementOver();
		FunctionalElement elementInCursor = Game.getInstance().getActionManager().getElementInCursor();
		
		if (elementOver!=null && elementInCursor==null)  {
			stateContext.setState(HUDstate.ActionsState, elementOver);
		}
		else stateContext.setState(HUDstate.HiddenState,null);
		
		return false;
	}
	
}
