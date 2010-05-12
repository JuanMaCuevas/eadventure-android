package es.eucm.eadandroid.common.data.chapter;

import android.graphics.Color;
import es.eucm.eadandroid.common.data.Documented;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;




public class GpsRule implements Cloneable, Documented {


	public static final double DEFAULT_LATITUDE = 0;
	public static final double DEFAULT_LONGITUD = 0;

 //   private long seconds;
    
    private double latitude;
    private double longitude;

    private Conditions initCond;

    private Conditions endCond;

    private Effects effect;

    private Effects postEffect;

    private String documentation;

    private Boolean usesEndCondition;
    
    private int Radio;

    /**
     * to denotate which gps we are getting;
     */
    private String Id;

    public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public int getRadio() {
		return Radio;
	}

	public void setRadio(int radio) {
		Radio = radio;
	}

	private int fontColor = Color.BLACK;

    private int borderColor = Color.WHITE;

    public GpsRule( double latitud,double longitud, Conditions init, Conditions end, Effects effect, Effects postEffect ) {

        this.latitude = latitud;
        this.longitude = longitud;
        this.initCond = init;
        this.endCond = end;
        this.effect = effect;
        this.postEffect = postEffect;
        usesEndCondition = true;
        Radio=50;
       
        
        
        
        
    }

    public GpsRule( double latitud, double longitud ) {

        this( latitud,longitud, new Conditions( ), new Conditions( ), new Effects( ), new Effects( ) );
    }

    public GpsRule( ) {

        this(0,0);
    }




    public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
     * @return the initCond
     */
    public Conditions getInitCond( ) {

        return initCond;
    }
    
    /**
     * @return the usesEndCondition
     */
    public Boolean isUsesEndCondition( ) {

        return usesEndCondition;
    }

    /**
     * @param usesEndCondition
     *            the usesEndCondition to set
     */
    public void setUsesEndCondition( Boolean usesEndCondition ) {

        this.usesEndCondition = usesEndCondition;
    }

    /**
     * @param initCond
     *            the initCond to set
     */
    public void setInitCond( Conditions initCond ) {

        this.initCond = initCond;
    }

    /**
     * @return the endCond
     */
    public Conditions getEndCond( ) {

        return endCond;
    }

    /**
     * @param endCond
     *            the endCond to set
     */
    public void setEndCond( Conditions endCond ) {

        this.endCond = endCond;
    }

    /**
     * @return the effect
     */
    public Effects getEffects( ) {

        return effect;
    }

    /**
     * @param effect
     *            the effect to set
     */
    public void setEffects( Effects effect ) {

        this.effect = effect;
    }

    /**
     * @return the postEffect
     */
    public Effects getPostEffects( ) {

        return postEffect;
    }

    /**
     * @param postEffect
     *            the postEffect to set
     */
    public void setPostEffects( Effects postEffect ) {

        this.postEffect = postEffect;
    }

    /**
     * @return the documentation
     */
    public String getDocumentation( ) {

        return documentation;
    }

    /**
     * @param documentation
     *            the documentation to set
     */
    public void setDocumentation( String documentation ) {

        this.documentation = documentation;
    }

   
    /**
     * @return the fontColor
     */
    public int getFontColor( ) {

        return fontColor;
    }

    /**
     * @param fontColor
     *            the fontColor to set
     */
    public void setFontColor( int fontColor ) {

        this.fontColor = fontColor;
    }

    /**
     * @return the borderColor
     */
    public int getBorderColor( ) {

        return borderColor;
    }

    /**
     * @param borderColor
     *            the borderColor to set
     */
    public void setBorderColor( int borderColor ) {

        this.borderColor = borderColor;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

    	GpsRule t = (GpsRule) super.clone( );
        t.documentation = ( documentation != null ? new String( documentation ) : null );
        t.effect = ( effect != null ? (Effects) effect.clone( ) : null );
        t.endCond = ( endCond != null ? (Conditions) endCond.clone( ) : null );
        t.initCond = ( initCond != null ? (Conditions) initCond.clone( ) : null );
        t.postEffect = ( postEffect != null ? (Effects) postEffect.clone( ) : null );
        t.usesEndCondition = usesEndCondition;
        return t;
    }

}
