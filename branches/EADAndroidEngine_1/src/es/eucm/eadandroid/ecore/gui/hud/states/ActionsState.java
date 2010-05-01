package es.eucm.eadandroid.ecore.gui.hud.states;


import android.graphics.Canvas;
import android.util.Log;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.FlingEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.TapEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.ActionButton;
import es.eucm.eadandroid.ecore.gui.hud.elements.ActionsPanel;


public class ActionsState extends HUDstate {

	
	ActionsPanel actionsPanel;
	
	public ActionsState(HUD stateContext) {
		super(stateContext);
		actionsPanel = new ActionsPanel();

	}
	
	public void setItem(Object item) {
		actionsPanel.setElementInfo((FunctionalElement)item);	
	}
	
	@Override
	public void update(long elapsedTime) {
		actionsPanel.update(elapsedTime);
	}
	
	@Override
	public void doDraw(Canvas c) {
		actionsPanel.doDraw(c);
	}
	
	public boolean processTap(UIEvent e) {
		TapEvent ev = (TapEvent) e;
		
		int srcX = (int)ev.event.getX();
		int srcY = (int)ev.event.getY();
		
		ActionButton ab = (ActionButton)actionsPanel.selectItemFromGrid(srcX, srcY);
		
		if (ab != null ) {
			Game.getInstance().getActionManager().processAction(ab,actionsPanel.getElementInfo());
			stateContext.setState(HUDstate.HiddenState,null);
		}
		else if (actionsPanel.isInCloseButton(srcX,srcY))
			stateContext.setState(HUDstate.HiddenState,null);
		
		return true;
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
			
		if (actionsPanel.pointInGrid(srcX,srcY)) {
			Log.w("SWIPE","Siiii");
			actionsPanel.gridSwipe(ev.eventDst.getEventTime(),(int) ev.velocityX);
		}
		
		return true;
	}

	@Override
	public boolean processUnPressed(UIEvent e) {
				
		return true;
		
	}


}
