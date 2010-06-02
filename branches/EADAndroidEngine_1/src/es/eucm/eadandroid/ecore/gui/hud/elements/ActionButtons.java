package es.eucm.eadandroid.ecore.gui.hud.elements;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadandroid.ecore.gui.GUI;

import android.graphics.Bitmap;

public class ActionButtons implements DataSet{
	
    /**
     * Hand index action button
     */
    public static final int ACTIONBUTTON_HAND = 0;

    /**
     * Eye index action button
     */
    public static final int ACTIONBUTTON_EYE = 1;

    /**
     * Mouth index action button
     */
    public static final int ACTIONBUTTON_MOUTH = 2;

    /**
     * Drag index action button
     */
    public static final int ACTIONBUTTON_DRAG = 3;

    /**
     * Custom index action button
     */
    public static final int ACTIONBUTTON_CUSTOM = 4;

    public static final int MAX_BUTTON_WIDTH = (int) ( 160 * GUI.DISPLAY_DENSITY_SCALE );

    public static final int MAX_BUTTON_HEIGHT = (int) ( 96 * GUI.DISPLAY_DENSITY_SCALE );

    public static final int MIN_BUTTON_WIDTH = (int) ( 80 * GUI.DISPLAY_DENSITY_SCALE );

    public static final int MIN_BUTTON_HEIGHT = (int) ( 48 * GUI.DISPLAY_DENSITY_SCALE );
    
   private List<ActionButton> buttons = new ArrayList<ActionButton>();
    

	public Object getItem(int i) {

		return buttons.get(i);
	}

	public int getItemCount() {
		return buttons.size();
	}

	public Bitmap getItemImageIcon(int i) {
		return buttons.get(i).getButtonNormal();
	}

	public String getItemName(int i) {
		return buttons.get(i).getName();
	}

	public void clear() {
		buttons.clear();	
	}

	public void add(ActionButton actionButton) {
		buttons.add(actionButton);
	}

}
