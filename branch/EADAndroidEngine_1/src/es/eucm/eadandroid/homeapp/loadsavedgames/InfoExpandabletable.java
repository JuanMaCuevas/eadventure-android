package es.eucm.eadandroid.homeapp.loadsavedgames;

import android.graphics.Bitmap;

public class InfoExpandabletable {

	private String[] group;
	private String[][] children;
	private Bitmap[][] screenShots;

	public String[] getGroup() {
		return group;
	}

	public void setGroup(String[] group) {
		this.group = group;
	}

	public String[][] getChildren() {
		return children;
	}

	public void setChildren(String[][] children) {
		this.children = children;
	}

	public void setScreenShots(Bitmap[][] screenShots) {
		this.screenShots = screenShots;
	}

	public Bitmap[][] getScreenShots() {
		return screenShots;
	}

}
