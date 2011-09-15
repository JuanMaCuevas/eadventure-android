package es.eucm.eadandroid.ecore.gui.hud.states;

import android.graphics.Canvas;
import android.util.Log;
import es.eucm.eadandroid.ecore.control.Game;
import es.eucm.eadandroid.ecore.control.functionaldata.FunctionalElement;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.FlingEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.OnDownEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.TapEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UnPressedEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Inventory;

public class InventoryState extends HUDstate {

	private Inventory inventory;
	
	public InventoryState(HUD stateContext, Inventory inventory) {
		super(stateContext);
		this.inventory = inventory;
	}

	@Override
	public void doDraw(Canvas c) {
		inventory.doDraw(c);
	}

	@Override
	public void update(long elapsedTime) {
		inventory.update(elapsedTime);
	}
	
	@Override
	public boolean processTap(UIEvent e) {

		TapEvent ev = (TapEvent) e;
		int dstX = (int) ev.event.getX();
		int dstY = (int) ev.event.getY();

		FunctionalElement fe = null;

		if (inventory.pointInGrid(dstX, dstY)) {
			fe = (FunctionalElement) inventory.selectItemFromGrid(
					(int) ev.event.getX(), (int) ev.event.getY());

			FunctionalElement elementInCursor = Game.getInstance()
					.getActionManager().getElementInCursor();

			if (fe != null && elementInCursor == null)
				stateContext.setState(HUDstate.ActionsState, fe);
			else if (elementInCursor!=null) {
				Game.getInstance().getActionManager().setElementOver(fe);
				stateContext.setState(HUDstate.HiddenState, null);
				return false;
			}
		}
		
		return true;
	}

	@Override
	public boolean processPressed(UIEvent e) {

		PressedEvent ev = (PressedEvent) e;
		
		// lo bueno de hacer esto aqui es que si lo hgo dentro de inventory , si
		// hay mas tipos de eventos se va a enguarrinar
		if (ev.event.getY() > Inventory.FOLD_REGION_POSY)
			inventory.updateDraggingPos((int) ((PressedEvent) e).event.getY());
		return true;
	}

	@Override
	public boolean processScrollPressed(UIEvent e) {

		ScrollPressedEvent ev = (ScrollPressedEvent) e;

		int srcY = (int) ev.eventSrc.getY();
		int srcX = (int) ev.eventSrc.getX();
		int dstX = (int) ev.eventDst.getX();
		int dstY = (int) ev.eventDst.getY();

		int distanceX = -(int) ev.distanceX;

		if (srcY > Inventory.FOLD_REGION_POSY)
			inventory.updateDraggingPos(dstY);
		else if (inventory.pointInGrid(dstX, dstY)) {
			inventory.setItemFocus(dstX, dstY);
			inventory.updateDraggingGrid(distanceX);
		} else
			inventory.resetItemFocus();

		return true;
	}
	
	@Override
	public boolean processFling(UIEvent e) {

		FlingEvent ev = (FlingEvent) e;
		
		int srcX = (int)ev.eventSrc.getX();
		int srcY = (int)ev.eventSrc.getY();
		
		Log.w("SWIPE",String.valueOf( ev.velocityX));
			
		if (inventory.pointInGrid(srcX,srcY))
			inventory.gridSwipe(ev.eventDst.getEventTime(),(int) ev.velocityX);
		
		return true;
	}

	@Override
	public boolean processUnPressed(UIEvent e) {
		
		if (inventory.isAnimating((int) ((UnPressedEvent) e).event.getY())) {
			UnPressedEvent ev = (UnPressedEvent) e;
			int dstX = (int) ev.event.getX();
			int dstY = (int) ev.event.getY();
			
			FunctionalElement fe = null;

			if (inventory.pointInGrid(dstX, dstY)) {
				fe = (FunctionalElement) inventory.selectItemFromGrid(
						(int) ev.event.getX(), (int) ev.event.getY());

				FunctionalElement elementInCursor = Game.getInstance()
						.getActionManager().getElementInCursor();

				if (fe != null && elementInCursor == null)
					stateContext.setState(HUDstate.ActionsState, fe);
				else if (elementInCursor!=null) {
					Game.getInstance().getActionManager().setElementOver(fe);
					stateContext.setState(HUDstate.HiddenState, null);
					return false;
				}
			}
		} else {
			inventory.resetPos();
			stateContext.setState(HUDstate.HiddenState,null);
		}
	


		return true;
	}
	
	@Override
	public boolean processOnDown(UIEvent e) {
		
		OnDownEvent ev = (OnDownEvent) e;
				
		int srcX = (int)ev.event.getX();
		int srcY = (int)ev.event.getY();
		
		inventory.setItemFocus(srcX, srcY);
			
		return true;
		
	}

}