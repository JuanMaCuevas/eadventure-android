Fuente :Post gestos en Android 1.6

http://android-developers.blogspot.com/2009_10_01_archive.html

Codigo GestureAPI Builder :

[Lo puedes sacar de aqui](http://docs.google.com/a/google.com/File?id=cf49fh6b_6dvdb38c9_b)

Gesture Builder API  ( A partir de la 1.6 )
  * Es una aplicacion que permite al desarrolador guardar el gesto facilmente en un fichero para luego poder usarlo en su aplicación
  * Hace falta tener SD para usarlo ya que los gestos se guardan en la SD .
  * Es util hacer mas de un gesto con el mismo nombre para que la precision del reconocimiento del gesto sea optimo
  * Cada vez que se añade un nuevo gesto este se guarda en sdcard/gestures , estos gestos se deben empaquetar en R.raw para luego hacer uso de ellos con GestureLibrary en tu aplicacion .


```
 mLibrary = GestureLibraries.fromRawResource(this, R.raw.gesto);
if (!mLibrary.load()) {
    finish();
}
```

  * Estructura de un gestureLibrary  ( Imagen )
  * Reconocimiento de gestos :

  * esture overlay :






Esto hay que echarle un vistazo : Son apps que tocan temas interesantes ;D

http://code.google.com/p/apps-for-android/