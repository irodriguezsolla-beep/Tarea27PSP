import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class Servidor {
    public static  void main(String[] args){
        try(DatagramSocket socket =new DatagramSocket(5000)) {
            System.out.println("Servidor UDP escuchando en el puerto 5000...");
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket recibido = new DatagramPacket(buffer, buffer.length);
                socket.receive(recibido);
                String texto = new String(recibido.getData(), 0,
                        recibido.getLength(), StandardCharsets.UTF_8);
                System.out.println(recibido.getAddress() + ":" + recibido.getPort() + " -> " + texto);

                String[] palabras = texto.split(",");

                String masLarga = "";
                for (String p : palabras) {
                    p = p.trim();
                    if (p.length() > masLarga.length()) {
                        masLarga = p;
                    }
                }

                String salida ="La palabra más larga es "+ masLarga + " con " + masLarga.length()+" letras.";
                byte[] resp = ("ECO: " + salida).getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(resp, resp.length,
                        recibido.getAddress(), recibido.getPort()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
