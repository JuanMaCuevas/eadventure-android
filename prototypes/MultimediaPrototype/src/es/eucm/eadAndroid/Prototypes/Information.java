package es.eucm.eadAndroid.Prototypes;

import java.util.HashMap;

import es.eucm.eadAndroid.Prototypes.multimedia.MultimediaManager;

import android.graphics.Bitmap;

public class Information {
	
	
	private HashMap<String,Object> objects;
	
	private static Information instance = new Information( );
	
	
	public static Information getInstance( ) {

        return instance;
    }
	
	
	/**
     * Empty constructor
     */
    @SuppressWarnings ( "unchecked")
    private Information( ) {
    	
    	
    	objects=new HashMap<String, Object>();
    	
    }
    
    
    public void addobject(String d, Object o)
    {
    	objects.put(d, o);
    }
	
	public Object getobject(String d)
	{
		return objects.get(d);
	}
	
	
	

}
