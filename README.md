# TAREA27
Para enviar las palabras, primero las recogí en una lista y, antes de mandarlas,
la transformé en un único String separándolas por comas. Hice esto porque UDP solo
envía arrays de bytes seguidos en fila y no puede mandar una lista de Java tal cual, 
ya que un array o una lista guarda direcciones de memoria que solo tienen sentido dentro 
de la RAM del programa cliente. Por eso necesitaba juntar todas las palabras en una sola 
cadena de texto antes de convertirlas a bytes.


##  Declaración del uso de la IA
En este trabajo usé la ia porque estaba perdido, por ser un cazurro y no trabajar un poco más en clase,
no me hizo el código, sino que le pedí que me fuera guiando para saber qué hacer. No le pase nada de codigo, solo le pase
el enunciado, para la base de mi código utilice los códigos de ejemplo de Cliente y Servidor de las presentaciones.

El prompt fue el seguiente:

El cliente pide al usuario varias palabras y las envía al servidor por UDP. El servidor responde con la más larga y su longitud. Si no llega respuesta en 2 segundos, el cliente reintenta hasta 3 veces y, si sigue sin respuesta, muestra «Servidor no disponible».
Requisitos para APTO
- El servidor responde al remitente usando getAddress() y getPort().
- Sin el servidor arrancado, el cliente hace los 3 reintentos y termina.
- En un README explicáis qué formato habéis elegido para enviar las palabras.
  Ayúdame a hacer este ejercicio, pero no me lo hagas, dame una guia de pasos.

