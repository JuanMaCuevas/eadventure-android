package es.eucm.eadventure.prototypes.gui.hud.states;

import android.view.MotionEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.PressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.ScrollPressedEvent;
import es.eucm.eadventure.prototypes.control.gamestates.eventlisteners.events.UIEvent;
import es.eucm.eadventure.prototypes.gui.hud.HUD;
import es.eucm.eadventure.prototypes.gui.hud.HUDstate;
import es.eucm.eadventure.prototypes.gui.hud.elements.Inventory;

public class HiddenState extends HUDstate{
	
	
	public HiddenState(HUD stateContext) {
		super(stateContext);
	}
	
	@Override
	public boolean processPressed(UIEvent e) {	
		MotionEvent m = ((PressedEvent) e).event;
		process(m);
		return stateContext.processPressed(e);
	}

	@Override
	public boolean processScrollPressed(UIEvent e) {
		MotionEvent m = ((ScrollPressedEvent) e).eventSrc;
		process(m);
		return stateContext.processScrollPressed(e);
	}
	
	private void process(MotionEvent e) {
		if (e.getY() > Inventory.INVENTORY_DRAG_REGION_HEIGHT) 
			stateContext.setState(HUDstate.MagnifierState);
		else stateContext.setState(HUDstate.ShowingInventoryState);
	}

}
