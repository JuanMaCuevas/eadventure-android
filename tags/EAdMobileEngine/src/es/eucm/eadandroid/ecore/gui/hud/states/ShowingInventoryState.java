package es.eucm.eadandroid.ecore.gui.hud.states;

import android.graphics.Canvas;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.PressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UIEvent;
import es.eucm.eadandroid.ecore.control.gamestate.eventlisteners.events.UnPressedEvent;
import es.eucm.eadandroid.ecore.gui.hud.HUD;
import es.eucm.eadandroid.ecore.gui.hud.HUDstate;
import es.eucm.eadandroid.ecore.gui.hud.elements.Inventory;

public class ShowingInventoryState extends HUDstate {

	private Inventory inventory;

	public ShowingInventoryState(HUD stateContext, Inventory inventory) {
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
	public boolean processPressed(UIEvent e) {
		inventory.updateDraggingPos((int) ((PressedEvent) e).event.getY());
		return true;
	}

	@Override
	public boolean processScrollPressed(UIEvent e) {
		inventory.updateDraggingPos((int) ((ScrollPressedEvent) e).eventDst
				.getY());
		return true;
	}

	@Override
	public boolean processUnPressed(UIEvent e) {

		if (inventory.isAnimating()) {
			stateContext.setState(HUDstate.InventoryState,null);
		} else {
			inventory.resetPos();
			stateContext.setState(HUDstate.HiddenState,null);
		}

		return true;
	}

}
