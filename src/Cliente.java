import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args){
        // Se inicializa el escáner para leer por teclado y una lista para almacenar las palabras introducidas.
        Scanner sc = new Scanner(System.in);
        List<String> palabras = new ArrayList<>();
        System.out.println("Escribe tantas palablas como quieras, para salir escribe fin o deja un espacio vacío:");

        // Bucle infinito que solicita palabras al usuario hasta que se cumpla la condición de salida.
        while(true){

            // Se lee la línea introducida por el usuario.
            String entrada = sc.nextLine();

            // Comprueba si el usuario ha escrito "fin" (ignorando mayúsculas/minúsculas) o un espacio en blanco para salir del bucle.
            if(entrada.equalsIgnoreCase("fin") || entrada.equalsIgnoreCase("Fin")||entrada.equalsIgnoreCase(" ")){
                break;
            }else{
                // Si no es la palabra de salida, se añade a la lista.
                palabras.add(entrada);
            }
        }

        // Se unen todas las palabras de la lista en una sola cadena separada por comas para poder enviarla por UDP.
        String mensaje = String.join(",", palabras);

        // Se abre un socket UDP en un puerto libre (efímero) usando try-with-resources para que se cierre automáticamente al terminar.
        try (DatagramSocket socket = new DatagramSocket()) {
            // Se configura un tiempo máximo de espera de 2000 milisegundos (2 segundos) para recibir respuesta.
            socket.setSoTimeout(2000);

            // Se obtiene la dirección IP del servidor destino (en este caso, la máquina local).
            InetAddress servidor = InetAddress.getByName("localhost");

            // Se convierte el mensaje de texto en un array de bytes usando codificación UTF-8.
            byte[] datos = mensaje.getBytes(StandardCharsets.UTF_8);

            // Se reserva un array de bytes en memoria para almacenar la respuesta del servidor.
            byte[] buffer = new byte[1024];

            // Variables para controlar el número de reintentos y si se ha recibido respuesta.
            int reintentos = 0;
            final int maximos_reintentos = 3;
            boolean recibido = false;

            // El bucle se repite mientras no se reciba respuesta y no se supere el límite de reintentos.
            while(!recibido && reintentos <= maximos_reintentos){
                // Se crea y envía el datagrama con los datos hacia la IP y el puerto 5000 del servidor.
                socket.send(new DatagramPacket(datos, datos.length, servidor, 5000));

                // Se prepara un paquete vacío donde se volcará la respuesta entrante.
                DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length);
                try {
                    // El cliente queda a la espera de recibir el paquete durante un máximo de 2 segundos.
                    socket.receive(respuesta);

                    // Si llega la respuesta, se transforma de bytes a String leyendo solo la longitud real recibida y se imprime.
                    System.out.println(new String(buffer, 0, respuesta.getLength(), StandardCharsets.UTF_8));

                    // Se marca como recibido para salir del bucle while.
                    recibido = true;
                } catch (SocketTimeoutException e) {
                    // Si pasan los 2 segundos sin respuesta, salta esta excepción, se incrementa el contador y se avisa por consola.
                    reintentos++;
                    System.out.println("Servidor no disponible");
                }
            }

        }catch (Exception e){
            // Captura cualquier otro error de red o de entrada/salida y muestra su descripción.
            System.out.println(e.getMessage());
        }
    }
}

