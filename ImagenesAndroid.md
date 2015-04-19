# Interfaz de las aventuras gráficas en dispositivos móviles #

Uno de los retos que se nos plantean durante el diseño de ésta aplicación es la forma de mostrar al usuario la interfaz del juego. Hasta ahora el motor de eAdventure muestra los juegos en una ventana de tamaño fijo (600x800px) apto para pantallas de ordenador. En cambio en Android la situación cambia ya que el sistema operativo está diseñado para funcionar en dispositivos móviles con resoluciones de pantalla mas reducidas. Por ello debemos encontrar una solución óptima para que la experiencia del usuario durante el juego mantenga un buen nivel, es decir que se vea bien y que sea rápido. Para éste propósito vamos a analizar una serie de cuestiones sobre la plataforma.


## Cuestiones sobre la carga y tratamiento de imágenes ##
### Analisis del pre-procesado de imágenes. ¿Se cargan las imágenes directamente del sistema de ficheros? ¿Es necesario pre-procesarlas? ###

> Preprocesado de imágenes : (escalar comprimir, elegir formato (PNG))

Si queremos soportar los juegos actuales (generados para escritorio) tenemos las siguientes opciones:
  * Realizar el procesado de las imágenes en el editor. Al exportar un proyecto de aventura seleccionar una opción, ej.: exportar para móviles android.
    * Pros: La carga del preprocesado la realiza el ordenador de escritorio, tamaño del .ead es reducido (acorde con la memoria de dispositivos móviles).
    * Cons: Requiere modificación del editor. Cuestión: ¿a qué tamaño escalamos?

  * Realizar el procesado de las imágenes en el móvil en tiempo de ejecución.
    * Pros: Un solo .ead (generado en el editor actual). Funcionaría en distintos dispositivos. No requiere cambiar el editor.
    * Cons: Los .ead ocupan mucho. El preprocesado de imágenes consume muchos recursos CPU y memoria.

  * Opción entre las anteriores. Preprocesar imágenes en editor a un tamaño estándar para móviles. Esto resulta en archivos .ead más livianos y el reescalado en el móvil es menos costoso

  * _Alternativa_:  Mantener los juegos subidos en un repositorio. Se podría generar en el servidor el .ead personalizado para cada resolución y tipo de dispositivo.
    * Pros: El preprocesado en el servidor y que no se tratan imágenes en el dispositivo. Las imágenes del juego estarían adaptadas a cada resolución.
    * Cons: La implementacion y el mantenimiento del servidor.




•	Averiguar cómo se cargan imágenes en ANDROID. Analizar distintas opciones.
-	Solo se puede cargar las imágenes de Resources de Android cuando el juego va compilado con el motor .
-	Cargar imágenes desde memoria externa o interna ( donde se encuentre el ead)

-	Carga por red
- Cargarlo como Drawable ,  imageview , como bitmap , como textura opengl




### Averiguar cómo se usan imágenes en ANDROID. Analizar rendimiento. ¿Es necesario usar una caché? ¿Pueden cargarse imágenes al vuelo según se necesitan? hay que cargarlas todas a la vez? cuántas imágenes pueden cargarse a la vez? ###

[PrototipoDrawPicsAndroid#Carga](PrototipoDrawPicsAndroid#Carga.md): Tiempo de carga de imágenes varios tamaños, pruebas de cache, acceso directo al .ead (memoria interna o SD), precarga a memoria. Analizar tiempos de carga y tamaño en memoria.

### Averiguar cómo se pueden escalar imágenes en ANDROID. Analizar distintas opciones. Analizar rendimiento ###


[PrototipoDrawPicsAndroid#Escalado](PrototipoDrawPicsAndroid#Escalado.md):  Escalado de imágenes. Tiempos de ejecución

### Analizar el tratamiento de transparencias en imágenes ANDROID (rendimiento) ###

[PrototipoDrawPicsAndroid#Transparencias](PrototipoDrawPicsAndroid#Transparencias.md): Benchmark de transparencias.

### Analizar el sistema de pintado de ANDROID. ¿Permite backbuffer? ¿Quién controla el pintado? ¿Cada cuánto se repinta la pantalla? ###

Objetos Bitmap, BitmapDrawable, PictureDrawable, LayerDrawable

### Determinar formatos de imagen compatibles.Mecanismos de tratamiento de imágenes. ¿Como se realiza un fade out? ###

Existen dos opciones para el pintado de imágenes con la API de Android
Opción  A, pintar directamente en una vista. Es la mejor opción para gráficos simples que no hace falta cambiar dinamicamente, mientras no se trate de un juego de alto rendimiento.

Opción B, pintado sobre el canvas. Es major cuando el repintado es frecuente. Es la técnica recomendada para cualquier videojuego. Pero hay dos formas de hacerlo: en el mismo thread haciendo uso de la llamada onDraw(), o en otro thread controlado a través de un SurfaceView y dibujando tan rápido como el thread es capaz. Ésta última es la mejor opción para nuestra aplicación.

[PrototipoCargaImagenes](PrototipoCargaImagenes.md) de distintas cargas de imágenes.



### Configuraciones de resolución en ANDROID. ¿Qué configuraciones de resolución permite ANDROID? (E.g. 800x600, 1024x768...) ¿Puedes poner resoluciones "libres" como tú harías en una aplicación de escritorio (X ej en java puedes darle a una ventana el tamaño que quieras). ###



La siguiente tabla muestra las [configuracions de pantalla soportadas por Android](http://developer.android.com/guide/practices/screens_support.html#range)


Low density (120), ldpi
Medium density (160), mdpi
High density (240), hdpi
Small screen
QVGA (240x320), 2.6"-3.0" diagonal


Normal screen
WQVGA (240x400), 3.2"-3.5" diagonal
FWQVGA (240x432), 3.5"-3.8" diagonal
HVGA (320x480), 3.0"-3.5" diagonal
WVGA (480x800), 3.3"-4.0" diagonal
FWVGA (480x854), 3.5"-4.0" diagonal
Large screen

WVGA (480x800), 4.8"-5.5" diagonal
FWVGA (480x854), 5.0"-5.8" diagonal


## Enlaces útiles ##

  * [Scrolling large image](http://groups.google.com/group/android-developers/browse_thread/thread/75469626215fa969?pli=1)