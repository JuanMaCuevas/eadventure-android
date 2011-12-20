package es.eucm.eadandroid.common.data.chapter;

import android.graphics.Color;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;

public class QrcodeRule {
	
	public static final double DEFAULT_LATITUDE = 0;
	public static final double DEFAULT_LONGITUD = 0;

 //   private long seconds;
    
    private String Code;
    

    private Conditions initCond;

    private Conditions endCond;

    private Effects effect;



    public String getCode() {
		return Code;
	}

	public void setCode(String password) {
		Code = password;
	}


	private String documentation;

/**
     * to denotate which gps we are getting;
     */
    private String sceneName;

    public String getSceneName() {
		return sceneName;
	}

	public void setSceneName(String id) {
		sceneName = id;
	}


private int fontColor = Color.BLACK;

    private int borderColor = Color.WHITE;

    public QrcodeRule( String pasword, Conditions init, Conditions end, Effects effect ) {

       this.Code=pasword;
        this.initCond = init;
        this.endCond = end;
        this.effect = effect;

   }

    public QrcodeRule( String password ) {

        this( password, new Conditions( ), new Conditions( ), new Effects( ) );
    }

    public QrcodeRule( ) {

        this("");
    }






	/**
     * @return the initCond
     */
    public Conditions getInitCond( ) {

        return initCond;
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

    	QrcodeRule t = (QrcodeRule) super.clone( );
        t.documentation = ( documentation != null ? new String( documentation ) : null );
        t.effect = ( effect != null ? (Effects) effect.clone( ) : null );
        t.endCond = ( endCond != null ? (Conditions) endCond.clone( ) : null );
        t.initCond = ( initCond != null ? (Conditions) initCond.clone( ) : null );
 
        return t;
    }

}
