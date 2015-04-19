## Escenas: ##
### (Escenas conectadas por salidas , Dibujo por el que puedes realizar acciones) ###
  * Se muestra en el fondo de la pantalla (“imagenes.background”)
  * (“imagenes.mascara”)
  * Se ejecuta una musica de fondo : duracion(“reporduccion audio”)
  * contiene(“Informacion.documentacion”) (“informacion.nombre”) ???
  * Se muestran los (“Objetos”) asociados a la escena si se cumplen las (“condiciones”) asociadas
  * Barreras: sitios por los que el personaje no puede pasar con (“condiciones”)
  * Zonas activas: poseen nombre se activaran dependiendo de unas(“condiciones”) y podran ejecutar (“acciones”) puede hacer estas acciones (examinar,usar,coger y personalizar) no todas las posibilidades
  * hay (“salidas”)
  * movimientos del protagonista
  * mostrara posicion inicial del jugador(x,y)
  * mostrara tamaño del protagonista
  * condicionara trayecctorias del protagonista

## imagenes ##
  * Backgroung – imagen JPG etc.. obligatorio, 800x600 al menos
  * Mascara – imagen png blanco y negro se guarda en /assets/background
  * objeto para repersentarlo /assets/image/ (no hay restricciones)
  * Icono para representarlo en inventario tamaño 80x46 /assets/icon/

## informacion ##
  * documentacion(texto amplio no saldra por pantalla)
  * nombre
  * Descripcion breve(puede salir por pantalla)
  * Descripcion detallada(puede salir por pantalla)


## Salidas ##
### : (enlace entre dos escenas) ###
  * Siguiente escena
  * Posicion protagonista en siguiente escena (x,y)
  * Tipos de transiciones ( sin transicion , arriba, abajo... transparencia)
  * tiempo de transicion (milisegundos)
  * icono de transicion
  * posicion, tamaño de la salida – rectangular (x,yancho alto )
  * irregular????
  * Una transicions tiene (“condiciones)” y puede crear (“Efectos”) y (“postefectos”)??

## Objetos ##
  * contiene (“imagenes.objeto”) (“imagenes.icono”)
  * contiene (“informacion.nombre”) y (“informacion.breve”)(“informacion.detallada”)
  * (“informacion.breve”): se muestra al mirar el objeto
  * (“informacion.detallada”) se muestra cuando se examina
  * Pueden hacer (“acciones”) con (“condiciones”) con la posibilidad de alcanzar el objeto para hacer la accion o no
> ## Acciones ##
**apunte Por defecto, el jugador solo puede realizar acciones predefinidas sobre los objetos (básicamente coger, examinar y usar), sobre los personajes (examinar, hablar y usar  con) y sobre las zonas activas (coger, examinar, usar y usar** con)
.
  * coger:
    * documentacion de accion
    * puede crear (“efectos”)
    * inventario
    * contiene (“imagen.icono”)
  * Examina:
    * puede crear (“Efectos”)
    * contiene (“imagen.icono”)
    * documentacion de la accion(se mostrara a no ser que la descripcion detallada este activa)
  * Usar:
    * puede crear(“efecto”)
    * contiene (“imagen.icono”)
      * documentacion de la accion

  * Personalizable  accion sobre el objeto
    * Nombre de la acccion:(se llamara asi en la gui)
    * puede utilizar  imagenes
    * contiene imagen estado norma(“imagen.icono”)
    * contiene imagen cuando el boton esta encima(“imagen.icono”)
    * contiene imagen para el boton cuando se presiona(“imagen.icono”)
    * puede utilizar (“animaciones”)
    * documentacion de la accion
    * crear (“efectos”)

  * Personalizable: accion que interactua con otro objeto:
    * Nombre de la acccion:(se llamara asi en la gui)
    * interactua con otro objeto mirar (“condiciones”)
    * utiliza imanenes
    * contiene imagen estado norma(“imagen.icono”)
    * contiene imagen cuando el boton esta encima(“imagen.icono”)
    * contiene imagen para el boton cuando se presiona(“imagen.icono”)
    * puede utilizar (“animaciones”)
    * documentacion de la accion
    * crear (“efectos”)
  * Usar con:
    * puede crear efectos
    * contiene imagen (imagen.icono)
    * interactua con otro objeto mirar (“condiciones”)
    * documentacion de la accion
  * Entregar a:
    * puede generar Efectos
    * interactua con otro personaje mirar condiciones
    * contiene image(“imagen.icono”)


  * Varias acciones pueden estar activas en el mismo objeto ????

