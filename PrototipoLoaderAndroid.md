Aquí voy a anotar los cambios y decisiones que voy tomando para realizar la integración del loader en Android .

## Cambios realizados ##

  1. Añado clase Polygon sacada de URL : http://www.anddev.org/viewtopic.php?p=22799  y añado metodo addPoint().
  1. Cambio clases java.awt.Point por las de Android , falta metodo Clone -> lo hago a pelo creandome un objeto nuevo y usando el constructor por copia.
  1. Me cargo el handler de Animaciones y toda la informacion necesaria para la carga de animaciones -> Tarea para la siguiente iteracion ( carga de animacinoes )
  1. Realizo limpieza del paquete es.eucm.eadventure.common.auxiliar ( no se usa por el momento en el motor ) -> dejo solo assetsConstants
  1. Al no tener JOptionPane para mostrar mensajes -> apaño crenado una clase que en el reimplementa los metodos con Alerts de Android ( creo que no se va a usar) .
  1. Null pointer exception en : assesmentSubparser debido a una declaracion doble de currentString , la que se declara localmente y la que se declara en la clase padre subParser.

## Limitaciones de plataforma ##

### Limitaciones SAX Android ###

  * **Caracteristicas soportadas :**

> The default Android implementation of SAXParser supports only the following three features:

> http://xml.org/sax/features/namespaces : Queries the state of namespace-awareness.
> http://xml.org/sax/features/namespace-prefixes : Queries the state of namespace prefix processing
> http://xml.org/sax/features/validation :Queries the state of validation.

> Note that despite the ability to query the validation feature, there is currently no validating parser available. Also note that currently either namespaces or namespace prefixes can be enabled, but not both at the same time.

  * **Bug implementacion qName** : [ISSUE 990](https://code.google.com/p/eadventure-android/issues/detail?id=990): http://code.google.com/p/android/issues/detail?id=990

> Solucion tomada :
    1. Cambio qName por sName en todos los parser y subparser del Loader.
    1. Cambio getQName() por getLocalName() en todos los parser y subparser del Loader.


### Limitaciones DDMS ###

  1. Bug al realizar pull y push a SD del emulador
  1. Solucion : Usar los siguientes comandos de consola.
    * Meter datos : adb push foo.txt /sdcard/foo.txt
    * Sacar datos : adb pull /sdcard/foo.txt foo.txt