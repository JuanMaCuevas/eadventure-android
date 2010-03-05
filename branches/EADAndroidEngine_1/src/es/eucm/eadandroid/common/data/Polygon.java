package es.eucm.eadandroid.common.data;


/**
 * Minimum Polygon class for Android.
 */
public class Polygon {

    // Polygon coodinates.
    private int[] polyY, polyX;
    private int points;

    // Number of sides in the polygon.
    private int polySides;

    /**
     * Default constructor.
     * @param px Polygon y coods.
     * @param py Polygon x coods.
     * @param ps Polygon sides count.
     */
    
    
  
    
    
    public Polygon( int[] px, int[] py, int ps ) {

        polyX = px;
        polyY = py;
        polySides = ps;
    }
    
	public Polygon(int maxSize) {
		// TODO Auto-generated constructor stub
		polyX = new int[maxSize];
		polyY = new int[maxSize];
		points = 0;
		polySides = -1;
	}


	/**
     * Checks if the Polygon contains a point.
     * @see "http://alienryderflex.com/polygon/"
     * @param x Point horizontal pos.
     * @param y Point vertical pos.
     * @return Point is in Poly flag.
     */
    public boolean contains( int x, int y ) {

        boolean oddTransitions = false;
        for( int i = 0, j = polySides -1; i < polySides; j = i++ ) {
            if( ( polyY[ i ] < y && polyY[ j ] >= y ) || ( polyY[ j ] < y && polyY[ i ] >= y ) ) {
                if( polyX[ i ] + ( y - polyY[ i ] ) / ( polyY[ j ] - polyY[ i ] ) * ( polyX[ j ] - polyX[ i ] ) < x ) {
                    oddTransitions = !oddTransitions;          
                }
            }
        }
        return oddTransitions;
    }
    
	/**
     * Checks if the Polygon contains a point.
     * @see "http://alienryderflex.com/polygon/"
     * @param x Point horizontal pos.
     * @param y Point vertical pos.
     * @return Point is in Poly flag.
     */
    public boolean contains( float fx, float fy ) {
    	int x=Float.floatToIntBits(fx);
    	int y=Float.floatToIntBits(fy);
        boolean oddTransitions = false;
        for( int i = 0, j = polySides -1; i < polySides; j = i++ ) {
            if( ( polyY[ i ] < y && polyY[ j ] >= y ) || ( polyY[ j ] < y && polyY[ i ] >= y ) ) {
                if( polyX[ i ] + ( y - polyY[ i ] ) / ( polyY[ j ] - polyY[ i ] ) * ( polyX[ j ] - polyX[ i ] ) < x ) {
                    oddTransitions = !oddTransitions;          
                }
            }
        }
        return oddTransitions;
    }


	public void addPoint(int x, int y) {
		
		    polyX[points] = x;
	        polyY[points] = y;
	        points++;
	        polySides++;
		
	}  
}