package es.eucm.eadventure.prototypes.gui.hud.states;

import android.graphics.Canvas;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.PressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UnPressedEvent;
import es.eucm.eadventure.prototypes.gui.hud.HUD;
import es.eucm.eadventure.prototypes.gui.hud.HUDstate;
import es.eucm.eadventure.prototypes.gui.hud.elements.Inventory;

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

		if (inventory.isAnimating((int) ((UnPressedEvent) e).event.getY())) {
			stateContext.setState(HUDstate.InventoryState);
		} else {
			inventory.resetPos();
			stateContext.setState(HUDstate.HiddenState);
		}

		return true;
	}

}
