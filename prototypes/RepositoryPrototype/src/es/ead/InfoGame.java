package es.ead;

import java.util.StringTokenizer;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/** @author Steven Osborn - http://steven.bitsetters.com */
public class InfoGame implements Comparable<InfoGame>{
   
	private String mTittle = "";
     private String mText = "";
     private Bitmap mIcon;
     private boolean mSelectable = true;
     private String codigo;
     private String Imagen;
     private String icon;

     public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public InfoGame(String ti,String text,String cod,String Ima) {
         // mIcon = bullet;
          mText = text;
          mTittle=ti;
          codigo=cod;
          Imagen=Ima;
          
          
          
          StringTokenizer prueba= new StringTokenizer(Ima,"/",true);
		  String aux1=null;
		    while (prueba.hasMoreTokens())
		    {
		    	aux1=prueba.nextToken();
		    }
          icon=aux1;
          
   }
     

     
     public  String getStringIcon() {
 		return icon;
 	}

	public String getImagen() {
		return Imagen;
	}

	public boolean isSelectable() {
          return mSelectable;
     }
     
     public String getTittle() {
		return mTittle;
	}

	public void setTittle(String mTittle) {
		this.mTittle = mTittle;
	}

	public void setSelectable(boolean selectable) {
          mSelectable = selectable;
     }
     
     public String getText() {
          return mText;
     }
     
     public void setText(String text) {
          mText = text;
     }
     
     public void setIcon(Bitmap bitmap) {
          mIcon = bitmap;
     }
     
     public Bitmap getIcon() {
          return mIcon;
     }

     /** Make IconifiedText comparable by its name */
     @Override
     public int compareTo(InfoGame other) {
          if(this.mText != null)
               return this.mText.compareTo(other.getText());
          else
               throw new IllegalArgumentException();
     }
} 