package com.example.emmarher.diapa_v1;

import java.io.PrintStream;
import java.net.Socket;

public class SocketThread implements Runnable {
    private Socket socket;
    private PrintStream out;
    private Boolean connected;
    @Override
    public void run() {
        try{
            socket = new Socket ("mi direccion de ip", 5001); // Inicializa el constructor del socket
            out = new PrintStream(socket.getOutputStream(),true);
            connected = true;
        } catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
}

// PrintStream tiene la habilidad de imprimir diferentes tipos de valores de datos
// connected lo dejamos como true, para que si el socket esta conectado
//entonces procesa a enviar los datos.