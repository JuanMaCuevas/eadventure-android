# Lupa #

  * Para poder hacer zoom necesito tener una copia del bitmap del surface para poder coger la región y representarla zoomeada en la lupa. La decision es tener un bitmap sobre el que pintar todo el escenario , personajes etc..  y a la hora de pintarlo en pantalla pintar ese bitmap sobre el surface .
  * Es eficiente aunque contenemos una copia de lo que estamos pintando .
  * Efecto Lupa con Mesh
  * Donde pongo el texto sobre la lupa dentro de la lupa?¿

## Zoom ##
  * Que sea definible

# Orientacion de  pantalla #

  * Para que el activity SOLO se muestre en orientacion landscape , debe estar definido como activity con orientacion landscape en el manifest : ( por el momento vale , pero esto cada vez que se gira el activiry se pausa y se recrea , deberia de continuar , y que pase de los eventos de girar el dispositivo
```
<activity android:name=".Drawproof" 
    android:label="@string/app_name" 
  android:screenOrientation="landscape"> 
```

## UI Events ##

  * Repasar con equipo la diversidad de los eventos que pueden producirse ...