> ## Objetos de atrezo ##
  * no permiten interaccion
  * apariencia (imagenes.objeto)
  * nombre del objeto(“informacion.nombre”)
  * documentacion del objeto(“informacion.documentacion”)

## libros ##
  * contiene imagen de backgroung(“imagen.background”)
  * contiene documentacion (“informacion.descripcion”)
  * se podra mostrar
    * parrafo del titulo con su contenido
    * parrafo de texto con su contenido
    * parrafo de lista con su contenido punto por punto
    * añadir imagen contendra (“imagen.objeto”)


## Personajes ##

  * El personaje puede tener de 0..N apariencias distintas  - > Bloque de Recursos #1 ..#N
  * Estas apariencias cambian según unas condiciones establecidas. ("Condiciones")
  * De todas las apariencias en las que se cumplan las ("Condiciones") se usara la que se haya definido antes.
  * Cada apariencia cuenta con un grupo de animaciones : Animaciones Hablando ,Animaciones Parado , Animaciones Caminando , Animaciones Usando
    * Cuando un personaje realize la ("Accion") hablar se ejecutara la animación Hablando.
    * Cuando un personaje realize la ("Accion") de moverse ("Movimiento") se ejecutara la animacion de caminar.
    * Cuando un personaje realize la ("Accion") de usar o usar con.. se ejecutara la animacion Usando
      * Cada grupo de animaciones cuenta con 4 tipos de animacion excepto “Usando” que puede tener 2 tipos de animacion (hacia derecha y izquierda). Estas animaciones son Hacia Arriba , Hacia Abajo , Hacia Derecha ,Hacia Izquierda .
    * La Animacion que se ejecuta se elige dependiendo de la direccion en la cual se esta realizando la accion a la que representan.
    * Si se ejecuta hacia la izquierda y esta no esta definida se utilizara Hacia la derecha invertida.
    * Cada animación es un conjunto de ("Imagenes")“soportadas por el motor” que sigen el siguiente formato : _**01.png ….**_DD.png .
    * Al reproducirse se utiliza un sistema de Animaciones basado en FrameRate
    * Las imagenes se guardan en /assets/images

  * El personaje contiene informacion asociada que el motor usa para ("Accesibiliadad") y mostrarlo en el juego.
  * El nombre del personaje es texto( puede contener variables y flags incrustrados) que se muestra al poner el puntero del raton sobre el personaje .
  * Descripcion breve del personaje es texto( puede contener variables y flags incrustrados) que se muestra al mirar el personaje.
  * Descripcion detallada del personaje es texto( puede contener variables y flags incrustrados ) que se muestra al examinar el personaje.

  * El dialogo en un personaje puede mostrarse CON o SIN Bocadillo
  * El bocadillo es un area rectangular con los bordes redondeados que contiene el texto asociado a un dialogo de una ("Conversacion").
  * La letra del dialogo tiene asociado un color de relleno y de borde en RGB o Hexadecimal???
  * El bocadillo tambien tiene asociado un color de relleno y de borde en RGB o Hexadecimal ?¿?¿
  * El dialogo de una ("Conversacion") de un personaje puede ser sintetizado por voz ("Sintetizador de Voz")
  * El personaje puede tener activado el sintetizar todas las conversaciones o no.

  * Pueden tener de 0..N acciones
  * Las acciones pueden ser : Examinar , Hablar Con.. Usar y Accion personalizada
  * Para cada accion se puede definir si el protagonista o jugador necesita alcanzar el personaje para realizar la acción.
    * Si se necesita alcanzar el personaje se especifica a que distancia en pixeles?¿ se considera alcanzado .
  * La accion puede tener unas condiciones asociadas para que se cumplan ("Condiciones")
  * Si el personaje tiene varias acciones del mismo tipo , se empleara de las acciones que cumplen las condiciones asociadas , la que se definio antes.
  * Las acciones tendran asociadas una representacion en forma de animacion ya definida en  las Animacinoes del personaje .

  * Cada tipo de accion tienen caracteristicas distintas :

  * Accion Examinar :
    * Puede tener asociado de 0..N “Efectos” (Vease Efectos) que se realizan cuando el personaje se examine . Los efectos pueden tener “condiciones” asociadas . Si se ponen efectos repetidos se emplea el efecto que cumpla las condiciones y que primero se definio.
    * Salida Activa cuando condiciones Inactivas?¿?¿?¿?¿
    * Si salida Activa cuando condiciones Inactivas → No Efectos .
      * Puede tener de 0..N no efectos . Los no efectos pueden tener (“Condiciones”) asociadas .
      * Si se ponen no efectos repetidos se emplea el efecto que cumpla las condiciones y que primero se definio.

  * Accion Usar :
    * as caracteristicas de la accion USAR son exactamente las mismas que las de EXAMINAR .

  * Accion Hablar Con..
    * La accion puede tener asociado un (“Efecto”)o no
    * Las demas caracteristicas son iguales que las de USAR y EXAMINAR puede tener mas efectos .

  * Accion Personalizable :  ME falta...

