package es.eucm.eadandroid.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadandroid.common.data.chapter.Chapter;
import es.eucm.eadandroid.common.data.chapter.GpsRule;
import es.eucm.eadandroid.common.data.chapter.Timer;
import es.eucm.eadandroid.common.data.chapter.conditions.Conditions;
import es.eucm.eadandroid.common.data.chapter.effects.Effects;
import es.eucm.eadandroid.common.data.chapter.scenes.Scene;



public class GpsSubParser extends SubParser {

    /* Attributes */
    /**
     * Constant for subparsing nothing
     */
    private static final int SUBPARSING_NONE = 0;

    /**
     * Constant for subparsing condition tag
     */
    private static final int SUBPARSING_CONDITION = 1;

    /**
     * Constant for subparsing effect tag
     */
    private static final int SUBPARSING_EFFECT = 2;

    /**
     * Stores the current element being subparsed
     */
    private int subParsing = SUBPARSING_NONE;

    /**
     * Stores the current timer being parsed
     */
    private GpsRule Gpsrule;

    /**
     * Stores the current conditions being parsed
     */
    private Conditions currentConditions;

    /**
     * Stores the current effects being parsed
     */
    private Effects currentEffects;

    /**
     * The subparser for the condition or effect tags
     */
    private SubParser subParser;

    Chapter chapter;

    /* Methods */

    /**
     * Constructor
     * 
     * @param chapter
     *            Chapter data to store the read data
     */
    public GpsSubParser( Chapter chapter ) {

        super( chapter );
        this.chapter=chapter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
     *      java.lang.String, org.xml.sax.Attributes)
     */
    @Override
    public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {
        	// If it is a timer tag, create a new timer with its time
        	//<timer countDown="yes" displayName="timer" multipleStarts="yes" 
        	//runsInLoop="yes" showTime="no" showWhenStopped="no" time="55" usesEndCondition="yes">
            if( sName.equals( "gps" ) ) {
               double longitud=0;
               double latitud=0;
              int radio=0;
               String sceneName="";

                for( int i = 0; i < attrs.getLength( ); i++ ) {
                    if( attrs.getLocalName( i ).equals( "longitud" ) )
                    	longitud = Double.parseDouble(attrs.getValue( i ));
                    if( attrs.getLocalName( i ).equals( "latitud" ) )
                    	latitud = Double.parseDouble(attrs.getValue( i ));
                    if( attrs.getLocalName( i ).equals( "radio" ) )
                    	radio = Integer.parseInt(attrs.getValue( i ));
                    if( attrs.getLocalName( i ).equals( "sceneName" ) )
                    	sceneName = attrs.getValue( i );
                   
                }

                Gpsrule = new GpsRule(latitud,longitud);
               Gpsrule.setRadio(radio);
                Gpsrule.setSceneName(sceneName);
                
                
            }

            // If it is a condition tag, create the new condition, the subparser and switch the state
            else if( sName.equals( "init-condition" ) ) {
                currentConditions = new Conditions( );
                subParser = new ConditionSubParser( currentConditions, chapter );
                subParsing = SUBPARSING_CONDITION;
            }

            // If it is a effect tag, create the new effect, the subparser and switch the state
            else if( sName.equals( "effect" )) {
                currentEffects = new Effects( );
                subParser = new EffectSubParser( currentEffects, chapter );
                subParsing = SUBPARSING_EFFECT;
            }

        }

        // If it is reading an effect or a condition, spread the call
        if( subParsing != SUBPARSING_NONE ) {
            subParser.startElement( namespaceURI, sName, sName, attrs );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
     *      java.lang.String)
     */
    @Override
    public void endElement( String namespaceURI, String sName, String qName ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE ) {

            // If it is a timer tag, add it to the game data
            if( sName.equals( "gps" ) ) {
                chapter.addGpsRule(Gpsrule);
            }

            // If it is a documentation tag, hold the documentation in the slidescene
            else if( sName.equals( "documentation" ) ) {
                Gpsrule.setDocumentation( currentString.toString( ).trim( ) );
            }

            // Reset the current string
            currentString = new StringBuffer( );
        }

        // If a condition is being subparsed
        else if( subParsing == SUBPARSING_CONDITION ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            // If the condition tag is being closed
            if( sName.equals( "init-condition" ) ) {
                Gpsrule.setInitCond( currentConditions );

                // Switch the state
                subParsing = SUBPARSING_NONE;
            }

            // If the condition tag is being closed
            if( sName.equals( "end-condition" ) ) {
                Gpsrule.setEndCond( currentConditions );

                // Switch the state
                subParsing = SUBPARSING_NONE;
            }
        }

        // If an effect is being subparsed
        else if( subParsing == SUBPARSING_EFFECT ) {
            // Spread the call
            subParser.endElement( namespaceURI, sName, sName );

            // If the effect tag is being closed, store the effect in the next scene and switch the state
            if( sName.equals( "effect" ) ) {
                Gpsrule.setEffects( currentEffects );
                subParsing = SUBPARSING_NONE;
            }


        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
     */
    @Override
    public void characters( char[] buf, int offset, int len ) {

        // If no element is being subparsed
        if( subParsing == SUBPARSING_NONE )
            super.characters( buf, offset, len );

        // If it is reading an effect or a condition
        else
            subParser.characters( buf, offset, len );
    }
}
