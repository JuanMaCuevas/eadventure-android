package es.eucm.eadandroid.ecore.gui.hud.states;


import android.graphics.Canvas;
import android.util.Log;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.FlingEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.ActionsPanel;


public class ActionsState extends HUDstate {

	
	ActionsPanel actionsPanel;
	
	public ActionsState(HUD stateContext) {
		super(stateContext);
		actionsPanel = new ActionsPanel();

	}
	
	@Override
	public void doDraw(Canvas c) {
		actionsPanel.doDraw(c);
	}
	

	@Override
	public boolean processScrollPressed(UIEvent e) {
		
		
			
		ScrollPressedEvent ev = (ScrollPressedEvent) e;
		
		int dstX = (int)ev.eventDst.getX();
		int dstY = (int)ev.eventDst.getY();
		
		int distanceX = -(int)ev.distanceX;
		
		 if (actionsPanel.pointInGrid(dstX,dstY))
			 actionsPanel.updateDraggingGrid(distanceX);
					
		return true;
	}
	
	@Override
	public boolean processFling(UIEvent e) {

		FlingEvent ev = (FlingEvent) e;
		
		int srcX = (int)ev.eventSrc.getX();
		int srcY = (int)ev.eventSrc.getY();
		
		Log.w("SWIPE",String.valueOf( ev.velocityX));
			
		if (actionsPanel.pointInGrid(srcX,srcY))
			actionsPanel.gridSwipe(ev.eventDst.getEventTime(),(int) ev.velocityX);
		
		return true;
	}

	@Override
	public boolean processUnPressed(UIEvent e) {
		
		return true;
	}

}