## Sintetizador de Voz ##
  * El sintetizador de voz recibe como entrada un fragmento de texto de una ("Conversacion") y lo transforma en audio.
  * El sintetizador de voz cuenta con 2 voces : Kevin y Kevin16
  * Cuenta solo con soporte del habla inglesa.

## Condiciones ##

  * Las condiciones son fórmulas lógicas (operadores AND y OR) en las que los elementos pueden ser (“Flag”) , (“Variables”) y (“Estados Globales”) del capitulo.
  * Los (“Flag”) se puede comprobar si estan activados o no
  * Las (“Variables”) se puede comprobar si son > >= < <= = que un valor numerico comprendido entre [0,inf]
  * Los estados globales se pueden combrobar si se estan cumpliendo o no (“Estados Globalos”)

## Flags ##

  * Es un dato en el que se guarda un valor : ACTIVADO o DESACTIVADO
  * Cada flag tiene su nombre de flag asociado.

## Variables ##

  * Es un dato en el que se guarda un valor numerico comprendido entre [0,inf]
  * Cada variable tiene un nombre de variable asociado

## Efectos ##

  * Activar flag (“Flags” ) : de entre todos creados en el capitulo . De X → 1
  * Desactivar flag ( “Flags” ) de entre todos creados en el capitulo . De X → 0
  * Cambiar valor variable (“Variables”)  por un valor numerico , de entre todas creados en el capitulo
  * Incrementa el valor de una variable (“Variables”) con un valor numerico ,  de entre todos creadas en el capitulo
  * Decrementar variable (“Variables”) con valor numerico de entre todos las creadas en el capitulo
  * Lanzar (“Reproduccion de Audio”) .
    * Puede hacerse en segundo plano o en primer plano.
    * En primer plano el juego se bloquea hasta que termine la reproduccion y no deja lanzar efectos , mover el protagonista etc...)
    * En segundo plano el juego continua mientras se reproduce el audio .
  * Lanzar una animacion (“Animaciones”) en unas coordenadas  XY de destino , de entre todas las del capitulo.
  * Lanzar (“Escena Intermedia”) de entre todas las del capitulo .
    * Terminada la escena intermedia se continuara ejecutando los efectos que tenga asociada dicha escena intermedia . Se debe producir la cadena de efectos.
  * Lanzar dialogo protagonista :
    * El texto saldra con los colores asociados a las (“Conversaciones”) para el protagonista si tiene activada la opcion sintetizar siempre , la frase introducida se 	sitetizara (“Sintetizador de Voz”) El texto se muestra al lado del protagonista .
  * Lanzar dialogo personaje :
    * El texto saldra con los colores asociados a las (“Conversaciones”) para el personaje si tiene activada la opcion sintetizar siempre , la frase introducida se sitetizara 	(“Sintetizador de Voz”) El texto se muestra al lado del personaje .
  * Lanzar (“Libro”) :
    * Se produce una transicion al (“Libro”) especificado , de entre los libros del capitulo.
  * Lanzar (“Conversacion”) . Solo las del capitulo l.
  * Lanzar (“Transicion de Escena”) --> No esta hecho asi pero interesaria , esta hecho directamente la escena a pelo y se elige donde debe aparecer el protagonista … )
  * Lanzar ir a la ultima escena  → Se produce una transicion a la ultima escena.
  * Consumir un objeto : el (“Objeto”) desaparece de la (“Escena”)
  * Generar objeto :  el (“Objeto”) se añade al (“Inventario”)
  * Lanzar Mover protagonista : se produce un (“Movimiento ”) del (“Personaje”) a una pos XY→ no tira en el motor
  * Lanzar Mover personaje : se produce un (“Movimiento ”) del (“Personaje”) a una pos XY → supongo que esto tampoco tirara
  * Lanzar (“Macro”) → se lanza la (“Macro”) ->se ejecutan los (“Efectos”) de la macro uno seguido de otro
  * Lanzar Cancelar Accion → Se cancelan todas los (“Efectos”) que tienen las (“Acciones”) por defecto  ( No entiendorrr ) Ejemplo : se desactiva que se consuma automáticamente el objeto de la escena al realizar la (“Accion”) de coger un objeto .
  * Lanzar Efecto con probabilidad
    * Se tienen dos (“Efectos”) .
    * Se sabe la probabilidad que tienen de ser escogidos los dos efectos .
    * Se realiza el calculo de ser elegido con esa probabilidad y se lanza el (“Efecto”) .
    * La probabilidad esta en % y entre las dos suman 100%.
  * Lanzar espera de tiempo : el juego se bloquea y no se permite interaccion con el usuario , se define en segundos .






