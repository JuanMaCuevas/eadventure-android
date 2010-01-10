
//HELPCODE Resource handler link a pagina : http://www.rbgrn.net/content/288-android-how-to-load-bitmaps-rgb565

package es.eucm.eadandroid.res.resourcehandler2;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;

public class GameResourceHandler {
public static final Bitmap.Config DEFAULT_BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
public static final Bitmap.Config FAST_BITMAP_CONFIG = Bitmap.Config.RGB_565;

public static Bitmap loadBitmap(Drawable sprite, Bitmap.Config bitmapConfig) {
int width = sprite.getIntrinsicWidth();
int height = sprite.getIntrinsicHeight();

Bitmap bitmap = Bitmap.createBitmap(width, height, bitmapConfig);

Canvas canvas = new Canvas(bitmap);
sprite.setBounds(0, 0, width, height);
sprite.draw(canvas);

return bitmap;
}

public static Bitmap loadBitmap(Drawable sprite) {
return loadBitmap(sprite, DEFAULT_BITMAP_CONFIG);
}

private Context context;
private HashSet<GameResourceSet> loadedResourceSets;

/**
* Constructor for a GameResources object
* @param context The application context
*/
public GameResourceHandler(Context context) {
this.context = context;
loadedResourceSets = new HashSet<GameResourceSet>();
}

/**
* This method will generate a resource set containing Bitmap objects based on the passed resourceIds.
* The bitmaps will be loaded using the drawables that are referenced by the resource id's and by rendering
* them onto a canvas using the bitmapConfig that was passed.
* @param setName The name of the resource set that will be generated
* @param resourceIds The resource id's for the movie resources
* @param bitmapConfig The Bitmap.Config object that specifies the rendering method when the bitmap gets generated
* @return Returns the generated resource set
*/
public GameResourceSet loadBitmapResourceSet(String setName, int[] resourceIds, Bitmap.Config bitmapConfig) {
Resources r = context.getResources();

Bitmap[] bitmaps = new Bitmap[resourceIds.length];
for(int i = 0; i < resourceIds.length; i++) {
bitmaps[i] = loadBitmap(r.getDrawable(resourceIds[i]), bitmapConfig);
}

GameResourceSet resourceSet = new GameResourceSet(setName, bitmaps);
loadedResourceSets.add(resourceSet);

return resourceSet;
}

/**
* This method will load movie resources depending on the passed reourceIds.
* @param setName The name of the resource set that will be generated
* @param resourceIds The resource id's for the movie resources
* @return Returns the generated resource set
*/
public GameResourceSet loadMovieResourceSet(String setName, int[] resourceIds) {
Resources r = context.getResources();

Movie[] movies = new Movie[resourceIds.length];
for(int i = 0; i < resourceIds.length; i++) {
movies[i] = r.getMovie(resourceIds[i]);
}

GameResourceSet resourceSet = new GameResourceSet(setName, movies);
loadedResourceSets.add(resourceSet);

return resourceSet;
}

/**
* This method for loading raw resources into a resource set should only be called for loading small files
* that need to be buffered to optimize performance. It will store the raw resources in byte arrays.
* @param setName The name of the resource set that will be generated
* @param resourceIds The resource id's for the raw resources
* @return Returns the generated resource set
* @throws IOException
*/
public GameResourceSet loadRawBytesResourceSet(String setName, int[] resourceIds) throws IOException {
Resources r = context.getResources();

byte[][] byteBuffers = new byte[resourceIds.length][];
for(int i = 0; i < resourceIds.length; i++) {
InputStream is = r.openRawResource(resourceIds[i]);
is.read(byteBuffers[i]);
is.close();
}

GameResourceSet resourceSet = new GameResourceSet(setName, byteBuffers);
loadedResourceSets.add(resourceSet);

return resourceSet;
}

/**
* This method gets a loaded resource set by it's name.
* @param setName The name of the resource set that was loaded earlier
* @return Returns the resource set if found - else returns null
*/
public GameResourceSet getResourceSetByName(String setName) {
for(GameResourceSet resourceSet : loadedResourceSets) {
if(resourceSet.getResourceSetName().equals(setName)) {
return resourceSet;
}
}

return null;
}

/**
* Unloads a specific resource set. This will outlaw the resources in the resource set for the garbage collector.
* The resource set must be known or can be obtained using the getResourceByName method.
* @param resourceSet The resource set that should be unloaded
* @return true if the resource set was found and could be unloaded - false otherwise
*/
public boolean unloadResourceSet(GameResourceSet resourceSet) {
if(resourceSet == null) {
return false;
}

resourceSet.freeResources();
return loadedResourceSets.remove(resourceSet);
}

/**
* This method unloads all resource sets in this GameResources object and outlaws all resources for garbage collecting.
*/
public void unloadAllResourceSets() {
for(GameResourceSet resourceSet : loadedResourceSets) {
resourceSet.freeResources();
}

loadedResourceSets.clear();
loadedResourceSets = new HashSet<GameResourceSet>();
}
}




