package es.eucm.eadandroid.ecore.gui.hud;

import android.graphics.Canvas;
import android.graphics.Color;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.gui.GUI;
import es.eucm.eadandroid.ecore.gui.hud.elements.Inventory;
import es.eucm.eadandroid.ecore.gui.hud.elements.Magnifier;
import es.eucm.eadandroid.ecore.gui.hud.elements.Wave;
import es.eucm.eadandroid.ecore.gui.hud.states.ActionsState;
import es.eucm.eadandroid.ecore.gui.hud.states.DraggingState;
import es.eucm.eadandroid.ecore.gui.hud.states.HiddenState;
import es.eucm.eadandroid.ecore.gui.hud.states.InventoryState;
import es.eucm.eadandroid.ecore.gui.hud.states.MagnifierState;
import es.eucm.eadandroid.ecore.gui.hud.states.ShowingInventoryState;

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
	private DraggingState draggingState;
	
	/**
	 * ELEMENTS
	 */
	
	private Magnifier mag ;
	private Inventory inventory;
	private Wave wave;
	
	

	public HUD() {

		initElements();
		
		hiddenState = new HiddenState(this,wave);
		magnifierState = new MagnifierState(this,mag);
		showInventoryState = new ShowingInventoryState(this,inventory);
		inventoryState = new InventoryState(this,inventory);
		actionsState = new ActionsState(this);
		draggingState = new DraggingState(this);

		currentState = hiddenState;
	}

	private void initElements() {
		
		mag = new Magnifier(65,5,1.5f,GUI.getInstance().getBmpCpy());
		inventory = new Inventory();
		wave = new Wave(Color.parseColor("#2E9AFE"));
		
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
	public boolean processOnDown(UIEvent e) {
		return currentState.processOnDown(e);
	}

	public void setState(int state , Object info) {

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
			((ActionsState) currentState).setItem(info);
			break;
		case HUDstate.DraggingState:
			currentState = draggingState;
			break;
		}

	}

	public void reset() {
		
		currentState = hiddenState;
		
	}
	
	public DraggingState getDragState(){
		
		return draggingState;
	}


}