## Escenas Intermedias ##
  * contienen nombre y descripción???
  * pueden ser de diapositivas o de video
  * ocupan toda la pantalla al mostrarse
  * las de diapositivas:
    * muestran una animacion en formato jpg slides o eaa ("Animaciones")
    * reproducen un sonido en formato mp3, mid o midi ("Reproduccion Audio")
  * las de video muestran un video en formato mov,avi,mpg ("Reproducción de Video")
  * Al finalizar pueden: volver a la escena anterior, ir a nueva escena ("Transicion Escenas") o terminar el capítulo.
  * durante la escena el usuario no puede interactuar ( solo con Menu)

## Conversaciones ##
  * muestran conversación entre dos o más personajes (incluido el protagonista???)
  * compuestas por nodos de opciones y nodos de diálogo unidos entre enlaces
  * los nodos de dialogo están compuestos por líneas de texto (bocadillo).
  * cada línea de texto pertenece a un solo personaje que participa en la conversación.
  * en cada momento de un diálogo se muestra una sola línea de texto
  * cada línea se mostrará con tiempo proporcional a su longitud
  * nodos de opciones muestran en lineas de texto las opciones que el jugador puede responder
  * cuando el jugador selecciona una respuesta la conversación continúa (enlaza) con el nodo correspondiente
  * las líneas pueden tener audio asociado ("Reproduccion Audio")
  * las lineas pueden contener condiciones que decidirán si se muestran o no. ("Condiciones")
  * las líneas pueden ser reproducidas por un ("sintetizador de voz") ("Accesibilidad")
  * los nodos pueden tener efectos



## Caracteristicas Avanzadas ##
### Temporizadores ###
  * cada uno tiene un tiempo asociado entre 1 y 356.400 segundos
  * un temp. se puede mostrar o no
  * si se muestra:
    * se muestra su nombre
    * se muestra avanzando ó retrocediendo
    * cuando está parado se muestran ó no
  * un temp. puede tener una o más ("condiciones") iniciales
    * un temp se activa cuando se cumplen sus condiciones iniciales.
    * si un temp. no tiene condiciones iniciales se activa automaticamente.
  * un temp. puede tener una o más ("condiciones") finales
    * un temp. se desactiva cuando se cumplen sus condiciones finales
    * si un temp. no tiene condiciones finales se desactiva cuando NO se cumplen sus condiciones iniciales
  * un temp. puede tener uno o más efectos a ejecutar cuando expire
  * un temp. puede tener uno o más efectos a ejecutar cuando se desactive
  * un temp. parado puede iniciarse nuevamente (multiples inicios) si se cumplen las condiciones de entrada
  * un temp. puede funcionar en bucle o detenerse cuando llega a su fin.


### Estados globales ###
  * un EG Tiene identificador, descripción y una lista de condiciones
  * EG estará activo si no tiene condiciones ó si se cumplen todas sus condiciones

### Macros ###
  * un macro tiene identificador, descripción y una lista de efectos