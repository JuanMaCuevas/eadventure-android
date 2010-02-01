package es.eucm.eadventure.prototypes.gui.hud;

import android.graphics.Canvas;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;
import es.eucm.eadventure.prototypes.gui.GUI;
import es.eucm.eadventure.prototypes.gui.hud.elements.Inventory;
import es.eucm.eadventure.prototypes.gui.hud.elements.Magnifier;
import es.eucm.eadventure.prototypes.gui.hud.states.ActionsState;
import es.eucm.eadventure.prototypes.gui.hud.states.HiddenState;
import es.eucm.eadventure.prototypes.gui.hud.states.InventoryState;
import es.eucm.eadventure.prototypes.gui.hud.states.MagnifierState;
import es.eucm.eadventure.prototypes.gui.hud.states.ShowingInventoryState;

public class HUD {

	/**
	 * CURRENT STATE
	 */
	protected HUDstate currentState;

	/**
	 * STATES
	 */

	private HiddenState hiddenState;
	private MagnifierState magnifierState;
	private ShowingInventoryState showInventoryState;
	private InventoryState inventoryState;
	private ActionsState actionsState;
	
	/**
	 * ELEMENTS
	 */
	
	private Magnifier mag ;
	private Inventory inventory;
	
	

	public HUD() {

		initElements();
		
		hiddenState = new HiddenState(this);
		magnifierState = new MagnifierState(this,mag);
		showInventoryState = new ShowingInventoryState(this,inventory);
		inventoryState = new InventoryState(this,inventory);
		actionsState = new ActionsState(this);

		currentState = hiddenState;
	}

	private void initElements() {
		
		mag = new Magnifier(50,6,1.5f,GUI.getInstance().getBmpCpy());
		inventory = new Inventory();
		
	}
	
	public void update(long elapsedTime) {
		currentState.update(elapsedTime);
	}

	public void doDraw(Canvas c) {
		currentState.doDraw(c);
	}

	public boolean processTap(UIEvent e) {
		return currentState.processTap(e);
	}

	public boolean processPressed(UIEvent e) {
		return currentState.processPressed(e);
	}

	public boolean processUnPressed(UIEvent e) {
		return currentState.processUnPressed(e);
	}

	public boolean processFling(UIEvent e) {
		return currentState.processFling(e);
	}

	public boolean processScrollPressed(UIEvent e) {
		return currentState.processScrollPressed(e);
	}

	public void setState(int state) {

		switch (state) {

		case HUDstate.HiddenState:
			currentState = hiddenState;
			break;
		case HUDstate.MagnifierState:
			currentState = magnifierState;
			break;
		case HUDstate.ShowingInventoryState:
			currentState = showInventoryState;
			break;
		case HUDstate.InventoryState:
			currentState = inventoryState;
			break;
		case HUDstate.ActionsState:
			currentState = actionsState;
			break;

		}

	}

}
